package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;

import java.util.Date;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class TCBan {
    public static void runCommand(CommandSender s,String[] args, Main server){
        if(args.length <= 0){
            s.sendMessage("Usage: /tcban <player> <Hours>");
            s.sendMessage("Usage: /tcban <player> <Hours> <Mins>");
            s.sendMessage("Usage: /tcban <player> <Hours>  <Mins>  <Secs>");
            return;
        }

        Player target = server.getServer().getPlayer(args[0]);
        if(target == null){
            s.sendMessage(TextFormat.RED+"Target Not Found!");
            return;
        }
        if(args.length == 1){
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * 24);
            server.tcban.set(target.getUniqueId().toString(),time);
            for(Item I : target.getInventory().getContents().values())target.getLevel().dropItem(target,I);
            target.kick("Your Client Have Been Temp Banned For 24 Hours!");
            s.sendMessage("That Client has Been Temp Banned For 24 Hours!");
        }else if(args.length == 2){
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * Integer.parseInt(args[1]));
            server.tcban.set(target.getUniqueId().toString(),time);
            for(Item I : target.getInventory().getContents().values())target.getLevel().dropItem(target,I);
            target.kick("Your Client Have Been Temp Banned For "+Integer.parseInt(args[1])+" Hours!");
            s.sendMessage("That Client has Been Temp Banned For "+Integer.parseInt(args[1])+" Hours!");
        }else if(args.length == 3){
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * Integer.parseInt(args[1]) + (1000 * 60 * Integer.parseInt(args[2])));
            server.tcban.set(target.getUniqueId().toString(),time);
            for(Item I : target.getInventory().getContents().values())target.getLevel().dropItem(target,I);
            target.kick("Your Client Have Been Temp Banned For "+args[1]+" Hours "+args[2]+" Mins!");
            s.sendMessage("That Client has Been Temp Banned For "+args[1]+" Hours "+args[2]+" Mins!");
        }else if(args.length == 4){
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * Integer.parseInt(args[1]) + (1000 * 60 * Integer.parseInt(args[2]))) + (1000 * Integer.parseInt(args[3]));
            server.tcban.set(target.getUniqueId().toString(),time);
            for(Item I : target.getInventory().getContents().values())target.getLevel().dropItem(target,I);
            target.kick("Your Client Have Been Temp Banned For "+args[1]+" Hours "+args[2]+" Mins and "+args[3]+"Secs!");
            s.sendMessage("That Client has Been Temp Banned For "+args[1]+" Hours "+args[2]+" Mins and "+args[3]+"Secs!");
        }else{
            return;
        }
    }
}
