package net.yungtechboy1.CyberCore.Manager.FT;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.EntityData;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.*;
import cn.nukkit.metadata.MetadataValue;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.potion.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class FloatingTextEntity extends Entity {
    protected String text;
    protected String title;
    protected String finaltext;
    public boolean isVisitorSensitive = false;
    public Long entityId;
    public Position Pos;
    protected boolean invisible;
    protected EntityMetadata metadata;

    private static final EntityMetadata DEFAULT_DATA = new EntityMetadata()
            .putLong(Entity.DATA_FLAGS, (
                    (1L << Entity.DATA_FLAG_CAN_SHOW_NAMETAG) |
                            (1L << Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG) |
                            (1L << Entity.DATA_FLAG_IMMOBILE) |
                            (1L << Entity.DATA_FLAG_SILENT)
//                            (1L << Entity.DATA_FLAG_INVISIBLE)
            ))
            .putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 0)
            .putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0)
            .putFloat(Entity.DATA_SCALE, 0f)
//            .putFloat(Entity.DATA_HEALTH, 100)
            .putLong(Entity.DATA_LEAD_HOLDER_EID, -1)
            .putByte(Entity.DATA_ALWAYS_SHOW_NAMETAG, 1);

    public FloatingTextEntity(Position pos, String text, FullChunk fc, CompoundTag ct) {
        this(pos, text, "", fc, ct);
    }

    public FloatingTextEntity(Position pos, String text, String title, FullChunk fc, CompoundTag ct) {
        super(fc, ct);
        setPosition(pos);
        this.entityId = -1L;
        this.invisible = false;
        this.metadata = new EntityMetadata();
        this.text = text;
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long generateEID() {
        this.entityId = 1095216660480L + ThreadLocalRandom.current().nextLong(0L, 2147483647L);
        return entityId;
    }

    public void GetFinalText(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isInvisible() {
        return this.invisible;
    }

    public void setInvisible() {
        this.setInvisible(true);
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public DataPacket[] encode() {
        ArrayList<DataPacket> packets = new ArrayList<>();

        if (this.entityId == -1) {
            this.entityId = 1095216660480L + ThreadLocalRandom.current().nextLong(0, 0x7fffffffL);
        } else {
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.eid = this.entityId;

            packets.add(pk);
        }
        //title = entityId+"";

        if (!this.invisible) {
            AddPlayerPacket pk = new AddPlayerPacket();
            pk.uuid = UUID.randomUUID();
            pk.username = "";
            pk.entityUniqueId = this.entityId;
            pk.entityRuntimeId = this.entityId;
            pk.x = (float) this.x;
            pk.y = (float) (this.y - 1.62);
            pk.z = (float) this.z;
            pk.speedX = 0;
            pk.speedY = 0;
            pk.speedZ = 0;
            pk.yaw = 0;
            pk.pitch = 0;
            long flags = 0;
            flags |= 1 << Entity.DATA_FLAG_INVISIBLE;
            flags |= 1 << Entity.DATA_FLAG_CAN_SHOW_NAMETAG;
            flags |= 1 << Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG;
            flags |= 1 << Entity.DATA_FLAG_IMMOBILE;
            pk.metadata = new EntityMetadata()
                    .putLong(Entity.DATA_FLAGS, flags)
                    .putString(Entity.DATA_NAMETAG, this.title + (!"".equals(this.text) ? "\n" + this.text : ""))
                    .putLong(Entity.DATA_LEAD_HOLDER_EID, -1);
//                    .putByte(Entity.DATA_LEAD, 0);
            pk.item = Item.get(Item.AIR);
            packets.add(pk);
        }

        return packets.stream().toArray(DataPacket[]::new);
    }

    @Override
    public void spawnTo(Player player) {
        long id = entityId;

        AddEntityPacket pk = new AddEntityPacket();
        pk.entityUniqueId = id;
        pk.entityRuntimeId = id;
        pk.type = 61; //
        pk.x = (float) Pos.x;
        pk.y = (float) Pos.y;
        pk.z = (float) Pos.z;
        pk.speedX = 0;
        pk.speedY = 0;
        pk.speedZ = 0;
        pk.yaw = 0;
        pk.pitch = 0;
        pk.metadata = new EntityMetadata()
                .putLong(Entity.DATA_FLAGS, (
                        (1L << Entity.DATA_FLAG_CAN_SHOW_NAMETAG) |
                                (1L << Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG) |
                                (1L << Entity.DATA_FLAG_IMMOBILE) |
                                (1L << Entity.DATA_FLAG_SILENT)
//                            (1L << Entity.DATA_FLAG_INVISIBLE)
                ))
                .putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 0)
                .putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0)
                .putFloat(Entity.DATA_SCALE, 0f)
//            .putFloat(Entity.DATA_HEALTH, 100)
                .putLong(Entity.DATA_LEAD_HOLDER_EID, -1)
                .putByte(Entity.DATA_ALWAYS_SHOW_NAMETAG, 1).putString(Entity.DATA_NAMETAG, finaltext);
        pk.attributes = new Attribute[]{};

        pk.encode();
        pk.isEncoded = true;

        player.dataPacket(pk);
    }

    @Override
    public int getNetworkId() {
        return -1;
    }

    @Override
    public boolean canCollide() {
        return false;
    }

    @Override
    protected float getGravity() {
        return 0f;
    }

    ///
    @Override
    protected void initEntity() {
        super.initEntity();
    }


    @Override
    public boolean isImmobile() {
        return true;
    }

    @Override
    public void setImmobile() {
        super.setImmobile();
    }


    @Override
    public void saveNBT() {
        super.saveNBT();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public boolean onUpdate(int currentTick) {
        return super.onUpdate(currentTick);
    }

}