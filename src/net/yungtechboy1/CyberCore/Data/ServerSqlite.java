package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Warp.WarpData;
import org.apache.logging.log4j.core.Core;

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

    public void LoadPlayer(Player p){
        LoadHomes((CorePlayer)p);
    }
    public void LoadPlayer(CorePlayer p){
        LoadHomes(p);
    }
    public void UnLoadPlayer(Player p){
        SaveHomes((CorePlayer)p);
    }
    public void UnLoadPlayer(CorePlayer p){
        SaveHomes(p);
    }

    private void LoadHomes(CorePlayer p) {
        try {
            List<HashMap<String, Object>> data = executeSelect("SELECT * FROM `Homes` WHERE `owneruuid` == '"+p.getUniqueId()+"'");
            if (data == null) {
                CyberCoreMain.getInstance().getLogger().error("Error Loading Warps from Sqlite!");
                return;
            } else {
                plugin.getLogger().info("Loading " + data.size() + " Warps!");
            }

            for (HashMap<String, Object> v : data) {
                p.AddHome(new HomeData((String) v.get("name"), (Double) v.get("x"), (Double) v.get("y"), (Double) v.get("z"), (String) v.get("level"),p));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SaveHomes(CorePlayer p) {
        try {
            executeUpdate("DELETE FROM `Homes` WHERE `owneruuid` == '"+p.getUniqueId()+"'");
            for(HomeData h : p.HD){
                executeUpdate("INSERT INTO `Homes` VALUES (0,'"+h.getName()+"',"+h.getX()+","+h.getY()+","+h.getZ()+",'"+h.getLevel()+"','"+h.getOwner()+"','"+h.getOwneruuid()+"')");
            }
            plugin.getLogger().info("Homes saved for "+p.getName());
            p.sendTip("Homes Saved!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
