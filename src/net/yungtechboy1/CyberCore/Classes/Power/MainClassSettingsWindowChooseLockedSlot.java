package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.form.element.ElementDropdown;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import java.util.ArrayList;

public class MainClassSettingsWindowChooseLockedSlot extends CyberFormCustom {
    BaseClass _BC;
    LockedSlot _LS;

    public MainClassSettingsWindowChooseLockedSlot(String title, BaseClass _BC, LockedSlot ls) {
        super(FormType.MainForm.Main_Class_Settings_Window_Active_Powers, title);
        this._BC = _BC;
        _LS = ls;
        inti();
    }

    public MainClassSettingsWindowChooseLockedSlot(BaseClass _BC, LockedSlot ls) {
        super(FormType.MainForm.Main_Class_Settings_Window_Active_Powers, _BC.getDisplayName() + " Power Slot InternalPlayerSettings");
        this._BC = _BC;
        _LS = ls;
        inti();
    }

    private void inti() {
        int d = 0;
        int k = 0;
        ArrayList<String> l = new ArrayList<>();
        l.add("N/A");
        for (PowerData pd : _BC.getClassSettings().getPowerDataList()) {
            k++;
            if (!pd.getNeedsLockedSlot()) continue;//Can not Enable NOT LockedSlot Powers here
            boolean e = pd.getActive();
            PowerEnum pe = pd.getPowerID();
            if (pe == _BC.getClassSettings().getPreferedSlot(_LS)) d = k;
            PowerAbstract p = _BC.getPossiblePower(pe);
            String pn = p.getDispalyName();
            l.add(pn);
        }
        addElement(new ElementDropdown("Choose Which Power Will be In Slot " + _LS.getSlot(), l, d));
//        addButton(new ElementButton("<< Back"));
    }

    /**
     * Return True only if a Response has been executed
     *
     * @param p CorePlayer
     * @return boolean
     */
    @Override
    public boolean onRun(CorePlayer p) {
        if (super.onRun(p)) return true;
        int k = getResponse().getDropdownResponse(0).getElementID();
        if (k == 0) {
            _BC.deactivatePower(_BC.getClassSettings().getPreferedSlot(_LS));
        } else {
            int kk = 0;
            for (PowerData pd : _BC.getClassSettings().getPowerDataList()) {

                if (!pd.getNeedsLockedSlot()) continue;//Can not Enable NOT LockedSlot Powers here
                kk++;
                if (kk == k) {
                    if(!pd.getActive()){
                        p.sendMessage("Attempting to set active slot and Class!");
                        _BC.activatePower(pd.getPowerID());
                    }
                }
            }
        }


        p.showFormWindow(_BC.getSettingsWindow());
        return true;
    }
}
