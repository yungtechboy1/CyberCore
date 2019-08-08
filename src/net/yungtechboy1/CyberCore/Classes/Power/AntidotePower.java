package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public class AntidotePower extends StagePowerAbstract {
    public AntidotePower(BaseClass baseClass) {
        super(baseClass);
    }

    public AntidotePower(BaseClass b, AdvancedPowerEnum ape) {
        super(b, ape);
    }

    public AntidotePower(AdvancedPowerEnum ape) {
        super(ape);
    }

    @Override
    public StageEnum getMaxStage() {
        return StageEnum.STAGE_5;
    }

    @Override
    public int getTickUpdate() {
        return getTickInterval();
    }

    @Override
    public int getPowerSuccessChance() {
        return 100;
    }

    @Override
    public double getPowerSourceCost() {
        return 25;
    }

    @Override
    public ArrayList<Class> getAllowedClasses() {
        return null;
    }

    @Override
    public PowerSettings getPowerSettings() {
        return new PowerSettings(true,false,true,false);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public void onActivate() {
        getPlayer().sendMessage(TextFormat.GREEN+"POWER > "+getDispalyName()+" has been activated!");
    }

    @Override
    protected int getCooldownTimeSecs() {
        return getRunTimeTick() * 2 / 20;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.AntidotePower;
    }

    @Override
    public int getRunTimeTick() {
        switch (getStage()){
            default:
            case STAGE_1:
                return 30*20;
            case STAGE_2:
            case STAGE_3:
            case STAGE_4:
                return 45*20;
            case STAGE_5:
                return 35*20;
        }
    }

    public int getTickInterval(){
        switch (getStage()){
            case STAGE_1:
            case STAGE_3:
                return 10*20;
            default:
            case STAGE_2:
                return 15*20;
            case STAGE_4:
            case STAGE_5:
                return 7*20;
        }
    }

    public int getHealAmount(){
        switch (getStage()){
            default:
            case STAGE_1:
                return 1;
            case STAGE_2:
            case STAGE_3:
            case STAGE_4:
                return 2;
            case STAGE_5:
                return 3;
        }
    }

    @Override
    public String getName() {
        return "Antidote Power";
    }

    @Override
    public void whileAbilityActive() {
        super.whileAbilityActive();
        getPlayer().heal(getHealAmount());
    }
}
