package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Msgs;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Fix {
    Main Owner;
    Integer S = 0;
    Integer T = 0;

    public Fix(Main server) {
        Owner = server;
    }

    //fix
    //fix yung

    public void runCommand(CommandSender s, String[] args) {
        CheckPerms(s, args,true);
        if (S == 0 && !s.isOp()) {
            s.sendMessage(Main.NAME + TextFormat.RED + "Error! You don't have access to this command!");
            return;
        }
        if (args.length == 0) {
            if (s instanceof Player) {
                //Check Cooldown
                Boolean skip = (S <= 7 && !s.isOp());
                if (skip) {
                    ConfigSection cds = (ConfigSection) Owner.cooldowns.get("fix");
                    if (cds.containsKey(s.getName().toLowerCase())) {
                        Integer time = (Integer) cds.get(s.getName().toLowerCase());
                        Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000);
                        //Check time
                        if (ct < time) {
                            String diff = getDifferenceBtwTime((long) time);
                            s.sendMessage(Main.NAME + TextFormat.RED + "Error! You must wait " + diff);
                            return;
                        }
                    }
                }
                Item hand = ((Player) s).getInventory().getItemInHand().clone();
                if (!(hand instanceof ItemTool) && !(hand instanceof ItemArmor)) {
                    s.sendMessage(Main.NAME + TextFormat.RED + "Error! You can repair Armor and Tools!");
                    return;
                }
                hand.setDamage(0);
                ((Player) s).getInventory().setItemInHand(hand);
                if (skip) {
                    Integer q = 60;
                    if (S == 1) q = 60 * 60 * 12;
                    if (S == 2) q = 60 * 60 * 8;
                    if (S == 3) q = 60 * 60 * 4;
                    if (S == 4) q = 60 * 60;
                    Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000) + q;
                    Owner.cooldowns.set("fix." + s.getName().toLowerCase(), ct);
                }
            } else {
                s.sendMessage(Main.NAME + Msgs.NEED_TO_BE_PLAYER);
            }
        } else {
            if(S < 8){
                s.sendMessage(Main.NAME + TextFormat.RED + "Error! You don't have access to this command!");
                return;
            }
            Player t = Owner.getServer().getPlayer(args[0]);
            if (t == null) {
                s.sendMessage(Main.NAME + TextFormat.RED+"Player "+args[0]+" not found!");
                return;
            }
            Item hand = ((Player) s).getInventory().getItemInHand();
            if (!(hand instanceof ItemTool) && !(hand instanceof ItemArmor)) {
                s.sendMessage(Main.NAME + TextFormat.RED + "Error! That player does not have a valid item selected!");
                return;
            }
            hand.setDamage(0);
            ((Player) s).getInventory().setItemInHand(hand);
        }
    }

    public void CheckPerms(CommandSender s, String[] args) {
        CheckPerms(s,args,false);
    }
    public void CheckPerms(CommandSender s, String[] args, Boolean target) {
        if (s instanceof ConsoleCommandSender) {
            S = 50;
        } else if (s instanceof Player) {
            S = Owner.GetPlayerRank((Player) s, true);
        }

        //TARGETING PLAYER CHECK TARGET RANK
        if (args.length >= 1 && target) {
            Player targett = Owner.getServer().getPlayer(args[0]);
            if (targett != null) T = Owner.GetPlayerRank(targett);
        }
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
}