package net.yungtechboy1.CyberCore.Manager;

        import cn.nukkit.Server;
        import cn.nukkit.inventory.*;
        import cn.nukkit.item.Item;
        import cn.nukkit.network.protocol.BatchPacket;
        import cn.nukkit.network.protocol.CraftingDataPacket;
        import cn.nukkit.utils.BinaryStream;
        import cn.nukkit.utils.Config;
        import cn.nukkit.utils.MainLogger;
        import cn.nukkit.utils.Utils;
        import io.netty.util.collection.CharObjectHashMap;
        import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayDeque;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collection;
        import java.util.Comparator;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Map;
        import java.util.UUID;

public class CustomCraftingManager {
    public final Collection<Recipe> recipes = new ArrayDeque();
    public static BatchPacket packet = null;
    public final Map<Integer, Map<UUID, ShapedRecipe>> shapedRecipes = new Int2ObjectOpenHashMap();
    public final Map<Integer, FurnaceRecipe> furnaceRecipes = new Int2ObjectOpenHashMap();
    public final Map<Integer, BrewingRecipe> brewingRecipes = new Int2ObjectOpenHashMap();
    private static int RECIPE_COUNT = 0;
    public final Map<Integer, Map<UUID, ShapelessRecipe>> shapelessRecipes = new Int2ObjectOpenHashMap();
    public static final Comparator<Item> recipeComparator = (i1, i2) -> {
        if (i1.getId() > i2.getId()) {
            return 1;
        } else if (i1.getId() < i2.getId()) {
            return -1;
        } else if (i1.getDamage() > i2.getDamage()) {
            return 1;
        } else {
            return i1.getDamage() < i2.getDamage() ? -1 : Integer.compare(i1.getCount(), i2.getCount());
        }
    };

    public CustomCraftingManager() {
        String path = Server.getInstance().getDataPath() + "recipes.json";
        if (!(new File(path)).exists()) {
            try {
                Utils.writeFile(path, Server.class.getClassLoader().getResourceAsStream("recipes.json"));
            } catch (IOException var17) {
                MainLogger.getLogger().logException(var17);
            }
        }

        List<Map> recipes = (new Config(path, 1)).getMapList("recipes");
        MainLogger.getLogger().info("Loading recipes...");
        Iterator var3 = recipes.iterator();

        while(var3.hasNext()) {
            Map recipe = (Map)var3.next();

            try {
                Map first;
                switch(Utils.toInt(recipe.get("type"))) {
                    case 0:
                        first = (Map)((List)recipe.get("output")).get(0);
                        List<Item> sorted = new ArrayList();
                        Iterator var7 = ((List)recipe.get("input")).iterator();

                        while(var7.hasNext()) {
                            Map<String, Object> ingredient = (Map)var7.next();
                            sorted.add(Item.fromJson(ingredient));
                        }

                        sorted.sort(recipeComparator);
                        ShapelessRecipe result = new ShapelessRecipe(Item.fromJson(first), sorted);
                        this.registerRecipe(result);
                        break;
                    case 1:
                        List<Map> output = (List)recipe.get("output");
                        first = (Map)output.remove(0);
                        String[] shape = (String[])((List)recipe.get("shape")).toArray(new String[0]);
                        Map<Character, Item> ingredients = new CharObjectHashMap();
                        List<Item> extraResults = new ArrayList();
                        Map<String, Map<String, Object>> input = (Map)recipe.get("input");
                        Iterator var21 = input.entrySet().iterator();

                        while(var21.hasNext()) {
                            java.util.Map.Entry<String, Map<String, Object>> ingredientEntry = (java.util.Map.Entry)var21.next();
                            char ingredientChar = ((String)ingredientEntry.getKey()).charAt(0);
                            Item ingredient = Item.fromJson((Map)ingredientEntry.getValue());
                            ingredients.put(ingredientChar, ingredient);
                        }

                        var21 = output.iterator();

                        while(var21.hasNext()) {
                            Map<String, Object> data = (Map)var21.next();
                            extraResults.add(Item.fromJson(data));
                        }

                        this.registerRecipe(new ShapedRecipe(Item.fromJson(first), shape, ingredients, extraResults));
                        break;
                    case 2:
                    case 3:
                        Map<String, Object> resultMap = (Map)recipe.get("output");
                        Item resultItem = Item.fromJson(resultMap);
                        this.registerRecipe(new FurnaceRecipe(resultItem, Item.get(Utils.toInt(recipe.get("inputId")), recipe.containsKey("inputDamage") ? Utils.toInt(recipe.get("inputDamage")) : -1, 1)));
                }
            } catch (Exception var18) {
                MainLogger.getLogger().error("Exception during registering recipe", var18);
            }
        }

        this.registerBrewing();
        this.rebuildPacket();
        MainLogger.getLogger().info("Loaded " + this.recipes.size() + " recipes.");
    }

    protected void registerBrewing() {
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 4, 1), Item.get(372, 0, 1), Item.get(373, 0, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 3, 1), Item.get(348, 0, 1), Item.get(373, 0, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 2, 1), Item.get(331, 0, 1), Item.get(373, 0, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 31, 1), Item.get(377, 0, 1), Item.get(373, 4, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 32, 1), Item.get(331, 0, 1), Item.get(373, 31, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 32, 1), Item.get(331, 0, 1), Item.get(373, 33, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 33, 1), Item.get(348, 0, 1), Item.get(373, 31, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 33, 1), Item.get(348, 0, 1), Item.get(373, 32, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 34, 1), Item.get(376, 0, 1), Item.get(373, 0, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 35, 1), Item.get(331, 0, 1), Item.get(373, 34, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 5, 1), Item.get(396, 0, 1), Item.get(373, 4, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 6, 1), Item.get(331, 0, 1), Item.get(373, 5, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 7, 1), Item.get(376, 0, 1), Item.get(373, 5, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 8, 1), Item.get(331, 0, 1), Item.get(373, 7, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 8, 1), Item.get(376, 0, 1), Item.get(373, 6, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 12, 1), Item.get(378, 0, 1), Item.get(373, 4, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 13, 1), Item.get(331, 0, 1), Item.get(373, 12, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 17, 1), Item.get(376, 0, 1), Item.get(373, 12, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 17, 1), Item.get(376, 0, 1), Item.get(373, 14, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 17, 1), Item.get(376, 0, 1), Item.get(373, 9, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 18, 1), Item.get(376, 0, 1), Item.get(373, 13, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 18, 1), Item.get(376, 0, 1), Item.get(373, 15, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 14, 1), Item.get(353, 0, 1), Item.get(373, 4, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 15, 1), Item.get(331, 0, 1), Item.get(373, 14, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 16, 1), Item.get(348, 0, 1), Item.get(373, 14, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 21, 1), Item.get(382, 0, 1), Item.get(373, 4, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 22, 1), Item.get(348, 0, 1), Item.get(373, 21, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 25, 1), Item.get(375, 0, 1), Item.get(373, 4, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 26, 1), Item.get(331, 0, 1), Item.get(373, 25, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 27, 1), Item.get(348, 0, 1), Item.get(373, 25, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 28, 1), Item.get(370, 0, 1), Item.get(373, 4, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 29, 1), Item.get(331, 0, 1), Item.get(373, 28, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 30, 1), Item.get(348, 0, 1), Item.get(373, 28, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 23, 1), Item.get(376, 0, 1), Item.get(373, 19, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 23, 1), Item.get(376, 0, 1), Item.get(373, 21, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 23, 1), Item.get(376, 0, 1), Item.get(373, 25, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 24, 1), Item.get(348, 0, 1), Item.get(373, 23, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 24, 1), Item.get(376, 0, 1), Item.get(373, 22, 1)));
        this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, 24, 1), Item.get(376, 0, 1), Item.get(373, 26, 1)));
    }

    public void rebuildPacket() {
        CraftingDataPacket pk = new CraftingDataPacket();
        pk.cleanRecipes = true;
        Iterator var2 = this.getRecipes().iterator();

        while(var2.hasNext()) {
            Recipe recipe = (Recipe)var2.next();
            if (recipe instanceof ShapedRecipe) {
                pk.addShapedRecipe(new ShapedRecipe[]{(ShapedRecipe)recipe});
            } else if (recipe instanceof ShapelessRecipe) {
                pk.addShapelessRecipe(new ShapelessRecipe[]{(ShapelessRecipe)recipe});
            }
        }

        var2 = this.getFurnaceRecipes().values().iterator();

        while(var2.hasNext()) {
            FurnaceRecipe recipe = (FurnaceRecipe)var2.next();
            pk.addFurnaceRecipe(new FurnaceRecipe[]{recipe});
        }

        pk.encode();
        packet = pk.compress(9);
    }

    public Collection<Recipe> getRecipes() {
        return this.recipes;
    }

    public Map<Integer, FurnaceRecipe> getFurnaceRecipes() {
        return this.furnaceRecipes;
    }

    public FurnaceRecipe matchFurnaceRecipe(Item input) {
        FurnaceRecipe recipe = (FurnaceRecipe)this.furnaceRecipes.get(getItemHash(input));
        if (recipe == null) {
            recipe = (FurnaceRecipe)this.furnaceRecipes.get(getItemHash(input.getId(), 0));
        }

        return recipe;
    }

    public static UUID getMultiItemHash(Collection<Item> items) {
        BinaryStream stream = new BinaryStream();
        Iterator var2 = items.iterator();

        while(var2.hasNext()) {
            Item item = (Item)var2.next();
            stream.putVarInt(getFullItemHash(item));
        }

        return UUID.nameUUIDFromBytes(stream.getBuffer());
    }

    private static int getFullItemHash(Item item) {
        return 31 * getItemHash(item) + item.getCount();
    }

    public void registerFurnaceRecipe(FurnaceRecipe recipe) {
        Item input = recipe.getInput();
        this.furnaceRecipes.put(getItemHash(input), recipe);
    }

    public static int getItemHash(Item item) {
        return getItemHash(item.getId(), item.getDamage());
    }

    private static int getItemHash(int id, int meta) {
        return id << 4 | meta & 15;
    }

    public void registerShapedRecipe(ShapedRecipe recipe) {
        int resultHash = getItemHash(recipe.getResult());
        Map<UUID, ShapedRecipe> map = this.shapedRecipes.computeIfAbsent(resultHash, (k) -> {
            return new HashMap();
        });
        map.put(getMultiItemHash(recipe.getIngredientList()), recipe);
    }

    private Item[][] cloneItemMap(Item[][] map) {
        Item[][] newMap = new Item[map.length][];

        int y;
        Item[] row;
        for(y = 0; y < newMap.length; ++y) {
            row = map[y];
            Item[] n = new Item[row.length];
            System.arraycopy(row, 0, n, 0, n.length);
            newMap[y] = n;
        }

        for(y = 0; y < newMap.length; ++y) {
            row = newMap[y];

            for(int x = 0; x < row.length; ++x) {
                Item item = newMap[y][x];
                newMap[y][x] = item.clone();
            }
        }

        return newMap;
    }

    public void registerRecipe(Recipe recipe) {
        if (recipe instanceof CraftingRecipe) {
            UUID id = Utils.dataToUUID(new String[]{String.valueOf(++RECIPE_COUNT), String.valueOf(recipe.getResult().getId()), String.valueOf(recipe.getResult().getDamage()), String.valueOf(recipe.getResult().getCount()), Arrays.toString(recipe.getResult().getCompoundTag())});
            ((CraftingRecipe)recipe).setId(id);
            this.recipes.add(recipe);
        }

        if(recipe instanceof ShapelessRecipe)registerShapelessRecipe((ShapelessRecipe)recipe);
        else if( recipe instanceof ShapedRecipe)registerShapedRecipe((ShapedRecipe)recipe);
        else if( recipe instanceof FurnaceRecipe)registerFurnaceRecipe((FurnaceRecipe)recipe);
        else if( recipe instanceof BrewingRecipe)registerBrewingRecipe((BrewingRecipe)recipe);
//
//        //TODO CHECK
//        recipe.registerToCraftingManager(this);
    }

    public void registerShapelessRecipe(ShapelessRecipe recipe) {
        List<Item> list = recipe.getIngredientList();
        list.sort(recipeComparator);
        UUID hash = getMultiItemHash(list);
        int resultHash = getItemHash(recipe.getResult());
        Map<UUID, ShapelessRecipe> map = (Map)this.shapelessRecipes.computeIfAbsent(resultHash, (k) -> {
            return new HashMap();
        });
        map.put(hash, recipe);
    }

    public void registerBrewingRecipe(BrewingRecipe recipe) {
        Item input = recipe.getInput();
        Item potion = recipe.getPotion();
        this.brewingRecipes.put(getItemHash(input.getId(), potion.getDamage()), recipe);
    }

    public BrewingRecipe matchBrewingRecipe(Item input, Item potion) {
        return (BrewingRecipe)this.brewingRecipes.get(getItemHash(input.getId(), potion.getDamage()));
    }

    public boolean matchItems(ShapedRecipe sr, Item[][] input, Item[][] output) {
        System.out.println("CHECK MATCH 5.0"+sr==null);
        if (!this.matchInputMap(sr,(Item[][])Utils.clone2dArray(input))) {
            System.out.println("5.0.5E");
            Item[][] reverse = (Item[][])Utils.clone2dArray(input);

            for(int y = 0; y < reverse.length; ++y) {
                reverse[y] = (Item[])Utils.reverseArray(reverse[y], false);
            }

            if (!this.matchInputMap(sr,reverse)) {
                System.out.println("5.1 DEEAD");
                return false;
            }
        }

        System.out.println("5.2");
        List<Item> haveItems = new ArrayList();
        Item[][] var10 = output;
        int var5 = output.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Item[] items = var10[var6];
            haveItems.addAll(Arrays.asList(items));
        }

        List<Item> needItems = sr.getExtraResults();
        Iterator var12 = (new ArrayList(haveItems)).iterator();
        Iterator var13 = (new ArrayList(needItems)).iterator();
        Iterator var14 = (new ArrayList(needItems)).iterator();

        int k = 0;

        while(true) {
            while(var12.hasNext()) {
                System.out.println("5.3");
                Item haveItem = (Item)var12.next();
                if (haveItem.isNull()) {
                    System.out.println("5.4.1");
                    haveItems.remove(haveItem);
                    k++;
                } else {

//                    System.out.println("5.4.2");
                    while(var14.hasNext()) {

                        System.out.println("5.4.2.1");
                        Item needItem = (Item)var14.next();
                        if (needItem.equals(haveItem, needItem.hasMeta(), needItem.hasCompoundTag()) && needItem.getCount() == haveItem.getCount()) {

                            System.out.println("5.4.2.2");
                            haveItems.remove(haveItem);
                            needItems.remove(needItem);
                            break;
                        }
                    }
                }

                if(k == 9)needItems.clear();


            }

            System.out.println("5.5"+haveItems.isEmpty()+"|"+needItems.isEmpty());
            return haveItems.isEmpty() && needItems.isEmpty();
        }
    }

    private boolean matchInputMap(ShapedRecipe sr,Item[][] input) {
        System.out.println("Z1"+sr);
        System.out.println("Z1"+sr.getIngredientMap());
        Map<Integer, Map<Integer, Item>> map = sr.getIngredientMap();
        System.out.println("Z2");
        int y = 0;

        int y2;
        int x;
        for(y2 = sr.getHeight(); y < y2; ++y) {
            x = 0;System.out.println("Z3");

            for(int x2 = sr.getWidth(); x < x2; ++x) {
                System.out.println("Z4");
                Item given = input[y][x];
                Item required = (Item)((Map)map.get(y)).get(x);
                if (given == null || !required.equals(given, required.hasMeta(), required.hasCompoundTag()) || required.getCount() != given.getCount()) {
                    System.out.println("5.7"+required+"||||||||||"+given);
                    return false;
                }

                System.out.println("Zz");
                input[y][x] = null;
            }
        }

        System.out.println("Zzz");
        Item[][] var11 = input;
        y2 = input.length;

        for(x = 0; x < y2; ++x) {
            Item[] items = var11[x];
            Item[] var13 = items;
            int var14 = items.length;

            for(int var9 = 0; var9 < var14; ++var9) {
                Item item = var13[var9];
                if (item != null && !item.isNull()) {

                    System.out.println("5.8");
                    return false;
                }
            }
        }

        System.out.println("5.9 ITEMS GIVEN ARE CORRECT INGREDIENTS");
        return true;
    }

    public CraftingRecipe matchRecipe(Item[][] inputMap, Item primaryOutput, Item[][] extraOutputMap) {
        int outputHash = getItemHash(primaryOutput);
        ArrayList list;
        Item[][] var6;
        int var7;
        int var8;
        Item[] a;
        UUID inputHash;
        Map recipes;
        Iterator var14;
        System.out.println("CALL 3"+primaryOutput+" | "+primaryOutput.getCustomName()+"|"+primaryOutput.getName()+"|"+primaryOutput.getId());
        System.out.println("CALL 3.0.1 >> "+inputMap+" | "+extraOutputMap.toString());
        if (this.shapedRecipes.containsKey(outputHash)) {
            System.out.println("CALL 3.1");
            list = new ArrayList();
            var6 = inputMap;
            var7 = inputMap.length;

            for(var8 = 0; var8 < var7; ++var8) {
                a = var6[var8];
                list.addAll(Arrays.asList(a));
            }

            inputHash = getMultiItemHash(list);
            recipes = (Map)this.shapedRecipes.get(outputHash);
            if (recipes != null) {
                ShapedRecipe recipe = (ShapedRecipe)recipes.get(inputHash);
                System.out.println("CALL 3.2"+recipe);
                int k = -1;
                Item[][] ii =this.cloneItemMap(inputMap);
                Item[][] iii =this.cloneItemMap(extraOutputMap);
                for(Item[] aa : ii){
                    k++;
                    for(int aaa = 0; aaa < aa.length; aaa++){
                        System.out.println(ii[k][aaa] +"||+"+iii[k][aaa] );
                    }
                }
                if (recipe != null && (recipe.matchItems(this.cloneItemMap(inputMap), this.cloneItemMap(extraOutputMap)) || recipe.matchItems(this.cloneItemMap(inputMap), new Item[0][])|| matchItems(recipe,this.cloneItemMap(inputMap), this.cloneItemMap(extraOutputMap)))){
                    System.out.println("CALL 3.3-------");
                    return recipe;
                }

                var14 = recipes.values().iterator();

                while(var14.hasNext()) {
                    ShapedRecipe shapedRecipe = (ShapedRecipe)var14.next();
                    System.out.println("CALL 3.4"+shapedRecipe.getResult().getName()+"||"+shapedRecipe.getIngredientList().toString());
                    if (shapedRecipe.matchItems(this.cloneItemMap(inputMap), this.cloneItemMap(extraOutputMap))|| shapedRecipe.matchItems(this.cloneItemMap(inputMap), new Item[0][]) || matchItems(shapedRecipe,this.cloneItemMap(inputMap), this.cloneItemMap(extraOutputMap))) {
                        System.out.println("CALL 3.5");
                        return shapedRecipe;
                    }
                }
            }
        }

        System.out.println("CALL 3.6---");

        if (this.shapelessRecipes.containsKey(outputHash)) {
            System.out.println("CALL 4.1");
            list = new ArrayList();
            var6 = inputMap;
            var7 = inputMap.length;

            for(var8 = 0; var8 < var7; ++var8) {
                a = var6[var8];
                list.addAll(Arrays.asList(a));
            }

            list.sort(recipeComparator);
            inputHash = getMultiItemHash(list);
            recipes = (Map)this.shapelessRecipes.get(outputHash);
            if (recipes == null) {
                return null;
            }


            int k = -1;
            Item[][] ii =this.cloneItemMap(inputMap);
            Item[][] iii =this.cloneItemMap(extraOutputMap);
            for(Item[] aa : ii){
                k++;
                for(int aaa = 0; aaa < aa.length; aaa++){
                    System.out.println("!!!!"+ii[k][aaa] +"||+"+iii[k][aaa] );
                }
            }

            ShapelessRecipe recipe = (ShapelessRecipe)recipes.get(inputHash);
            System.out.println("CALL 4.2"+recipe);
            if(recipe != null)System.out.println("CALL 4.2.1 ER > "+recipe.getExtraResults());
            if (recipe != null && recipe.matchItems(this.cloneItemMap(inputMap), new Item[0][])) {
                return recipe;
            }

            System.out.println("CALL 4.3");
            var14 = recipes.values().iterator();

            while(var14.hasNext()) {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe)var14.next();
                System.out.println("CALL 4.4"+shapelessRecipe.getResult().getName()+"||"+shapelessRecipe.getResult()+"||"+shapelessRecipe.getIngredientList());
                if (shapelessRecipe.matchItems(this.cloneItemMap(inputMap), new Item[0][])) {
                    return shapelessRecipe;
                }
            }
        }


//        for(Recipe r: this.recipes){
//            if(r instanceof ShapelessRecipe){
//                ShapelessRecipe slr = (ShapelessRecipe)r;
//                slr.matchItems()
//            }
//        }

        return null;
    }


    public static class Entry {
        final int resultItemId;
        final int resultMeta;
        final int ingredientItemId;
        final int ingredientMeta;
        final String recipeShape;
        final int resultAmount;

        public Entry(int resultItemId, int resultMeta, int ingredientItemId, int ingredientMeta, String recipeShape, int resultAmount) {
            this.resultItemId = resultItemId;
            this.resultMeta = resultMeta;
            this.ingredientItemId = ingredientItemId;
            this.ingredientMeta = ingredientMeta;
            this.recipeShape = recipeShape;
            this.resultAmount = resultAmount;
        }
    }
}
