package net.yungtechboy1.CyberCore.Data;

import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.sql.*;

public class SQLConnection {

    public final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public final String DB_URL = "jdbc:mysql://truelinux.cf:3306/truelinu_mcpe";

    public final String USER = "truelinu_admin";
    public final String PASS = "3X0T~l1p8mJ*";

    public CyberCoreMain Main;

    public Connection conn;

    public SQLConnection(CyberCoreMain Main) {
        this.Main = Main;
        connect();
    }

    private void disconnect() {
        try {
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        Statement stmt = null;
        try{

            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            Main.getLogger().info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(conn.isValid(10)) {
                Main.getLogger().info("Connected!");
            }
//            //STEP 4: Execute a query
//            System.out.println("Creating statement...");
//            stmt = conn.createStatement();
//            String sql;
//            //sql = "SELECT 1 FROM mcpe WHERE key= ";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            //STEP 5: Extract data from result set
//            while(rs.next()){
//                //Retrieve by column name
//                int id  = rs.getInt("id");
//                int age = rs.getInt("age");
//                String first = rs.getString("first");
//                String last = rs.getString("last");
//
//                //Display values
//                System.out.print("ID: " + id);
//                System.out.print(", Age: " + age);
//                System.out.print(", First: " + first);
//                System.out.println(", Last: " + last);
//            }
//          rs.close();
        }catch(SQLException se){
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeSql(String sql) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(conn.isValid(10) && rs.next()) {
                Main.getLogger().info("RUNNING A SQL!");
                return rs;
            } else {
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public void insertSql(PreparedStatement stmt) {
        try {
            int rs = stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
