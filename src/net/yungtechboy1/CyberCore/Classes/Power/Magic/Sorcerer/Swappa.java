package net.yungtechboy1.CyberCore.Classes.Power.Magic.Sorcerer;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public class Swappa extends StagePowerAbstract {
    public Swappa(BaseClass b) {
        super(b);
    }

    public Swappa(BaseClass b, AdvancedPowerEnum ape) {
        super(b, ape);
    }

    @Override
    public PowerSettings getPowerSettings() {
        return new PowerSettings(false, false, false, false);
    }

    @Override
    public StageEnum getMaxStage() {
        return StageEnum.STAGE_5;
    }

    @Override
    protected int getCooldownTimeSecs() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return 5 * 60;
            case STAGE_2:
            case STAGE_3:
                return 4 * 60;
            case STAGE_4:
                return 3 * 60;
        }
    }

    @Override
    public ArrayList<Class> getAllowedClasses() {
        return null;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.Swappa;
    }

    @Override
    public String getName() {
        return "Swappa";
    }
}
