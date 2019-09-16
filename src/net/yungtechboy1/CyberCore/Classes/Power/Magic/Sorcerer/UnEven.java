package net.yungtechboy1.CyberCore.Classes.Power.Magic.Sorcerer;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public class UnEven extends PowerAbstract {
    public UnEven(BaseClass b) {
        super(b);
    }

    public UnEven(BaseClass b, AdvancedPowerEnum ape) {
        super(b, ape);
    }


    @Override
    public ArrayList<Class> getAllowedClasses() {
        return null;
    }

    @Override
    public PowerSettings getPowerSettings() {
        return new PowerSettings(false, false, false, false);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public int getPowerSuccessChance() {
        return 100;
    }

    @Override
    protected int getCooldownTimeSecs() {
        switch (getStage()) {
            default:
            case NA:
            case STAGE_1:
                return 45;
            case STAGE_2:
                return 35;
            case STAGE_3:
                return 30;
            case STAGE_4:
                return 25;
            case STAGE_5:
                return 15;
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.UnEven;
    }

    public Object usePower(Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return "UnEven | " + getStage().getDisplayName();
    }

    @Override
    public void onTick(int tick) {
        super.onTick(tick);
    }

}