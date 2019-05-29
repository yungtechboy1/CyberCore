package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.CorePlayer;

public abstract class PowerCooldown extends Power{
    public PowerCooldown(int lvl, CorePlayer p) {
        super(lvl, p);
    }

    public PowerCooldown(int psc, int lvl, CorePlayer p) {
        super(psc, lvl, p);
    }
}
