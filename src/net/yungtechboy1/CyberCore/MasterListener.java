package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

import java.util.*;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class MasterListener implements Listener {
    CyberCoreMain plugin;

    public MasterListener(CyberCoreMain main) {
        plugin = main;
    }

    /**
     * JOIN
     * @param event PlayerJoinEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void joinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        String Msg = plugin.colorize((String) plugin.MainConfig.get("Join-Message"), p);
        event.setJoinMessage(Msg);
        p.sendTitle(plugin.colorize("&l&bCyberTech"), plugin.colorize("&l&2Welcome!"),30,30, 10);

//        _plugin.initiatePlayer(p);
        plugin.ServerSQL.LoadPlayer(plugin.getCorePlayer(p));
        String rank = plugin.RF.getPlayerRank(p).getDisplayName();
        p.sendMessage(plugin.colorize("&2You Have Joined with the Rank: " + rank));
        if (rank != null && rank.equalsIgnoreCase("op")) {
            p.setOp(true);
        } else {
            p.setOp(false);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void spawnEvent(PlayerRespawnEvent event) {

    }


//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onCreation(PlayerCreationEvent event) {
//        event.setPlayerClass(CorePlayer.class);
//        event.setBaseClass(CorePlayer.class);
//    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreation(PlayerCreationEvent event) {
        event.setPlayerClass(CorePlayer.class);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void quitEvent(PlayerQuitEvent event) {
        String Msg = (String) plugin.MainConfig.get("Leave-Message");
        event.setQuitMessage(Msg.replace("{player}", event.getPlayer().getName()));

    }


    //@TODO Check for BadWords!
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatEvent(PlayerChatEvent event) {
        if (event.isCancelled()) return;
        //SHouldnt need thins @TODO^^
        event.setCancelled(true);
        if (plugin.MuteChat && (!event.getPlayer().hasPermission("CyberTech.CyberChat.op"))) {
            event.getPlayer().sendMessage(TextFormat.YELLOW + "All Chat Is Muted! Try again later!");
            return;
        }
        if (plugin.isMuted(event.getPlayer())) {
            event.getPlayer().sendMessage(TextFormat.YELLOW + "You are Muted! Try again later!");
            return;
        }
        String FinalChat = formatForChat(event.getPlayer(), event.getMessage());
        if (FinalChat == null) return;
        if (plugin.LM.containsKey(event.getPlayer().getName().toLowerCase()) && plugin.LM.get(event.getPlayer().getName().toLowerCase()).equalsIgnoreCase(FinalChat)) {
            event.getPlayer().sendMessage(FinalChat);
            return;
        }
        plugin.LM.put(event.getPlayer().getName().toLowerCase(), FinalChat);
        plugin.checkSpam(event.getPlayer());
        Server.getInstance().getLogger().info(FinalChat);
        for (Map.Entry<UUID, Player> e : plugin.getServer().getOnlinePlayers().entrySet()) {
            if (plugin.PlayerMuted.contains(e.getValue().getName().toLowerCase())) continue;
            e.getValue().sendMessage(FinalChat);
        }
    }

    public String formatForChat(Player player, String chat) {
        HashMap<String, Object> badwords = new HashMap<String, Object>() {{
            put("fuck", "f***");
            put("shit", "s***");
            put("nigger", "kitty");
            put("nigga", "boi");
            put("bitch", "Sweetheart");
            put("hoe", "tool");
            put("ass", "butt");
        }};
        String faction;
        Faction pf = plugin.getPlayerFaction(player);
        if (pf != null) {
            faction = pf.GetDisplayName();
            //FactionFormat = TextFormat.GRAY+FactionFormat.replace("{value}",fp.getFaction().getTag())+TextFormat.WHITE;
        } else {
            faction = TextFormat.GRAY + "[NF]" + TextFormat.WHITE;
        }

        //ANTI BADWORDS
        String chatb4 = chat;
        String chatafter = chat;
        for (String s : chat.split(" ")) {
            if (!badwords.containsKey(s.toLowerCase())) continue;
            chatafter = chatafter.replaceAll("(?i)" + s, badwords.get(s.toLowerCase()).toString());
        }
        /*
        Fucks up words like Class and BAss
        for(String b: badwords.keySet()){
            if(chatafter.toLowerCase().contains(b.toLowerCase())) {
                chatafter = chatafter.replaceAll("(?i)" + b, badwords.get(b).toString());
                chatafter = chatafter.replaceAll(b, badwords.get(b).toString());
            }
        }*/
        /*String chat2 = chat;
        chat2 = chat.replace(" ","");
         */
        //ANTI WORK AROUND BADWORDS
        //@TODO remove all spaces and use Regex to replace all Instaces of it

        return plugin.RF.getPlayerRank(player).getChat_format().format(faction, plugin.RF.getPlayerRank(player).getDisplayName(), player, chatafter);
/*
        put("Chat-Format", "{rank}{faction}{player-name} > {msg}");
        put("Faction-Format", "[{value}]");
        put("Rank-Format", "[{value}]");
        put("Join-Message", "");
        put("Leave-Message", "");*/
    }

}
