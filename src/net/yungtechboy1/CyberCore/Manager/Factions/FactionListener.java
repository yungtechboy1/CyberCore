package net.yungtechboy1.CyberCore.Manager.Factions;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.scheduler.NukkitRunnable;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;

import static net.yungtechboy1.CyberCore.FormType.MainForm.*;

/**
 * Created by carlt on 3/7/2019.
 */
public class FactionListener implements Listener {
    private CyberCoreMain plugin;

    private FactionsMain factions;
    public FactionListener(CyberCoreMain main, FactionsMain factions) {
        plugin = main;
        this.factions = factions;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void FactionChatEvent(PlayerChatEvent event) {
//        ChatEvent ce = new ChatEvent(Main.FM,event);
//        event = ce.Event;
    }

    @EventHandler
    public void FactionPlayerDeath(PlayerDeathEvent event) {
        if (event == null) return;
        CorePlayer player = plugin.getCorePlayer(event.getEntity());
        String playern = event.getEntity().getName();
        EntityDamageEvent cause = event.getEntity().getLastDamageCause();
        event.getEntity().setExperience(0);
        player.addDeath();
        if (cause != null && cause.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (cause instanceof EntityDamageByEntityEvent && event.getEntity() instanceof Player) {
                Entity e = ((EntityDamageByEntityEvent) cause).getDamager();
                CorePlayer killer = plugin.getCorePlayer(e.getName());
                if (e instanceof Player) {
                    event.setDeathMessage(TextFormat.GRAY + playern + " was killed by " + e.getName() + TextFormat.RED + " (" + e.getHealth() + "❤)");
                    //@TODO Bounty XD
                    /*
                    int tb = 0;
                    if(isset(plugin.bounty[strtolower(playern)])){
                        foreach(plugin.bounty[strtolower(playern)] as name=>bounty){
                            tb += bounty;
                            event.getEntity().setNameTag(plugin.nt[strtolower(event.getPlayer().getName())]);
                            unset(plugin.bounty[strtolower(playern)][name]);
                        }
                        if(tb != 0){
                            plugin.api.addMoney(e,tb);
                            e.sendMessage(TextFormat.GREEN."[Bounty] Bounty Claimed for the Amount of tb");
                            e.addExperience(5);
                        }
                    }*/
                    String killername = e.getName();
                    //DEath FIne
                    Faction kf = factions.FFactory.getPlayerFaction((Player) event.getEntity());
                    if (kf != null) {
                        kf.HandleKillEvent(event);
                        kf.TakePower(2);
                    }
                    killer.addKill();
                    if (killer.kills == 5) {
                        plugin.getServer().broadcastMessage(TextFormat.GREEN + killername + " is on a 5 KillStreak!");
                        //if(kf != null)plugin.AddFactionPower(kf, 5);
                    }
                    if (killer.kills == 8) {
                        plugin.getServer().broadcastMessage(TextFormat.AQUA + killername + " is on a 8 KillStreak!");
                        //if(kf != null)plugin.AddFactionPower(kf, 8);
                    }
                    if (killer.kills == 10) {
                        plugin.getServer().broadcastMessage(TextFormat.LIGHT_PURPLE + killername + " is on a 10 KillStreak!");
                        //if(kf != null)plugin.AddFactionPower(kf, 10);
                    }
                    if (killer.kills > 10) {
                        Integer kills = killer.kills;
                        plugin.getServer().broadcastMessage(TextFormat.LIGHT_PURPLE + killername + " is on a " + kills + " KillStreak!");
                        //if(kf != null)plugin.AddFactionPower(kf, kills*2);
                    }
                }
            }
        }
    }

    //GUI Listener
//    @EventHandler
//    public void PFRE(PlayerFormRespondedEvent pr) {
//        int fid = pr.getFormID();
//        Player p = pr.getPlayer();
//        CorePlayer cp = ((CorePlayer) p);
//        switch (cp.LastSentFormType) {
//            case Class_0:
//                FormResponseModal frm = (FormResponseModal) pr.getResponse();
//                if (frm.getClickedButtonId() == 0) {
//                    System.out.println("Bye!");
//                    cp.LastSentFormType = NULL;
//                } else {
//                    System.out.println("HI!!!!!");
//                    cp.showFormWindow(cp.getNewWindow());
//                    cp.LastSentFormType = Class_1;
//                    cp.clearNewWindow();
//                }
//                break;
//            case Class_1:
//                FormResponseSimple frs = (FormResponseSimple) pr.getResponse();
//                int k = frs.getClickedButtonId();
//                if(cp.LastSentSubMenu == FormType.SubMenu.MainMenu) {
//                    if (k == 0) {//Offense
//                        cp.showFormWindow(new FormWindowSimple("Choose your Class Catagory!", "Visit Cybertechpp.com for more info on classes!",
//                                new ArrayList<ElementButton>() {{
//                                    add(new ElementButton("Assassin"));
//                                    add(new ElementButton("Knight"));
//                                    add(new ElementButton("Raider"));
//                                    add(new ElementButton("Theif"));
//                                }}));
//                        cp.LastSentFormType = Class_1;
//                        cp.LastSentSubMenu = FormType.SubMenu.Offense;
//                    }
//                }else if(cp.LastSentSubMenu == FormType.SubMenu.Offense){
//                    switch (k){
//                        case 0:
//                            break;//Assassin
//                        case 1:
//                            break;//Knight
//                        case 2:
//                            break;//Raider
//                        case 3:
//                            break;//Theif
//                    }
//                }
//                break;
//        }
//    }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void FationsJoinEvent(PlayerJoinEvent event) {
            //plugin.uuid[event.getPlayer().getName()][event.getPlayer().getClientId()] = date(DATE_COOKIE);
            String player = event.getPlayer().getName();

            if(factions.isInFaction(player)) {
                new NukkitRunnable() {
                    @Override
                    public void run() {
                        event.getPlayer().sendPopup("Your Faction is : HACKED FACTION");//TODO: Make faction data retrievable and savable.
                    }
                }.runTaskLater(plugin, 60);
            }


/*
        asd = "";
        //CHECK BOUNTY
        tb = 0;
        if(isset(plugin.bounty[strtolower(sender.getName())])){
            foreach(plugin.bounty[strtolower(sender.getName())] as name=>bounty){
                tb += bounty;
            }
            if(tb != 0){
                asd = "\n".TextFormat.AQUA."[Bounty tb]";
            }
        }
        faction = plugin.getPlayerFaction(player);
        if(faction !== false){
            plugin.MessageFaction(faction, TextFormat.GREEN."player Has Joined!");
            fc = plugin.DecodeFactionColor(plugin.getFactionColor(faction));
            if(fc == false){
                fc = TextFormat.GRAY;
            }
            abcdefg = fc."[faction] \n".TextFormat.RESET.event.getPlayer().getName();
        }else{
            abcdefg = event.getPlayer().getName();
        }
        event.getPlayer().setNameTag(abcdefg.asd);
        plugin.nt[strtolower(event.getPlayer().getName())] = abcdefg;
*/
        /*
        rank = plugin.GetRank(player);
        if(sender.isOp() && (rank == false || rank == "Guest")){
            sender.kick("YOu should not be OP");
        }
        a = array();
        aaa = @mysqli_query( plugin.db2,"SELECT * FROM `ranks` WHERE `name` = 'player'");
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
                unset(plugin.CC.yml["prefixs"][player]);
                return true;
            }
        }else{
            unset(plugin.CC.yml["prefixs"][player]);
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
        if(rank !== "Guest")plugin.getServer ().getScheduler ().scheduleDelayedTask (new SetNameTeg(Main.FM, event.getPlayer(), color.rankt."\n".abcdefg), 20);
        if(rank !== "Guest" && rank !== "OP")plugin.CC.yml["prefixs"][player] = color.rankt;
        if(rank !== "Guest")event.getPlayer().setNameTag (color.rankt."\n".abcdefg);
        plugin.nt[strtolower(event.getPlayer().getName())] = color.rankt."\n".abcdefg;
        echo color.rankt."-rank\n".abcdefg;
        if(rank !== "Guest" && (rank == "OP" || rank == "BUILDER"))event.getPlayer().setOp(true);
        //if(rank == "Guest")plugin.CC.yml["prefixs"][player] = null;
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

        //TODO Add 30 Sec Cooldown

        //TODO Create CheckClaim Function
        //Checks factions claim
        String chunkfaction = factions.GetChunkOwner((int) ev.getPlayer().getX() >> 4, (int) ev.getPlayer().getZ() >> 4);
        if (chunkfaction != null) {
            String pf = factions.getPlayerFaction(ev.getPlayer().getName());
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

        String chunkfaction = factions.GetChunkOwner((int) ev.getPlayer().getX() >> 4, (int) ev.getPlayer().getZ() >> 4);
        if (chunkfaction != null) {
            String pf = factions.getPlayerFaction(ev.getPlayer().getName());
            if (pf != null) {
                if (pf.equalsIgnoreCase(chunkfaction)) return;
                ev.getPlayer().sendMessage(FactionsMain.NAME + TextFormat.GRAY + "This area is claimed by " + chunkfaction);
            }
            ev.setCancelled(true);
        }
    }


    //IPChecker
    //IPChecker
    //
    //TODO CHECK BANNED
    @EventHandler()
    public void PreLogin(PlayerPreLoginEvent ev) {

    }

//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void PURGEPVPNSTUFF(EntityDamageEvent factionDamage) {
//        if (Main.Purge) {
//            //Purge is on
//            factionDamage.setCancelled(false);
//        }
//
//        if (factionDamage.isCancelled()) return;
//        if (factionDamage instanceof EntityDamageByEntityEvent) {
//            if ((factionDamage.getEntity() instanceof Player) && (((EntityDamageByEntityEvent) factionDamage).getDamager() instanceof Player)) {
//                Integer t = (int) (Calendar.getInstance().getTime().getTime() / 1000) + 10;
//                plugin.pvplog.put(factionDamage.getEntity().getName(), t);
//                plugin.pvplog.put(((EntityDamageByEntityEvent) factionDamage).getDamager().getName(), t);
//                float d = factionDamage.getFinalDamage();
//                String text = TextFormat.RED.toString() + d + " Damage";
//                long eid = 9999999 + (long) (Math.random() * 50000);
//                Entity e = factionDamage.getEntity();
//                CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(e.x, e.y + 1.5, e.z), text);
//                ft.entityId = eid;
//                ArrayList<Player> ps = new ArrayList<Player>() {{
//                    add((Player) ((EntityDamageByEntityEvent) factionDamage).getDamager());
//                }};
//                Entity dmger = ((EntityDamageByEntityEvent) factionDamage).getDamager();
//                dmger.getLevel().addParticle(ft, ps);
//                plugin.popups.put(eid, ft);
//                plugin.getServer().getScheduler().scheduleDelayedTask(new PopUp(Main.FM, (Player) ((EntityDamageByEntityEvent) factionDamage).getDamager(), eid, ((EntityDamageByEntityEvent) factionDamage).getDamager().getLevel()), 7);
//            } else if (((EntityDamageByEntityEvent) factionDamage).getDamager() instanceof Player) {
//                float d = factionDamage.getFinalDamage();
//                String text = TextFormat.RED.toString() + d + " Damage";
//                long eid = 9999999 + (long) (Math.random() * 50000);
//                Entity e = factionDamage.getEntity();
//                CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(e.x, e.y + 1.5, e.z), text);
//                ft.entityId = eid;
//                ArrayList<Player> ps = new ArrayList<Player>() {{
//                    add((Player) ((EntityDamageByEntityEvent) factionDamage).getDamager());
//                }};
//                ((EntityDamageByEntityEvent) factionDamage).getDamager().getLevel().addParticle(ft, ps);
//                plugin.popups.put(eid, ft);
//                plugin.getServer().getScheduler().scheduleDelayedTask(new PopUp(Main.FM, (Player) ((EntityDamageByEntityEvent) factionDamage).getDamager(), eid, ((EntityDamageByEntityEvent) factionDamage).getDamager().getLevel()), 7);
//            } /*else {
//                float d = factionDamage.getFinalDamage();
//                String text = TextFormat.RED.toString() + d + " Damage";
//                long eid = 9999999 + (long) (Math.random() * 50000);
//                Entity e = factionDamage.getEntity();
//                CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(e.x, e.y + 1.5, e.z), text);
//                ft.entityId = eid;
//                factionDamage.getEntity().getLevel().addParticle(ft);
//                plugin.popups.put(eid, ft);
//                plugin.getServer().getScheduler().scheduleDelayedTask(new PopUp(Main.FM, (Player) ((EntityDamageByEntityEvent) factionDamage).getDamager(), eid, ((EntityDamageByEntityEvent) factionDamage).getDamager().getLevel()), 7);
//
//            }*/
//        }
//
//    }

    //LOG COMMANDS IN CONSOLE
    @EventHandler
    public void CommandEvent(PlayerCommandPreprocessEvent event) {
        plugin.getServer().getLogger().info(event.getPlayer().getName() + "  > " + event.getMessage());
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void factionPVP(EntityDamageEvent factionDamage) {
        if (factionDamage.isCancelled()) return;
//        factionDamage.get
//        Faction pf = factions.FFactory.getFaction("peace");
//        int x = factionDamage.getEntity().getFloorX() >> 4;
//        int z = factionDamage.getEntity().getFloorZ() >> 4;
//        if (pf.GetPlots().contains(x + "|" + z)) {
//            if (factionDamage.getEntity() instanceof Player) {
//                if (((Player) factionDamage.getEntity()).isOp()) {
//                    factionDamage.setCancelled(false);
//                    return;
//                }
//            }
//            factionDamage.setCancelled();
//            return;
//        }
//        if (factionDamage instanceof EntityDamageByEntityEvent) {
//            if (!(factionDamage.getEntity() instanceof Player) || !(((EntityDamageByEntityEvent) factionDamage).getDamager() instanceof Player))
//                return;
//            String player1 = factionDamage.getEntity().getName();
//            String player2 = ((EntityDamageByEntityEvent) factionDamage).getDamager().getName();
//            ((Player) ((EntityDamageByEntityEvent) factionDamage).getDamager()).sendPopup(player1 + "'s Health: " + ((Player) factionDamage.getEntity()).getPlayer().getHealth() + "/" + ((Player) factionDamage.getEntity()).getPlayer().getMaxHealth());
//            String faction1 = factions.getPlayerFaction(player1.toLowerCase());
//            String faction2 = factions.getPlayerFaction(player2.toLowerCase());
//            if (faction1 == null || faction2 == null) return;
//            if (faction1.equalsIgnoreCase(faction2)) {
//                factionDamage.setCancelled(true);
//                return;
//            }
//            if (factions.isFactionsAllyed(faction1, faction2)) {
//                factionDamage.setCancelled(true);
//                return;
//            }
//        }
    }

    @EventHandler
    public void factionBlockBreakProtect(BlockBreakEvent event) {
        if (event.getPlayer() != null && event.getPlayer().isOp()) {
            String pf = factions.getPlayerFaction(event.getPlayer().getName());
            Faction fac = factions.FFactory.getFaction(pf);
            if (fac != null) fac.HandleBreakEvent(event);
            event.setCancelled(false);
            return;
        }
        if (event.isCancelled()) return;
        String pf = factions.getPlayerFaction(event.getPlayer().getName());
        String chunkfaction = factions.GetChunkOwner((int) event.getBlock().getX() >> 4, (int) event.getBlock().getZ() >> 4);
        if (chunkfaction != null) {
            if (pf != null) {
                if (pf.equalsIgnoreCase(chunkfaction)) {
                    Faction fac = factions.FFactory.getFaction(pf);
                    if (fac != null) fac.HandleBreakEvent(event);
                    return;
                }
                event.setCancelled(true);
                event.getPlayer().sendMessage(FactionsMain.NAME + "This area is claimed by " + chunkfaction);
                return;
            }
            event.setCancelled(true);
        }
        Faction fac = factions.FFactory.getFaction(pf);
        if (fac != null) fac.HandleBreakEvent(event);
    }

    @EventHandler
    public void factionBlockPlaceProtect(BlockPlaceEvent event) {
        if (event.getPlayer() != null && event.getPlayer().isOp()) {
            String pf = factions.getPlayerFaction(event.getPlayer().getName());
            Faction fac = factions.FFactory.getFaction(pf);
            if (fac != null) fac.HandlePlaceEvent(event);
            return;
        }
        if (event.isCancelled()) return;
        String pf = factions.getPlayerFaction(event.getPlayer().getName());
        String chunkfaction = factions.GetChunkOwner((int) event.getBlock().getX() >> 4, (int) event.getBlock().getZ() >> 4);
        if (chunkfaction != null) {
            if (pf != null) {
                if (pf.equalsIgnoreCase(chunkfaction)) {
                    Faction fac = factions.FFactory.getFaction(pf);
                    if (fac != null) fac.HandlePlaceEvent(event);
                    return;
                }
                event.setCancelled(true);
                event.getPlayer().sendMessage(FactionsMain.NAME + TextFormat.GRAY + " This area is claimed by " + chunkfaction);
                return;
            }
            event.setCancelled(true);
        }
        Faction fac = factions.FFactory.getFaction(pf);
        if (fac != null) fac.HandlePlaceEvent(event);
    }
}
