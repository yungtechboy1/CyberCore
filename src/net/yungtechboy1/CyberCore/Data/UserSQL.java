package net.yungtechboy1.CyberCore.Data;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.PlayerSettingsData;
import ru.nukkit.dblib.DbLib;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserSQL extends MySQL {


    /**
     * Connects to the MYSQL database assigned in config.yml for GLOBAL data
     *
     * @return Connection
     */

    public String table = "mcpe";
    public String file = "cyber_core_data.db";
    public String createSQL = "create table if not exists " + table + " (";
    public String insertQuery = "insert into " + table + " (";
    public String addQuery = "insert into " + table + " (uuid) values (:uuid)";
    public String loadQuery = "select * from " + table + " where uuid=':uuid'";
    public String saveQuery = "update " + table + " set ";
    public HashMap<String, String> columns;

    public UserSQL(CyberCoreMain plugin) {
        super(plugin);
    }

    @Override
    public Connection connectToDb() {
        String host = plugin.MainConfig.getSection("db2").getString("mysql-host");
        String pass = plugin.MainConfig.getSection("db2").getString("mysql-pass");
        int port = plugin.MainConfig.getSection("db2").getInt("mysql-port");
        String user = plugin.MainConfig.getSection("db2").getString("mysql-user");
        String db = plugin.MainConfig.getSection("db2").getString("mysql-db-Server");
        if (!enabled) return null;
        Connection connection = DbLib.getMySqlConnection(host, port,
                db, user, pass);

        if (connection == null) enabled = false;
        return connection;
    }

    public boolean isinDB(CorePlayer p) {
        try {

            ArrayList<HashMap<String, Object>> a = executeSelect("SELECT * FROM `PlayerSettings` WHERE `Name` LIKE '" + p.getName() + "'");
            if (a == null) return false;
            if (a.size() == 0) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public PlayerSettingsData getPlayerSettingsData(CorePlayer corePlayer) {
        PlayerSettingsData psd = new PlayerSettingsData(corePlayer);
        try {

            ArrayList<HashMap<String, Object>> a = executeSelect("SELECT * FROM `PlayerSettings` WHERE `Name` LIKE '" + corePlayer.getName() + "'");
            if (a == null || a.size() == 0) {
                corePlayer.setSettingsData(psd);
                return psd;
            }
            psd = new PlayerSettingsData(a.get(0));
            if (!psd.UUIDS.contains(corePlayer.getUniqueId())) psd.UUIDS.add(corePlayer.getUniqueId());
            corePlayer.setSettingsData(psd);
            return psd;
        } catch (Exception e) {
            plugin.getLogger().error("Error getting UserSQL PlayerSettingData");
            e.printStackTrace();
            return null;
        }
    }

    public boolean savePlayerSettingData(CorePlayer corePlayer) {
        PlayerSettingsData psd = corePlayer.getSettingsData();
        try {
        try {
            executeUpdate("DELETE * FROM `PlayerSettings` WHERE `Name` LIKE '" + corePlayer.getName() + "'");
        }catch (Exception e){
            e.printStackTrace();
        }
            String q = "INSERT INTO `PlayerSettings` VALUES (";
            q = addToQuery(q, psd.Name) + ",";
            q = addToQuery(q, psd.UUIDSToJSON()) + ",";
            q = addToQuery(q, psd.Cash) + ",";
            q = addToQuery(q, psd.CreditScore) + ",";
            q = addToQuery(q, psd.CreditLimit) + ",";
            q = addToQuery(q, psd.UsedCredit) + ",";
            q = addToQuery(q, psd.PlayerWarningToJSON()) + ",";
            q = addToQuery(q, psd.PlayerTempBansToJSON()) + ",";
            q = addToQuery(q, psd.PlayerKicksToJSON()) + ",";
            q = addToQuery(q, psd.PlayerBansToJSON()) + ",";
            q = addToQuery(q, psd.Rank);
            q += ")";
            plugin.getLogger().info("Saved Player With SQL:" + q);
            executeUpdate(q);
            return true;

        } catch (Exception e) {
            plugin.getLogger().error("Error getting UserSQL PlayerSettingData");
            e.printStackTrace();
            return false;
        }
    }

    private String addToQuery(String q, String v) {
        return q += "`" + v + "`";
    }

    private String addToQuery(String q, int v) {
        return q += v;
    }

    private String addToQuery(String q, double v) {
        return q += "`" + (int) v + "`";
    }

//    public void loadUser(String uuid) {
//        try {
//            List<HashMap<String,Object>> data = executeSelect(loadQuery, "uuid", uuid, columns.keySet());
//            CorePlayer p = _plugin.getCorePlayer(uuid);
//            for(String sel: columns.keySet()) {
//                if(!sel.equalsIgnoreCase("uuid")) {
//                    p.getClass().getField(sel).set(p, data.get(0).get(sel));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//    }
}
