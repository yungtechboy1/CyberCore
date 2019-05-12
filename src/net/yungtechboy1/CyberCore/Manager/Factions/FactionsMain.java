package net.yungtechboy1.CyberCore.Manager.Factions;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.FactionBaseCMD;
import net.yungtechboy1.CyberCore.Manager.Factions.Data.FactionSQL;
import net.yungtechboy1.CyberCore.Utils;

import java.sql.*;
import java.util.*;

/**
 * Created by carlt_000 on 4/10/2016.
 */
public class FactionsMain {

    //public static final String NAME = "[ArchFactions]";
    public static final String NAME = Utils.NAME;
    private static FactionsMain instance = null;
    public ConfigSection BossBar = new ConfigSection();
    public ConfigSection BBN = new ConfigSection();
    public ConfigSection War = new ConfigSection();
    public FactionsCommands FC = new FactionsCommands(this);
    //    public Map<String,Integer> killed = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//    public Map<String,Integer> death = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//    public Map<String,Integer> pvplog = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Map<Long, CustomFloatingTextParticle> popups = new HashMap<>();
    public FactionFactory FFactory = null;
    public CyberCoreMain plugin;
    public FactionSQL factionData;
    public HashMap<Integer, FactionString> TextList = new HashMap<>();
    //private Statement Statement = null;
    private PreparedStatement PreparedStatement = null;
    //public Map<String,Integer> death;
    private ResultSet ResultSet = null;

    public FactionsMain(CyberCoreMain main, FactionSQL factionData) {
        plugin = main;
        this.factionData = factionData;
        onLoad();

        getServer().getPluginManager().registerEvents(new FactionListener(main, this), main);
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Manager/Factions/Cmds", new FactionBaseCMD(plugin));

//        getServer().getScheduler().scheduleDelayedRepeatingTask(new FactionPower(this),20*10,20*60*5);
//        getServer().getScheduler().scheduleDelayedRepeatingTask(new Purge(this,false,60),20*60*5,20*60*15);//15 mINS
        //getServer().getScheduler().scheduleDelayedRepeatingTask(new BBNUpdater(this),5,5);//60 mINS
//
//
        Integer Count = 0;
        boolean peace = false;
        boolean wilderness = false;
        System.out.println(FFactory == null);
        System.out.println(FFactory.GetAllFactions() == null);
        for (String fn : FFactory.GetAllFactions()) {
            Faction f = FFactory.getFaction(fn);
            if (f == null) {
                continue;
                /*Connection c = FFactory.getMySqlConnection();
                try {
                    getServer().getLogger().error("DELETEING Faction "+fn+"!");
                    Statement stmt = c.createStatement();
                    stmt.executeUpdate(String.format("DELETE FROM `allies` WHERE `factiona` LIKE '%s' OR `factionb` LIKE '%s';",fn,fn));
                    stmt.executeUpdate(String.format("DELETE FROM `plots` WHERE `faction` LIKE '%s';",fn));
                    stmt.executeUpdate(String.format("DELETE FROM `confirm` WHERE `faction` LIKE '%s';",fn));
                    stmt.executeUpdate(String.format("DELETE FROM `home` WHERE `faction` LIKE '%s';",fn));
                    stmt.executeUpdate(String.format("DELETE FROM `settings` WHERE `faction` LIKE '%s';",fn));
                    stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `faction` LIKE '%s';",fn));
                    stmt.close();
                } catch (Exception  ex) {
                    getServer().getLogger().info( ex.getClass().getName() + ":9 " + ex.getMessage()+" > "+ex.getStackTrace()[0].getLineNumber()+" ? "+ex.getCause());
                }*/
            } else {
                FFactory.Top.put(f.GetName(), f.GetMoney());
                FFactory.Rich.put(f.GetName(), f.GetRich());
            }
            if (fn.equalsIgnoreCase("peace")) {
                peace = true;
            } else if (fn.equalsIgnoreCase("wilderness")) {
                wilderness = true;
            }
            Count++;
        }
//        if(!peace){
//            getServer().getLogger().info("Peace Faction Being Created!");
//            Faction fac = new Faction(this,"peace",TextFormat.GREEN+"peaceful","p",new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
//            FFactory.List.put("peace",fac);
//        }
//
//        if(!wilderness){
//            getServer().getLogger().info("Wilderness Faction Being Created!");
//            Faction fac = new Faction(this,"wilderness",TextFormat.RED+"wilderness","w",new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
//            FFactory.List.put("wilderness",fac);
//        }
//
//        getServer().getLogger().info("SUCCESSFUL Loading of "+Count+" Factions from the database!");
    }

    public static FactionsMain getInstance() {
        return FactionsMain.instance;
    }

    public Server getServer() {
        return plugin.getServer();
    }

    public void onLoad() {
        FactionsMain.instance = this;
        FFactory = new FactionFactory(this);
    }

    public void initiatePlayer(CorePlayer player) {
    }

    public boolean isFactionsAllyed(String faction1, String faction2) {
        Faction fac1 = FFactory.getFaction(faction1);
        if (fac1 != null && fac1.isAllied(faction2)) return true;
        return false;
    }

    public void AddFactionPower(String faction, Integer power) {
        Faction fac = FFactory.getFaction(faction);
        if (fac != null) {
            Integer power1 = fac.GetPowerBonus() * power;
            fac.AddPower(power1);
        }
    }

    public boolean isInFaction(Player player) {
        return player != null && isInFaction((CorePlayer) player);
    }

    public boolean isInFaction(CorePlayer player) {
        return player != null && player.Faction != null;
    }

    public boolean isInFaction(String name) {
        return isInFaction(plugin.getCorePlayer(name));
    }

    /**
     * @param player CorePlayer
     * @return String
     */
    public String getPlayerFaction(CorePlayer player) {
        return getPlayerFaction(player.getName());
    }

    /**
     * @param uuid String
     * @return String
     */
    public String getPlayerFaction(String uuid) {
        //if(FFactory.FacList.containsKey(player))return FFactory.FacList.get(player);
        return null;
    }

    public int getNumberOfPlayers(String faction) {
        return getNumberOfPlayers(FFactory.getFaction(faction));
    }

    public int getNumberOfPlayers(Faction faction) {
        if (faction != null) {
            Integer a = 1;
            a += faction.GetMembers().toArray().length;
            a += faction.GetOfficers().toArray().length;
            a += faction.GetGenerals().toArray().length;
            return a;
        }
        return 0;
    }

    public int GetMaxPlayers(String faction) {
        Faction fac = FFactory.getFaction(faction);
        if (fac != null) return fac.GetMaxPlayers();
        return 25;
    }

    public int GetMaxPlayers(Faction faction) {
        if (faction != null) return faction.GetMaxPlayers();
        return 25;
    }

    public Boolean isLeader(CorePlayer player) {
        return isLeader(player.getName());
    }

    public Boolean isLeader(String player) {
        if (FFactory.FacList.containsKey(player.toLowerCase())) {
            Faction fac = FFactory.getFaction(FFactory.FacList.get(player.toLowerCase()));
            if (fac != null) return fac.GetLeader().toLowerCase().equalsIgnoreCase(player);
        }
        return false;
    }

    public Boolean factionExists(String fac) {
        if (FFactory.List.containsKey(fac.toLowerCase())) return true;
        return false;
    }

    public String GetChunkOwner(int x, int z) {
        if (FFactory.PlotsList.containsKey(x + "|" + z)) {
            return FFactory.PlotsList.get(x + "|" + z);
        }
        return null;
    }

    public boolean isOfficer(String player) {
        if (FFactory.FacList.containsKey(player.toLowerCase())) {
            Faction fac = FFactory.getFaction(FFactory.FacList.get(player.toLowerCase()));
            if (fac != null) {
                for (String name : fac.GetOfficers()) {
                    if (name.equalsIgnoreCase(player)) return true;
                }
            }
        }
        return false;
    }

    public boolean isGeneral(String player) {
        if (FFactory.FacList.containsKey(player.toLowerCase())) {
            Faction fac = FFactory.getFaction(FFactory.FacList.get(player.toLowerCase()));
            if (fac != null) {
                for (String name : fac.GetGenerals()) {
                    if (name.equalsIgnoreCase(player)) return true;
                }
            }
        }
        return false;
    }

    public void LoadPlayer(Player player) {

    }

    public enum FactionString {
        NO_ERROR("NO ERROR", 0),
        Success_FactionCreated(TextFormat.GREEN + "[CyboticFactions] Faction successfully created!",101),
        Success_ADMIN_Faction_Saved(TextFormat.GREEN + "[CyboticFactions] All Factions Successfully Saved!",102),
        Success_ADMIN_Faction_Load(TextFormat.GREEN + "[CyboticFactions] All Factions Successfully Saved!",103),
        Success_ADMIN_Faction_Reload(TextFormat.GREEN + "[CyboticFactions] All Factions Successfully Saved!",104),
        Error_OnlyNumbersNLetters(FactionsMain.NAME + TextFormat.RED + "You may only use letters and numbers!", 1),
        Error_BannedName(FactionsMain.NAME + TextFormat.RED + "That is a Banned faction Name!", 2),
        Error_FactionExists(FactionsMain.NAME + TextFormat.RED + "Faction already exists", 3),
        Error_NameTooLong(FactionsMain.NAME + TextFormat.RED + "Faction name is too long. Please try again!", 4),
        Error_InFaction(FactionsMain.NAME + TextFormat.RED + "You must leave your faction first", 5),
        Error_NameTooShort(FactionsMain.NAME + TextFormat.RED + "Your faction name must be at least 3 Letters long", 6),
        Error_SA221("Seems like an error occured! Please report Error: SA221", 221),
        Error_SA223("Seems like an error occured while creating your faction! Please report Error: SA223", 223);
//        FactionExists(FactionsMain.NAME+TextFormat.RED+"Faction already exists",2),
//        FactionExists(FactionsMain.NAME+TextFormat.RED+"Faction already exists",2),


        static int k = 0;
        private String Msg;
        private int ID;


        FactionString(String s, int id) {
            Msg = s;
            ID = id;
            FactionsMain.getInstance().TextList.put(id, this);

        }

        public String getMsg() {
            return Msg;
        }

        public int getID() {
            return ID;
        }


        @Override
        public String toString() {
            return getMsg();
        }
    }

//    public ConfigSection getBBN() {
//        return BBN;
//    }
//    public void setBBN(ConfigSection BBN) {
//        this.BBN = BBN;
//    }
//    public void AddBBN(Player n,BossBarNotification a){
//        AddBBN(n.getName().toLowerCase(),a);
//    }
//    public void AddBBN(String n,BossBarNotification a){
//        BBN.put(n,a);
//    }
//    public void DelBBN(Player n){
//        DelBBN(n.getName().toLowerCase());
//    }
//    public void DelBBN(String n){
//        if(BBN.containsKey(n))BBN.remove(n);
//    }
}
