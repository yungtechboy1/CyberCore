package net.yungtechboy1.CyberCore.Classes.New.Offense;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.PowerAbility;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class DrakKnightPoisonousStench extends PowerAbility {


    public DrakKnightPoisonousStench(BaseClass bc) {
        super(bc, 100);
        TickUpdate = 20;//1 Secs
    }

    @Override
    public void onAbilityActivate() {

    }

    @Override
    public void onAbilityDeActivate() {

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
    public String getName() {
        return null;
    }
}
