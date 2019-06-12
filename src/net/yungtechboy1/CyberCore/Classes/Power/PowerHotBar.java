package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRedstone;
import cn.nukkit.item.ItemSlimeball;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class PowerHotBar extends Power {
    public static enum LockedSlot{
        SLOT_9,
        SLOT_8,
        SLOT_7;

        public int getSlot(){
            if(this == SLOT_7)return 7;
            if(this == SLOT_8)return 8;
            if(this == SLOT_9)return 9;
            return 9;
        }
    }
    LockedSlot LS;
    public PowerHotBar(BaseClass b, int psc) {
        this(b,psc,LockedSlot.SLOT_9);
    }
    public PowerHotBar(BaseClass b, int psc, LockedSlot ls) {
        super(b, psc);
        TickUpdate = 20;
        LS = ls;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PowerEnum getType() {
        return null;
    }

    @Override
    public Object usePower(CorePlayer cp, Object... args) {
        return null;
    }

    public void setPowerAvailable() {
        getPlayer().getInventory().setItem(LS.getSlot(),getActiveItem());
    }

    public void setPowerUnAvailable() {
        getPlayer().getInventory().setItem(LS.getSlot(),getUnAvailableItem());
    }

    @Override
    public void onTick(int tick) {
        if (CanRun()) {
            setPowerAvailable();
        } else {
            setPowerUnAvailable();
        }
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
