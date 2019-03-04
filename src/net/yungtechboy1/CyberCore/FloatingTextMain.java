package net.yungtechboy1.CyberCore;


import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextEntity;
import net.yungtechboy1.CyberCore.Tasks.UpdateFloatingTextTask;

import java.io.File;
import java.util.*;

/**
 * Created by carlt_000 on 1/30/2017.
 */
public class FloatingTextMain implements Listener {
    CyberCoreMain CCM;

    public Config MainConfig;
    public Config KDConfig;
    public List<String> PlayerMuted = new ArrayList<>();
    public Boolean MuteChat = false;
    public Map<String, Integer> Spam = new HashMap<>();
    public Map<String, Object> Popups = new HashMap<>();
    public Map<Vector3, Integer> TextKey = new HashMap<>();
    public ArrayList<UpdateFloatingTextTask> TaskArray = new ArrayList<>();
    public Map<String,Long> EV3 = new HashMap<>();
    public ArrayList<Long> Eids = new ArrayList<>();
    public ArrayList<Long> DEids = new ArrayList<>();
    public Boolean FactionsLoaded = false;
    public FactionsMain Factions;

    public FloatingTextMain(CyberCoreMain main){
        CCM = main;
        new File(CCM.getDataFolder().toString()).mkdirs();
        MainConfig = new Config(new File(CCM.getDataFolder(), "ftconfig.yml"), Config.YAML);
        KDConfig = new Config(new File(CCM.getDataFolder(), "kd1.yml"), Config.YAML);
        CCM.getLogger().info(TextFormat.GREEN+"Floating Text Enabled!");
        MainConfig.save();
        CCM.getServer().getPluginManager().registerEvents(this,CCM);
        //P Factions = (P) CCM.getServer().getPluginManager().getPlugin("FactionsV3");
        if(CCM.FM == null){
            CCM.getLogger().warning("Factions Not Found!");
        }else{
            CCM.getLogger().info(TextFormat.GREEN+"Factions Found!");
            FactionsLoaded = true;
        }
    }

    public void onDisable() {
        MainConfig.save();
        KDConfig.save();
    }


    public String FormatText(String text) {
        return FormatText(text,null);
    }

    public String FormatText(String text, Player player){
        text = text.replace("{online-players}",""+CCM.getServer().getOnlinePlayers().size())
                .replace("{ticks}",CCM.getServer().getTicksPerSecondAverage()+"")
                .replace("`","\n")
                .replace("{&}",TextFormat.ESCAPE+"");
        if(player != null)text = text.replace("{name}",player.getName());
        if(player != null){
            //Faction

            String pf = "No Faction";
            if(CCM.FM != null) {
                pf = CCM.FM.getPlayerFaction(player);
                if (pf == null) pf = "No Faction";
            }
            //Kills
            Double kills = 0d;//Factions.GetKills(player.getName());
            if(KDConfig.exists(player.getName().toLowerCase())){
                kills = Double.parseDouble(((LinkedHashMap)KDConfig.get(player.getName().toLowerCase())).get("kills")+"");
            }
            //Deaths
            Double deaths = 0d;//Factions.GetDeaths(player.getName());
            if(KDConfig.exists(player.getName().toLowerCase())){
                deaths = Double.parseDouble(((LinkedHashMap)KDConfig.get(player.getName().toLowerCase())).get("deaths")+"");
            }
            //KDR
            Double kdr = kills/deaths;//Factions.GetKDR(player.getName());
            CyberCoreMain CC = (CyberCoreMain) CCM.getServer().getPluginManager().getPlugin("CyberCore");
            String rank = "Guest|";
            if(CC != null){
                rank = CC.getPlayerRankCache(player.getName());
                if(rank == null)rank = "Guest";
            }
            String tps = ""+CCM.getServer().getTicksPerSecond();
            String players = ""+CCM.getServer().getOnlinePlayers().size();
            String max = ""+CCM.getServer().getMaxPlayers();
            String money = "0";
            ArchEconMain AA = (ArchEconMain) CCM.getServer().getPluginManager().getPlugin("ArchEcon");
            if(AA != null){
                money = ""+AA.GetMoney(player.getName());
            }

            text = text
                    .replace("{faction}",pf)
                    .replace("{kills}",kills+"")
                    .replace("{deaths}",deaths+"")
                    .replace("{kdr}",kdr+"")
                    .replace("{rank}",rank)
                    .replace("{tps}",tps)
                    .replace("{players}",players)
                    .replace("{max}",max)
                    .replace("{money}",money)
            ;
        }else{
            text = text
                    .replace("{faction}","No Faction")
                    .replace("{kills}","N/A")
                    .replace("{deaths}","N/A")
                    .replace("{kdr}","N/A");
        }
        return text;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void joinEvent(PlayerJoinEvent event) {
        LinkedHashMap setting = new LinkedHashMap<String, Object>(){{
            put("kills", 0d);
            put("deaths", 0d);
        }};
        if(!KDConfig.exists(event.getPlayer().getName().toLowerCase()))KDConfig.set(event.getPlayer().getName().toLowerCase(),setting);
        for(Map.Entry<String,Object> e : MainConfig.getAll().entrySet()){
            if(!(e.getValue() instanceof LinkedHashMap))continue;
            LinkedHashMap v = (LinkedHashMap<String,Object>)e.getValue();

            Level l = CCM.getServer().getLevelByName((String)v.get("level"));
            if(l == null){
                CCM.getLogger().alert("ERROR3 Loading Text with key "+e.getKey()+" On "+(String) v.get("level") + " VS " + v.get("text"));
                continue;
            }
            String[] split = e.getKey().split("&");
            Vector3 v3 = new Vector3(Double.parseDouble(split[0]),Double.parseDouble(split[1]),Double.parseDouble(split[2]));
            String FT = FormatText((String)v.get("text"),event.getPlayer());
            FloatingTextEntity ftp = new FloatingTextEntity(v3,FT);
            Player[] p = new Player[]{event.getPlayer()};
            l.addParticle(ftp,p);
            Eids.add(ftp.entityId);
            if((Boolean) v.get("Dynamic") || (Boolean) v.get("Syntax"))DEids.add(ftp.entityId);
            //l.addParticle(ftp,l.getPlayers().values());

        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerDeath(PlayerDeathEvent event){
        LinkedHashMap setting = new LinkedHashMap<String, Object>(){{
            put("kills", 0d);
            put("deaths", 0d);
        }};
        Player Killed = event.getEntity();
        if(!KDConfig.exists(Killed.getName().toLowerCase()))KDConfig.set(Killed.getName().toLowerCase(),setting);
        if(KDConfig.exists(Killed.getName().toLowerCase())){
            Double deaths1 = (Double)((LinkedHashMap)KDConfig.get(Killed.getName().toLowerCase())).get("deaths") + 1d;
            Double kills1 = (Double)((LinkedHashMap)KDConfig.get(Killed.getName().toLowerCase())).get("kills");
            LinkedHashMap setting1 = new LinkedHashMap<String, Object>(){{
                put("kills", kills1);
                put("deaths", deaths1);
            }};
            KDConfig.set(Killed.getName().toLowerCase(),setting1);
        }
        if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
            Entity Killer =  ((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager();
            if(Killer instanceof Player){
                if(!KDConfig.exists(Killer.getName().toLowerCase()))KDConfig.set(Killer.getName().toLowerCase(),setting);
                if(KDConfig.exists(Killer.getName().toLowerCase())){
                    Double deaths2 = (Double)((LinkedHashMap)KDConfig.get(Killer.getName().toLowerCase())).get("deaths") ;
                    Double kills2 = (Double)((LinkedHashMap)KDConfig.get(Killer.getName().toLowerCase())).get("kills")+ 1d;
                    LinkedHashMap setting2 = new LinkedHashMap<String, Object>(){{
                        put("kills", kills2);
                        put("deaths", deaths2);
                    }};
                    KDConfig.set(Killer.getName().toLowerCase(),setting2);
                }
            }
        }
    }

    public void ReloadDynamicText(){
        //if(CCM.getServer().getQueryInformation().getPlayerCount() == 0)return;
        String text;
        LinkedHashMap<Vector3, Object> ee = new LinkedHashMap<>();
        if(MainConfig.getAll().size() > 0) {
            for (Map.Entry<String, Object> e : MainConfig.getAll().entrySet()) {
                if(e.getValue() instanceof String)return;
                LinkedHashMap v = (LinkedHashMap<String, Object>) e.getValue();
                if ((Boolean) v.get("Dynamic")) {
                    Level l = CCM.getServer().getLevelByName((String) v.get("level"));
                    if (l == null) {
                        CCM.getLogger().alert("ERROR1 Loading Text with key " + e.getKey() + " On " + (String) v.get("level") + " VS " + v.get("text"));
                        continue;
                    }
                    String[] split = e.getKey().split("&");
                    Vector3 v3 = new Vector3(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
                    String FT = FormatText((String) v.get("text"));

                    FloatingTextEntity ftp = new FloatingTextEntity(v3, FT);
                    if (EV3.containsKey(e.getKey())) {
                        ftp.entityId = EV3.get(e.getKey());
                    } else {
                        Long feid = ftp.generateEID();
                        EV3.put(e.getKey(), feid);
                    }
                    l.addParticle(ftp);
                    //Eids.add((int)ftp.entityId);
                    Popups.put(e.getKey(), new LinkedHashMap<String, Object>() {{
                        put("text", (String) v.get("text"));
                        put("eid", ftp.entityId);
                        put("Dynamic", v.get("Dynamic"));
                        put("Syntax", v.get("Syntax"));
                        put("level", v.get("level"));
                    }});
                } else if ((Boolean) v.get("Syntax")) {
                    Level l = CCM.getServer().getLevelByName((String) v.get("level"));
                    if (l == null) {
                        CCM.getLogger().alert("ERROR2 Loading Text with key " + e.getKey() + " On " + (String) v.get("level") + " VS " + v.get("text"));
                        continue;
                    }
                    String[] split = e.getKey().split("&");
                    Vector3 v3 = new Vector3(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
                    String FT = ((String) v.get("text"));
                    ee.put(v3, new LinkedHashMap<String, Object>() {{
                        put("text", FT.replace("{&}", TextFormat.ESCAPE + ""));
                        put("level", v.get("level"));

                    }});
                } else {
                    Level l = CCM.getServer().getLevelByName((String) v.get("level"));
                    if (l == null) {
                        CCM.getLogger().alert("ERROR3 Loading Text with key " + e.getKey() + " On " + (String) v.get("level") + " VS " + v.get("text"));
                        continue;
                    }
                    String[] split = e.getKey().split("&");
                    Vector3 v3 = new Vector3(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
                    String FT = ((String) v.get("text"))
                            .replace("`", "\n");
                    FloatingTextEntity ftp = new FloatingTextEntity(v3, FT);
                    l.addParticle(ftp);
                    //l.addParticle(ftp,l.getPlayers().values());
                    Eids.add(ftp.entityId);
                }
            }
            for (Map.Entry<Vector3, Object> e : ee.entrySet()) {
                for (Map.Entry<UUID, Player> eee : CCM.getServer().getOnlinePlayers().entrySet()) {
                    LinkedHashMap v = (LinkedHashMap<String, Object>) e.getValue();
                    Level l = CCM.getServer().getLevelByName((String) v.get("level"));
                    if (l == null) {
                        CCM.getLogger().alert("ERROR4 Loading Text with key " + e.getKey() + " On " + (String) v.get("level") + " VS " + v.get("text"));
                        continue;
                    }
                    FloatingTextEntity ftp = new FloatingTextEntity(e.getKey(), FormatText((String) v.get("text"), eee.getValue()));

                    String key = e.getKey().getX() + "&" + e.getKey().getY() + "&" + e.getKey().getZ();

                    if (EV3.containsKey(key)) {
                        ftp.entityId = EV3.get(key);
                    } else {
                        Long feid = ftp.generateEID();
                        EV3.put(key, feid);
                    }
                    //Sends To Everyone in Level!
                    l.addParticle(ftp, eee.getValue());
                    //Eids.add((int)ftp.entityId);
                }
            }
        }
    }


    public void LevelChange(EntityLevelChangeEvent event){

    }

    public void AddFloatingText(Player player){
        Vector3 v3 = player.clone();
        v3.y = v3.getY()+1;
        String v3name = (int)v3.getX()+"&"+(int)v3.getY()+"&"+(int)v3.getZ();
        LinkedHashMap setting = new LinkedHashMap<String, Object>(){{
            put("text", "Testing Text");
            put("Dynamic", false);
            put("Syntax", false);
            put("level", player.getLevel().getFolderName());

        }};
        FloatingTextEntity ftp = new FloatingTextEntity(v3, "Testing Text","TTEESSTT");
        //player.getLevel().addParticle(ftp,  player.getLevel().getPlayers().values());
        player.getLevel().addParticle(ftp);
        Eids.add(ftp.entityId);
        MainConfig.set(v3name,setting);
        //ReloadDynamicText();
    }

}
