package net.yungtechboy1.CyberCore.Manager.Factions.Tasks;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.AsyncTask;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

//@Todo Probablly unneeded!
/**
 * Created by carlt_000 on 1/6/2017.
 */
public class FactionRichAsyncSingle extends AsyncTask {
    FactionsMain Main;
    Level Lvl;
    Faction fac;
    Integer rich = 0;

    public FactionRichAsyncSingle(FactionsMain main, Level lvl, Faction facc) {
        this.Main = main;
        this.Lvl = lvl;
        this.fac = facc;
    }

    @Override
    public void onRun() {

        Level lvl = Lvl;
        Integer value = 0;
        for (String plot : fac.GetPlots()) {
            String key = plot.split("\\|")[0] + "|" + plot.split("\\|")[1];
            int sx = Integer.parseInt(plot.split("\\|")[0]) << 4;
            int sz = Integer.parseInt(plot.split("\\|")[1]) << 4;
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 255; y++) {
                    for (int z = 0; z < 16; z++) {
                        int fx = x + sx;
                        int fz = z + sz;
                        Block b = lvl.getBlock(new Vector3(fx, y, fz));
                        String kkey = "";
                        if (b.getDamage() != 0) {
                            kkey = b.getId() + "|" + b.getDamage();
                        } else {
                            kkey = b.getId() + "";
                        }
                        if (Main.BV.exists(kkey)) value += (Integer) Main.BV.get(kkey);
                    }
                }
            }
            rich = value;
        }
    }

     public void onCompletion(Server server){
         FactionsMain main = (FactionsMain) server.getPluginManager().getPlugin("CyberFaction");
         if (main != null) {
             Faction f = main.FFactory.getFaction(fac.GetName());
             if (f != null) {
                 f.SetRich(rich);
                 f.UpdateRichResults();
                 f.UpdateTopResults();
             }
         }
         server.getLogger().info("COMPLETE"+fac.GetName());
     }

}
