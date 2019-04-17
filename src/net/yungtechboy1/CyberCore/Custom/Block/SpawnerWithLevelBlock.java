package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.Server;
import cn.nukkit.item.ItemBookEnchanted;
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

import javax.swing.plaf.nimbus.State;

/**
 * Created by carlt_000 on 1/14/2017.
 * Bitwise:
 * 0&1 => Used for spawner level
 */
//TODO
public class SpawnerWithLevelBlock extends BlockMobSpawner {

    public static Item CreateTest() {
        Item i = Item.get(Item.MONSTER_SPAWNER, 0, 1);
        i.setCompoundTag(new CompoundTag() {{
            putInt("Level", 1);
            putInt("Type", 12);
        }});
        return i;
    }


    public SpawnerWithLevelBlock() {
        super();
    }


    @Override
    public String getName() {
        int type = getSpawnerType();
        String t = "Unknown";

        return TextFormat.GOLD + "Spawner " + TextFormat.AQUA + "Level " + getSpawnerLevel();
    }

    public int GetTypeFromItem(Item i) {
        if (!i.hasCompoundTag()) return -1;
        CompoundTag ct = i.getNamedTag();
        int v = ct.getInt("Type");
        if (v == 0) return -1;
        return v;
    }

    public int GetLevelFromItem(Item i) {
        if (!i.hasCompoundTag()) return -1;
        CompoundTag ct = i.getNamedTag();
        int v = ct.getInt("Level");
        if (v == 0) return -1;
        return v;
    }

    public Integer getSpawnerLevel(Item i) {
        return GetLevelFromItem(i);
    }

    public Integer getSpawnerLevel() {
        if(getLevel() == null)return -1;
        BlockEntitySpawner bse = (BlockEntitySpawner) getLevel().getBlockEntity(this);
        if(bse == null)return -1;
        return bse.GetSpawnerLevel();
    }

    public Integer getSpawnerType() {
        if(getLevel() == null)return -1;
        BlockEntitySpawner bse = (BlockEntitySpawner) getLevel().getBlockEntity(this);
        if(bse == null)return -1;
        return bse.GetSEntityID();
    }

    @Deprecated
    /**
     * Depreciated Do Not Use Yet!
     * #TODO
     */
    public void setSpawnerInfo(int level, int type) {
//        meta = type;
//        meta = meta << 3;
//        meta |= level;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {

        BlockEntity blockEntity = this.getLevel().getBlockEntity(this);
        if (blockEntity != null && blockEntity instanceof BlockEntitySpawner) {
            Server.getInstance().broadcastMessage("ENTITY ALREADY CREATED!!!!");
            ((BlockEntitySpawner) blockEntity).setSpawnEntityType(item.getDamage());
        } else {
            Server.getInstance().broadcastMessage("ENTITY NOT ALREADY CREATED!!!!");
            if (blockEntity != null) blockEntity.close();
            int sl = getSpawnerLevel(item);
            int t = GetTypeFromItem(item);
            if (sl == -1 || t == -1) {
                //Invalid Block
                player.sendMessage("Error! Spawner is invalid! T:"+t+" SL:"+sl);
                return false;
            }
            CompoundTag nbt = new CompoundTag()
//                    .putString("id", BlockEntity.MOB_SPAWNER)
                    .putString("id", BlockEntity.MOB_SPAWNER)
                    .putInt("EntityId", t)
                    .putInt("x", (int) this.x)
                    .putInt("y", (int) this.y)
                    .putInt("z", (int) this.z);
//                    .putInt("Level", sl);
            player.sendMessage("PLACE! Spawner is valid! T:"+t+" SL:"+sl);
            SpawnerWithLevelBlockEntity s = new SpawnerWithLevelBlockEntity(this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt, sl);
            Server.getInstance().broadcastMessage(s.namedTag.getShort("MinSpawnDelay")+" <<<<<<");
            boolean a = super.place(item, block, target, face, fx, fy, fz, player);
            if(a)getLevel().addBlockEntity(s);
            return a;
        }
        return super.place(item, block, target, face, fx, fy, fz, player);
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
