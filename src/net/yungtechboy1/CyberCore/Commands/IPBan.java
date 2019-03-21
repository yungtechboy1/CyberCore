package net.yungtechboy1.CyberCore.Commands;

import net.yungtechboy1.CyberCore.Commands.Constructors.TargetCommand;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Bans.Ban;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.RankList;
import net.yungtechboy1.CyberCore.Utils;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class IPBan extends TargetCommand {

    public IPBan(CyberCoreMain server) {
        super(server, "ban", "Bans player", "/banip <player> [Message|'']", RankList.PERM_ADMIN_1, 0, true);
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
        if (!super.execute(s, str, args)) return SendError();
        Player target = Target;
        if (target == null) {
            s.sendMessage(TextFormat.RED + "Target Not Found!");
            return true;
        }

        Long time = -1L;
        new Ban(Owner,target,TextFormat.RED+s.getName()+" Has banned you! Reason:"+ Utils.implode(" ",args),time,true,true,true);
        for (Item I : target.getInventory().getContents().values()) target.getLevel().dropItem(target, I);
        target.getInventory().clearAll();
        target.kick("You Have Been Banned!");
        Owner.getServer().broadcastMessage(Target.getName()+" has been banned!");
        return true;
    }
}
