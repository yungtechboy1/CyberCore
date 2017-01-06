package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.PluginTask;

/**
 * Created by carlt_000 on 7/7/2016.
 */
public class Restart extends PluginTask<Main> {

    public Position Pos;
    public Player P;

    public Restart(Main owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        getOwner().getServer().shutdown();
    }
}
