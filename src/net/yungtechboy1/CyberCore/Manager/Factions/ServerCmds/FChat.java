package net.yungtechboy1.CyberCore.Manager.Factions.ServerCmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Chat;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 12/5/2016.
 */
public class FChat extends CommandBase{
    public FChat(FactionsMain main){
        super("fchat","DESC","USEAGE",main);
        commandParameters.clear();
        this.commandParameters.put("default",
            new CommandParameter[]{
                    new CommandParameter("message", CommandParamType.RAWTEXT, false)
            });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        String[] b = new String[strings.length];
        int c = 0;
        for(String a: strings){
            b[c] = a;
            c++;
        }
        Chat cmd = new Chat(commandSender,b,main);
        cmd.run();
        return true;
    }
}
