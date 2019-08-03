package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import cn.nukkit.math.NukkitRandom;
import net.yungtechboy1.CyberCore.Classes.New.Offense.Knight;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;

public class MainClassSettingsKnightWindow extends MainClassSettingsWindow {
    public MainClassSettingsKnightWindow(Knight knight) {
        super(knight,FormType.MainForm.Class_Settings_Window_Knight,"Knight InternalPlayerSettings","");
        addButton(new ElementButton("Damage Lvl1"));
        addButton(new ElementButton("Damage Lvl2"));
        addButton(new ElementButton("Damage Lvl3"));
        addButton(new ElementButton("Give Apples"));
        addButton(new ElementButton("SEnd Att"));

    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        int k = getResponse().getClickedButtonId();
        NukkitRandom nr = new NukkitRandom();
        if(k == getKey()){
            p.attack(1);
        } else if(k == getKey()){
            p.attack(nr.nextRange(0,6));
        } else if(k == getKey()){
            p.attack(nr.nextRange(0,9));
        } else if(k == getKey()){
            p.getInventory().addItem(Item.get(Item.APPLE,0,60));
        } else if(k == getKey()){
            p.sendAttributes();
        }
        return true;
    }
}
