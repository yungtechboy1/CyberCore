package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/18/2017.
 */
public class Troll extends Command {
    CyberCoreMain Owner;

    public Troll(CyberCoreMain server) {
        super("troll", "Catching Hackers", "/troll on/off");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("on / off", CommandParameter.ARG_TYPE_RAW_TEXT, true)
        });
        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length != 1 || !(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (p != null) {
            if(args[0].equalsIgnoreCase("on")) {
                p.setGamemode(1);
                AdventureSettings newSettings = p.getAdventureSettings();
                newSettings.setCanDestroyBlock(true);
                newSettings.setCanFly(true);
                newSettings.setNoclip(true);
                newSettings.setNoPvp(false);
                newSettings.update();
            }else{
                p.setGamemode(1);
            }
        }
        return true;
    }
}