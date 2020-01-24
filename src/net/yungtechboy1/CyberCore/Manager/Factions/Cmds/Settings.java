package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionErrorString;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Faction.FactionSettingsWindow;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Settings extends Commands {

    public Settings(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f settings",m);
        senderMustBePlayer = true;
//        senderMustBe = FactionRank.Recruit;
        senderMustBeInFaction = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        FactionRank nr = fac.getPermSettings().getAllowedToEditSettings();
        FactionRank pr = fac.getPlayerRank(Sender);
        if (pr.hasPerm(nr)) {
            Sender.showFormWindow(new FactionSettingsWindow(Sender.getFaction()));
        } else {
            Sender.sendMessage(FactionErrorString.Error_Settings_No_Permission.getMsg());
        }
    }
}
