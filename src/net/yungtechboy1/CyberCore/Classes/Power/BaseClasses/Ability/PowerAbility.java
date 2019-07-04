package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import cn.nukkit.Server;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.ClassLevelingManager;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;

public abstract class PowerAbility extends PowerAbstract implements PowerAbilityInterface {

//    private boolean Active = false;
    private int DeActivatedTick = -1;

    public PowerAbility(BaseClass b, ClassLevelingManager lt, int psc) {
        super(b, lt, psc);
        TickUpdate = 10;//Every 10 Ticks
    }

    public PowerAbility(BaseClass b, ClassLevelingManager lm, int psc, double cost) {
        super(b, lm, psc, cost);
        TickUpdate = 10;//Every 10 Ticks
    }

    public int getDeActivatedTick() {
        return DeActivatedTick;
    }

    public void setDeActivatedTick(int deActivatedTick) {
        DeActivatedTick = deActivatedTick;
    }

    @Override
    public int getRunTimeTick() {
        return getStage().getValue() * 20;//1-5 Secs
    }

    @Override
    public boolean CanRun(boolean force, Object... args) {
        if (isActive()) return false;
        return super.CanRun(force, args);
    }

//    @Override
//    public void setActive(boolean active) {
//        Active = active;
//    }

    public final void activate() {
        if (isActive()) return;
        setActive(true);
        DeActivatedTick = Server.getInstance().getTick() + getRunTimeTick();
        onActivate();
    }

    public abstract void onActivate();

    @Override
    public void onTick(int tick) {
        //Only For Deactivation
        System.out.println("POWER TICKKKKKK2");
        if (isActive()) {
            System.out.println("POWER TICKKKKKK3");
            whileAbilityActive();
            if (tick >= DeActivatedTick) {
                System.out.println("POWER TICKKKKKK444444444444444444444444444444444444444444444444444444444444444444444");
                setActive(false);
                DeActivatedTick = -1;
                onAbilityDeActivate();
            }
        }
    }

    @Override
    public final Object usePower(Object... args) {
        activate();
        onAbilityActivate();
        return null;
    }

}
