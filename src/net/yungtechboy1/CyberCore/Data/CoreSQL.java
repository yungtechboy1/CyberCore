package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import ru.nukkit.dblib.DbLib;
import sun.applet.Main;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoreSQL {

    public Connection connection;
    public CyberCoreMain plugin;
    private boolean enabled = true;

    public CoreSQL(CyberCoreMain plugin) {
        this.plugin = plugin;
    }

    /**
     * Connects to the MYSQL database assigned in config.yml for GLOBAL data
     * @return Connection
     */
    public Connection connectToDb() {
        plugin.getLogger().info("ATTEMPTING CONNECTION");
        String host = plugin.MainConfig.getString("mysql-host");
        String pass = plugin.MainConfig.getString("mysql-pass");
        int port = plugin.MainConfig.getInt("mysql-port");
        String user = plugin.MainConfig.getString("mysql-user");
        String db = plugin.MainConfig.getString("mysql-db");
        if (!enabled) return null;
        Connection connection = DbLib.getMySqlConnection(host, port,
                db, user, pass);

        if (connection == null) enabled = false;
        try {
            if(connection.isValid(10)) {
                plugin.getLogger().info("SQL CONNEECTION VALID!");
            } else {
                plugin.getLogger().error("SQL CONNEECTION INVALID!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

//
//    public Integer getInteger(String table, String condition, Object value, String selector) throws SQLException {
//        int result;
//        String query = ("SELECT * FROM " + table + " WHERE " + condition + "=" + value);
//        Statement statement = connection.prepareStatement(query);
//        ResultSet resultSet = statement.executeQuery(query);
//        result = resultSet.getInt(selector);
//        if (statement != null) statement.close();
//        if (connection != null) connection.close();
//        return result;
//    }



    public void addUser(String uuid, String ip) throws SQLException {
        executeUpdate("insert into mcpe (uuid, last_ip) values ('"+uuid+"' , '"+ip+"')");
        plugin.getLogger().info("User: " + uuid + " - added to DB");
    }





    public void checkUser(Player player) throws SQLException {
        String uuid = player.getUniqueId().toString();
        String ip = player.getAddress();
        plugin.getLogger().info(ip);
        String query = "SELECT * FROM mcpe WHERE uuid='" + uuid + "'";
        String[] selector = {"uuid","last_ip","rank"};
        ArrayList<HashMap<String, Object>> data = executeSelect(query, selector);
        if(data == null) {
            plugin.getLogger().info("USER BEING CREATED!!!");
            addUser(uuid, ip);
            plugin.UserSQL.addUser(uuid);
            int rank = 0;
            plugin.RankFactory.RankCache.put(uuid, rank);
        } else {
            plugin.getLogger().info(data.get(0).get("last_ip").toString());
            if(!data.get(0).get("last_ip").equals(ip)) {
                Config ip_change = new Config(new File(plugin.getDataFolder(), "ip_changes.yml"));
                ip_change.set(uuid, ip_change.getList(uuid).add(ip));
                ip_change.save();
            }
            int rank = (int) data.get(0).get("rank");
            plugin.RankFactory.RankCache.put(uuid, rank);
            plugin.UserSQL.loadUser(uuid);
            plugin.getLogger().info("USER EXISTS!!!");
        }
        plugin.UserSQL.saveUser(plugin.getCorePlayer(player));
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

    public ArrayList<HashMap<String, Object>> executeSelect(String query, String[] selectors) throws SQLException {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        Statement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);
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
