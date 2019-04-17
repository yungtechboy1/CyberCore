package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Rank.RankList;
import ru.nukkit.dblib.DbLib;
import sun.applet.Main;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoreSQL extends MySQL {


    public CoreSQL(CyberCoreMain plugin) {
        super(plugin);
    }


    /**
     * @param identity Can be a player gamertag or UUID.
     * @return boolean
     */
    public boolean checkUser(String identity) {
        String query = "SELECT * FROM mcpe WHERE gamertag=':gamertag'";
        try {
            ArrayList<HashMap<String, Object>> data = executeSelect(query, "gamertag", identity.toLowerCase(), null);
            if (data != null && !data.isEmpty()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "SELECT * FROM mcpe WHERE uuid=':uuid'";
        try {
            ArrayList<HashMap<String, Object>> data = executeSelect(query, "uuid", identity, null);
            if (data != null && !data.isEmpty()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void loadUserHouses(CorePlayer player) throws SQLException {
        String uuid = player.getUniqueId().toString();

        String[] selectors = {"owner", "username", "pos"};
        String query = "SELECT * FROM `Homes` WHERE uuid=':uuid'";
        try {
            ArrayList<HashMap<String, Object>> data = executeSelect(query, "uuid", uuid, selectors);
            ArrayList<HomeData> hd = new ArrayList<>();
            for(HashMap<String,Object> d : data) {
                String owner = (String)d.get("owner");
                String name = (String) d.get("name");
                String posstring = (String) d.get("pos");
                hd.add(new HomeData(owner,name,posstring));
            }
            player.LoadHomes(hd);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUser(CorePlayer player) throws SQLException {
        String uuid = player.getUniqueId().toString();
        String ip = player.getAddress();
        if (checkUser(uuid)) {
            String[] selectors = {"uuid", "last_ip", "rank"};
            String query = "SELECT * FROM mcpe WHERE uuid=':uuid'";
            try {
                ArrayList<HashMap<String, Object>> data = executeSelect(query, "uuid", uuid, selectors);
                if (!data.get(0).get("last_ip").equals(ip)) {
                    Config ip_change = new Config(new File(plugin.getDataFolder(), "ip_changes.yml"));
                    ip_change.set(uuid, ip_change.getList(uuid).add(ip));
                    ip_change.save();
                }
                int rank = (int) data.get(0).get("rank");
                int fc = (int) data.get(0).get("FixCoins");
                player.SetRank(RankList.GetRankFromInt(rank));
                player.fixcoins = fc;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createUser(uuid, ip);
            CorePlayer p = plugin.getCorePlayer(uuid);
            p.kills = 0;
            p.deaths = 0;
            p.fixcoins = 0;
            p.money = 300;
            p.faction_id = "no_faction";
            p.setBanned(false);
//            plugin.UserSQL.saveUser(p);
            return;
        }
//        plugin.UserSQL.loadUser(uuid);
//        plugin.UserSQL.saveUser(plugin.getCorePlayer(player));
    }

    public void createUser(String uuid, String ip) {
        try {
            executeUpdate("insert into mcpe (uuid, last_ip) values ('" + uuid + "' , '" + ip + "')");
            plugin.log("User: " + uuid + " - added to DB");
//            plugin.UserSQL.createUser(uuid);
            int rank = 0;
            plugin.RankFactory.RankCache.put(uuid, rank);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
