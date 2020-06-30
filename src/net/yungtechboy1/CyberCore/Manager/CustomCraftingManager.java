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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CustomCraftingManager {
    private static final Logger log = LogManager.getLogger(CraftingManager.class);
    public final Collection<Recipe> recipes = new ArrayDeque();
    public static BatchPacket packet = null;
    protected final Map<Integer, Map<UUID, ShapedRecipe>> shapedRecipes = new Int2ObjectOpenHashMap();
    public final Map<Integer, FurnaceRecipe> furnaceRecipes = new Int2ObjectOpenHashMap();
    public final Map<Integer, BrewingRecipe> brewingRecipes = new Int2ObjectOpenHashMap();
    public final Map<Integer, ContainerRecipe> containerRecipes = new Int2ObjectOpenHashMap();
    private static int RECIPE_COUNT = 0;
    protected final Map<Integer, Map<UUID, ShapelessRecipe>> shapelessRecipes = new Int2ObjectOpenHashMap();
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
        InputStream recipesStream = Server.class.getClassLoader().getResourceAsStream("recipes.json");
        if (recipesStream == null) {
            throw new AssertionError("Unable to find recipes.json");
        } else {
            Config recipesConfig = new Config(1);
            recipesConfig.load(recipesStream);
            this.loadRecipes(recipesConfig);
            String path = Server.getInstance().getDataPath() + "custom_recipes.json";
            File filePath = new File(path);
            if (filePath.exists()) {
                Config customRecipes = new Config(filePath, 1);
                this.loadRecipes(customRecipes);
            }

            this.rebuildPacket();
            MainLogger.getLogger().info("Loaded " + this.recipes.size() + " recipes.");
        }
    }

    private void loadRecipes(Config config) {
        List<Map> recipes = config.getMapList("recipes");
        MainLogger.getLogger().info("Loading recipes...");
        Iterator var3 = recipes.iterator();

        while(var3.hasNext()) {
            Map recipe = (Map)var3.next();

            try {
                String craftingBlock;
                List outputs;
                Map first;
                String recipeId;
                int priority;
                switch(Utils.toInt(recipe.get("type"))) {
                    case 0:
                        craftingBlock = (String)recipe.get("block");
                        if (!"crafting_table".equals(craftingBlock)) {
                            break;
                        }

                        outputs = (List)recipe.get("output");
                        if (outputs.size() > 1) {
                            break;
                        }

                        first = (Map)outputs.get(0);
                        List<Item> sorted = new ArrayList();
                        Iterator var31 = ((List)recipe.get("input")).iterator();

                        while(var31.hasNext()) {
                            Map<String, Object> ingredient = (Map)var31.next();
                            sorted.add(Item.fromJson(ingredient));
                        }

                        sorted.sort(recipeComparator);
                        recipeId = (String)recipe.get("id");
                        priority = Utils.toInt(recipe.get("priority"));
                        ShapelessRecipe result = new ShapelessRecipe(recipeId, priority, Item.fromJson(first), sorted);
                        this.registerRecipe(result);
                        break;
                    case 1:
                        craftingBlock = (String)recipe.get("block");
                        if (!"crafting_table".equals(craftingBlock)) {
                            break;
                        }

                        outputs = (List)recipe.get("output");
                        first = (Map)outputs.remove(0);
                        String[] shape = (String[])((List)recipe.get("shape")).toArray(new String[0]);
                        Map<Character, Item> ingredients = new CharObjectHashMap();
                        List<Item> extraResults = new ArrayList();
                        Map<String, Map<String, Object>> input = (Map)recipe.get("input");
                        Iterator var34 = input.entrySet().iterator();

                        while(var34.hasNext()) {
                            java.util.Map.Entry<String, Map<String, Object>> ingredientEntry = (java.util.Map.Entry)var34.next();
                            char ingredientChar = ((String)ingredientEntry.getKey()).charAt(0);
                            Item ingredient = Item.fromJson((Map)ingredientEntry.getValue());
                            ingredients.put(ingredientChar, ingredient);
                        }

                        var34 = outputs.iterator();

                        while(var34.hasNext()) {
                            Map<String, Object> data = (Map)var34.next();
                            extraResults.add(Item.fromJson(data));
                        }

                        recipeId = (String)recipe.get("id");
                        priority = Utils.toInt(recipe.get("priority"));
                        this.registerRecipe(new ShapedRecipe(recipeId, priority, Item.fromJson(first), shape, ingredients, extraResults));
                        break;
                    case 2:
                    case 3:
                        craftingBlock = (String)recipe.get("block");
                        if ("furnace".equals(craftingBlock)) {
                            Map<String, Object> resultMap = (Map)recipe.get("output");
                            Item resultItem = Item.fromJson(resultMap);

                            Item inputItem;
                            try {
                                Map<String, Object> inputMap = (Map)recipe.get("input");
                                inputItem = Item.fromJson(inputMap);
                            } catch (Exception var20) {
                                inputItem = Item.get(Utils.toInt(recipe.get("inputId")), recipe.containsKey("inputDamage") ? Utils.toInt(recipe.get("inputDamage")) : -1, 1);
                            }

                            this.registerRecipe(new FurnaceRecipe(resultItem, inputItem));
                        }
                }
            } catch (Exception var21) {
                MainLogger.getLogger().error("Exception during registering recipe", var21);
            }
        }

        List<Map> potionMixes = config.getMapList("potionMixes");
        Iterator var23 = potionMixes.iterator();

        int fromItemId;
        int ingredient;
        while(var23.hasNext()) {
            Map potionMix = (Map)var23.next();
            int fromPotionId = ((Number)potionMix.get("fromPotionId")).intValue();
            fromItemId = ((Number)potionMix.get("ingredient")).intValue();
            ingredient = ((Number)potionMix.get("toPotionId")).intValue();
            this.registerBrewingRecipe(new BrewingRecipe(Item.get(373, fromPotionId), Item.get(fromItemId), Item.get(373, ingredient)));
        }

        List<Map> containerMixes = config.getMapList("containerMixes");
        Iterator var26 = containerMixes.iterator();

        while(var26.hasNext()) {
            Map containerMix = (Map)var26.next();
            fromItemId = ((Number)containerMix.get("fromItemId")).intValue();
            ingredient = ((Number)containerMix.get("ingredient")).intValue();
            int toItemId = ((Number)containerMix.get("toItemId")).intValue();
            this.registerContainerRecipe(new ContainerRecipe(Item.get(fromItemId), Item.get(ingredient), Item.get(toItemId)));
        }

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

        var2 = this.brewingRecipes.values().iterator();

        while(var2.hasNext()) {
            BrewingRecipe recipe = (BrewingRecipe)var2.next();
            pk.addBrewingRecipe(new BrewingRecipe[]{recipe});
        }

        var2 = this.containerRecipes.values().iterator();

        while(var2.hasNext()) {
            ContainerRecipe recipe = (ContainerRecipe)var2.next();
            pk.addContainerRecipe(new ContainerRecipe[]{recipe});
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

    private static UUID getMultiItemHash(Collection<Item> items) {
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

    private static int getItemHash(Item item) {
        return getItemHash(item.getId(), item.getDamage());
    }

    private static int getItemHash(int id, int meta) {
        return id << 4 | meta & 15;
    }

    public void registerShapedRecipe(ShapedRecipe recipe) {
//        int resultHash = getItemHash(recipe.getResult());
//        Map<UUID, ShapedRecipe> map = (Map)this.shapedRecipes.computeIfAbsent(resultHash, (k) -> {
//            return new HashMap();
//        });
//        List<Item> inputList = new LinkedList(recipe.getIngredientsAggregate());
//        map.put(getMultiItemHash(inputList), recipe);
    }
//
    public void registerRecipe(Recipe recipe) {
//        if (recipe instanceof CraftingRecipe) {
//            UUID id = Utils.dataToUUID(new String[]{String.valueOf(++RECIPE_COUNT), String.valueOf(recipe.getResult().getId()), String.valueOf(recipe.getResult().getDamage()), String.valueOf(recipe.getResult().getCount()), Arrays.toString(recipe.getResult().getCompoundTag())});
//            ((CraftingRecipe)recipe).setId(id);
//            this.recipes.add(recipe);
//        }
//
//        recipe.registerToCraftingManager(this);
    }

    public void registerShapelessRecipe(ShapelessRecipe recipe) {
        List<Item> list = recipe.getIngredientsAggregate();
        UUID hash = getMultiItemHash(list);
        int resultHash = getItemHash(recipe.getResult());
        Map<UUID, ShapelessRecipe> map = (Map)this.shapelessRecipes.computeIfAbsent(resultHash, (k) -> {
            return new HashMap();
        });
        map.put(hash, recipe);
    }

    private static int getPotionHash(int ingredientId, int potionType) {
        return ingredientId << 6 | potionType;
    }

    private static int getContainerHash(int ingredientId, int containerId) {
        return ingredientId << 9 | containerId;
    }

    public void registerBrewingRecipe(BrewingRecipe recipe) {
        Item input = recipe.getIngredient();
        Item potion = recipe.getInput();
        this.brewingRecipes.put(getPotionHash(input.getId(), potion.getDamage()), recipe);
    }

    public void registerContainerRecipe(ContainerRecipe recipe) {
        Item input = recipe.getIngredient();
        Item potion = recipe.getInput();
        this.containerRecipes.put(getContainerHash(input.getId(), potion.getId()), recipe);
    }

    public BrewingRecipe matchBrewingRecipe(Item input, Item potion) {
        int id = potion.getId();
        return id != 373 && id != 438 && id != 441 ? null : (BrewingRecipe)this.brewingRecipes.get(getPotionHash(input.getId(), potion.getDamage()));
    }

    public ContainerRecipe matchContainerRecipe(Item input, Item potion) {
        return (ContainerRecipe)this.containerRecipes.get(getContainerHash(input.getId(), potion.getId()));
    }

    public CraftingRecipe matchRecipe(List<Item> inputList, Item primaryOutput, List<Item> extraOutputList) {
        int outputHash = getItemHash(primaryOutput);
        UUID inputHash;
        Map recipes;
        Iterator var8;
        if (this.shapedRecipes.containsKey(outputHash)) {
            inputList.sort(recipeComparator);
            inputHash = getMultiItemHash(inputList);
            recipes = (Map)this.shapedRecipes.get(outputHash);
            if (recipes != null) {
                label85: {
                    ShapedRecipe recipe = (ShapedRecipe)recipes.get(inputHash);
                    if (recipe != null && (recipe.matchItems(inputList, extraOutputList) || this.matchItemsAccumulation(recipe, inputList, primaryOutput, extraOutputList))) {
                        return recipe;
                    }

                    var8 = recipes.values().iterator();

                    ShapedRecipe shapedRecipe;
                    do {
                        if (!var8.hasNext()) {
                            break label85;
                        }

                        shapedRecipe = (ShapedRecipe)var8.next();
                    } while(!shapedRecipe.matchItems(inputList, extraOutputList) && !this.matchItemsAccumulation(shapedRecipe, inputList, primaryOutput, extraOutputList));

                    return shapedRecipe;
                }
            }
        }

        if (this.shapelessRecipes.containsKey(outputHash)) {
            inputList.sort(recipeComparator);
            inputHash = getMultiItemHash(inputList);
            recipes = (Map)this.shapelessRecipes.get(outputHash);
            if (recipes == null) {
                return null;
            } else {
                ShapelessRecipe recipe = (ShapelessRecipe)recipes.get(inputHash);
                if (recipe != null && (recipe.matchItems(inputList, extraOutputList) || this.matchItemsAccumulation(recipe, inputList, primaryOutput, extraOutputList))) {
                    return recipe;
                } else {
                    var8 = recipes.values().iterator();

                    ShapelessRecipe shapelessRecipe;
                    do {
                        if (!var8.hasNext()) {
                            return null;
                        }

                        shapelessRecipe = (ShapelessRecipe)var8.next();
                    } while(!shapelessRecipe.matchItems(inputList, extraOutputList) && !this.matchItemsAccumulation(shapelessRecipe, inputList, primaryOutput, extraOutputList));

                    return shapelessRecipe;
                }
            }
        } else {
            return null;
        }
    }

    private boolean matchItemsAccumulation(CraftingRecipe recipe, List<Item> inputList, Item primaryOutput, List<Item> extraOutputList) {
        Item recipeResult = recipe.getResult();
        if (primaryOutput.equals(recipeResult, recipeResult.hasMeta(), recipeResult.hasCompoundTag()) && primaryOutput.getCount() % recipeResult.getCount() == 0) {
            int multiplier = primaryOutput.getCount() / recipeResult.getCount();
            return recipe.matchItems(inputList, extraOutputList, multiplier);
        } else {
            return false;
        }
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