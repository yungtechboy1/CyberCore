package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;

public class CustomItemGunpowder extends Item {

    public CustomItemGunpowder() {
        this(GunpowderType.Lvl_1, 1);
    }

    public CustomItemGunpowder(Integer meta) {
        this(GunpowderType.getfromint(meta), 1);
    }

    public CustomItemGunpowder(Integer meta, int count) {
        this(GunpowderType.getfromint(meta), count);
    }

    public CustomItemGunpowder(GunpowderType meta) {
        this(meta, 1);
    }

    public CustomItemGunpowder(GunpowderType meta, int count) {
        super(289, meta.ordinal(), count, meta.getName() +"Gunpowder");
        setCustomName();
//        name = TextFormat.RED + GunpowderType.getfromint(getDamage()).getName() + TextFormat.YELLOW + " Gunpowder";

    }


    private void setCustomName() {
        GunpowderType gpt = GunpowderType.getfromint(getDamage());
        if (!gpt.isCompacted())
            setCustomName(GunpowderType.getfromint(getDamage()).getName() + " Gunpowder");
//            setCustomName(TextFormat.RED + GunpowderType.getfromint(getDamage()).getName() + TextFormat.YELLOW + " Gunpowder");
        else
            setCustomName( GunpowderType.getfromint(getDamage()).getName() + " Compacted Gunpowder");
//            setCustomName(TextFormat.RED + GunpowderType.getfromint(getDamage()).getName() + TextFormat.YELLOW + " Compacted Gunpowder");
    }

    public boolean isCompacted() {
        return GunpowderType.getfromint(getDamage()).isCompacted();
    }


    public enum GunpowderType {
        Lvl_1,
        Lvl_2,
        Lvl_3,
        Lvl_4,
        Lvl_5,
        Lvl_1_Compacted,
        Lvl_2_Compacted,
        Lvl_3_Compacted,
        Lvl_4_Compacted,
        Lvl_5_Compacted;

        public static GunpowderType getfromint(int i) {
            if (Lvl_1.ordinal() == i) return Lvl_1;
            if (Lvl_2.ordinal() == i) return Lvl_2;
            if (Lvl_3.ordinal() == i) return Lvl_3;
            if (Lvl_4.ordinal() == i) return Lvl_4;
            if (Lvl_5.ordinal() == i) return Lvl_5;
            if (Lvl_1_Compacted.ordinal() == i) return Lvl_1_Compacted;
            if (Lvl_2_Compacted.ordinal() == i) return Lvl_2_Compacted;
            if (Lvl_3_Compacted.ordinal() == i) return Lvl_3_Compacted;
            if (Lvl_4_Compacted.ordinal() == i) return Lvl_4_Compacted;
            if (Lvl_5_Compacted.ordinal() == i) return Lvl_5_Compacted;
            return Lvl_1;
        }

        public String getName() {
            switch (this) {
                case Lvl_1:
                    return "Lvl_1";
                case Lvl_2:
                    return "Lvl_2";
                case Lvl_3:
                    return "Lvl_3";
                case Lvl_4:
                    return "Lvl_4";
                case Lvl_5:
                    return "Lvl_5";
                case Lvl_1_Compacted:
                    return "Lvl_1 Compacted";
                case Lvl_2_Compacted:
                    return "Lvl_2 Compacted";
                case Lvl_3_Compacted:
                    return "Lvl_3 Compacted";
                case Lvl_4_Compacted:
                    return "Lvl_4 Compacted";
                case Lvl_5_Compacted:
                    return "Lvl_5 Compacted";
                default:
                    return "Unknown Type";
            }
        }

        public boolean isCompacted() {
            return this.ordinal() >= Lvl_1_Compacted.ordinal();
        }

    }
}
