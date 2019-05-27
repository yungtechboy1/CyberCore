package net.yungtechboy1.CyberCore.Commands.Homes;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class DelHome extends Command {
    CyberCoreMain Owner;

    public DelHome(CyberCoreMain server) {
        super("delhome", "Delete Your Home", "/delhome <key>");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("key", CommandParamType.RAWTEXT, true)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length != 1 || !(sender instanceof Player)) return false;
        CorePlayer p = (CorePlayer) sender;
        if (!p.CheckHomeKey(args[0])) {
            p.sendMessage("Error! You don't have a home named '" + args[0] + "'!");
            return true;
        }
        p.AddHome(args[0]);
        p.sendMessage("Success! Home saved as '" + args[0] + "'!");
        return true;
    }
}