package net.yungtechboy1.CyberCore.Classes.New.Offense;

import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.PowerAbilityHotBar;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class DoubleTimeAbility extends PowerAbilityHotBar implements XPLevelingPowerInt {
    public DoubleTimeAbility(Knight knight) {
        super();
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public void setMaxLevel(int maxLevel) {

    }

    @Override
    public int getXP() {
        return 0;
    }

    @Override
    public int getRealXP() {
        return 0;
    }

    @Override
    public void addXP(int a) {

    }

    @Override
    public void takeXP(int a) {

    }

    @Override
    public void onActivate() {

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
    public void onAbilityActivate() {

    }

    @Override
    public void whileAbilityActive() {

    }

    @Override
    public void onAbilityDeActivate() {

    }

    @Override
    public boolean canUpdateHotBar(int tick) {
        return false;
    }
}
