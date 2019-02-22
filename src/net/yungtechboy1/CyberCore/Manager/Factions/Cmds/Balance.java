package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Balance extends Commands {

    public Balance(CommandSender s, String[] a, FactionsMain m){
        super(s,a,"/f balance",m);
        senderMustBePlayer = true;
        senderMustBeInFaction = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        Integer money = fac.GetMoney();
        Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"Your Faction has "+TextFormat.AQUA+money);
        fac.UpdateTopResults();
    }
}
