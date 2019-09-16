package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;

//@Deprecated
public class PowerData {
    PowerAbstract PA = null;
    AdvancedPowerEnum APE = null;
    Boolean Enabled = false;
    Boolean NeedsLockedSlot = false;
    LockedSlot LS = LockedSlot.NA;

    public AdvancedPowerEnum getAPE() {
        return APE;
    }
//    public PowerData(AdvancedPowerEnum powerID, Boolean enabled) {
//        PowerID = powerID;
//        Enabled = enabled;
//    }

    public PowerData(PowerAbstract PA) {
        this.PA = PA;
        APE = PA.getAPE();
        if(PA.getPowerSettings() != null){
            NeedsLockedSlot = PA.getPowerSettings().isHotbar();
        }
    }
//
//    public PowerData(PowerEnum powerID, Boolean enabled, Boolean nls) {
//        PowerID = powerID;
//        Enabled = enabled;
//        NeedsLockedSlot = nls;
//    }
//
//    public PowerData(PowerEnum powerID, Boolean enabled, Boolean nls, LockedSlot LS, PowerAbstract pa) {
//        PowerID = powerID;
//        NeedsLockedSlot = nls;
//        Enabled = enabled;
//        PA = pa;
//        this.LS = LS;
//    }

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
        return APE.getPowerEnum();
    }

//    public void setPowerID(PowerEnum powerID) {
//        PowerID = powerID;
//    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
    }

    public LockedSlot getLS() {
        return LS;
    }

    public void setLS(LockedSlot l) {
        System.out.println("LOCKEDSLOT22222222 SET FOR "+this.getClass().getName()+" || "+l);
        if(l == null)l = LockedSlot.NA;
        NeedsLockedSlot = (l != LockedSlot.NA);
        if(l != LockedSlot.NA && getPA() != null)getPA().setLS(l);
        LS = l;
    }
}
