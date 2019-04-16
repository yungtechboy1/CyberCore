package net.yungtechboy1.CyberCore.Data;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Warp.WarpData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlt on 4/15/2019.
 */
public class ServerSqlite extends SQLite {


    public ServerSqlite(CyberCoreMain plugin, String settings) {
        super(plugin, settings);
    }

    public boolean LoadAllWarps() {
        try {
            List<HashMap<String, Object>> data = executeSelect("SELECT * FROM `Warps`");
            if(data != null) {
                CyberCoreMain.getInstance().getLogger().error("Error Loading Warps from Sqlite!");
                return true;
            }
            for(HashMap<String, Object> v : data){
                plugin.WarpManager.AddWarp(new WarpData((String)v.get("name"),(double)v.get("x"),(double)v.get("y"),(double)v.get("z"),(String)v.get("level")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            List<HashMap<String, Object>> data = executeSelect(loadQuery,"uuid", identity, null);
            if(data != null) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void addUser(String uuid) throws SQLException {
        if (!enabled) {
            plugin.log("SQLite disabled but still running!!!");
            return;
        }
        executeUpdate(addQuery.replace(":uuid", "'"+uuid+"'"));
    }

    public void saveUser(CorePlayer player) {
        try {
            if(player != null) {
                executeUpdate("update " + table + " set kills=" + player.kills + ", deaths=" + player.deaths + ", banned=" + player.banned +" where uuid='" + player.getUniqueId().toString()+"'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUser(String uuid) {
        List<? extends HashMap> list = null;
        try {
            List<HashMap<String,Object>> data = executeSelect(loadQuery,"uuid", uuid, columns.keySet());
            CorePlayer p = plugin.getCorePlayer(uuid);
            p.kills = (Integer) data.get(0).get("kills");
            p.deaths = (Integer) data.get(0).get("deaths");
            p.setBanned((Integer) data.get(0).get("banned") != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
