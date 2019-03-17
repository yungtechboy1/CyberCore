package net.yungtechboy1.CyberCore.Manager;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/14/2019.
 */
public class GUIManager {
    CyberCoreMain Main;

    ConfigSection List = new ConfigSection();

//    public void AddPlayerForm()

    public void onFormResponse(PlayerFormRespondedEvent e) {
        Player p = e.getPlayer();
        if (e.getWindow() instanceof FormWindowSimple) {
            FormWindowSimple gui = (FormWindowSimple) e.getWindow();
            String responseName = gui.getResponse().getClickedButton().getText();

            if (responseName.equals("Adventure")) p.setGamemode(2);
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
    public void showGmf(Player p) {
        FormWindowSimple gui = new FormWindowSimple("Gamemode UI", "Change your game mode quickly and simply");

        gui.addButton(new ElementButton("Adventure"));
        gui.addButton(new ElementButton("Creative"));
        gui.addButton(new ElementButton("Survival"));
        gui.addButton(new ElementButton("Spectator"));


        p.showFormWindow(gui);
    }
}
