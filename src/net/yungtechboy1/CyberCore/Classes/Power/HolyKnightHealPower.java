package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

public class HolyKnightHealPower extends Power {
    public HolyKnightHealPower(BaseClass b, int lvl) {
        super(b, 75, lvl);
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.HolyKnightHeal;
    }

    @Override
    public String getName() {
        return "Holy Knight";
    }

    public int getRange() {
        switch (getStage()){
            case STAGE_1:
                return 7;
            case STAGE_2:
                return 8;
            case STAGE_3:
                return 9;
            case STAGE_4:
                return 10;
            case STAGE_5:
                return 11;
            case NA:
                default:
                    return 7;
        }
    }
    public int getHealingAmount() {
        switch (getStage()){
            case STAGE_1:
                return 2;
            case STAGE_2:
                return 2;
            case STAGE_3:
                return 3;
            case STAGE_4:
                return 4;
            case STAGE_5:
                return 5;
            case NA:
                default:
                    return 7;
        }
    }

    @Override
    protected int getCooldownTime() {
        return 30 + (10*(6-getStage().ordinal()));
    }
}
