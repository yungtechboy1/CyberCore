package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.List;

public class SettingsPage0 extends CyberFormSimple {
    public SettingsPage0() {
        this(new ArrayList());
    }

    public SettingsPage0(List<ElementButton> buttons) {
        super(FormType.MainForm.SettingsPage0, "CyberFactions | InternalPlayerSettings Manager");
        addButton(new ElementButton("Player Setting"));
        addButton(new ElementButton("Faction InternalPlayerSettings"));
        addButton(new ElementButton("Class InternalPlayerSettings"));
//        addButton(new ElementButton("Show Damage Tags"));
//        addButton(new ElementButton("Show Advanced Damage Tags"));
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fap = getResponse();
        int k = fap.getClickedButtonId();
        if (k == 0) {
            cp.showFormWindow(new PlayerSettingsPage0());
        } else if (k == 1) {
            cp.showFormWindow(new PlayerFactionSettingsPage0());
        } else if (k == 2) {
            cp.showFormWindow(new PlayerFactionSettingsPage0());
        }
        return false;
    }
}
