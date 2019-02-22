package net.yungtechboy1.CyberCore.Manager.Factions.Tasks;

import cn.nukkit.Server;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.ConfigSection;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.Map;

/**
 * Created by carlt_000 on 1/6/2017.
 */
public class FactionRichAsyncAll extends AsyncTask {
    FactionsMain Main;
    Level Lvl;
    Faction fac;
    ConfigSection RichMap = new ConfigSection();

    public FactionRichAsyncAll(FactionsMain main, Level lvl) {
        this.Main = main;
        this.Lvl = lvl;
    }

    @Override
    public void onRun() {
        Level lvl = Lvl;
        lvl = Server.getInstance().getLevelByName("world");
        if (lvl != null) {
            for (Map.Entry<String, Faction> a : Main.FFactory.List.entrySet()) {
                Integer value = 0;
                for (String plot : a.getValue().GetPlots()) {
                    BaseFullChunk chunk = lvl.getChunk(Integer.parseInt(plot.split("\\|")[0]), Integer.parseInt(plot.split("\\|")[1]));
                    if(chunk == null || !chunk.isGenerated())continue;
                    for (int x = 0; x < 16; x++) {
                        for (int y = 0; y < 255; y++) {
                            for (int z = 0; z < 16; z++) {
                                String id = "" + chunk.getBlockId(x, y, z);
                                if (Main.BV.exists(id)) value += (Integer) Main.BV.get(id);
                            }
                        }
                    }
                    RichMap.put(a.getValue().GetName(), value);
                    try {
                        wait(500);
                    }catch (Exception ex){
                    }
                }
            }
        }
    }

    @Override
    public void onCompletion(Server server) {
        FactionsMain main = (FactionsMain) server.getPluginManager().getPlugin("CyberFaction");
        if (main != null) {
            for (Map.Entry<String, Object> a : RichMap.entrySet()) {
                Faction f = main.FFactory.getFaction(a.getKey());
                if (f != null) {
                    f.SetRich((Integer) a.getValue());
                    f.UpdateRichResults();
                }
            }
        }
        server.getLogger().info("-------Rich Updated------");
    }
}
