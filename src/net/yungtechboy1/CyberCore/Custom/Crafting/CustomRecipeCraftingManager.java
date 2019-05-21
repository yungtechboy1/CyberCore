package net.yungtechboy1.CyberCore.Custom.Crafting;

import cn.nukkit.Server;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.MainLogger;
import cn.nukkit.utils.Utils;
import net.yungtechboy1.CyberCore.Custom.Crafting.Recipies.GunpowderRecipe;
import net.yungtechboy1.CyberCore.Custom.Crafting.Recipies.TNTBlockRecipe;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomRecipeCraftingManager {
    CyberCoreMain CCM = null;
    public CustomRecipeCraftingManager(CyberCoreMain ccm){
        CCM = ccm;
        Recipies.addAll(new TNTBlockRecipe().Recipies);
        Recipies.addAll(new GunpowderRecipe().Recipies);
        RegisterCustomCraftingRecipies();
    }

    public ArrayList<Recipe> Recipies = new ArrayList<>();
    public void RegisterCustomCraftingRecipies(){
        for(Recipe r: Recipies){
            CCM.CraftingManager.registerRecipe(r);
            int q = Item.getCreativeItemIndex(r.getResult());
            if(q == -1)Item.addCreativeItem(r.getResult());
        }
    }
}
