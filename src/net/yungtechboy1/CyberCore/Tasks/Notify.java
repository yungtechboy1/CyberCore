package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/4/2017.
 */
public class Notify extends PluginTask<CyberCoreMain> {
    public Notify(CyberCoreMain main){
        super(main);
    }

    @Override
    public void onRun(int i) {
        for(Player p: getOwner().getServer().getOnlinePlayers().values()){
        }
    }
}