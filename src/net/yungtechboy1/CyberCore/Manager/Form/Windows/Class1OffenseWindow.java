package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.Classes.New.Minner.TNTSpecialist;
import net.yungtechboy1.CyberCore.Classes.New.Offense.Knight;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.List;

public class Class1OffenseWindow extends CyberFormSimple {
    public Class1OffenseWindow() {
        this(new ArrayList());
    }

    public Class1OffenseWindow(List<ElementButton> buttons) {
        super(FormType.MainForm.Class_1, "Choose your Class Category Offense!", "Visit Cybertechpp.com for more info on classes!",
                new ArrayList<ElementButton>() {{
                    add(new ElementButton("Knight"));
                    add(new ElementButton("Mercenary"));
                    add(new ElementButton("Holy Knight"));
                    add(new ElementButton("Dark Knight"));
                    add(new ElementButton("Dragon Slayer"));
                    add(new ElementButton("Berserker"));
                    add(new ElementButton("Samuri"));
                }});
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fapp = getResponse();
        int k = fapp.getClickedButtonId();
        switch (k) {
            case 0:
                Knight ts = new Knight(_plugin, cp, null);
                cp.SetPlayerClass(ts);
                _plugin.ClassFactory.SaveClassToFile(cp);
                cp.sendMessage("Class Set!");
                break;//Kngiht
            case 1:
                break;//Knight
            case 2:
                break;//Raider
            case 3:
                break;//Theif
        }
        return false;
    }
}
