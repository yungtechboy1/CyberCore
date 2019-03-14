package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.Calendar;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Promote extends Commands {

    public static final int RECRUIT = 1;
    public static final int MEMBER = 2;
    public static final int OFFICER = 3;
    public static final int GENERAL = 4;
    public static final int LEADER = 5;

    public Promote(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f Promote <player>", m);
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
            SendUseage();
            return;
        }
        Player pp = Main.getServer().getPlayer(Args[1]);
        if (pp == null){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Player Is Not Online!");;
            return;
        }
        String ppn = pp.getName();
        if (!fac.IsInFaction(ppn)) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Target Player Not In Your Faction!");
            return;
        }
        //Perms System
        Integer perm = fac.GetPlayerPerm(Sender.getName());
        Integer fromperm = fac.GetPlayerPerm(ppn);

        if(perm >= MEMBER && fromperm < RECRUIT){
            fac.DelRecruit(Sender.getName());
            fac.AddMember(Sender.getName());
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+ppn+" Has been Promoted to Member");
            pp.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"You have been promoted to Member");
        }else if(perm >= OFFICER && fromperm < MEMBER){
            fac.DelMember(Sender.getName());
            fac.AddOfficer(Sender.getName());
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+ppn+" Has been Promoted to Officer");
            pp.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"You have been promoted to Officer");
        }else if(perm >= GENERAL && fromperm < OFFICER){
            fac.DelOfficer(Sender.getName());
            fac.AddGeneral(Sender.getName());
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+ppn+" Has been Promoted to General");
            pp.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"You have been promoted to General");
        }else if(perm >= LEADER && fromperm < GENERAL){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+ppn+" They can not be promoted any more! Please make them leader by using /f leader");
        }else{
            if(perm == RECRUIT){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be Higher than a Recruit to Promote this Person!");
            }else if(perm == MEMBER){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be Higher than a Member to Promote this Person!");
            }else if(perm == OFFICER){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be Higher than a Officer to Promote this Person!");
            }else if(perm == GENERAL){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must be Higher than a General to Promote this Person!");
            }
            }
//        }
//        Main.CC.Setnametag((Player) Sender);
//        Main.CC.Setnametag(pp);
    }
}