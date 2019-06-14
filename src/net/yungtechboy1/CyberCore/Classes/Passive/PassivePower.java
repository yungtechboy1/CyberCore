package net.yungtechboy1.CyberCore.Classes.Passive;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Power;

public abstract class PassivePower extends Power {
    public PassivePower(BaseClass b) {
        this(b,100);
    }

    public PassivePower(BaseClass b,int psc) {
        super(b,psc);
    }
}
