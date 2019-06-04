package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

public abstract class PowerAbility extends Power {
    boolean Active = false;
    public PowerAbility(BaseClass bc, int psc, int lvl) {
        super(bc,psc, lvl);
    }

    public int getRunTime(){
        return getStage().getValue() * 20;
    }

    public boolean isActive(){
        return Active;
    }

    public void activate(){

    }




}
