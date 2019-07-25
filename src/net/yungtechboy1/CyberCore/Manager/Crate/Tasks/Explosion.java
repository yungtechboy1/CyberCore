package net.yungtechboy1.CyberCore.Manager.Crate.Tasks;

import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.ExplodeParticle;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.level.particle.HugeExplodeSeedParticle;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ExplodePacket;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.scheduler.Task;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 1/18/2017.
 */
public class Explosion extends Task {
    CrateMain main;
    Vector3 v3;
    Level l;
    public Explosion(CrateMain main, Vector3 v3, Level l){
        this.main = main;
        this.v3 = v3;
        this.l = l;
    }

    @Override
    public void onRun(int i) {
//        l.addParticle(new HugeExplodeSeedParticle(v3));
        l.addParticle(new GenericParticle(v3, Particle.TYPE_HUGE_EXPLODE_SEED,0));
        l.addParticle(new ExplodeParticle(v3.add(0,5,0)));
        l.addSound(v3.add(0,-2,0), Sound.RANDOM_EXPLODE);


        ArrayList<Vector3> send = new ArrayList<>();
        send.add(v3);

        ExplodePacket pk = new ExplodePacket();
        pk.x = (float) v3.x;
        pk.y = (float) v3.y;
        pk.z = (float) v3.z;
        pk.radius =  10f;
        pk.records = send.toArray(new Vector3[0]);

        l.addChunkPacket((int) v3.x >> 4, (int) v3.z >> 4, pk);
        l.addParticle(new HugeExplodeSeedParticle(v3));
    }
}
