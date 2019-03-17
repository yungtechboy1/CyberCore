package net.yungtechboy1.CyberCore.Data;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import ru.nukkit.dblib.DbLib;
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
        String host = plugin.MainConfig.getString("mysql-host");
        String pass = plugin.MainConfig.getString("mysql-pass");
        int port = plugin.MainConfig.getInt("mysql-port");
        String user = plugin.MainConfig.getString("mysql-user");
        String db = plugin.MainConfig.getString("mysql-db");
        Connection connectionSource =
                DbLib.getMySqlConnection(host,port,db, user, pass);
        if (connectionSource == null) enabled=false;
        return connectionSource;
    }

    public Integer getInteger(String table, String condition, Object value, String selector) throws SQLException {
        return getInteger(table, condition, value, selector, -1);
    }

    public Integer getInteger(String table, String condition, Object value, String selector , Integer def) throws SQLException {
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
        Connection connection = connectToDb();
        String query = "insert mcpe set uuid = '"+uuid+"'";
        Statement stmt = connection.prepareStatement(query);
        stmt.executeUpdate(query);
        plugin.getLogger().info("User: " + uuid + " - added to DB");
    }






    public void checkCoreUser(String uuid) throws SQLException {
        Connection connection = connectToDb();
        String query = "SELECT * FROM mcpe WHERE uuid='" + uuid + "'";
        Statement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery(query);
        if(rs == null) {
            plugin.getLogger().info("USER BEING CREATED!!!");
            addCoreUser(uuid);
        } else {
            if(rs.next()) {
                int rank = rs.getInt("rank");
                plugin.RankFactory.RankCache.put(uuid, rank);
                rs.close();
                plugin.getLogger().info("USER EXISTS!!!");
            }
        }
    }

}
