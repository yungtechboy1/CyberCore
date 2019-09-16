package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Faction.FactionLeaveConfirmWindow;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Leave extends Commands {

    public Leave(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f leave", m);
        senderMustBeInFaction = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (!fac.Leader.equalsIgnoreCase(Sender.getName())) {
           Sender.showFormWindow(new FactionLeaveConfirmWindow(fac));
        } else {
            Sender.sendMessage(FactionsMain.NAME+"You are the leader of the faction... Please Do `/f del` if you wish to leave or pass leadership on to someone else!");
        }
    }
}