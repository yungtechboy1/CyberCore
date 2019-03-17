package net.yungtechboy1.CyberCore.Manager.Factions.ServerCmds;

import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Chat;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;


/**
 * Created by carlt_000 on 12/5/2016.
 */
public class FDelete extends CommandBase{
    public FDelete(FactionsMain main){
        super("fdelete","DESC","USEAGE",main);
        commandParameters.clear();
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
