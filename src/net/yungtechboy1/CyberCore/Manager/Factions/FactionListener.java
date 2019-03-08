package net.yungtechboy1.CyberCore.Manager.Factions;

import CyberTech.CyberChat.Main;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.level.ChunkLoadEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.MainLogger;
import cn.nukkit.utils.TextFormat;
import javafx.scene.layout.Priority;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by carlt_000 on 4/15/2016.
 */
public class FactionListener implements Listener {

    /**
     * @var FactionMain
     */
    public FactionsMain plugin;
    private Map<String, String> nt;
    public Map<String, String> kill;


    public FactionListener(FactionsMain factionMain) {
        this.plugin = factionMain;
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {
        if (event == null) return;
        String playern = event.getEntity().getName();
        EntityDamageEvent cause = event.getEntity().getLastDamageCause();
        event.getEntity().setExperience(0);
        if (this.plugin.killed.containsKey(event.getEntity().getName())) {
            this.plugin.killed.replace(playern, this.plugin.killed.get(playern) + 1);
        } else {
            this.plugin.killed.put(playern, 1);
        }
        if (cause != null && cause.getCause() == EntityDamageEvent.CAUSE_ENTITY_ATTACK) {
            if (cause instanceof EntityDamageByEntityEvent && event.getEntity() instanceof Player) {
                Entity e = ((EntityDamageByEntityEvent) cause).getDamager();
                if (e instanceof Player) {
                    event.setDeathMessage(TextFormat.GRAY + playern + " was killed by " + e.getName() + TextFormat.RED + " (" + e.getHealth() + "❤)");
                    //@TODO Bounty XD
                    /*
                    int tb = 0;
                    if(isset(this.plugin.bounty[strtolower(playern)])){
                        foreach(this.plugin.bounty[strtolower(playern)] as name=>bounty){
                            tb += bounty;
                            event.getEntity().setNameTag(this.plugin.nt[strtolower(event.getPlayer().getName())]);
                            unset(this.plugin.bounty[strtolower(playern)][name]);
                        }
                        if(tb != 0){
                            this.plugin.api.addMoney(e,tb);
                            e.sendMessage(TextFormat.GREEN."[Bounty] Bounty Claimed for the Amount of tb");
                            e.addExperience(5);
                        }
                    }*/
                    String killername = e.getName();
                    //DEath FIne
                    Faction kf = this.plugin.FFactory.getPlayerFaction((Player) event.getEntity());
                    if (kf != null) {
                        kf.HandleKillEvent(event);
                        kf.TakePower(2);
                    }
                    if (!(this.plugin.death.containsKey(killername))) {
                        this.plugin.death.put(killername, 1);
                    } else {
                        int temp = this.plugin.death.get(killername);
                        this.plugin.death.replace(killername, 1 + temp);
                    }
                    if (this.plugin.death.get(killername) == 5) {
                        this.plugin.getServer().broadcastMessage(TextFormat.GREEN + killername + " is on a 5 KillStreak!");
                        //if(kf != null)this.plugin.AddFactionPower(kf, 5);
                    }
                    if (this.plugin.death.get(killername) == 8) {
                        this.plugin.getServer().broadcastMessage(TextFormat.AQUA + killername + " is on a 8 KillStreak!");
                        //if(kf != null)this.plugin.AddFactionPower(kf, 8);
                    }
                    if (this.plugin.death.get(killername) == 10) {
                        this.plugin.getServer().broadcastMessage(TextFormat.LIGHT_PURPLE + killername + " is on a 10 KillStreak!");
                        //if(kf != null)this.plugin.AddFactionPower(kf, 10);
                    }
                    if (this.plugin.death.get(killername) > 10) {
                        Integer kills = this.plugin.death.get(killername);
                        this.plugin.getServer().broadcastMessage(TextFormat.LIGHT_PURPLE + killername + " is on a " + kills + " KillStreak!");
                        //if(kf != null)this.plugin.AddFactionPower(kf, kills*2);
                    }
                }
            }
        }
        this.plugin.death.replace(playern, 0);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void ChatEvent(PlayerChatEvent event){
        new ChatEvent(plugin,event);
    }
    @EventHandler
    public void QuitEvent(PlayerQuitEvent event) {
        new LeaveEvent(plugin,event);
        String playern = event.getPlayer().getName();
        Faction fac = plugin.FFactory.getPlayerFaction(playern);
        if (fac != null) fac.BroadcastMessage(FactionsMain.NAME + TextFormat.YELLOW + playern + " Has Left!");
        Long now = Calendar.getInstance().getTime().getTime() / 1000;
        if (this.plugin.pvplog.containsKey(playern)) {
            if (this.plugin.pvplog.get(playern) > now) {
                if (event.getPlayer() != null) {
                    //@TODO CHeck if Just killing works
                    event.getPlayer().kill();
                }
            }
        }
    }

    //@TODO Might Be needed
 /*
    public void AnvilItemInHand(Player player) {
        String name = player.getName();
        String anvil = TextFormat.LIGHT_PURPLE+"[Anvil] ";
        player.getInventory().sendContents(player);
        hand = player.getInventory().getItemInHand();
        rank = this.plugin.GetRank(player);
        if(rank == false && !player.isOp()){
            player.sendMessage(anvil.TextFormat.AQUA."You must buy rank to use Anvils");
            player.sendMessage(anvil.TextFormat.RED."Please Support the server To Get Full Anvil Abillity!");
            player.sendMessage(anvil.TextFormat.RED."Buy Ranks from 5 at www.cyberechpp.com/MCPE");
            player.sendMessage(anvil.TextFormat.RED."Ranks activate immediately");
            return true;
        }/*
            if(player.getGamemode() != Player.SURVIVAL){
                player.sendMessage(anvil.TextFormat.AQUA."You must be in Gamemode Survival");
                return true;
            }*/
        /*if(hand.getCount() > 1){
            player.sendMessage(TextFormat.RED."One Item at a Time Please!");
        }
        if(hand == null)return true;
        if(!isset(this.plugin.anvil[name])){
            this.plugin.anvil[name] = hand;
            player.getInventory().setItemInHand(new Item(0));
            player.sendMessage(anvil.TextFormat.AQUA."Item Slot 1 Set Please Tap another Item to combine!");
            return true;
        }else{
            if(!this.plugin.anvil[name] instanceof \pocketmine\item\Book && !hand instanceof \pocketmine\item\Book){
                if(this.plugin.anvil[name].isTool() != hand.isTool()){
                    player.sendMessage(anvil.TextFormat.RED."Only Tools Work!");
                    player.getInventory().addItem(this.plugin.anvil[name]);
                    unset(this.plugin.anvil[name]);
                    player.sendMessage(anvil.TextFormat.RED."ERROR Item Slot 1 Returned Try Again!");
                    player.getInventory().sendContents(player);
                    return true;
                }
                if(this.plugin.anvil[name].isAxe() != hand.isAxe()){
                    player.sendMessage(anvil.TextFormat.RED."Items Must be the same");
                    player.getInventory().addItem(this.plugin.anvil[name]);
                    unset(this.plugin.anvil[name]);
                    player.sendMessage(anvil.TextFormat.RED."ERROR Item Slot 1 Returned Try Again!");
                    player.getInventory().sendContents(player);
                    return true;
                }
                if(this.plugin.anvil[name].isHoe() != hand.isHoe()){
                    player.sendMessage(anvil.TextFormat.RED."Items Must be the same");
                    player.getInventory().addItem(this.plugin.anvil[name]);
                    player.sendMessage(anvil.TextFormat.RED."ERROR Item Slot 1 Returned Try Again!");
                    unset(this.plugin.anvil[name]);
                    player.getInventory().sendContents(player);
                    return true;
                }
                if(this.plugin.anvil[name].isPickaxe() != hand.isPickaxe()){
                    player.sendMessage(anvil.TextFormat.RED."Items Must be the same");
                    player.getInventory().addItem(this.plugin.anvil[name]);
                    unset(this.plugin.anvil[name]);
                    player.sendMessage(anvil.TextFormat.RED."ERROR Item Slot 1 Returned Try Again!");
                    player.getInventory().sendContents(player);
                    return true;
                }
                if(this.plugin.anvil[name].isShovel() != hand.isShovel()){
                    player.sendMessage(anvil.TextFormat.RED."Items Must be the same");
                    player.getInventory().addItem(this.plugin.anvil[name]);
                    unset(this.plugin.anvil[name]);
                    player.getInventory().sendContents(player);
                    return true;
                }
                if(this.plugin.anvil[name].isSword() != hand.isSword()){
                    player.sendMessage(anvil.TextFormat.RED."Items Must be the same");
                    player.getInventory().addItem(this.plugin.anvil[name]);
                    unset(this.plugin.anvil[name]);
                    player.sendMessage(anvil.TextFormat.RED."ERROR Item Slot 1 Returned Try Again!");
                    player.getInventory().sendContents(player);
                    return true;
                }
            }

            enchat = this.plugin.anvil[name].getEnchantments();
            encc = array();
            foreach(enchat as e){
                encc[e.getId()] = e;
            }
            foreach(hand.getEnchantments() as ee){
                if(!isset(encc[ee.getId()])){
                    encc[ee.getId()] = ee;
                    echo "Add ".ee.getName()."\n";
                }else{
                    lvl1 = encc[ee.getId()].getLevel();
                    lvl2 = ee.getLevel();
                    lvl = lvl1 + lvl2;
                    ee.setLevel(lvl);
                    encc[ee.getId()] = ee;
                    echo "Combine ".e.getName()." => LEvel".lvl."\n";
                }
            }
            costl = 0;
            foreach(encc as f){
                costl += f.getLevel();
            }
            //        2   *  5  /3 *2 = 10
            //cost = costl * 5 * count(encc);
            // = 5
            cost = ((costl * 2)/3)*1.5;
            if(player.getExpLevel() < cost){
                player.sendMessage(anvil.TextFormat.GREEN." cost ".TextFormat.RED." Exp Levels Required for the transaction!");
                player.getInventory().addItem(this.plugin.anvil[name]);
                unset(this.plugin.anvil[name]);
                player.getInventory().sendContents(player);
                return true;
            }
            player.setExperience(player.getExperience() - this.plugin.getServer().getExpectedExperience(cost));
            id = hand.getId();
            ni = Item.fromString(id);
            ni.setCount(1);
            foreach(encc as f)this.addEnchantment(f, ni);
            player.getInventory().setItemInHand(ni);
            player.getInventory().sendContents(player);
            player.getInventory().sendHeldItem(player);
            player.sendMessage(anvil.TextFormat.AQUA." Took ".TextFormat.GREEN." cost ".TextFormat.AQUA." Levels For this Transaction");
            unset(this.plugin.anvil[name]);
        }


    }


    public function addEnchantment(\pocketmine\item\enchantment\Enchantment ench, Item item) {
        if (!item.hasCompoundTag()) {
            tag = new \pocketmine\nbt\tag\Compound("", []);
        } else {
            tag = item.getNamedTag();
        }

        if (!isset(tag.ench)) {
            tag.ench = new \pocketmine\nbt\tag\Enum("ench", []);
            tag.ench.setTagType(\pocketmine\nbt\NBT.TAG_Compound);
        }

        found = false;

        foreach (tag.ench as k => entry) {
            if (entry["id"] === ench.getId()) {
                tag.ench.{k} = new \pocketmine\nbt\tag\Compound("", [
                        "id" => new \pocketmine\nbt\tag\Short("id", ench.getId()),
                        "lvl" => new \pocketmine\nbt\tag\Short("lvl", ench.getLevel())
                ]);
                found = true;
                break;
            }
        }

        if (!found) {
            tag.ench.{ench.getId()} = new \pocketmine\nbt\tag\Compound("", [
                    "id" => new \pocketmine\nbt\tag\Short("id", ench.getId()),
                    "lvl" => new \pocketmine\nbt\tag\Short("lvl", ench.getLevel())
            ]);
        }

        item.setNamedTag(tag);
    }

    public function EnchantItemInHand(Player player) {
        enchanting = TextFormat.LIGHT_PURPLE."[Enchanting] ";
        player.getInventory().sendContents(player);
        hand = player.getInventory().getItemInHand();
        rank = this.plugin.GetRank(player);
        if(hand.getCount() > 1){
            player.sendMessage(TextFormat.RED."One Item at a Time Please!");
            return;
        }elseif(hand == null){
            player.sendMessage(enchanting.TextFormat.RED."No Item In Hand");
            return;
        }elseif(hand.hasEnchantments()){
            player.sendMessage(enchanting.TextFormat.RED."Your Item is already enchanted!!!");
            return;
        }/*elseif(player.getGamemode() != Player.SURVIVAL){
                player.sendMessage(TextFormat.AQUA."You must be in Gamemode Survival");
                return true;
            }*/
        /*if(hand.getId() == Item.DIAMOND_SWORD || hand.getId() == Item.IRON_SWORD || hand.getId() == Item.STONE_SWORD || hand.getId() == Item.GOLDEN_SWORD || hand.getId() == Item.WOODEN_SWORD){
            if(rank == false && !player.isOp()){
                player.sendMessage(enchanting.TextFormat.AQUA."Support the server today and UnLock all Enchants!");
                player.sendMessage(enchanting.TextFormat.AQUA."Buy Ranks from 5 at www.cyberechpp.com/MCPE");
                player.sendMessage(enchanting.TextFormat.AQUA."Ranks activate immediately");
            }
            rand[] = 9;
            rand[] = 10;
            rand[] = 11;
            rand[] = 12;
            rand[] = 13;
            rand[] = 17;
            id = rand[mt_rand(0, (count(rand)-1))];
            plevel = mt_rand(1, 8);
            enchant = \pocketmine\item\enchantment\Enchantment.getEnchantment(id);
            enchant.setLevel(1);
            if(player.getExpLevel() >= plevel){
                player.setExperience(this.plugin.getServer().getExpectedExperience(player.getExpLevel() - plevel));
                hand.addEnchantment(enchant);
                if(!hand.hasEnchantments())player.sendMessage ("ERROR");
                player.sendMessage(enchanting.TextFormat.GREEN." Took ".TextFormat.GOLD.plevel." Levels".TextFormat.GREEN." to Enchant Your ".TextFormat.AQUA.hand.getName().TextFormat.GREEN." was given Enchant ".TextFormat.LIGHT_PURPLE.enchant.getName());

                player.getInventory().setItemInHand(hand);
                player.getInventory().sendContents(player);
                return;
            }else{
                player.sendMessage(enchanting.TextFormat.AQUA."plevel Levels of XP".TextFormat.RED." was need in order to give your item ".TextFormat.LIGHT_PURPLE.enchant.getName()."!");
                return;
            }
        }
        if(rank == false && !player.isOp()){
            player.sendMessage(enchanting.TextFormat.AQUA."You can only enchant Swords!");
            player.sendMessage(enchanting.TextFormat.RED."Please Support the server To Get Full Enchanting Abillity!");
            player.sendMessage(enchanting.TextFormat.RED."Buy Ranks from 5 at www.cyberechpp.com/MCPE");
            player.sendMessage(enchanting.TextFormat.RED."Ranks activate immediately");
            return true;
        }
        if(stripos(hand.getName(), "axe") || stripos(hand.getName(), "shovel") ){
            id = mt_rand(15, 18);
            plevel = mt_rand(1, 8);
            enchant = \pocketmine\item\enchantment\Enchantment.getEnchantment(id);
            enchant.setLevel(1);
            if(player.getExpLevel() >= plevel){
                player.setExperience(this.plugin.getServer().getExpectedExperience(player.getExpLevel() - plevel));
                hand.addEnchantment(enchant);
                player.sendMessage(enchanting.TextFormat.GREEN."Your ".TextFormat.AQUA.hand.getName().TextFormat.GREEN." was given Enchant ".TextFormat.LIGHT_PURPLE.enchant.getName());
                player.getInventory().setItemInHand(hand);
                player.getInventory().sendContents(player);
                return;
            }else{
                player.sendMessage(enchanting.TextFormat.AQUA."plevel Levels of XP".TextFormat.RED." was need in order to give your item ".TextFormat.LIGHT_PURPLE.enchant.getName()."!");
                return;
            }
        }

        if(hand instanceof \pocketmine\item\Armor){
            enchanting = TextFormat.LIGHT_PURPLE."[Enchanting]";
            if(rank == false && !player.isOp()){
                player.sendMessage(enchanting.TextFormat.AQUA."Please Support the server To Enchant Armor!");
                player.sendMessage(enchanting.TextFormat.AQUA."Buy Ranks from 5 at www.cyberechpp.com/MCPE");
                player.sendMessage(enchanting.TextFormat.AQUA."Ranks activate immediately");
                return true;
            }
            if(stripos(hand.getName(), "helment")){
                rand[] = 0;
                rand[] = 1;
                rand[] = 3;
                rand[] = 4;
                rand[] = 6;
                rand[] = 8;
                rand[] = 17;
                id = rand[mt_rand(0, (count(rand)-1))];
                plevel = mt_rand(1, 8);
                enchant = \pocketmine\item\enchantment\Enchantment.getEnchantment(id);
                enchant.setLevel(1);
                if(player.getExpLevel() >= plevel){
                    player.setExperience(this.plugin.getServer().getExpectedExperience(player.getExpLevel() - plevel));
                    hand.addEnchantment(enchant);
                    player.sendMessage(enchanting.TextFormat.GREEN." Your ".TextFormat.AQUA.hand.getName().TextFormat.GREEN." was given Enchant ".TextFormat.LIGHT_PURPLE.enchant.getName());
                    player.getInventory().setItemInHand(hand);
                    player.getInventory().sendContents(player);
                    return;
                }else{
                    player.sendMessage(enchanting.TextFormat.AQUA."plevel Levels of XP".TextFormat.RED." was need in order to give your item ".TextFormat.LIGHT_PURPLE.enchant.getName()."!");
                    return;
                }
            }
            if(stripos(hand.getName(), "chestplate")){
                rand[] = 0;
                rand[] = 1;
                rand[] = 3;
                rand[] = 4;
                rand[] = 5;
                rand[] = 17;
                id = rand[mt_rand(0, (count(rand)-1))];
                plevel = mt_rand(1, 8);
                enchant = \pocketmine\item\enchantment\Enchantment.getEnchantment(id);
                enchant.setLevel(1);
                if(player.getExpLevel() >= plevel){
                    player.setExperience(this.plugin.getServer().getExpectedExperience(player.getExpLevel() - plevel));
                    hand.addEnchantment(enchant);
                    player.sendMessage(enchanting.TextFormat.GREEN." Your ".TextFormat.AQUA.hand.getName().TextFormat.GREEN." was given Enchant ".TextFormat.LIGHT_PURPLE.enchant.getName());
                    player.getInventory().setItemInHand(hand);
                    player.getInventory().sendContents(player);
                    return;
                }else{
                    player.sendMessage(enchanting.TextFormat.AQUA."plevel Levels of XP".TextFormat.RED." was need in order to give your item ".TextFormat.LIGHT_PURPLE.enchant.getName()."!");
                    return;
                }
            }
            if(stripos(hand.getName(), "boots")){
                rand[] = 0;
                rand[] = 1;
                rand[] = 2;
                rand[] = 3;
                rand[] = 4;
                rand[] = 7;
                rand[] = 17;
                id = rand[mt_rand(0, (count(rand)-1))];
                plevel = mt_rand(1, 8);
                enchant = \pocketmine\item\enchantment\Enchantment.getEnchantment(id);
                enchant.setLevel(1);
                if(player.getExpLevel() >= plevel){
                    player.setExperience(this.plugin.getServer().getExpectedExperience(player.getExpLevel() - plevel));
                    hand.addEnchantment(enchant);
                    player.sendMessage(enchanting.TextFormat.GREEN."Your ".TextFormat.AQUA.hand.getName().TextFormat.GREEN." was given Enchant ".TextFormat.LIGHT_PURPLE.enchant.getName());
                    player.getInventory().setItemInHand(hand);
                    player.getInventory().sendContents(player);
                    return;
                }else{
                    player.sendMessage(enchanting.TextFormat.AQUA."plevel Levels of XP".TextFormat.RED." was need in order to give your item ".TextFormat.LIGHT_PURPLE.enchant.getName()."!");
                    return;
                }
            }
            rand[] = 0;
            rand[] = 1;
            rand[] = 3;
            rand[] = 4;
            rand[] = 17;
            id = rand[mt_rand(0, (count(rand)-1))];
            plevel = mt_rand(1, 8);
            enchant = \pocketmine\item\enchantment\Enchantment.getEnchantment(id);
            enchant.setLevel(1);
            if(player.getExpLevel() >= plevel){
                player.setExperience(this.plugin.getServer().getExpectedExperience(player.getExpLevel() - plevel));
                hand.addEnchantment(enchant);
                player.sendMessage(enchanting.TextFormat.GREEN." Your ".TextFormat.AQUA.hand.getName().TextFormat.GREEN." was given Enchant ".TextFormat.LIGHT_PURPLE.enchant.getName());
                player.getInventory().setItemInHand(hand);
                player.getInventory().sendContents(player);
                return;
            }else{
                player.sendMessage(enchanting.TextFormat.AQUA."plevel Levels of XP".TextFormat.RED." was need in order to give your item ".TextFormat.LIGHT_PURPLE.enchant.getName()."!");
                return;
            }
        }
        enchanting = TextFormat.LIGHT_PURPLE."[Enchanting]";
        if(rank == false && !player.isOp()){
            player.sendMessage(enchanting.TextFormat.AQUA."Please Support the server To Get Full Enchanting Abillity!");
            player.sendMessage(enchanting.TextFormat.AQUA."Buy Ranks from 5 at www.cyberechpp.com/MCPE");
            player.sendMessage(enchanting.TextFormat.AQUA."Ranks activate immediately");
            return true;
        }
        if(hand instanceof \pocketmine\item\Bow){
            rand[] = 19;
            rand[] = 20;
            rand[] = 21;
            rand[] = 22;
            rand[] = 17;
            id = rand[mt_rand(0, (count(rand)-1))];
            plevel = mt_rand(1, 8);
            enchant = \pocketmine\item\enchantment\Enchantment.getEnchantment(id);
            enchant.setLevel(1);
            if(player.getExpLevel() >= plevel){
                player.setExperience(this.plugin.getServer().getExpectedExperience(player.getExpLevel() - plevel));
                hand.addEnchantment(enchant);
                player.sendMessage(enchanting.TextFormat.GREEN." Your ".TextFormat.AQUA.hand.getName().TextFormat.GREEN." was given Enchant ".TextFormat.LIGHT_PURPLE.enchant.getName());
                player.getInventory().setItemInHand(hand);
                player.getInventory().sendContents(player);
                return;
            }else{
                player.sendMessage(enchanting.TextFormat.AQUA."plevel Levels of XP".TextFormat.RED." was need in order to give your item ".TextFormat.LIGHT_PURPLE.enchant.getName()."!");
                return;
            }
        }
        if(hand instanceof \pocketmine\item\Book){
            id = mt_rand(0, 24);
            plevel = mt_rand(1, 8);
            enchant = \pocketmine\item\enchantment\Enchantment.getEnchantment(id);
            enchant.setLevel(1);
            if(player.getExpLevel() >= plevel){
                player.setExperience(this.plugin.getServer().getExpectedExperience(player.getExpLevel() - plevel));
                hand.addEnchantment(enchant);
                player.sendMessage(enchanting.TextFormat.GREEN." Your ".TextFormat.AQUA.hand.getName().TextFormat.GREEN." was given Enchant ".TextFormat.LIGHT_PURPLE.enchant.getName());
                player.getInventory().setItemInHand(hand);
                player.getInventory().sendContents(player);
                return;
            }else{
                player.sendMessage(enchanting.TextFormat.AQUA."plevel Levels of XP".TextFormat.RED." was need in order to give your item ".TextFormat.LIGHT_PURPLE.enchant.getName()."!");
                return;
            }
        }
    }
*/

    @EventHandler(priority = EventPriority.HIGHEST)
    public void joinEvent(PlayerJoinEvent event) {
        new JoinEvent(plugin,event);
        //this.plugin.uuid[event.getPlayer().getName()][event.getPlayer().getClientId()] = date(DATE_COOKIE);
        String player = event.getPlayer().getName();
        this.plugin.death.put(player, 0);

        String fn = GetFactionFromMember(event.getPlayer().getName());
        Faction f = plugin.FFactory.getFaction(fn);
        if (f != null) {
            plugin.FFactory.List.put(fn.toLowerCase(), f);
            plugin.FFactory.FacList.put(player.toLowerCase(), fn);
        }

/*
        asd = "";
        //CHECK BOUNTY
        tb = 0;
        if(isset(this.plugin.bounty[strtolower(sender.getName())])){
            foreach(this.plugin.bounty[strtolower(sender.getName())] as name=>bounty){
                tb += bounty;
            }
            if(tb != 0){
                asd = "\n".TextFormat.AQUA."[Bounty tb]";
            }
        }
        faction = this.plugin.getPlayerFaction(player);
        if(faction !== false){
            this.plugin.MessageFaction(faction, TextFormat.GREEN."player Has Joined!");
            fc = this.plugin.DecodeFactionColor(this.plugin.getFactionColor(faction));
            if(fc == false){
                fc = TextFormat.GRAY;
            }
            abcdefg = fc."[faction] \n".TextFormat.RESET.event.getPlayer().getName();
        }else{
            abcdefg = event.getPlayer().getName();
        }
        event.getPlayer().setNameTag(abcdefg.asd);
        this.plugin.nt[strtolower(event.getPlayer().getName())] = abcdefg;
*/
        /*
        rank = this.plugin.GetRank(player);
        if(sender.isOp() && (rank == false || rank == "Guest")){
            sender.kick("YOu should not be OP");
        }
        a = array();
        aaa = @mysqli_query( this.plugin.db2,"SELECT * FROM `ranks` WHERE `name` = 'player'");
        if(@mysqli_num_rows(aaa) > 0){
            f = false;
            while(row = @mysqli_fetch_assoc(aaa)){
                if(row['expires'] < strtotime("now") && row['forever'] == 0){
                    continue;
                }
                f = true;
                if(stripos(row["prefix"],"CRATE"))return true;
                a["prefix"] = row["prefix"];
                a["color"] = row["color"];
                a["claimed"] = row["claimed"];
                break;
            }
            if(f == false){
                unset(this.plugin.CC.yml["prefixs"][player]);
                return true;
            }
        }else{
            unset(this.plugin.CC.yml["prefixs"][player]);
            return true;
        }
        if(a['color'] !== ""){
            c = explode(";", a['color']);
            color = "";
            foreach(c as colorcode){
                if(colorcode == "")break;
                color .= "§".array[colorcode];
            }
        }else{
            color = "§a";
        }
        rankt = a['prefix'];
        if(rankt == "")rankt = rank;
        if(rankt == "")rankt = "UNKNOWN";
        if(rank !== "Guest")this.plugin.getServer ().getScheduler ().scheduleDelayedTask (new SetNameTeg(this.plugin, event.getPlayer(), color.rankt."\n".abcdefg), 20);
        if(rank !== "Guest" && rank !== "OP")this.plugin.CC.yml["prefixs"][player] = color.rankt;
        if(rank !== "Guest")event.getPlayer().setNameTag (color.rankt."\n".abcdefg);
        this.plugin.nt[strtolower(event.getPlayer().getName())] = color.rankt."\n".abcdefg;
        echo color.rankt."-rank\n".abcdefg;
        if(rank !== "Guest" && (rank == "OP" || rank == "BUILDER"))event.getPlayer().setOp(true);
        //if(rank == "Guest")this.plugin.CC.yml["prefixs"][player] = null;
        if(rank == "Guest")event.getPlayer().setOp (false);
        //this.CC.yml["prefixs"][player] = "§a".rank;
        if(a['claimed'] == 0){
            event.getPlayer().sendMessage(TextFormat.AQUA."Use /claim to Claim your Rank Rewards!");
        }
        */
        // }
    }

    @EventHandler
    public void BucketEmpty(PlayerBucketEmptyEvent ev) {
        if (ev.getPlayer().isOp()) return;
        Vector3 spawn = ev.getPlayer().getLevel().getSpawnLocation();
        double sx = spawn.getX();
        double sz = spawn.getZ();
        double px = ev.getPlayer().getX();
        double pz = ev.getPlayer().getZ();
        boolean x = false;
        boolean z = false;
        if ((sx - 200) < px && px < (sx + 200)) {
            x = true;
        }
        if ((sz - 200) < pz && pz < (sz + 200)) {
            z = true;
        }
        if (z && x) {
            ev.getPlayer().sendMessage(TextFormat.RED + "No Using Buckets 200 Block close to spawn!");
            ev.setCancelled();
            return;
        }

        String chunkfaction = this.plugin.GetChunkOwner((int) ev.getPlayer().getX() >> 4, (int) ev.getPlayer().getZ() >> 4);
        if (chunkfaction != null) {
            String pf = this.plugin.getPlayerFaction(ev.getPlayer().getName());
            if (pf != null) {
                if (pf.equalsIgnoreCase(chunkfaction)) return;
                ev.getPlayer().sendMessage(FactionsMain.NAME + TextFormat.GRAY + " This area is claimed by" + chunkfaction);
            }
            ev.setCancelled(true);
        }

    }

    @EventHandler
    public void BucketFill(PlayerBucketFillEvent ev) {
        if (ev.getPlayer().isOp()) return;
        Vector3 spawn = ev.getPlayer().getLevel().getSpawnLocation();
        double sx = spawn.getX();
        double sz = spawn.getZ();
        double px = ev.getPlayer().getX();
        double pz = ev.getPlayer().getZ();
        boolean x = false;
        boolean z = false;
        if ((sx - 200) < px && px < (sx + 200)) {
            x = true;
        }
        if ((sz - 200) < pz && pz < (sz + 200)) {
            z = true;
        }
        if (z && x) {
            ev.getPlayer().sendMessage(TextFormat.RED + "No Using Buckets 200 Block close to spawn");
            ev.setCancelled();
        }

        String chunkfaction = plugin.GetChunkOwner((int) ev.getPlayer().getX() >> 4, (int) ev.getPlayer().getZ() >> 4);
        if (chunkfaction != null) {
            String pf = plugin.getPlayerFaction(ev.getPlayer().getName());
            if (pf != null) {
                if (pf.equalsIgnoreCase(chunkfaction)) return;
                ev.getPlayer().sendMessage(FactionsMain.NAME + TextFormat.GRAY + "This area is claimed by " + chunkfaction);
            }
            ev.setCancelled(true);
        }
    }

    /*
    public function PreLogin(\pocketmine\event\player\PlayerPreLoginEvent ev){
        if(count(this.plugin.getServer().getOnlinePlayers()) < this.plugin.getServer().getMaxPlayers())return;
        name = ev.getPlayer().getName();
        rank = this.plugin.GetRank(name, true);
        if(rank != false && rank != "Guest"){
            foreach (this.plugin.getServer().getOnlinePlayers() as p){
                pr = this.plugin.GetRank(p.getName());
                if(pr != false && pr != "Guest")continue;
                p.kick("You were Kicked to make room for a Paid Rank! Buy a Rank at cybertechpp.com/MCPE to ensure this dosen't happen again.");
                break;
            }
        }
    }*/
    @EventHandler()
    public void PreLogin(PlayerPreLoginEvent ev){
        new PreLoginEvent(plugin,ev);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void PURGEPVPNSTUFF(EntityDamageEvent factionDamage) {
        if (plugin.Purge) {
            //Purge is on
            factionDamage.setCancelled(false);
        }

        if (factionDamage.isCancelled()) return;
        if (factionDamage instanceof EntityDamageByEntityEvent) {
            if ((factionDamage.getEntity() instanceof Player) && (((EntityDamageByEntityEvent) factionDamage).getDamager() instanceof Player)) {
                Integer t = (int) (Calendar.getInstance().getTime().getTime() / 1000) + 10;
                this.plugin.pvplog.put(factionDamage.getEntity().getName(), t);
                this.plugin.pvplog.put(((EntityDamageByEntityEvent) factionDamage).getDamager().getName(), t);
                float d = factionDamage.getFinalDamage();
                String text = TextFormat.RED.toString() + d + " Damage";
                long eid = 9999999 + (long) (Math.random() * 50000);
                Entity e = factionDamage.getEntity();
                CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(e.x, e.y + 1.5, e.z), text);
                ft.entityId = eid;
                ArrayList<Player> ps = new ArrayList<Player>() {{
                    add((Player) ((EntityDamageByEntityEvent) factionDamage).getDamager());
                }};
                Entity dmger = ((EntityDamageByEntityEvent) factionDamage).getDamager();
                dmger.getLevel().addParticle(ft, ps);
                this.plugin.popups.put(eid, ft);
                this.plugin.getServer().getScheduler().scheduleDelayedTask(new PopUp(this.plugin, (Player) ((EntityDamageByEntityEvent) factionDamage).getDamager(), eid, ((EntityDamageByEntityEvent) factionDamage).getDamager().getLevel()), 7);
            } else if (((EntityDamageByEntityEvent) factionDamage).getDamager() instanceof Player) {
                float d = factionDamage.getFinalDamage();
                String text = TextFormat.RED.toString() + d + " Damage";
                long eid = 9999999 + (long) (Math.random() * 50000);
                Entity e = factionDamage.getEntity();
                CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(e.x, e.y + 1.5, e.z), text);
                ft.entityId = eid;
                ArrayList<Player> ps = new ArrayList<Player>() {{
                    add((Player) ((EntityDamageByEntityEvent) factionDamage).getDamager());
                }};
                ((EntityDamageByEntityEvent) factionDamage).getDamager().getLevel().addParticle(ft, ps);
                this.plugin.popups.put(eid, ft);
                this.plugin.getServer().getScheduler().scheduleDelayedTask(new PopUp(this.plugin, (Player) ((EntityDamageByEntityEvent) factionDamage).getDamager(), eid, ((EntityDamageByEntityEvent) factionDamage).getDamager().getLevel()), 7);
            } /*else {
                float d = factionDamage.getFinalDamage();
                String text = TextFormat.RED.toString() + d + " Damage";
                long eid = 9999999 + (long) (Math.random() * 50000);
                Entity e = factionDamage.getEntity();
                CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(e.x, e.y + 1.5, e.z), text);
                ft.entityId = eid;
                factionDamage.getEntity().getLevel().addParticle(ft);
                this.plugin.popups.put(eid, ft);
                this.plugin.getServer().getScheduler().scheduleDelayedTask(new PopUp(this.plugin, (Player) ((EntityDamageByEntityEvent) factionDamage).getDamager(), eid, ((EntityDamageByEntityEvent) factionDamage).getDamager().getLevel()), 7);

            }*/
        }

    }

    @EventHandler
    public void CommandEvent(PlayerCommandPreprocessEvent event){
        plugin.getServer().getLogger().info(event.getPlayer().getName()+"  > "+event.getMessage());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void factionPVP(EntityDamageEvent factionDamage) {
        if (factionDamage.isCancelled()) return;
        Faction pf = plugin.FFactory.getFaction("peace");
        int x = factionDamage.getEntity().getFloorX() >> 4;
        int z = factionDamage.getEntity().getFloorZ() >> 4;
        if (pf.GetPlots().contains(x+"|"+z)){
            if (factionDamage.getEntity() instanceof Player) {
                if(((Player)factionDamage.getEntity()).isOp()){
                    factionDamage.setCancelled(false);
                    return;
                }
            }
                factionDamage.setCancelled();
                return;
        }
        if (factionDamage instanceof EntityDamageByEntityEvent) {
            if (!(factionDamage.getEntity() instanceof Player) || !(((EntityDamageByEntityEvent) factionDamage).getDamager() instanceof Player))
                return;
            String player1 = factionDamage.getEntity().getName();
            String player2 = ((EntityDamageByEntityEvent) factionDamage).getDamager().getName();
            ((Player) ((EntityDamageByEntityEvent) factionDamage).getDamager()).sendPopup(player1 + "'s Health: " + ((Player) factionDamage.getEntity()).getPlayer().getHealth() + "/" + ((Player) factionDamage.getEntity()).getPlayer().getMaxHealth());
            String faction1 = this.plugin.getPlayerFaction(player1.toLowerCase());
            String faction2 = this.plugin.getPlayerFaction(player2.toLowerCase());
            if (faction1 == null || faction2 == null) return;
            if (faction1.equalsIgnoreCase(faction2)) {
                factionDamage.setCancelled(true);
                return;
            }
            if (this.plugin.isFactionsAllyed(faction1, faction2)) {
                factionDamage.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void factionBlockBreakProtect(BlockBreakEvent event) {
        if (event.getPlayer() != null && event.getPlayer().isOp()) {
            String pf = this.plugin.getPlayerFaction(event.getPlayer().getName());
            Faction fac = plugin.FFactory.getFaction(pf);
            if (fac != null) fac.HandleBreakEvent(event);
            event.setCancelled(false);
            return;
        }
        if (event.isCancelled()) return;
        String pf = this.plugin.getPlayerFaction(event.getPlayer().getName());
        String chunkfaction = this.plugin.GetChunkOwner((int) event.getBlock().getX() >> 4, (int) event.getBlock().getZ() >> 4);
        if (chunkfaction != null) {
            if (pf != null) {
                if (pf.equalsIgnoreCase(chunkfaction) || plugin.AtWar(pf, chunkfaction)) {
                    Faction fac = plugin.FFactory.getFaction(pf);
                    if (fac != null) fac.HandleBreakEvent(event);
                    return;
                }
                event.setCancelled(true);
                event.getPlayer().sendMessage(FactionsMain.NAME + "This area is claimed by " + chunkfaction);
                return;
            }
            event.setCancelled(true);
        }
        Faction fac = plugin.FFactory.getFaction(pf);
        if (fac != null) fac.HandleBreakEvent(event);
    }

    @EventHandler
    public void factionBlockPlaceProtect(BlockPlaceEvent event) {
        if (event.getPlayer() != null && event.getPlayer().isOp()) {
            String pf = this.plugin.getPlayerFaction(event.getPlayer().getName());
            Faction fac = plugin.FFactory.getFaction(pf);
            if (fac != null) fac.HandlePlaceEvent(event);
            return;
        }
        if (event.isCancelled()) return;
        String pf = this.plugin.getPlayerFaction(event.getPlayer().getName());
        String chunkfaction = this.plugin.GetChunkOwner((int) event.getBlock().getX() >> 4, (int) event.getBlock().getZ() >> 4);
        if (chunkfaction != null) {
            if (pf != null) {
                if (pf.equalsIgnoreCase(chunkfaction) || plugin.AtWar(pf, chunkfaction)) {
                    Faction fac = plugin.FFactory.getFaction(pf);
                    if (fac != null) fac.HandlePlaceEvent(event);
                    return;
                }
                event.setCancelled(true);
                event.getPlayer().sendMessage(FactionsMain.NAME + TextFormat.GRAY + " This area is claimed by " + chunkfaction);
                return;
            }
            event.setCancelled(true);
        }
        Faction fac = plugin.FFactory.getFaction(pf);
        if (fac != null) fac.HandlePlaceEvent(event);
    }
        
        /*Add Soon
         * public function PlayerItemHeldEvent(\pocketmine\event\player\PlayerItemHeldEvent event) {
            player = event.getPlayer();
            playern = player.getName();
            if(in_array(playern, this.plugin.block, true)){
                block = event.getItem();
                bid = block.getId();
                if(bid == Item.DIAMOND_BLOCK){
                    //Use Diamond Chest
                    player.getInventory().clearAll();
                    player.getInventory().setContents(this.plugin.saveinv[playern]);
                    this.OpenDiamondChest(player, player.getLevel());
                }
                if(bid == Item.EMERALD_BLOCK){
                    player.getInventory().clearAll();
                    player.getInventory().setContents(this.plugin.saveinv[playern]);
                    this.OpenEmeraldChest(player, player.getLevel());
                    //Use Emerald Chest
                }
                if(bid == Item.IRON_BLOCK){
                    player.getInventory().clearAll();
                    //Use Iron Chest
                }
                if(bid == Item.GOLD_BLOCK){
                    player.getInventory().clearAll();
                    //Use Gold Chest
                }
                if(bid == Item.COAL_BLOCK){
                    player.getInventory().clearAll();
                    //Use Coal Chest
                }
                
            }
        }*/
/*        
        public function OpenEmeraldChest(Player player, \pocketmine\level\Level level) {
            playern = player.getName();
            //Number Of Winneings
            rand = mt_rand(0, 100);
            numofwins = 2;
            if(rand >= 75)numofwins = mt_rand(2, 10);
            for(x = 0;x <= numofwins; x++){
                rand = mt_rand(0, 100);
                //Diamond
                if(rand > 90){
                    item = new Item(264, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Diamonds!";
                    continue;
                }
                //TNT
                if(rand > 80){
                    item = new Item(Item.TNT, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().addItem(clone item);
                    won[] = "rand TNT!";
                    continue;
                }
                //Iron
                if(rand > 70){
                    item = new Item(Item.IRON_INGOT, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Iron Ingots!";
                    continue;
                }
                
                //Gold
                if(rand > 60){
                    item = new Item(Item.GOLD_INGOT, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Gold Ingots!";
                    continue;
                }
                
                //Coal
                if(rand > 50){
                    item = new Item(Item.COAL, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().setContents(this.plugin.saveinv[playern]);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Coal!";
                    continue;
                }
                
                 //Obsidian
                if(rand > 40){
                    item = new Item(Item.OBSIDIAN, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().setContents(this.plugin.saveinv[playern]);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Obsidian!";
                    continue;
                }
                
                 //Netherrack
                if(rand > 30){
                    item = new Item(Item.NETHERRACK, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().setContents(this.plugin.saveinv[playern]);
                    player.getInventory().addItem(clone item);
                    won[] = "rand NetherRack!";
                    continue;
                }
                
                 //NETHER_QUARTZ
                if(rand > 20){
                    item = new Item(Item.NETHER_QUARTZ, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().setContents(this.plugin.saveinv[playern]);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Nether Quartz!";
                    continue;
                }
            }
            message = TextFormat.GREEN."Congradulations!!!! You Won: \n";
            foreach(won as w)message .= TextFormat.AQUA." > ".w."!!! \n";
            message .= TextFormat.GREEN."Wow You Won A Lot!";
            player.sendMessage(message);
            n = array_search(player.getName(),this.plugin.block);
            unset(this.plugin.block[n]);
        }
        public function OpenDiamondChest(Player player, \pocketmine\level\Level level) {
            playern = player.getName();
            //Number Of Winneings
            rand = mt_rand(0, 100);
            numofwins = 1;
            if(rand >= 75)numofwins = mt_rand(0, 5);
            for(x = 0;x <= numofwins; x++){
                rand = mt_rand(0, 100);
                //Diamond
                if(rand > 90){
                    item = new Item(264, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Diamonds!";
                    continue;
                }
                //TNT
                if(rand > 75){
                    item = new Item(Item.TNT, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().addItem(clone item);
                    won[] = "rand TNT!";
                    continue;
                }
                //Iron
                if(rand > 50){
                    item = new Item(Item.IRON_INGOT, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Iron Ingots!";
                    continue;
                }
                
                //Gold
                if(rand > 25){
                    item = new Item(Item.GOLD_INGOT, 0, 0);
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(0, 100);
                        if(rand > 90){
                            rand = mt_rand(1, 25);
                            item.setCount(rand);
                        }
                        rand = mt_rand(1, 10);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 5);
                    item.setCount(rand);
                    player.getInventory().addItem(clone item);
                    won[] = "rand Gold Ingots!";
                    continue;
                }
                
                //Coal
                item = new Item(Item.COAL, 0, 0);
                rand = mt_rand(0, 100);
                if(rand > 90){
                    rand = mt_rand(0, 100);
                    if(rand > 90){
                        rand = mt_rand(1, 25);
                        item.setCount(rand);
                    }
                    rand = mt_rand(1, 10);
                    item.setCount(rand);
                }
                rand = mt_rand(1, 5);
                item.setCount(rand);
                player.getInventory().setContents(this.plugin.saveinv[playern]);
                player.getInventory().addItem(clone item);
                won[] = "rand Coal!";
                continue;
            }
            message = TextFormat.GREEN."Congradulations!!!! You Won: \n";
            foreach(won as w)message .= TextFormat.AQUA." > ".w."!!! \n";
            message .= TextFormat.GREEN."Wow You Won A Lot!";
            player.sendMessage(message);
            n = array_search(player.getName(),this.plugin.block);
            unset(this.plugin.block[n]);
        }
*/

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playern = event.getPlayer().getName();
            /*if(event.getBlock().getId() == 116){
                event.setCancelled();
                this.EnchantItemInHand(event.getPlayer());
                return true;
            }*/
        /*if(event.getBlock().getId() == 145){
            event.setCancelled();
            //this.AnvilItemInHand(event.getPlayer());
            return;
        }*/

            /* if(player.getInventory().getItemInHand().getId() == \pocketmine\item\Item.EMERALD_BLOCK){
                //New Random Chest!
                //Save inv
                this.plugin.saveinv[playern] = player.getInventory().getContents();
                db2 = this.plugin.db2;
                this.plugin.block[] = playern;
                a1 = @mysqli_query(db2, "SELECT * FROM `Chests` WHERE `name` = 'playern'");
                a2 = @mysqli_fetch_assoc(a1);
                diamond = a2['diamond'];
                emerald = a2['emerald'];
                iron = a2['iron'];
                gold = a2['gold'];
                contents[] = new Item(Item.DIAMOND_BLOCK, 0, diamond);
                contents[] = new Item(Item.EMERALD_BLOCK, 0, emerald);
                contents[] = new Item(Item.TNT, 0, iron);
                contents[] = new Item(Item.GOLD_BLOCK, 0, gold);
                player.getInventory().setContents(contents);
                
                //player.getInventory().setHotbarSlotIndex(index, slot);
                player.getInventory().setHotbarSlotIndex(0, 4);
                player.getInventory().setHotbarSlotIndex(1, 0);
                player.getInventory().setHotbarSlotIndex(2, 1);
                player.getInventory().setHotbarSlotIndex(3, 2);
                player.getInventory().setHotbarSlotIndex(4, 3);
                player.getInventory().sendHeldItem(player);
                player.getInventory().sendContents(player);
                player.getInventory().sendSlot(0,player);
                player.getInventory().sendSlot(1,player);
                player.getInventory().sendSlot(2,player);
                player.getInventory().sendSlot(3,player);
                player.getInventory().sendSlot(4,player);
                player.despawnFromAll();
                player.sendMessage(TextFormat.GREEN."Please Choose a Chest Type!");
                //this.OpenDiamondChest(player);
            }
            */
        if (player.getInventory().getItemInHand().getId() == Item.COMPASS) {
            this.plugin.getServer().dispatchCommand(player, "home 1");
        }
        if (player.getInventory().getItemInHand().getId() == Item.END_PORTAL) {
            this.plugin.getServer().dispatchCommand(player, "f home");
        }
    }
}
