package net.yungtechboy1.CyberCore.Manager.Factions.ServerCmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 12/5/2016.
 */
public class FCreate extends CommandBase{
    public FCreate(FactionsMain main){
        super("fcreate","DESC","USEAGE",main);
        commandParameters.clear();
        this.commandParameters.put("default",
                new CommandParameter[]{
                        new CommandParameter("Name", CommandParamType.RAWTEXT, true)
                });
        description = "asdasd";
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
//        Create cmd = new Create(commandSender,strings,main);
//        cmd.run();
        return true;
    }
}
