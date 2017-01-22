package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class TPD {
    public static void runCommand(CommandSender s, CyberCoreMain server){
        if(server.tpr.containsKey(s.getName().toLowerCase())) {
            Player p1 = server.getServer().getPlayerExact(server.tpr.get(s.getName().toLowerCase()));
            if(p1 == null){
                s.sendMessage("Error 2422!");
                server.getLogger().error("2422");
                server.tpr.remove(s.getName().toLowerCase());
                return;
            }
            p1.sendMessage(Messages.TPDENYED_SENDER);
            s.sendMessage(Messages.TPDENYED.replace("{0}",p1.getName()));
            server.tpr.remove(s.getName().toLowerCase());
        }else{
            s.sendMessage(TextFormat.RED+"No Teleport Found!");
        }
    }
}
