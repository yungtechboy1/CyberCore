package net.yungtechboy1.CyberCore.Custom.Item;

import de.kniffo80.mobplugin.items.MobPluginItems;

/**
 * Created by Snake1999 on 2016/1/14.
 * Package cn.nukkit.item in project nukkit.
 */
public class ItemMuttonRaw extends MobPluginItems {

    public ItemMuttonRaw() {
        this(0, 1);
    }

    public ItemMuttonRaw(Integer meta) {
        this(meta, 1);
    }

    public ItemMuttonRaw(Integer meta, int count) {
        super(RAW_MUTTON, meta, count, "Raw Mutton");
    }
}
