package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class MercenaryDisarm extends Power {
    public MercenaryDisarm(BaseClass b, int lvl) {
        super(b, 2, lvl);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof CorePlayer)InitPowerRun(e.getEntity());
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MercenaryDisarm;
    }

    @Override
    public int getPowerSuccessChance() {
        switch (getStage()){
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
                return 2;
        }
    }

    @Override
    public Object usePower(CorePlayer cp, Object... args) {
        CorePlayer tp = (CorePlayer) args[0];
        NukkitRandom nr = new NukkitRandom();
        if(tp.getInventory().isFull()){
            tp.getInventory().setHeldItemSlot(nr.nextRange(0,6));
            tp.sendMessage(cp.getDisplayName()+ TextFormat.AQUA+" USED ON YOU [ EFFECT ] "+getDispalyName());
        }else{
            tp.getInventory().setHeldItemSlot(nr.nextRange(0,6));
            tp.sendMessage(cp.getDisplayName()+ TextFormat.AQUA+" USED ON YOU [ EFFECT ] "+getDispalyName());
        }
        return null;
    }

    @Override
    public String getName() {
        return "Disarm";
    }
}
