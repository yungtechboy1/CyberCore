package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class MercenaryDisarm extends PowerAbstract {
    public MercenaryDisarm(BaseClass b) {
        super(b,null, 2);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof CorePlayer)initPowerRun(e.getEntity());
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
    public Object usePower( Object... args) {
        CorePlayer tp = (CorePlayer) args[0];
        NukkitRandom nr = new NukkitRandom();
        if(tp.getInventory().isFull()){
            tp.getInventory().setHeldItemSlot(nr.nextRange(0,6));
            tp.sendMessage(getPlayer().getDisplayName()+ TextFormat.AQUA+" USED ON YOU [ EFFECT ] "+getDispalyName());
        }else{
            tp.getInventory().setHeldItemSlot(nr.nextRange(0,6));
            tp.sendMessage(getPlayer().getDisplayName()+ TextFormat.AQUA+" USED ON YOU [ EFFECT ] "+getDispalyName());
        }
        return null;
    }

    @Override
    public String getName() {
        return "Disarm";
    }
}
