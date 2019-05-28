package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.block.BlockTripWire;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import javafx.scene.effect.SepiaTone;

public class CustomItemString extends Item {

    public CustomItemString() {
        this(0, 1);
    }

    public CustomItemString(Integer meta) {
        this(meta, 1);
    }

    public CustomItemString(Integer meta, int count) {
        super(STRING, meta, count, "String");
        this.block = new BlockTripWire();
        ItemStringType is = ItemStringType.getFromInt(meta);
        setCustomName(is.tf+is.name());
    }

    public enum ItemStringType {
        Regular(TextFormat.GRAY),
        Enchanted_String(TextFormat.AQUA);

        TextFormat tf = null;

        ItemStringType(TextFormat t) {
            tf = t;
        }

        public static ItemStringType getFromInt(int i) {
            if (i == 0) return Regular;
            if (i == 1) return Enchanted_String;
            return Regular;
        }

        public TextFormat getColor() {
            return tf;
        }
    }
}
