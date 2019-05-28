package net.yungtechboy1.CyberCore.Custom.Crafting.Recipies;

import cn.nukkit.block.BlockID;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.ShapedRecipe;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemIngotIron;
import io.netty.util.collection.CharObjectHashMap;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockPurpleGlazedTerraCotta;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockTNT;
import net.yungtechboy1.CyberCore.Custom.Block.CustomWoolBlockTerra;
import net.yungtechboy1.CyberCore.Custom.Item.CustomBlockWool;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemPurpleGlazedTerraCotta;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemString;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemTNT;

import java.util.ArrayList;

import static net.yungtechboy1.CyberCore.Custom.Crafting.Recipies.GunpowderRecipe.GP1;

public class PurpleGlazedTerraRecipe extends CustomRecipe {
    //Array Fill
    //https://stackoverflow.com/a/5600690/3884636
    private static Item Wool = Item.get(BlockID.WOOL);
    private static Item Iron = new ItemIngotIron();
    public static Item CIString = new CustomItemString(1);
    public static CustomItemPurpleGlazedTerraCotta SilkWool = new CustomItemPurpleGlazedTerraCotta(CustomWoolBlockTerra.CustomWoolType.Silk_Wool.ordinal());
    public static CustomItemPurpleGlazedTerraCotta PremSilkWool = new CustomItemPurpleGlazedTerraCotta(CustomWoolBlockTerra.CustomWoolType.Premium_Silk_Wool.ordinal());
    public static CustomItemPurpleGlazedTerraCotta IronSilk = new CustomItemPurpleGlazedTerraCotta(CustomWoolBlockTerra.CustomWoolType.Iron_Silk_Wool.ordinal());

    public PurpleGlazedTerraRecipe() {
        Recipies.add(SilkWool());
        Recipies.add(PremiumSilkWool());
        Recipies.add(IronSilk());
    }


    public Recipe SilkWool() {
        return new ShapedRecipe(SilkWool, new String[]{"WWW", "SSS", "WWW"}, new CharObjectHashMap<Item>() {{
            //TODO
            put("W".charAt(0), Wool);
            put("S".charAt(0), CIString);
        }}, new ArrayList<Item>() {{
            add(SilkWool);
        }});
    }
    public Recipe PremiumSilkWool() {
        return new ShapedRecipe(PremSilkWool, new String[]{"WWW", "WWW", "WWW"}, new CharObjectHashMap<Item>() {{
            //TODO
            put("W".charAt(0), SilkWool);
        }}, new ArrayList<Item>() {{
            add(PremSilkWool);
        }});
    }
    public Recipe IronSilk() {
        return new ShapedRecipe(IronSilk, new String[]{"III", "SPS", "III"}, new CharObjectHashMap<Item>() {{
            //TODO
            put("P".charAt(0), PremSilkWool);
            put("I".charAt(0), Iron);
            put("S".charAt(0), CIString);
        }}, new ArrayList<Item>() {{
            add(IronSilk);
        }});
    }

}
