package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Kick extends Commands {

    public static final int RECRUIT = 1;
    public static final int MEMBER = 2;
    public static final int OFFICER = 3;
    public static final int GENERAL = 4;
    public static final int LEADER = 5;

    public Kick(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f Kick <player>", m);
        senderMustBeInFaction = true;
        senderMustBeMember = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (Args.length < 2) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Usage /f kick <player>");
            return;
        }
        Player pp = Main.getServer().getPlayer(Args[1]);
        if (pp == null) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Player Is Not Online or Does Not Exist!");
            return;
        }
        String ppn = pp.getName();
        Faction ofaction = Main.FFactory.getPlayerFaction(pp);
        if (ofaction == null) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Player Not In Faction!");
            return;
        }
        String fn = fac.GetName();
        if (!ofaction.GetName().equalsIgnoreCase(fn)) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Player is not in this faction!");
            return;
        }
        //Perms System
        Integer perm = fac.GetPlayerPerm(Sender.getName());
        Integer ppnperm = fac.GetPlayerPerm(ppn);

        if (perm > ppnperm) {
            if (ppnperm == LEADER) {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "You can not kick your leader!");
                return;
            } else if (ppnperm == GENERAL) {
                fac.DelGeneral(ppn);
            } else if (ppnperm == OFFICER) {
                fac.DelOfficer(ppn);
            } else if (ppnperm == MEMBER) {
                fac.DelMember(ppn);
            } else if (ppnperm == RECRUIT) {
                fac.DelRecruit(ppn);
            }
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "You successfully kicked " + ppn + "!");
            pp.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "You Have Been Kicked From factionName!!!");
            Main.FFactory.FacList.remove(ppn);
            fac.TakePower(2);
//            Main.CC.Setnametag(ppn);
//            Main.sendBossBar(pp);

        } else if (perm == ppnperm) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.YELLOW + "You can not kick those who are the same rank as you!");
        } else if (perm < ppnperm) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "You can not kick those who are a higher rank than you!");
        }
    }
}