package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.PowerAbility;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;

public class MercenaryDoubleTake extends PowerAbility {
    public MercenaryDoubleTake(BaseClass bc) {
        super(bc,20);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        if(e.getCause() == CustomEntityDamageEvent.CustomDamageCause.DoubleTakeMagic)return e;
        if(e.getEntity() instanceof CorePlayer) {
            float d = e.getOriginalDamage();
            if(!isActive())initPowerRun((CorePlayer)e.getEntity());
            else {
                NukkitRandom nr = new NukkitRandom();
                if (nr.nextRange(0, 100) <= getPowerSuccessChance()) {
                    //Success
                    ((CorePlayer)e.getEntity()).attack(new CustomEntityDamageByEntityEvent(getPlayer(),e.getEntity(), CustomEntityDamageEvent.CustomDamageCause.DoubleTakeMagic,d));
                }
            }
        }
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MercenaryDoubleTake;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDispalyName() {
        return null;
    }



    @Override
    public boolean CanRun(boolean force, Object... args) {
        if(force)return true;
        if(super.CanRun(force,args)) {
            CorePlayer cp = getPlayer();
            return !(cp.getHealth() > 8);
        }
        return false;
    }

    @Override
    public int getRunTimeTick() {
        return 20*30;
    }

    @Override
    public void onAbilityActivate() {
        getPlayer().sendMessage(TextFormat.GOLD+"[ABILITY] > Double Take Activated!");
    }

    @Override
    public void onAbilityDeActivate() {

    }
}
