package net.yungtechboy1.CyberCore.Manager.Factions;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.*;

/**
 * Created by carlt_000 on 5/13/2016.
 */


public class FactionFactory {

    /**
     * Give Faction Name and Return the Faction Class!
     */
    public Map<String, Faction> List = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Map<String, String> FacList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Map<String, Faction> PlotsList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Map<String, String> allyrequest = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public HashMap<String, String> InvList = new HashMap<>();
    public Map<String, String> War = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);//Attacking V Defending
    public Map<String, Integer> Top = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Map<String, Integer> Rich = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public FactionsMain Main;
    private ArrayList<String> bannednames = new ArrayList<String>() {{
        add("wilderness");
        add("safezone");
        add("peace");
    }};

    public FactionFactory(FactionsMain main) {
        Main = main;
    }

    private FactionsMain getMain() {
        return Main;
    }

    private Server getServer() {
        return Main.getServer();
    }

    public Faction GetPlotStatus(Integer x, Integer z) {
        if (PlotsList.containsKey(x + "|" + z)) {
            return PlotsList.get(x + "|" + z);
        }
        return null;
    }

    private Connection getMySqlConnection() {
        return Main.factionData.connectToDb();
    }

    public void RemoveFaction(Faction fac) {
        Connection c = getMySqlConnection();
        try {
            String name = fac.GetName();
            Statement stmt = c.createStatement();
            for (String m : fac.GetRecruits()) {
                if (FacList.containsKey(m)) FacList.remove(m);
                if (Main.getServer().getPlayerExact(m) != null) {
//                    Main.Setnametag(m);
//                    Main.sendBossBar(m);
                }
            }
            for (String m : fac.GetMembers()) {
                if (FacList.containsKey(m)) FacList.remove(m);
                if (Main.getServer().getPlayerExact(m) != null) {
//                    Main.CC.Setnametag(m);
//                    Main.sendBossBar(m);
                }
            }
            for (String m : fac.GetOfficers()) {
                if (Main.getServer().getPlayerExact(m) != null) {
//                    Main.CC.Setnametag(m);
//                    Main.sendBossBar(m);
                }
                if (FacList.containsKey(m)) FacList.remove(m);
            }
            for (String m : fac.GetGenerals()) {
                if (Main.getServer().getPlayerExact(m) != null) {
//                    Main.CC.Setnametag(m);
//                    Main.sendBossBar(m);
                }
                if (FacList.containsKey(m)) FacList.remove(m);
            }
            if (FacList.containsKey(fac.GetLeader().toLowerCase())) {
                if (Main.getServer().getPlayerExact(fac.GetLeader()) != null) {
//                    Main.CC.Setnametag(fac.GetLeader());
//                    Main.sendBossBar(fac.GetLeader());
                }
                FacList.remove(fac.GetLeader().toLowerCase());
            }
            List.remove(fac.GetName());
            stmt.executeUpdate(String.format("DELETE FROM `allies` WHERE `factiona` LIKE '%s' OR `factionb` LIKE '%s';", name, name));
            stmt.executeUpdate(String.format("DELETE FROM `plots` WHERE `faction` LIKE '%s';", name));
            stmt.executeUpdate(String.format("DELETE FROM `confirm` WHERE `faction` LIKE '%s';", name));
            stmt.executeUpdate(String.format("DELETE FROM `home` WHERE `faction` LIKE '%s';", name));
            stmt.executeUpdate(String.format("DELETE FROM `settings` WHERE `faction` LIKE '%s';", name));
            stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `faction` LIKE '%s';", name));
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            getServer().getLogger().info(ex.getClass().getName() + ":9 " + ex.getMessage() + " > " + ex.getStackTrace()[0].getLineNumber() + " ? " + ex.getCause());
        }
    }

    public void SaveAllFactions() {
        try {
            Statement stmt = getMySqlConnection().createStatement();
            /*Statement stmt2 = getMySqliteConnection2().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS \"master\" " +
                    "(player VARCHAR PRIMARY KEY UNIQUE     NOT NULL," +
                    " faction           VARCHAR    NOT NULL, " +
                    " rank            VARCHAR     NOT NULL)";
            String sql1 = "CREATE TABLE IF NOT EXISTS \"allies\" (  " +
                    "                        `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " +
                    "                        `factiona` VARCHAR NOT NULL,  " +
                    "                        `factionb` varchar NOT NULL,  " +
                    "                        `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  " +
                    "                        );";
            String sql2 = "CREATE TABLE IF NOT EXISTS \"confirm\" (" +
                    "`player`TEXT NOT NULL," +
                    "`faction`TEXT NOT NULL," +
                    "`timestamp`INTEGER," +
                    "`id`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                    ")";
            String sql3 = "CREATE TABLE  IF NOT EXISTS \"home\" (" +
                    "                     `faction` varchar NOT NULL UNIQUE, " +
                    "                     `x` int(250) NOT NULL, " +
                    "                     `y` int(250) NOT NULL, " +
                    "                     `z` int(250) NOT NULL, " +
                    "                     PRIMARY KEY(faction) " +
                    "                    );";
            String sql4 = "CREATE TABLE IF NOT EXISTS \"plots\" (  " +
                    "            `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " +
                    "            `faction` varchar(250) NOT NULL,  " +
                    "            `x` int(250) NOT NULL,  " +
                    "            `z` int(250) NOT NULL  " +
                    "            );";
            String sql6 = "CREATE TABLE IF NOT EXISTS \"Settings\" (" +
                    "`faction`varchar(250) NOT NULL UNIQUE," +
                    "`max`int(250) NOT NULL," +
                    "`powerbonus`int(50) NOT NULL," +
                    "`MOTD`varchar(1000) NOT NULL," +
                    "`displayname`varchar(1000) NOT NULL," +
                    "`desc`varchar(1000) NOT NULL," +
                    "`prem`INTEGER NOT NULL," +
                    "`privacy`int(11) NOT NULL," +
                    "`power`INTEGER NOT NULL," +
                    "`money`INTEGER NOT NULL," +
                    "PRIMARY KEY(faction)" +
                    ")";
            //String sql7 = "CREATE TABLE IF NOT EXISTS war ( attacker varchar(250) NOT NULL PRIMARY KEY, defender varchar(250) NOT NULL, start int(250) NOT NULL)";
            stmt2.executeUpdate(sql);
            stmt2.executeUpdate(sql1);
            stmt2.executeUpdate(sql2);
            stmt2.executeUpdate(sql3);
            stmt2.executeUpdate(sql4);
            stmt2.executeUpdate(sql6);*/
            //stmt.executeUpdate("DELETE FROM allies; DELETE FROM confirm; DELETE FROM home; DELETE FROM plots; DELETE FROM Settings; DELETE FROM master;");
            Main.plugin.getLogger().info("Going to save: " + List.size());
            stmt.executeUpdate("BEGIN;");
            String yaml = "";
            for (Map.Entry<String, Faction> e : List.entrySet()) {
                try {
                    Faction fac = e.getValue();
                    String name = e.getKey();
                    ArrayList<String> allies = fac.GetAllies();
                    ArrayList<String> enemies = fac.GetEnemies();
                    ArrayList<String> plots = fac.GetPlots();
                    Map<String, Integer> invites = fac.GetInvite();
                    Vector3 home = fac.GetHome();
                    String motd = fac.GetMOTD();
                    String displayName = fac.GetDisplayName();
                    String desc = fac.GetDesc();
                    Integer perms = fac.GetPerm();
                    Integer powerbonus = fac.GetPowerBonus();
                    Integer privacy = fac.GetPrivacy();
                    Integer maxplayers = fac.GetMaxPlayers();
                    Integer power = fac.GetPower();
                    Integer money = fac.GetMoney();

                    //@TODO
                    Integer point = fac.GetPoints();
                    Integer xp = fac.GetXP();
                    Integer lvl = fac.GetLevel();
                    Integer rich = fac.GetRich();
                    String am = "";
//                    if (fac.GetActiveMission() != null) {
//                        am = fac.GetActiveMission().id + "";
//                        try {
//                            ActiveMission a = fac.GetActiveMission();
//                            a.faction = null;
//                            a.Main = null;
//                            Main.AM.set(fac.GetName(), a.ToHashMap());
//                         /*Yaml y = new Yaml();
//                        Utils.writeFile(new File(Main.getDataFolder().toString() + "/missions/" + fac.GetName() + ".yml"), y.dump(a));
//                        ;
//                        YamlWriter reader = new YamlWriter(new FileWriter(Main.getDataFolder().toString()+"/missions/"+fac.GetName()+".yml"));
//                        reader.write(a);
//                        reader.close();*/
//                        } catch (Exception ex) {
//                            getServer().getLogger().info(ex.getClass().getName() + ":9 " + ex.getMessage() + " > " + ex.getStackTrace()[0].getLineNumber() + " ? " + ex.getCause());
//                            am = "";
//                        } /*catch (IOException var8) {
//                        Server.getInstance().getLogger().logException(var8);
//                    }*/
//                    }
                    ArrayList<Integer> CMID1 = fac.GetCompletedMissions();
                    String CMID = "";
                    if (CMID1.size() > 1) {
                        Boolean f = true;
                        for (Integer aa : CMID1) {
                            if (!f) {
                                CMID = CMID + ",";
                            } else {
                                f = false;
                            }
                            CMID = CMID + aa;
                        }
                    } else if (CMID1.size() != 0) {
                        for (Integer aa : CMID1) {
                            CMID = CMID + aa;
                        }
                    }

                    stmt.executeUpdate(String.format("DELETE FROM `allies` WHERE `factiona` LIKE '%s' OR `factionb` LIKE '%s';", name, name));
                    for (String ally : allies) {
                        stmt.executeUpdate(String.format("INSERT INTO `allies` VALUES (null,'%s','%s','');", name, ally));
                    }

                    stmt.executeUpdate(String.format("DELETE FROM `enemies` WHERE `factiona` LIKE '%s' OR `factionb` LIKE '%s';", name, name));
                    for (String enemy : enemies) {
                        stmt.executeUpdate(String.format("INSERT INTO `enemies` VALUES (null,'%s','%s','');", name, enemy));
                    }

                    stmt.executeUpdate("DELETE FROM `plots` WHERE `faction` LIKE '" + name + "';");
                    for (String plot : plots) {
                        String[] p = plot.split("\\|");
                        stmt.executeUpdate(String.format("INSERT INTO `plots` VALUES (null,'%s','%s','%s');", name, p[0], p[1]));
                    }
                    stmt.executeUpdate("DELETE FROM `confirm` WHERE `faction` LIKE '" + name + "';");
                    for (Map.Entry<String, Integer> ee : invites.entrySet()) {
                        stmt.executeUpdate(String.format("INSERT INTO `confirm` VALUES ('%s','%s','%s',null);", ee.getKey(), name, ee.getValue()));
                    }

                    stmt.executeUpdate(String.format("DELETE FROM `home` WHERE `faction` LIKE '%s';", name));

                    if (home != null) {
                        stmt.executeUpdate(String.format("INSERT INTO `home` VALUES ('" + name + "','%s','%s','%s') ;", home.getX(), home.getY(), home.getZ()));
                    }
                    //stmt2.executeUpdate(String.format("DELETE FROM `home` WHERE `faction` LIKE '%s';",name));
                    //stmt2.executeUpdate(String.format("INSERT INTO `home` VALUES ('"+name+"',%s,%s,%s) ;",home.getX(),home.getY(),home.getZ()));
                    System.out.println(String.format("INSERT INTO `settings` VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');"
                            , name, maxplayers, powerbonus, motd, displayName, desc, perms, privacy, power, money, point, xp, lvl, CMID, am, rich));
                    CyberCoreMain.getInstance().getLogger().error(String.format("INSERT INTO `settings` VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');"
                            , name, maxplayers, powerbonus, motd, displayName, desc, perms, privacy, power, money, point, xp, lvl, CMID, am, rich));
                    stmt.executeUpdate(String.format("DELETE FROM `settings` WHERE `faction` LIKE '%s';", fac.GetName()));
                    stmt.executeUpdate(String.format("INSERT INTO `settings` VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');"
                            , name, maxplayers, powerbonus, motd, displayName, desc, perms, privacy, power, money, point, xp, lvl, CMID, am, rich));
                    ;
                    //stmt2.executeUpdate(String.format("DELETE FROM `settings` WHERE `faction` LIKE '%s';",name));
                    //stmt2.executeUpdate(String.format("INSERT INTO `settings` VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s'); ",name,maxplayers,powerbonus,motd,displayName,desc,perms,privacy,power,money));
                    //Saving Members, Leader, And Officers
                    stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `faction` = '%s';", name));
                    for (String member : fac.GetRecruits()) {
                        stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `player` = '%s';", member));
                        stmt.executeUpdate(String.format("INSERT IGNORE INTO `master`(`player`,`faction`,`rank`) VALUES ('%s','%s','%s');", member, name, "Recruits"));
                    }
                    //for(String member: fac.GetRecruits())stmt2.executeUpdate(String.format("INSERT OR IGNORE INTO `master`(`player`,`faction`,`rank`) VALUES (%s,%s,%s);",member,name,"Recruits"));
                    for (String member : fac.GetMembers()) {
                        stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `player` = '%s';", member));
                        stmt.executeUpdate(String.format("INSERT IGNORE INTO `master`(`player`,`faction`,`rank`) VALUES ('%s','%s','%s');", member, name, "Member"));
                        //for(String member: fac.GetMembers())stmt2.executeUpdate(String.format("INSERT OR IGNORE INTO `master`(`player`,`faction`,`rank`) VALUES ('%s','%s','%s');",member,name,"Member"));
                    }
                    for (String member : fac.GetOfficers()) {
                        stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `player` = '%s';", member));
                        stmt.executeUpdate(String.format("INSERT IGNORE INTO `master`(`player`,`faction`,`rank`) VALUES ('%s','%s','%s');", member, name, "Officer"));
                        //for(String member: fac.GetOfficers())stmt2.executeUpdate(String.format("INSERT OR IGNORE INTO `master`(`player`,`faction`,`rank`) VALUES ('%s','%s','%s');",member,name,"Officer"));
                    }
                    for (String member : fac.GetGenerals()) {
                        stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `player` = '%s';", member));
                        stmt.executeUpdate(String.format("INSERT INTO `master`(`player`,`faction`,`rank`) VALUES ('%s','%s','%s');", member, name, "General"));
                    }
                    //for(String member: fac.GetGenerals())stmt2.executeUpdate(String.format("INSERT OR IGNORE INTO `master`(`player`,`faction`,`rank`) VALUES ('%s','%s','%s');",member,name,"General"));
                    System.out.println(fac.GetName() + " > " + fac.GetLeader());
                    stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `player` = '%s';", fac.GetLeader()));
                    stmt.executeUpdate(String.format("INSERT INTO `master`(`player`,`faction`,`rank`) VALUES ('%s','%s','%s');", fac.GetLeader(), name, "Leader"));
                    //stmt2.executeUpdate(String.format("INSERT INTO `master`(`player`,`faction`,`rank`) VALUES (''%s'',''%s'',''%s'');",fac.GetLeader(),name,"Leader"));
                    Main.plugin.getLogger().info(TextFormat.GREEN + "[Factions] Saving Faction " + name);
                } catch (Exception ex) {
                    Main.plugin.getLogger().error(TextFormat.RED + "[Factions] Error! Faction " + e.getKey(), ex);
                    ex.printStackTrace();
                    getServer().getLogger().info(ex.getClass().getName() + ":77 " + ex.getMessage());
                }
            }
            stmt.executeUpdate("COMMIT;");
            stmt.close();
            //stmt2.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            getServer().getLogger().error(":7 ", ex);
        }
    }

/*
    public Boolean PlayerExistsInDB(String player, String fac) {
        try {
            ResultSet r = this.ExecuteQuerySQL(String.format("select count(*) from master where `faction` LIKE '%s' , `player` LIKE '%s'",fac,player));
            if(r == null)return false;
            if(r.next())if(r.getInt(1) > 0)return true;
            r.close();
            return false;
        } catch (Exception e) {
            return false;
        }
    }*/

    public ResultSet ExecuteQuerySQL(String s) {
        try {

            Statement stmt = this.getMySqlConnection().createStatement();
            ResultSet r = stmt.executeQuery(s);
            //this.getServer().getLogger().info( s );
//            stmt.close();
            return r;
        } catch (Exception ex) {

            getServer().getLogger().info(ex.getClass().getName() + ":8 " + ex.getMessage(), ex);
            return null;
        }
    }

    public Boolean factionExistsInDB(String name) {
        try {
            ResultSet r = this.ExecuteQuerySQL(String.format("select count(*) from `settings` where `faction` LIKE '%s'", name));
            if (r == null) return false;
            if (r.next()) if (r.getInt(1) > 0) return true;
            r.close();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Object GetFromSettings(String key, String faction) {
        try {
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `settings` where faction = '%s'", faction));
            if (r == null) return null;
            if (r.next()) return r.getObject(key);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String GetDisplayName(String faction) {
        try {
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `settings` where faction = '%s'", faction));
            if (r == null) return null;
            if (r.next()) {
                return r.getString("displayname");
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String GetLeader(String faction) {
        try {
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `master` where `faction` = '%s' and rank LIKE 'leader'", faction));
            if (r == null) return null;
            if (r.next()) {
                return r.getString("player");
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetRecruits(String faction) {
        try {
            ArrayList<String> result = new ArrayList<>();
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `master` where `faction` LIKE '%s' AND `rank` LIKE '%s'", faction, "recruit"));
            if (r == null) return null;
            while (r.next()) {
                result.add(r.getString("player").toLowerCase());
                FacList.put(r.getString("player").toLowerCase(), faction);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetMemebrs(String faction) {
        try {
            ArrayList<String> result = new ArrayList<>();
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `master` where `faction` LIKE '%s' AND `rank` LIKE '%s'", faction, "Member"));
            if (r == null) return null;
            while (r.next()) {
                result.add(r.getString("player").toLowerCase());
                FacList.put(r.getString("player").toLowerCase(), faction);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetOfficers(String faction) {
        try {
            ArrayList<String> result = new ArrayList<>();
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `master` where `faction` LIKE '%s' AND `rank` LIKE '%s'", faction, "Officer"));
            if (r == null) return null;
            while (r.next()) {
                result.add(r.getString("player").toLowerCase());
                FacList.put(r.getString("player").toLowerCase(), faction);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetGenerals(String faction) {
        try {
            ArrayList<String> result = new ArrayList<>();
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `master` where `faction` LIKE '%s' AND `rank` LIKE '%s'", faction, "General"));
            if (r == null) return null;
            while (r.next()) {
                result.add(r.getString("player").toLowerCase());
                FacList.put(r.getString("player").toLowerCase(), faction);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetPlots(String faction) {
        return GetPlots(faction,false);
    }
    public ArrayList<String> GetPlots(String faction, boolean safe) {
        try {
            Faction f =  null;
            if(!safe)f = getFaction(faction);
//            if(f == null)return null;
            ArrayList<String> results = new ArrayList<>();
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `plots` where `faction` LIKE '%s'", faction));
            if (r == null) return null;
            while (r.next()) {
                results.add(r.getInt("x") + "|" + r.getInt("z"));
//                f.AddPlots(r.getInt("x") + "|" + r.getInt("z"));
                if(f != null)PlotsList.put(r.getInt("x") + "|" + r.getInt("z"), f);
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param faction
     * @return
     * @deprecated
     */
    public ConfigSection GetWars(String faction) {
        try {
            ConfigSection results = new ConfigSection();

            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `war` where `attackingfaction` LIKE '%s'", faction));
            if (r == null) return null;
            while (r.next()) {
                War.put(r.getString("attackingfaction"), r.getString("defendingfaction"));
                String df = r.getString("defendingfaction");
                results.set("attackingfaction", r.getString("attackingfaction"));
                results.set("defendingfaction", r.getString("defendingfaction"));
                results.set("start", r.getInt("start"));
                results.set("stop", r.getInt("stop"));
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetAllFactions() {
        Main.plugin.getLogger().info("GETTINGALL FACS");
        ArrayList<String> results = new ArrayList<>();
        try {
            ResultSet r = this.ExecuteQuerySQL("select * from `settings`");
            if (r == null) return null;
            while (r.next()) {
                String ff = r.getString("faction");
                Main.plugin.getLogger().info("FOUNDDDDDDD FACCCCCCCCCCCCCCC" + ff);
                if (!results.contains(ff)) results.add(ff);
            }
            r.close();
//            r.getStatement().close();
            return results;
        } catch (Exception e) {
            Main.plugin.getLogger().info("EEE", e);
//            throw new RuntimeException(e);
            return results;
        }
    }

    public ArrayList<String> GetAllies(String faction) {
        try {
            ArrayList<String> results = new ArrayList<>();
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `allies` where `factiona` LIKE '%s' OR `factionb` LIKE '%s'", faction, faction));
            if (r == null) return null;
            while (r.next()) {
                if (r.getString("factiona").equalsIgnoreCase(faction)) results.add(r.getString("factionb"));
                if (r.getString("factionb").equalsIgnoreCase(faction)) results.add(r.getString("factiona"));
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetEnemies(String faction) {
        try {
            ArrayList<String> results = new ArrayList<>();
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `enemies` where `factiona` LIKE '%s' OR `factionb` LIKE '%s'", faction, faction));
            if (r == null) return null;
            while (r.next()) {
                if (r.getString("factiona").equalsIgnoreCase(faction)) results.add(r.getString("factionb"));
                if (r.getString("factionb").equalsIgnoreCase(faction)) results.add(r.getString("factiona"));
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Vector3 GetHome(String faction) {
        try {
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `home` where `faction` LIKE '%s'", faction));
            if (r == null) return null;
            if (r.next()) return new Vector3(r.getInt("x"), r.getInt("y"), r.getInt("z"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Map<String, Integer> GetInvites(String faction) {
        try {
            Map<String, Integer> result = new HashMap<>();
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `confirm` where `faction` LIKE '%s'", faction));
            if (r == null) return null;
            while (r.next()) {
                result.put(r.getString("player").toLowerCase(), r.getInt("timestamp"));
                InvList.put(r.getString("player").toLowerCase(), faction);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> GetCompletedMissions(String faction) {
        ArrayList<Integer> a = new ArrayList<>();
        String cmid = (String) GetFromSettings("cmid", faction);
        if (cmid == null || cmid.equalsIgnoreCase("")) return a;
        if (cmid.contains(",")) {
            for (String b : cmid.split(",")) {
                a.add(Integer.parseInt(b));
            }
        } else {
            a.add(Integer.parseInt(cmid));
        }
        return a;
    }

    public Faction CreateFaction(String name, CorePlayer p) {
        return CreateFaction(name, p, "Just a CyberTech Faction", false);
    }

    public int CheckFactionName(String name) {
        if (!name.matches("^[a-zA-Z0-9]*")) {
            return Error_OnlyNumbersNLetters.getID();
        }
        if (bannednames.contains(name.toLowerCase())) {
            return Error_BannedName.getID();
        }
        if (Main.factionExists(name)) {
            return Error_FactionExists.getID();
        }
        if (name.length() > 20) {
            return Error_NameTooLong.getID();
        }
        if (name.length() < 3) {
            return Error_NameTooShort.getID();
        }
        return 0;
    }

    public Faction CreateFaction(String name, CorePlayer p, String motd, boolean privacy) {

        if (p.Faction != null) {
            p.sendMessage(Error_InFaction.getMsg());
            return null;
        }


        Faction fac = new Faction(Main, name, name, p.getName().toLowerCase(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        System.out.println(fac + " <<<<<< FFFFFFFFFFFFFFFF");
        List.put(name.toLowerCase(), fac);
        FacList.put(p.getName().toLowerCase(), name);
        fac.SetPower(2);
        fac.SetDesc(motd);
        if (privacy) fac.SetPrivacy(1);
        else fac.SetPrivacy(0);
        p.sendMessage(Success_FactionCreated.getMsg());
        p.Faction = fac.GetName();
        RegitsterToRich(fac);
        return fac;
    }

    public void RegitsterToRich(String f, int cash) {
        Rich.put(f, cash);
    }

    public void RegitsterToRich(Faction f) {
        RegitsterToRich(f.GetName(), f.GetMoney());
    }

    public String factionPartialName(String name) {
        try {
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `master` where `faction`= '%s'", name + "%"));
            if (r == null) return null;
            if (r.next()) {
                return r.getString("faction");
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Faction getPlayerFaction(Player name) {
        if (name instanceof CorePlayer) {
            String ff = ((CorePlayer) name).Faction;
            if (ff != null) return getFaction(ff);
        }
        Faction f = getPlayerFaction(name.getName().toLowerCase());
        if (name instanceof CorePlayer) {
            if (f != null) {
                ((CorePlayer) name).Faction = f.GetName();
            } else {
                ((CorePlayer) name).Faction = null;
            }
        }
        return f;
    }

    public Faction getPlayerFaction(CommandSender name) {
        return getPlayerFaction(name.getName().toLowerCase());
    }

    public Faction IsPlayerInFaction(CorePlayer p) {
        String f = GetFactionFromMember(p.getName());
        System.out.println("FACCCCC >>>>>>> " + f);
        if (f == null && FacList.containsKey(p.getName().toLowerCase())) f = FacList.get(p.getName().toLowerCase());
        System.out.println("FACCCCC >>>>>>> " + f);
        if (f == null || f.length() == 0) return null;
        return getFaction(f);
    }

    public String GetFactionFromMember(String faction) {
        try {
            System.out.println(String.format("select * from `master` where `player` LIKE '%s'", faction));
            ResultSet r = this.ExecuteQuerySQL(String.format("select * from `master` where `player` LIKE '%s'", faction));
            if (r == null) return null;
            while (r.next()) {
                return r.getString("faction").toLowerCase();
            }
            return null;
        } catch (Exception e) {
            CyberCoreMain.getInstance().getLogger().error("ERROR 1544", e);
        }
        return null;

    }

    public Faction getPlayerFaction(String name) {
        if (name != null && FacList.containsKey(name.toLowerCase())) {
            return getFaction(FacList.get(name.toLowerCase()));
        }
        return null;
    }

    public Faction getFaction(String name) {
        return getFaction(name,true);
    }
    public Faction getFaction(String name, boolean create) {
        if (name == null) return null;
        if (List.containsKey(name.toLowerCase())) {
            //getServer().getLogger().debug("In Cache");
            return List.get(name.toLowerCase());
        } else if (factionExistsInDB(name) && create) {
            //getServer().getLogger().debug("In DB");
            //if (List.containsKey(name.toLowerCase())) return List.get(name.toLowerCase());
            //No leader == No Faction!
            if (GetLeader(name) == null && !name.equalsIgnoreCase("peace") && !name.equalsIgnoreCase("wilderness"))
                return null;
            Faction fac = new Faction(Main, name, (String) GetFromSettings("displayname", name), GetLeader(name), GetMemebrs(name), GetOfficers(name), GetGenerals(name), GetRecruits(name));
            fac.SetPlots(GetPlots(name,true));
            fac.SetMaxPlayers((Integer) GetFromSettings("max", name));
            fac.SetPowerBonus((Integer) GetFromSettings("powerbonus", name));
            fac.SetMOTD((String) GetFromSettings("MOTD", name));
            fac.SetDesc((String) GetFromSettings("desc", name));
            fac.SetPrivacy((Integer) GetFromSettings("privacy", name));
            fac.SetPerm((Integer) GetFromSettings("perm", name));
            fac.SetHome(GetHome(name));
            fac.SetAllies(GetAllies(name));
            fac.SetEnemies(GetEnemies(name));
            fac.SetInvite(GetInvites(name));
            fac.SetDisplayName(GetDisplayName(name));
            fac.SetPower((Integer) GetFromSettings("power", name));
            fac.SetMoney((Integer) GetFromSettings("money", name));
            fac.SetPoints((Integer) GetFromSettings("points", name));
            fac.SetXP((Integer) GetFromSettings("xp", name));
            fac.SetLevel((Integer) GetFromSettings("level", name));
            fac.RetrieveActiveMission((String) GetFromSettings("am", name));
            fac.SetRich((Integer) GetFromSettings("rich", name));
            fac.SetCompletedMissisons(GetCompletedMissions(name));
            List.put(fac.GetName().toLowerCase(), fac);

            return fac;
        }
        Main.plugin.getLogger().error("WTF NOTHING FOUND AT 374!" + name);
        return null;
    }


    public ArrayList<Faction> GetAllOpenFactions() {
        ArrayList<Faction> found = new ArrayList<>();
        try {
            ResultSet r = this.ExecuteQuerySQL("select * from `settings` where `privacy`= '1'");
            if (r == null) return null;
            while (r.next()) {
//                return r.getString("faction");
                Faction f = Main.FFactory.getFaction(r.getString("faction"));
                if (f != null) found.add(f);
            }
        } catch (Exception e) {
            Main.plugin.getLogger().error("ERROR GETTING ALL OPEN FACTIONS", e);
        }
        return found;
    }

    public ArrayList<Faction> GetAllOpenFactions(String name) {
        ArrayList<Faction> found = new ArrayList<>();
        try {
            ResultSet r = this.ExecuteQuerySQL("select * from `settings` where `privacy`= '1' and `faction` LIKE '" + name + "'");
            if (r == null) return null;
            while (r.next()) {
//                return r.getString("faction");
                Faction f = Main.FFactory.getFaction(r.getString("faction"));
                if (f != null) found.add(f);
            }
        } catch (Exception e) {
            Main.plugin.getLogger().error("ERROR GETTING ALL OPEN FACTIONS", e);
        }
        return found;
    }

    public Faction GetOwnedChunk(int xx, int zz) {
        if (PlotsList.containsKey(xx + "|" + zz)) return PlotsList.get(xx + "|" + zz);
        try {
            ArrayList<String> results = new ArrayList<>();
            ResultSet r = this.ExecuteQuerySQL("select * from `plots` where `x` = '" + xx + "' AND `z` = '" + zz + "'");
            if (r == null) return null;
            while (r.next()) {
                String fn = r.getString("faction");
                Faction f = getFaction(fn);
                if(f != null)return f;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
