package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;

public class PowerData {
    PowerEnum PowerID = PowerEnum.Unknown;
    Boolean Active = false;
    Boolean NeedsLockedSlot = false;
    LockedSlot LS = LockedSlot.NA;

    public PowerData(PowerEnum powerID, Boolean active) {
        PowerID = powerID;
        Active = active;
    }
    public PowerData(PowerEnum powerID, Boolean active, Boolean nls) {
        PowerID = powerID;
        Active = active;
        NeedsLockedSlot = nls;
    }

    public PowerData(PowerEnum powerID, Boolean active, Boolean nls, LockedSlot LS) {
        PowerID = powerID;
        NeedsLockedSlot = nls;
        Active = active;
        this.LS = LS;
    }

    public Boolean getNeedsLockedSlot() {
        return NeedsLockedSlot;
    }

    public void setNeedsLockedSlot(Boolean needsLockedSlot) {
        NeedsLockedSlot = needsLockedSlot;
    }

    public PowerEnum getPowerID() {
        return PowerID;
    }

    public void setPowerID(PowerEnum powerID) {
        PowerID = powerID;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    public LockedSlot getLS() {
        return LS;
    }

    public void setLS(LockedSlot LS) {
        this.LS = LS;
    }
}
