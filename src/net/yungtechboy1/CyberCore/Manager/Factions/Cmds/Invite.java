package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.Calendar;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Invite extends Commands {


    public Invite(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f Invite <player>", m);
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
        if(Args.length != 2) {
            SendUseage();
            return;
        }
        if(fac == null) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Not in a faction!");
            return;
        }
        if(fac.GetNumberOfPlayers() >= fac.GetMaxPlayers()) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Faction is full. Please kick players to make room.\n");//+TextFormat.RED+"Or pay to upgrade your faction limit!");
            return;
        }

        Player invited = Main.getServer().getPlayer(Args[1]);
        if(invited == null) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"No Player By That Name Is Online!");
            return;
        }
        if(null == Main.FFactory.getPlayerFaction(Sender)) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Player is currently in a faction");
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

        Integer time = (int)(Calendar.getInstance().getTime().getTime()/1000)+60*5;
        fac.AddInvite(invited.getName().toLowerCase(),time);
        Main.FFactory.InvList.put(invited.getName().toLowerCase(),fac.GetName());

        Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"Successfully invited "+invited.getName()+"!");
        invited.sendMessage(FactionsMain.NAME+TextFormat.YELLOW+"You have been invited to faction.\n"+TextFormat.GREEN+"Type '/f accept' or '/f deny' into chat to accept or deny!");
    }
}