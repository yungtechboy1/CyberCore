package net.yungtechboy1.CyberCore.Manager.BossBar;

import cn.nukkit.Player;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.network.protocol.*;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.HashMap;

/**
 * Created by carlt on 3/3/2019.
 */
public class BossBarManager {

    public HashMap<String, BossBarGeneric> BossList;
    public CyberCoreMain Main;

    public BossBarManager(CyberCoreMain m){
        Main = m;
    }

    public boolean PlayerHasBossBar(Player p){
        return BossList.containsKey(p.getName());
    }

    public BossBarGeneric GetCurrentBossBar(Player p){
        if(!PlayerHasBossBar(p))return null;
        return BossList.getOrDefault(p.getName(),null);
    }

    public void AddBossBar(Player p, BossBarGeneric bbn){
        if(PlayerHasBossBar(p))GetCurrentBossBar(p).kill();
        BossList.put(p.getName(),bbn);
    }

    public static void sendBossBar(Player player, int eid, String title){
        if(title.equals("")){
            return;
        }
        AddEntityPacket packet = new AddEntityPacket();
        packet.entityUniqueId = eid;
        packet.entityRuntimeId = eid;
        packet.type = 52;
        packet.yaw = 0;
        packet.pitch = 0;
        EntityMetadata dataProperties= new EntityMetadata();
        dataProperties.putLong(Entity.DATA_LEAD_HOLDER_EID, -1);
        dataProperties.putLong(Entity.DATA_FLAGS, 0 ^ 1 << Entity.DATA_FLAG_SILENT ^ 1 << Entity.DATA_FLAG_INVISIBLE ^ 1 << Entity.DATA_FLAG_NO_AI);
        dataProperties.putFloat(Entity.DATA_SCALE, 0);
        dataProperties.putString(Entity.DATA_NAMETAG, title);
        dataProperties.putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0);
        dataProperties.putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 0);
        packet.metadata = dataProperties;
        packet.x = (float) player.x;
        packet.y = (float) (player.y - 28);
        packet.z = (float) player.z;
        player.dataPacket(packet);

        BossEventPacket bpk = new BossEventPacket(); // This updates the bar
        bpk.bossEid = eid;
        bpk.type = 0;
        player.dataPacket(bpk);
    }

    /**
     * Sets how many % the bar is full by EID
     */
    public static void sendPercentage(Player player, int eid, double percentage){
        UpdateAttributesPacket upk = new UpdateAttributesPacket(); // Change health of fake wither . bar progress
        if(percentage > 100){
            percentage = 100;
        }
        if(percentage < 0){
            percentage = 0;
        }
        Attribute a = Attribute.getAttribute(4);
        a.setMaxValue(600);
        a.setValue((float) (percentage * 6)); // Ensures that the number is between 0 and 100;
        Attribute[] newEntries = new Attribute[1];
        newEntries[0] = a;
        upk.entries = newEntries;
        upk.entityId = eid;
        player.dataPacket(upk);

        BossEventPacket bpk = new BossEventPacket(); // This updates the bar
        bpk.bossEid = eid;
        bpk.type = 0;
        player.dataPacket(bpk);
    }

    /**
     * Sets the BossBar title by EID
     */
    public static void sendTitle(Player player, int eid, String title){
        SetEntityDataPacket npk = new SetEntityDataPacket(); // change name of fake wither . bar text
        EntityMetadata dataProperties= new EntityMetadata();
        dataProperties.putString(Entity.DATA_NAMETAG, title);
        npk.metadata = dataProperties;
        npk.eid = eid;
        player.dataPacket(npk);

        BossEventPacket bpk = new BossEventPacket(); // This updates the bar
        bpk.bossEid = eid;
        bpk.type = 0;
        player.dataPacket(bpk);
    }

    /**
     * Remove BossBar from players by EID
     */
    public static boolean removeBossBar(Player player, int eid){
        RemoveEntityPacket pk = new RemoveEntityPacket();
        pk.eid = eid;
        player.dataPacket(pk);
        return true;
    }
}
