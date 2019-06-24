package net.yungtechboy1.CyberCore.entities.block;

import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntitySpawnable;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.ShortTag;
import net.yungtechboy1.CyberCore.Factory.CustomFactory;
import net.yungtechboy1.CyberCore.MobAI.MobPlugin;
import net.yungtechboy1.CyberCore.Tasks.SpawnerCalculationsAsync;
import net.yungtechboy1.CyberCore.entities.BaseEntity;
import net.yungtechboy1.CyberCore.entities.EntityStackable;
import net.yungtechboy1.CyberCore.entities.animal.walking.Pig;
import net.yungtechboy1.CyberCore.entities.utils.Utils;

import javax.swing.text.html.StyleSheet;
import java.util.ArrayList;

public class BlockEntitySpawner extends BlockEntitySpawnable {

    public int spawnRange;
    public int maxNearbyEntities;
    public int requiredPlayerRange = 32;
    public boolean wait = false;
    public int minSpawnDelay;
    public int maxSpawnDelay;
    private int entityId = -1;
    private int lvl;
    private int delay = 0;

    public BlockEntitySpawner(FullChunk chunk, CompoundTag nbt, int eid, int llevel, int spawnRange, int minSpawnDelay, int maxSpawnDelay, int requiredPlayerRange) {

        super(chunk, nbt);
        this.entityId = eid;
        this.lvl = llevel;
        this.namedTag.putInt("Type", eid);
        this.namedTag.putInt("Level", lvl);
        this.namedTag.putShort("SpawnRange", spawnRange);
        this.namedTag.putShort("MinSpawnDelay", minSpawnDelay);//2 Min
        this.namedTag.putShort("MaxSpawnDelay", maxSpawnDelay);//6 Mins
        this.namedTag.putShort("RequiredPlayerRange", requiredPlayerRange);
        this.requiredPlayerRange = 32;

    }

    public BlockEntitySpawner(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
//
//        System.out.println("STARRRRRRRRRRRRRRRRRR! " + nbt.toString());
        if (this.namedTag.contains("Type")) {
            this.entityId = this.namedTag.getInt("Type");
        }

        if (!this.namedTag.contains("SpawnRange") || !(this.namedTag.get("SpawnRange") instanceof ShortTag)) {
            this.namedTag.putShort("SpawnRange", 8);
        }

        if (!this.namedTag.contains("MinSpawnDelay") || !(this.namedTag.get("MinSpawnDelay") instanceof ShortTag)) {
            this.namedTag.putShort("MinSpawnDelay", 2400);//2 Min
        }

        if (!this.namedTag.contains("MaxSpawnDelay") || !(this.namedTag.get("MaxSpawnDelay") instanceof ShortTag)) {
            this.namedTag.putShort("MaxSpawnDelay", 20 * 60 * 8);//6 Mins
        }

        if (!this.namedTag.contains("MaxNearbyEntities") || !(this.namedTag.get("MaxNearbyEntities") instanceof ShortTag)) {
            this.namedTag.putShort("MaxNearbyEntities", 2);
        }

        this.spawnRange = this.namedTag.getShort("SpawnRange");
        this.minSpawnDelay = this.namedTag.getShort("MinSpawnDelay");
        this.maxSpawnDelay = this.namedTag.getShort("MaxSpawnDelay");
        this.maxNearbyEntities = this.namedTag.getShort("MaxNearbyEntities");
//        this.requiredPlayerRange = this.namedTag.getShort("RequiredPlayerRange");
        this.requiredPlayerRange = 32;

        this.scheduleUpdate();
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    public int GetSEntityID() {
        return entityId;
    }

    public int GetSpawnerLevel() {
        return lvl;
    }

    public String GetEntityNameFromID() {
        switch (GetSEntityID()) {
            case 4:
            case Pig.NETWORK_ID:
                return "Pig";
            default:
                return getName();
        }
    }

    @Override
    public String toString() {
        return "BlockEntitySpawner{" +
                "entityId=" + entityId +
                ", spawnRange=" + spawnRange +
                ", maxNearbyEntities=" + maxNearbyEntities +
                ", requiredPlayerRange=" + requiredPlayerRange +
                ", lvl=" + lvl +
                ", delay=" + delay +
                ", wait=" + wait +
                ", minSpawnDelay=" + minSpawnDelay +
                ", maxSpawnDelay=" + maxSpawnDelay +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean onUpdate() {
        if (this.closed) {
            return false;
        }

        int r = Utils.rand(this.minSpawnDelay, this.maxSpawnDelay);
//        System.out.println("UPDATE "+this.x+" | "+this.y+" | "+this.z+" | WAIT: "+delay+"<"+r);
        if (this.delay++ >= r && !wait) {
//            System.out.println("UPDATE "+this.x+" | "+this.y+" | "+this.z+" | DEWLAY TTTTTTTTTTTTTTTTTTTTT"+delay);
            this.delay = 0;

            SpawnerCalculationsAsync SCA = new SpawnerCalculationsAsync(level.getEntities(), this, requiredPlayerRange, maxNearbyEntities);
            server.getScheduler().scheduleAsyncTask(SCA);
            wait = true;
        }
        return true;
    }

    public void afterUpdate(ArrayList<Entity> list) {
//        Server.getInstance().broadcastMessage("After Update"+list.size()+" | "+maxNearbyEntities);
        if (list.size() > 0) {
            if (list.size() <= this.maxNearbyEntities) {
//                System.out.println("CREATE ENTITYT");
                Entity entity = CustomFactory.SpawnEntityStack(this.entityId, this);
                if (entity != null) {
                    entity.spawnToAll();
                    if (((EntityStackable) entity).IsStackable()) {
                        System.out.println("GOING TO + " + ((EntityStackable) entity).GetStackCount());
                    }
                }
            } else {
                System.out.println("YEAAAAA FULL");
            }
        }
    }

    @Override
    protected void initBlockEntity() {
        System.out.println("INITTTTTT");
        if (this.namedTag.contains("Type")) {
            this.entityId = this.namedTag.getInt("Type");
        }

        if (!this.namedTag.contains("SpawnRange") || !(this.namedTag.get("SpawnRange") instanceof ShortTag)) {
            this.namedTag.putShort("SpawnRange", 8);
        }

        if (!this.namedTag.contains("MinSpawnDelay") || !(this.namedTag.get("MinSpawnDelay") instanceof ShortTag)) {
            this.namedTag.putShort("MinSpawnDelay", 2400);//2 Min
        }

        if (!this.namedTag.contains("MaxSpawnDelay") || !(this.namedTag.get("MaxSpawnDelay") instanceof ShortTag)) {
            this.namedTag.putShort("MaxSpawnDelay", 20 * 60 * 8);//6 Mins
        }

        if (!this.namedTag.contains("MaxNearbyEntities") || !(this.namedTag.get("MaxNearbyEntities") instanceof ShortTag)) {
            this.namedTag.putShort("MaxNearbyEntities", 2);
        }
        if (this.namedTag.contains("Level")) {
            this.lvl = this.namedTag.getInt("Level");
        } else {
            lvl = 1;
        }


        super.initBlockEntity();
        onUpdate();
    }

    @Override
    public void saveNBT() {
        super.saveNBT();

//        this.namedTag.putString("id", "MobSpawner");
        this.namedTag.putInt("EntityId", this.entityId);
        this.namedTag.putInt("Type", this.entityId);
        this.namedTag.putInt("Level", this.lvl);
        this.namedTag.putShort("SpawnRange", this.spawnRange);
        this.namedTag.putShort("MinSpawnDelay", this.minSpawnDelay);
        this.namedTag.putShort("MaxSpawnDelay", this.maxSpawnDelay);
        this.namedTag.putShort("MaxNearbyEntities", this.maxNearbyEntities);
        this.namedTag.putShort("RequiredPlayerRange", this.requiredPlayerRange);
    }

    @Override
    public CompoundTag getSpawnCompound() {


//        this.namedTag.putString("id", this.getSaveId());
//        this.namedTag.putInt("x", (int) this.getX());
//        this.namedTag.putInt("y", (int) this.getY());
//        this.namedTag.putInt("z", (int) this.getZ());
//        this.namedTag.putBoolean("isMovable", this.movable);
        return new CompoundTag()

                .putInt("EntityId", this.entityId)
                .putInt("Type", this.entityId)
        .putString("id", this.getSaveId())
//                .putString("SpawnData", this.entityId + "")//"minecraft:zombie"
//                .putList(new ListTag<CompoundTag>("SpawnPotentials") {
//                    {
//                        add(new CompoundTag() {{
//                            putInt("Weight", 1);
//                            putCompound("Entity", new CompoundTag() {{
//                                putString("id", entityId + "");
//                            }});
//                        }});
//                    }
//                })
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z);

    }

    @Override
    public boolean isBlockEntityValid() {
        return this.getBlock().getId() == Item.MONSTER_SPAWNER;
    }

    public void setSpawnEntityType(int entityId) {
        this.entityId = entityId;
        this.spawnToAll();
    }

    public void setMinSpawnDelay(int minDelay) {
        if (minDelay > this.maxSpawnDelay) {
            return;
        }

        this.minSpawnDelay = minDelay;
    }

    public void setMaxSpawnDelay(int maxDelay) {
        if (this.minSpawnDelay > maxDelay) {
            return;
        }

        this.maxSpawnDelay = maxDelay;
    }

    public void setSpawnDelay(int minDelay, int maxDelay) {
        if (minDelay > maxDelay) {
            return;
        }

        this.minSpawnDelay = minDelay;
        this.maxSpawnDelay = maxDelay;
    }

    public void setRequiredPlayerRange(int range) {
        this.requiredPlayerRange = range;
    }

    public void setMaxNearbyEntities(int count) {
        this.maxNearbyEntities = count;
    }

}
