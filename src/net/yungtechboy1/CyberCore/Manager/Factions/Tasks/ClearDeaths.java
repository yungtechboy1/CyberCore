package net.yungtechboy1.CyberCore.Manager.Factions.Tasks;

import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by carlt_000 on 4/16/2016.
 */
public class ClearDeaths extends PluginTask<FactionsMain> {
    public ClearDeaths(FactionsMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        this.getOwner().death = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }
}
