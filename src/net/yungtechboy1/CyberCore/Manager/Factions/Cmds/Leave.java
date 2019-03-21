package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Leave extends Commands {

    public Leave(CommandSender s, String[] a, FactionsMain m) {
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

            if(fac.IsMember( Sender.getName()))fac.DelMember(Sender.getName());
            if(fac.IsOfficer( Sender.getName()))fac.DelOfficer(Sender.getName());
            if(fac.IsGeneral( Sender.getName()))fac.DelGeneral(Sender.getName());
            if(fac.IsRecruit( Sender.getName()))fac.DelRecruit(Sender.getName());

            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "You successfully left faction");
            fac.TakePower(1);
            String lm = "";
            if(Args.length >= 1){
                lm = "And has left with the following Message:"+TextFormat.AQUA;
                for(String a:Args){
                    lm = lm + " " + a;
                }
            }
            fac.BroadcastMessage(FactionsMain.NAME+TextFormat.YELLOW+Sender.getName()+" has Left the Faction!"+lm);
//            if(Main.CC != null)Main.CC.Setnametag((Player)Sender);
//            Main.CC.Setnametag((Player) Sender);
//            Main.sendBossBar((Player) Sender);
            Main.FFactory.FacList.remove(Sender.getName().toLowerCase());
        } else {
            Sender.sendMessage(FactionsMain.NAME+"You are the leader of the faction... Please Do `/f del` if you wish to leave or pass leadership on to someone else!");
        }
    }
}