package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Player;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Config;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MySQL {

    public Connection connection;
    public CyberCoreMain plugin;
    public boolean enabled = true;


//    public String createSQL = "create table if not exists " + table + " (";
//    public String insertQuery = "insert into " + table + " (";
//    public String addQuery = "insert into " + table + " (uuid) values (:uuid)";
//    public String loadQuery = "select * from " + table + " where uuid=':uuid'";
//    public String saveQuery = "update " + table + " set ";

    public MySQL(CyberCoreMain plugin) {

        this.plugin = plugin;
    }

    /**
     * Connects to the MYSQL database assigned in config.yml for GLOBAL data
     * @return Connection
     */
    public Connection connectToDb() {
        String host = plugin.MainConfig.getString("mysql-host");
        String pass = plugin.MainConfig.getString("mysql-pass");
        int port = plugin.MainConfig.getInt("mysql-port");
        String user = plugin.MainConfig.getString("mysql-user");
        String db = plugin.MainConfig.getString("mysql-db");
        if (!enabled) return null;
        Connection connection = DbLib.getMySqlConnection(host, port,
                db, user, pass);

        if (connection == null) enabled = false;
        return connection;
    }

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
            byte[] ba = NBTIO.write(ct, ByteOrder.LITTLE_ENDIAN);

            pstmt.setBytes(1, ba);
            pstmt.execute();
            CyberCoreMain.getInstance().getLogger().error("ALL GGGGAAADDDD");
        } catch (IOException var3) {
           CyberCoreMain.getInstance().getLogger().error(">>>assda>>sd asda.>",var3);
        }

        if (pstmt != null) pstmt.close();
        if (connection != null) connection.close();
        return true;
    }



    public ArrayList<HashMap<String, Object>> executeSelect(String query, String identifier, String search, String[] selectors) throws SQLException {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        query = query.replace(":"+identifier, search);
        plugin.log(query);
        Statement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet == null) return null;
        if(selectors == null) selectors = new String[]{"uuid"};
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
