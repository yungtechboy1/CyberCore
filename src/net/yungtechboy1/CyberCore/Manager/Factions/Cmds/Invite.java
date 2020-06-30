package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;


import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionInviteChooseRank;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionInviteChooseWindow;

import java.util.ArrayList;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionErrorString.*;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Invite extends Commands {


    public Invite(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f Invite <player> or /f invite", m);
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
        if (fac == null) {
            Sender.sendMessage(Error_NotInFaction.getMsg());
            return;
        }
        if (fac.GetNumberOfPlayers() >= fac.GetMaxPlayers()) {
            Sender.sendMessage(Error_FactionFull.getMsg());//+TextFormat.RED+"Or pay to upgrade your faction limit!");
            return;
        }

        FactionRank r = fac.getPlayerRank(Sender);
        if (r != null) {
            if (!r.hasPerm(fac.getPermSettings().getAllowedToInvite())) {
                Sender.sendMessage(TextFormat.RED+"Error! You dont have permission to Invite players at your current faction rank! Your faction only allows " + fac.getPermSettings().getAllowedToInvite().getName() + "s to invite players!");
                return;
            }
        }

        //f invite Yung
        if (Args.length == 2) {
            //Try to get exact Player
            CorePlayer invited = (CorePlayer) Main.getServer().getPlayerExact(Args[1]);
            if (invited == null) {
                //Get players with close names and then display
                ArrayList<Player> l = CyberCoreMain.getInstance().getAllPlayerNamesCloseTo(Args[1]);
                if (l.size() == 0) {
                    Sender.sendMessage(Error_CMD_Invite_UnableToFindPlayer.getMsg());
                    return;
                } else if (l.size() == 1) {
                    invited = (CorePlayer) l.get(0);
                } else {
                    Sender.showFormWindow(new FactionInviteChooseWindow(l));
                    return;
                }
            }


            if (invited == null) {
                Sender.sendMessage(Error_CMD_Invite_UnableToFindPlayer.getMsg() + "!@ SUPER ERROR!!!@@@");
                return;
            }
            if (null != Main.FFactory.getPlayerFaction(invited)) {
                //TODO Allow Setting to ignore Faction messages
                Sender.sendMessage(Error_CMD_Invite_PlayerInFaction.getMsg());
                return;
            }
            //PERMS
        /*Integer perm = fac.GetPerm(4);
        if(perm < fac.GetPlayerPerm(Sender.getName())){
            if(perm == 1)Sender.sendMessage(TextFormat.RED+"Only Members and above may invite!");
            if(perm == 2)Sender.sendMessage(TextFormat.RED+"Only Officers and above may invite!");
            if(perm == 3)Sender.sendMessage(TextFormat.RED+"Only Generals and above may invite!");
            if(perm == 4)Sender.sendMessage(TextFormat.RED+"Only your Leader may invite!");
            return;
        }*/

//        Main.FFactory.InvList.put(invited.getName().toLowerCase(), fac.getName());
            Sender.showFormWindow(new FactionInviteChooseRank(Sender, invited));
//            Main.PlayerInvitedToFaction(invited, Sender, fac);
        } else {
            Sender.sendMessage(Error_CMD_Invite_No_Player_Entered.getMsg());
        }
    }
}