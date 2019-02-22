package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 12/8/2016.
 */
public class Ally extends Commands {

    public Ally(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f ally <fac>", m);
        senderMustBeInFaction = true;
        senderMustBeLeader = true;
        senderMustBeGeneral = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (Args.length <= 1) {
            Sender.sendMessage(TextFormat.RED + "[ArchFactions] Usage: /f ally <fac>");
            return;
        }

        if (Args[1].equalsIgnoreCase("accept")){
            if (Main.FFactory.allyrequest.containsKey(fac.GetName())) {
                Faction tf = Main.FFactory.getFaction(Main.FFactory.allyrequest.get(fac.GetName()));
                if (tf == null){
                    Sender.sendMessage(TextFormat.RED+"ERROR 5556! Try again!");
                    Main.FFactory.allyrequest.remove(fac.GetName());
                    return;
                }
                fac.AddAlly(tf.GetName());
                tf.AddAlly(fac.GetName());
                fac.BroadcastMessage(TextFormat.AQUA+"[ArchFactions] Your faction is now allied with "+tf.GetDisplayName());
                tf.BroadcastMessage(TextFormat.AQUA+"[ArchFactions] Your faction is now allied with "+fac.GetDisplayName());
                return;
            }else{
                Sender.sendMessage(TextFormat.RED+" No ally request exists for your faction!");
                return;
            }
        }else if(Args[1].equalsIgnoreCase("deny")){
            if (Main.FFactory.allyrequest.containsKey(fac.GetName())) {
                Main.FFactory.allyrequest.remove(fac.GetName());
                return;
            }else{
                Sender.sendMessage(TextFormat.RED+" No ally request exists for your faction!");
                return;
            }
        }

        Faction target = Main.FFactory.getFaction(Args[1]);
        if (target == null) {
            Sender.sendMessage(TextFormat.RED+"Error the faction containing '" + Args[1] + "' could not be found!");
            return;
        }

        target.BroadcastMessage(TextFormat.AQUA+"[ArchFactions] "+fac.GetDisplayName()+" wants to be Ally's with you!");
        target.BroadcastMessage(TextFormat.AQUA+"[ArchFactions] Use `/f ally accept` or `/f ally deny` to respond");
        Main.FFactory.allyrequest.put(target.GetName(),fac.GetName());
        Sender.sendMessage(TextFormat.AQUA+"[ArchFactions] Ally request sent to "+target.GetDisplayName());
    }
}