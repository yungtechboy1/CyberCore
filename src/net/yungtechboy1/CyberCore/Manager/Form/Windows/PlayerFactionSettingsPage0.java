package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactionSettingsPage0  extends CyberFormCustom {
    public PlayerFactionSettingsPage0() {
        this(new ArrayList());
    }

    public PlayerFactionSettingsPage0(List<ElementButton> buttons) {
        super(FormType.MainForm.PlayerFactionSettingsPage0, "CyberFactions | Player Faction Settings Page (1/1)");

        addElement(new ElementToggle("Allow Faction Invite Pop-Ups"));
//        addButton(new ElementButton("Show Advanced Damage Tags"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseCustom fap = getResponse();
        cp.Settings.updateFromWindow(fap);
        cp.sendMessage("Settings Updated!");
        cp.showFormWindow(new SettingsPage0());
    }
}
