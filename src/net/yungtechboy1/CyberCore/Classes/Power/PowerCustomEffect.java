package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import javax.xml.soap.Text;

public class PowerCustomEffect extends Power {
    public Effect PotionEffect = null;
    public Boolean PotionOnSelf = false;
    int DurationTicks = 0;
    public PowerCustomEffect(BaseClass b, int psc, int lvl) {
        super(b, psc, lvl);
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
    public final Object usePower(CorePlayer cp, Object... args) {
        if (getPotionOnSelf()) {
            cp.sendMessage(TextFormat.GREEN+"["+getDispalyName()+"] Effect Active");
            cp.addEffect(getEffect());
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
