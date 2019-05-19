package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;

public class CustomItemGunpowder extends Item {

    public CustomItemGunpowder() {
        this(TNTType.Normal, 1);
    }

    public CustomItemGunpowder(Integer meta) {
        this(TNTType.getfromint(meta), 1);
    }

    public CustomItemGunpowder(Integer meta, int count) {
        this(TNTType.getfromint(meta), count);
    }

    public CustomItemGunpowder(TNTType meta) {
        this(meta, 1);
    }

    public CustomItemGunpowder(TNTType meta, int count) {
        super(289, meta.ordinal(), count, "Gunpowder");
        setCustomName(TextFormat.RED + TNTType.getfromint(getDamage()).getName() + TextFormat.YELLOW + "Gunpowder");
//        name = TextFormat.RED + TNTType.getfromint(getDamage()).getName() + TextFormat.YELLOW + "Gunpowder";
    }

    public enum TNTType {
        Normal,
        Silent,
        Upgraded,
        Super,
        Experimental;

        public static TNTType getfromint(int i) {
            if (Normal.ordinal() == i) return Normal;
            if (Silent.ordinal() == i) return Silent;
            if (Upgraded.ordinal() == i) return Upgraded;
            if (Super.ordinal() == i) return Super;
            if (Experimental.ordinal() == i) return Experimental;
            return Normal;
        }

        public String getName() {
            switch (this) {
                case Normal:
                    return "Normal";
                case Silent:
                    return "Silent";
                case Upgraded:
                    return "Upgraded";
                case Super:
                    return "Super";
                case Experimental:
                    return "Experimental";
                default:
                    return "Unknown Type";
            }
        }
    }
}
