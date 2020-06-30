package net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

public class FactionTestCommand extends FactionCommand {
    public FactionTestCommand(CyberCoreMain main) {
        super("test", "TEST COMMAND DESC", "/factest test <name> ", main);
        commandParameters.put("default",
                new CommandParameter[]{
                        new CommandParameter("text", CommandParamType.STRING, false)
                });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!super.execute(sender, commandLabel, args))return false;
        sender.sendMessage("HEYTYYYY");
        sender.sendMessage("HEYTYYYY"+args[0]);
        return true;
    }
}
