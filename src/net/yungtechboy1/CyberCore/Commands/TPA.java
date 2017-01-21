package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Msgs;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class TPA {
    public static Player runCommand(CommandSender s, Main server){
        if(server.tpr.containsKey(s.getName().toLowerCase())) {
            Player p1 = server.getServer().getPlayerExact(server.tpr.get(s.getName().toLowerCase()));
            if(p1 == null){
                s.sendMessage("Error 242!");
                server.getLogger().error("242");
                server.tpr.remove(s.getName().toLowerCase());
                return null;
            }
            p1.sendMessage(Msgs.TPING.replace("{0}",s.getName()));
            s.sendMessage(Msgs.TPACCEPTED.replace("{0}",s.getName()));
            return p1;
        }else{
            s.sendMessage(TextFormat.RED+"No Teleport Found!");
            return null;
        }
    }
}
