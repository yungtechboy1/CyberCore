package net.yungtechboy1.CyberCore.Manager.Factions;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.CyberUtils;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.FactionBaseCMD;
import net.yungtechboy1.CyberCore.Manager.Factions.Data.FactionSQL;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionInvited;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlt_000 on 4/10/2016.
 */
public class FactionsMain {

    //public static final String NAME = "[ArchFactions]";
    public static final String NAME = CyberUtils.NAME;
    private static FactionsMain instance = null;
    public ConfigSection BossBar = new ConfigSection();
    public ConfigSection BBN = new ConfigSection();
    public ConfigSection War = new ConfigSection();
    public FactionsCommands FC = new FactionsCommands(this);
    //    public Map<String,Integer> killed = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//    public Map<String,Integer> death = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//    public Map<String,Integer> pvplog = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Map<Long, CustomFloatingTextParticle> popups = new HashMap<>();
    public FactionFactory FFactory = new FactionFactory(this);
    ;
    public CyberCoreMain plugin;
    public FactionSQL FactionData = null;
    public HashMap<Integer, FactionErrorString> FactionString = new HashMap<>();
    //private Statement Statement = null;
    private PreparedStatement PreparedStatement = null;
    //public Map<String,Integer> death;
    private ResultSet ResultSet = null;

    public FactionsMain(CyberCoreMain main, FactionSQL fd) {
        plugin = main;
        if (fd == null) FactionData = new FactionSQL(CyberCoreMain.getInstance());
        else FactionData = fd;
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
        for (String fn : FFactory.GetAllFactionsNames()) {
            System.out.println("Loading Faction " + fn);
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
                    stmt.executeUpdate(String.format("DELETE FROM `Master` WHERE `faction` LIKE '%s';",fn));
                    stmt.executeUpdate(String.format("DELETE FROM `Master` WHERE `faction` LIKE '%s';",fn));
                    stmt.close();
                } catch (Exception  ex) {
                    getServer().getLogger().info( ex.getClass().getName() + ":9 " + ex.getMessage()+" > "+ex.getStackTrace()[0].getLineNumber()+" ? "+ex.getCause());
                }*/
            } else {
                FFactory.Top.put(f.getName(), f.getSettings().getMoney());
                FFactory.Rich.put(f.getName(), f.getSettings().getRich());
            }
            if (fn.equalsIgnoreCase("peace")) {
                peace = true;
            } else if (fn.equalsIgnoreCase("wilderness")) {
                wilderness = true;
            }
            Count++;
        }
        if (!peace) {
            getServer().getLogger().info("Peace Faction Being Created!");
            Faction fac = new Faction(this, "peace", TextFormat.GREEN + "peaceful", false);
            FFactory.LocalFactionCache.put("peace", fac);
        }
//
        if (!wilderness) {
            getServer().getLogger().info("Wilderness Faction Being Created!");
            Faction fac = new Faction(this, "wilderness", TextFormat.RED + "wilderness", false);
            FFactory.LocalFactionCache.put("wilderness", fac);
        }
//
//        getServer().getLogger().info("SUCCESSFUL Loading of "+Count+" Factions from the database!");
    }

    public FactionsMain(CyberCoreMain cyberCoreMain) {
        this(cyberCoreMain, null);
    }

    public static FactionsMain getInstance() {
        return FactionsMain.instance;
    }

    public Server getServer() {
        return plugin.getServer();
    }

    public void onLoad() {
        FactionsMain.instance = this;
//        FFactory = new FactionFactory(this);
    }

    public void initiatePlayer(CorePlayer player) {
    }

    public boolean isFactionsAllyed(String faction1, String faction2) {
        Faction fac1 = FFactory.getFaction(faction1);
        if (fac1 != null && fac1.isAllied(faction2)) return true;
        return false;
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
        if (FFactory.LocalFactionCache.containsKey(fac.toLowerCase())) return true;
        return false;
    }

    public String GetChunkOwner(int x, int z) {
        return FactionsMain.getInstance().FFactory.PM.getFactionFromPlot(x, z);
    }

    public void LoadPlayer(Player player) {

    }

    public void PlayerInvitedToFaction(CorePlayer invited, CorePlayer Sender, Faction fac) {
        PlayerInvitedToFaction(invited, Sender, fac, fac.getPermSettings().getDefaultJoinRank());
    }

    public void PlayerInvitedToFaction(CorePlayer invited, CorePlayer Sender, Faction fac, FactionRank fr) {
        if (invited == null) {
            CyberCoreMain.getInstance().getLogger().warning("WARNING!!! TRING TO INVITE NULL PLAYER to FAC: " + fac.getSettings().getDisplayName());
            return;
        }

        fac.BroadcastMessage(Sender.getName() + " has invited " + invited.getName() + " to the faction as a " + fr.getChatColor() + fr.getName());
        Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "You successfully invited " + invited.getName() + " to your faction as a " + fr.getChatColor() + fr.getName() + " !");
        invited.sendMessage(FactionsMain.NAME + TextFormat.YELLOW + "You have been invited to join " + fac.getSettings().getDisplayName() + " by " + Sender.getName() + "\n" + TextFormat.GREEN + "Type '/f accept' or '/f deny' into chat to accept or deny!");

        Integer time = GetIntTime() + 60 * 5;//5 Mins
        fac.AddInvite(invited, time, Sender, fr);

        invited.FactionInvite = fac.getName();
        invited.FactionInviteTimeout = time;

//        FFactory.InvList.put(invited.getName().toLowerCase(), fac.getName());

        FFactory.addFactionInvite(new FactionInviteData(invited.getName(), time, fac.getName(), fr));


//        invited.
        if (!invited.InternalPlayerSettings.isAllowFactionRequestPopUps()) return;
        invited.showFormWindow(new FactionInvited(invited.getDisplayName(), fac.getSettings().getDisplayName()));


    }

    public Integer GetIntTime() {
        return plugin.getIntTime();
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
