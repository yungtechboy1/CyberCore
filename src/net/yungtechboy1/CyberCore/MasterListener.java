package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.Data.FactionSQL;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionString;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.*;

import static net.yungtechboy1.CyberCore.FormType.MainForm.*;
import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.*;

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

//        plugin.initiatePlayer(p);
        plugin.ServerSQL.LoadPlayer(p);
        String rank = plugin.RankFactory.getPlayerRank(p).getDisplayName();
        p.sendMessage(plugin.colorize("&2You Have Joined with the Rank: " + rank));
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
            case Faction_Invite_Choose:
                FormResponseSimple fic = (FormResponseSimple) pr.getResponse();
                String pn = fic.getClickedButton().getText();
                CorePlayer cpp = (CorePlayer) CyberCoreMain.getInstance().getServer().getPlayerExact(pn);
                if (cpp == null) {
                    cp.sendMessage("Error! The name '" + pn + "' could not be found on server!");
                    return;
                } else {
                    if (null == plugin.FM.FFactory.getPlayerFaction(cpp)) {
                        //TODO Allow Setting to ignore Faction messages
                        cp.sendMessage(Error_PlayerInFaction.getMsg());
                        return;
                    }
                    Integer time = (int) (Calendar.getInstance().getTime().getTime() / 1000) + 60 * 5;
                    Faction fac = plugin.FM.FFactory.getFaction(cp.Faction);
                    if(fac == null){
                        cp.sendMessage(Error_SA224.getMsg());
                        return;
                    }
                    fac.AddInvite(cpp.getName().toLowerCase(), time);
                    plugin.FM.FFactory.InvList.put(cpp.getName().toLowerCase(), fac.GetName());

                    cp.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Successfully invited " + cpp.getName() + "!");
                    cpp.sendMessage(FactionsMain.NAME + TextFormat.YELLOW + "You have been invited to faction.\n" + TextFormat.GREEN + "Type '/f accept' or '/f deny' into chat to accept or deny!");
                }
                break;
            case Faction_Admin_Page_SLR:
                FormResponseSimple fapp = (FormResponseSimple) pr.getResponse();
                int idd = fapp.getClickedButtonId();
                switch (idd) {
                    case 0:
                        plugin.FM.FFactory.SaveAllFactions();
                        cp.sendMessage(Success_ADMIN_Faction_Saved.getMsg());
                        break;
                    case 1:
                        plugin.FM = new FactionsMain(plugin, new FactionSQL(plugin, "FDB"));
                        cp.sendMessage(Success_ADMIN_Faction_Saved.getMsg());
                        break;
                    case 2:
                        plugin.FM.FFactory.SaveAllFactions();
                        plugin.FM = new FactionsMain(plugin, new FactionSQL(plugin, "FDB"));
                        break;
                }
                break;
            case Faction_Admin_Page_1:
                FormResponseSimple fap = (FormResponseSimple) pr.getResponse();
                int id = fap.getClickedButtonId();
                switch (id) {
                    case 0:
                        cp.LastSentFormType = Faction_Admin_Page_SLR;
                        FormWindowSimple FWM = new FormWindowSimple("CyberFactions | Admin Page > SLR", "");
                        FWM.addButton(new ElementButton("Save"));
                        FWM.addButton(new ElementButton("Load"));
                        FWM.addButton(new ElementButton("Reload"));
                        cp.showFormWindow(FWM);
                        break;
                }
                break;
            case Faction_Create_0:
            case Faction_Create_0_Error:
                new FormWindowModal("CyberFactions | Create Faction (2/2)!", "Faction Created!", "OK", "OK");
                FormResponseCustom frc = (FormResponseCustom) pr.getResponse();
                String fn;
                if (cp.LastSentFormType == Faction_Create_0) {

                    fn = frc.getInputResponse(0);
                } else {
                    fn = frc.getInputResponse(1);
                }
                if (fn == null || fn.length() == 0) return;
                System.out.println("PRINGING THE NAME " + fn);
                int r = plugin.FM.FFactory.CheckFactionName(fn);
                if (r != 0) {
                    FormWindowCustom FWM = new FormWindowCustom("CyberFactions | Create Faction (1/2)");
//        Element e = null;
                    FactionString fs = FactionsMain.getInstance().TextList.getOrDefault(r, null);
                    if (fs == null) fs = Error_SA221;
                    FWM.addElement(new ElementInput("Desired Faction Name"));
                    FWM.addElement(new ElementLabel(fs.getMsg()));
                    FWM.addElement(new ElementInput("MOTD", "A CyberTech Faction"));
                    FWM.addElement(new ElementLabel("Enabeling Faction Privacy will require a player to have an invite to join your faction."));
                    FWM.addElement(new ElementToggle("Faction Privacy", false));
                    cp.showFormWindow(FWM);
                    cp.LastSentFormType = Faction_Create_0_Error;
                    return;
                }
                String motd;
                boolean privacy;
                if (cp.LastSentFormType == Faction_Create_0_Error) {
                    motd = frc.getInputResponse(2);
                    privacy = frc.getToggleResponse(4);
                } else {
                    motd = frc.getInputResponse(1);
                    privacy = frc.getToggleResponse(3);
                }

                Faction f = plugin.FM.FFactory.CreateFaction(fn, cp, motd, privacy);
                if (f == null) {
                    cp.sendMessage(Error_SA223.getMsg());
                }
                break;
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
                FormResponseCustom frc1 = (FormResponseCustom) pr.getResponse();
                FormResponseData frd = frc1.getStepSliderResponse(3);
                int ke = frd.getElementID();
                cp.sendMessage(frd.getElementContent() + "<<<<<<<");
                Enchantment e = cp.GetStoredEnchants().get(ke);
                if (e == null) {
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

        plugin.ServerSQL.UnLoadPlayer(event.getPlayer());
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
