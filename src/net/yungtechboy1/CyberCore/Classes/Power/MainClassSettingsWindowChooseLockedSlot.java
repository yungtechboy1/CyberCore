package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindow;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class MainClassSettingsWindowChooseLockedSlot extends CyberFormCustom {
    BaseClass _BC;
    LockedSlot LS;
    public MainClassSettingsWindowChooseLockedSlot(String title, BaseClass _BC, LockedSlot ls) {
        super(FormType.MainForm.Main_Class_Settings_Window_Active_Powers, title);
        this._BC = _BC;
        LS = ls;
        inti();
    }
    public MainClassSettingsWindowChooseLockedSlot(BaseClass _BC, LockedSlot ls) {
        super(FormType.MainForm.Main_Class_Settings_Window_Active_Powers, _BC.getDisplayName()+" Power Slot Settings");
        this._BC = _BC;
        LS = ls;
        inti();
    }

    private void inti() {
        ArrayList<String> l = new ArrayList<>();
        for (PowerData pd : _BC.getClassSettings().getPowerDataList()) {
            if (!pd.getNeedsLockedSlot()) continue;//Can not Enable NOT LockedSlot Powers here
            boolean e = pd.getActive();
            PowerEnum pe = pd.getPowerID();
            PowerAbstract p = _BC.getPower(pe);
            String pn = p.getDispalyName();
            l.add(pn);
        }
        addElement(new ElementDropdown("Choose Which Power Will be In Slot "+LS.getSlot(), l));
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
            System.out.println(getResponse().getDropdownResponse(0));
        p.showFormWindow(_BC.getSettingsWindow());
        return true;
    }
}
