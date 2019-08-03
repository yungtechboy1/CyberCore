package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseCustom;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import java.util.ArrayList;
import java.util.List;

public class PlayerSettingsPage0 extends CyberFormCustom {
    public PlayerSettingsPage0() {
        this(new ArrayList());
    }

    public PlayerSettingsPage0(List<ElementButton> buttons) {
        super(FormType.MainForm.PlayerSettingsPage0, "CyberFactions | Player InternalPlayerSettings Page (1/1)");

        addElement(new ElementToggle("Show Damage Tags"));
        addElement(new ElementToggle("Show Advanced Damage Tags"));
        addElement(new ElementToggle("Show Hud"));
        addElement(new ElementToggle("Show Class Hud"));
        addElement(new ElementToggle("Show Faction Hud"));
        addElement(new ElementToggle("Show Position Hud"));
//        addButton(new ElementButton("Show Advanced Damage Tags"));
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseCustom fap = getResponse();
        cp.InternalPlayerSettings.updateFromWindow(fap);
        cp.sendMessage("InternalPlayerSettings Updated!");
        cp.showFormWindow(new SettingsPage0());
        return false;
    }
}
