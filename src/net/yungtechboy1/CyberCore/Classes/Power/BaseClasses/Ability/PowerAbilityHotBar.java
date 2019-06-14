package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerHotBarInt;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public abstract class PowerAbilityHotBar extends PowerAbility, PowerHotBarInt {
    public PowerAbilityHotBar(BaseClass bc, int psc) {
        super(bc, psc);
    }

    public PowerAbilityHotBar(BaseClass b, int psc, double cost) {
        super(b, psc, cost);
    }


}
