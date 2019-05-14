package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CoreSettings;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Warp.WarpData;
import ru.nukkit.dblib.DbLib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlt on 4/15/2019.
 */
public class ServerSqlite extends MySQL {


    public ServerSqlite(CyberCoreMain plugin) {
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

    public ArrayList<HashMap<String, Object>> executeSelect(String query) throws SQLException {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            HashMap<String, Object> map = new HashMap<>();
            ResultSet rs = resultSet;
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                map.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            data.add(map);
        }
        if (connection != null) connection.close();
        return data.isEmpty() ? null : data;
    }


    public void LoadAllWarps() {
        try {
            List<HashMap<String, Object>> data = executeSelect("SELECT * FROM `Warps`");
            if (data == null) {
                CyberCoreMain.getInstance().getLogger().error("Error Loading Warps from Sqlite!");
                return;
            } else {
                plugin.getLogger().info("Loading " + data.size() + " Warps!");
            }

            for (HashMap<String, Object> v : data) {
                plugin.WarpManager.AddWarp(new WarpData((String) v.get("name"), (double) v.get("x"), (double) v.get("y"), (double) v.get("z"), (String) v.get("level")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void LoadPlayer(Player p) {
        try{
        LoadHomes((CorePlayer) p);
        LoadSettings((CorePlayer) p);
        Faction f = plugin.FM.FFactory.IsPlayerInFaction((CorePlayer) p);
        ((CorePlayer) p).Faction = f.GetName();
    }catch (Exception e){
            CyberCoreMain.getInstance().getLogger().error("EEEEE11122223333",e);
        }
    }

    public void LoadPlayer(CorePlayer p) {
        LoadHomes(p);
    }

    public void UnLoadPlayer(Player p) {
        SaveHomes((CorePlayer) p);
        SaveSettings((CorePlayer)p);
    }

    public void UnLoadPlayer(CorePlayer p) {
        SaveHomes(p);
    }

    private void LoadHomes(CorePlayer p) {
        try {
            List<HashMap<String, Object>> data = executeSelect("SELECT * FROM `Homes` WHERE `owneruuid` LIKE '" + p.getUniqueId() + "'");
            if (data == null) {
                CyberCoreMain.getInstance().getLogger().error("Error Loading Warps from Sqlite!");
                return;
            } else {
                plugin.getLogger().info("Loading " + data.size() + " Warps!");
            }

            for (HashMap<String, Object> v : data) {
                p.AddHome(new HomeData((String) v.get("name"), (Double) v.get("x"), (Double) v.get("y"), (Double) v.get("z"), (String) v.get("level"), p));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadSettings(CorePlayer p) {
        try {
            List<HashMap<String, Object>> data = executeSelect("SELECT * FROM `Settings` WHERE `name` LIKE '" + p.getName().toLowerCase() + "'");
            if (data == null || data.size() < 1) {
                CyberCoreMain.getInstance().getLogger().error("Error Loading Settings from Sql!");
                return;
            } else {
                plugin.getLogger().info(p.getDisplayName() + "'s Settings Loaded!");
            }

            p.Settings = new CoreSettings(data.get(0));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SaveHomes(CorePlayer p) {
        try {
            executeUpdate("DELETE FROM `Homes` WHERE `owneruuid` LIKE '" + p.getUniqueId() + "'");
            for (HomeData h : p.HD) {
                executeUpdate("INSERT INTO `Homes` VALUES (0,'" + h.getName() + "'," + h.getX() + "," + h.getY() + "," + h.getZ() + ",'" + h.getLevel() + "','" + h.getOwner() + "','" + h.getOwneruuid() + "')");
            }
            plugin.getLogger().info("Homes saved for " + p.getName());
            p.sendTip("Homes Saved!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SaveSettings(CorePlayer p) {
        try {
            executeUpdate("DELETE FROM `settings` WHERE `name` LIKE '" + p.getName().toLowerCase() + "'");
            executeUpdate("INSERT INTO `settings` VALUES ('" + p.getName().toLowerCase() + "'," + p.Settings.isHudOff() + "," + p.Settings.isHudClassOff() + "," + p.Settings.isHudPosOff() + "," + p.Settings.isHudFactionOff() + ")");
            plugin.getLogger().info("Settings saved for " + p.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
