package net.yungtechboy1.CyberCore.Data;

import javafx.scene.effect.Effect;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SQLite {


    public static boolean _Lock = false;
    public static int _LC = 0;
    public CyberCoreMain plugin;
    public String table = "mcpe";
    public String file = "cyber_core_data.db";
    public String createSQL = "create table if not exists " + table + " (";
    public String insertQuery = "insert into " + table + " (";
    public String addQuery = "insert into " + table + " (uuid) values (:uuid)";
    public String loadQuery = "select * from " + table + " where uuid=':uuid'";
    public String saveQuery = "update " + table + " set ";
    public HashMap<String, String> columns;
    protected boolean enabled;

    public SQLite(CyberCoreMain plugin, String settings) {
        System.setProperty("java.io.tmpdir", plugin.getDataFolder() + "/../../");
        this.plugin = plugin;
//        columns = new HashMap<>();
//        this.table = _plugin.MainConfig.getString(Settings + ".table");
//        int valueCount = 0;
//        String insertValues = "";
//        for (String columnName: _plugin.MainConfig.getSection(Settings + ".data").getAllMap().keySet()) {
//            _plugin.log(columnName);
//            String columntype = _plugin.MainConfig.getString(Settings + ".data." + columnName + ".type");
//            createSQL += columnName + " " + columntype + ", ";
//            insertQuery += columnName + ", ";
//            saveQuery += columnName + "=" + ":" + columnName + ", ";
//            insertValues += "{"+valueCount+"}, :";
//            columns.put(columnName, columntype);
//            valueCount++;
//        }
//        createSQL = createSQL.replaceAll(", $", "");
//        insertQuery = insertQuery.replaceAll(", $", "");
//        insertValues = insertValues.replaceAll(", : $", "");
//        saveQuery = saveQuery.replaceAll(", $","");
//        createSQL += ")";
//        insertQuery  += ") values (:";
//        insertQuery = insertQuery + insertValues + ")";
//        saveQuery += " where uuid=:uuid";

        file = settings + ".sql";
    }

    public ArrayList<HashMap<String, Object>> executeSelect(String query, String identifier, String search, String[] selectors) throws SQLException {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        query = query.replace(":" + identifier, search);
        plugin.log(query);
        Statement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet == null) return null;
        if (selectors == null) selectors = new String[]{"uuid"};
        while (resultSet.next()) {
            HashMap<String, Object> map = new HashMap<>();
            for (String selector : selectors) {
                map.put(selector, resultSet.getObject(selector));
            }
            data.add(map);
        }
        if (connection != null) connection.close();
        return data.isEmpty() ? null : data;
    }

    /**
     * * Connects to the MYSQL database assigned in config.yml for GLOBAL data
     * * @return Connection
     */
    public Connection connectToDb() {
        enabled = (plugin.getServer().getPluginManager().getPlugin("DbLib") != null);
        if (!enabled) return null;
        while (true) {
            if (_Lock) {
                _LC++;
                System.out.println("DB LOCKED "+_LC);
                if (_LC >= 150){
                    System.out.println("DB FULLY LOCKED!!!");
                    return null;
                }
                continue;
            }
            _Lock = true;
            java.sql.Connection connection = DbLib.getSQLiteConnection(new File(plugin.getDataFolder() + File.separator + file));
            if (connection == null) enabled = false;
            _Lock = false;
            return connection;
        }
    }

//    public ArrayList<HashMap<String, Object>> executeSelect(String query, String identifier, String search, Set<String> selectors) throws SQLException {
//        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//        Connection connection = connectToDb();
//        if (connection == null) return null;
//        ResultSet resultSet = connection.createStatement().executeQuery(query.replace(":" + identifier, search));
//        if (resultSet == null) return null;
//        while (resultSet.next()) {
//            HashMap<String, Object> map = new HashMap<>();
//            for (String selector : selectors) {
//                map.put(selector, resultSet.getObject(selector));
//            }
//            data.add(map);
//        }
//        if (connection != null) connection.close();
//        return data.isEmpty() ? null : data;
//    }

    public boolean executeUpdate(String query) throws SQLException {
        Connection connection = connectToDb();
        if (connection == null) return false;
        try {
            connection.createStatement().executeUpdate(query);
        } catch (Exception e) {
            CyberCoreMain.getInstance().getLogger().error("QUERRY : " + query);
            CyberCoreMain.getInstance().getLogger().error("ERROR!!!!!!!!!", e);
        }
        connection.close();
        return true;
    }

    public ResultSet ExecuteQuerySQLite(String sql) throws SQLException {
        Connection connection = connectToDb();
        if (connection == null) return null;
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if (resultSet == null) return null;
        return resultSet;
    }

    public List<HashMap<String, Object>> executeSelect(String query, Set<String> selectors) throws SQLException {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            HashMap<String, Object> map = new HashMap<>();
            for (String selector : selectors) {
                map.put(selector, resultSet.getObject(selector));
            }
            data.add(map);
        }
        if (connection != null) connection.close();
        return data.isEmpty() ? null : data;
    }
//    }

    public List<HashMap<String, Object>> executeSelect(String query) throws SQLException {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
//        while (true) {
//            if(_Lock){
//                _LC++;
//                if(_LC >= 150)return null;
//                continue;
//            }
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            HashMap<String, Object> map = new HashMap<>();
            ResultSet rs = resultSet;
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                map.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            data.add(map);
        }
        if (connection != null) connection.close();
        return data.isEmpty() ? null : data;
    }

}
