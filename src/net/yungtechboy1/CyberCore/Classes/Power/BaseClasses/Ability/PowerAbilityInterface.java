package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import cn.nukkit.Server;

public interface PowerAbilityInterface {

    int getRunTimeTick();

    boolean isActive();

    void setActive(boolean active);

    void setDeActivatedTick(int deActivatedTick);

    //    abstract void activate();
    default void activate() {
        if (isActive()) return;
        setActive(true);
        setDeActivatedTick(Server.getInstance().getTick() + getRunTimeTick());
    }


    void onAbilityActivate();
    void whileAbilityActive();

    void onAbilityDeActivate();

}