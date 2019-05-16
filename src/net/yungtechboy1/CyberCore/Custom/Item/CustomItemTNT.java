package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockTNT;

/**
 * Created by carlt on 5/16/2019.
 */
public class CustomItemTNT extends ItemBlock {
    int TNTLevel = 1;
    private static String CustomLevel = "customlevel";

    public CustomItemTNT(CustomBlockTNT block ) {
        this(block,1);
    }
    public CustomItemTNT(CustomBlockTNT block, int lvl ) {
        super(block);
        setTNTLevel(lvl);
        WriteTags();
    }

    public static int GetLevelFromTags(Item b) {
        CompoundTag ct = b.getNamedTag();
        if(ct == null)ct = new CompoundTag();
        if(ct.contains(CustomLevel)){
            return ct.getInt(CustomLevel);
        }
        return 1;
    }
    private void WriteTags() {
        CompoundTag ct = getNamedTag();
        if(ct == null)ct = new CompoundTag();
        ct.putInt(CustomLevel,getTNTLevel());

    }

    public CustomItemTNT(CustomBlockTNT block, Integer meta, int count) {
        super(block, meta, count);
    }

    public int getTNTLevel() {
        return TNTLevel;
    }

    public void setTNTLevel(int t) {
        this.TNTLevel = t;
    }



//setCustomBlockData
//   pla
}
