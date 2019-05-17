package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.item.ItemSwordDiamond;
import cn.nukkit.item.enchantment.Enchantment;
import net.yungtechboy1.CyberCore.Commands.Constructors.CheckPermCommand;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemTool;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CustomEnchantment;
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
        //@TODO Add MustBePlayer bool in Constructor
        super(server, "fix", "Fixes Item in hand", "/fix", RankList.PERM_VIP);
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!super.execute(commandSender, s, strings)) return SendError();
        Error = null;
        if (commandSender instanceof Player) {

            CorePlayer p = ((CorePlayer) commandSender);
            double fc_cost = 0;
            int pfc = p.fixcoins;
            Item item = p.getInventory().getItemInHand();
            if (item.getId() == 0) {
                commandSender.sendMessage(CyberCoreMain.NAME + " Please hold the item you want to fix!");
                return true;
            }
            int tt = (int) Math.ceil(item.getEnchantments().length / 3.0d);

            switch (tt) {
                case 1:
                    fc_cost += 1;
                    break;
                case 2:
                    fc_cost += 1.5d;
                    break;
                case 3:
                    fc_cost += 2;
                    break;
                case 4:
                    fc_cost += 3;
                    break;
                case 5:
                    fc_cost += 5;
                    break;
            }

            for(Enchantment e : CustomEnchantment.getAllEnchantFromItem(item)){
                if(e instanceof CustomEnchantment){
                    CustomEnchantment ee = (CustomEnchantment) e;
                    switch (ee.TTier){
                        case Basic:
                            fc_cost += .2d;
                            break;
                        case Standard:
                            fc_cost += .3d;
                            break;
                        case Upgraded:
                            fc_cost += .4d;
                            break;
                        case Eternal:
                            fc_cost += .5d;
                            break;
                        case Legendary:
                            fc_cost += .7d;
                            break;
                        case Rare:
                            fc_cost += 1d;
                            break;
                        case Untold:
                            fc_cost += 1.5d;
                            break;
                        case Unheard:
                            fc_cost += 3d;
                            break;
                    }
                }
            }
            if(item.getNamedTag().contains("FIXED")){
                int f =  (int)Math.ceil(item.getNamedTag().getInt("FIXED") / 5);
                switch (f) {
                    case 1:
                        fc_cost += f;
                        break;
                    case 2:
                        fc_cost += f *2;
                        break;
                    case 3:
                        fc_cost += f *2;
                        break;
                }
            }

            if(fc_cost > pfc){
                //TOO EXPENSIVE
                p.sendMessage("To fix this Item costs: "+fc_cost+" FixCoins!");
                return true;
            }

            p.getInventory().remove(item);
            item.setDamage(0);

            if(item.getNamedTag().contains("FIXED"))item.getNamedTag().putInt("FIXED",item.getNamedTag().getInt("FIXED")+1);
            else item.getNamedTag().putInt("FIXED",1);
            p.getInventory().addItem(item);
            p.sendMessage("Item Repaired!");
            return true;



            //Check Cooldown
//            Boolean skip = (CheckPerms(commandSender) >= RankList.PERM_ADMIN_3);
//            CompoundTag nt = ((Player) commandSender).namedTag;
//            if (skip) {
//                if (nt != null) {
//                    Integer time = nt.getInt("CCFix");
//                    Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000);
//                    //Check time
//                    if (ct < time) {
//                        String diff = Utils.getDifferenceBtwTime((long) time);
//                        commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You must wait " + diff);
//                        return true;
//                    }
//                }
//            }
//            Item hand = ((Player) commandSender).getInventory().getItemInHand().clone();
//            if (!(hand instanceof ItemTool) && !(hand instanceof ItemArmor)) {
//                commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You can repair Armor and Tools!");
//                return true;
//            }
//            hand.setDamage(0);
//            ((Player) commandSender).getInventory().setItemInHand(hand);
//            if (skip) {
//                Integer q = 60;
//                int S = CheckPerms(commandSender);
//                if (S == 1) q = 60 * 60 * 12;//Hun?!
//                if (S == 2) q = 60 * 60 * 8;
//                if (S == 3) q = 60 * 60 * 4;
//                if (S == 4) q = 60 * 60;
//                Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000) + q;
//                if (nt != null)nt.putInt("CCFix",ct);
//            }
        } else {
            commandSender.sendMessage(CyberCoreMain.NAME + Messages.NEED_TO_BE_PLAYER);
        }
        return true;
    }
}