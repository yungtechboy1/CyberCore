package net.yungtechboy1.CyberCore.Custom.BlockEntity;

import net.yungtechboy1.CyberCore.entities.block.BlockEntitySpawner;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * Created by carlt_000 on 1/13/2017.
 */
public class SpawnerWithLevelBlockEntity extends BlockEntitySpawner {

    public Integer Lvl = 0;
    public SpawnerWithLevelBlockEntity(FullChunk chunk, CompoundTag nbt, Integer level){
        super(chunk,nbt);
        Lvl = level;
        if(level == 1){
            this.namedTag.putShort("MinSpawnDelay", 20*60);
            this.namedTag.putShort("MaxSpawnDelay", 20*60*6);
            this.namedTag.putShort("MaxNearbyEntities", 5);
            //this.namedTag.putShort("RequiredPlayerRange", 2400);
        }else if(level == 2){
            this.namedTag.putShort("MinSpawnDelay", 20*45);
            this.namedTag.putShort("MaxSpawnDelay", 20*60*5);
            this.namedTag.putShort("MaxNearbyEntities", 15);
        }else if(level == 3){
            this.namedTag.putShort("MinSpawnDelay", 20*30);
            this.namedTag.putShort("MaxSpawnDelay", 20*60*4);
            this.namedTag.putShort("MaxNearbyEntities", 25);
        }else if(level == 4){
            this.namedTag.putShort("MinSpawnDelay", 20*30);
            this.namedTag.putShort("MaxSpawnDelay", 20*60*3);
            this.namedTag.putShort("MaxNearbyEntities", 35);
        }else if(level == 5){
            this.namedTag.putShort("MinSpawnDelay", 20*15);
            this.namedTag.putShort("MaxSpawnDelay", 20*60*2);
            this.namedTag.putShort("MaxNearbyEntities", 45);
        }
    }
}
