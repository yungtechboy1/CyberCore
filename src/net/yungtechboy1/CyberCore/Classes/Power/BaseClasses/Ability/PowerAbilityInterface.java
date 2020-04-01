package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

public interface PowerAbilityInterface {

    int getRunTimeTick();

    boolean isActive();

    void setActive(boolean active);

//    //    abstract void activate();
//    default void activate() {
//        if (isActive()) return;
//        setEnabled(true);
//        setDeActivatedTick(Server.getInstance().getTick() + getRunTimeTick());
//    }


    void whileAbilityActive();

    void onAbilityDeActivate();

}
