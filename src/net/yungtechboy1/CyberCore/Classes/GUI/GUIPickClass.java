package net.yungtechboy1.CyberCore.Classes.GUI;

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


    }

    public void Step1r(PlayerFormRespondedEvent e) {
        Player p = e.getPlayer();
        if (e.getWindow() instanceof FormWindowSimple) {
            FormWindowSimple gui = (FormWindowSimple) e.getWindow();
            String responseName = gui.getResponse().getClickedButton().getText();

            if (responseName.equals("Offense")) p.setGamemode(2);
            if (responseName.equals("Adventure"))p.sendMessage("\u00A76You changed your game mode successfully to \u00A7cAdventure\u00A7r");

            if (responseName.equals("Creative")) p.setGamemode(1);
            if (responseName.equals("Creative")) p.sendMessage("\u00A76You changed your game mode successfully to \u00A7cCreative\u00A7r");

            if (responseName.equals("Survival")) p.setGamemode(0);
            if (responseName.equals("Survival")) p.sendMessage("\u00A76You changed your game mode successfully to \u00A7cSurvival\u00A7r");

            if (responseName.equals("Spectator")) p.setGamemode(3);
            if (responseName.equals("Spectator")) p.sendMessage("\u00A76You changed your game mode successfully to \u00A7cSpectator\u00A7r");


            return;
        }
    }
}

