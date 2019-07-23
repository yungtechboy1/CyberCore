package net.yungtechboy1.CyberCore.Manager.Crate.Tasks;

import Crate.MainClass;
import cn.nukkit.level.Level;
import cn.nukkit.level.particle.HugeExplodeSeedParticle;
import cn.nukkit.level.sound.ExplodeSound;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.PluginTask;

/**
 * Created by carlt_000 on 1/18/2017.
 */
public class Explosion extends PluginTask<MainClass> {
    MainClass main;
    Vector3 v3;
    Level l;
    public Explosion(MainClass main, Vector3 v3, Level l){
        super(main);
        this.main = main;
        this.v3 = v3;
        this.l = l;
    }

    @Override
    public void onRun(int i) {
        l.addParticle(new HugeExplodeSeedParticle(v3));
        l.addSound(new ExplodeSound(v3.add(0,-2,0)));
    }
}
