package net.yungtechboy1.CyberCore.Data;

import net.yungtechboy1.CyberCore.CyberCoreMain;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SQLite {


    public CyberCoreMain plugin;

    protected boolean enabled;

    public String table = "mcpe";
    public String file = "cyber_core_data.db";

    public String createSQL = "create table if not exists "+table+" (";
    public String insertQuery = "insert into "+table+" (";
    public String addQuery = "insert into "+table+" (uuid) values (:uuid)";
    public String loadQuery = "select * from "+table+" where uuid=':uuid'";
    public String saveQuery = "update "+table+" set ";

    public HashMap<String, String> columns;



    public SQLite(CyberCoreMain plugin, String settings) {
        System.setProperty("java.io.tmpdir", plugin.getDataFolder() + "/../../");
        this.plugin = plugin;
        columns = new HashMap<>();
        this.table = plugin.MainConfig.getString(settings + ".table");
        int valueCount = 0;
        String insertValues = "";
        for (String columnName: plugin.MainConfig.getSection(settings + ".data").getAllMap().keySet()) {
            plugin.log(columnName);
            String columntype = plugin.MainConfig.getString(settings + ".data." + columnName + ".type");
            createSQL += columnName + " " + columntype + ", ";
            insertQuery += columnName + ", ";
            saveQuery += columnName + "=" + ":" + columnName + ", ";
            insertValues += "{"+valueCount+"}, :";
            columns.put(columnName, columntype);
            valueCount++;
        }
        createSQL = createSQL.replaceAll(", $", "");
        insertQuery = insertQuery.replaceAll(", $", "");
        insertValues = insertValues.replaceAll(", : $", "");
        saveQuery = saveQuery.replaceAll(", $","");
        createSQL += ")";
        insertQuery  += ") values (:";
        insertQuery = insertQuery + insertValues + ")";
        saveQuery += " where uuid=:uuid";

        file = plugin.MainConfig.getString(settings + ".file");

        try {
            if (executeUpdate(createSQL)) plugin.log("Table successfully created");
            else {
                plugin.log("&cFailed to create table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *      * Connects to the MYSQL database assigned in config.yml for GLOBAL data
     *      * @return Connection
     */
    public Connection connectToDb() {
        enabled = (plugin.getServer().getPluginManager().getPlugin("DbLib") != null);
        if (!enabled) return null;
        java.sql.Connection connection = DbLib.getSQLiteConnection(new File(plugin.getDataFolder() + File.separator +file));
        if (connection == null) enabled = false;
        return connection;
    }


    public boolean executeUpdate(String query) throws SQLException {
        Connection connection = connectToDb();
        if (connection == null) return false;
        connection.createStatement().executeUpdate(query);
        if (connection != null) connection.close();
        return true;
    }


    public ArrayList<HashMap<String, Object>> executeSelect(String query, String identifier, String search, Set<String> selectors) throws SQLException {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        ResultSet resultSet = connection.createStatement().executeQuery(query.replace(":"+identifier, search));
        if (resultSet == null) return null;
        while (resultSet.next()) {
            HashMap<String,Object> map = new HashMap<>();
            for (String selector:selectors) {
                map.put(selector, resultSet.getObject(selector));
            }
            data.add(map);
        }
        if (connection != null) connection.close();
        return data.isEmpty() ? null: data;
    }

}
