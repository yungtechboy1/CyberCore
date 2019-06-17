package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.Block.SpawnerWithLevelBlock;

//@Deprecated
public class CustomItemBlockSpawnerWithLevelBlock extends ItemBlock {


    Block _Block = null;
    SpawnerWithLevelBlock.SpawnerType ST = null;

    public CustomItemBlockSpawnerWithLevelBlock(Integer meta) {
        this(new SpawnerWithLevelBlock(meta), meta, 1);
    }

    @Override
    public String getCustomName() {
        return super.getCustomName();
    }

    public CustomItemBlockSpawnerWithLevelBlock(Block b, Integer meta, int count) {
        super(b, 0, count);
        if (meta != 0) {
            ST = SpawnerWithLevelBlock.SpawnerType.getFromInt(meta);
            setNamedTag(getNamedTag());
        }
//        else {
//            if (hasCompoundTag() && getNamedTag().contains("type")) {
//                ST = SpawnerWithLevelBlock.SpawnerType.getFromInt(getNamedTag().getInt("type"));
//            }
//        }
//        RelaodSpawnerType();
//        if (ST != null) setSpawnerType(ST);
//        block = new SpawnerWithLevelBlock(meta);
    }

//    public void RelaodSpawnerType() {
//        RelaodSpawnerType(false);
//    }
//    public void RelaodSpawnerType(boolean f) {
//        if (ST == null && hasCompoundTag() && getNamedTag().contains("type")) {
//            ST = SpawnerWithLevelBlock.SpawnerType.getFromInt(getNamedTag().getInt("type"));
//        }
//        if (ST != null) setCustomName(TextFormat.AQUA + ST.name() + " Spawner");
//        else setCustomName(TextFormat.RED + "UNKNWON Spawner");
//    }

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
        ct.putInt("type", st.getID());
        setCompoundTag(ct);
        return;

    }

    private CompoundTag addCustomNametoCT(CompoundTag tag, String n){
        if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
            tag.getCompound("display").putString("Name", n);
        } else {
            tag.putCompound("display", new CompoundTag("display")
                    .putString("Name", n)
            );
        }
        return tag;
    }

    @Override
    public Item setNamedTag(CompoundTag tag) {
        if(tag == null){
            clearNamedTag();
            return this;
        }
        if (ST == null) {
            if (tag.contains("type")) {
                int t = tag.getInt("type");
                ST = SpawnerWithLevelBlock.SpawnerType.getFromInt(t);
                tag = addCustomNametoCT(tag,TextFormat.AQUA + ST.name() + " Spawner");
            }else{
                tag = addCustomNametoCT(tag,TextFormat.RED + "UNKNWON Spawner");

            }
        }else{
            tag = addCustomNametoCT(tag,TextFormat.AQUA + ST.name() + " Spawner");
        }
        super.setNamedTag(tag);
        return this;
    }

//    @Deprecated
//    @Override
//    public Item setCustomName(String name) {
////        if (name == null || name.equals("")) {
////            this.clearCustomName();
////        }
////
////        CompoundTag tag;
////        if (!this.hasCompoundTag()) {
////            tag = new CompoundTag();
////        } else {
////            tag = this.getNamedTag();
////        }
////        if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
////            tag.getCompound("display").putString("Name", name);
////        } else {
////            tag.putCompound("display", new CompoundTag("display")
////                    .putString("Name", name)
////            );
////        }
////        this.setNamedTag(tag);
//        return this;
//    }

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
    public CompoundTag getNamedTag() {
        CompoundTag c = super.getNamedTag();
        if(c == null)c = new CompoundTag();
//        if (ST == null) ST = SpawnerWithLevelBlock.SpawnerType.Unknown;
        if (ST != null)c.putInt("type", ST.getID());
        return c;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        return super.onClickAir(player, directionVector);
    }
}
