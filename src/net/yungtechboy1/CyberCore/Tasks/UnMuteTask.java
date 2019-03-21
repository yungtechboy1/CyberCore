package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class UnMuteTask extends PluginTask<CyberCoreMain> {
    public UnMuteTask(CyberCoreMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        for (Map.Entry<String, Object> e :this.getOwner().MuteConfig.getAll().entrySet()){
            Integer time = (int)(Calendar.getInstance().getTime().getTime()/1000);
            Integer v = (Integer) e.getValue();
            if(v <= time){
                Player t = this.getOwner().getServer().getPlayerExact(e.getKey());
                if(t != null){
                    t.sendMessage(TextFormat.GREEN+"You are now un-muted!");
                    this.getOwner().removeMute(t);
                }
            }
        }
    }
}