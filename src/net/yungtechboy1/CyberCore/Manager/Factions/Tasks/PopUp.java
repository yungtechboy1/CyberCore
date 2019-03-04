package net.yungtechboy1.CyberCore.Manager.Factions.Tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.CustomFloatingTextParticle;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by carlt_000 on 8/16/2016.
 */
public class PopUp extends PluginTask<CyberCoreMain>{
    public int Age = 0;
    public String Text = "";
    public long Eid = 0;
    public Player Target = null;
    public Level Level = null;

    public PopUp(CyberCoreMain main,Player target,Long eid,Level level){
        this(main,target,eid,level,0);
    }

    public PopUp(CyberCoreMain main,Player target,Long eid,Level level,int age)
    {
        super(main);
        Eid = eid;
        Age = age;
        Level = level;
        Target = target;
    }

    @Override
    public void onRun(int currentTick) {

        return;
        //TODO
//        CustomFloatingTextParticle e = getOwner().popups.getOrDefault(Eid,null);
//        if(e == null)return;
//        /*pos = new Vector3(e.x,e.y+.25,e.z);
//            //level.addEntityMovement(x >> 4, z >> 4, getId(), x, y + getEyeHeight(), z, yaw, pitch, yaw);
//        pk = new MoveEntityPacket();
//        pk.entities = [eid,pos.getX(),pos.getY(),pos.getZ(),0,0,0];
//        pk.encode();*/
//        RemoveEntityPacket pk1 = new RemoveEntityPacket();
//        pk1.eid = Eid;
//        //Server::broadcastPacket([target],pk0);
//        Target.dataPacket(pk1);
//        //target.dataPacket(pk0);
//        PopUp nnew = new PopUp(getOwner(),Target,Eid,Level,Age);
//        nnew.Age++;
//        ArrayList<Player> ps = new ArrayList<Player>(){{
//            add(Target);
//        }};
//        if(nnew.Age > 3*4){
//            getOwner().popups.remove(Eid);
//            RemoveEntityPacket pk0 = new RemoveEntityPacket();
//            pk0.eid = Eid;
//            pk0.encode();
//            Server.broadcastPacket(ps,pk0);
//            return;
//        }
//        CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(e.x, e.y + .25, e.z), e.text);
//        ft.entityId = Eid;
//        ft.encode();
//        getOwner().popups.put(Eid,ft);
//        Level.addParticle(ft,ps);
//        //target.dataPacket(pk);
//        owner.getServer().getScheduler().scheduleDelayedTask(nnew,7,true);
    }

}
