package net.yungtechboy1.CyberCore.Classes.Power.Attack.Knight;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Offense.Knight;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.PlayerTakeDamageEvent;

import java.util.ArrayList;

public class RagePower extends PowerAbstract {
    public RagePower(AdvancedPowerEnum ape) {
        super(ape);
    }

    public RagePower(BaseClass b) {
        super(b);
    }

    public RagePower(BaseClass b, AdvancedPowerEnum ape) {
        super(b, ape);
    }

    @Override
    public ArrayList<Class> getAllowedClasses() {
        ArrayList<Class> a = super.getAllowedClasses();
        a.add(Knight.class);
        return a;
    }

    @Override
    public PowerSettings getPowerSettings() {
        return new PowerSettings(false, true, true, false);
    }

    public float getFinalDamageBoost(CustomEntityDamageByEntityEvent e) {
        return e.getBaseDamage() * getDamageBoostAmount();
    }

    public float getDamageBoostAmount() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return 1 / 5;
            case STAGE_2:
            case STAGE_3:
                return 2 / 5;
            case STAGE_4:
            case STAGE_5:
                return 3 / 5;
        }
    } public float getFinalDamagetReduction(CustomEntityDamageEvent e) {
        return -1 * (e.getBaseDamage() * getDamageReductionAmount());
    }

    public float getDamageReductionAmount() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return 1 / 10;
            case STAGE_2:
                return 1/5;
            case STAGE_4:
            case STAGE_3:
                return 3 / 10;
            case STAGE_5:
                return 2 / 5;
        }
    }

    @Override
    //Called by Damager
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        if (isEnabled() && isActive()) {
            e.addDamage(CustomEntityDamageByEntityEvent.CustomDamageModifier.PowerRage_Strength, getFinalDamageBoost(e));
        }
        return e;
    }

    @Override
    public PlayerTakeDamageEvent PlayerTakeDamageEvent(PlayerTakeDamageEvent e) {
        if (isEnabled() && isActive()) {
            e.DamageReduction = getFinalDamagetReduction(e.source);
        }
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
                return 45*2;
            case STAGE_2:
                return 35*2;
            case STAGE_3:
                return 30*2;
            case STAGE_4:
                return 25*2;
            case STAGE_5:
                return 15*2;
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.RagePower;
    }

    public Object usePower(Object... args) {
        return null;
    }

    @Override
    public String getName() {
        try{
            return "RagePower | Lvl: " + getXLM().getLevel() + " | "+ getXLM().getXP();
        }catch (Exception e){
            return "Rage Power | Error Formatting";
        }
    }

    @Override
    public void onTick(int tick) {
        super.onTick(tick);
    }

}