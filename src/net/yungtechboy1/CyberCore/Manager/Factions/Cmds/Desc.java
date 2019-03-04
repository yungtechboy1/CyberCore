package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Desc extends Commands {

    public Desc(CommandSender s, String[] a, FactionsMain m){
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
        String desc = GetStringAtArgs(1,"A ArchMCPE Faction!");
        fac.SetDesc(desc);
        Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+" Faction description changed!");
    }
}
