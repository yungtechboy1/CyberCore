package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.PluginTask;

/**
 * Created by carlt_000 on 7/7/2016.
 */
public class ReTPTask extends PluginTask<Main> {

    public Position Pos;
    public Player P;

    public ReTPTask(Main owner, Player p, Position pos) {
        super(owner);
        P = p;
        Pos = pos;
    }

    @Override
    public void onRun(int currentTick) {
        P.teleport(P.getLevel().getSafeSpawn(new Vector3(Pos.x+.5,Pos.y,Pos.z+.5)));
        P.sendMessage("RE-TPed");
    }
}
