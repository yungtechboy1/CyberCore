package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class TPA extends Command {
    CyberCoreMain Owner;

    public TPA(CyberCoreMain server) {
        super("TPA", "Accept TP", "/tpa", new String[]{"tpaccept"});
        Owner = server;
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.player");
    }


    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (!(s instanceof CorePlayer)) return false;
        CorePlayer cp = (CorePlayer) s;
        if (cp.TPR != null) {
            CorePlayer p1 = (CorePlayer)Owner.getServer().getPlayerExact(cp.TPR);
            if (p1 == null) {
                s.sendMessage("Error 242! Error Player can not be found! Please ask them to re-send the request!");
                Owner.getLogger().error("Error Player can not be found! Please ask them to re-send the request!");
                return true;
            }
            p1.sendMessage(Messages.TPING.replace("{0}", s.getName()));
            s.sendMessage(Messages.TPACCEPTED.replace("{0}", s.getName()));
            cp.StartTeleport(p1);
            return true;
        } else {
            s.sendMessage(TextFormat.RED + "No Teleport Found!");
            return true;
        }
    }
}
