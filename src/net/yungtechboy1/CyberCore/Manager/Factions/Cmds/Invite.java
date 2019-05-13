package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

import com.sun.org.apache.xpath.internal.Arg;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;
import java.util.Calendar;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.*;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Invite extends Commands {


    public Invite(CommandSender s, String[] a, FactionsMain m) {
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
        if (Args.length == 2) {
            SendUseage();
            return;
        }
        if (fac == null) {
            Sender.sendMessage(Error_NotInFaction.getMsg());
            return;
        }
        if (fac.GetNumberOfPlayers() >= fac.GetMaxPlayers()) {
            Sender.sendMessage(Error_FactionFull.getMsg());//+TextFormat.RED+"Or pay to upgrade your faction limit!");
            return;
        }


        CorePlayer invited = (CorePlayer) Main.getServer().getPlayerExact(Args[1]);
        if (invited == null) {
            ArrayList<Player> l = CyberCoreMain.getInstance().getAllPlayerNamesCloseTo(Args[1]);
            if (l.size() == 0) {
                Sender.sendMessage(Error_UnableToFindPlayer.getMsg());
                return;
            } else if (l.size() == 1) {
                invited = (CorePlayer) l.get(0);
            } else {
                FormWindowSimple FWM = new FormWindowSimple("CyberFactions | Invite Player", "");
                int k = 0;
                FWM.addButton(new ElementButton("Grinch!"));
                for(Player p: l){
                    k++;
                    if(k > 20)break;
                    FWM.addButton(new ElementButton(p.getName()));
                }

                CorePlayer cp = (CorePlayer) CyberCoreMain.getInstance().getServer().getPlayerExact(Sender.getName());
                cp.showFormWindow(FWM);
                cp.LastSentFormType = FormType.MainForm.Faction_Invite_Choose;

            }
        }


        if (invited == null) {
            Sender.sendMessage(Error_UnableToFindPlayer.getMsg());
            return;
        }
        if (null == Main.FFactory.getPlayerFaction(Sender)) {
            //TODO Allow Setting to ignore Faction messages
            Sender.sendMessage(Error_PlayerInFaction.getMsg());
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

        Integer time = Main.GetIntTime()+ 60 * 5;
        fac.AddInvite(invited.getName().toLowerCase(), time);
//        Main.FFactory.InvList.put(invited.getName().toLowerCase(), fac.GetName());

        Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Successfully invited " + invited.getName() + "!");
        invited.sendMessage(FactionsMain.NAME + TextFormat.YELLOW + "You have been invited to faction.\n" + TextFormat.GREEN + "Type '/f accept' or '/f deny' into chat to accept or deny!");

        invited.FactionInvite = fac.GetName();
        invited.FactionInviteTimeout = time;

        Main.PlayerInvitedToFaction(invited,fac);
    }
}