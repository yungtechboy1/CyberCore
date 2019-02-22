package net.yungtechboy1.CyberCore.Manager.Factions.Listener;

import CyberTech.CyberChat.Main;
import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.BossBar.BossBar;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by joneca04 on 12/30/2016.
 */
public class JoinEvent {
    FactionsMain Main;
    PlayerJoinEvent Event;
    public JoinEvent(FactionsMain main, PlayerJoinEvent ev){
        Main = main;
        Event = ev;
        run();
    }

    public void run(){
        sendBossBar();
    }

    public void sendBossBar(){
        Player player = Event.getPlayer();
        Main.sendBossBar(player);
    }
}
