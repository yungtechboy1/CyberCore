package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockSolidMeta;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.item.Item;
import cn.nukkit.level.Sound;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.metadata.MetadataValue;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.BlockColor;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

/**
 * Created by carlt on 5/16/2019.
 */
public class CustomBlockTNT extends BlockSolidMeta {
    TNTType TType = TNTType.Basic;

    public CustomBlockTNT(TNTType meta) {
        super(meta.ordinal());
        TType = meta;
    }

    public CustomBlockTNT(int meta) {
        super(meta);
        setTNTType();
    }

    public CustomBlockTNT() {
        this(0);
    }

    @Override
    public Item[] getDrops(Item item) {
        return new ArrayList<Item>() {
            {
                add(toItem());
            }
        }.toArray(new Item[1]);
    }

    @Override
    public Item toItem() {
        Item i = super.toItem();
        i.setCustomName(getName());
        return i;
    }

    private void setTNTType() {
        TType = TNTType.getfromint(getDamage());
    }

    @Override
    public String getName() {
//        return TextFormat.AQUA + TType.getName() + TextFormat.RED + " TNT";
        return TType.getName() + " TNT";
    }

    @Override
    public int getId() {
        return 46;
    }

    @Override
    public boolean canBeReplaced() {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return super.getBoundingBox();
    }

    @Override
    public double getHardness() {
        return 0.0D;
    }

    @Override
    public double getResistance() {
        return 0.0D;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public int getBurnChance() {
        return 15;
    }

    @Override
    public boolean canBePlaced() {
        return true;
    }

    @Override
    public int getBurnAbility() {
        return 100;
    }

    public int getFuse() {
        NukkitRandom nr = new NukkitRandom();
        switch (getTNTType()) {
            case Basic:
                return nr.nextRange(150, 250);
            case Silent:
                return nr.nextRange(100, 200);
            case Upgraded:
                return nr.nextRange(80, 170);
            case Super:
                return nr.nextRange(70, 130);
            case Experimental:
                return nr.nextRange(70, 90);
            default:
                return nr.nextRange(150, 250);
        }
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        if (this.getLevel().setBlock(this, this, true, true)) {

            return true;
        }
        return false;
    }

    public TNTType getTNTType() {
//        CyberCoreMain.getInstance().getLogger().error("LVL >>"+getDamage());
        return TNTType.getfromint(getDamage());
    }

    public void prime() {
        this.prime(getFuse());
    }

    public void prime(int fuse) {
        this.prime(fuse, (Entity) null);
    }

    public void prime(int fuse, Entity source) {
        this.getLevel().setBlock(this, new BlockAir(), true);
        double mot = (double) (new NukkitRandom()).nextSignedFloat() * 3.141592653589793D * 2.0D;
        CompoundTag nbt = (new CompoundTag()).putList((new ListTag("Pos")).add(new DoubleTag("", this.x + 0.5D)).add(new DoubleTag("", this.y)).add(new DoubleTag("", this.z + 0.5D))).putList((new ListTag("Motion")).add(new DoubleTag("", -Math.sin(mot) * 0.02D)).add(new DoubleTag("", 0.2D)).add(new DoubleTag("", -Math.cos(mot) * 0.02D))).putList((new ListTag("Rotation")).add(new FloatTag("", 0.0F)).add(new FloatTag("", 0.0F))).putShort("Fuse", fuse);
        Entity tnt = new EntityPrimedTNT(this.getLevel().getChunk(this.getFloorX() >> 4, this.getFloorZ() >> 4), nbt, source);
        tnt.spawnToAll();
        this.level.addSound(this, Sound.RANDOM_FUSE);
    }

    @Override
    public int onUpdate(int type) {
        if ((type == 1 || type == 6) && this.level.isBlockPowered(this.getLocation())) {
            this.prime();
        }

        return 0;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (item.getId() == 259) {
            item.useOn(this);
            this.prime(80, player);
            return true;
        } else if (item.getId() == 385) {
            if (!player.isCreative()) {
                player.getInventory().removeItem(new Item[]{Item.get(385, Integer.valueOf(0), 1)});
            }

            this.level.addSound(player, Sound.MOB_GHAST_FIREBALL);
            this.prime(80, player);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.TNT_BLOCK_COLOR;
    }

    public enum TNTType {
        Basic,
//        Experimental,
        Silent,
        Upgraded,
        Super,
//        UNUSED;
        Experimental;

        public static TNTType getfromint(int i) {
            if (Basic.ordinal() == i) return Basic;
            if (Silent.ordinal() == i) return Silent;
            if (Upgraded.ordinal() == i) return Upgraded;
            if (Super.ordinal() == i) return Super;
            if (Experimental.ordinal() == i) return Experimental;
            return Basic;
        }

        public String getName() {
            switch (this) {
                case Basic:
                    return "Basic";
                case Silent:
                    return "Silent";
                case Upgraded:
                    return "Upgraded";
                case Super:
                    return "Super";
                case Experimental:
                    return "Experimental";
                default:
                    return "Unknown Type";
            }
        }


    }

    public class TNTMetaDataValue extends MetadataValue {

        int lvl = 1;

        public TNTMetaDataValue(int l) {
            super(CyberCoreMain.getInstance());
            lvl = l;
        }

        @Override
        public Object value() {
            return lvl;
        }

        @Override
        public void invalidate() {

        }
    }


}
