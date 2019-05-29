package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;

public abstract class PowerToggle extends Power {

    private boolean MasterToggle = false;
    private CoolDown OnCooldown = null;

    public PowerToggle(int lvl, CorePlayer p) {
        super(lvl, p);
    }

    public PowerToggle(int psc, int lvl, CorePlayer p) {
        super(psc, lvl, p);
    }

    public boolean getMasterToggle() {
        return MasterToggle;
    }

    public void setMasterToggle(boolean masterToggle) {
        MasterToggle = masterToggle;
    }

    public void setMasterToggle() {
        setMasterToggle(true);
    }

    public void ToggleOn(){
        addCooldown();
    }

    @Override
    public Object usePower(CorePlayer cp, Object... args) {
        ToggleOn();
        return super.usePower(cp);
    }
}
