package net.yungtechboy1.CyberCore.Manager.Factions;


import cn.nukkit.Nukkit;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Explosion;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.object.tree.ObjectBirchTree;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by carlt_000 on 4/10/2016.
 */
public class FactionsMain {

    private static FactionsMain instance = null;
    //private Statement Statement = null;
    private PreparedStatement PreparedStatement = null;
    private ResultSet ResultSet = null;
    public ConfigSection BossBar = new ConfigSection();
    public ConfigSection BBN = new ConfigSection();

    public ConfigSection War = new ConfigSection();
    private FactionsCommands FC = new FactionsCommands(this);
//    public Map<String,Integer> killed = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//    public Map<String,Integer> death = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//    public Map<String,Integer> pvplog = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Map<Long,CustomFloatingTextParticle> popups = new HashMap<>();
    public FactionFactory FFactory = new FactionFactory(this);
    public CyberCoreMain Main;
    //public static final String NAME = "[ArchFactions]";
    public static final String NAME = TextFormat.GOLD+""+TextFormat.BOLD+"§eTERRA§6TIDE "+TextFormat.RESET+TextFormat.GOLD+"» "+TextFormat.RESET;
    //public Map<String,Integer> death;

    public FactionsMain(CyberCoreMain main){
        Main = main;

        getServer().getPluginManager().registerEvents(new FactionListener(this),Main);

//        getServer().getScheduler().scheduleDelayedRepeatingTask(new FactionPower(this),20*10,20*60*5);
//        getServer().getScheduler().scheduleDelayedRepeatingTask(new Purge(this,false,60),20*60*5,20*60*15);//15 mINS
        //getServer().getScheduler().scheduleDelayedRepeatingTask(new BBNUpdater(this),5,5);//60 mINS


        Integer Count = 0;
        boolean peace = false;
        boolean wilderness = false;
        for(String fn : FFactory.GetAllFactions()) {
            Faction f = FFactory.getFaction(fn);
            if(f == null){
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
            }else{
                FFactory.Top.put(f.GetName(),f.GetMoney());
                FFactory.Rich.put(f.GetName(),f.GetRich());
            }
            if(fn.equalsIgnoreCase("peace")){
                peace = true;
            }else if(fn.equalsIgnoreCase("wilderness")){
                wilderness = true;
            }
            Count++;
        }
        if(!peace){
            getServer().getLogger().info("Peace Faction Being Created!");
            Faction fac = new Faction(this,"peace",TextFormat.GREEN+"peaceful","p",new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
            FFactory.List.put("peace",fac);
        }

        if(!wilderness){
            getServer().getLogger().info("Wilderness Faction Being Created!");
            Faction fac = new Faction(this,"wilderness",TextFormat.RED+"wilderness","w",new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
            FFactory.List.put("wilderness",fac);
        }

        getServer().getLogger().info("SUCCESSFUL Loading of "+Count+" Factions from the database!");
    }

    public Server getServer(){
        return Main.getServer();
    }



    public static FactionsMain getInstance(){
        return FactionsMain.instance;
    }

    public void onLoad(){
        FactionsMain.instance = this;
    }

//    public void sendBossBar(Player player, Faction fac){
//        String name = player.getName().toLowerCase();
//        BossBar bb;
//        String f;
//        Integer fp;
//        if (fac != null){
//            fp = fac.GetXPPercent();
//            // CyberTech | Level : 12\n
//            //XP: 10 / 100
//            f = fac.BossBarText();
//        }else{
//            fp = 0;
//            f = TextFormat.BOLD+"§6~~~~~§bNO§6FACTION~~~~~";
//        }
//        if(BossBar.containsKey(name)){
//            bb = (BossBar) BossBar.get(name);
//            bb.setTitle(f);
//            bb.setHealth(fp);
//        }else{
//            //BossBar bb1 = new BossBar(player, fac.GetDisplayName()+TextFormat.AQUA+" Faction"+"\n\n"+TextFormat.RESET+" XP: "+fac.GetXP()+" / "+fac.calculateRequireExperience(fac.GetLevel()),fp);
//            //BossBar bb2 = new BossBar(player, fac.GetDisplayName()+TextFormat.AQUA+" Faction"+"\n\r\n"+TextFormat.RESET+" XP: "+fac.GetXP()+" / "+fac.calculateRequireExperience(fac.GetLevel()),fp);
//            //bb1.send();
//            //bb2.send();
//
//            bb = new BossBar(player, f,fp);
//            BossBar.set(name,bb);
//        }
//        bb.send();
//    }
//
//    public void sendBossBar(String player){
//        Player p = getServer().getPlayerExact(player);
//        if(p != null)sendBossBar(p);
//    }
//    public void sendBossBar(Player player){
//        String name = player.getName().toLowerCase();
//        Faction fac = FFactory.getPlayerFaction(player);
//        Integer eid = null;
//        BossBar bb;
//        String f;
//        Integer fp;
//        if (fac != null){
//            fp = fac.GetXPPercent();
//            // CyberTech | Level : 12\n
//            //XP: 10 / 100
//            f = fac.BossBarText();
//        }else{
//            fp = 0;
//            f = TextFormat.BOLD+"§6~~~~~§bNO§6FACTION~~~~~";
//        }
//        if(BossBar.containsKey(name)){
//            bb = (BossBar) BossBar.get(name);
//            bb.setTitle(f);
//            bb.setHealth(fp);
//        }else{
//            bb = new BossBar(player, f,fp);
//            BossBar.set(name,bb);
//        }
//        bb.send();
//    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return this.FC.onCommand(this,sender,command,label,args);
    }

    public boolean isFactionsAllyed(String faction1, String faction2) {
        Faction fac1 = FFactory.getFaction(faction1);
        if(fac1 != null && fac1.isAllied(faction2))return true;
        return false;
    }

    public void AddFactionPower(String faction ,Integer power) {
        Faction fac = FFactory.getFaction(faction);
        if (fac != null){
            Integer power1 = fac.GetPowerBonus() * power;
            fac.AddPower(power1);
        }
    }

    public boolean isInFaction(Player p) {
        return isInFaction(p.getName());
    }

    public boolean isInFaction(String name) {
        if(FFactory.FacList.containsKey(name))return true;
        return false;
    }


    /**
     *
     * @param player Player
     * @return String
     */
    public String getPlayerFaction(Player player) {
        return getPlayerFaction(player.getName());
    }

    /**
     *
     * @param player String
     * @return String
     */
    public String getPlayerFaction(String player) {
        if(FFactory.FacList.containsKey(player))return FFactory.FacList.get(player);
        return null;
    }

    public int getNumberOfPlayers(String faction) {
        return getNumberOfPlayers(FFactory.getFaction(faction));
    }

    public int getNumberOfPlayers(Faction faction) {
        if(faction != null){
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
        if(fac != null)return fac.GetMaxPlayers();
        return 25;
    }

    public int GetMaxPlayers(Faction faction) {
        if(faction != null)return faction.GetMaxPlayers();
        return 25;
    }

    public Boolean isLeader(Player player) {
        return isLeader(player.getName());
    }

    public Boolean isLeader(String player) {
       if(FFactory.FacList.containsKey(player.toLowerCase())){
           Faction fac = FFactory.getFaction(FFactory.FacList.get(player.toLowerCase()));
           if(fac != null)return fac.GetLeader().toLowerCase().equalsIgnoreCase(player);
       }
        return false;
    }

    public Boolean factionExists(String fac) {
        if(FFactory.List.containsKey(fac.toLowerCase()))return true;
        return false;
    }

    public String GetChunkOwner(int x, int z) {
        if(FFactory.PlotsList.containsKey(x+"|"+z)){
            return FFactory.PlotsList.get(x+"|"+z);
        }
        return null;
    }

    public boolean isOfficer(String player) {
        if(FFactory.FacList.containsKey(player.toLowerCase())){
            Faction fac = FFactory.getFaction(FFactory.FacList.get(player.toLowerCase()));
            if(fac != null){
                for(String name: fac.GetOfficers()){
                    if(name.equalsIgnoreCase(player))return true;
                }
            }
        }
        return false;
    }

    public boolean isGeneral(String player) {
        if(FFactory.FacList.containsKey(player.toLowerCase())){
            Faction fac = FFactory.getFaction(FFactory.FacList.get(player.toLowerCase()));
            if(fac != null){
                for(String name: fac.GetGenerals()){
                    if(name.equalsIgnoreCase(player))return true;
                }
            }
        }
        return false;
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
