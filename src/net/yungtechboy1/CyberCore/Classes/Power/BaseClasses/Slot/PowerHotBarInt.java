package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRedstone;
import cn.nukkit.item.ItemSlimeball;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.CoolDownTick;
import net.yungtechboy1.CyberCore.CorePlayer;

public interface PowerHotBarInt {

    public static String getPowerHotBarItemNamedTagKey = "PowerHotBarItem";

    public static void RemoveAnyItemsInSlot(CorePlayer cp , LockedSlot ls){
        Item i = cp.getInventory().getItem(ls.getSlot());
        if (!i.isNull()) {//i.getNamedTag() != null
            if (i.getNamedTag() == null || !i.getNamedTag().contains(getPowerHotBarItemNamedTagKey)) {
                if (cp.getInventory().isFull()) {
                    cp.getLevel().dropItem(cp, i);
                } else {
                    for (int ii = 0; ii < cp.getInventory().getSize(); ii++) {
                        if (ii == ls.getSlot()) continue;
                        Item iii = cp.getInventory().getItem(ii);
                        if (iii.isNull()) {
                            cp.getInventory().setItem(ii, i);
                            break;
                        }
                    }
                }
            }
        }
        cp.getInventory().clear(ls.getSlot(), true);
    }



//    @Override
//    public InventoryClickEvent InventoryClickEvent(InventoryClickEvent e) {
//        getPlayer().sendMessage(TextFormat.RED + "Error! You can not change your Class Slot");
//        if (e.getSlot() == LS.getSlot()) {
//            if (e.getSourceItem().getNamedTag() != null && !e.getSourceItem().getNamedTag().contains(getPowerHotBarItemNamedTagKey)) {
//                e.setCancelled();
//                getPlayer().sendMessage(TextFormat.RED + "Error! You can not change your Class Slot");
//            }else{
//                getPlayer().sendMessage(TextFormat.YELLOW + "SAME SLOT THO!");
//            }
//        }
//        return e;
//
//    }
    boolean canUpdateHotBar(int tick);
default void updateHotbar(LockedSlot ls, CoolDownTick c, PowerAbstract p){
    if(ls == LockedSlot.NA)return;
    if (c == null || !c.isValid()) {
        setPowerAvailable(p);
        System.out.println("ACTIVE POWER");
    } else {
        System.out.println("UNNNNNNNNACTIVE POWER");
        setPowerUnAvailable(p);
    }
}


    default void antiSpamCheck(PowerAbstract p) {
//        int slot = 0;
        boolean k = false;
        for (int slot = 0; slot < p.getPlayer().getInventory().getSize(); slot++) {
            if (slot == p.getLS().getSlot()) continue;
            boolean g = false;
            for(LockedSlot ls : p.PlayerClass.getLockedSlots()){
                if(ls.getSlot() ==slot) {
                    g = true;
                    break;
                }
            }
            if(g) continue;
            //Checking other ActivePowers
//            if()
            Item i = p.getPlayer().getInventory().getItem(slot);
            if (i.getNamedTag() != null) {
                if (i.getNamedTag().contains(getPowerHotBarItemNamedTagKey)) {
                    p.getPlayer().getInventory().clear(slot, true);
                    k = true;
                }
            }
            slot++;
        }
//        if (k) p.getPlayer().kick("Please do not spam system!");
    }
    default void setPowerAvailable(PowerAbstract p) {

        p.getPlayer().getInventory().setItem(p.getLS().getSlot(), addNamedTag(p,getActiveItem(), p.getSafeName(), "Active"));
//        getPlayer().getInventory().setHeldItemIndex(LS.getSlot());
    }

    default void setPowerUnAvailable(PowerAbstract p) {
        p.getPlayer().getInventory().setItem(p.getLS().getSlot(), addNamedTag(p,getUnAvailableItem(), p.getSafeName(), "Idle"));
    }


    default Item addNamedTag(PowerAbstract p , Item i, String key, String val) {
        if (p.Cooldown == null || !p.Cooldown.isValid()) {
            i.setCustomName(TextFormat.GREEN+"Power: "+p.getDispalyName());
            i.setLore(TextFormat.GREEN + "Ready to Use", TextFormat.GREEN+"Costs: "+p.getPowerSourceCost()+" "+ p.PlayerClass.getPowerSourceType().name()+" ");
        } else {
            i.setCustomName(TextFormat.RED+"Power: "+p.getDispalyName());
            i.setLore(p.Cooldown.toString(), TextFormat.GREEN+"Costs: "+p.getPowerSourceCost()+" "+ p.PlayerClass.getPowerSourceType().name()+" ");
        }
        CompoundTag ct = i.getNamedTag();
        if (ct == null) ct = new CompoundTag();
        ct.putBoolean(getPowerHotBarItemNamedTagKey, true);
        i.setNamedTag(ct);
        if (key == null) return i;
        ct.putString(key, val);
        i.setNamedTag(ct);
        return i;
    }


    default Item getAvailableItem() {
        return new ItemSlimeball();
    }

    default Item getActiveItem() {
        return new ItemSlimeball(0, 5);
    }

    default Item getUnActiveItem() {
        return new ItemRedstone();
    }

    default Item getUnAvailableItem() {
        return new ItemRedstone();
    }
}
