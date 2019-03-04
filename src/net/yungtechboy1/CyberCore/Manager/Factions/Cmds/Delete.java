package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.Calendar;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Delete extends Commands {

    public Delete(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f delete", m);
        senderMustBeInFaction = true;
        senderMustBeLeader = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (fac.Leader.equalsIgnoreCase(Sender.getName())) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Faction Deleted!");
            fac.BroadcastMessage(FactionsMain.NAME+TextFormat.YELLOW+"!!~~!!Faction has been Deleted by "+Sender.getName());
            Main.FFactory.RemoveFaction(fac);
        } else {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "You are not the leader!");
        }
    }
}