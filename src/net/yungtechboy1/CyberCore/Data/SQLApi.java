package net.yungtechboy1.CyberCore.Data;

import sun.applet.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLApi {

    public SQLConnection connection;

    public SQLApi(SQLConnection connection) {
        this.connection = connection;
    }

    public Integer getInteger(String table, String condition, Object value, String selector) {
        try {
            ResultSet r = connection.executeSql("SELECT 1 FROM " + table + " WHERE " + condition + "=" + value);
            return r.getInt(selector);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }






    public void addCoreUser(String uuid) {
        try {
            String query = "insert mcpe set uuid = ? ";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            stmt.setString(1,uuid);
            connection.insertSql(stmt);
            connection.Main.getLogger().info("User: " + uuid + " - added to DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public void checkCoreUser(String uuid) {
        try {
            ResultSet r = connection.executeSql("SELECT 1 FROM mcpe WHERE uuid='" + uuid + "'");
            if(r == null) {
                connection.Main.getLogger().info("USER BEING CREATED!!!");
                addCoreUser(uuid);
            } else {
                r.close();
                connection.Main.getLogger().info("USER EXISTS!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
