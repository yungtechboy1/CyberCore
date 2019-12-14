package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.block.BlockSolidMeta;
import cn.nukkit.utils.BlockColor;
import cn.nukkit.utils.DyeColor;

public class CustomBlockWool extends BlockSolidMeta {

    public CustomBlockWool() {
        this(0);
    }

    public CustomBlockWool(int meta) {
        super(meta);
    }

    public CustomBlockWool(DyeColor dyeColor) {
        //
        this(dyeColor.getWoolData());
    }

    public String getName() {
        return this.getDyeColor().getName() + " Wool";
    }

    public int getId() {
        return 35;
    }

    public int getToolType() {
        return 5;
    }

    public double getHardness() {
        return 0.8D;
    }

    public double getResistance() {
        return 4.0D;
    }

    public int getBurnChance() {
        return 30;
    }

    public int getBurnAbility() {
        return 60;
    }

    public BlockColor getColor() {
        return DyeColor.getByWoolData(this.getDamage()).getColor();
    }

    public DyeColor getDyeColor() {
        return DyeColor.getByWoolData(this.getDamage());
    }
}
