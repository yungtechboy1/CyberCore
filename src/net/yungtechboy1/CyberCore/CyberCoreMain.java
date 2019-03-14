package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.BossBar.BossBarManager;
import net.yungtechboy1.CyberCore.Manager.Econ.EconManager;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextFactory;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Bans.Ban;
import net.yungtechboy1.CyberCore.Commands.*;
import net.yungtechboy1.CyberCore.Commands.Gamemode.*;
import net.yungtechboy1.CyberCore.Commands.Homes.*;
import net.yungtechboy1.CyberCore.Custom.Item.ItemChickenCooked;
import net.yungtechboy1.CyberCore.Custom.Item.ItemPorkchopCooked;
import net.yungtechboy1.CyberCore.Events.CyberChatEvent;
import net.yungtechboy1.CyberCore.Factory.*;
import net.yungtechboy1.CyberCore.Manager.KD.KDManager;
import net.yungtechboy1.CyberCore.Manager.SQLManager;
import net.yungtechboy1.CyberCore.Manager.Save.SaveMain;
import net.yungtechboy1.CyberCore.MobAI.MobPlugin;
import net.yungtechboy1.CyberCore.Tasks.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class CyberCoreMain extends PluginBase implements CommandExecutor, Listener {

    public static final String NAME = TextFormat.GOLD + "" + TextFormat.BOLD + "§eTERRA§6CORE " + TextFormat.RESET + TextFormat.GOLD + "» " + TextFormat.RESET;
    private EconManager ECON;
    private static CyberCoreMain instance;
    public BossBarManager BBM;
    //CyberChat
    public static Connection Connect = null;
    public static Connection Connect2 = null;
    public static String Prefix = TextFormat.AQUA + "[TerraTP]";
    public Config job;
    public Config Homes;
    public Config ban;
    public Config tban;
    public Config tcban;
    public Config tipban;
    public Config cooldowns;
    //HUD Off
    //TODO add PlayerSetting Object to allow players to save
    //TODO add /settings commands that adds GUI
    public ArrayList<String> HudOff = new ArrayList<>();
    public ArrayList<String> HUDClassOff = new ArrayList<>();
    public ArrayList<String> HUDFactionOff = new ArrayList<>();
    public ArrayList<String> HUDPosOff = new ArrayList<>();
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
    //Floating Text
    public FloatingTextFactory FTM;
    //Mob Plugin and AI
    public MobPlugin MP;
    //KDR
    public KDManager KDM;
    //Classes / MMO
    public ClassFactory ClassFactory;
    //PasswordFactoy
    public PasswordFactoy PasswordFactoy;
    //CustomFactory
    public CustomFactory CustomFactory;
    //FactoriesA
    public HomeManager HomeFactory;
    public RankFactory RankFactory;
    public AuctionFactory AuctionFactory;
    public List<String> Final = new ArrayList<>();
    public List<String> TPING = new ArrayList<>();
    public HashMap<String, HashMap<String, Object>> cache = new HashMap<>();
    public HashMap<String, String> LastMsg = new HashMap<>();
    //public CyberTech.CyberChat.Main CC;
    public Map<String, String> tpr = new HashMap<>();
    public ArrayList<Ban> bans = new ArrayList<>();
    Vector3 p1;
    Vector3 p2;

    public SaveMain Save;
    public SQLManager SQLSaveManager;

    @Override
    public void onEnable() {
        new File(getDataFolder().toString()).mkdirs();

        Save = new SaveMain(this);
        SQLSaveManager = new SQLManager(this);

        Item.list[Item.COOKED_CHICKEN] = ItemChickenCooked.class;
        Item.list[Item.COOKED_PORKCHOP] = ItemPorkchopCooked.class;

        getServer().getScheduler().scheduleDelayedTask(new GetFactions(this), 20);

        //KDR Manager
        KDM = new KDManager();
        //BossBar Manager
        BBM = new BossBarManager(this);
        //Floating Text
        FTM = new FloatingTextFactory(this);
        //Mob Plugin
        MP = new MobPlugin(this);

        ECON = new EconManager(this);

        getServer().getScheduler().scheduleRepeatingTask(new UnMuteTask(this), 20 * 15);
        getServer().getScheduler().scheduleRepeatingTask(new ClearSpamTick(this), 20 * 5);
        getServer().getScheduler().scheduleRepeatingTask(new CheckOP(this), 20 * 60);//1 Min

        Homes = new Config(new File(this.getDataFolder(), "homes.yml"), Config.YAML, new ConfigSection());

        HomeFactory = new HomeManager(this);
        RankFactory = new RankFactory(this);
        AuctionFactory = new AuctionFactory(this);

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
        if (ban.getAll().size() > 0) {
            for (Object c : ban.getAll().values().toArray()) {
                if(c instanceof ConfigSection)bans.add(new Ban((ConfigSection)c));
            }
        }
        tban = new Config(new File(this.getDataFolder(), "tban.yml"), Config.YAML);
        tcban = new Config(new File(this.getDataFolder(), "tcban.yml"), Config.YAML);
        tipban = new Config(new File(this.getDataFolder(), "tipban.yml"), Config.YAML);
        job = new Config(new File(this.getDataFolder(), "job.yml"), Config.YAML);
        cooldowns = new Config(new File(this.getDataFolder(), "cooldowns.yml"), Config.YAML);
        getLogger().info(TextFormat.GREEN + "Initializing Cyber Essentials");

        PasswordFactoy = new PasswordFactoy(this);

        ClassFactory = new ClassFactory(this);

        CustomFactory = new CustomFactory(this);

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new CyberChatEvent(this), this);
        getServer().getPluginManager().registerEvents(ClassFactory, this);
        getServer().getPluginManager().registerEvents(AuctionFactory, this);

        getServer().getScheduler().scheduleDelayedTask(new Restart(this), 20 * 60 * 60 * 2);//EVERY 2 Hours
        getServer().getScheduler().scheduleRepeatingTask(new SendHUD(this), 50);//EVERY Sec


        //COMMANDS
        getServer().getCommandMap().register("CyberCore", new BanCmd(this));
        getServer().getCommandMap().register("CyberCore", new Ci(this));
        getServer().getCommandMap().register("CyberCore", new Fix(this));
        getServer().getCommandMap().register("CyberCore", new IPBan(this));
        getServer().getCommandMap().register("CyberCore", new Msg(this));
        getServer().getCommandMap().register("CyberCore", new Reply(this));
        getServer().getCommandMap().register("CyberCore", new Spawn(this));
        //getServer().getCommandMap().register("CyberCore", new Tban(this));
        getServer().getCommandMap().register("CyberCore", new Top(this));
        getServer().getCommandMap().register("CyberCore", new Vote(this));
        getServer().getCommandMap().register("CyberCore", new Wild(this));

        getServer().getCommandMap().register("CyberCore", new Home(this));
        getServer().getCommandMap().register("CyberCore", new SetHome(this));
        getServer().getCommandMap().register("CyberCore", new DelHome(this));

        getServer().getCommandMap().register("CyberCore", new AA(this));
        getServer().getCommandMap().register("CyberCore", new ClassCmd(this));
        getServer().getCommandMap().register("CyberCore", new SetClass(this));

        getServer().getCommandMap().register("CyberCore", new Hud(this));

        getServer().getCommandMap().register("CyberCore", new FT(this));
        getServer().getCommandMap().register("CyberCore", new FTS(this));
        getServer().getCommandMap().register("CyberCore", new FTR(this));

        getServer().getCommandMap().register("CyberCore", new TPR(this));
        getServer().getCommandMap().register("CyberCore", new TPD(this));
        getServer().getCommandMap().register("CyberCore", new TPA(this));

        getServer().getCommandMap().register("CyberCore", new Warp(this));
        getServer().getCommandMap().register("CyberCore", new SetWarp(this));

        getServer().getCommandMap().register("CyberCore", new ClassCmd(this));
        getServer().getCommandMap().register("CyberCore", new AClassCmd(this));

        getServer().getCommandMap().register("CyberCore", new Sync(this));

        getServer().getCommandMap().register("CyberCore", new Email(this));
        getServer().getCommandMap().register("CyberCore", new Login(this));
        getServer().getCommandMap().register("CyberCore", new Register(this));

        getServer().getCommandMap().register("CyberCore", new ChatEnchant(this));

        getServer().getCommandMap().register("CyberCore", new AuctionHouseCmd(this));
        getServer().getCommandMap().register("CyberCore", new SellHand(this));
    }

    public void onLoad(){
        CyberCoreMain.instance = this;
    }

    public static CyberCoreMain getInstance(){
        return CyberCoreMain.instance;
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
    public Connection getMySqlConnection2() {
        try {
            if (Connect2 != null && Connect2.isClosed()) Connect2 = null;
            if (Connect2 != null) return Connect2;
            Class.forName("com.mysql.jdbc.Driver");
            //getLogger().info(TextFormat.GREEN+"Connecting to database...");
            Connect2 = DriverManager.getConnection("jdbc:mysql://localhost/Terratide?user=TerraTideMC&password=TerraTideMC&autoreconnect=true");
            return Connect2;
            //} catch (Exception ignore) {
        } catch (SQLException ex) {
            // handle any errors
            getServer().getLogger().info("SQLException: " + ex.getMessage());
            getServer().getLogger().info("SQLState: " + ex.getSQLState());
            getServer().getLogger().info("VendorError: " + ex.getErrorCode());
            Connect2 = null;
        } catch (Exception ignore) {
            getServer().getLogger().info(ignore.getClass().getName() + ": " + ignore.getMessage());
            Connect2 = null;
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
        for (Ban b : bans) {
            bc.put(b.name, b.toconfig());
        }
        ban.setAll(bc);
        ban.save();
        tipban.save();
        tban.save();
        tcban.save();
        cooldowns.save();

        Homes.save();

        //CyberChat
        MainConfig.save();
        MuteConfig.save();
        RankListConfig.save();

        PasswordFactoy.onDisable();

        //Classes
        ClassFactory.Saveall();

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
     *
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

        if (FM != null) {
            Faction f = FM.FFactory.getPlayerFaction(p);
            if (f != null) {
                f1 = TextFormat.GRAY + "[" + f.GetFactionNameTag(p) + TextFormat.GRAY + "]\n";
            } else {
                f1 = TextFormat.GRAY + "[NF]";
            }
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

    @EventHandler(ignoreCancelled = true)
    public void PlayerLoginEvent(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        p.getName();
        for (Ban b : bans) {
            if (b.checkbanned(p, event)){
                event.setCancelled();
                return;
            }
        }

    }

    public String getDifferenceBtwTime(Object dateTime) {
        return getDifferenceBtwTime(Long.parseLong(dateTime + ""));
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

    public EconManager GetEcon(){
        return ECON;
    }
}
