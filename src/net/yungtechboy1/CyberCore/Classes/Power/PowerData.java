package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;

public class PowerData {
    PowerAbstract PA = null;
    PowerEnum PowerID = PowerEnum.Unknown;
    Boolean Active = false;
    Boolean NeedsLockedSlot = false;
    LockedSlot LS = LockedSlot.NA;

    public PowerData(PowerEnum powerID, Boolean active) {
        PowerID = powerID;
        Active = active;
    }

    public PowerData(PowerAbstract PA) {
        this.PA = PA;
        PowerID = PA.getType();
        if(PA.getPowerSettings() != null){
            NeedsLockedSlot = PA.getPowerSettings().isHotbar();
        }
    }

    public PowerData(PowerEnum powerID, Boolean active, Boolean nls) {
        PowerID = powerID;
        Active = active;
        NeedsLockedSlot = nls;
    }

    public PowerData(PowerEnum powerID, Boolean active, Boolean nls, LockedSlot LS, PowerAbstract pa) {
        PowerID = powerID;
        NeedsLockedSlot = nls;
        Active = active;
        PA = pa;
        this.LS = LS;
    }

    public PowerAbstract getPA(){
        return PA;
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

    public void setLS(LockedSlot l) {
        if(l != LockedSlot.NA)NeedsLockedSlot = true;
        LS = l;
    }
}
