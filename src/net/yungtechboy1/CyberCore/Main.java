package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.command.*;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Commands.*;
import net.yungtechboy1.CyberCore.Commands.Gamemode.GMC;
import net.yungtechboy1.CyberCore.Commands.Gamemode.GMS;
import net.yungtechboy1.CyberCore.Tasks.TeleportEvent;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class Main extends PluginBase implements CommandExecutor, Listener {

    public static final String NAME = TextFormat.GOLD + "" + TextFormat.BOLD + "§eTERRA§6CORE " + TextFormat.RESET + TextFormat.GOLD + "» " + TextFormat.RESET;
    public Config job;
    public Config tban;
    public Config tcban;
    public Config tipban;
    public Config cooldowns;
    public HashMap<String, HashMap<String, Object>> cache = new HashMap<>();
    public HashMap<String, String> LastMsg = new HashMap<>();
    public CyberTech.CyberChat.Main CC;
    public Map<String, String> tpr = new HashMap<>();
    Vector3 p1;
    Vector3 p2;

    @Override
    public void onEnable() {
        new File(getDataFolder().toString()).mkdirs();

        CC = (CyberTech.CyberChat.Main) getServer().getPluginManager().getPlugin("CyberChat");
        tban = new Config(new File(this.getDataFolder(), "tban.yml"), Config.YAML);
        tcban = new Config(new File(this.getDataFolder(), "tcban.yml"), Config.YAML);
        tipban = new Config(new File(this.getDataFolder(), "tipban.yml"), Config.YAML);
        job = new Config(new File(this.getDataFolder(), "job.yml"), Config.YAML);
        cooldowns = new Config(new File(this.getDataFolder(), "cooldowns.yml"), Config.YAML);
        getLogger().info(TextFormat.GREEN + "Initializing Cyber Essentials");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getScheduler().scheduleDelayedTask(new Restart(this), 20 * 60 * 60 * 2);//EVERY 2 Hours
        PluginCommand pc = new PluginCommand<>("msg",this);
        pc.setUsage("/msg <Player> [Message]");
        pc.setPermission("CyberTech.CyberCore.player");
        getServer().getCommandMap().register(getDescription().getName(),pc);
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
        tipban.save();
        tban.save();
        tcban.save();
        cooldowns.save();
    }

    public Integer GetPlayerRank(Player p) {
        return GetPlayerRank(p, false);
    }

    public Integer GetPlayerRank(Player p, Boolean all) {
        return GetPlayerRank(p.getName().toLowerCase(), all);
    }

    public Integer GetPlayerRank(String p, Boolean all) {
        String rank = "";
        rank = CC.GetAdminRank(p);
        if (rank == null) rank = CC.GetMasterRank(p);
        if (rank == null) rank = CC.GetSecondaryRank(p);
        if (rank == null) {
            return 0;
        } else if (rank.equalsIgnoreCase("tourist")) {
            if (all) return 1;
        } else if (rank.equalsIgnoreCase("islander")) {
            if (all) return 2;
        } else if (rank.equalsIgnoreCase("adventurer")) {
            if (all) return 3;
        } else if (rank.equalsIgnoreCase("conquerer")) {
            if (all) return 4;
        } else if (rank.equalsIgnoreCase("TMOD")) {
            return 5;
        } else if (rank.equalsIgnoreCase("MOD1") || rank.equalsIgnoreCase("yt")) {
            return 6;
        } else if (rank.equalsIgnoreCase("MOD2")) {
            return 7;
        } else if (rank.equalsIgnoreCase("MOD3")) {
            return 8;
        } else if (rank.equalsIgnoreCase("ADMIN1")) {
            return 9;
        } else if (rank.equalsIgnoreCase("ADMIN2")) {
            return 10;
        } else if (rank.equalsIgnoreCase("ADMIN3")) {
            return 11;
        } else if (rank.equalsIgnoreCase("OP")) {
            return 12;
        }
        return 0;
    }

    @EventHandler(ignoreCancelled = true)
    public void PlayerLoginEvent(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        if (tban.exists(p.getName().toLowerCase()) && Long.parseLong(tban.get(p.getName().toLowerCase())+"") > new Date().getTime()) {
            event.setCancelled(true);
            event.setKickMessage(TextFormat.RED + "You are Temp Banned for " + getDifferenceBtwTime(tban.get(p.getName().toLowerCase())) + "!");
        } else if (tipban.exists(p.getAddress().toLowerCase()) && Long.parseLong(tipban.get(p.getAddress().toLowerCase())+"") > new Date().getTime()) {
            event.setCancelled(true);
            event.setKickMessage(TextFormat.RED + "Your IP is Temp Banned for " + getDifferenceBtwTime(tipban.get(p.getAddress().toLowerCase())) + "!");
        } else if (tcban.exists(p.getUniqueId().toString()) && Long.parseLong(tcban.get(p.getUniqueId().toString())+"") > new Date().getTime()) {
            event.setCancelled(true);
            event.setKickMessage(TextFormat.RED + "Your Client is Temp Banned for " + getDifferenceBtwTime(tcban.get(p.getUniqueId().toString())) + "!");
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
        Integer a = 0;
        if (s instanceof ConsoleCommandSender) {
            a = 50;
        } else if (s instanceof Player) {
            a = GetPlayerRank((Player) s);
        }

        //TARGETING PLAYER CHECK TARGET RANK
        Integer t = 0;
        if (args.length >= 1) {
            Player targett = getServer().getPlayer(args[0]);
            if (targett != null) {
                t = GetPlayerRank(targett);
            }
        }


        String cmdd = cmd.getName().toLowerCase();
        switch (cmdd) {
            case "vote":
                Vote.runCommand(s, args, this);
                return true;
            case "msg":
                Msg.runCommand(s, args, this);
                return true;
            case "message":
                Msg.runCommand(s, args, this);
                return true;
            case "reply":
                Reply.runCommand(s, args, this);
                return true;
            case "r":
                Reply.runCommand(s, args, this);
                return true;
            case "tpaccept":
            case "tpa":
                Player ve = TPA.runCommand(s, this);
                if (ve != null) {
                    getServer().getScheduler().scheduleDelayedTask(new TeleportEvent(this, ve, (Vector3) s), 20 * 20);
                }
                return true;
            case "tpd":
                TPD.runCommand(s, this);
                return true;
            case "tpdeny":
                TPD.runCommand(s, this);
                return true;
            case "tpr":
                TPR.runCommand(s, args, this);
                return true;
            case "spawn":
                Spawn.runCommand(s, args, this);
                return true;
            case "wild":
                Wild.runCommand(s, this);
                return true;
            case "fix":
                new Fix(this).runCommand(s, args);
                return true;
            case "job":
                Job.runCommand(s, args, this);
                return true;
            case "cl":
                if (a >= 5 || s.isOp()) {
                    for (Map.Entry<Integer, Level> level : getServer().getLevels().entrySet()) {
                        for (Entity e : level.getValue().getEntities()) {
                            if (e instanceof Player) continue;
                            e.kill();
                        }
                    }
                    return true;
                }
            case "top":
                new Top(this).runCommand(s, args);
                return true;
            case "tipban":
                if (a > 5 || s.isOp() || s instanceof ConsoleCommandSender) {
                    if (!CanTarget(s, a, t)) return false;
                    TIPBan.runCommand(s, args, this);
                    return true;
                }
            case "tipbanp":
                if (a > 5 || s.isOp() || s instanceof ConsoleCommandSender) {
                    tipban.set(args[0].toLowerCase(), 0L);
                    s.sendMessage(TextFormat.GREEN + "Removed Ban from " + args[0]);
                    return true;
                }
            case "tban":
                if (a > 4 || s.isOp() || s instanceof ConsoleCommandSender) {
                    if (!CanTarget(s, a, t)) return false;
                    Tban.runCommand(s, args, this);
                    return true;
                }
            case "tbanp":
                if (a > 4 || s.isOp() || s instanceof ConsoleCommandSender) {
                    tban.set(args[0].toLowerCase(), 0L);
                    s.sendMessage(TextFormat.GREEN + "Removed Ban from " + args[0]);
                    return true;
                }
            case "tcban":
                if (a >= 8 || s.isOp() || s instanceof ConsoleCommandSender) {
                    if (!CanTarget(s, a, t)) return false;
                    TCBan.runCommand(s, args, this);
                    return true;
                }
            case "cban":
                if (a >= 8 || s.isOp() || s instanceof ConsoleCommandSender) {
                    if (!CanTarget(s, a, t)) return false;
                    LocalDateTime time = LocalDateTime.now().plusYears(99);
                    ZonedDateTime zdt = time.atZone(ZoneId.of("America/Chicago"));
                    long millis = zdt.toInstant().toEpochMilli();

                    Player target = getServer().getPlayer(args[0]);
                    if (target == null) {
                        s.sendMessage(TextFormat.RED + "Target Not Found!");
                        return true;
                    }
                    tcban.set(target.getUniqueId().toString(), millis);
                    for (Item I : target.getInventory().getContents().values()) target.getLevel().dropItem(target, I);
                    target.kick("Your Client Have Been Temp Banned For 24 Hours!");
                    return true;
                }
            case "gms":
                if (a >= 11 || s.isOp()) {
                    GMS.runCommand(s, args, this);
                    return true;
                }
            case "gmc":
                if (a >= 11 || s.isOp()) {
                    GMC.runCommand(s, args, this);
                    return true;
                }
        }
        return false;
    }


    public boolean CanTarget(CommandSender sender, Integer a, Integer t) {
        return CanTarget(sender, a, t, false);
    }

    public boolean CanTarget(CommandSender sender, Integer a, Integer t, Boolean equal) {
        if (a > t) {
            return true;
        }
        if (a == t) return equal;
        if (a < t) {
            sender.sendMessage(TextFormat.RED + "You can not target that player! He is a higher rank than you!");
            return false;
        }
        return true;
    }
}
