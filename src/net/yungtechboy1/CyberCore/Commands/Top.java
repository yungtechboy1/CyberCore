package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Messages;

import java.util.Calendar;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Top {
    Main Owner;
    Integer S = 0;
    Integer T = 0;

    public Top(Main server) {
        Owner = server;
    }

    //top

    public void runCommand(CommandSender s, String[] args) {
        CheckPerms(s, args);
        if (S == 0 && !s.isOp()) {
            s.sendMessage(Main.NAME + TextFormat.RED + "Error! You don't have access to this command!");
            return;
        }
        if (s instanceof Player) {
            //Check Cooldown
            Boolean skip = (S <= 7 && !s.isOp());
            if (skip) {
                ConfigSection cds = (ConfigSection) Owner.cooldowns.get("top");
                if (cds.containsKey(s.getName().toLowerCase())) {
                    Integer time = (Integer) cds.get(s.getName().toLowerCase());
                    Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000);
                    //Check time
                    if (ct < time) {
                        String diff = Owner.getDifferenceBtwTime((long) time);
                        s.sendMessage(Main.NAME + TextFormat.RED + "Error! You must wait " + diff);
                        return;
                    }
                }
            }

            gototp((Player) s);

            if (skip) {
                Integer q = 10;
                if (S == 1) q = 60 * 5;
                if (S == 2) q = 60 * 2;
                Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000) + q;
                Owner.cooldowns.set("top." + s.getName().toLowerCase(), ct);
            }
        } else {
            s.sendMessage(Main.NAME + Messages.NEED_TO_BE_PLAYER);
        }
    }

    public void CheckPerms(CommandSender s, String[] args) {
        CheckPerms(s, args,false);
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

    public void gototp(Player s) {
        int y;
        for (y = 256; y >= 0; --y) {
            int b = s.getLevel().getBlockIdAt(s.getFloorX(), y, s.getFloorZ());
            if (b != Block.AIR && b != Block.LEAVES && b != Block.LEAVES2 && b != Block.SNOW_LAYER) break;
        }
        if (y == 0) {
            s.sendMessage(Main.NAME + TextFormat.RED + "Error! Could not teleport to top!");
        } else {
            s.teleport(new Vector3(s.getFloorX(), ++y, s.getFloorZ()));
            s.sendMessage(Main.NAME + TextFormat.GREEN + "Teleport to top!");
        }

    }
}