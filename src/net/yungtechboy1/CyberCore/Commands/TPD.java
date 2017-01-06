package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Msgs;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class TPD {
    public static void runCommand(CommandSender s, Main server){
        if(server.tpr.containsKey(s.getName().toLowerCase())) {
            Player p1 = server.getServer().getPlayerExact(server.tpr.get(s.getName().toLowerCase()));
            if(p1 == null){
                s.sendMessage("Error 2422!");
                server.getLogger().error("2422");
                server.tpr.remove(s.getName().toLowerCase());
                return;
            }
            p1.sendMessage(Msgs.TPDENYED_SENDER);
            s.sendMessage(Msgs.TPDENYED.replace("{0}",p1.getName()));
            server.tpr.remove(s.getName().toLowerCase());
        }else{
            s.sendMessage(TextFormat.RED+"No Teleport Found!");
        }
    }
}
