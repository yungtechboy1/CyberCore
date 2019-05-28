package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.block.BlockMeta;

public class CustomBlockPurpleGlazedTerraCotta extends BlockMeta {

    protected CustomBlockPurpleGlazedTerraCotta(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "purple_glazed_terracotta"+getDamage();
    }

    @Override
    public int getId() {
        return 219;
    }

    @Override
    public boolean canBePlaced() {
        return false;
    }
}
