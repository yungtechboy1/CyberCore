package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Msgs;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Ci extends Command {
    Main Owner;

    public Ci(Main server) {
        super("ci");
        Owner = server;
        this.setPermission("CyberTech.CyberCore.op");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender s, String str, String[] args) {
        if (!this.testPermission(s)) {
            return true;
        }
        if (s instanceof Player) {
            Player p = (Player) s;
            if (args.length == 1) {
                Player t = Owner.getServer().getPlayer(args[0]);
                if (t == null) {
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                t.getInventory().clearAll();
                t.sendMessage(TextFormat.YELLOW + "Your Inventory Cleared!");
                s.sendMessage(TextFormat.GREEN + t.getName() + " Inventory Cleared!");
            } else {
                p.getInventory().clearAll();
            }
        } else {
            if (args.length == 1) {
                Player t = Owner.getServer().getPlayer(args[0]);
                if (t == null) {
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                t.getInventory().clearAll();
                t.sendMessage(TextFormat.YELLOW + "Your Inventory Cleared!");
                s.sendMessage(TextFormat.GREEN + t.getName() + " Inventory Cleared!");
                return true;
            }
            s.sendMessage(Msgs.NEED_TO_BE_PLAYER);
        }
        return true;
    }
}