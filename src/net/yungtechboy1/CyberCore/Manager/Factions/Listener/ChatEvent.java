package net.yungtechboy1.CyberCore.Manager.Factions.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by joneca04 on 12/30/2016.
 */
public class ChatEvent {
    FactionsMain Main;
    public PlayerChatEvent Event;
    String Name;

    public ChatEvent(FactionsMain main, PlayerChatEvent ev) {
        Main = main;
        Event = ev;
        Name = Event.getPlayer().getName().toLowerCase();
        run();
    }

    public void run() {
        Faction fac = Main.FFactory.getPlayerFaction(Name);
        FactionChat(fac);
        AllyChat(fac);
    }

    public void FactionChat(Faction fac){
        if(fac == null)return;
        if(fac.getFChat().contains(Name)){
            Event.setCancelled();
            String n = Event.getPlayer().getName();
            if(fac.Leader.equalsIgnoreCase(Name)){
                fac.BroadcastMessage(TextFormat.YELLOW+"~***["+n+"]***~: "+Event.getMessage());
            }else if(fac.IsGeneral(Name)){
                fac.BroadcastMessage(TextFormat.YELLOW+"~**["+n+"]**~: "+Event.getMessage());
            }else if(fac.IsOfficer(Name)){
                fac.BroadcastMessage(TextFormat.YELLOW+"~*["+n+"]*~: "+Event.getMessage());
            }else if(fac.IsMember(Name)){
                fac.BroadcastMessage(TextFormat.YELLOW+"~["+n+"]~: "+Event.getMessage());
            }else{
                fac.BroadcastMessage(TextFormat.YELLOW+"-["+n+"]-: "+Event.getMessage());
            }
            Event.setCancelled();
        }
    }

    public void AllyChat(Faction fac){
        if(fac == null)return;
        if(fac.getFAlly().contains(Name)){Event.setCancelled();
            String n = Event.getPlayer().getName();
            if(fac.Leader.equalsIgnoreCase(Name)){
                fac.MessageAllys(TextFormat.YELLOW+"~***["+n+"]***~: "+Event.getMessage());
            }else if(fac.IsGeneral(Name)){
                fac.MessageAllys(TextFormat.YELLOW+"~**["+n+"]**~: "+Event.getMessage());
            }else if(fac.IsOfficer(Name)){
                fac.MessageAllys(TextFormat.YELLOW+"~*["+n+"]*~: "+Event.getMessage());
            }else if(fac.IsMember(Name)){
                fac.MessageAllys(TextFormat.YELLOW+"~["+n+"]~: "+Event.getMessage());
            }else{
                fac.MessageAllys(TextFormat.YELLOW+"-["+n+"]-: "+Event.getMessage());
            }
            Event.setCancelled();
        }

    }
}