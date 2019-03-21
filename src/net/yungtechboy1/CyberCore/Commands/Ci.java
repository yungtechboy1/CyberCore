package net.yungtechboy1.CyberCore.Commands;

import net.yungtechboy1.CyberCore.Commands.Constructors.TargetCommand;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.RankList;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Ci extends TargetCommand {

    public Ci(CyberCoreMain server) {
        super(server,"ci","Clears Player's Inv","/ci [player]", RankList.PERM_ADMIN_1,1,true);
        OT = true;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender s, String str, String[] args) {
        if (!super.execute(s, str, args)) return SendError();
        Error = null;
        if (s instanceof Player) {
            Player p = (Player) s;
            if (args.length == 1) {
                Player t = Target;
                if (t == null) {
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                t.getInventory().clearAll();
                t.sendMessage(TextFormat.YELLOW + "Your Inventory Cleared!");
                s.sendMessage(TextFormat.GREEN + t.getName() + " Inventory Cleared!");
            } else {
                p.getInventory().clearAll();
                p.sendMessage(TextFormat.YELLOW + "Your Inventory Cleared!");
            }
        } else {
            if (args.length == 1) {
                Player t = Target;
                if (t == null) {
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                t.getInventory().clearAll();
                t.sendMessage(TextFormat.YELLOW + "Your Inventory Cleared!");
                s.sendMessage(TextFormat.GREEN + t.getName() + " Inventory Cleared!");
                return true;
            }
            s.sendMessage(Messages.NEED_TO_BE_PLAYER);
        }
        return true;
    }
}