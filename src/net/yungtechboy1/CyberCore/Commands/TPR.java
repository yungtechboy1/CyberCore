package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Messages;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class TPR {
    public static void runCommand(CommandSender s, String[] args, Main server){
        if(args.length == 0 || !(s instanceof Player)){
            s.sendMessage(TextFormat.RED+" > /tpr {Player}");
        }else if(args.length == 1){
            Player t = server.getServer().getPlayer(args[0]);
            if(t != null ){
                t.sendMessage(Messages.TPA_REQUEST.replace("{0}",s.getName()));
                s.sendMessage(Messages.TPA_SENT.replace("{0}",t.getName()));
                server.tpr.put(t.getName().toLowerCase(),s.getName().toLowerCase());
            }else{
                s.sendMessage(Messages.PLAYER_NOT_FOUND);
            }
        }
    }
}
