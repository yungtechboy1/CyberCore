package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/7/2017.
 */
public class TeleportEvent extends PluginTask<CyberCoreMain> {
    CorePlayer P;
    Vector3 V3;
    public TeleportEvent(CyberCoreMain main, CorePlayer p, Vector3 v3){
        super(main);
        P = p;
        V3 = v3;
        Effect e1 = Effect.getEffect(9);
        Effect e2 = Effect.getEffect(2);
        e1.setAmplifier(2);
        e2.setAmplifier(2);
        e1.setDuration(20*600);
        e2.setDuration(20*600);
        p.addEffect(e1);
        p.addEffect(e2);
    }

    @Override
    public void onRun(int i) {
        Effect e1 = P.getEffect(9);
        Effect e2 = P.getEffect(2);
        e1.setDuration(1);
        e2.setDuration(1);
        P.addEffect(e1);
        P.addEffect(e2);

        P.teleport(V3);
    }
}
