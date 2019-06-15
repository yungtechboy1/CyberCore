package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.PowerAbility;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class MercenaryReneration extends PowerAbility {
    public MercenaryReneration(BaseClass bc) {
        super(bc,30);
        TickUpdate = 20 * 5;//Every 5 Secs make sure player is in combat
        PlayerToggleable = false;
    }

    @Override
    public void initStages() {
        super.initStages();
        switch (getStage()) {
            case STAGE_1:
                setPowerSuccessChance(30);
                break;
            case STAGE_2:
                setPowerSuccessChance(40);
                break;
            case STAGE_3:
                setPowerSuccessChance(50);
                break;
            case STAGE_4:
                setPowerSuccessChance(60);
                break;
            case STAGE_5:
                setPowerSuccessChance(75);
                break;
            default:
                setPowerSuccessChance(30);
                break;
        }
    }

    @Override
    public boolean CanRun(boolean force, Object... o) {
        if(force)return true;
        boolean s = super.CanRun(force,o);
        return s;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }

    @Override
    protected int getCooldownTime() {
        return 30;
    }

    public int _incombat = 0;

    @Override
    public void onTick(int tick) {
        super.onTick(tick);
        if(PlayerClass.getPlayer().checkCombat()){
            _incombat++;
        }else{
            _incombat = 0;
        }

        if(_incombat > 6){
            //Allow Heal, Run PowerPublicInterface
            initPowerRun();
            _incombat = 0;
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MercenaryRegeneration;
    }

    @Override
    public void onAbilityActivate() {
        EntityRegainHealthEvent e = new EntityRegainHealthEvent(getPlayer(),1, EntityRegainHealthEvent.CAUSE_REGEN);
        getPlayer().heal(e);
        getPlayer().sendMessage(TextFormat.GOLD+"[ABILITY] > Mercenary Regeneration Activated!");
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
