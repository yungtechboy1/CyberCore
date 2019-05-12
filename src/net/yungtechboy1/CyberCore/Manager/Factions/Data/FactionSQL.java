package net.yungtechboy1.CyberCore.Manager.Factions.Data;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Data.SQLite;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactionSQL extends SQLite {


    public FactionSQL(CyberCoreMain plugin, String settings) {

        super(plugin, settings);

    }

    public boolean checkUser(String identity) {
        try {
            List<HashMap<String, Object>> data = executeSelect(loadQuery,"gamertag", identity.toLowerCase(), null);
            if(data != null) return true;
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
            List<HashMap<String,Object>> data = executeSelect(loadQuery,"uuid", uuid, (String[])columns.keySet().toArray());
            CorePlayer p = plugin.getCorePlayer(uuid);
            p.kills = (Integer) data.get(0).get("kills");
            p.deaths = (Integer) data.get(0).get("deaths");
            p.setBanned((Integer) data.get(0).get("banned") != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
