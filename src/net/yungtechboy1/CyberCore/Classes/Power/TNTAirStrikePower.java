package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public  class TNTAirStrikePower extends StagePowerAbstract {

    public TNTAirStrikePower(BaseClass b) {
        super(b);
    }

    public TNTAirStrikePower(BaseClass b, AdvancedPowerEnum ape) {
        super(b, ape);
    }

    @Override
    public int getTickUpdate() {
        return 20*5;
    }

    @Override
    public ArrayList<Class> getAllowedClasses() {
        return null;
    }

    @Override
    public StageEnum getMaxStage() {
        return StageEnum.STAGE_5;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.TNTAirStrike;
    }

    @Override
    public Object usePower( Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return "TNT Air Strike";
    }

    @Override
    public boolean CanRun(boolean force, Object... args) {
        if (force) return force;
        if (PlayerClass.getPlayer().getHealth() < getHealthDraw())return false;
        return super.CanRun(false,args);
    }

    private int getSendHealth() {
        switch (getStage()) {
            case STAGE_1:
            case STAGE_2:
                return 3;
            case STAGE_3:
            case STAGE_4:
                return 4;
            case STAGE_5:
                return 5;
            default:
                return 3;
        }
    }
    private int getHealthDraw() {
        switch (getStage()) {
            case STAGE_1:
            case STAGE_2:
                return 5;
            case STAGE_3:
            case STAGE_4:
                return 4;
            case STAGE_5:
                return 3;
            default:
                return 5;
        }
    }
}
