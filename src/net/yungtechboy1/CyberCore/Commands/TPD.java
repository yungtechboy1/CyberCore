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
public class TPD extends Command {
    CyberCoreMain Owner;

    public TPD(CyberCoreMain server) {
        super("hud", "Turn your HUD on or off!", "hud <on / class / fac / pos / off>", new String[]{"tpdeny"});
        Owner = server;
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if(Owner.tpr.containsKey(s.getName().toLowerCase())) {
            Player p1 = Owner.getServer().getPlayerExact(Owner.tpr.get(s.getName().toLowerCase()));
            if(p1 == null){
                s.sendMessage("Error 2422!");
                Owner.getLogger().error("2422");
                Owner.tpr.remove(s.getName().toLowerCase());
                return true;
            }
            p1.sendMessage(Messages.TPDENYED_SENDER);
            s.sendMessage(Messages.TPDENYED.replace("{0}",p1.getName()));
            Owner.tpr.remove(s.getName().toLowerCase());
        }else{
            s.sendMessage(TextFormat.RED+"No Teleport Found!");
        }
        return true;
    }
}
