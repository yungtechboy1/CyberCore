package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public class MercenaryDisarm extends StagePowerAbstract {
    public MercenaryDisarm(BaseClass b) {
        super(b);
//        MainPowerType = PowerType.Ability;
    }

    public MercenaryDisarm(BaseClass b, AdvancedPowerEnum ape) {
        super(b, ape);
    }

    @Override
    public PowerSettings getPowerSettings() {
        return new PowerSettings(true, false, false, true);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof CorePlayer) initPowerRun(e.getEntity());
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MercenaryDisarm;
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
    public int getPowerSuccessChance() {
        switch (getStage()) {
            default:
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
        }
    }

    @Override
    public Object usePower(Object... args) {
        CorePlayer tp = (CorePlayer) args[0];
        NukkitRandom nr = new NukkitRandom();
        if (tp.getInventory().isFull()) {
            tp.getInventory().setHeldItemSlot(nr.nextRange(0, 6));
            tp.sendMessage(getPlayer().getDisplayName() + TextFormat.AQUA + " USED ON YOU [ EFFECT ] " + getDispalyName());
        } else {
            tp.getInventory().setHeldItemSlot(nr.nextRange(0, 6));
            tp.sendMessage(getPlayer().getDisplayName() + TextFormat.AQUA + " USED ON YOU [ EFFECT ] " + getDispalyName());
        }
        return null;
    }

    @Override
    public String getName() {
        return "Disarm";
    }
}
