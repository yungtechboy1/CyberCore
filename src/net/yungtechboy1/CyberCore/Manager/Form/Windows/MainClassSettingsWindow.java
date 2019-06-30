package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.MainClassSettingsWindowChooseLockedSlot;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.List;

public class MainClassSettingsWindow extends CyberFormSimple {
    BaseClass _BC;

    public MainClassSettingsWindow(BaseClass bd, FormType.MainForm ttype, String title, String content) {
        super(ttype, title, content);
        _BC = bd;
        inti();
    }

    public MainClassSettingsWindow(BaseClass bd, FormType.MainForm ttype, String title, String content, List<ElementButton> buttons) {
        super(ttype, title, content, buttons);
        _BC = bd;
        inti();

    }

    @Deprecated
    public MainClassSettingsWindow(BaseClass bd, String title, String content) {
        super(FormType.MainForm.Main_Class_Settings_Window, title, content);
        _BC = bd;
        inti();

    }

    @Deprecated
    public MainClassSettingsWindow(BaseClass bd, String title, String content, List<ElementButton> buttons) {
        super(FormType.MainForm.Main_Class_Settings_Window, title, content, buttons);
        _BC = bd;
        inti();

    }

    private void inti() {
        addButton(new ElementButton("How to use " + TextFormat.AQUA + _BC.getName()));
        addButton(new ElementButton("Class Merchant"));
        addButton(new ElementButton(TextFormat.RED + "Leave Class"));
        addButton(new ElementButton(TextFormat.YELLOW + "----Powers---"));
        addButton(new ElementButton("Choose Active Powers"));
        addButton(new ElementButton("Set Preferred Power For Slot 9"));
        addButton(new ElementButton("Set Preferred Power For Slot 8"));
        addButton(new ElementButton("Set Preferred Power For Slot 7"));
        //Plugin Callback
        _BC.addButtons(this);
        addButton(new ElementButton(TextFormat.BLACK + "-------------"));
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
        int k = getResponse().getClickedButtonId();
        if (k == 0) {
            //TODO
            p.showFormWindow(p.getPlayerClass().getHowToUseClassWindow());
        } else if (k == 1) {
            p.showFormWindow(p.getPlayerClass().getClassMerchantWindow());
        } else if (k == 2) {
            CyberCoreMain.getInstance().ClassFactory.leaveClass(p);
        } else if (k == 3) {
            //Null
        } else if (k == 4) {
            //Choose active Powers
            p.showFormWindow(new MainClassSettingsWindowActivePowers(p.getPlayerClass()));
        }else if (k == 5) {
            //Choose active Powers
            p.showFormWindow(new MainClassSettingsWindowChooseLockedSlot(p.getPlayerClass(), LockedSlot.SLOT_9));
        }else if (k == 6) {
            //Choose active Powers
            p.showFormWindow(new MainClassSettingsWindowChooseLockedSlot(p.getPlayerClass(), LockedSlot.SLOT_8));
        }else if (k == 7) {
            //Choose active Powers
            p.showFormWindow(new MainClassSettingsWindowChooseLockedSlot(p.getPlayerClass(), LockedSlot.SLOT_7));
        }
        return true;
    }
}
