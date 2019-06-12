package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.event.entity.EntityInventoryChangeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRedstone;
import cn.nukkit.item.ItemSlimeball;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import javax.swing.text.TabExpander;
import java.awt.*;

public abstract class PowerHotBarInt extends PowerHotBar {

    public static enum LockedSlot{
        NA,
        SLOT_9,
        SLOT_8,
        SLOT_7;

        public int getSlot(){
            if(this == NA)return -1;
            if(this == SLOT_7)return 6;
            if(this == SLOT_8)return 7;
            if(this == SLOT_9)return 8;
            return 8;
        }
    }

    LockedSlot LS;
    public PowerHotBarInt(BaseClass b, int psc) {
        this(b,psc,LockedSlot.SLOT_9);
    }
    public PowerHotBarInt(BaseClass b, int psc, LockedSlot ls) {
        super(b, psc);
        TickUpdate = 20;
        LS = ls;
        Item i = getPlayer().getInventory().getItem(ls.getSlot());
        if(!i.isNull()){
            if(getPlayer().getInventory().isFull()){
                getPlayer().getLevel().dropItem(getPlayer(),i);
            }else{
                for(int ii = 0; ii < getPlayer().getInventory().getSize(); ii++){
                    if(ii == ls.getSlot())continue;
                    Item iii = getPlayer().getInventory().getItem(ii);
                    if(iii.isNull()){
                        getPlayer().getInventory().setItem(ii,i);
                        continue;
                    }
                }
            }
        }
        getPlayer().getInventory().clear(ls.getSlot(),true);
    }

    public LockedSlot getLockedSlot(){
        return LS;
    }

    @Override
    public EntityInventoryChangeEvent EntityInventoryChangeEvent(EntityInventoryChangeEvent e) {
        System.out.println("Tring to change SLOT "+e.getSlot()+ " BUT LOCKED SLOT "+getLockedSlot().getSlot());
        if(e.getSlot() == getLockedSlot().getSlot()){
            if(!e.getNewItem().getNamedTag().contains(getPowerHotBarItemNamedTagKey)){
                e.setCancelled();
                getPlayer().sendMessage(TextFormat.RED+"Error! You can not change your Class Slot");
            }
        }
        return e;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }



    public void setPowerAvailable() {

        getPlayer().getInventory().setItem(LS.getSlot(),addNamedTag(getActiveItem(),getSafeName(),"Active"));
//        getPlayer().getInventory().setHeldItemIndex(LS.getSlot());
    }

    public void setPowerUnAvailable() {
        getPlayer().getInventory().slots.put(LS.getSlot(),addNamedTag(getUnAvailableItem(),getSafeName(),"Idle"));
    }

    @Override
    public void onTick(int tick) {
        if (Cooldown == null || !Cooldown.isValid()) {
            setPowerAvailable();
        } else {
            setPowerUnAvailable();
        }
    }

    public static String getPowerHotBarItemNamedTagKey = "PowerHotBarItem";
    public Item addNamedTag(Item i, String key, String val){
        i.getNamedTag().putBoolean(getPowerHotBarItemNamedTagKey,true);
        i.setNamedTag(i.getNamedTag());
        if(key == null)return i;
        i.getNamedTag().putString(key,val);
        i.setNamedTag(i.getNamedTag());
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
        return new ItemSlimeball(0,5);
    }

    public Item getUnActiveItem() {
        return new ItemRedstone();
    }

    public Item getUnAvailableItem() {
        return new ItemRedstone();
    }
}
