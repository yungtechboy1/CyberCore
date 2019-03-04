package net.yungtechboy1.CyberCore.Manager.BossBar;

import cn.nukkit.Player;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.mob.EntityCreeper;
import cn.nukkit.network.protocol.*;
import cn.nukkit.utils.DummyBossBar;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import sun.java2d.opengl.OGLRenderQueue;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by carlt on 3/3/2019.
 */
public class BossBarGeneric {

    public long EID;
    public Player Owner;
    public String Title;
    public String Msg;
    public int MaxHealth;
    public int CurrentHealth;
    public int Lasttick;
    public boolean Visible = true;
    public BossBarManager BBM;

    public BossBarGeneric(BossBarManager b,Player owner, String title, String msg) {
        GetRandomID();
        BBM = b;
        Owner = owner;
        Title = title;
        Msg = msg;
    }

    public void GetRandomID() {
        EID = 1095216660480L + ThreadLocalRandom.current().nextLong(0, 0x7fffffffL);
    }

    public Player getPlayer() {
        return Owner;
    }

    public String getText() {
        return Title+ TextFormat.RED+"\n\n"+Msg;
    }


    private void createBossEntity() {
        AddEntityPacket pkAdd = new AddEntityPacket();
        pkAdd.type = EntityCreeper.NETWORK_ID;
        pkAdd.entityUniqueId = EID;
        pkAdd.entityRuntimeId = EID;
        pkAdd.x = (float) Owner.x;
        pkAdd.y = (float) -10; // Below the bedrock
        pkAdd.z = (float) Owner.z;
        pkAdd.speedX = 0;
        pkAdd.speedY = 0;
        pkAdd.speedZ = 0;
        pkAdd.metadata = new EntityMetadata()
                // Default Metadata tags
                .putLong(Entity.DATA_FLAGS, 0)
                .putShort(Entity.DATA_AIR, 400)
                .putShort(Entity.DATA_MAX_AIR, 400)
                .putLong(Entity.DATA_LEAD_HOLDER_EID, -1)
                .putString(Entity.DATA_NAMETAG, getText()) // Set the entity name
                .putFloat(Entity.DATA_SCALE, 0); // And make it invisible

        Owner.dataPacket(pkAdd);
    }

    private void sendAttributes() {
        UpdateAttributesPacket pkAttributes = new UpdateAttributesPacket();
        pkAttributes.entityId = EID;
        Attribute attr = Attribute.getAttribute(Attribute.MAX_HEALTH);
        attr.setMaxValue(MaxHealth); // Max value - We need to change the max value first, or else the "setValue" will return a IllegalArgumentException
        attr.setValue(CurrentHealth); // Entity health
        pkAttributes.entries = new Attribute[]{attr};
        Owner.dataPacket(pkAttributes);
    }

    private void sendShowBossBar() {
        BossEventPacket pkBoss = new BossEventPacket();
        pkBoss.bossEid = EID;
        pkBoss.type = BossEventPacket.TYPE_SHOW;
        pkBoss.title = getText();
        pkBoss.healthPercent = CurrentHealth;
        Owner.dataPacket(pkBoss);
    }

    private void sendHideBossBar() {
        BossEventPacket pkBoss = new BossEventPacket();
        pkBoss.bossEid = EID;
        pkBoss.type = BossEventPacket.TYPE_HIDE;
        Owner.dataPacket(pkBoss);
    }

    private void sendSetBossBarTitle() {
        BossEventPacket pkBoss = new BossEventPacket();
        pkBoss.bossEid = EID;
        pkBoss.type = BossEventPacket.TYPE_TITLE;
        pkBoss.title = getText();
        pkBoss.healthPercent = CurrentHealth;
        Owner.dataPacket(pkBoss);
    }

    /**
     * Don't let the entity go too far from the player, or the BossBar will disappear.
     * Update boss entity's position when teleport and each 5s.
     */
    public void updateBossEntityPosition() {
        MoveEntityAbsolutePacket pk = new MoveEntityAbsolutePacket();
        pk.eid = EID;
        pk.x = Owner.x;
        pk.y = -10;
        pk.z = Owner.z;
        pk.headYaw = 0;
        pk.yaw = 0;
        pk.pitch = 0;
        pk.onGround = true;
        Owner.dataPacket(pk);
    }

    public void updateBossEntityNameTag() {
        SetEntityDataPacket pk = new SetEntityDataPacket();
        pk.eid = EID;
        pk.metadata = new EntityMetadata().putString(Entity.DATA_NAMETAG, getText());
        Owner.dataPacket(pk);
    }

    private void removeBossEntity() {
        RemoveEntityPacket pkRemove = new RemoveEntityPacket();
        pkRemove.eid = EID;
        Owner.dataPacket(pkRemove);
    }

    public void create() {
        createBossEntity();
        sendAttributes();
        sendShowBossBar();
    }

    /**
     * Once the player has teleported, resend Show BossBar
     */
    public void reshow() {
        updateBossEntityPosition();
        sendShowBossBar();
    }

    public void kill() {
        sendHideBossBar();
        removeBossEntity();
    }

    public boolean CheckUpdate(int tick){
        int diff = tick - Lasttick;
        int diff2 = CurrentHealth - diff;
        if(diff2 < 0){//Kill
            kill();
            return false;
        }
        CurrentHealth = diff2;
        Lasttick = tick;
        return true;
    }

    public void onUpdate(int tick){
        CheckUpdate(tick);
    }
}
