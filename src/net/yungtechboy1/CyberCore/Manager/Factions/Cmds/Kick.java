package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionKickListWindow;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Kick extends Commands {

    public static final int RECRUIT = 1;
    public static final int MEMBER = 2;
    public static final int OFFICER = 3;
    public static final int GENERAL = 4;
    public static final int LEADER = 5;

    public Kick(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f Kick <player>", m);
        senderMustBeInFaction = true;
        //ignore
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
        FactionRank perm = fac.getPermSettings().getAllowedToKick();
        FactionRank fr = fac.getPlayerRank(Sender);
        if (fr == null || !fr.hasPerm(perm)) {
            Sender.sendMessage("ERror you dont have perms to kick players!");
            return;
        }

        if(Args.length == 1){
            CorePlayer pp = (CorePlayer)Main.getServer().getPlayer(Args[0]);
            if (pp == null) {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Player Is Not Online or Does Not Exist!");
                return;
            }

            String ppn = pp.getName();
            Faction ofaction = Main.FFactory.getFaction(pp.Faction);
            if (ofaction == null) {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Player Not In Faction!");
                return;
            }
            String fn = fac.getName();
            if (!ofaction.getName().equalsIgnoreCase(fn)) {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Player is not in this faction!");
                return;
            }

            fac.KickPlayer(pp);
        }else {
            ArrayList<String> af = new ArrayList<>(fac.PlayerRanks.keySet());
            Sender.showFormWindow(new FactionKickListWindow(af));
//            Sender.LastSentFormType = FormType.MainForm.Faction_Kick_List;

        }


    }
}