package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.Item;

/**
 * Created by carlt_000 on 2/6/2017.
 */
public class ItemMagmaCream extends Item {
    public ItemMagmaCream() {
        this(0, 1);
    }

    public ItemMagmaCream(Integer meta) {
        this(meta, 1);
    }

    public ItemMagmaCream(Integer meta, int count) {
        super(381, meta, count, "Magma Cream");
    }

    @Override
    public int getMaxStackSize() {
        if(getDamage() > 0)return 1;
        return super.getMaxStackSize();
    }
}
