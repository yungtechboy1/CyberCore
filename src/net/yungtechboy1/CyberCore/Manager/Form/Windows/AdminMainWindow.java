package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class AdminMainWindow extends CyberFormSimple {
    public AdminMainWindow() {
        super(FormType.MainForm.Admin_Main, "Admin Page [1/1]");
        addButton(new ElementButton("Make Chest a Crate"));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        switch (getResponse().getClickedButtonId()){
            case 0:
                //TODO
                break;
        }
        return true;
    }
}
