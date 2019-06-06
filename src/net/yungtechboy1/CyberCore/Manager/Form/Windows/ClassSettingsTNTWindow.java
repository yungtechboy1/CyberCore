package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;

public class ClassSettingsTNTWindow extends ClassSettingsWindow {
    public ClassSettingsTNTWindow(BaseClass bc) {
        super(bc, FormType.MainForm.Class_Settings_TNT, "CyberFactions | TNT-Specialist Settings", TextFormat.AQUA + "Current XP: " + TextFormat.GREEN + bc.XPRemainder(bc.getXP()) + "\n" + TextFormat.AQUA + "Current Level: " + TextFormat.GREEN + bc.getLVL());
//        _C = c;
    }

    @Override
    public boolean onRun(CorePlayer p) {
        if (super.onRun(p)) return true;
        int k = getResponse().getClickedButtonId();
        if (k == 0) {
            //TODO
            p.showFormWindow(_BC.getHowToUseClassWindow());
        } else if (k == 1) {

        }
        return false;
    }
}
