package net.yungtechboy1.CyberCore.Manager.Factions.Tasks;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.anvil.Chunk;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.*;

/**
 * Created by carlt_000 on 4/16/2016.
 */
public class FactionPower extends PluginTask<FactionsMain> {
    public FactionPower(FactionsMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        onRun2(0);
        /*
        for (Map.Entry<String, Faction> e :  getOwner().FFactory.List.entrySet()) {
            e.getValue().AddPower(2*e.getValue().GetOnlinePlayers());
            e.getValue().BroadcastMessage(TextFormat.GREEN+"Faction Power Given!");
        }*/
    }

    public void onRun2(int currentTick) {
        Map<String, Integer> factionlist = new HashMap<>();
        for (Map.Entry<UUID, Player> e : this.getOwner().getServer().getOnlinePlayers().entrySet()) {
            if(this.getOwner().killed.containsKey(e.getValue().getName()))continue;
            String fac = this.getOwner().getPlayerFaction(e.getValue().getName());
            if(fac == null)continue;
            if(factionlist.containsKey(fac)){
                factionlist.put(fac,factionlist.get(fac)+1);
            }else{
                factionlist.put(fac,1);
            }
        }
        for (Map.Entry<String, Integer> e : factionlist.entrySet()) {
            this.getOwner().AddFactionPower(e.getKey() , e.getValue());
        }
        getOwner().getLogger().info("FAN!");
        //getOwner().getServer().getScheduler().scheduleDelayedTask(new FactionRichAsyncAll(getOwner(),getOwner().getServer().getLevelByName("world")),10,true);
        getOwner().getServer().getScheduler().scheduleAsyncTask(new FactionRichAsyncAll(getOwner(),getOwner().getServer().getLevelByName("world")));
        getOwner().getLogger().info("FAN!");
        this.getOwner().killed = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }
}
