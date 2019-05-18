package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.Minner.TNTSpecialist;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.List;

public class ClassSettingsTNTWindow extends CyberFormSimple {
TNTSpecialist C;
    public ClassSettingsTNTWindow (TNTSpecialist c){
        super(FormType.MainForm.Class_Settings_TNT, "CyberFactions | TNT-Specialist Settings", TextFormat.AQUA+"Current XP: "+TextFormat.GREEN+c.XPRemainder(c.getXP())+"\n"+TextFormat.AQUA+"Current Level: "+TextFormat.GREEN+c.getLVL());
        addButton(new ElementButton("How to use "+TextFormat.AQUA+c.getName()));
        addButton(new ElementButton("Class Merchant"));
        C = c;
    }

    @Override
    public void onRun(CorePlayer p) {
        super.onRun(p);
        int k = getResponse().getClickedButtonId();
        if(k == 0){
            //TODO
            p.showFormWindow(C.getHowToUseClassWindow());
        }else if(k == 1){

        }
    }
}
