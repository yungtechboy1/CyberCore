package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Bans.Ban;
import net.yungtechboy1.CyberCore.Commands.Constructors.TargetCommand;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.RankList;
import net.yungtechboy1.CyberCore.Utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class Tban extends TargetCommand {

    public Tban(CyberCoreMain server) {
        super(server, "tban", "Temp bans player", "/tban <player> [Hours|24] [Message|'']", RankList.PERM_ADMIN_1, 1, true);
        OT = false;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, true),
                new CommandParameter("Hours", CommandParameter.ARG_TYPE_INT, true),
                new CommandParameter("Message", CommandParameter.ARG_TYPE_RAW_TEXT, true)
        });
    }

    @Override
    public boolean execute(CommandSender s, String str, String[] args) {
        ArrayList<String> args2 = new ArrayList<>();
        int i = 0;
        for(String strr: args){
            if(i > 1)args2.add(strr);
            i++;
        }
        Player target = Target;
        if (target == null) {
            s.sendMessage(TextFormat.RED + "Target Not Found!");
            return true;
        }

        if (args.length == 1) {
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * 24);
            new Ban(Owner,target,TextFormat.RED+s.getName()+" Has banned you!",time,true,false,true);
            for (Item I : target.getInventory().getContents().values()) target.getLevel().dropItem(target, I);
            target.getInventory().clearAll();
            target.kick("You Have Been Temp Banned For 24 Hours!");
            Owner.getServer().broadcastMessage(Target.getName()+" has been temp banned for 24 Hours!");
        } else if (args.length >= 2) {
            Long time = (new Date().getTime()) + (1000 * 60 * 60 * Integer.parseInt(args[1]));
            new Ban(Owner,target,TextFormat.RED+s.getName()+" Has banned you! Reason:"+ Utils.implode(" ",(String[]) args2.toArray()),time,true,false,true);
            for (Item I : target.getInventory().getContents().values()) target.getLevel().dropItem(target, I);
            target.kick("You Have Been Temp Banned For " + Integer.parseInt(args[1]) + " Hours!");
            Owner.getServer().broadcastMessage(Target.getName()+" has Been Temp Banned For " + Integer.parseInt(args[1]) + " Hours!");
        }
        return true;
    }
}
