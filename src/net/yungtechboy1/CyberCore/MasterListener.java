package net.yungtechboy1.CyberCore;

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
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.CustomFloatingTextParticle;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Listener.ChatEvent;
import net.yungtechboy1.CyberCore.Manager.Factions.Listener.JoinEvent;
import net.yungtechboy1.CyberCore.Manager.Factions.Listener.PreLoginEvent;
import net.yungtechboy1.CyberCore.Manager.Factions.Tasks.PopUp;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by carlt on 3/7/2019.
 */
public class MasterListener implements Listener {
    CyberCoreMain Main;

    public MasterListener(CyberCoreMain mm) {
        Main = mm;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void FactionChatEvent(PlayerChatEvent event){
        ChatEvent ce = new ChatEvent(Main.FM,event);
        event = ce.Event;
    }

    @EventHandler
    public void FactionPlayerDeath(PlayerDeathEvent event){
        if (event == null) return;
        String playern = event.getEntity().getName();
        EntityDamageEvent cause = event.getEntity().getLastDamageCause();
        event.getEntity().setExperience(0);
        Main.KDM.AddDeath(playern);
        if (cause != null && cause.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
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
                    Faction kf = Main.FM.FFactory.getPlayerFaction((Player) event.getEntity());
                    if (kf != null) {
                        kf.HandleKillEvent(event);
                        kf.TakePower(2);
                    }
                   Main.KDM.AddKill(killername);
                    if ( Main.KDM.GetKills(killername) == 5) {
                        Main.getServer().broadcastMessage(TextFormat.GREEN + killername + " is on a 5 KillStreak!");
                        //if(kf != null)this.plugin.AddFactionPower(kf, 5);
                    }
                    if (Main.KDM.GetKills(killername) == 8) {
                        Main.getServer().broadcastMessage(TextFormat.AQUA + killername + " is on a 8 KillStreak!");
                        //if(kf != null)this.plugin.AddFactionPower(kf, 8);
                    }
                    if (Main.KDM.GetKills(killername) == 10) {
                        Main.getServer().broadcastMessage(TextFormat.LIGHT_PURPLE + killername + " is on a 10 KillStreak!");
                        //if(kf != null)this.plugin.AddFactionPower(kf, 10);
                    }
                    if (Main.KDM.GetKills(killername) > 10) {
                        Integer kills = Main.KDM.GetKills(killername);
                        Main.getServer().broadcastMessage(TextFormat.LIGHT_PURPLE + killername + " is on a " + kills + " KillStreak!");
                        //if(kf != null)this.plugin.AddFactionPower(kf, kills*2);
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void FationsJoinEvent(PlayerJoinEvent event) {
        //this.plugin.uuid[event.getPlayer().getName()][event.getPlayer().getClientId()] = date(DATE_COOKIE);
        String player = event.getPlayer().getName();

        String fn = Main.FM.getPlayerFaction(event.getPlayer().getName());
        Faction f = Main.FM.FFactory.getFaction(fn);
        if (f != null) {
            Main.FM.FFactory.List.put(fn.toLowerCase(), f);
            Main.FM.FFactory.FacList.put(player.toLowerCase(), fn);
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

        //TODO Add 30 Sec Cooldown

        //TODO Create CheckClaim Function
        //Checks factions claim
        String chunkfaction = Main.FM.GetChunkOwner((int) ev.getPlayer().getX() >> 4, (int) ev.getPlayer().getZ() >> 4);
        if (chunkfaction != null) {
            String pf = Main.FM.getPlayerFaction(ev.getPlayer().getName());
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

        String chunkfaction = Main.FM.GetChunkOwner((int) ev.getPlayer().getX() >> 4, (int) ev.getPlayer().getZ() >> 4);
        if (chunkfaction != null) {
            String pf = Main.FM.getPlayerFaction(ev.getPlayer().getName());
            if (pf != null) {
                if (pf.equalsIgnoreCase(chunkfaction)) return;
                ev.getPlayer().sendMessage(FactionsMain.NAME + TextFormat.GRAY + "This area is claimed by " + chunkfaction);
            }
            ev.setCancelled(true);
        }
    }


    //IPChecker
    //IPChecker
    //IPChecker
    @EventHandler()
    public void PreLogin(PlayerPreLoginEvent ev){
        new PreLoginEvent(Main.FM,ev);
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

    //LOG COMMANDS IN CONSOLE
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
}
