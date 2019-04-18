package net.yungtechboy1.CyberCore.Commands.Homes;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.defaults.GamemodeCommand;
import cn.nukkit.command.defaults.TeleportCommand;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Data.HomeData;
import net.yungtechboy1.CyberCore.Tasks.TPToHome;

import java.util.Arrays;
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
        CommandParameter lcp = new CommandParameter("list");
        CommandParameter kcp = new CommandParameter("key", CommandParamType.TEXT, false);
        lcp.enumData = new CommandEnum("List", Arrays.asList(new String[]{"list"}));
//        kcp.enumData = new CommandEnum("Key Name", Arrays.asList();
        this.commandParameters.clear();
        this.commandParameters.put("key", new CommandParameter[]{
                kcp
        });
        this.commandParameters.put("list", new CommandParameter[]{
                lcp
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length != 1 || !(sender instanceof Player)) return false;
        CorePlayer p = (CorePlayer) sender;
        if (args[0].equalsIgnoreCase("list")) {
            String a = "";
            a += TextFormat.GRAY + "=========================" + TextFormat.RESET + "\n";
            a += TextFormat.GRAY + "==========USAGE==========" + TextFormat.RESET + "\n";
            a += TextFormat.GRAY + "= Use /home <home name> =" + TextFormat.RESET + "\n";
            a += TextFormat.GRAY + "======= Home Names ======" + TextFormat.RESET + "\n";
            for (HomeData hd : p.HD) {
                String b = hd.getName();
                a += TextFormat.YELLOW + "------> [ " + b + " ] <------ " + TextFormat.RESET + "\n";
            }
            if (p.HD.size() == 0) a += "----->" + "You have no Homes!<-----"+ "\n";;
            a += TextFormat.GRAY + "========================" + TextFormat.RESET;
            sender.sendMessage(a);
            return true;
        }
        if (!p.CheckHomeKey(args[0])) {
            sender.sendMessage(Prefix + "Error! You do not have a home called " + args[0]);
            return true;
        }
        if (p.Teleporting) {
            sender.sendMessage(Prefix + "Error! You are in the process of TPing already!");
            return true;
        }
        p.Teleporting = true;
        p.TeleportToHome(args[0],10);
        return true;
    }
}