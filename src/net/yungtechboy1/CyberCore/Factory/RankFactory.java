package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.RankList;
import net.yungtechboy1.CyberCore.Ranks.Rank;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class RankFactory {

    public final String DB_TABLE = "mcpe";

    public Map<String, Integer> RankCache = new HashMap<>();
    CyberCoreMain Main;
    public ConfigSection GARC = new ConfigSection();
    public ConfigSection MRC = new ConfigSection();
    public ConfigSection SRC = new ConfigSection();

    public Map<Integer, Rank> ranks = new HashMap<>();

    public RankFactory(CyberCoreMain main) {
        Main = main;
        loadRanks();
    }

    public void loadRanks() {
        Main.getLogger().info("Loading Ranks...");
        Config rankConf = new Config(new File(Main.getDataFolder(), "ranks.yml"));
        Main.getLogger().info(rankConf.get("Ranks").toString());
        Map<String, Object> map = rankConf.getSection("Ranks").getAllMap();
        for(String s: map.keySet()) {
            Main.getLogger().info("-===" + s + "===-");
            int index = rankConf.getInt("Ranks." +s+ ".rank");
            String display = rankConf.getString("Ranks." +s+ ".display_name");
            Rank data = new Rank(index, display);
            ranks.put(index, data);
            Main.getLogger().info("Rank: " + s + " ["+index+"]- loaded...");
        }
    }

    public Rank getPlayerRank(String p) {
        String uuid;
        if((uuid = Main.getPlayer(p).getUniqueId().toString()) != null ) {
            return ranks.get(RankCache.get(uuid));
        } else {
            try {
                return(ranks.get(Main.SQLApi.getInteger(DB_TABLE, "gamertag", p, "rank")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Rank getPlayerRank(Player p) {
        return getPlayerRank(p.getName());
    }

    public String GetRankStringFromGroup(String group) {
        String r = null;
        if (group.equalsIgnoreCase("11")) r = "steve+";
        if (group.equalsIgnoreCase("9")) r = "hero";
        if (group.equalsIgnoreCase("12")) r = "vip";
        if (group.equalsIgnoreCase("12")) r = "vip+";
        if (group.equalsIgnoreCase("10")) r = "legend";
        if (group.equalsIgnoreCase("17")) r = "mod1";
        if (group.equalsIgnoreCase("18")) r = "mod2";
        if (group.equalsIgnoreCase("19")) r = "mod3";
        //if(group.equalsIgnoreCase("18"))r = "mod4";
        //if(group.equalsIgnoreCase("19"))r = "mod5";
        if (group.equalsIgnoreCase("14")) r = "admin1";
        if (group.equalsIgnoreCase("15")) r = "admin2";
        if (group.equalsIgnoreCase("16")) r = "admin3";
        if (group.equalsIgnoreCase("6")) r = "tmod";
        if (group.equalsIgnoreCase("7")) r = "tmod";
        //if(group.equalsIgnoreCase("11"))r = "tmod";
        //if(group.equalsIgnoreCase("13"))r = "scrub";
        if (group.equalsIgnoreCase("3")) r = "op";
        if (group.equalsIgnoreCase("8")) r = "op";
        if (group.equalsIgnoreCase("24")) r = "op";
        if (group.equalsIgnoreCase("25")) r = "yt";
        if (group.equalsIgnoreCase("28")) r = "adventurer";
        if (group.equalsIgnoreCase("29")) r = "conquerer";
        if (group.equalsIgnoreCase("27")) r = "tourist";
        if (group.equalsIgnoreCase("26")) r = "islander";
        //@TODO Add Member and Member+

        return r;
    }


    /**
     * Returns rank from 0 - 8
     * 0 -> No Admin Rank
     * 8 -> OP
     *
     * @param rank Rank Name String
     * @return Integer
     */
    public Integer RankAdminRank(String rank) {
        if (rank == null) {
            return 0;
        } else if (rank.equalsIgnoreCase("TMOD")) {
            return 1;
        } else if (rank.equalsIgnoreCase("MOD1") || rank.equalsIgnoreCase("yt")) {
            return 2;
        } else if (rank.equalsIgnoreCase("MOD2")) {
            return 3;
        } else if (rank.equalsIgnoreCase("MOD3")) {
            return 4;
        } else if (rank.equalsIgnoreCase("ADMIN1")) {
            return 5;
        } else if (rank.equalsIgnoreCase("ADMIN2")) {
            return 6;
        } else if (rank.equalsIgnoreCase("ADMIN3")) {
            return 7;
        } else if (rank.equalsIgnoreCase("OP")) {
            return 8;
        }
        return 0;
    }

    public Integer AllRanksToInt(String rank) {
        return AllRanksToInt(rank, false);
    }

    public Integer AllRanksToInt(String rank, Boolean all) {
        if (rank == null) {
            if (all) return RankList.PERM_GUEST;
        } else if (rank.equalsIgnoreCase("member")) {
            if (all) return RankList.PERM_MEMBER;
        } else if (rank.equalsIgnoreCase("member+")) {
            if (all) return RankList.PERM_MEMBER_PLUS;
        } else if (rank.equalsIgnoreCase("tourist")) {
            if (all) return RankList.PERM_TOURIST;
        } else if (rank.equalsIgnoreCase("islander")) {
            if (all) return RankList.PERM_ISLANDER;
        } else if (rank.equalsIgnoreCase("adventurer")) {
            if (all) return RankList.PERM_ADVENTURER;
        } else if (rank.equalsIgnoreCase("conquerer")) {
            if (all) return RankList.PERM_CONQUERER;
        } else if (rank.equalsIgnoreCase("TMOD")) {
            return RankList.PERM_TMOD;
        } else if (rank.equalsIgnoreCase("MOD1") || rank.equalsIgnoreCase("yt")) {
            return RankList.PERM_MOD_1;
        } else if (rank.equalsIgnoreCase("MOD2")) {
            return RankList.PERM_MOD_2;
        } else if (rank.equalsIgnoreCase("MOD3")) {
            return RankList.PERM_MOD_3;
        } else if (rank.equalsIgnoreCase("ADMIN1")) {
            return RankList.PERM_ADMIN_1;
        } else if (rank.equalsIgnoreCase("ADMIN2")) {
            return RankList.PERM_ADMIN_2;
        } else if (rank.equalsIgnoreCase("ADMIN3")) {
            return RankList.PERM_ADMIN_3;
        } else if (rank.equalsIgnoreCase("OP")) {
            return RankList.PERM_OP;
        }
        return 0;
    }
}
