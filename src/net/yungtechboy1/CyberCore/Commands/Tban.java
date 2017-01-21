package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Commands.Constructors.TargetCommand;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Ranks;

import java.util.Date;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class Tban extends TargetCommand {

    public Tban(Main server) {
        super(server, "tban", "Temp bans player", "/tban <player> [Hours|24] [Mins] [Secs]", Ranks.PERM_ADMIN_1, 1, true);
        OT = false;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, true),
                new CommandParameter("Hours", CommandParameter.ARG_TYPE_INT, true),
                new CommandParameter("Mins", CommandParameter.ARG_TYPE_INT, true),
                new CommandParameter("Secs", CommandParameter.ARG_TYPE_INT, true)
        });
    }

    @Override
    public boolean execute(CommandSender s, String str, String[] args) {
        Player target = Target;
        if (target == null) {
            s.sendMessage(TextFormat.RED + "Target Not Found!");
            return true;
        }

        long mbl = (new Date().getTime()) + (1000 * 60 * 60 * 24 * 5);//5 Days Max Ban
        if(CheckPerms(s) < Ranks.PERM_ADMIN_1)(new Date().getTime()) + (1000 * 60 * 60 * 24 * 5);//5 Days Max Ban

        if (args.length == 1) {
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * 24);
            Owner.tban.set(target.getName().toLowerCase(), time);
            for (Item I : target.getInventory().getContents().values()) target.getLevel().dropItem(target, I);
            target.getInventory().clearAll();
            target.kick("You Have Been Temp Banned For 24 Hours!");
            s.sendMessage("They Have Been Temp Banned For 24 Hours!");
        } else if (args.length == 2) {
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * Integer.parseInt(args[1]));
            Owner.tban.set(target.getName().toLowerCase(), time);
            for (Item I : target.getInventory().getContents().values()) target.getLevel().dropItem(target, I);
            target.kick("You Have Been Temp Banned For " + Integer.parseInt(args[1]) + " Hours!");
            target.sendMessage("They Have Been Temp Banned For " + Integer.parseInt(args[1]) + " Hours!");
        } else if (args.length == 3) {
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * Integer.parseInt(args[1]) + (1000 * 60 * Integer.parseInt(args[2])));
            Owner.tban.set(target.getName().toLowerCase(), time);
            for (Item I : target.getInventory().getContents().values()) target.getLevel().dropItem(target, I);
            target.kick("You Have Been Temp Banned For " + args[1] + " Hours " + args[2] + " Mins!");
            s.sendMessage("They Have Been Temp Banned For " + args[1] + " Hours " + args[2] + " Mins!");
        } else if (args.length == 4) {
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * Integer.parseInt(args[1]) + (1000 * 60 * Integer.parseInt(args[2]))) + (1000 * Integer.parseInt(args[3]));
            Owner.tban.set(target.getName().toLowerCase(), time);
            for (Item I : target.getInventory().getContents().values()) target.getLevel().dropItem(target, I);
            target.kick("You Have Been Temp Banned For " + args[1] + " Hours " + args[2] + " Mins and " + args[3] + "Secs!");
            s.sendMessage("They Have Been Temp Banned For " + args[1] + " Hours " + args[2] + " Mins and " + args[3] + "Secs!");
        }
        return true;
    }
}
