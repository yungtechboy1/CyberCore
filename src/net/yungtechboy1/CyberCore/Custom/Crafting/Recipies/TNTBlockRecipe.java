package net.yungtechboy1.CyberCore.Custom.Crafting.Recipies;

import cn.nukkit.block.BlockID;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.ShapedRecipe;
import cn.nukkit.item.Item;
import io.netty.util.collection.CharObjectHashMap;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockTNT;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemTNT;

import java.util.ArrayList;

import static net.yungtechboy1.CyberCore.Custom.Crafting.Recipies.GunpowderRecipe.*;

public class TNTBlockRecipe extends CustomRecipe {
    //Array Fill
    //https://stackoverflow.com/a/5600690/3884636
    public static CustomItemTNT TNTBasic = new CustomItemTNT(CustomBlockTNT.TNTType.Basic);
    public static CustomItemTNT TNTSilent = new CustomItemTNT(CustomBlockTNT.TNTType.Silent);
    public static CustomItemTNT TNTUpgraded = new CustomItemTNT(CustomBlockTNT.TNTType.Upgraded);
    public static CustomItemTNT TNTSuper = new CustomItemTNT(CustomBlockTNT.TNTType.Super);
    public static CustomItemTNT TNTExperimental = new CustomItemTNT(CustomBlockTNT.TNTType.Experimental);

    public TNTBlockRecipe() {
        Recipies.add(TNTBasic());
        Recipies.add(TNTSilent());
//        Recipies.add(TNTBasic2());
        Recipies.add(TNTUpgraded());
        Recipies.add(TNTSuper());
        Recipies.add(TNTExperimental());

//        CustomItemTNT.TNTType.
    }


    public Recipe TNTBasic() {
        ShapedRecipe nsr = new ShapedRecipe(TNTBasic, new String[]{"SWS", "WWW", "SWS"}, new CharObjectHashMap<Item>() {{
            put("W".charAt(0), GP1);
            put("S".charAt(0), Item.get(BlockID.SAND));
        }}, new ArrayList<Item>() {{
            add(TNTBasic);
        }});
        return nsr;
    }
//    public Recipe TNTBasic2() {
//        ShapedRecipe nsr = new ShapedRecipe(TNTBasic, new String[]{"W", "S", "W"}, new CharObjectHashMap<Item>() {{
//            put("W".charAt(0), GP1);
//            put("S".charAt(0), Item.get(BlockID.SAND));
//        }}, new ArrayList<Item>() {{
//            add(TNTBasic);
//        }});
//        return nsr;
//    }

    public Recipe TNTSilent() {
        ShapedRecipe nsr = new ShapedRecipe(TNTSilent, new String[]{"WWW", "WTW", "WWW"}, new CharObjectHashMap<Item>() {{
            put("W".charAt(0), Item.get(BlockID.COBWEB));
            put("T".charAt(0), TNTBasic);
        }}, new ArrayList<Item>() {{
            add(TNTSilent);
        }});
        return nsr;
    }


    public Recipe TNTUpgraded() {
        return new ShapedRecipe(TNTUpgraded, new String[]{"GSG", "GTG", "GSG"}, new CharObjectHashMap<Item>() {{
            put("G".charAt(0), GP3);
            put("T".charAt(0), TNTBasic);
            put("S".charAt(0), TNTSilent);
        }}, new ArrayList<Item>() {{
            add(TNTUpgraded);
        }});
    }

    public Recipe TNTSuper() {
        return new ShapedRecipe(TNTSuper, new String[]{"BBB", "UUU", "BBB"}, new CharObjectHashMap<Item>() {{
            put("B".charAt(0), TNTBasic);
            put("U".charAt(0), TNTUpgraded);
        }}, new ArrayList<Item>() {{
            add(TNTSuper);
        }});
    }

    public Recipe TNTExperimental() {
        return new ShapedRecipe(TNTExperimental, new String[]{"BBB", "USU", "BBB"}, new CharObjectHashMap<Item>() {{
            put("B".charAt(0), GP5);
            put("U".charAt(0), TNTUpgraded);
            put("S".charAt(0), TNTSuper);
        }}, new ArrayList<Item>() {{
            add(TNTExperimental);
        }});
    }
}
