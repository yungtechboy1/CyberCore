package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

public class PowerSettings {
    private boolean isPassive = false;
    private boolean isHotbar = false;
    private boolean isAbility = false;
    private boolean isEffect = false;
    public PowerSettings() {

    }

    public PowerSettings(boolean isPassive, boolean isHotbar, boolean isAbility, boolean isEffect) {
        this.isPassive = isPassive;
        this.isHotbar = isHotbar;
        this.isAbility = isAbility;
        this.isEffect = isEffect;
    }

    public boolean isPassive() {
        return isPassive;
    }
//    private boolean is;

    public void setPassive(boolean passive) {
        isPassive = passive;
    }

    public boolean isHotbar() {
        return isHotbar;
    }

    public void setHotbar(boolean hotbar) {
        isHotbar = hotbar;
    }

    public boolean isAbility() {
        return isAbility;
    }

    public void setAbility(boolean ability) {
        isAbility = ability;
    }

    public boolean isEffect() {
        return isEffect;
    }

    public void setEffect(boolean effect) {
        isEffect = effect;
    }
}
