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

public class Spawn {

    public static void runCommand(CommandSender s,String[] args, Main server){
        if(s instanceof Player){
            Player p = (Player)s;
            int r = server.GetPlayerRank(p);
            if(args.length == 1 && r > 0){
                Player t = server.getServer().getPlayer(args[0]);
                if(t == null){
                    s.sendMessage(TextFormat.RED+"Error! Target Player Not Found!");
                    return;
                }
                t.teleport(t.getLevel().getSafeSpawn());
                t.sendMessage(TextFormat.YELLOW+" Your at spawn!");
                s.sendMessage(TextFormat.GREEN+t.getName()+" Teleported to spawn!");
            }else{
                p.teleport(p.getLevel().getSafeSpawn());
                s.sendMessage(TextFormat.GREEN+"Your at spawn!");
            }
        } else {
            if(args.length == 1){
                Player t = server.getServer().getPlayer(args[0]);
                if(t == null){
                    s.sendMessage(TextFormat.RED+"Error! Target Player Not Found!");
                    return;
                }
                t.teleport(t.getLevel().getSafeSpawn());
                t.sendMessage(TextFormat.YELLOW+" Your at spawn!");
                s.sendMessage(TextFormat.GREEN+t.getName()+" Teleported to spawn!");
                return;
            }
            s.sendMessage(Msgs.NEED_TO_BE_PLAYER);
        }
    }
}