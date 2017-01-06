package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Msgs;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Ci {
    Main Owner;
    public void Ci(Main server){
        Owner = server;
    }

    public static void runCommand(CommandSender s,String[] args, Main server){
        if(s instanceof Player){
            Player p = (Player)s;
            if(args.length == 1){
                Player t = server.getServer().getPlayer(args[0]);
                if(t == null){
                    s.sendMessage(TextFormat.RED+"Error! Target Player Not Found!");
                    return;
                }
                t.getInventory().clearAll();
                t.sendMessage(TextFormat.YELLOW+"Your Inventory Cleared!");
                s.sendMessage(TextFormat.GREEN+t.getName()+" Inventory Cleared!");
            }else{
                p.getInventory().clearAll();
            }
        } else {
            if(args.length == 1){
                Player t = server.getServer().getPlayer(args[0]);
                if(t == null){
                    s.sendMessage(TextFormat.RED+"Error! Target Player Not Found!");
                    return;
                }
                t.getInventory().clearAll();
                t.sendMessage(TextFormat.YELLOW+"Your Inventory Cleared!");
                s.sendMessage(TextFormat.GREEN+t.getName()+" Inventory Cleared!");
                return;
            }
            s.sendMessage(Msgs.NEED_TO_BE_PLAYER);
        }
    }
}