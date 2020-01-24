package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
@Deprecated
public class Deny extends Commands {

    public Deny(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f Deny", m);
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        String player = Sender.getName();
        CorePlayer cp = (CorePlayer) Sender;

        if (cp.FactionInvite != null) {
            Faction fac = Main.FFactory.getFaction(cp.FactionInvite);
            fac.BroadcastMessage(TextFormat.YELLOW + Sender.getName() + " has declined the invitation to your faction", fac.getPermSettings().getAllowedToInvite());
            cp.FactionInvite = null;
            cp.FactionInviteTimeout = -1;
            Sender.sendMessage(TextFormat.GREEN+"Faction invite declined!");
            //TODO add check in Coreplayer to clear after the 3 Mins...
            return;
        }

        Sender.sendMessage(TextFormat.RED + "Error! No faction invite found");
    }
}