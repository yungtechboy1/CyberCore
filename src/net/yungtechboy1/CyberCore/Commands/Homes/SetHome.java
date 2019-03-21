package net.yungtechboy1.CyberCore.Commands.Homes;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class SetHome extends Command {
    CyberCoreMain Owner;

    public SetHome(CyberCoreMain server) {
        super("sethome", "Set Your Home", "/sethome <key>");
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
        Owner.HomeFactory.AddPlayerHome(p, args[0]);
        return true;
    }
}