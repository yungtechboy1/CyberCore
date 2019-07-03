package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class StagePowerAbilityHotBarImpl extends StagePowerAbilityHotBar {
    public StagePowerAbilityHotBarImpl(BaseClass b, int psc, double cost, LockedSlot ls) {
        super(b, psc, cost, ls);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }

    @Override
    public PowerEnum getType() {
        return null;
    }

    @Override
    public Object usePower(Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean canUpdateHotBar(int tick) {
        return false;
    }
}
