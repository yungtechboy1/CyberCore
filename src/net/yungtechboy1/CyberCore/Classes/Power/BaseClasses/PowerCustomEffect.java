package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerPublicInterface;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class PowerCustomEffect extends PowerPublicInterface {
    public Effect PotionEffect = null;
    public Boolean PotionOnSelf = false;

    public int getDurationTicks() {
        return DurationTicks;
    }

    int DurationTicks = 0;
    public PowerCustomEffect(BaseClass b, int psc) {
        super(b, psc);
    }

    public Boolean getPotionOnSelf() {
        return PotionOnSelf;
    }

    public void setPotionOnSelf(boolean pos){
        PotionOnSelf = pos;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.Unknown;
    }

    @Override
    public final Object usePower( Object... args) {
        if (getPotionOnSelf()) {
            getPlayer().sendMessage(TextFormat.GREEN+"["+getDispalyName()+"] Effect Active");
            getPlayer().addEffect(getEffect());
        }else {
            ((Player)args[0]).sendMessage(TextFormat.GREEN+"["+getDispalyName()+"] Effect Active");
            ((Entity)args[0]).addEffect(getEffect());
        }
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public void setDurationTicks(int d) {
        DurationTicks = d;
    }

    public int getEffectDuration() {
        return DurationTicks;
    }

    public Effect getEffect() {
        return PotionEffect;
    }
}
