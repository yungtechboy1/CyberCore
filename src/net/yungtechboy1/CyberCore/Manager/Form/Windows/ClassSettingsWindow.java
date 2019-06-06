package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.List;

public class ClassSettingsWindow extends CyberFormSimple {
    BaseClass _BC;

    public ClassSettingsWindow(BaseClass bd, FormType.MainForm ttype, String title, String content) {
        super(ttype, title, content);
        _BC = bd;
        inti();
    }

    public ClassSettingsWindow(BaseClass bd, FormType.MainForm ttype, String title, String content, List<ElementButton> buttons) {
        super(ttype, title, content, buttons);
        _BC = bd;
        inti();

    }

    @Deprecated
    public ClassSettingsWindow(BaseClass bd, String title, String content) {
        super(FormType.MainForm.Class_Settings_Window, title, content);
        _BC = bd;
        inti();

    }

    @Deprecated
    public ClassSettingsWindow(BaseClass bd, String title, String content, List<ElementButton> buttons) {
        super(FormType.MainForm.Class_Settings_Window, title, content, buttons);
        _BC = bd;
        inti();

    }

    private void inti() {
        addButton(new ElementButton("How to use " + TextFormat.AQUA + _BC.getName()));
        addButton(new ElementButton("Class Merchant"));
        addButton(new ElementButton(TextFormat.RED + "Leave Class"));
        addButton(new ElementButton(TextFormat.RED + "--------------"));
    }

    /**
     * Return True only if a Response has been executed
     * @param p CorePlayer
     * @return boolean
     */
    @Override
    public boolean onRun(CorePlayer p) {
        if (super.onRun(p)) return true;
        int k = getResponse().getClickedButtonId();
        if (k <= 3) {
            if (k == 0) {
                //TODO
                p.showFormWindow(p.GetPlayerClass().getHowToUseClassWindow());
            } else if (k == 1) {
                p.showFormWindow(p.GetPlayerClass().getClassMerchantWindow());
            } else if (k == 2) {
                CyberCoreMain.getInstance().ClassFactory.leaveClass(p);
            } else if (k == 3) {
                //Null
            }
            return true;
        }
        return false;
    }
}
