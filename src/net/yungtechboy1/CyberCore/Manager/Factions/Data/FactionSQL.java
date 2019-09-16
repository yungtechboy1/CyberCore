package net.yungtechboy1.CyberCore.Manager.Factions.Data;

import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Data.MySQL;
import ru.nukkit.dblib.DbLib;

import java.sql.Connection;

public class FactionSQL extends MySQL {


    public static int k = 0;
    public static Connection SC = null;

    public FactionSQL(CyberCoreMain plugin) {

        super(plugin);

    }

    public static String SBBB(String host, int port, String database) {
        StringBuilder sb = new StringBuilder(host);
        if (port >= 0) {
            sb.append(":").append(port);
        }
        k++;
        if (k % 10 == 0) System.out.println("===========SSB CALLED >> X " + k);
        sb.append("/").append(database);
        sb.append("?useSSL=false&serverTimezone=CST6CDT");
        return sb.toString();
    }

    @Override
    public Connection connectToDb() {
        String host = plugin.MainConfig.getSection("db2").getString("mysql-host");
        String pass = plugin.MainConfig.getSection("db2").getString("mysql-pass");
        int port = plugin.MainConfig.getSection("db2").getInt("mysql-port");
        String user = plugin.MainConfig.getSection("db2").getString("mysql-user");
        String db = plugin.MainConfig.getSection("db2").getString("mysql-db-Faction");
        if (SC != null) {
            try {
                if (!SC.isClosed()) return SC;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!enabled) return null;

        Connection connection = DbLib.getMySqlConnection(SBBB(host, port, db), user, pass);

        if (connection == null) {
            System.out.println("CONEEEEECTTTTTTTTTIONNNNNNNNNNNN FAILEDDDDDDDD!!!!!!!!!!!!");
            enabled = false;
        } else {
            SC = connection;
        }
        return connection;
    }


}
