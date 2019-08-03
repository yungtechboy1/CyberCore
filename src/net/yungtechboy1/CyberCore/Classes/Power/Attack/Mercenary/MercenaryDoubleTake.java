package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;

import java.util.ArrayList;

public class MercenaryDoubleTake extends StagePowerAbstract {
    public MercenaryDoubleTake(BaseClass bc) {
        super(bc);
        setPowerSettings(false, false, false, true);
    }

    @Override
    public double getPowerSourceCost() {
        return 20;
    }

    @Override
    public int getPowerSuccessChance() {
        return 10;
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
    public ArrayList<Class> getAllowedClasses() {
        return null;
    }

    @Override
    public int getRunTimeTick() {
        return 20*30;
    }

    @Override
    public void onActivate() {
        getPlayer().sendMessage(TextFormat.GOLD + "[ABILITY] > Double Take Activated!");
    }

    @Override
    public void whileAbilityActive() {

    }

    @Override
    public void onAbilityDeActivate() {

    }
}
