package net.yungtechboy1.CyberCore.Manager.Factions.Tasks;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.BossBar.BossBarNotification;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.*;

/**
 * Created by carlt_000 on 4/16/2016.
 */
public class BBNUpdater extends PluginTask<FactionsMain> {
    public BBNUpdater(FactionsMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        onRun2(currentTick);
    }

    public void onRun2(int currentTick) {
       for(Object b : owner.BBN.values()){
           BossBarNotification a = (BossBarNotification)b;
           if(a != null)a.onUpdate(currentTick);
       }
    }
}
