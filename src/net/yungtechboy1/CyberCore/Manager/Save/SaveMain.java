package net.yungtechboy1.CyberCore.Manager.Save;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by carlt on 2/21/2019.
 */
public class SaveMain {
    private CyberCoreMain Main;
    public Config KD = null;
    public Config CD = null;
    public Config AM = null;
    public Config BV = null;
    public Config Perks = null;
    public Config IP = null;
    public Config MissionCfg = null;
    public boolean Purge = false;
    Config War = null;

    public SaveMain(CyberCoreMain main) {
        Main = main;
    }



    public void Init() {
        War = new Config(new File(Main.getDataFolder(), "War.yml"),Config.YAML, new ConfigSection());
        new File(Main.getDataFolder().toString()).mkdirs();
        KD = new Config(new File(Main.getDataFolder(), "KD.yml"), Config.YAML);
        KD.save();
        CD = new Config(new File(Main.getDataFolder(), "CD.yml"), Config.YAML);
        CD.save();
        AM = new Config(new File(Main.getDataFolder(), "AM.yml"), Config.YAML);
        AM.save();
        Perks = new Config(new File(Main.getDataFolder(), "Perks.yml"), Config.YAML);
        Perks.save();
        IP = new Config(new File(Main.getDataFolder(), "IPs.yml"), Config.YAML);
        IP.save();
        BV = new Config(new File(Main.getDataFolder(), "BlockValue.yml"), Config.YAML, new ConfigSection() {{
            put("1", 1);
            put("2", 1);
            put("3", 1);
            put("4", 1);
            put("5", 1);
        }});
        BV.save();
//        MissionCfg = new Config(new File(Main.getDataFolder(), "Missions.yml"), Config.YAML);
//        MissionCfg.save();
//        for(Map.Entry<String,Object> a: MissionCfg.getAll().entrySet()){
//            Missions.add(new Mission(this,(ConfigSection) a.getValue()));
//        }
    }

    public void onDisable() {

        War.save();
        CD.save();
        AM.save();
        IP.save();
    }

    //Kills
    public Integer GetKills(Player player) {
        return GetKills(player.getName());
    }

    public Integer GetKills(String player) {
        if(KD.exists(player)){
            LinkedHashMap v = (LinkedHashMap<String, Object>) KD.get(player.toLowerCase());
            return (Integer) v.get("Kills");
        }
        return 0;
    }

    public void AddKill(String player) {
        AddKill(player,1);
    }

    public void AddKill(String player, Integer amount) {
        if(KD.exists(player)){
            LinkedHashMap v = (LinkedHashMap<String,Object>) KD.get(player);
            Integer a = (int)v.get("Kills");
            KD.set(player+".Kills",a+amount);
        }else{
            LinkedHashMap settings = new LinkedHashMap(){{
                put("Kills",amount);
                put("Deaths",0);
            }};
            KD.set(player,settings);
        }
    }

    //Get Deaths
    public Integer GetDeaths(String player) {
        if(KD.exists(player)){
            LinkedHashMap v = (LinkedHashMap<String, Object>) KD.get(player);
            return (Integer) v.get("Deaths");
        }
        return 0;
    }

    public void AddDeath(String player) {
        AddDeath(player,1);
    }

    public void AddDeath(String player, Integer amount) {
        if(KD.exists(player)){
            LinkedHashMap v = (LinkedHashMap<String,Object>) KD.get(player);
            Integer a = (int)v.get("Deaths");
            KD.set(player+".Deaths",a+amount);
        }else{
            LinkedHashMap settings = new LinkedHashMap(){{
                put("Kills",0);
                put("Deaths",amount);
            }};
            KD.set(player,settings);
        }
    }

    //KDR
    public float GetKDR(String player){
        Float k = (float)GetKills(player);
        Float d = (float)GetDeaths(player);
        return k/d;
    }
}
