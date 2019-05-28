package net.yungtechboy1.CyberCore.Custom.Crafting;

import cn.nukkit.inventory.Recipe;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.Crafting.Recipies.GunpowderRecipe;
import net.yungtechboy1.CyberCore.Custom.Crafting.Recipies.PurpleGlazedTerraRecipe;
import net.yungtechboy1.CyberCore.Custom.Crafting.Recipies.TNTBlockRecipe;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

public class CustomRecipeCraftingManager {
    static ArrayList<Recipe> addedtoCreative = new ArrayList<>();
    public ArrayList<Recipe> Recipies = new ArrayList<>();
    CyberCoreMain CCM = null;

    public CustomRecipeCraftingManager(CyberCoreMain ccm) {
        CCM = ccm;
        Recipies.addAll(new TNTBlockRecipe().Recipies);
        Recipies.addAll(new GunpowderRecipe().Recipies);
        Recipies.addAll(new PurpleGlazedTerraRecipe().Recipies);
        RegisterCustomCraftingRecipies();
    }

    public void RegisterCustomCraftingRecipies() {
        for (Recipe r : Recipies) {
            if (!addedtoCreative.contains(r)) {
                CCM.CraftingManager.registerRecipe(r);
                CCM.getLogger().info("Registering Recipe : " + r);
                CCM.getLogger().info("Registering Result Item : " + r.getResult());
                CCM.getLogger().info("Registering Result Item : " + r.getResult().getId());
                CCM.getLogger().info("Registering Result Item : " + r.getResult().getDamage());
                if (!addedtoCreative.contains(r.getResult())) {
                    CCM.getLogger().info(TextFormat.GREEN+"Added To Creative List!");
                    Item.addCreativeItem(r.getResult());
                    addedtoCreative.add(r);
                }
                CCM.getLogger().info("-------------------------------------");
            }
        }
    }
}
