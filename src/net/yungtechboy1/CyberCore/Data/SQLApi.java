package net.yungtechboy1.CyberCore.Data;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import ru.nukkit.dblib.DbLib;
import ru.nukkit.dblib.core.M;
import sun.applet.Main;

import java.sql.*;

public class SQLApi {

    public Connection connection;
    public CyberCoreMain plugin;
    private boolean enabled;

    public SQLApi(CyberCoreMain plugin) {
        this.plugin = plugin;
    }

    public Connection connectToDb() {
        System.out.println("2222222222222222222222221111111111100000000000000000000");
        try {
            Class.forName("ru.nukkit.dblib.DbLib");
        } catch (ClassNotFoundException var5) {
            System.out.println("FUKKKKKKKKKKKK");
        }
        String host = plugin.MainConfig.getString("mysql-host");
        String pass = plugin.MainConfig.getString("mysql-pass");
        int port = plugin.MainConfig.getInt("mysql-port");
        String user = plugin.MainConfig.getString("mysql-user");
        String db = plugin.MainConfig.getString("mysql-db");
        if (host == null) System.out.println("1");
        if (pass == null) System.out.println("2");
        if (db == null) System.out.println("3");
        System.out.println("122222||" + port);
        if (user == null) System.out.println("4");
        try {
            Connection connectionSource =
                    DbLib.getMySqlConnection(host, port, db, user, pass);
            if (connectionSource == null) enabled = false;
            return connectionSource;
        } catch (Exception e) {
            enabled = false;
            return null;
        }
    }

    public Integer getInteger(String table, String condition, Object value, String selector) throws SQLException {
        return getInteger(table, condition, value, selector, -1);
    }

    public Integer getInteger(String table, String condition, Object value, String selector, Integer def) throws SQLException {
        int result;
        String query = ("SELECT * FROM " + table + " WHERE " + condition + "=" + value);
        Statement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);
        result = resultSet.getInt(selector);
        if (statement != null) statement.close();
        if (connection != null) connection.close();
        return result;
    }


    public void addCoreUser(String uuid) throws SQLException {
        if (!enabled) return;
        Connection connection = connectToDb();
        String query = "insert mcpe set uuid = '" + uuid + "'";
        Statement stmt = connection.prepareStatement(query);
        stmt.executeUpdate(query);
        plugin.getLogger().info("User: " + uuid + " - added to DB");
    }


    public void checkCoreUser(String uuid) throws SQLException {
        if (!enabled) {
            plugin.RankFactory.RankCache.put(uuid, 0);
            return;
        }
        Connection connection = connectToDb();
        String query = "SELECT * FROM mcpe WHERE uuid='" + uuid + "'";
        Statement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery(query);
        if (rs == null) {
            plugin.getLogger().info("USER BEING CREATED!!!");
            addCoreUser(uuid);
        } else {
            if (rs.next()) {
                int rank = rs.getInt("rank");
                plugin.RankFactory.RankCache.put(uuid, rank);
                rs.close();
                plugin.getLogger().info("USER EXISTS!!!");
            }
        }
    }

}
