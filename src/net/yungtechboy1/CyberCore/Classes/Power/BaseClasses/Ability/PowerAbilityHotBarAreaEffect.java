package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerHotBar;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

//todo
public class PowerAbilityHotBarAreaEffect extends PowerAbilityAreaEffect implements PowerHotBar {
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
