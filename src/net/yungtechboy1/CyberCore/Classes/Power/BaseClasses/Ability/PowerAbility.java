package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import cn.nukkit.Server;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Power;

public abstract class PowerAbility extends Power {
    private boolean Active = false;
    private int DeActivatedTick = -1;
    public PowerAbility(BaseClass bc, int psc) {

        super(bc, psc);
        TickUpdate = 10;//Every 10 Ticks
    }

    public PowerAbility(BaseClass b, int psc, double cost) {
        super(b, psc, cost);
        TickUpdate = 10;//Every 10 Ticks
    }

    public int getRunTimeTick() {
        return getStage().getValue()*20;//1-5 Secs
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    private void activate() {
        if (isActive()) return;
        Active = true;
        DeActivatedTick = Server.getInstance().getTick() + getRunTimeTick();
    }

    @Override
    public void onTick(int tick) {
        if (isActive()) {
            if (tick >= DeActivatedTick) {
                Active = false;
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

    public abstract void onAbilityActivate();

    public abstract void onAbilityDeActivate();


}
