package net.yungtechboy1.CyberCore.Tasks;

import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CustomEnchantment;
import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.Map;

/**
 * Created by carlt_000 on 2/8/2017.
 */
public class SendInvTask extends PluginTask<CyberCoreMain> {
    Player P;
    Map<Integer, Item> LIST;

    public SendInvTask(CyberCoreMain owner, Player p, Map<Integer, Item> list) {
        super(owner);
        P = p;
        LIST = list;
    }

    @Override
    public void onRun(int currentTick) {
        P.getInventory().setContents(LIST);

        for(Map.Entry<Integer,Item> i: LIST.entrySet()){
            Item a = P.getInventory().getItem(i.getKey());
            if(a == null)continue;
            if(a.hasEnchantments()){
                for(Enchantment e: a.getEnchantments()){
                    if(e.getId() >= 25 && e instanceof CustomEnchantment){
                        ((CustomEnchantment) e).CheckCustomName(P);
                        P.getInventory().setItem(i.getKey(),a);
                        break;
                    }
                }
            }
        }

        P.getInventory().sendHeldItem(P);
        P.getInventory().sendArmorContents(P);
        P.getInventory().sendContents(P);
    }
    //@TODO
}
