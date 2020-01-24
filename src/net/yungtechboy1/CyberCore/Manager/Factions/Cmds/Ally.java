package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 12/8/2016.
 */
public class Ally extends Commands {

    public Ally(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f ally <fac>", m);
        senderMustBeInFaction = true;
        senderMustBeLeader = true;
        senderMustBeGeneral = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (Args.length <= 1) {
            Sender.sendMessage(TextFormat.RED + "[ArchFactions] Usage: /f ally <fac>");
            return;
        }

        FactionRank r = fac.getPlayerRank(Sender);
        if (!fac.getPermSettings().getAllowedToAcceptAlly().hasPerm(r)) {
            Sender.sendMessage("Error, you do not have permission to send ally requests or accept them!");
            return;
        }

//        if (Args[1].equalsIgnoreCase("accept")) {
//            if (Main.FFactory.allyrequest.containsKey(fac.getName())) {
//                Faction tf = Main.FFactory.getFaction(Main.FFactory.allyrequest.get(fac.getName()));
//                if (tf == null) {
//                    Sender.sendMessage(TextFormat.RED + "ERROR 5556! Try again!");
//                    Main.FFactory.allyrequest.remove(fac.getName());
//                    return;
//                }
//                fac.AddAlly(tf.getName());
//                tf.AddAlly(fac.getName());
//                fac.BroadcastMessage(TextFormat.AQUA + "[ArchFactions] Your faction is now allied with " + tf.GetDisplayName());
//                tf.BroadcastMessage(TextFormat.AQUA + "[ArchFactions] Your faction is now allied with " + fac.GetDisplayName());
//                return;
//            } else {
//                Sender.sendMessage(TextFormat.RED + " No ally request exists for your faction!");
//                return;
//            }
//        } else if (Args[1].equalsIgnoreCase("deny")) {
//            if (Main.FFactory.allyrequest.containsKey(fac.getName())) {
//                Main.FFactory.allyrequest.remove(fac.getName());
//                return;
//            } else {
//                Sender.sendMessage(TextFormat.RED + " No ally request exists for your faction!");
//                return;
//            }
//        }

        Faction target = Main.FFactory.getFaction(Args[1]);
        if (target == null) {
            target = Main.FFactory.getFaction(Main.FFactory.factionPartialName(Args[1]));
            if (target == null) {
                Sender.sendMessage(TextFormat.RED + "Error the faction containing '" + Args[1] + "' could not be found!");
                return;
            }
        }

//        target.AddAllyRequest(fac);
        int to = Main.GetIntTime() + 60 * 60 * 3;
        target.AddAllyRequest(fac, Sender, to);//3 Day Time out
    }
}