package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.yungtechboy1.CyberCore.FormType.MainForm.*;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class MasterListener implements Listener {
    CyberCoreMain plugin;

    public MasterListener(CyberCoreMain main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void joinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        String Msg = plugin.colorize((String) plugin.MainConfig.get("Join-Message"));
        event.setJoinMessage(Msg.replace("{player}", p.getName()));
        p.sendTitle(plugin.colorize("&l&bCyberTech"), plugin.colorize("&l&2Welcome!"), 30, 30, 10);

        plugin.initiatePlayer(p);
        String rank = plugin.RankFactory.getPlayerRank(p).getDisplayName();
        p.sendMessage(plugin.colorize("&2You Have Joined with the Rank: " + rank));
//        plugin.getCorePlayer(p).uuid = p.getUniqueId().toString();
        if (rank != null && rank.equalsIgnoreCase("op")) {
            p.setOp(true);
        } else {
            p.setOp(false);
        }
    }


    //GUI Listener
    @EventHandler
    public void PFRE(PlayerFormRespondedEvent pr) {
        int fid = pr.getFormID();
        Player p = pr.getPlayer();
        CorePlayer cp = ((CorePlayer) p);
        switch (cp.LastSentFormType) {
            case Class_0:
            case Enchanting_0:
                FormResponseModal frm = (FormResponseModal) pr.getResponse();
                if (frm.getClickedButtonId() == 0) {
                    if (cp.LastSentFormType == Enchanting_0) {
                        Item e = Item.get(Block.ENCHANT_TABLE, 0, 1);
                        p.getInventory().addItem(e.setCustomName("TTTTTTTTTT"));
                    }
                    cp.LastSentFormType = NULL;
                } else {
                    cp.showFormWindow(cp.getNewWindow());
                    if (cp.LastSentFormType == Enchanting_0) {
                        //Take Hand
                        cp.ReturnItemBeingEnchanted();
                        Item i = cp.getInventory().getItemInHand();
                        cp.getInventory().remove(i);//Take item and Store it
                        cp.setItemBeingEnchanted(i);
                        cp.LastSentFormType = Enchanting_1;
                    } else if (cp.LastSentFormType == Class_0) cp.LastSentFormType = Class_1;
                    cp.clearNewWindow();
                }
                break;
            case Class_1:
                FormResponseSimple frs = (FormResponseSimple) pr.getResponse();
                int k = frs.getClickedButtonId();
                if (cp.LastSentSubMenu == FormType.SubMenu.MainMenu) {
                    if (k == 0) {//Offense
                        cp.showFormWindow(new FormWindowSimple("Choose your Class Catagory!", "Visit Cybertechpp.com for more info on classes!",
                                new ArrayList<ElementButton>() {{
                                    add(new ElementButton("Assassin"));
                                    add(new ElementButton("Knight"));
                                    add(new ElementButton("Raider"));
                                    add(new ElementButton("Theif"));
                                }}));
                        cp.LastSentFormType = Class_1;
                        cp.LastSentSubMenu = FormType.SubMenu.Offense;
                    } else if (k == 4) {
                        //TEMP-GIVE SPAWNER
                        Item i = Item.get(Item.MONSTER_SPAWNER, 0, 1);
                        i.setCompoundTag(new CompoundTag() {{
                            putInt("Level", 1);
                            putInt("Type", 12);
                            putShort("MinSpawnDelay", 20 * 10);
                            putShort("MaxSpawnDelay", 20 * 10 + 10);
                        }});
                        cp.sendMessage("Gave ITem!");
                        cp.getInventory().addItem(i);
                    }
                } else if (cp.LastSentSubMenu == FormType.SubMenu.Offense) {
                    switch (k) {
                        case 0:
                            break;//Assassin
                        case 1:
                            break;//Knight
                        case 2:
                            break;//Raider
                        case 3:
                            break;//Theif
                    }
                }
                break;
            case Enchanting_1:
                FormResponseCustom frc = (FormResponseCustom) pr.getResponse();
                FormResponseData frd = frc.getStepSliderResponse(3);
                int ke = frd.getElementID();
                cp.sendMessage(frd.getElementContent()+"<<<<<<<");
                Enchantment e = cp.GetStoredEnchants().get(ke);
                if(e == null){
                    cp.sendMessage("Error!");
                }
/*
* cp.setNewWindow(new FormWindowCustom("Choose your Class Catagory!",
                    new ArrayList<Element>() {{
                        addAll(CustomEnchantment.PrepareEnchantList(cp.GetStoredEnchants(GetTier(),3,item)));
                        add(new ElementStepSlider("TE3", new ArrayList<String>() {{
                            add("1");
                            add("2");
                            add("3");
                        }}, 0));
                    }}));
* */
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void spawnEvent(PlayerRespawnEvent event) {

    }

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

        return plugin.RankFactory.getPlayerRank(player).getChat_format().format(faction, plugin.RankFactory.getPlayerRank(player).getDisplayName(), player, chatafter);
/*
        put("Chat-Format", "{rank}{faction}{player-name} > {msg}");
        put("Faction-Format", "[{value}]");
        put("Rank-Format", "[{value}]");
        put("Join-Message", "");
        put("Leave-Message", "");*/
    }

}
