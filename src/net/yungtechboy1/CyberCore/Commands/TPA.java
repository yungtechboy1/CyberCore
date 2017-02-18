package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class TPA extends Command {
    CyberCoreMain Owner;

    public TPA(CyberCoreMain server) {
        super("TPA", "Accept TP", "/tpa",new String[]{"tpaccept"});
        Owner = server;
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.player");
    }



    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if(Owner.tpr.containsKey(s.getName().toLowerCase())) {
            Player p1 = Owner.getServer().getPlayerExact(Owner.tpr.get(s.getName().toLowerCase()));
            if(p1 == null){
                s.sendMessage("Error 242!");
                Owner.getLogger().error("242");
                Owner.tpr.remove(s.getName().toLowerCase());
                return true;
            }
            p1.sendMessage(Messages.TPING.replace("{0}",s.getName()));
            s.sendMessage(Messages.TPACCEPTED.replace("{0}",s.getName()));
            return true;
        }else{
            s.sendMessage(TextFormat.RED+"No Teleport Found!");
            return true;
        }
    }
}
