package net.yungtechboy1.CyberCore.Rank;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Data.FactionSQL;
import net.yungtechboy1.CyberCore.Rank.Ranks.Guest_Rank;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class RankFactory {

    public UMCSQL UD = new UMCSQL(CyberCoreMain.getInstance());

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

    public void loadDefault() {
        ranks.put(RankList.PERM_GUEST.getID(), new Guest_Rank());
    }

    public void loadRanks() {
        Main.getLogger().info("Loading Ranks...");
        loadDefault();
        Config rankConf = new Config(new File(Main.getDataFolder(), "ranks.yml"));
        Map<String, Object> map = rankConf.getSection("Ranks").getAllMap();
        for (String s : map.keySet()) {
            Main.getLogger().info("-===" + s + "===-");
            int index = rankConf.getInt("Ranks." + s + ".rank");
            String display = rankConf.getString("Ranks." + s + ".display_name");
            String chat_prefix = rankConf.getString("Ranks." + s + ".chat_prefix");
            if (!ranks.containsKey(index)) {
                Rank data = new Rank(index, display, chat_prefix);
                ranks.put(index, data);
                Main.getLogger().info("Rank: " + s + " [" + index + "]- loaded...");
            } else {
                Rank rank = ranks.get(index);
                rank.display_name = display;
                rank.chat_prefix = chat_prefix;

                Main.getLogger().info("Rank: " + s + " [" + index + "]- Updated!...");
            }
        }
    }

    public Rank getPlayerRank(Player p) {
        String pn = p.getName();
        if(pn == null)return  new Guest_Rank();
        if (!RankCache.containsKey(pn)) {
            int uid = getUserIDFromMCPEName(pn);
            int rid = getRankIDFromUserID(uid);
            CyberCoreMain.getInstance().getLogger().warning("Loading rank for "+pn+" wtih Rank ID "+rid+" & User ID: "+uid);
            if(rid != -1){
                return ranks.getOrDefault(rid,new Guest_Rank());
            }
            return new Guest_Rank();
        }
        return ranks.getOrDefault(RankCache.get(pn), new Guest_Rank());
    }

    public int getUserIDFromMCPEName(String name){
        try{
        Connection c = UD.connectToDb();
        ResultSet r = c.createStatement().executeQuery("SELECT * FROM `xf_user_field_value` WHERE `field_value` LIKE '"+name+"' AND `field_id` = CAST(0x6d6370656964 AS BINARY)");
        if(r.next()){
            return r.getInt("user_id");
        }else return 0;
    }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int getRankIDFromUserID(int id){
        if(id == 0)return -1;
        try{
            Connection c = UD.connectToDb();
            ResultSet r = c.createStatement().executeQuery("SELECT * FROM `xf_user` WHERE `user_id` = "+id);
            if(r.next()){
                int ugi = r.getInt("user_group_id");
//                String sgi = r.getString("secondary_group_ids");
                return ugi;
            }else return -1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }


//    public String GetRankStringFromGroup(String group) {
//        String r = null;
//        if (group.equalsIgnoreCase("11")) r = "steve+";
//        if (group.equalsIgnoreCase("9")) r = "hero";
//        if (group.equalsIgnoreCase("12")) r = "vip";
//        if (group.equalsIgnoreCase("12")) r = "vip+";
//        if (group.equalsIgnoreCase("10")) r = "legend";
//        if (group.equalsIgnoreCase("17")) r = "mod1";
//        if (group.equalsIgnoreCase("18")) r = "mod2";
//        if (group.equalsIgnoreCase("19")) r = "mod3";
//        //if(group.equalsIgnoreCase("18"))r = "mod4";
//        //if(group.equalsIgnoreCase("19"))r = "mod5";
//        if (group.equalsIgnoreCase("14")) r = "admin1";
//        if (group.equalsIgnoreCase("15")) r = "admin2";
//        if (group.equalsIgnoreCase("16")) r = "admin3";
//        if (group.equalsIgnoreCase("6")) r = "tmod";
//        if (group.equalsIgnoreCase("7")) r = "tmod";
//        //if(group.equalsIgnoreCase("11"))r = "tmod";
//        //if(group.equalsIgnoreCase("13"))r = "scrub";
//        if (group.equalsIgnoreCase("3")) r = "op";
//        if (group.equalsIgnoreCase("8")) r = "op";
//        if (group.equalsIgnoreCase("24")) r = "op";
//        if (group.equalsIgnoreCase("25")) r = "yt";
//        if (group.equalsIgnoreCase("28")) r = "adventurer";
//        if (group.equalsIgnoreCase("29")) r = "conquerer";
//        if (group.equalsIgnoreCase("27")) r = "tourist";
//        if (group.equalsIgnoreCase("26")) r = "islander";
//        //@TODO Add Member and Member+
//
//        return r;
//    }


    /**
     * Returns rank from 0 - 8
     * 0 -> No Admin Rank
     * 8 -> OP
     *
     * @param rank Rank Name String
     * @return Integer
     */
//    public Integer RankAdminRank(String rank) {
//        if (rank == null) {
//            return 0;
//        } else if (rank.equalsIgnoreCase("TMOD")) {
//            return 1;
//        } else if (rank.equalsIgnoreCase("MOD1") || rank.equalsIgnoreCase("yt")) {
//            return 2;
//        } else if (rank.equalsIgnoreCase("MOD2")) {
//            return 3;
//        } else if (rank.equalsIgnoreCase("MOD3")) {
//            return 4;
//        } else if (rank.equalsIgnoreCase("ADMIN1")) {
//            return 5;
//        } else if (rank.equalsIgnoreCase("ADMIN2")) {
//            return 6;
//        } else if (rank.equalsIgnoreCase("ADMIN3")) {
//            return 7;
//        } else if (rank.equalsIgnoreCase("OP")) {
//            return 8;
//        }
//        return 0;
//    }

//    public Integer AllRanksToInt(String rank) {
//        return AllRanksToInt(rank, false);
//    }
//
//    public Integer AllRanksToInt(String rank, Boolean all) {
//        if (rank == null) {
//            if (all) return RankList.PERM_GUEST;
//        } else if (rank.equalsIgnoreCase("member")) {
//            if (all) return RankList.PERM_MEMBER;
//        } else if (rank.equalsIgnoreCase("member+")) {
//            if (all) return RankList.PERM_MEMBER_PLUS;
//        } else if (rank.equalsIgnoreCase("tourist")) {
//            if (all) return RankList.PERM_TOURIST;
//        } else if (rank.equalsIgnoreCase("islander")) {
//            if (all) return RankList.PERM_ISLANDER;
//        } else if (rank.equalsIgnoreCase("adventurer")) {
//            if (all) return RankList.PERM_ADVENTURER;
//        } else if (rank.equalsIgnoreCase("conquerer")) {
//            if (all) return RankList.PERM_CONQUERER;
//        } else if (rank.equalsIgnoreCase("TMOD")) {
//            return RankList.PERM_TMOD;
//        } else if (rank.equalsIgnoreCase("MOD1") || rank.equalsIgnoreCase("yt")) {
//            return RankList.PERM_MOD_1;
//        } else if (rank.equalsIgnoreCase("MOD2")) {
//            return RankList.PERM_MOD_2;
//        } else if (rank.equalsIgnoreCase("MOD3")) {
//            return RankList.PERM_MOD_3;
//        } else if (rank.equalsIgnoreCase("ADMIN1")) {
//            return RankList.PERM_ADMIN_1;
//        } else if (rank.equalsIgnoreCase("ADMIN2")) {
//            return RankList.PERM_ADMIN_2;
//        } else if (rank.equalsIgnoreCase("ADMIN3")) {
//            return RankList.PERM_ADMIN_3;
//        } else if (rank.equalsIgnoreCase("OP")) {
//            return RankList.PERM_OP;
//        }
//        return 0;
//    }
}
