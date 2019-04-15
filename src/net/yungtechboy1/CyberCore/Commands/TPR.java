package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class TPR extends Command {
    CyberCoreMain Owner;

    public TPR(CyberCoreMain server) {
        super("tpr", "Request TP", "/tpr <player>");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, false)
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if(args.length == 0 || !(s instanceof CorePlayer)){
            s.sendMessage(TextFormat.RED+" > /tpr {Player}");
        }else if(args.length == 1){
            Player t = Owner.getServer().getPlayer(args[0]);
            if(t != null ){
                t.sendMessage(Messages.TPA_REQUEST.replace("{0}",s.getName()));
                s.sendMessage(Messages.TPA_SENT.replace("{0}",t.getName()));
                ((CorePlayer)t).TPR = s.getName();
            }else{
                s.sendMessage(Messages.PLAYER_NOT_FOUND);
            }
        }
        return true;
    }
}
