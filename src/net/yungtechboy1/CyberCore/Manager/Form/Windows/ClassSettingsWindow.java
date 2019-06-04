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
    BaseClass BC;

    public ClassSettingsWindow(BaseClass bd, FormType.MainForm ttype, String title, String content) {
        super(ttype, title, content);
        inti();
        BC = bd;
    }

    public ClassSettingsWindow(BaseClass bd, FormType.MainForm ttype, String title, String content, List<ElementButton> buttons) {
        super(ttype, title, content, buttons);
        inti();
        BC = bd;

    }

    public ClassSettingsWindow(BaseClass bd, String title, String content) {
        super(title, content);
        inti();
        BC = bd;

    }

    public ClassSettingsWindow(BaseClass bd, String title, String content, List<ElementButton> buttons) {
        super(title, content, buttons);
        inti();
        BC = bd;

    }

    private void inti() {
        addButton(new ElementButton("How to use " + TextFormat.AQUA + BC.getName()));
        addButton(new ElementButton("Class Merchant"));
        addButton(new ElementButton(TextFormat.RED + "Leave Class"));
        addButton(new ElementButton(TextFormat.RED + "--------------"));
    }


    @Override
    public void onRun(CorePlayer p) {
        super.onRun(p);
        int k = getResponse().getClickedButtonId();
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
    }
}
