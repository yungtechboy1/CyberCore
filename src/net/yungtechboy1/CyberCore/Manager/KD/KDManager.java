package net.yungtechboy1.CyberCore.Manager.KD;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;

/**
 * Created by carlt on 2/17/2019.
 */
public class KDManager {
    /**
     * Name:
     * Kills:0
     * Deaths:0
     */
    public ConfigSection KillData;
    public ConfigSection DeathData;
    CyberCoreMain Main;

    public KDManager(CyberCoreMain cyberCoreMain) {
        Main = cyberCoreMain;
    }

    public void LoadData() {

    }

    public void SaveData() {
        Main.Save.KD.save();
    }


    public void AddDeath(Player p) {
        AddDeath(p.getName());
    }

    public void AddDeath(String p) {
        Main.Save.GetDeaths(p);
    }

    public void AddKill(Player p) {
        AddDeath(p.getName());
    }

    public void AddKill(String p) {
        Main.Save.AddKill(p);
    }

    public int GetKills(Player p) {
        return GetKills(p.getName());
    }

    public int GetKills(String n) {
        return Main.Save.GetKills(n);
    }

    public int GetDeaths(Player p) {
        return GetDeaths(p.getName());
    }

    public int GetDeaths(String n) {
        return Main.Save.GetDeaths(n);
    }

    public double GetKDR(Player p) {
        return GetKDR(p.getName());
    }

    public double GetKDR(String n) {
        double f = GetKills(n) / GetDeaths(n);
        return f;
    }

    public void RecordKD(Player killed, Player killer) {
        AddKill(killer);
        AddDeath(killed);
        //TODO remove effects?
    }


}
