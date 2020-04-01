package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityArmorChangeEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
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

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent evnt) {
//        Player attacker = (Player)evnt.getDamager();
//        Player defender = (Player)evnt.getEntity();
//        if(defender ==  null)return;//Defender not player
//        Item cp = defender.getInventory().getChestplate();
//        if(cp == null)return;//No Chestplate on
//        EntityDamageEvent.DamageCause cause = evnt.getCause();
//        //Check if defender has BurnShield
//        BurnShield bs = (BurnShield) CustomEnchantment.getEnchantFromIDFromItem(cp,CustomEnchantment.BURNSHILED);
//        if(bs == null)return;
//        int bsl = bs.getLevel();
//        switch (bsl){
//            case 1:
//
//
//        }

    }
    @EventHandler
   public void EntityRegainHealthEvent(EntityRegainHealthEvent event){
        Player defender = (Player)event.getEntity();
        if(defender ==  null)return;//Defender not player
        Item cp = defender.getInventory().getChestplate();
        if(cp == null)return;//No Chestplate on
        int cause = event.getRegainReason();
        //Check if defender has BurnShield
        Restoration r = (Restoration) CustomEnchantment.getEnchantFromIDFromItem(cp,CustomEnchantment.RESTORATION);
        if(r == null)return;
        event.setAmount(event.getAmount() + r.GetLevelEffect());
    }

    @EventHandler
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
            Enchantment kohn = CustomEnchantment.getEnchantFromIDFromItem(ni, (short) CustomEnchantment.KINGOFHEARTS);
            Enchantment koho = CustomEnchantment.getEnchantFromIDFromItem(oi, (short) CustomEnchantment.KINGOFHEARTS);

            if (evnt.getNewItem().getId() == Item.AIR) {//Removed Armor
                if (glowo != null) p.removeEffect(Effect.NIGHT_VISION);
                if (gillso != null) p.removeEffect(Effect.WATER_BREATHING);
                if(koho != null)p.setMaxHealth(20);

            } else {//Putting on armor
                if (glown != null && glowo == null)  p.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(20*60*3).setAmplifier(1).setVisible(false));
                if (gillsn != null && gillso == null)  p.addEffect(Effect.getEffect(Effect.WATER_BREATHING).setDuration(20*60*3).setAmplifier(1).setVisible(false));
                if(kohn != null){
                    p.setMaxHealth(20 + kohn.getLevel());
                }

            }
        }
    }

}
