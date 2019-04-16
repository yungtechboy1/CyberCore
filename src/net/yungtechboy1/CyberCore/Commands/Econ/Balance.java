package net.yungtechboy1.CyberCore.Commands.Econ;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Balance extends Command {

    private CyberCoreMain plugin;

    public Balance(CyberCoreMain plugin) {
        super("balance");
        this.plugin = plugin;
        String[] aliases = {"bal", "money", "credit", "coins", "pesos", "bank"};
        this.setAliases(aliases);
        this.setUsage(plugin.colorize("&o&7Usage: /bal [player]"));
        this.setDescription(getUsage());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(args.length == 1) {
            String to = args[0];
            if(plugin.isOnline(to)) {
                Integer bal = plugin.getCorePlayer(to).money;
                commandSender.sendMessage(plugin.colorize("&7{player}&e Balance : &2$"+bal, plugin.getPlayer(to)));
            }
        }
        if(commandSender.isPlayer()) {
            Integer money = plugin.getCorePlayer((Player) commandSender).money;
            commandSender.sendMessage(plugin.colorize("&eBalance &7: &2$"+money));
        }
        return false;
    }
}
