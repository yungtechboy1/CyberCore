package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.math.Vector3;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class CrateAdminChooseCrateWindow extends CyberFormSimple {
    private Vector3 V;

    public CrateAdminChooseCrateWindow(Vector3 v) {
        super(FormType.MainForm.Crate_Admin_ChooseCrate, "Choose Crate");
        V = v;
    }



}
