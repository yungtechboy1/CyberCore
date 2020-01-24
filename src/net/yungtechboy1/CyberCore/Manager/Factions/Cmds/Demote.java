package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Demote extends Commands {

//    public static final int RECRUIT = 1;
//    public static final int MEMBER = 2;
//    public static final int OFFICER = 3;
//    public static final int GENERAL = 4;
//    public static final int LEADER = 5;

    public Demote(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f Demote <player>", m);
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
        if(Args.length < 2){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Usage /f demote <player>");
            return;
        }
        Player pp = Main.getServer().getPlayer(Args[1]);
        if (pp == null){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Player Is Not Online!");;
            return;
        }
        String ppn = pp.getName();
        if (!Main.isInFaction(ppn)) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Target Player Not In Your Faction!");
            return;
        }

        FactionRank r = fac.getPlayerRank((Player)Sender);
        FactionRank fr = fac.getPermSettings().getAllowedToPromote();
        if (r.hasPerm(fr)) {
            fac.DemotePlayer(pp);
        }

//
//        //Perms System
//        Integer perm = fac.GetPlayerPerm(Sender.getName());
//        Integer fromperm = fac.GetPlayerPerm(ppn);
//
//        if(perm > GENERAL && fromperm > GENERAL){
//            fac.AddOfficer(Sender.getName());
//            fac.DelGeneral(Sender.getName());
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+ppn+" Has been Promoted to General");
//            pp.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"You have been promoted to General");
//        }else if(perm > OFFICER && fromperm > OFFICER){
//            fac.AddMember(Sender.getName());
//            fac.DelOfficer(Sender.getName());
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+ppn+" Has been Promoted to Officer");
//            pp.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"You have been promoted to Officer");
//        }else if(perm > MEMBER && fromperm > MEMBER){
//            fac.AddRecruit(Sender.getName());
//            fac.DelMember(Sender.getName());
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+ppn+" Has been Promoted to Member");
//            pp.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"You have been promoted to Member");
//        }else if(fromperm == LEADER){
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+" You can not Demote the Leader!");
//        }else{
//            if(perm == RECRUIT){
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be Higher than a Recruit to Demote this Person!");
//            }else if(perm == MEMBER){
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be Higher than a Member to Demote this Person!");
//            }else if(perm == OFFICER){
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be Higher than a Officer to Demote this Person!");
//            }else if(perm == GENERAL){
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be Higher than a General to Demote this Person!");
//            }
//        }
//        Main.CC.Setnametag((Player) Sender);
//        Main.CC.Setnametag(pp);
    }
}