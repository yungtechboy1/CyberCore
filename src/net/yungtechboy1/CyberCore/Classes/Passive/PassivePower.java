package net.yungtechboy1.CyberCore.Classes.Passive;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.Power;

public abstract class PassivePower extends Power {
    public PassivePower(BaseClass b,int lvl) {
        this(b,100, lvl);
    }

    public PassivePower(BaseClass b,int psc, int lvl) {
        super(b,psc, lvl);
    }
}
