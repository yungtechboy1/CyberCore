package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

public class ClassSettingsWindow extends CyberFormCustom {
    BaseClass _BC;

    public ClassSettingsWindow(BaseClass bd, String title) {
        super(FormType.MainForm.Class_Settings_Window, title);
//        _BC = bd;
        inti();
    }

    private void inti() {

    }

    /**
     * Return True only if a Response has been executed
     * @param p CorePlayer
     * @return boolean
     */
    @Override
    public boolean onRun(CorePlayer p) {
        if (super.onRun(p)) return true;
        int k = 0;
//        int k = getResponse().getClickedButtonId();
        if (k <= 3) {
            if (k == 0) {
                //TODO
                p.showFormWindow(p.getPlayerClass().getHowToUseClassWindow());
            } else if (k == 1) {
                p.showFormWindow(p.getPlayerClass().getClassMerchantWindow());
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