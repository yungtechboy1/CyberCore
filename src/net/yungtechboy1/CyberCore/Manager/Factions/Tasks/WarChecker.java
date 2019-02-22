package net.yungtechboy1.CyberCore.Manager.Factions.Tasks;

import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.*;

/**
 * Created by carlt_000 on 4/16/2016.
 */
public class WarChecker extends PluginTask<FactionsMain> {
    public WarChecker(FactionsMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        Integer time = (int)(Calendar.getInstance().getTime().getTime()/1000);
        FactionsMain Main = getOwner();
        ArrayList<String> remove = new ArrayList<>();
        for(Map.Entry<String, Object> a: Main.War.entrySet()){
            String key = a.getKey();
            ConfigSection b = (ConfigSection) a.getValue();
            String an = b.getString("attackers");
            String dn = b.getString("defenders");
            Integer start = b.getInt("start");
            Integer stop = b.getInt("stop");

            Faction af = Main.FFactory.getFaction(an);
            Faction df = Main.FFactory.getFaction(dn);

            if(af == null || df == null){
                remove.add(key);
                continue;
            }

            if (time >= stop){
                af.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+" The war is over! Time to head home and regroup!");
                df.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+" The war is over! Time to start rebuilding!");
                //@TODO Display Kills during war
                remove.add(key);
                af.EndWar();
                df.EndWar();
                df.AddCooldown(60*15);//15 Mins
                continue;
            }

            if (!af.AtWar())af.StartWar(key);
            if (!df.AtWar())df.StartWar(key);
        }
        for (String a: remove){
            Main.War.remove(a);
        }
    }
}
