package net.yungtechboy1.CyberCore.Classes.New.Offense;

import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBar;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class DragonSlayerFireStomp extends PowerHotBar {
    public DragonSlayerFireStomp(DragonSlayer dragonSlayer) {
        super(dragonSlayer,100,50, LockedSlot.SLOT_9 );
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object usePower(Object... args) {
//        getPlayer().addMotion();
        return null;
    }

    @Override
    public boolean canUpdateHotBar(int tick) {
        return false;
    }
}
