package net.yungtechboy1.CyberCore.Commands.Econ;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Pay extends Command {

    private CyberCoreMain plugin;

    public Pay(CyberCoreMain plugin) {
        super("pay");
        this.plugin = plugin;
        String[] aliases = {"venmo", "deposit"};
        this.setAliases(aliases);
        this.setUsage(plugin.colorize("&o&7Usage: /pay <player> <amount>"));
        this.setDescription(getUsage());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(commandSender.isPlayer()) {
            Integer money = plugin.getCorePlayer((Player) commandSender).money;
            Integer amount;
            if(args.length == 2) {
                try{
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException ex) {
                    amount = 0;
                }
                String to = args[0].toLowerCase();
                if(plugin.isOnline(to)) {
                    if(!plugin.getPlayer(to).getName().equalsIgnoreCase(commandSender.getName())) {
                        amount = Integer.valueOf(args[1]);
                        if (money - amount >= 0) {
                            plugin.getCorePlayer((Player) commandSender).subtractMoney(amount);
                            plugin.getCorePlayer(to).addMoney(amount);
                            commandSender.sendMessage(plugin.colorize("&eYou sent {player} &2$" + amount + "", plugin.getPlayer(to)));
                        } else {
                            commandSender.sendMessage(plugin.colorize("&eSorry, you're too broke to do that!"));
                        }
                    } else {
                        commandSender.sendMessage(plugin.colorize("&eSorry, you can't send money to yourself!"));
                    }
                }
            } else {
                commandSender.sendMessage(plugin.colorize(getUsage()));
            }
            commandSender.sendMessage(plugin.colorize("&eBalance &7: &2$"+money));
        }
        return false;
    }
}
