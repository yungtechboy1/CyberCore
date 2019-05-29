package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.CorePlayer;

public abstract class PassivePowerEvent extends PowerEvent {

    public PassivePowerEvent(int lvl, CorePlayer p) {
        super(lvl, p);
    }

    public PassivePowerEvent(int psc, int lvl, CorePlayer p) {
        super(psc, lvl, p);
    }
}
