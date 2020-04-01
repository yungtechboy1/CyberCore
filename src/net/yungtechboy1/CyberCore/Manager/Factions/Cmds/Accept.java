package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
@Deprecated
public class Accept extends Commands {

    public Accept(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f Accept", m);
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        String player = Sender.getName();
        CorePlayer cp = (CorePlayer) Main.getServer().getPlayerExact(player);
        if(cp == null)return;
        if (cp.FactionInvite != null) {
            Faction FF = Main.FFactory.getFaction(cp.FactionInvite);
            if(FF == null){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"That faction no longer exists!");
                cp.ClearFactionInvite();
                return;
            }
            if (FF.AcceptInvite(Sender)) {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Faction Invite Accepted!");
//                Main.CC.Setnametag(Sender.getName());
            } else {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + " Invite has expired or their was an error! Please try again!");
            }
        }else{
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error You have no invites!");
        }
    }
}