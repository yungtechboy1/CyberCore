package net.yungtechboy1.CyberCore.Custom.Block;

import net.yungtechboy1.CyberCore.Custom.BlockEntity.SpawnerWithLevelBlockEntity;
import net.yungtechboy1.CyberCore.entities.block.BlockEntitySpawner;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockMobSpawner;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;

/**
 * Created by carlt_000 on 1/14/2017.
 * Bitwise:
 * 0&1 => Used for spawner level
 */
//TODO
public class SpawnerWithLevelBlock extends BlockMobSpawner {
    int meta;

    public SpawnerWithLevelBlock() {
        this(0);
    }

    public SpawnerWithLevelBlock(int m) {
        super();
        meta = m;
    }

    @Override
    public String getName() {
        int type = getSpawnerType();
        String t = "Unknown";

        return TextFormat.GOLD + "Spawner " + TextFormat.AQUA + "Level " + getSpawnerLevel();
    }

    public Integer getSpawnerLevel() {
        return (meta & 7) + 1;
    }

    public Integer getSpawnerType() {
        return (meta >> 3);
    }

    public void setSpawnerInfo(int level, int type) {
        level--;
        if (level >= 8) level = 7;
        if (level < 0) level = 0;
        meta = type;
        meta = meta << 3;
        meta |= level;
    }



    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {

        BlockEntity blockEntity = this.getLevel().getBlockEntity(this);
        if (blockEntity != null && blockEntity instanceof BlockEntitySpawner) {
            ((BlockEntitySpawner) blockEntity).setSpawnEntityType(item.getDamage());
        } else {
            if (blockEntity != null) blockEntity.close();
            CompoundTag nbt = new CompoundTag().putString("id", BlockEntity.MOB_SPAWNER).putInt("EntityId", item.getDamage()).putInt("x", (int) this.x).putInt("y", (int) this.y).putInt("z", (int) this.z).putInt("level", getSpawnerLevel());
            new SpawnerWithLevelBlockEntity(this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt, getSpawnerLevel());
        }
        return super.place(item, block, target, face, fx, fy, fz, player);
    }

    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        return place(item, block, target, face, fx, fy, fz, null);
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (item.getId() == Item.SPAWN_EGG) {

        }
        return true;
    }

    @Override
    public int getId() {
        return Block.MONSTER_SPAWNER;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public double getHardness() {
        return 5.0D;
    }

    @Override
    public double getResistance() {
        return 25;
    }

    //TODO
//    public Item[] getDrops(Item item) {
//        return item.isPickaxe() && item.getTier() >= 1 ? new Item(Item.MONSTER_SPAWNER,meta) : new int[0][0];
//    }
}
