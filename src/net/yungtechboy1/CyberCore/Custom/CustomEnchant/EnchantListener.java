package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityArmorChangeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/24/2019.
 */
public class EnchantListener implements Listener {
    CyberCoreMain Main;

    public EnchantListener(CyberCoreMain mm) {
        Main = mm;
    }

    public void PlayerInventoryChange(EntityArmorChangeEvent evnt) {
        Entity e = evnt.getEntity();
        if (e instanceof Player) {
            Player p = (Player) e;
            Item ni = evnt.getNewItem();
            Item oi = evnt.getOldItem();
            //Armor Passive Check And Get
            Enchantment glown = CustomEnchantment.getEnchantFromIDFromItem(ni, (short) CustomEnchantment.NIGHTVISION);
            Enchantment glowo = CustomEnchantment.getEnchantFromIDFromItem(oi, (short) CustomEnchantment.NIGHTVISION);
            Enchantment gillsn = CustomEnchantment.getEnchantFromIDFromItem(ni, (short) CustomEnchantment.GILLS);
            Enchantment gillso = CustomEnchantment.getEnchantFromIDFromItem(oi, (short) CustomEnchantment.GILLS);

            if (evnt.getNewItem().getId() == Item.AIR) {//Removed Armor
                if (glown == null && glowo != null) p.removeEffect(Effect.NIGHT_VISION);
                if (gillsn == null && gillso != null) p.removeEffect(Effect.WATER_BREATHING);

            } else {//Putting on armor
                if (glown != null && glowo == null)  p.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(20*60*3).setAmplifier(1).setVisible(false));
                if (gillsn != null && gillso == null)  p.addEffect(Effect.getEffect(Effect.WATER_BREATHING).setDuration(20*60*3).setAmplifier(1).setVisible(false));


            }
        }
    }

}
