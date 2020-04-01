package net.yungtechboy1.CyberCore.Custom.Crafting.Recipies;

import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.ShapelessRecipe;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemGunpowder;

import java.util.Collections;

public class GunpowderRecipe extends CustomRecipe {
    //Array Fill
    //https://stackoverflow.com/a/5600690/3884636
    public static CustomItemGunpowder GP1 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_1);
    public static CustomItemGunpowder GP2 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_2);
    public static CustomItemGunpowder GP3 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_3);
    public static CustomItemGunpowder GP4 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_4);
    public static CustomItemGunpowder GP5 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_5);
    public static CustomItemGunpowder CGP1 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_1_Compacted);
    public static CustomItemGunpowder CGP2 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_2_Compacted);
    public static CustomItemGunpowder CGP3 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_3_Compacted);
    public static CustomItemGunpowder CGP4 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_4_Compacted);
    public static CustomItemGunpowder CGP5 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_5_Compacted);

    public GunpowderRecipe() {
        Recipies.add(GunPowderLvl1ToLvl2());
        Recipies.add(GunPowderLvl1ToLvl3());
        Recipies.add(GunPowderLvl1ToLvl4());
        Recipies.add(GunPowderLvl2ToLvl3());
        Recipies.add(GunPowderLvl2ToLvl4());
        Recipies.add(GunPowderLvl2ToLvl5());
        Recipies.add(GunPowderLvl3ToLvl4());
        Recipies.add(GunPowderLvl3ToLvl5());
        Recipies.add(GunPowderLvl4ToLvl5());
        Recipies.add(CompactedGunPowderLvl1());
        Recipies.add(CompactedGunPowderLvl2());
        Recipies.add(CompactedGunPowderLvl3());
        Recipies.add(CompactedGunPowderLvl4());
        Recipies.add(CompactedGunPowderLvl5());
    }

    public Recipe GunPowderLvl1ToLvl2() {
        return new ShapelessRecipe(GP2, Collections.nCopies(3, GP1));
    }

    public Recipe GunPowderLvl1ToLvl3() {
        return new ShapelessRecipe(GP3, Collections.nCopies(6, GP1));
    }

    public Recipe GunPowderLvl1ToLvl4() {
        return new ShapelessRecipe(GP4, Collections.nCopies(9, GP1));
    }

    public Recipe GunPowderLvl2ToLvl3() {
        return new ShapelessRecipe(GP3, Collections.nCopies(3, GP2));
    }

    public Recipe GunPowderLvl2ToLvl4() {
        return new ShapelessRecipe(GP4, Collections.nCopies(6, GP2));
    }

    public Recipe GunPowderLvl2ToLvl5() {
        return new ShapelessRecipe(GP5, Collections.nCopies(9, GP2));
    }

    public Recipe GunPowderLvl3ToLvl4() {
        return new ShapelessRecipe(GP4, Collections.nCopies(3, GP3));
    }

    public Recipe GunPowderLvl3ToLvl5() {
        return new ShapelessRecipe(GP4, Collections.nCopies(6, GP3));
    }

    public Recipe GunPowderLvl4ToLvl5() {
        return new ShapelessRecipe(GP5, Collections.nCopies(3, GP4));
    }

    public Recipe CompactedGunPowderLvl1() {
        return new ShapelessRecipe(CGP1, Collections.nCopies(9, GP1));
    }

    public Recipe CompactedGunPowderLvl2() {
        return new ShapelessRecipe(CGP2, Collections.nCopies(9, GP2));
    }

    public Recipe CompactedGunPowderLvl3() {
        return new ShapelessRecipe(CGP3, Collections.nCopies(9, GP3));
    }

    public Recipe CompactedGunPowderLvl4() {
        return new ShapelessRecipe(CGP4, Collections.nCopies(9, GP4));
    }

    public Recipe CompactedGunPowderLvl5() {
        return new ShapelessRecipe(CGP5, Collections.nCopies(9, GP5));
    }


}
