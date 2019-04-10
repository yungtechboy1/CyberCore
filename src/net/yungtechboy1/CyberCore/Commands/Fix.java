package net.yungtechboy1.CyberCore.Commands;

import net.yungtechboy1.CyberCore.Commands.Constructors.CheckPermCommand;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemTool;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.Rank.RankList;
import net.yungtechboy1.CyberCore.Utils;

import java.util.Calendar;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Fix extends CheckPermCommand {

    public Fix(CyberCoreMain server) {
        //@TODO Check the min Rank for this
        super(server, "fix", "Fixes Item in hand", "/fix", RankList.PERM_ISLANDER);
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!super.execute(commandSender, s, strings)) return SendError();
        Error = null;
        if (commandSender instanceof Player) {
            //Check Cooldown
            Boolean skip = (CheckPerms(commandSender) >= RankList.PERM_ADMIN_3);
            CompoundTag nt = ((Player) commandSender).namedTag;
            if (skip) {
                if (nt != null) {
                    Integer time = nt.getInt("CCFix");
                    Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000);
                    //Check time
                    if (ct < time) {
                        String diff = Utils.getDifferenceBtwTime((long) time);
                        commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You must wait " + diff);
                        return true;
                    }
                }
            }
            Item hand = ((Player) commandSender).getInventory().getItemInHand().clone();
            if (!(hand instanceof ItemTool) && !(hand instanceof ItemArmor)) {
                commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You can repair Armor and Tools!");
                return true;
            }
            hand.setDamage(0);
            ((Player) commandSender).getInventory().setItemInHand(hand);
            if (skip) {
                Integer q = 60;
                int S = CheckPerms(commandSender);
                if (S == 1) q = 60 * 60 * 12;//Hun?!
                if (S == 2) q = 60 * 60 * 8;
                if (S == 3) q = 60 * 60 * 4;
                if (S == 4) q = 60 * 60;
                Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000) + q;
                if (nt != null)nt.putInt("CCFix",ct);
            }
        } else {
            commandSender.sendMessage(CyberCoreMain.NAME + Messages.NEED_TO_BE_PLAYER);
        }
        return true;
    }
}