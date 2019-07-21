package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Passive;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.ClassLevelingManager;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;

public abstract class PassivePower extends PowerAbstract {
    public PassivePower(BaseClass b,ClassLevelingManager lm) {
        this(b,lm,null,100);
    }

    public PassivePower(BaseClass b, ClassLevelingManager lm, PowerSettings ps,int psc) {
        super(b,lm,ps,psc);
    }

    @Override
    public boolean CanRun(boolean force, Object... args) {
        return super.CanRun(force, args);
    }
}
