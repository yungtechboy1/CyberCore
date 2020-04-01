package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.ItemBlock;
import net.yungtechboy1.CyberCore.Custom.Block.CustomWoolBlockTerra;

public class CustomItemPurpleGlazedTerraCotta extends ItemBlock{

    public CustomItemPurpleGlazedTerraCotta() {
        this(0);
    }

    public CustomItemPurpleGlazedTerraCotta( Integer meta) {
        this( meta,1);
    }

    public CustomItemPurpleGlazedTerraCotta( Integer meta, int count) {
        super(new CustomWoolBlockTerra(meta), meta, count);
        setCustomName(getBlock().getName());
    }
}