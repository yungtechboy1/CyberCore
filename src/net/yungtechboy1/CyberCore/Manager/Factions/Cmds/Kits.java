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
public class Kits extends Commands {

    public Kits(CommandSender s, String[] a, FactionsMain m){
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
