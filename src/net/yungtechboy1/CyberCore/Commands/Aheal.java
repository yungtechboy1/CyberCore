package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;

import java.util.Calendar;
import java.util.Date;

import static net.yungtechboy1.CyberCore.Utils.getDifferenceBtwTime;

/**
 * Created by carlt_000 on 3/21/2016.
 */

//@TODO!
public class Aheal {
    CyberCoreMain Owner;
    Integer S = 0;
    Integer T = 0;

    //@TODO
    //  /AbilityHeal?
    public Aheal(CyberCoreMain server) {
        Owner = server;
    }

    //fix
    //fix yung

    public void runCommand(CommandSender s, String[] args) {
        CheckPerms(s, args);
        if (S == 0 && !s.isOp()) {
            s.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You don't have access to this command!");
            return;
        }
        if (args.length == 0) {
            if (s instanceof Player) {
                //Check Cooldown
                Boolean skip = (S <= 7 && !s.isOp());
                if (skip) {
                    ConfigSection cds = (ConfigSection) Owner.cooldowns.get("aheal");
                    if (cds.containsKey(s.getName().toLowerCase())) {
                        Integer time = (Integer) cds.get(s.getName().toLowerCase());
                        Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000);
                        //Check time
                        if (ct < time) {
                            String diff = getDifferenceBtwTime((long) time);
                            s.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You must wait " + diff);
                            return;
                        }
                    }
                }
                Vector3 pv3 = (Vector3) s;

                /*

                for (int x = -15; x < 16; x++) {
                    for (int y = s.ge; y < 255; y++) {
                        for (int z = -15; z < 16; z++) {
                            String id = "" + chunk.getBlockId(x, y, z);
                            if (Main.BV.exists(id)) value += (Integer) Main.BV.get(id);
                        }
                    }
                }

                */

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
                s.sendMessage(CyberCoreMain.NAME + Messages.NEED_TO_BE_PLAYER);
            }
        } else {
            if(S < 8){
                s.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You don't have access to this command!");
                return;
            }
            Player t = Owner.getServer().getPlayer(args[0]);
            if (t == null) {
                s.sendMessage(CyberCoreMain.NAME + TextFormat.RED+"Player "+args[0]+" not found!");
                return;
            }
            Item hand = ((Player) s).getInventory().getItemInHand();
            if (!(hand instanceof ItemTool) && !(hand instanceof ItemArmor)) {
                s.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! That player does not have a valid item selected!");
                return;
            }
            hand.setDamage(0);
            ((Player) s).getInventory().setItemInHand(hand);
        }
    }

    public void CheckPerms(CommandSender s, String[] args) {
        CheckPerms(s,args);
    }
    public void CheckPerms(CommandSender s, String[] args, Boolean target) {
        if (s instanceof ConsoleCommandSender) {
            S = 50;
        } else if (s instanceof Player) {
            S = Owner.GetPlayerRankInt((Player) s, true);
        }

        //TARGETING PLAYER CHECK TARGET RANK
        if (args.length >= 1 && target) {
            Player targett = Owner.getServer().getPlayer(args[0]);
            if (targett != null) T = Owner.GetPlayerRankInt(targett);
        }
    }
}