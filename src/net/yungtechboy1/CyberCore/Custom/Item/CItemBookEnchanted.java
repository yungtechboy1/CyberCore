package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;

/**
 * Created by carlt_000 on 1/18/2017.
 */
public class CItemBookEnchanted extends Item {
    public CItemBookEnchanted() {
        this(Integer.valueOf(0), 1);
    }

    public CItemBookEnchanted(Integer meta) {
        this(meta, 1);
    }

    public CItemBookEnchanted(Integer meta, int count) {
        super(403, meta, count, "CEnchanted Book");
    }

    public Enchantment getEnchantment(){
        return Enchantment.get(getDamage());
    }
}
