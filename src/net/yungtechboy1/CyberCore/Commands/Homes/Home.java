package net.yungtechboy1.CyberCore.Commands.Homes;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Tasks.TPToHome;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.yungtechboy1.CyberCore.CyberCoreMain.Prefix;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class Home extends Command {
    CyberCoreMain Owner;

    public Home(CyberCoreMain server) {
        super("home", "Home", "/home [key | list]");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("key | list", CommandParameter.ARG_TYPE_RAW_TEXT, true)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length != 1 || !(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if(args[0].equalsIgnoreCase("list")){
            String a = "";
            a += TextFormat.GRAY+"========================"+TextFormat.RESET+"\n";
            LinkedHashMap<String, String> v = (LinkedHashMap<String, String>)Owner.HomeFactory.homes.get(sender.getName().toLowerCase(), new LinkedHashMap<String, String>());
            for(Map.Entry<String, String> c: v.entrySet()){
                String b = c.getKey();
                String d = c.getValue();
                a += TextFormat.YELLOW+"------> [ "+b+" ] <------ "+TextFormat.RESET+"\n";
            }
            if(v.size() == 0)a += "----->"+"You have no HomeManager!<-----";
            a += TextFormat.GRAY+"========================"+TextFormat.RESET;
            sender.sendMessage(a);
            return true;
        }
        if (!Owner.HomeFactory.HasHomeAtKey(sender.getName(), args[0])) {
            sender.sendMessage(Prefix + "Error! You do not have a home called " + args[0]);
            return true;
        }
        if (Owner.TPING.contains(sender.getName().toLowerCase())) {
            sender.sendMessage(Prefix + "Error! You are in the process of TPing already!");
            return true;
        }
        Owner.TPING.add(sender.getName().toLowerCase());
        Owner.getServer().getScheduler().scheduleDelayedTask(new TPToHome(Owner, p, args[0]), 20 * 8);
        return true;
    }
}