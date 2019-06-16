package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.utils.TextFormat;

public class CustomWoolBlockTerra extends CustomBlockPurpleGlazedTerraCotta {

    public CustomWoolBlockTerra(CustomWoolType meta) {
        super(meta.ordinal());
    }

    public CustomWoolBlockTerra(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return  CustomWoolType.getFromInt(getDamage()).getColor()+CustomWoolType.getFromInt(getDamage()).name();
    }

    public enum CustomWoolType {
        Silk_Wool,
        Premium_Silk_Wool,
        Iron_Silk_Wool;

        public static CustomWoolType getFromInt(int i) {
            switch (i) {
                case 0:
                    return Silk_Wool;
                case 1:
                    return Premium_Silk_Wool;
                case 2:
                    return Iron_Silk_Wool;
                default:
                    return Silk_Wool;
            }
        }

        public TextFormat getColor() {
            switch (this) {
                case Silk_Wool:
                    return TextFormat.YELLOW;
                case Iron_Silk_Wool:
                    return TextFormat.GRAY;
                case Premium_Silk_Wool:
                    return TextFormat.AQUA;
                default:
                    return TextFormat.YELLOW;
            }
        }
    }
}
