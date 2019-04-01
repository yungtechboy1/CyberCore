package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Withdraw extends Commands {

    public Withdraw(CommandSender s, String[] a, FactionsMain m){
        super(s,a,"/f withdraw <amount>",m);
        senderMustBePlayer = true;
        senderMustBeGeneral = true;
        sendUsageOnFail = true;
        senderMustBeInFaction = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        if(Args.length < 2){
            SendUseage();
            return;
        }
        Integer money = Integer.parseInt(Args[1]);
        if(fac.GetMoney() < money){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your faction doesn't have $"+money+" Money!");
            return;
        }
        fac.TakeMoney(money);
        Main.plugin.GetEcon().AddMoney(Sender.getName(),money);
        Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"$"+money+" Money taken from your Faction!");
    }
}
