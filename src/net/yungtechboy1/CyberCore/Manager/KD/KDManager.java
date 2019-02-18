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
    public ConfigSection Data;

    public void LoadData() {

    }

    public void SaveData() {

    }

    public void AddDeath(Player p) {
        String key = p + ".Deaths";
        if (!Data.exists(key)) {
            Data.set(key,1);
        }else{
            Data.set(key,(int)Data.get(key) + 1);
        }
    }

    public void RecordKD(Player killed, Player killer) {
        DataPacket[killed + ".Deaths"] =
    }


}
