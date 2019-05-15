package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Settings extends Commands {

    public Settings(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f settings",m);
        senderMustBePlayer = true;
        senderMustBe = FactionRank.Recruit;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        FactionRank nr = fac.getSettings().getAllowedToEditSettings();
        FactionRank pr = fac.getPlayerRank(Sender);
        if(pr.HasPerm(nr)){
            
        }

        //@todo
        if(Args.length < 2){
            SendUseage();
            return;
        }

        String desc = "";
        int a = 0;
        for (String c : Args) {
            a++;
            if (a == 1) continue;
            desc += c + " ";
        }
        fac.SetMOTD(desc);
        Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+" Faction MOTD changed!");
    }
}
