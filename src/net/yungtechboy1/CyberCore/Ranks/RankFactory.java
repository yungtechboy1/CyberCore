package net.yungtechboy1.CyberCore.Ranks;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.RankList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class RankFactory {
    public Map<String, Object> RankCache = new HashMap<>();
    CyberCoreMain Main;
    public ConfigSection GARC = new ConfigSection();
    public ConfigSection MRC = new ConfigSection();
    public ConfigSection SRC = new ConfigSection();

    public RankFactory(CyberCoreMain main) {
        Main = main;
    }

    public ArrayList<String> GetAllRanks(String p) {
        ArrayList<String> al = new ArrayList<>();
        if (GARC.containsKey(p.toLowerCase())) return (ArrayList<String>) GARC.get(p.toLowerCase());
        try {
            Statement stmt = Main.getMySqlConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `xf_user` WHERE `MCPE` = '" + p + "'");
            while (rs.next()) {
                String secusergroups = rs.getString("secondary_group_ids");
                String usergroups = rs.getString("user_group_id");
                if (usergroups != null || !usergroups.equalsIgnoreCase("")) {
                    String r = GetRankStringFromGroup(usergroups);
                    if (r != null) al.add(r);
                    MRC.put(p.toLowerCase(), r);
                }
                if (secusergroups != null || !secusergroups.equalsIgnoreCase("")) {
                    String r = null;
                    if (secusergroups.contains(",")) {
                        String[] secgroups = secusergroups.split(",");
                        for (String secgroup : secgroups) {
                            //http://209.126.102.26/phpmyadmin/index.php?db=admin_arch&target=db_search.php&token=d8db4073e2feb9b6386d5bd3d1612f46#PMAURL-4:sql.php?db=admin_arch&table=xf_user_group&server=1&target=&token=d8db4073e2feb9b6386d5bd3d1612f46
                            r = GetRankStringFromGroup(secgroup);
                            if (r != null) al.add(r);
                        }
                    } else {
                        r = GetRankStringFromGroup(secusergroups);
                        if (r != null) al.add(r);
                    }
                }
            }
        } catch (SQLException ex) {
        }

        if (al.size() == 0) {
            GARC.put(p.toLowerCase(), new ArrayList<String>());
            return null;
        } else {
            GARC.put(p.toLowerCase(), al);
            return al;
        }

    }

    public String GetMasterRank(String p) {
        if (MRC.containsKey(p.toLowerCase())) {
            return (String) MRC.get(p.toLowerCase());
        } else {
            try {
                Statement stmt = Main.getMySqlConnection().createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `xf_user` WHERE `MCPE` = '" + p + "'");
                while (rs.next()) {
                    String usergroups = rs.getString("user_group_id");
                    if (usergroups != null || !usergroups.equalsIgnoreCase("")) {
                        String r = GetRankStringFromGroup(usergroups);
                        if (r != null) {
                            MRC.put(p.toLowerCase(), r);
                            return r;
                        }
                    }
                    //return null;
                }
            } catch (SQLException ex) {
            }
        }
        MRC.put(p.toLowerCase(), null);
        return null;
    }

    /**
     * Gets the highest Seccondary Rank from user
     *
     * @param p Player Name
     * @return Integer|null Highest Rank
     */
    public String GetSecondaryRank(String p) {
        if (SRC.containsKey(p.toLowerCase())) {
            return (String) SRC.get(p.toLowerCase());
        }
        try {
            Statement stmt = Main.getMySqlConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `xf_user` WHERE `MCPE` = '" + p + "'");
            while (rs.next()) {
                String secusergroups = rs.getString("secondary_group_ids");
                if (secusergroups != null || !secusergroups.equalsIgnoreCase("")) {
                    String r = null;
                    String tr = null;
                    Integer mod = -1;
                    if (secusergroups.contains(",")) {
                        String[] secgroups = secusergroups.split(",");
                        for (String secgroup : secgroups) {
                            //http://209.126.102.26/phpmyadmin/index.php?db=admin_arch&target=db_search.php&token=d8db4073e2feb9b6386d5bd3d1612f46#PMAURL-4:sql.php?db=admin_arch&table=xf_user_group&server=1&target=&token=d8db4073e2feb9b6386d5bd3d1612f46
                            tr = GetRankStringFromGroup(secgroup);
                            Integer rsr = AllRanksToInt(tr,true);
                            if (rsr > mod) {
                                r = tr;
                                mod = rsr;
                            }
                        }
                        SRC.put(p.toLowerCase(), r);
                        return r;
                    } else {
                        r = GetRankStringFromGroup(secusergroups);
                        if (r != null) {
                            SRC.put(p.toLowerCase(), r);
                            return r;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
        }
        SRC.put(p.toLowerCase(), null);
        return null;
    }

    public String getPlayerRank(Player p) {
        return getPlayerRank(p.getName());
    }

    public String getPlayerRank(String p) {
        if (RankCache.containsKey(p.toLowerCase())) {
            return (String) RankCache.get(p.toLowerCase());
        } else {
            try {
                Statement stmt = Main.getMySqlConnection().createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `xf_user` WHERE `MCPE` = '" + p + "'");
                while (rs.next()) {
                    String secusergroups = rs.getString("secondary_group_ids");
                    String usergroups = rs.getString("user_group_id");
                    if (usergroups != null || !usergroups.equalsIgnoreCase("")) {
                        String r = GetRankStringFromGroup(usergroups);
                        if (r != null) {
                            RankCache.put(p.toLowerCase(), r);
                            return r;
                        }
                    }
                    if (secusergroups != null || !secusergroups.equalsIgnoreCase("")) {
                        String r = null;
                        String[] secgroups = secusergroups.split(",");
                        for (String secgroup : secgroups) {
                            //http://209.126.102.26/phpmyadmin/index.php?db=admin_arch&target=db_search.php&token=d8db4073e2feb9b6386d5bd3d1612f46#PMAURL-4:sql.php?db=admin_arch&table=xf_user_group&server=1&target=&token=d8db4073e2feb9b6386d5bd3d1612f46
                            r = GetRankStringFromGroup(secgroup);
                            if (r != null) {
                                RankCache.put(p.toLowerCase(), r);
                                return r;
                            }
                        }
                    }
                    //return null;
                }
            } catch (SQLException ex) {
            }
        }
        RankCache.put(p.toLowerCase(), null);
        return null;
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
     * Gets Highest Admin rank from all your possible ranks
     *
     * @param p
     * @return String | Null
     */
    public String GetAdminRank(String p) {
        Integer mod = 0;
        String r = null;
        ArrayList<String> gar = GetAllRanks(p);
        if (gar.size() == 0) return null;
        for (String rank : gar) {
            Integer nm = RankAdminRank(rank);
            if (mod < nm) {
                mod = nm;
                r = rank;
            }
        }
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
