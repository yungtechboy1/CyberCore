package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Leader extends Commands {


    public Leader(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f leader <player>", m);
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
        if(fac.Leader.equalsIgnoreCase(Sender.getName())) {
            if(Args.length <= 1){
                SendUseage();
                return;
            }
            Player pp = Main.getServer().getPlayer(Args[1]);
            if (pp != null){
                String ppn = pp.getName();
                if(Main.getPlayerFaction(Sender.getName()).equalsIgnoreCase(Main.getPlayerFaction(ppn))) {
                    Integer r = fac.GetPlayerPerm(ppn);
                    if(r == 0)fac.DelRecruit(ppn);
                    if(r == 1)fac.DelMember(ppn);
                    if(r == 2)fac.DelOfficer(ppn);
                    if(r == 3)fac.DelGeneral(ppn);
                    fac.SetLeader(ppn.toLowerCase());
                    fac.AddMember(Sender.getName());
                    fac.BroadcastMessage(FactionsMain.NAME+TextFormat.YELLOW+""+ppn+" Is your New Leader!");
                    Sender.sendMessage(FactionsMain.NAME+TextFormat.YELLOW+"You are no longer leader!");
//                    Main.CC.Setnametag((Player) Sender);
//                    Main.CC.Setnametag(pp);
                    pp.sendMessage(FactionsMain.NAME+TextFormat.YELLOW+"You are now leader of factionName!");
                } else {
                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Add player to faction first!");
                }
            } else {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Player Not Online or Found!");
            }
        } else {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be leader to use this");
        }
    }
}