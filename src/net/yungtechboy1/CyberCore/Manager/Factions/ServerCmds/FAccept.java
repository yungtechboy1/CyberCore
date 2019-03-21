package net.yungtechboy1.CyberCore.Manager.Factions.ServerCmds;

import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Accept;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 12/5/2016.
 */
public class FAccept extends CommandBase{
    public FAccept(FactionsMain main){
        super("faccept","DESC","USEAGE",main);
        commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Accept cmd = new Accept(commandSender,strings,main);
        cmd.run();
        return true;
    }
}
