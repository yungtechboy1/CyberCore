package net.yungtechboy1.CyberCore.Manager.Factions.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.Map;

/**
 * Created by joneca04 on 12/31/2016.
 *
 *
 */
public class LeaveEvent {
    FactionsMain Main;
    PlayerQuitEvent Event;
    public LeaveEvent(FactionsMain main, PlayerQuitEvent ev){
        Main = main;
        Event = ev;
        run();
    }

    public void run(){
        removekey();
    }

    public void removekey(){
        Player player = Event.getPlayer();
        String name = player.getName().toLowerCase();
        if(Main.BossBar.containsKey(name))Main.BossBar.remove(name);
        if(Main.BBN.containsKey(name))Main.BBN.remove(name);
    }
}