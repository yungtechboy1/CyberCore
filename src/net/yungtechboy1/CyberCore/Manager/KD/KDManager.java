package net.yungtechboy1.CyberCore.Manager.KD;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.utils.ConfigSection;
import javafx.print.PageLayout;

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

    public void LoadData() {

    }

    public void SaveData() {

    }

    public void AddDeath(Player p) {
        AddDeath(p.getName());
    }
    public void AddDeath(String p) {
        String key = p + ".Deaths";
        if (!DeathData.exists(key)) {
            DeathData.set(key,1);
        }else{
            DeathData.set(key,(int)DeathData.get(key) + 1);
        }
    }

    public void AddKill(Player p) {
        AddDeath(p.getName());
    }
    public void AddKill(String p) {
        String key = p + ".Deaths";
        if (!KillData.exists(key)) {
            KillData.set(key,1);
        }else{
            KillData.set(key,(int)KillData.get(key) + 1);
        }
    }

    public void RecordKD(Player killed, Player killer) {
       AddKill(killer);
       AddDeath(killed);
       //TODO remove effects?
    }


}
