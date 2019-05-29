package net.yungtechboy1.CyberCore.Classes.Passive;

import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.CorePlayer;

public abstract class PassivePower extends Power {

    public PassivePower(int lvl, CorePlayer p) {
        super(lvl, p);
    }

    public PassivePower(int psc, int lvl, CorePlayer p) {
        super(psc, lvl, p);
    }

    public PowerType getType() {
        return PowerType.PassivePower;
    }
}
