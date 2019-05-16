package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.Player;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockTNT;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.item.Item;
import cn.nukkit.level.Sound;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.BlockColor;

/**
 * Created by carlt on 5/16/2019.
 */
public class CustomBlockTNT extends BlockTNT {
    public enum TNTTypes{
        Regular,
        Level_1,
        Level_2,
        Level_3,
        Level_4,
        Level_5,
    }

    public CustomBlockTNT() {
    }



    @Override
    public String getName() {
        return "TNT";
    }

    @Override
    public int getId() {
        return 46;
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
    public int getBurnAbility() {
        return 100;
    }

    public int getFuse(){
        switch (Level){

        }
    }

    @Override
    public void prime() {
        this.prime(80);
    }

    @Override
    public void prime(int fuse) {
        this.prime(fuse, (Entity)null);
    }

    @Override
    public void prime(int fuse, Entity source) {
        this.getLevel().setBlock(this, new BlockAir(), true);
        double mot = (double)(new NukkitRandom()).nextSignedFloat() * 3.141592653589793D * 2.0D;
        CompoundTag nbt = (new CompoundTag()).putList((new ListTag("Pos")).add(new DoubleTag("", this.x + 0.5D)).add(new DoubleTag("", this.y)).add(new DoubleTag("", this.z + 0.5D))).putList((new ListTag("Motion")).add(new DoubleTag("", -Math.sin(mot) * 0.02D)).add(new DoubleTag("", 0.2D)).add(new DoubleTag("", -Math.cos(mot) * 0.02D))).putList((new ListTag("Rotation")).add(new FloatTag("", 0.0F)).add(new FloatTag("", 0.0F))).putShort("Fuse", fuse);
        Entity tnt = new EntityPrimedTNT(this.getLevel().getChunk(this.getFloorX() >> 4, this.getFloorZ() >> 4), nbt, source);
        tnt.spawnToAll();
        this.level.addSound(this, Sound.RANDOM_FUSE);
    }

    @Override
    public int onUpdate(int type) {
        if((type == 1 || type == 6) && this.level.isBlockPowered(this.getLocation())) {
            this.prime();
        }

        return 0;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if(item.getId() == 259) {
            item.useOn(this);
            this.prime(80, player);
            return true;
        } else if(item.getId() == 385) {
            if(!player.isCreative()) {
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
}
