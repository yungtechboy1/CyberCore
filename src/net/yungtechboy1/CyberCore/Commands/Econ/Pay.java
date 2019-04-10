package net.yungtechboy1.CyberCore.Commands.Econ;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Pay extends Command {

    private CyberCoreMain plugin;

    public Pay(CyberCoreMain plugin) {
        super("name");
        String[] aliases = {"venmo", "deposit"};
        this.setAliases(aliases);
        this.setUsage("&l&7Usage: /pay <player> <amount>");
        this.setDescription(getUsage());
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(commandSender.isPlayer()) {
            if(args.length == 2) {
                String to = args[0].toLowerCase();
                if(plugin.isOnline(to)) {
                    Integer amount = Integer.valueOf(args[1]);
                    plugin.getCorePlayer((Player) commandSender).subtractMoney(Integer.valueOf(args[1]));
                    plugin.getCorePlayer(to).addMoney(Integer.valueOf(args[1]));
                    commandSender.sendMessage(plugin.colorize("&eYou sent {player} &2$"+amount+"", plugin.getPlayer(to).getName()));
                }
            } else {
                commandSender.sendMessage(plugin.colorize(getUsage()));
            }
            Integer money = plugin.getCorePlayer((Player) commandSender).money;
            commandSender.sendMessage(plugin.colorize("&eBalance &7: &2$"+money));
        }
        return false;
    }
}
