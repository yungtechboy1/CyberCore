package net.yungtechboy1.CyberCore.Custom.Crafting.Recipies;

import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.ShapelessRecipe;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemGunpowder;

import java.util.ArrayList;
import java.util.Collections;

public class GunpowderRecipe extends CustomRecipe {
    //Array Fill
    //https://stackoverflow.com/a/5600690/3884636
    public static CustomItemGunpowder GP1 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_1);
    public static CustomItemGunpowder GP2 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_2);
    public static CustomItemGunpowder GP3 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_3);
    public static CustomItemGunpowder GP4 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_4);
    public static CustomItemGunpowder GP5 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_5);

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
}
