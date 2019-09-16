package net.yungtechboy1.CyberCore.Custom.Block.MainClasses;

import cn.nukkit.block.Block;
import cn.nukkit.utils.BlockColor;

public abstract class CustomBlockTransparentMeta extends Block {

    private int meta;

    protected CustomBlockTransparentMeta() {
        this(0);
    }

    protected CustomBlockTransparentMeta(int m) {
        meta = m;
    }

    public boolean isTransparent() {
        return true;
    }

    public BlockColor getColor() {
        return BlockColor.TRANSPARENT_BLOCK_COLOR;
    }

    public int getFullId() {
        return (this.getId() << 4) + this.getDamage();
    }

    public final int getDamage() {
        return this.meta;
    }

    public void setDamage(int meta) {
        this.meta = meta;
    }
}
