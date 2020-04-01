package net.yungtechboy1.CyberCore.Manager.Factions.ServerCmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 12/5/2016.
 */
public class FClaim extends CommandBase{
    public FClaim(FactionsMain main){
        super("fclaim","DESC","USEAGE",main);
        commandParameters.clear();
        this.commandParameters.put("default",
                new CommandParameter[]{
                        new CommandParameter("Radius", CommandParamType.INT, true)
                });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
//        Claim cmd = new Claim(commandSender,strings,main);
//        cmd.run();
        return true;
    }
}
