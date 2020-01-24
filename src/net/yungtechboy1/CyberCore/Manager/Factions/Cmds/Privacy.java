package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Privacy extends Commands {


    public Privacy(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f privacy <on/off>",m);
        senderMustBeInFaction = true;
        senderMustBeGeneral = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        if(Args.length == 2) {
            if (Args[1].equalsIgnoreCase("on")) {
                fac.getSettings().setPrivacy(1);
                Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Faction Privacy is Now On!");
            } else if (Args[1].equalsIgnoreCase("off")) {
                Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Faction Privacy is Now Off!");
                fac.getSettings().setPrivacy(0);
            } else {
                SendUseage();
                return;
            }
        }
    }
}
