package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Passive;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;

public abstract class PassivePower extends PowerAbstract {
    public PassivePower(BaseClass b) {
        this(b,100);
    }

    public PassivePower(BaseClass b,int psc) {
        super(b,psc);
    }

    @Override
    public boolean CanRun(boolean force, Object... args) {
        return super.CanRun(force, args);
    }
}
