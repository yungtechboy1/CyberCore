package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.StagePowerAbilityHotBar;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.CoolDownTick;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class KnightDoubleTime extends PowerAbstract.StagePowerAbilityHotBar {

    public KnightDoubleTime(BaseClass b, int psc, double cost, LockedSlot ls) {
        super(b, psc, cost, ls);
    }

    @Override
    public String getName() {
        return "Double Time";
    }

    @Override
    public InventoryTransactionEvent InventoryTransactionEvent(InventoryTransactionEvent e) {
        return super.InventoryTransactionEvent(e);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }

    public PowerEnum getType() {
        return PowerEnum.DoubleTime;
    }

    @Override
    public Object usePower(Object... args) {
        return null;
    }

    @Override
    public boolean canUpdateHotBar(int tick) {
        return true;
    }

}