package net.yungtechboy1.CyberCore.Data;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import java.sql.Connection;
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
        Integer valueCount = 0;
        String insertValues = "";
        for (String columnName: plugin.MainConfig.getSection(settings).getAllMap().keySet()) {
            createSQL += ", ";
            insertQuery += ", ";
            insertValues += ", :";
            String columntype = plugin.MainConfig.getString(settings + "." + columnName + ".type");
            columns.put(columnName, columntype);
            createSQL += columnName + " " + columntype;
            insertQuery += columnName;
            insertValues += "{"+valueCount+"}";
            valueCount++;
        }
        createSQL += ")";
        insertQuery  += ") values (:";
        insertQuery = insertQuery + insertValues + ")";

        file = plugin.MainConfig.getString(settings + ".file");
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
        executeUpdate(addQuery.replace(":uuid", uuid));
        saveUser(plugin.getCorePlayer(uuid));
    }

    public void saveUser(CorePlayer player) {
        try {
            executeUpdate("update " + table + " set kills="+player.kills+", deaths="+player.deaths+" where uuid="+player.getUniqueId().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUser(String uuid) {
        List<? extends HashMap> list = null;
        try (Connection con = connectToDb()) {
            ResultSet resultSet = con.createStatement().executeQuery(loadQuery);
            CorePlayer p = plugin.getCorePlayer(uuid);
            while(resultSet.next()) {
                p.kills = (Integer) resultSet.getInt("kills");
                p.deaths = (Integer) resultSet.getInt("deaths");
                p.setBanned(resultSet.getInt("banned") == 0 ? false : true);
            }
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


    public List<HashMap<String, Object>> executeSelect(String query, String[] selectors) throws SQLException {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            HashMap<String,Object> map = new HashMap<>();
            for (String selector:selectors) {
                map.put(selector, resultSet.getObject(selector));
            }
            data.add(map);
        }
        if (connection != null) connection.close();
        return data;
    }



}
