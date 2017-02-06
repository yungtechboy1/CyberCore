package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockMobSpawner;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import de.kniffo80.mobplugin.entities.block.BlockEntitySpawner;
import net.yungtechboy1.CyberCore.Custom.*;
import net.yungtechboy1.CyberCore.Custom.BlockEntity.SpawnerWithLevelBlockEntity;

/**
 * Created by carlt_000 on 1/14/2017.
 * Bitwise:
 * 0&1 => Used for spawner level
 */
public class SpawnerWithLevelBlock extends BlockMobSpawner {
    public SpawnerWithLevelBlock() {
        this(0);
    }

    public SpawnerWithLevelBlock(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return TextFormat.GOLD + "Spawner " + TextFormat.AQUA + "Level " + getSpawnerLevel();
    }

    public void setSpawnerLevel(Integer lvl) {
        meta |= lvl;
    }
    public Integer getSpawnerLevel() {
        return (meta & 7);
    }

    @Override
    public boolean place(Item item, Block block, Block target, int face, double fx, double fy, double fz) {
        return super.place(item, block, target, face, fx, fy, fz);
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (item.getId() == Item.SPAWN_EGG) {
            BlockEntity blockEntity = this.getLevel().getBlockEntity(this);
            if (blockEntity != null && blockEntity instanceof BlockEntitySpawner) {
                ((BlockEntitySpawner) blockEntity).setSpawnEntityType(item.getDamage());
            } else {
                if (blockEntity != null)blockEntity.close();
                CompoundTag nbt = new CompoundTag().putString("id", BlockEntity.MOB_SPAWNER).putInt("EntityId", item.getDamage()).putInt("x", (int) this.x).putInt("y", (int) this.y).putInt("z",
                        (int) this.z);

                new SpawnerWithLevelBlockEntity(this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt,getSpawnerLevel());
            }
        }
        return true;
    }

    @Override
    public int getId() {
        return 52;
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

    public int[][] getDrops(Item item) {
        return item.isPickaxe() && item.getTier() >= 1 ? new int[][]{{52, meta, 1}} : new int[0][0];
    }
}
