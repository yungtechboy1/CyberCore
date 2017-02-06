package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Class_LumberJack;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.Map;
import java.util.UUID;

/**
 * Created by carlt_000 on 2/6/2017.
 */
public class LumberJackTreeCheckerTask extends PluginTask<CyberCoreMain> {
    public LumberJackTreeCheckerTask(CyberCoreMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        for(Map.Entry<String,Object> e: getOwner().ClassFactory.LumberJackTreePlants.getAll().entrySet()){
            String pn = e.getKey();
            Player p = getOwner().getServer().getPlayerExact(pn);
            if(p == null)continue;
            if(e.getValue() instanceof ConfigSection){
                ConfigSection f = (ConfigSection) ((ConfigSection) e.getValue()).clone();
                for(Map.Entry<String,Object> g: f.getAll().entrySet()){
                    String[] pos = g.getKey().split("\\|");
                    if(pos.length == 3) {
                        Vector3 v3 = new Vector3(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]),Integer.parseInt(pos[2]));
                        Block at = getOwner().getServer().getDefaultLevel().getBlock(v3);
                        if(at.getId() == Block.WOOD || at.getId() == Block.WOOD2){
                            ((ConfigSection) e.getValue()).remove(g.getKey());
                            BaseClass bc = getOwner().ClassFactory.GetClass(p);
                            if(bc instanceof Class_LumberJack){
                                bc.addXP(50);
                            }
                        }
                    }
                }
            }
        }
    }
}