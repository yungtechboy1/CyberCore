package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementToggle;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.PowerData;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

public class MainClassSettingsWindowActivePowers extends CyberFormCustom {
    BaseClass _BC;

    public MainClassSettingsWindowActivePowers(String title, BaseClass _BC) {
        super(FormType.MainForm.Main_Class_Settings_Window_Active_Powers, title);
        this._BC = _BC;
        inti();
    }

    public MainClassSettingsWindowActivePowers(BaseClass _BC) {
        super(FormType.MainForm.Main_Class_Settings_Window_Active_Powers, _BC.getDisplayName() + " Power Settings");
        this._BC = _BC;
        inti();
    }

    private void inti() {
        for (PowerData pd : _BC.getClassSettings().getPowerDataList()) {
            if (check(pd)) continue;//Can not Enable LockedSlot Powers here
            boolean e = pd.getActive();
            PowerEnum pe = pd.getPowerID();
            PowerAbstract p = _BC.getPower(pe,false);
            String pn ;
            if(p == null) pn = "UNKNOWN?!?"+e;
            else pn = p.getDispalyName();
            addElement(new ElementToggle(pn, e));
        }
//        addButton(new ElementButton("<< Back"));
    }

    private boolean check(PowerData pd) {
        return (pd.getLS() != LockedSlot.NA || pd.getNeedsLockedSlot());//Can not Enable LockedSlot Powers here
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
        int key = 0;
        for (PowerData pd : _BC.getClassSettings().getPowerDataList()) {
            boolean on = getResponse().getToggleResponse(key);
            boolean b = _BC.getClassSettings().getActivatedPowers().contains(pd.getPowerID());
            if (on && !b) {
                _BC.getClassSettings().getActivatedPowers().add(pd.getPowerID());
            } else if (!on && b) {
                _BC.getClassSettings().getActivatedPowers().remove(pd.getPowerID());
            }
            key++;
        }
        p.showFormWindow(_BC.getSettingsWindow());
        return key > 0;
    }
}
