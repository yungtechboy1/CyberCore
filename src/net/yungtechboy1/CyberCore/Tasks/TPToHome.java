package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class TPToHome extends PluginTask<CyberCoreMain> {
    Player P;
    String Home;
    public TPToHome(CyberCoreMain owner, Player p, String home) {
        super(owner);
        P = p;
        Home = home;
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

        getOwner().HomeFactory.TPPlayerHome(P,Home);
    }
}
