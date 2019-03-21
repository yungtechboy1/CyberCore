package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Accept extends Commands {

    public Accept(CommandSender s, String[] a, FactionsMain m) {
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
        if (Main.FFactory.InvList.containsKey(Sender.getName().toLowerCase())) {
            Faction FF = Main.FFactory.getFaction(Main.FFactory.InvList.get(Sender.getName().toLowerCase()));
            if(FF == null){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"That faction no longer exists!");
                Main.FFactory.InvList.remove(Sender.getName().toLowerCase());
                return;
            }
            if (FF.HasInvite(Sender.getName().toLowerCase()) && FF.AcceptInvite(Sender.getName().toLowerCase())) {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Faction Invite Accepted!");
                FF.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN + player + " Has joined your faction!");
//                Main.CC.Setnametag(Sender.getName());
                Main.FFactory.FacList.put(Sender.getName().toLowerCase(), FF.GetName());
            } else {
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + " Invite has expired or their was an error! Please try again!");
            }
        }else{
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error You have no invites!");
        }
    }
}