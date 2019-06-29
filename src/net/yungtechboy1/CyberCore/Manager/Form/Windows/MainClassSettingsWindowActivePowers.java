package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.PowerData;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class MainClassSettingsWindowActivePowers extends CyberFormCustom {
    BaseClass _BC;

    public MainClassSettingsWindowActivePowers(String title, BaseClass _BC) {
        super(FormType.MainForm.Main_Class_Settings_Window_Active_Powers, title);
        this._BC = _BC;
        inti();
    }

    public MainClassSettingsWindowActivePowers(BaseClass _BC) {
        super(FormType.MainForm.Main_Class_Settings_Window_Active_Powers, _BC.getDisplayName() + " Passive Power Settings");
        this._BC = _BC;
        inti();
    }

    private void inti() {
        for (PowerData pd : _BC.getClassSettings().getPowerDataList()) {
            System.out.println("YEAHHHHH >>> "+pd+" || "+pd.getNeedsLockedSlot()+"||"+pd.getPowerID()+"||"+pd.getPowerID().name());
            if (check(pd)) continue;//Can not Enable LockedSlot Powers here
            System.out.println("\\/\\/\\/\\/ NExt!!");
            boolean e = pd.getActive();
            PowerEnum pe = pd.getPowerID();
            PowerAbstract p = _BC.getPower(pe,false);
            System.out.println("NOW PPPPPP >>> "+p+" || ");//+p.getDispalyName()+"||"+p.getName());
            String pn ;
            if(p == null) pn = "UNKNOWN?!?"+e;
            else pn = p.getDispalyName();
            addElement(new ElementToggle(pn+ TextFormat.GRAY, e));
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
            if (check(pd)) continue;//Skip like above
            boolean on = getResponse().getToggleResponse(key);
            boolean b = _BC.getClassSettings().getActivatedPowers().contains(pd.getPowerID());
            if (on && !b) {
                _BC.getClassSettings().getActivatedPowers().add(pd.getPowerID());
                _BC.getPlayer().sendMessage(TextFormat.GREEN+"POWER > "+pd.get);
            } else if (!on && b) {
                _BC.getClassSettings().getActivatedPowers().remove(pd.getPowerID());
            }
            key++;
        }
        p.showFormWindow(_BC.getSettingsWindow());
        return key > 0;
    }
}
