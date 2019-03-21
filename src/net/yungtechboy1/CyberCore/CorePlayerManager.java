package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import org.apache.logging.log4j.core.Core;

import java.util.HashMap;

/**
 * Created by carlt on 3/21/2019.
 */
public class CorePlayerManager  {

    CyberCoreMain CCM;

    HashMap<String, CorePlayer> List = new HashMap<>();

    public CorePlayerManager(CyberCoreMain CCM) {
        this.CCM = CCM;
    }

    public CorePlayer GetCorePlayer(Player p) {
        if (List.containsKey(p.getName())) {
            return List.get(p.getName());
        }
        return CreatePlayer(p);

    }

    public CorePlayer CreatePlayer(Player p) {
        CorePlayer cp = new CorePlayer(p);
        List.put(p.getName().toLowerCase(), cp);
        return cp;
    }

}
