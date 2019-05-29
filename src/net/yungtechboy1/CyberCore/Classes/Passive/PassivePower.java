package net.yungtechboy1.CyberCore.Classes.Passive;

import net.yungtechboy1.CyberCore.Classes.Power.Power;

public abstract class PassivePower extends Power {
    public PassivePower(int lvl) {
        this(100, lvl);
    }

    public PassivePower(int psc, int lvl) {
        super(psc, lvl);
    }


    public int getType() {
        return -2;
    }
}
