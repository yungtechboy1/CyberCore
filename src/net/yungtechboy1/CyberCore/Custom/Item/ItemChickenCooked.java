package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.ItemEdible;
import cn.nukkit.utils.TextFormat;

import java.awt.*;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class ItemChickenCooked extends ItemEdible {

    public ItemChickenCooked() {
        this(0, 1);
    }

    public ItemChickenCooked(Integer meta) {
        this(meta, 1);
    }

    public ItemChickenCooked(Integer meta, int count) {
        super(COOKED_CHICKEN, meta, count, "Cooked Chicken");
        if(meta != 0){
            name = "Cooked Chicken"+ TextFormat.RESET+ "\n"+
                    TextFormat.GRAY+"Hormone Lvl "+meta;
        }
    }

}
