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
    CustomBlockTNT.TNTType TNTLevel = CustomBlockTNT.TNTType.Basic;
    private static String CustomLevel = "customlevel";

    public CustomItemTNT() {
        this(CustomBlockTNT.TNTType.Basic);
    }
    public CustomItemTNT(CustomBlockTNT.TNTType lvl ) {
        this(lvl.ordinal(),1);
    }

    public CustomItemTNT( Integer meta) {
        this(meta,1);
    }
    public CustomItemTNT( Integer meta, int count) {
        super( new CustomBlockTNT(CustomBlockTNT.TNTType.getfromint(meta)), meta,count);
        setTNTType(CustomBlockTNT.TNTType.getfromint(meta));
        setCustomName((block).getName());
    }

    @Override
    public String getCustomName() {
        return block.getName();
    }

    public CustomBlockTNT.TNTType getTNTType() {
        return TNTLevel;
    }

    public void setTNTType(CustomBlockTNT.TNTType t) {
        this.TNTLevel = t;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        return super.onActivate(level, player, block, target, face, fx, fy, fz);
    }

    @Override
    public boolean useOn(Block block) {
        return super.useOn(block);
    }

    //setCustomBlockData
//   pla
}
