package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public  class TNTAirStrikePower extends Power {
    public TNTAirStrikePower(BaseClass b, int lvl) {
        super(b, 100, lvl);
        TickUpdate = 20*5;//5 Secs
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
    public Object usePower(CorePlayer cp, Object... args) {
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
