package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.Offense.Knight;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
//PowerAbilityHotBar
public class DoubleTimeAbility extends StagePowerAbstract {
    private Buff oldbuff = null;

    public DoubleTimeAbility(Knight knight) {
        super(knight, 75, 10);
        setPowerSettings(true,false,true,false);
        setMaxStage(StageEnum.STAGE_5);
    }

    @Override
    public void onActivate() {
        getPlayer().sendMessage("TUNNING");
        getPlayer().addTemporaryBuff(new Buff(Buff.BuffType.Movement, getMovementBuff()));
//        oldbuff = PlayerClass.getBuff(Buff.BuffType.Movement);
//        PlayerClass.addBuff(new Buff(Buff.BuffType.Movement,1.1f));
//        PlayerClass.initBuffs();
    }

    public float getMovementBuff() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return 1.1f;
            case STAGE_2:
                return 1.15f;
            case STAGE_3:
            case STAGE_4:
                return 1.2f;
            case STAGE_5:
                return 1.25f;
        }
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }


    @Override
    public int getRunTimeTick() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return 15 * 20;
            case STAGE_2:
                return 30 * 20;
            case STAGE_3:
                return 45 * 20;
            case STAGE_4:
                return 60 * 20;
            case STAGE_5:
                return 75 * 20;
        }
    }

    @Override
    protected int getCooldownTime() {
        switch (getStage()) {
            default:
            case STAGE_5:
            case STAGE_1:
                return 45;
            case STAGE_2:
            case STAGE_3:
                return 60;
            case STAGE_4:
                return 55;

        }
    }

    @Override
    public int getPowerSuccessChance() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return 75;
            case STAGE_2:
            case STAGE_3:
            case STAGE_4:
                return 80;
            case STAGE_5:
                return 90;

        }
    }

    @Override
    public double getPowerSourceCost() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return 10;
            case STAGE_2:
                return 13;
            case STAGE_3:
                return 18;
            case STAGE_4:
                return 22;
            case STAGE_5:
                return 28;

        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.DoubleTime;
    }

    @Override
    public String getName() {
        return "Double Time " + getStage();
    }

    @Override
    public void whileAbilityActive() {

        getPlayer().sendMessage("WHILE ACTIVATE");
    }

    @Override
    public void onAbilityDeActivate() {
        getPlayer().delTemporbparyBuff(Buff.BuffType.Movement);
        getPlayer().sendMessage("ON DEACTIVATE");
    }
}
