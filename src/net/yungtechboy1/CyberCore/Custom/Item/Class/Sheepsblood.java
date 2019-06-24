package net.yungtechboy1.CyberCore.Custom.Item.Class;

import cn.nukkit.item.ItemPotion;
import cn.nukkit.utils.TextFormat;

public class Sheepsblood extends ItemPotion {
    public Sheepsblood(){
        this(0);
    }

    public Sheepsblood(Integer meta) {
        this(meta,1);
    }

    public Sheepsblood(Integer meta, int count) {
        super(meta, count);
        setCustomName(TextFormat.RED+"Sheep's Blood");
    }

    @Override
    public int getMaxStackSize() {
        return 100;
    }
}
