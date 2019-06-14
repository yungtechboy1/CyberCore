package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses;

import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRedstone;
import cn.nukkit.item.ItemSlimeball;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public abstract class PowerHotBarInt extends PowerHotBar {

    public static String getPowerHotBarItemNamedTagKey = "PowerHotBarItem";
    LockedSlot LS;

    public PowerHotBarInt(BaseClass b, int psc) {
        this(b, psc, LockedSlot.SLOT_9);
    }

    public PowerHotBarInt(BaseClass b, int psc, LockedSlot ls) {
        super(b, psc);
        TickUpdate = 20;
        LS = ls;
        Item i = getPlayer().getInventory().getItem(ls.getSlot());
        if (!i.isNull()) {//i.getNamedTag() != null
            if (i.getNamedTag() == null || !i.getNamedTag().contains(getPowerHotBarItemNamedTagKey)) {
                if (getPlayer().getInventory().isFull()) {
                    getPlayer().getLevel().dropItem(getPlayer(), i);
                } else {
                    for (int ii = 0; ii < getPlayer().getInventory().getSize(); ii++) {
                        if (ii == ls.getSlot()) continue;
                        Item iii = getPlayer().getInventory().getItem(ii);
                        if (iii.isNull()) {
                            getPlayer().getInventory().setItem(ii, i);
                            break;
                        }
                    }
                }
            }
        }
        getPlayer().getInventory().clear(ls.getSlot(), true);
    }

    public LockedSlot getLockedSlot() {
        return LS;
    }

    @Override
    public InventoryTransactionEvent InventoryTransactionEvent(InventoryTransactionEvent e) {
        getPlayer().sendMessage(TextFormat.RED + "TRANS CALLEDDDDD! Slot");
        for (InventoryAction action : e.getTransaction().getActions()) {
            if (!(action instanceof SlotChangeAction)) {
                continue;
            }
            SlotChangeAction slotChange = (SlotChangeAction) action;

            if (slotChange.getInventory() instanceof PlayerInventory) {
//                who = (Player) slotChange.getInventory().getHolder();
                //Check to see if Slot is fucked with
                if (slotChange.getSlot() == LS.getSlot()) {
                    e.setCancelled();
                    getPlayer().sendMessage(TextFormat.RED + "Error! You can not change your Class Slot!");
                }
            }
        }

        return e;

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

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }


    public void setPowerAvailable() {

        getPlayer().getInventory().setItem(LS.getSlot(), addNamedTag(getActiveItem(), getSafeName(), "Active"));
//        getPlayer().getInventory().setHeldItemIndex(LS.getSlot());
    }

    public void setPowerUnAvailable() {
        getPlayer().getInventory().setItem(LS.getSlot(), addNamedTag(getUnAvailableItem(), getSafeName(), "Idle"));
    }

    @Override
    public void onTick(int tick) {
        if (Cooldown == null || !Cooldown.isValid()) {
            setPowerAvailable();
            System.out.println("ACTIVE POWER");
        } else {
            System.out.println("UNNNNNNNNACTIVE POWER");
            setPowerUnAvailable();
        }
        if (tick % 2 == 0) antiSpamCheck();
    }

    public void antiSpamCheck() {
//        int slot = 0;
        boolean k = false;
        for (int slot = 0; slot < getPlayer().getInventory().getSize(); slot++) {
            if (slot == LS.getSlot()) continue;
            Item i = getPlayer().getInventory().getItem(slot);
            if (i.getNamedTag() != null) {
                if (i.getNamedTag().contains(getPowerHotBarItemNamedTagKey)) {
                    getPlayer().getInventory().clear(slot, true);
                    k = true;
                }
            }
            slot++;
        }
        if (k) getPlayer().kick("Please do not spam system!");
    }

    public Item addNamedTag(Item i, String key, String val) {
        i.setCustomName("Use Power: " + getDispalyName());
        if (Cooldown == null) {
            i.setLore(TextFormat.GREEN + "Ready to Use", TextFormat.GREEN+"Costs: "+getPowerSourceCost()+" "+ PlayerClass.getPowerSourceType().name()+" ");
        } else {
            i.setLore(Cooldown.toString(), TextFormat.GREEN+"Costs: "+getPowerSourceCost()+" "+ PlayerClass.getPowerSourceType().name()+" ");
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

    @Override
    public String getName() {
        return null;
    }

    public Item getAvailableItem() {
        return new ItemSlimeball();
    }

    public Item getActiveItem() {
        return new ItemSlimeball(0, 5);
    }

    public Item getUnActiveItem() {
        return new ItemRedstone();
    }

    public Item getUnAvailableItem() {
        return new ItemRedstone();
    }

    public static enum LockedSlot {
        NA,
        SLOT_9,
        SLOT_8,
        SLOT_7;

        public int getSlot() {
            if (this == NA) return -1;
            if (this == SLOT_7) return 6;
            if (this == SLOT_8) return 7;
            if (this == SLOT_9) return 8;
            return 8;
        }
    }
}
