package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.List;

public class Class0Window extends CyberFormSimple {
    public Class0Window(FormType.MainForm ttype, String title, String content) {
        super(ttype, title, content);
    }

    public Class0Window() {
        super(FormType.MainForm.Class_0, "Choose your Class Catagory!", "Visit Cybertechpp.com for more info on classes!",
                new ArrayList<ElementButton>() {{
                    add(new ElementButton("Offense"));
                    add(new ElementButton("Defense"));
                    add(new ElementButton("Minning"));
                    add(new ElementButton("Intelligence"));
                    add(new ElementButton("TEMP-GIVE SPAWNER"));
                }});
    }
}
