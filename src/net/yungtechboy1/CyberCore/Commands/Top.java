package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Msgs;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Top {
    Main Owner;
    public void Top(Main server){
        Owner = server;
    }

    public static void runCommand(CommandSender s,String[] args, Main server){
        if(s instanceof Player){
            Player p = (Player)s;
            Vector3 v = p;
            p.teleport(p.getLevel().getSafeSpawn(v));
        } else {
            s.sendMessage(Msgs.NEED_TO_BE_PLAYER);
        }
    }
}