package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.Server;
import cn.nukkit.block.BlockSolid;
import cn.nukkit.block.BlockSolidMeta;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.passive.EntityAnimal;
import cn.nukkit.item.ItemBookEnchanted;
import net.yungtechboy1.CyberCore.Custom.BlockEntity.SpawnerWithLevelBlockEntity;
import net.yungtechboy1.CyberCore.entities.animal.swimming.Squid;
import net.yungtechboy1.CyberCore.entities.animal.walking.*;
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
import net.yungtechboy1.CyberCore.entities.monster.flying.Blaze;
import net.yungtechboy1.CyberCore.entities.monster.walking.*;

import javax.swing.plaf.nimbus.State;

/**
 * Created by carlt_000 on 1/14/2017.
 * Bitwise:
 * 0&1 => Used for spawner level
 */
//TODO
public class SpawnerWithLevelBlock extends BlockSolidMeta {

//    public static Item CreateTest() {
//        Item i = Item.get(Item.MONSTER_SPAWNER, 0, 1);
//        i.setCompoundTag(new CompoundTag() {{
//            putInt("Level", 1);
//            putInt("Type", 12);
//        }});
//        return i;
//    }

    public SpawnerWithLevelBlock() {
        this(0);
    }


    public SpawnerWithLevelBlock(int meta) {
        super(meta);
    }

    public String getName() {
        SpawnerType type = getSpawnerType();
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
        if (getLevel() == null) return -1;
        BlockEntitySpawner bse = (BlockEntitySpawner) getLevel().getBlockEntity(this);
        if (bse == null) return -1;
        return bse.GetSpawnerLevel();
    }

    public SpawnerType getSpawnerType() {
       return SpawnerType.getFromInt(getDamage());
    }
    public Integer getSpawnerType2() {
        if (getLevel() == null) return -1;
        BlockEntitySpawner bse = (BlockEntitySpawner) getLevel().getBlockEntity(this);
        if (bse == null) return -1;
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
                player.sendMessage("Error! Spawner is invalid! T:" + t + " SL:" + sl);
                return false;
            }
            CompoundTag nbt = new CompoundTag()
//                    .putString("id", BlockEntity.MOB_SPAWNER)
                    .putString("id", BlockEntity.MOB_SPAWNER)
//                    .putInt("EntityId", t)
                    .putInt("Type", t)
                    .putInt("x", (int) this.x)
                    .putInt("y", (int) this.y)
                    .putInt("z", (int) this.z)
                    .putInt("Level", sl);
            player.sendMessage("PLACE! Spawner is valid! T:" + t + " SL:" + sl);
            SpawnerWithLevelBlockEntity s = new SpawnerWithLevelBlockEntity(this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt, sl);
//            EntityHuman
            Server.getInstance().broadcastMessage(s.namedTag.getShort("MinSpawnDelay") + " <<<<<<");
            boolean a = super.place(item, block, target, face, fx, fy, fz, player);
            if (a) getLevel().addBlockEntity(s);
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

    public enum SpawnerType {
        Sheep(net.yungtechboy1.CyberCore.entities.animal.walking.Sheep.NETWORK_ID),
        Pig(net.yungtechboy1.CyberCore.entities.animal.walking.Pig.NETWORK_ID),
        Cow( net.yungtechboy1.CyberCore.entities.animal.walking.Cow.NETWORK_ID),
        Squid( net.yungtechboy1.CyberCore.entities.animal.swimming.Squid.NETWORK_ID),
        Chicken( net.yungtechboy1.CyberCore.entities.animal.walking.Chicken.NETWORK_ID),
        Rabbit(net.yungtechboy1.CyberCore.entities.animal.walking.Rabbit.NETWORK_ID),
        Zombie(net.yungtechboy1.CyberCore.entities.monster.walking.Zombie.NETWORK_ID),
        ZombieVillagers( ZombieVillager.NETWORK_ID),
        SilverFishh( Silverfish.NETWORK_ID),
        Creeper( net.yungtechboy1.CyberCore.entities.monster.walking.Creeper.NETWORK_ID),
        ZombiePigMan( net.yungtechboy1.CyberCore.entities.monster.walking.Zombie.NETWORK_ID),
        //        ZombiePigMan(3, net.yungtechboy1.CyberCore.entities.monster.walking.Zombie.),//TODO
        Mooshroom( net.yungtechboy1.CyberCore.entities.animal.walking.Mooshroom.NETWORK_ID),
        Skeleton( net.yungtechboy1.CyberCore.entities.monster.walking.Skeleton.NETWORK_ID),
        Spider( net.yungtechboy1.CyberCore.entities.monster.walking.Spider.NETWORK_ID),
        Blaze( net.yungtechboy1.CyberCore.entities.monster.flying.Blaze.NETWORK_ID),
        Slime( net.yungtechboy1.CyberCore.entities.monster.walking.Zombie.NETWORK_ID),
        //        Slime(3,),
        CaveSpider( net.yungtechboy1.CyberCore.entities.monster.walking.CaveSpider.NETWORK_ID),
        IronGolem( net.yungtechboy1.CyberCore.entities.monster.walking.IronGolem.NETWORK_ID),
        Witch( net.yungtechboy1.CyberCore.entities.monster.walking.Witch.NETWORK_ID),
        Horse( net.yungtechboy1.CyberCore.entities.animal.walking.Horse.NETWORK_ID);

        private int Key = -1;
        private int ID = -1;

        SpawnerType( int id) {
            ID = id;
        }

        public static SpawnerType getFromInt(int damage) {
            for(SpawnerType st: SpawnerType.values()){
                if(damage == st.getID())return st;
            }
            return null;
        }

        public int getID() {
            return ID;
        }
    }

    //TODO
//    public Item[] getDrops(Item item) {
//        return item.isPickaxe() && item.getTier() >= 1 ? new Item(Item.MONSTER_SPAWNER,meta) : new int[0][0];
//    }
}
