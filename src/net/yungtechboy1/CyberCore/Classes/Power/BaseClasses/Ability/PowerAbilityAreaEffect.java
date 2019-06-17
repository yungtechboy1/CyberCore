package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import cn.nukkit.math.Vector3;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

public abstract class PowerAbilityAreaEffect extends PowerAbility {
    private Vector3 ActivatedLocation;


    public PowerAbilityAreaEffect(BaseClass b, int psc, double cost) {
        super(b, psc, cost);
    }

    public Vector3 getActivatedLocation() {
        return ActivatedLocation;
    }

    @Override
    public void onActivate() {
        ActivatedLocation = getPlayer().clone();
    }
}
