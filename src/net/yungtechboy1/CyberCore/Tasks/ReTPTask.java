package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 7/7/2016.
 */
public class ReTPTask extends PluginTask<CyberCoreMain> {

    public Position Pos;
    public Player P;

    public ReTPTask(CyberCoreMain owner, Player p, Position pos) {
        super(owner);
        P = p;
        Pos = pos;
    }

    @Override
    public void onRun(int currentTick) {
        P.teleport(P.getLevel().getSafeSpawn(new Vector3(Pos.x+.5,Pos.y,Pos.z+.5)));
    }
}
