package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.IOException;
import java.nio.ByteOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class MySQL {

    public Connection connection;
    public CyberCoreMain Plugin;
    public boolean enabled = true;


//    public String createSQL = "create table if not exists " + table + " (";
//    public String insertQuery = "insert into " + table + " (";
//    public String addQuery = "insert into " + table + " (uuid) values (:uuid)";
//    public String loadQuery = "select * from " + table + " where uuid=':uuid'";
//    public String saveQuery = "update " + table + " set ";

    public MySQL(CyberCoreMain plugin) {

        Plugin = plugin;
    }

    /**
     * Connects to the MYSQL database assigned in config.yml for GLOBAL data
     *
     * @return Connection
     */
    public abstract Connection connectToDb();// {
//        String host = Plugin.MainConfig.getString("mysql-host");
//        String pass = Plugin.MainConfig.getString("mysql-pass");
//        int port = Plugin.MainConfig.getInt("mysql-port");
//        String user = Plugin.MainConfig.getString("mysql-user");
//        String db = Plugin.MainConfig.getString("mysql-db");
//        if (!enabled) return null;
//        Connection connection = DbLib.getMySqlConnection(SBBB(host, port,
//                db, user, pass);
//
//        if (connection == null) enabled = false;
//        return connection;
//    }

    public boolean executeUpdate(String query) throws SQLException {
        Connection connection = connectToDb();
        if (connection == null) return false;
        Statement statement = connection.prepareStatement(query);
        statement.executeUpdate(query);
        if (statement != null) statement.close();
        if (connection != null) connection.close();
        return true;
    }

    public boolean executeUpdate(String query, CompoundTag ct) throws SQLException {
        Connection connection = connectToDb();
        if (connection == null) return false;

        PreparedStatement pstmt = connection.prepareStatement(query);
        try {
//            ct.setName("");
            System.out.println("E112222223222222222222222222 > "+ct);
//            System.out.println("E1122222232 > "+ct.toString());
            byte[] ba = new byte[0];
            if(ct != null) {
                ba = NBTIO.write(ct, ByteOrder.LITTLE_ENDIAN);
                System.out.println("CT AWS WAS NOT NULLLLLL");
            }
            System.out.println("E1132 > |21| "+new String(ba)+"||3333|| "+ba);


            pstmt.setBytes(1, ba);
            pstmt.execute();
            CyberCoreMain.getInstance().getLogger().error("ALL GGGGAAADDDD");
        } catch (IOException var3) {
            CyberCoreMain.getInstance().getLogger().error(">>>assda>>sd asda.>", var3);
        }

        if (pstmt != null) pstmt.close();
        if (connection != null) connection.close();
        return true;
    }


    public ArrayList<HashMap<String, Object>> executeSelect(String query) throws SQLException {
        return executeSelect(query, null);

    }

    public ArrayList<HashMap<String, Object>> executeSelect(String query, String[] selectors) throws SQLException {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        Statement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            HashMap<String, Object> map = new HashMap<>();
            if (selectors != null) {
                for (String selector : selectors) {
                    map.put(selector, resultSet.getObject(selector));
                }
            } else {
                for (int i = 1; i < resultSet.getMetaData().getColumnCount()+1; i++) {
//                    System.out.println(i);
                    System.out.println(resultSet.getMetaData().getColumnName(i)+"|"+resultSet.getObject(i));
                    System.out.println("=====================");
                    map.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
                }
            }

            data.add(map);
        }
        if (connection != null) connection.close();
        return data.isEmpty() ? null : data;
    }


}
