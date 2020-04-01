package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class TPToHome extends PluginTask<CyberCoreMain> {
    public CorePlayer p;
    public Vector3 home;

    public TPToHome(CyberCoreMain owner, CorePlayer p, Vector3 home) {
        super(owner);
        this.p = p;
        this.home = home;
        Effect e1 = Effect.getEffect(Effect.CONFUSION);//9
        Effect e2 = Effect.getEffect(Effect.SLOWNESS);//2
        e1.setAmplifier(2);
        e2.setAmplifier(2);
        e1.setDuration(20*600);
        e2.setDuration(20*600);
        p.addEffect(e1);
        p.addEffect(e2);
    }

    @Override
    public void onRun(int i) {
        Effect e1 = p.getEffect(9);
        Effect e2 = p.getEffect(2);
        e1.setDuration(1);
        e2.setDuration(1);
        p.addEffect(e1);
        p.addEffect(e2);
        p.teleport(home, PlayerTeleportEvent.TeleportCause.NETHER_PORTAL);
    }
}
