package net.yungtechboy1.CyberCore.Classes.Power.Attack.Knight;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Offense.Knight;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.PlayerTakeDamageEvent;

import java.util.ArrayList;

public class LastStand extends StagePowerAbstract {
    public LastStand(BaseClass b) {
        super(b);
    }

    public LastStand(BaseClass b, AdvancedPowerEnum ape) {
        super(b, ape);
    }

    public LastStand(AdvancedPowerEnum ape) {
        super(ape);
    }

    @Override
    public int getPowerSuccessChance() {
        return 100;
    }

    @Override
    public PowerSettings getPowerSettings() {
        return new PowerSettings(true, false, false, false);
    }

    @Override
    public StageEnum getMaxStage() {
        return StageEnum.STAGE_5;
    }

    @Override
    protected int getCooldownTimeSecs() {
        int i  = 0;
        switch (getStage()) {
            default:
            case STAGE_1:
                i =  30;
                break;
            case STAGE_2:
                i =  25;
                break;
            case STAGE_3:
                i =  20;
                break;
            case STAGE_4:
                i =  15;
                break;
            case STAGE_5:
                i =  10;
                break;
        }
        return i * 60;
    }

    @Override
    public ArrayList<Class> getAllowedClasses() {
        ArrayList<Class> a = super.getAllowedClasses();
        a.add(Knight.class);
        return a;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PlayerTakeDamageEvent PlayerTakeDamageEvent(PlayerTakeDamageEvent e) {

        System.out.println("SSSSSSSSTTTART11122312");
        float f = e.getFinalDamage();
        if(f >= getPlayer().getHealth()){
            System.out.println("RUNNINGGGGG22222222");
            //Try to enable
            initPowerRun();
            if(!isEnabled())return e;

            float a = (getPlayer().getHealth() - f) - 1;
            e.DamageReduction -= a;
        }else {
            System.out.println("NOT RUNNINGGGGG");
            return e;
        }
        if(e.getFinalDamage() < 0)e.DamageIncrease += Math.abs(e.getFinalDamage());

        System.out.println("3333333333333333");
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.LastStand;
    }

    @Override
    public String getName() {
        return "LastStand";
    }
}
