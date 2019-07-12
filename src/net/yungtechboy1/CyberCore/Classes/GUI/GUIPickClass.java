package net.yungtechboy1.CyberCore.Classes.GUI;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;

/**
 * Created by carlt on 3/14/2019.
 */
public class GUIPickClass {
    public FormWindowSimple Step1() {
        FormWindowSimple gui = new FormWindowSimple("Choose your class UI",
                "Choose a Class Catagory to view more info and select");

        gui.addButton(new ElementButton("Offense"));
        gui.addButton(new ElementButton("Defensse"));
        gui.addButton(new ElementButton("Crafting"));
        gui.addButton(new ElementButton("--------"));
        return gui;
    }
}

