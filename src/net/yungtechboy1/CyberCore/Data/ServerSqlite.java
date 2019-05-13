package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CoreSettings;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
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

    public void LoadPlayer(Player p) {
        LoadHomes((CorePlayer) p);
        LoadSettings((CorePlayer) p);
        Faction f = plugin.FM.FFactory.IsPlayerInFaction((CorePlayer) p);
        ((CorePlayer) p).Faction = f.GetName();
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
            List<HashMap<String, Object>> data = executeSelect("SELECT * FROM `Homes` WHERE `owneruuid` == '" + p.getUniqueId() + "'");
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

            p.settings = new CoreSettings(data.get(0));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SaveHomes(CorePlayer p) {
        try {
            executeUpdate("DELETE FROM `Homes` WHERE `owneruuid` == '" + p.getUniqueId() + "'");
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
            executeUpdate("DELETE FROM `Settings` WHERE `name` LIKE '" + p.getName().toLowerCase() + "'");
            executeUpdate("INSERT INTO `Settings` VALUES ('" + p.getName().toLowerCase() + "'," + p.settings.isHudOff() + "," + p.settings.isHudClassOff() + "," + p.settings.isHudPosOff() + "," + p.settings.isHudFactionOff() + ")");
            plugin.getLogger().info("Settings saved for " + p.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
