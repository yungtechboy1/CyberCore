package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public class MercenaryReneration extends StagePowerAbstract {
    public int _incombat = 0;

    public MercenaryReneration(BaseClass bc) {
        super(bc);
//        TickUpdate = 20 * 5;//Every 5 Secs make sure player is in combat
        PlayerToggleable = false;
    }

    @Override
    public int getTickUpdate() {
        return 20 * 5;
    }

    @Override
    public StageEnum getMaxStage() {
        return StageEnum.STAGE_5;
    }

    @Override
    public ArrayList<Class> getAllowedClasses() {
        return null;
    }

    @Override
    public PowerSettings getPowerSettings() {
        return new PowerSettings(false, false, true, false);
    }

    @Override
    public double getPowerSourceCost() {
        return 30;
    }

    @Override
    public int getPowerSuccessChance() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return (30);
            case STAGE_2:
                return (40);
            case STAGE_3:
                return (50);
            case STAGE_4:
                return (60);
            case STAGE_5:
                return (75);
        }
    }

    @Override
    public void initStages() {
        super.initStages();
    }

    @Override
    public boolean CanRun(boolean force, Object... o) {
        if (force) return true;
        return super.CanRun(force, o);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }

    @Override
    protected int getCooldownTimeSecs() {
        return 30;
    }

    @Override
    public void onActivate() {
        EntityRegainHealthEvent e = new EntityRegainHealthEvent(getPlayer(), 1, EntityRegainHealthEvent.CAUSE_REGEN);
        getPlayer().heal(e);
        getPlayer().sendMessage(TextFormat.GOLD + "[ABILITY] > Mercenary Regeneration Activated!");
    }

    @Override
    public void onTick(int tick) {
        super.onTick(tick);
        if (PlayerClass.getPlayer().checkCombat()) {
            _incombat++;
        } else {
            _incombat = 0;
        }

        if (_incombat > 6) {
            //Allow Heal, Run PowerAbstract
            initPowerRun();
            _incombat = 0;
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MercenaryRegeneration;
    }

    @Override
    public void whileAbilityActive() {

    }

    @Override
    public void onAbilityDeActivate() {

    }

    @Override
    public String getName() {
        return "Mercenary Regeneration";
    }

    @Override
    public String getDispalyName() {
        return getName();
    }
}
