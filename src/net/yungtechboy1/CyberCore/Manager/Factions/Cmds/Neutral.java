package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 12/11/2016.
 */
public class Neutral extends Commands {
    public Neutral(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f neutral <fac>", m);
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
            Sender.sendMessage(TextFormat.RED + "[ArchFactions] Usage: /f neutral <fac>");
            return;
        }

        Faction target = Main.FFactory.getFaction(Args[1]);
        if (target == null) {
            Sender.sendMessage(TextFormat.RED+"Error the faction containing '" + Args[1] + "' could not be found!");
            return;
        }

        if (fac.isEnemy(target.getName())) {
            fac.RemoveEnemy(target,Sender);
            target.BroadcastMessage(TextFormat.AQUA + "[ArchFactions] " + fac.getDisplayName() + " Has removed you as an enemy!");
            Sender.sendMessage(TextFormat.AQUA + "[ArchFactions] " + target.getDisplayName() + " is not an enemy");
        } else if (fac.isAllied(target.getName())) {
            fac.RemoveAlly(target.getName());
            target.BroadcastMessage(TextFormat.AQUA + "[ArchFactions] " + fac.getDisplayName() + " Has added you as an ally!");
            Sender.sendMessage(TextFormat.AQUA + "[ArchFactions] " + target.getDisplayName() + " is not an ally");
        }else{
            Sender.sendMessage(TextFormat.RED+"Error! That faction is not an enemy or ally!");
        }

    }
}