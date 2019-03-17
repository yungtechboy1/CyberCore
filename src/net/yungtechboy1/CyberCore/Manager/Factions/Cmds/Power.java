package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Power extends Commands {


    public Power(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f Power", m);
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
        Sender.sendMessage(FactionsMain.NAME+TextFormat.LIGHT_PURPLE + "Your Faction Has " + fac.GetPower());
    }
}