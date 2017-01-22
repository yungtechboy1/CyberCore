package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.*;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import main.java.CyberFactions.FactionsMain;
import net.yungtechboy1.CyberCore.Bans.Ban;
import net.yungtechboy1.CyberCore.Commands.*;
import net.yungtechboy1.CyberCore.Commands.Gamemode.GMC;
import net.yungtechboy1.CyberCore.Commands.Gamemode.GMS;
import net.yungtechboy1.CyberCore.Commands.Homes.DelHome;
import net.yungtechboy1.CyberCore.Commands.Homes.Home;
import net.yungtechboy1.CyberCore.Commands.Homes.SetHome;
import net.yungtechboy1.CyberCore.Events.CyberChatEvent;
import net.yungtechboy1.CyberCore.Ranks.RankFactory;
import net.yungtechboy1.CyberCore.Tasks.*;
import ru.nukkit.welcome.players.PlayerManager;
import com.mysql.jdbc.Driver;

import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class CyberCoreMain extends PluginBase implements CommandExecutor, Listener {

    public static final String NAME = TextFormat.GOLD + "" + TextFormat.BOLD + "§eTERRA§6CORE " + TextFormat.RESET + TextFormat.GOLD + "» " + TextFormat.RESET;
    public Config job;
    public Config Homes;
    public Config ban;
    public Config tban;
    public Config tcban;
    public Config tipban;
    public Config cooldowns;


    //CyberChat
    public static Connection Connect = null;
    public Config RankChatColor;
    public Config RankConfig;
    public Config MainConfig;
    public Config RankListConfig;
    public FactionsMain FM;
    public Boolean nf = true;
    public Config MuteConfig;
    public List<String> PlayerMuted = new ArrayList<>();
    public Boolean MuteChat = false;
    public Map<String, Integer> Spam = new HashMap<>();
    public Map<String, String> LM = new HashMap<>();

    //Factories
    public Homes HomeFactory;
    public RankFactory RankFactory;

    public List<String> Final = new ArrayList<>();
    public List<String> TPING = new ArrayList<>();

    public HashMap<String, HashMap<String, Object>> cache = new HashMap<>();
    public HashMap<String, String> LastMsg = new HashMap<>();
    //public CyberTech.CyberChat.Main CC;
    public Map<String, String> tpr = new HashMap<>();
    public ArrayList<Ban> bans = new ArrayList<>();
    Vector3 p1;
    Vector3 p2;

    public static String Prefix = TextFormat.AQUA + "[TerraTP]";

    @Override
    public void onEnable() {
        new File(getDataFolder().toString()).mkdirs();

        initExternalPlugins();

        getServer().getScheduler().scheduleRepeatingTask(new UnMuteTask(this), 20 * 15);
        getServer().getScheduler().scheduleRepeatingTask(new ClearSpamTick(this), 20 * 5);
        getServer().getScheduler().scheduleRepeatingTask(new CheckOP(this), 20 * 60 * 5);//5 Mins

        HomeFactory = new Homes(this);
        RankFactory = new RankFactory(this);

        MainConfig = new Config(new File(getDataFolder(), "config.yml"), Config.YAML,
                new LinkedHashMap<String, Object>() {
                    {
                        put("Chat-Format", "{rank}{faction}{player-name} > {msg}");
                        put("Faction-Format", "[{value}]");
                        put("Rank-Format", "[{value}]");
                        put("Join-Message", "");
                        put("Leave-Message", "");
                    }
                });
        RankConfig = new Config(new File(getDataFolder(), "rank.yml"), Config.YAML,
                new LinkedHashMap<String, Object>() {
                    {
                        put("registered", TextFormat.GRAY + "Memeber");
                        put("hero", "&aH&bE&cR&dO");
                        put("vip", "&aV&bI&cP");
                        put("vip+", "&aV&bI&cP&d+");
                        put("legend", "&aL&bE&cN&dE&eN&eD");
                        put("steve+", "&aS&bT&cE&dV&eE&e+");
                        put("mod1", "&aMOD1");
                        put("mod2", "&aMOD2");
                        put("mod3", "&aMOD3");
                        put("mod4", "&aMOD4");
                        put("mod5", "&aMOD5");
                        put("admin1", "&cADMIN1");
                        put("admin2", "&cADMIN2");
                        put("admin3", "&cADMIN3");
                        put("op", "&9OP");
                        put("yt", TextFormat.DARK_RED + "YT");
                        put("adventurer", TextFormat.RED + "Adventurer");
                        put("islander", TextFormat.GREEN + "Islander");
                        put("adventurer", TextFormat.GOLD + "Adventurer");
                        put("conquerer", TextFormat.YELLOW + "Conquerer");
                    }
                });

        RankChatColor = new Config(new File(getDataFolder(), "RankChatColor.yml"), Config.YAML,
                new LinkedHashMap<String, Object>() {
                    {
                        put("registered", TextFormat.GRAY);
                        put("hero", "&d");
                        put("vip", "&c");
                        put("vip+", "&d");
                        put("legend", "&e");
                        put("steve+", "&e");
                        put("mod1", "&a");
                        put("mod2", "&a");
                        put("mod3", "&a");
                        put("mod4", "&a");
                        put("mod5", "&a");
                        put("admin1", "&c");
                        put("admin2", "&c");
                        put("admin3", "&c");
                        put("op", "&9");
                        put("yt", TextFormat.RED + "");
                        put("adventurer", TextFormat.RED + "");
                        put("islander", TextFormat.GREEN + "");
                        put("adventurer", TextFormat.GOLD + "");
                        put("conquerer", TextFormat.YELLOW + "");
                    }
                });
        RankListConfig = new Config(new File(getDataFolder(), "rank.yml"), Config.YAML);
        MuteConfig = new Config(new File(getDataFolder(), "Mute.yml"), Config.YAML);

        ban = new Config(new File(this.getDataFolder(), "ban.yml"), Config.YAML);
        if(ban.getAll().size() > 0) {
            for (ConfigSection c : (ConfigSection[]) ban.getAll().values().toArray()) {
                bans.add(new Ban(c));
            }
        }
        tban = new Config(new File(this.getDataFolder(), "tban.yml"), Config.YAML);
        tcban = new Config(new File(this.getDataFolder(), "tcban.yml"), Config.YAML);
        tipban = new Config(new File(this.getDataFolder(), "tipban.yml"), Config.YAML);
        job = new Config(new File(this.getDataFolder(), "job.yml"), Config.YAML);
        Homes = new Config(new File(this.getDataFolder(), "homes.yml"), Config.YAML);
        cooldowns = new Config(new File(this.getDataFolder(), "cooldowns.yml"), Config.YAML);
        getLogger().info(TextFormat.GREEN + "Initializing Cyber Essentials");

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new CyberChatEvent(this), this);

        getServer().getScheduler().scheduleDelayedTask(new Restart(this), 20 * 60 * 60 * 2);//EVERY 2 Hours
       /*PluginCommand pc = new PluginCommand<>("msg",this);
        pc.setUsage("/msg <Player> [Message]");
        pc.setPermission("CyberTech.CyberCore.player");*/
        getServer().getCommandMap().register("CyberCore",new net.yungtechboy1.CyberCore.Commands.Ban(this));
        getServer().getCommandMap().register("CyberCore",new Ci(this));
        getServer().getCommandMap().register("CyberCore",new Fix(this));
        getServer().getCommandMap().register("CyberCore",new IPBan(this));
        getServer().getCommandMap().register("CyberCore",new Msg(this));
        getServer().getCommandMap().register("CyberCore",new Reply(this));
        getServer().getCommandMap().register("CyberCore",new Spawn(this));
        getServer().getCommandMap().register("CyberCore",new Tban(this));
        getServer().getCommandMap().register("CyberCore",new Top(this));
        getServer().getCommandMap().register("CyberCore",new Vote(this));
        getServer().getCommandMap().register("CyberCore",new net.yungtechboy1.CyberCore.Commands.Ban(this));

        getServer().getCommandMap().register("CyberCore",new Home(this));
        getServer().getCommandMap().register("CyberCore",new SetHome(this));
        getServer().getCommandMap().register("CyberCore",new DelHome(this));
    }

    public void initExternalPlugins(){
        Plugin plug = getServer().getPluginManager().getPlugin("CyberFaction");
        if (plug instanceof FactionsMain) {
            setEnabled(false);
            getLogger().error("Error Factions Not Found!");
        }
        FM = (FactionsMain) getServer().getPluginManager().getPlugin("CyberFaction");
    }

    public Connection getMySqlConnection() {
        try {
            if (Connect != null && Connect.isClosed()) Connect = null;
            if (Connect != null) return Connect;
            Class.forName("com.mysql.jdbc.Driver");
            //getLogger().info(TextFormat.GREEN+"Connecting to database...");
            Connect = DriverManager.getConnection("jdbc:mysql://209.126.102.26/terratide_db?user=terratide_db&password=terratide_db&autoreconnect=true");
            return Connect;
            //} catch (Exception ignore) {
        } catch (SQLException ex) {
            // handle any errors
            getServer().getLogger().info("SQLException: " + ex.getMessage());
            getServer().getLogger().info("SQLState: " + ex.getSQLState());
            getServer().getLogger().info("VendorError: " + ex.getErrorCode());
            Connect = null;
        } catch (Exception ignore) {
            getServer().getLogger().info(ignore.getClass().getName() + ": " + ignore.getMessage());
            Connect = null;
        }
        return null;
    }

    public Map<TimeUnit, Long> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        Map<TimeUnit, Long> result = new LinkedHashMap<>();
        long milliesRest = diffInMillies;
        for (TimeUnit unit : units) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit, diff);
        }
        return result;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ConfigSection bc = new ConfigSection();
        for(Ban b:bans){
            bc.put(b.name,b.toconfig());
        }
        ban.setAll(bc);
        ban.save();
        tipban.save();
        tban.save();
        tcban.save();
        cooldowns.save();

        //CyberChat
        MainConfig.save();
        MuteConfig.save();
        RankListConfig.save();
        try {
            getMySqlConnection().close();
        } catch (Exception ex) {
        }
    }

    public void setMuteM(Player target, Integer time) {
        setMuteM(target.getName(), time);
        target.sendMessage(TextFormat.YELLOW + "You are now muted for " + (time * 60) + " Mins");
    }

    public void setMuteM(String target, Integer time) {
        Integer finaltime = (int) (Calendar.getInstance().getTime().getTime() / 1000) + (time * 60);
        MuteConfig.set(target.toLowerCase(), finaltime);
    }

    public void setMuteS(Player target, Integer time) {
        setMuteS(target.getName(), time);
        target.sendMessage(TextFormat.YELLOW + "You are now muted for " + time + " Secs");
    }

    public void setMuteS(String target, Integer time) {
        Integer finaltime = (int) (Calendar.getInstance().getTime().getTime() / 1000) + (time);
        MuteConfig.set(target.toLowerCase(), finaltime);
    }

    public void removeMute(String target) {
        MuteConfig.remove(target.toLowerCase());
    }

    public void removeMute(Player target) {
        removeMute(target.getName());
    }

    public boolean isMuted(Player player) {
        return isMuted(player.getName());
    }

    public boolean isMuted(String player) {
        player = player.toLowerCase();
        if (MuteConfig.exists(player)) {
            if ((Calendar.getInstance().getTime().getTime() / 1000) > (int) MuteConfig.get(player)) {
                MuteConfig.remove(player);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns Player Faction
     * @param p
     * @return String
     */
    public String getPlayerFaction(Player p) {
        if (FM != null) {
            Faction fac = FM.FFactory.getPlayerFaction(p);
            if (fac != null) {
                //@Todo add support for PREMIUM Facs
                return fac.GetDisplayName();
            }
        }
        return "";
    }

    public String getPlayerRankCache(String p) {
        return RankFactory.getPlayerRank(p);
    }

    public boolean checkSpam(Player p) {
        if (Spam.containsKey(p.getName().toLowerCase())) {
            int count = Spam.get(p.getName().toLowerCase());
            count++;
            Spam.put(p.getName().toLowerCase(), count);
            if (count >= 4) {
                p.sendMessage(TextFormat.YELLOW + "Slow Down Typing!");
                setMuteS(p, 10);
                return false;
            }
        } else {
            Spam.put(p.getName().toLowerCase(), 1);
        }
        return true;
    }

    public void Setnametag(Player p) {
        Setnametag(p.getName());
    }

    public void Setnametag(String p) {
        String RankFormat = null;
        if (RankFactory.getPlayerRank(p) != null) {
            String a = RankFactory.getPlayerRank(p);
            RankFormat = (String) RankConfig.get(a);
        }
        String pn = p;

        String f1 = "";//Factioin
        String f2 = "";//Rank

        Faction f = FM.FFactory.getPlayerFaction(p);
        if (f != null) {
            f1 = TextFormat.GRAY + "[" + f.GetFactionNameTag(p) + TextFormat.GRAY + "]\n";
        } else {
            f1 = TextFormat.GRAY + "[NF]";
        }

        if (RankFormat != null) {
            f2 = TextFormat.AQUA + "[" + RankFormat.replace("&", TextFormat.ESCAPE + "") + TextFormat.AQUA + "]";
        }
        Player pp = getServer().getPlayerExact(p);
        if (pp == null) getLogger().info("WTF12313 123 123 12 3");
        pp.setNameTag(f1 + f2 + " " + TextFormat.GRAY + pp.getName());
    }

    public Integer GetPlayerRankInt(Player p) {
        return GetPlayerRankInt(p, false);
    }

    public Integer GetPlayerRankInt(Player p, Boolean all) {
        return GetPlayerRankInt(p.getName().toLowerCase(), all);
    }

    public Integer GetPlayerRankInt(String p, Boolean all) {
        return RankFactory.AllRanksToInt(RankFactory.GetMasterRank(p));
    }

    @EventHandler
    public void PIEe(PlayerInteractEvent event) {/*
        event.getPlayer().setDataProperty(new StringEntityData(Entity.DATA_URL_TAG, "http://www.terratide.net"));
        event.getPlayer().sendMessage("AAA");*/
    }

    @EventHandler(ignoreCancelled = true)
    public void PlayerLoginEvent(PlayerPreLoginEvent event) {
        Player p = event.getPlayer();
        for(Ban b: bans){
            if(b.checkbanned(p,event))return;
        }

    }

    public String getDifferenceBtwTime(Object dateTime) {
        return getDifferenceBtwTime(Long.parseLong(dateTime+""));
    }
    public String getDifferenceBtwTime(Long dateTime) {

        long timeDifferenceMilliseconds = dateTime - new Date().getTime();
        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = (long) (timeDifferenceMilliseconds / (1000 * 60 * 60 * 24 * 365));

        if (diffSeconds < 1) {
            return "one sec";
        } else if (diffMinutes < 1) {
            return diffSeconds + " seconds";
        } else if (diffHours < 1) {
            return diffMinutes + " minutes";
        } else if (diffDays < 1) {
            return diffHours + " hours";
        } else if (diffWeeks < 1) {
            return diffDays + " days";
        } else if (diffMonths < 1) {
            return diffWeeks + " weeks";
        } else if (diffYears < 12) {
            return diffMonths + " months";
        } else {
            return diffYears + " years";
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
       String cmdd = cmd.getName().toLowerCase();
        switch (cmdd) {
            case "wild":
                Wild.runCommand(s, this);
                return true;
            case "cl":
                if (s.isOp()) {
                    for (Map.Entry<Integer, Level> level : getServer().getLevels().entrySet()) {
                        for (Entity e : level.getValue().getEntities()) {
                            if (e instanceof Player) continue;
                            e.kill();
                        }
                    }
                    return true;
                }
                return false;
            case "gms":
                if (s.isOp()) {
                    GMS.runCommand(s, args, this);
                    return true;
                }
                return false;
            case "gmc":
                if (s.isOp()) {
                    GMC.runCommand(s, args, this);
                    return true;
                }
                return false;
            case "chat":
                if (args.length == 1) {
                    String pnl = s.getName().toLowerCase();
                    if (args[0].equalsIgnoreCase("on")) {
                        if (PlayerMuted.contains(pnl)) {
                            s.sendMessage(TextFormat.YELLOW + "Chat Already Muted!");
                        } else {
                            PlayerMuted.add(pnl);
                            s.sendMessage(TextFormat.GREEN + "Chat Is Now Muted!");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("off")) {
                        if (PlayerMuted.contains(pnl)) {
                            s.sendMessage(TextFormat.GREEN + "Chat Is Now Un-Muted!");
                            PlayerMuted.remove(pnl);
                        } else {
                            s.sendMessage(TextFormat.YELLOW + "Chat Is Not Muted!");
                        }
                        return true;
                    }
                }
                return false;
            case "rank":
                if (RankFactory.RankCache.containsKey(s.getName())) {
                    RankFactory.RankCache.remove(s.getName());
                }
                if (RankFactory.GARC.containsKey(s.getName())) {
                    RankFactory.GARC.remove(s.getName());
                }
                if (RankFactory.MRC.containsKey(s.getName())) {
                    RankFactory.MRC.remove(s.getName());
                }
                if (RankFactory.SRC.containsKey(s.getName())) {
                    RankFactory.SRC.remove(s.getName());
                }
                String a = "";
                a = RankFactory.GetAdminRank(s.getName());
                if (a == null) a = RankFactory.GetMasterRank(s.getName());
                if (a == null) a = RankFactory.GetSecondaryRank(s.getName());
                if (a != null && a.equalsIgnoreCase("op")) s.setOp(true);
                s.sendMessage(TextFormat.GREEN + "[TerraTide] Your Rank is: " + a);
                break;
            case "link":
                /*
                if (args.length == 2) {
                    String username = args[0];
                    String pin = args[1];
                    if (CheckUserPin(username, pin, s)) {
                        s.sendMessage(TextFormat.GREEN + "Accounts Linked!");
                    }
                } else {
                    s.sendMessage(TextFormat.RED + "Usage: /link <forum username> <Forum PIN>");
                }*/
                return true;
            case "chmute":
                if (MuteChat) {
                    getServer().broadcastMessage(TextFormat.YELLOW + "All Chat Un-Muted!");
                    MuteChat = false;
                } else {
                    getServer().broadcastMessage(TextFormat.YELLOW + "All Chat Muted!");
                    MuteChat = true;
                }
                return true;
            case "mute":
                if (args.length == 0) {
                    return false;
                } else if (args.length == 1) {
                    Integer time = 5 * 60;//5 Mins
                    Player Target = getServer().getPlayer(args[0]);
                    if (Target == null) {
                        s.sendMessage(TextFormat.RED + "[TerraTide] Error, Player " + args[0] + " not found!");
                        return true;
                    }
                    setMuteM(Target, time);
                    return true;
                } else if (args.length == 2) {
                    Integer time;
                    try {
                        time = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        s.sendMessage(TextFormat.RED + "[TerraTide] Usage /mute <Player> [Mins]");
                        return true;
                    }
                    Player Target = getServer().getPlayer(args[0]);
                    if (Target == null) {
                        s.sendMessage(TextFormat.RED + "[TerraTide] Error, Player " + args[0] + " not found!");
                        return true;
                    }
                    setMuteM(Target, time);
                    return true;
                }
                return false;
            case "unmute":
                if (args.length == 0) {
                    return false;
                } else if (args.length == 1) {
                    Player Target = getServer().getPlayer(args[0]);
                    if (Target == null) {
                        s.sendMessage(TextFormat.RED + "[TerraTide] Error, Player " + args[0] + " not found!");
                        return true;
                    }
                    removeMute(Target);
                    return true;
                }
        }
        return false;
    }
}
