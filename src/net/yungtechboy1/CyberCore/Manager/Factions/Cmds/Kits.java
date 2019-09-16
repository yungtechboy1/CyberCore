package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Kits extends Commands {

    public Kits(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f kist ",m);
        senderMustBePlayer = true;
        senderMustBeMember = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    //@TODO
    @Override
    public void RunCommand(){
        return;
    }
}
