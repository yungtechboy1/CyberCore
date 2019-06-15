package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerPublicInterface;

public abstract class PowerAbility extends PowerPublicInterface implements PowerAbilityInterface {

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
    public boolean isActive() {
        return Active;
    }

    @Override
    public void setActive(boolean active) {
        Active = active;
    }

    @Override
    public void onTick(int tick) {
        if (isActive()) {
            whileAbilityActive();
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

}
