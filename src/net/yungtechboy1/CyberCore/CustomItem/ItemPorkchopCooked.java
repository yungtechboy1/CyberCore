package net.yungtechboy1.CyberCore.CustomItem;

import cn.nukkit.item.ItemEdible;
import cn.nukkit.utils.TextFormat;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class ItemPorkchopCooked extends ItemEdible {

    public ItemPorkchopCooked() {
        this(0, 1);
    }

    public ItemPorkchopCooked(Integer meta) {
        this(meta, 1);
    }

    public ItemPorkchopCooked(Integer meta, int count) {
        super(COOKED_PORKCHOP, meta, count, "Cooked Porkchop");
        if (meta != 0) {
            name = "Cooked Porkchop" + TextFormat.RESET + "\n" +
                    TextFormat.GRAY + "Hormone Lvl " + meta;
        }
    }
}
