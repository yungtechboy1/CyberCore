package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CustomEntity.CTFloatingTextParticle2;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.security.acl.Owner;
import java.util.*;

/**
 * Created by carlt_000 on 4/18/2016.
 */
public class UpdateFloatingTextTask  extends PluginTask<CyberCoreMain> {

    public UpdateFloatingTextTask(CyberCoreMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        /*
        for(Long eid: getOwner().FTM.Eids){
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.eid = eid;
            pk.encode();
            Server.broadcastPacket(getOwner().FTM.getServer().getOnlinePlayers().values(),pk);
        }

        getOwner().FTM.Eids = new ArrayList<>();*/

        for(Map.Entry<String,Long> e: getOwner().FTM.EV3.entrySet()) {
            Long eid = e.getValue();
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.eid = eid;
            pk.encode();
            Server.broadcastPacket(getOwner().getServer().getOnlinePlayers().values(),pk);
        }
        getOwner().FTM.EV3 = new HashMap<>();

        for(Long eid: getOwner().FTM.DEids){
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.eid = eid;
            pk.encode();
            Server.broadcastPacket(getOwner().getServer().getOnlinePlayers().values(),pk);
        }

        getOwner().FTM.DEids = new ArrayList<>();

        LinkedHashMap<Vector3, Object> ee = new LinkedHashMap<>();
        for(Map.Entry<String, Object> e: getOwner().FTM.Popups.entrySet()){
            LinkedHashMap v = (LinkedHashMap<String,Object>)e.getValue();
            if((Boolean) v.get("Syntax")){
                Level l = getOwner().getServer().getLevelByName((String)v.get("level"));
                if(l == null){
                    getOwner().getLogger().alert("ERROR Loading Text with key "+e.getKey()+" On "+v.get("Level"));
                    continue;
                }
                String[] split = e.getKey().split("&");
                Vector3 v3 = new Vector3(Double.parseDouble(split[0]),Double.parseDouble(split[1]),Double.parseDouble(split[2]));
                String FT = (String)v.get("text");
                ee.put(v3,new LinkedHashMap<String, Object>(){{
                    put("text",FT);
                    put("level",v.get("level"));
                }});
            }else{
                Level l = getOwner().getServer().getLevelByName((String)v.get("level"));
                if(l == null){
                    getOwner().getLogger().alert("ERROR Loading Text with key "+e.getKey()+" On "+v.get("Level"));
                    continue;
                }
                String[] split = e.getKey().split("&");
                Vector3 v3 = new Vector3(Double.parseDouble(split[0]),Double.parseDouble(split[1]),Double.parseDouble(split[2]));
                String FT = getOwner().FTM.FormatText((String)v.get("text"));
                CTFloatingTextParticle2 ftp = new CTFloatingTextParticle2(v3,FT);
                if(getOwner().FTM.EV3.containsKey(e.getKey())) {
                    ftp.entityId = getOwner().FTM.EV3.get(e.getKey());
                }else{
                    Long feid = ftp.generateEID();
                    getOwner().FTM.EV3.put(e.getKey(),feid);
                }
                l.addParticle(ftp,l.getPlayers().values());
                getOwner().FTM.DEids.add(ftp.entityId);
            }
        }
        for(Map.Entry<Vector3,Object> e: ee.entrySet()){
            for(Map.Entry<UUID,Player> eee: getOwner().getServer().getOnlinePlayers().entrySet()) {
                LinkedHashMap v = (LinkedHashMap<String, Object>) e.getValue();
                Level l = getOwner().getServer().getLevelByName((String) v.get("level"));
                if (l == null) {
                    getOwner().getLogger().alert("ERROR Loading Sending Syntax Text with key " + e.getKey() + " On " + v.get("Level"));
                    continue;
                }
                CTFloatingTextParticle2 ftp = new CTFloatingTextParticle2(e.getKey(), getOwner().FTM.FormatText((String )v.get("text"), eee.getValue()));

                String key = e.getKey().getX()+"&"+e.getKey().getY()+"&"+e.getKey().getZ();

                if(getOwner().FTM.EV3.containsKey(key)) {
                    ftp.entityId = getOwner().FTM.EV3.get(key);
                }else{
                    Long feid = ftp.generateEID();
                    getOwner().FTM.EV3.put(key,feid);
                }

                //Sends To Everyone in Level!
                l.addParticle(ftp, eee.getValue());
                getOwner().FTM.DEids.add(ftp.entityId);
            }
        }
    }
}
