package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockTNT;
import net.yungtechboy1.CyberCore.Custom.Block.SpawnerWithLevelBlock;

@Deprecated
public class CustomItemBlockSpawnerWithLevelBlock extends ItemBlock {

    public static Item getSpawnerItem(int meta){
        ItemBlock ib = new ItemBlock(new SpawnerWithLevelBlock(meta),meta);
//        ib.setCustomName("TEST");
        return ib;
    }

    Block _Block = null;
    SpawnerWithLevelBlock.SpawnerType ST = null;
//    public CustomItemBlockSpawnerWithLevelBlock(Block bblock) {
//        this(bblock, 0);
//    }
//
//    public CustomItemBlockSpawnerWithLevelBlock(Block bblock, Integer meta) {
//        this(bblock, meta, 1);
//    }
//
//    public CustomItemBlockSpawnerWithLevelBlock(Block bblock, Integer meta, int count) {
//        super(bblock.getId(), meta, count);
//        _Block = bblock;
//        setCustomName(((SpawnerWithLevelBlock) block).getName());
//    }

    public CustomItemBlockSpawnerWithLevelBlock() {
        this(0);
    }

    public CustomItemBlockSpawnerWithLevelBlock(Integer meta) {
        this(meta, 1);
    }

    public CustomItemBlockSpawnerWithLevelBlock(Integer meta, int count) {
        super(new SpawnerWithLevelBlock(meta), 0, count);
        ST = SpawnerWithLevelBlock.SpawnerType.getFromInt(meta);
        if (ST != null) setCustomName(TextFormat.AQUA + ST.name() + " Spawner");
        else setCustomName(TextFormat.AQUA + "DAMAGE: " + getDamage() + " Spawner");
        if (ST != null) setSpawnerType(ST);
        block = new SpawnerWithLevelBlock(meta);
    }

    public SpawnerWithLevelBlock.SpawnerType getSpawnerType() {
//        return SpawnerWithLevelBlock.SpawnerType.getFromInt(getDamage());
        if (hasCompoundTag()) {
            CompoundTag ct = getNamedTag();
            if (ct.contains("type")) return SpawnerWithLevelBlock.SpawnerType.getFromInt(ct.getInt("type"));
        }
        return null;
    }

    public void setSpawnerType(SpawnerWithLevelBlock.SpawnerType st) {
        CompoundTag ct = new CompoundTag();
        if (hasCompoundTag()) ct = getNamedTag();
         ct.putInt("type",st.getID());
         setCompoundTag(ct);
        return;

    }

    @Deprecated
    public int getQuantity() {
        if (hasCompoundTag()) {
            CompoundTag ct = getNamedTag();
            if (ct.contains("quantity")) return ct.getInt("quantity");
        }
        return 1;
    }

    @Deprecated
    public void addQunatity(int i) {
        if (hasCompoundTag()) {
            CompoundTag ct = getNamedTag();
//            if (ct.contains("quantity")) {
            ct.putInt("quantity", ct.getInt("quantity") + i);
//            }else{
//                ct.putInt("quantity", ct.getInt("quantity")+i);
//            }
        }
    }

    @Deprecated
    public boolean takeQunatity(int i) {
        if (hasCompoundTag()) {
            CompoundTag ct = getNamedTag();
            if (ct.contains("quantity")) {
                int q = ct.getInt("quantity");
                if (q < i) return false;
                ct.putInt("quantity", q - i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        return super.onClickAir(player, directionVector);
    }
}
