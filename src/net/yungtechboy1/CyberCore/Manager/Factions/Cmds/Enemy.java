package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 12/10/2016.
 */
public class Enemy extends Commands {

    public Enemy(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f enemy <fac>", m);
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
            Sender.sendMessage(TextFormat.RED + "[ArchFactions] Usage: /f enemy <fac>");
            return;
        }

        Faction target = Main.FFactory.getFaction(Args[1]);
        if (target == null) {
            Sender.sendMessage(TextFormat.RED+"Error the faction containing '" + Args[1] + "' could not be found!");
            return;
        }

        fac.AddEnemy(target.GetName());
        target.BroadcastMessage(TextFormat.AQUA+"[ArchFactions] "+fac.GetDisplayName()+" Has added you as an enemy!");
        Sender.sendMessage(TextFormat.AQUA+"[ArchFactions] "+target.GetDisplayName()+" is now an enemy");
    }
}