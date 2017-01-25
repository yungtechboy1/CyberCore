package net.yungtechboy1.CyberCore.Commands.Homes;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
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
                new CommandParameter("key", CommandParameter.ARG_TYPE_RAW_TEXT, true)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length != 1 || !(sender instanceof Player)) return false;
        Player p = (Player) sender;
        Owner.HomeFactory.DelPlayerHome(p, args[0]);
        return true;
    }
}