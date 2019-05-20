package net.yungtechboy1.CyberCore.Custom.Crafting.Recipies;

import cn.nukkit.block.BlockID;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.ShapedRecipe;
import cn.nukkit.inventory.ShapelessRecipe;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import io.netty.util.collection.CharObjectHashMap;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockTNT;
import net.yungtechboy1.CyberCore.Custom.Block.CustomItemTNT;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemGunpowder;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemTNT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TNTBlockRecipe extends CustomRecipe {
        //Array Fill
        //https://stackoverflow.com/a/5600690/3884636
        private CustomItemGunpowder GP1 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_1);
        private CustomItemGunpowder GP2 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_2);
        private CustomItemGunpowder GP3 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_3);
        private CustomItemGunpowder GP4 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_4);
        private CustomItemGunpowder GP5 = new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_5);
        private CustomItemTNT TNTBasic = new CustomItemTNT(CustomBlockTNT.TNTType.Basic);
        private CustomItemTNT TNTSilent = new CustomItemTNT(CustomBlockTNT.TNTType.Silent);
        private CustomItemTNT TNTUpgraded = new CustomItemTNT(CustomBlockTNT.TNTType.Upgraded);
        private CustomItemTNT TNTSuper = new CustomItemTNT(CustomBlockTNT.TNTType.Super);
        private CustomItemTNT TNTExperimental = new CustomItemTNT(CustomBlockTNT.TNTType.Experimental);

    public TNTBlockRecipe() {
            Recipies.add(TNTBasic());
            Recipies.add(GunPowderLvl1ToLvl3());
            Recipies.add(GunPowderLvl1ToLvl4());
            Recipies.add(GunPowderLvl2ToLvl3());
            Recipies.add(GunPowderLvl2ToLvl4());
            Recipies.add(GunPowderLvl2ToLvl5());
            Recipies.add(GunPowderLvl3ToLvl4());
            Recipies.add(GunPowderLvl3ToLvl5());
            Recipies.add(GunPowderLvl4ToLvl5());

//        CustomItemTNT.TNTType.
        }


        public Recipe TNTBasic() {
            List<Item> l = Collections.nCopies(6, GP1);
            l.addAll(Collections.nCopies(3,Item.get(BlockID.SAND)));
            return new ShapelessRecipe(TNTBasic,l);
        }
        public Recipe TNTSilent() {
            ShapedRecipe nsr = new ShapedRecipe(Item.get(46), new String[]{"WWW", "WTW", "WWW"}, new CharObjectHashMap<Item>() {{
                put("A".charAt(0), new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_2));
                put("B".charAt(0), Item.get(12));
            }}, new ArrayList<Item>() {{
                add(new CustomItemTNT());
            }});
            return nsr;
        }

        public Recipe GunPowderLvl1ToLvl3() {
            return new ShapelessRecipe(GP3, Collections.nCopies(6, GP1));
        }

        public Recipe GunPowderLvl1ToLvl4() {
            return new ShapelessRecipe(GP4, Collections.nCopies(9, GP1));
        }

        public Recipe GunPowderLvl2ToLvl3() {
            ArrayList<Item> al = new ArrayList<Item>();
            return new ShapelessRecipe(GP3, Collections.nCopies(3, GP2));
        }

        public Recipe GunPowderLvl2ToLvl4() {
            ArrayList<Item> al = new ArrayList<Item>();
            return new ShapelessRecipe(GP4, Collections.nCopies(6, GP2));
        }

        public Recipe GunPowderLvl2ToLvl5() {
            ArrayList<Item> al = new ArrayList<Item>();
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
