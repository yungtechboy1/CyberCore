package net.yungtechboy1.CyberCore.Events;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import ru.nukkit.welcome.players.PlayerManager;

import java.util.Map;
import java.util.UUID;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class CyberChatEvent implements Listener {
    CyberCoreMain plugin;
    public CyberChatEvent(CyberCoreMain main){
        plugin = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void joinEvent(PlayerJoinEvent event) {
        String Msg = (String) plugin.MainConfig.get("Join-Message");
        if (Msg.equalsIgnoreCase("")) {
            event.setJoinMessage("");
        } else {
            event.setJoinMessage(Msg);
        }
        String Rank = plugin.RankFactory.GetMasterRank(event.getPlayer().getName());
        event.getPlayer().sendMessage(TextFormat.GREEN + "You Have Joined with the Rank: " + Rank);
        plugin.Setnametag(event.getPlayer().getName());
        if (Rank != null && Rank.equalsIgnoreCase("op")) {
            event.getPlayer().setOp(true);
        } else {
            event.getPlayer().setOp(false);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void quitEvent(PlayerQuitEvent event) {
        String Msg = (String) plugin.MainConfig.get("Leave-Message");
        if (Msg.equalsIgnoreCase("")) {
            event.setQuitMessage("");
        } else {
            event.setQuitMessage(Msg);
        }
    }


    //@TODO Check for BadWords!
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatEvent(PlayerChatEvent event) {
        if (event.isCancelled()) return;
        //SHouldnt need thins @TODO
        //if (plugin.getServer().getPluginManager().getPlugin("Welcome") != null && !PlayerManager.isPlayerLoggedIn(event.getPlayer()))return;
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
        String ChatFormat = (String) plugin.MainConfig.get("Chat-Format");
        String FactionFormat = (String) plugin.MainConfig.get("Faction-Format");
        String RankFormat = (String) plugin.MainConfig.get("Rank-Format");
        //FPlayer fp = FPlayers.i.get(player);
        if (!plugin.getPlayerFaction(player).equalsIgnoreCase("")) {
            //if(fp.getFaction() != null && !fp.getFaction().getTag().equalsIgnoreCase(TextFormat.DARK_GREEN + "Wilderness")){
            FactionFormat = FactionFormat.replace("{value}", plugin.getPlayerFaction(player));
            //FactionFormat = TextFormat.GRAY+FactionFormat.replace("{value}",fp.getFaction().getTag())+TextFormat.WHITE;
        } else {
            FactionFormat = TextFormat.GRAY + "[NF]" + TextFormat.WHITE;
        }

        String a = "";
        System.out.println("Asdasdasd");
        if(plugin.RankFactory.GetAdminRank(player.getName()) == null){

            System.out.println("asdasd1231231231231");
        }
        a = plugin.RankFactory.GetAdminRank(player.getName());
        System.out.println("Asdasdasd");
        if (a == null) a = plugin.RankFactory.GetMasterRank(player.getName());
        System.out.println("Asdasdasd");
        if (a == null) a = plugin.RankFactory.GetSecondaryRank(player.getName());
        if (a != null) {
            RankFormat = RankFormat.replace("{value}", (String) plugin.RankConfig.get(a));
            RankFormat = RankFormat.replace("&", TextFormat.ESCAPE + "") + TextFormat.WHITE;
        } else {
            RankFormat = "";
        }
        if (a != null && plugin.RankChatColor.exists(a)) {
            chat = ((String) plugin.RankChatColor.get(a)).replace("&", TextFormat.ESCAPE + "") + chat;
        }
        chat = chat;
        String Final = ChatFormat.replace("{rank}", RankFormat + TextFormat.WHITE).replace("{faction}", FactionFormat).replace("{player-name}", player.getName()).replace("{msg}", chat);
        return Final;
/*
        put("Chat-Format", "{rank}{faction}{player-name} > {msg}");
        put("Faction-Format", "[{value}]");
        put("Rank-Format", "[{value}]");
        put("Join-Message", "");
        put("Leave-Message", "");*/
    }

}
