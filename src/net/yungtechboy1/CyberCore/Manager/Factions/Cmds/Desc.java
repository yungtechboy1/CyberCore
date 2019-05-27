package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Desc extends Commands {

    public Desc(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f desc <Description>",m);
        senderMustBePlayer = true;
        senderMustBeOfficer = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        //@todo
        if(Args.length < 2){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GRAY+"Usage /f desc <Description>");
            return;
        }
        String desc = "";
        int a = 0;
        for (String c : Args) {
            a++;
            if (a == 1) continue;
            desc += c + " ";
        }
        fac.SetDesc(desc);
        Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+" Faction description changed!");
    }
}
