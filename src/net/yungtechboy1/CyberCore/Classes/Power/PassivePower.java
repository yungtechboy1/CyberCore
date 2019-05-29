package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.CorePlayer;

public abstract class PassivePower extends Power{

    public PassivePower(int lvl) {
        super(lvl);
    }

    public PassivePower(int lvl, CorePlayer p) {
        super(lvl, p);
    }

    public PassivePower(int psc, int lvl) {
        super(psc, lvl);
    }

    public PassivePower(int psc, int lvl, CorePlayer p) {
        super(psc, lvl, p);
    }
}
