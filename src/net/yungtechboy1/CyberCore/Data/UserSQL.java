package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import java.sql.Connection;

import org.apache.logging.log4j.core.Core;
import org.sql2o.Sql2o;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class UserSQL {

    public CyberCoreMain plugin;

    private boolean enabled;

    private String table = "mcpe";
    private String file;

    private String createSQL = "create table if not exists "+table+" (";
    private String insertQuery = "insert into "+table+" (";
    private String addQuery = "insert into "+table+" (uuid) values (:uuid)";
    private String loadQuery = "select * from "+table+" where uuid=:uuid";
    private String saveQuery = "update "+table+" set ";

    private HashMap<String, String> columns;


    public UserSQL(CyberCoreMain plugin, String settings) {
        System.setProperty("java.io.tmpdir", plugin.getDataFolder() + "/../../");
        this.plugin = plugin;
        columns = new HashMap<>();
        this.table = plugin.MainConfig.getString(settings + ".table");
        int valueCount = 0;
        String insertValues = "";
        for (String columnName: plugin.MainConfig.getSection(settings + ".data").getAllMap().keySet()) {
            plugin.log(columnName);
            String columntype = plugin.MainConfig.getString(settings + ".data." + columnName + ".type");
            createSQL += columnName + " " + columntype;
            insertQuery += columnName;
            insertValues += "{"+valueCount+"}";
            createSQL += ", ";
            insertQuery += ", ";
            insertValues += ", :";
            columns.put(columnName, columntype);
            valueCount++;
        }
        createSQL = createSQL.replaceAll(", $", "");
        insertQuery = insertQuery.replaceAll(", $", "");
        insertValues = insertValues.replaceAll(", : $", "");
        createSQL += ")";
        insertQuery  += ") values (:";
        insertQuery = insertQuery + insertValues + ")";

        file = plugin.MainConfig.getString(settings + ".file");

        try {
            if (executeUpdate(createSQL)) plugin.log("Table successfully created");
            else {
                plugin.log("&cFailed to create table!");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects to the MYSQL database assigned in config.yml for GLOBAL data
     * @return Connection
     */
    public Connection connectToDb() {
        enabled = (plugin.getServer().getPluginManager().getPlugin("DbLib") != null);
        if (!enabled) return null;
        java.sql.Connection connection = DbLib.getSQLiteConnection(new File(plugin.getDataFolder() + File.separator +file));
        if (connection == null) enabled = false;
        return connection;
    }


    public void addUser(String uuid) throws SQLException {
        if (!enabled) {
            plugin.log("Failed to execute SQLite test");
            return;
        }
        executeUpdate(addQuery.replace(":uuid", "'"+uuid+"'"));
        plugin.getLogger().info("SAVING");
    }

    public void saveUser(CorePlayer player) {
        try {
            if(player instanceof CorePlayer) {
                executeUpdate("update " + table + " set kills=" + player.kills + ", deaths=" + player.deaths + ", banned=" + player.banned +" where uuid='" + player.getUniqueId().toString()+"'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUser(String uuid) {
        List<? extends HashMap> list = null;
        String[] selectors = {"kills","deaths","banned"};
        try {
            List<HashMap<String,Object>> data = executeSelect(loadQuery, uuid, selectors);
            CorePlayer p = plugin.getCorePlayer(uuid);
            p.kills = (Integer) data.get(0).get("kills");
            p.deaths = (Integer) data.get(0).get("deaths");
            p.setBanned((Integer) data.get(0).get("banned") != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean executeUpdate(String query) throws SQLException {
        Connection connection = connectToDb();
        if (connection == null) return false;
        connection.createStatement().executeUpdate(query);
        if (connection != null) connection.close();
        return true;
    }


    public List<HashMap<String, Object>> executeSelect(String query, String uuid, String[] selectors) throws SQLException {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        ResultSet resultSet = connection.createStatement().executeQuery(query.replace(":uuid", "'"+uuid+"'"));
        if (resultSet == null) return null;
        while (resultSet.next()) {
            plugin.log("USER EXISTS IN LOCAL DB!");
            HashMap<String,Object> map = new HashMap<>();
            for (String selector:selectors) {
                plugin.log(selector);
                map.put(selector, resultSet.getObject(selector));
            }
            data.add(map);
        }
        if (connection != null) connection.close();
        return data.isEmpty() ? null: data;
    }



}
