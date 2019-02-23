package net.yungtechboy1.CyberCore.Manager;

import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by carlt on 2/23/2019.
 */
public class SQLManager {

    public Connection Connect = null;
    CyberCoreMain Main;
     public SQLManager(CyberCoreMain m){
         Main = m;
     }

    public Connection getMySqlConnection() {
        //return getMySqliteConnection();
        try {
            if (Connect != null && !Connect.isClosed()) return Main.Connect;
            Main.getServer().getLogger().info("REGEtting Connection!");
            Class.forName("com.mysql.jdbc.Driver");
            Connect = DriverManager.getConnection("jdbc:mysql://localhost/Terratide?user=TerraTideMC&password=TerraTideMC");
            return Connect;
        } catch (Exception ex) {
            return null;
        }
    }

    public void EchoException(Exception ex){
        Main.getServer().getLogger().info( ex.getClass().getName() + ":2 " + ex.getMessage() );
    }

    public ResultSet ExecuteQuerySQLite(String s){
        try {
            Statement stmt = this.getMySqlConnection().createStatement();
            ResultSet r  = stmt.executeQuery(s);
            //this.getServer().getLogger().info( s );
            return r;
        } catch (Exception  ex) {

            Main.getServer().getLogger().info( ex.getClass().getName() + ":10 " + ex.getMessage() );
            this.EchoException(ex);
            return null;
        }
    }

    public boolean ExecuteUpdateSQLite(String s){
        try {
            Statement stmt = this.getMySqlConnection().createStatement();
            stmt.executeUpdate(s);
            Main.getServer().getLogger().info( s );
            stmt.close();
            return true;
        } catch (Exception  ex) {
            Main.getServer().getLogger().info( ex.getClass().getName() + ":3 " + ex.getMessage() );
            return false;
        }
    }

    public boolean ExecuteUpdateSQLite(String[] ss){
        try {
            Statement stmt = this.getMySqlConnection().createStatement();
            for(String s: ss){
                stmt.executeUpdate(s);
            }
            stmt.close();
            return true;
        } catch (Exception  ex) {
            Main.getServer().getLogger().info( ex.getClass().getName() + ":4 " + ex.getMessage() );
            return false;
        }
    }

    private void Init(){
         Init_Facs();
    }

    private void Init_Facs(){
        try {//ss
            Statement stmt = getMySqlConnection().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS \"master\" " +
                    "`player` VARCHAR(250)PRIMARY KEY     NOT NULL," +
                    " `faction`           VARCHAR(250)    NOT NULL, " +
                    " `rank`            VARCHAR(250)     NOT NULL)";
            String sql1 = "CREATE TABLE IF NOT EXISTS \"allies\" (  " +
                    "                        `id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,  " +
                    "                        `factiona` VARCHAR(250)NOT NULL,  " +
                    "                        `factionb` VARCHAR(250)NOT NULL,  " +
                    "                        `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  " +
                    "                        );";
            String sql2 = "CREATE TABLE IF NOT EXISTS \"confirm\" (" +
                    "`player`TEXT NOT NULL," +
                    "`faction`TEXT NOT NULL," +
                    "`timestamp`INTEGER," +
                    "`id`INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT" +
                    ")";
            String sql3 = "CREATE TABLE  IF NOT EXISTS \"home\" (" +
                    "                     `faction` VARCHAR(250)NOT NULL UNIQUE, " +
                    "                     `x` int(250) NOT NULL, " +
                    "                     `y` int(250) NOT NULL, " +
                    "                     `z` int(250) NOT NULL, " +
                    "                     PRIMARY KEY(faction) " +
                    "                    );";
            String sql4 = "CREATE TABLE IF NOT EXISTS \"plots\" (  " +
                    "            `id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,  " +
                    "            `faction` varchar(250) NOT NULL,  " +
                    "            `x` int(250) NOT NULL,  " +
                    "            `z` int(250) NOT NULL  " +
                    "            );";
            String sql6 = "CREATE TABLE IF NOT EXISTS \"settings\" (" +
                    "`faction`varchar(250) NOT NULL UNIQUE," +
                    "`max`int(250) NOT NULL," +
                    "`powerbonus`int(50) NOT NULL," +
                    "`MOTD`varchar(1000) NOT NULL," +
                    "`displayname`varchar(1000) NOT NULL," +
                    "`desc`varchar(1000) NOT NULL," +
                    "`prem`INTEGER NOT NULL," +
                    "`privacy`int(11) NOT NULL," +
                    "`power`INTEGER NOT NULL," +
                    "`money`INTEGER NOT NULL," +
                    "`point`INTEGER NOT NULL," +
                    "`xp`INTEGER NOT NULL," +
                    "`lvl`INTEGER NOT NULL," +
                    "`cmid`varchar(200) NOT NULL," +
                    "`am`varchar(200) NOT NULL," +
                    "`rich`INTEGER NOT NULL," +
                    "PRIMARY KEY(faction)" +
                    ")";
            String sql7 = "CREATE TABLE IF NOT EXISTS \"war\" (" +
                    "`defendingfaction` varchar(250) NOT NULL UNIQUE," +
                    "`attackingfaction` varchar(250) NOT NULL UNIQUE," +
                    "`start` int(250) NOT NULL," +
                    "`stop` int(250) NOT NULL," +
                    "PRIMARY KEY(defendingfaction)" +
                    ")";
            String sql8 = "CREATE TABLE IF NOT EXISTS \"overclaim\" (" +
                    "`defendingfaction` varchar(250) NOT NULL UNIQUE," +
                    "`attackingfaction` varchar(250) NOT NULL UNIQUE," +
                    "`start`int(250) NOT NULL," +
                    "`attacktime` int(50) NOT NULL," +
                    "`defendtime` int(50) NOT NULL," +
                    "`endtime` int(50) NOT NULL," +
                    "`x` int(50) NOT NULL," +
                    "`z` int(50) NOT NULL," +
                    "PRIMARY KEY(faction)" +
                    ")";
            String sql9 = "CREATE TABLE IF NOT EXISTS \"enemies\" (  " +
                    "                        `id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,  " +
                    "                        `factiona` VARCHAR(250)NOT NULL,  " +
                    "                        `factionb` VARCHAR(250)NOT NULL,  " +
                    "                        `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  " +
                    "                        );";
            //String sql7 = "CREATE TABLE IF NOT EXISTS war ( attacker varchar(250) NOT NULL PRIMARY KEY, defender varchar(250) NOT NULL, start int(250) NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.executeUpdate(sql4);
            stmt.executeUpdate(sql6);
            //sstmt.executeUpdate(sql7);
            //stmt.executeUpdate(sql8);
            //this.getServer().getLogger().info("ECECUTE 8");
            stmt.executeUpdate(sql9);
            stmt.close();
        } catch (Exception  ex) {
            Main.getServer().getLogger().info( ex.getClass().getName() + ":1 " + ex.getMessage() );
        }
        return;
    }
}
