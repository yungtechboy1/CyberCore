package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.Map;
import java.util.UUID;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class CheckOP extends PluginTask<CyberCoreMain> {
    public CheckOP(CyberCoreMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        for(Map.Entry<UUID,Player> e: getOwner().getServer().getOnlinePlayers().entrySet()){
            String rank = getOwner().getPlayerRankCache(e.getValue().toString());
            if (rank != null && !rank.equalsIgnoreCase("op") && e.getValue().isOp()){
                //getOwner().getServer().getNameBans().addBan(e.getValue().getName(),"You should not be OP!");
                e.getValue().setOp(false);
                e.getValue().sendMessage(TextFormat.RED+"You should not be OP!");
            }
        }
    }
    //@TODO
}