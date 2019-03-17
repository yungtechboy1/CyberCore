package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Deny extends Commands {

    public Deny(CommandSender s, String[] a, FactionsMain m) {
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
        String lowercaseName = player;

        if(Main.FFactory.InvList.containsKey(Sender.getName().toLowerCase())){
            fac.DenyInvite(Sender.getName().toLowerCase());
            Sender.sendMessage(FactionsMain.NAME+TextFormat.YELLOW + "Faction Invite Denied!");
            fac.BroadcastMessage(FactionsMain.NAME+TextFormat.YELLOW+Sender.getName()+" Has denied to join your faction!");
        }
    }
}