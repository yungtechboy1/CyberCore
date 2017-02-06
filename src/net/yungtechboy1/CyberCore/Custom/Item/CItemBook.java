package net.yungtechboy1.CyberCore.Custom.Item;

/**
 * Created by carlt_000 on 1/18/2017.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import cn.nukkit.item.Item;

public class CItemBook extends Item {
    public CItemBook() {
        this(0, 1);
    }

    public CItemBook(Integer meta) {
        this(meta, 1);
    }

    public CItemBook(Integer meta, int count) {
        super(340, meta, count, "CBook");
    }

    public int getEnchantAbility() {
        return 25;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
