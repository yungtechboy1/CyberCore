package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.Classes.New.Offense.Mercenary;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class MercenaryBlindingStrike extends StagePowerAbstract {

    public MercenaryBlindingStrike(Mercenary bc) {
        super(bc, 1, 2);
        setPowerSettings(false, true, false, true);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof CorePlayer)initPowerRun(e.getEntity());
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MercenaryBlindingStrike;
    }

    @Override
    public int getPowerSuccessChance() {
        switch (getStage()) {
            case STAGE_1:
                return 2;
            case STAGE_2:
                return 5;
            case STAGE_3:
                return 8;
            case STAGE_4:
                return 12;
            case STAGE_5:
                return 17;
            case NA:
            default:
                return 10;
        }
    }

    @Override
    public Effect getEffect() {
        return Effect.getEffect(Effect.BLINDNESS);
    }

    @Override
    public String getName() {
        return "Blinding Strike";
    }
}
