package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.transaction.CraftingTransaction;
import cn.nukkit.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomCraftItemEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Item[] input = new Item[0];
    private final Recipe recipe;
    private final Player player;
    private CustomCraftingTransaction transaction;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public CustomCraftItemEvent(CustomCraftingTransaction transaction) {
        this.transaction = transaction;
        List<Item> merged = new ArrayList();
        Item[][] input = transaction.getInputMap();
        Item[][] var4 = input;
        int var5 = input.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Item[] items = var4[var6];
            merged.addAll(Arrays.asList(items));
        }

        this.player = transaction.getSource();
        this.input = (Item[])merged.toArray(new Item[0]);
        this.recipe = transaction.getRecipe();
    }

    public CustomCraftItemEvent(Player player, Item[] input, Recipe recipe) {
        this.player = player;
        this.input = input;
        this.recipe = recipe;
    }

    public CustomCraftingTransaction getTransaction() {
        return this.transaction;
    }

    public Item[] getInput() {
        return this.input;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public Player getPlayer() {
        return this.player;
    }
}

