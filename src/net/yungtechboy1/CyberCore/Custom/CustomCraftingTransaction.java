package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.Player;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.inventory.BigCraftingGrid;
import cn.nukkit.inventory.CraftingRecipe;
import cn.nukkit.inventory.transaction.CraftingTransaction;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.scheduler.Task;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.Arrays;
import java.util.List;

public class CustomCraftingTransaction extends InventoryTransaction {
    protected int gridSize;
    protected Item[][] inputs;
    protected Item[][] secondaryOutputs;
    protected Item primaryOutput;
    protected CraftingRecipe recipe;

    public CustomCraftingTransaction(Player source, List<InventoryAction> actions) {
        super(source, actions, false);
        this.gridSize = source.getCraftingGrid() instanceof BigCraftingGrid ? 3 : 2;
        Item air = Item.get(0, 0, 1);
        this.inputs = new Item[this.gridSize][this.gridSize];
        Item[][] var4 = this.inputs;
        int var5 = var4.length;

        int var6;
        Item[] a;
        for(var6 = 0; var6 < var5; ++var6) {
            a = var4[var6];
            Arrays.fill(a, air);
        }

        this.secondaryOutputs = new Item[this.gridSize][this.gridSize];
        var4 = this.secondaryOutputs;
        var5 = var4.length;

        for(var6 = 0; var6 < var5; ++var6) {
            a = var4[var6];
            Arrays.fill(a, air);
        }

        this.init(source, actions);
    }

    public void setInput(int index, Item item) {
        int y = index / this.gridSize;
        int x = index % this.gridSize;
        if (this.inputs[y][x].isNull()) {
            this.inputs[y][x] = item.clone();
        } else if (!this.inputs[y][x].equals(item)) {
            throw new RuntimeException("Input " + index + " has already been set and does not match the current item (expected " + this.inputs[y][x] + ", got " + item + ")");
        }

    }

    public Item[][] getInputMap() {
        return this.inputs;
    }

    public void setExtraOutput(int index, Item item) {
        int y = index / this.gridSize;
        int x = index % this.gridSize;
        if (this.secondaryOutputs[y][x].isNull()) {
            this.secondaryOutputs[y][x] = item.clone();
        } else if (!this.secondaryOutputs[y][x].equals(item)) {
            throw new RuntimeException("Output " + index + " has already been set and does not match the current item (expected " + this.secondaryOutputs[y][x] + ", got " + item + ")");
        }

    }

    public Item getPrimaryOutput() {
        return this.primaryOutput;
    }

    public void setPrimaryOutput(Item item) {
        if (this.primaryOutput == null) {
            this.primaryOutput = item.clone();
        } else if (!this.primaryOutput.equals(item)) {
            throw new RuntimeException("Primary result item has already been set and does not match the current item (expected " + this.primaryOutput + ", got " + item + ")");
        }

    }

    public CraftingRecipe getRecipe() {
        return this.recipe;
    }

    private Item[][] reindexInputs() {
        int xMin = this.gridSize - 1;
        int yMin = this.gridSize - 1;
        int xMax = 0;
        int yMax = 0;

        int y;
        for(y = 0; y < this.inputs.length; ++y) {
            Item[] row = this.inputs[y];

            for(int x = 0; x < row.length; ++x) {
                Item item = row[x];
                if (!item.isNull()) {
                    xMin = Math.min(x, xMin);
                    yMin = Math.min(y, yMin);
                    xMax = Math.max(x, xMax);
                    yMax = Math.max(y, yMax);
                }
            }
        }

        y = yMax - yMin + 1;
        int width = xMax - xMin + 1;
        if (y >= 1 && width >= 1) {
            Item[][] reindexed = new Item[y][width];
            int my = yMin;

            for(int i = 0; my <= yMax; ++i) {
                System.arraycopy(this.inputs[my], xMin, reindexed[i], 0, width);
                ++my;
            }

            return reindexed;
        } else {
            return new Item[0][];
        }
    }

    public boolean canExecute() {
        Item[][] inputs = this.reindexInputs();
        this.recipe = CyberCoreMain.getInstance().CraftingManager.matchRecipe(inputs, this.primaryOutput, this.secondaryOutputs);
        return this.recipe != null && super.canExecute();
    }

    protected boolean callExecuteEvent() {
        CustomCraftItemEvent ev;
        this.source.getServer().getPluginManager().callEvent(ev = new CustomCraftItemEvent(this));
        return !ev.isCancelled();
    }

    protected void sendInventories() {
        super.sendInventories();
        final ContainerClosePacket pk = new ContainerClosePacket();
        pk.windowId = -1;
        this.source.getServer().getScheduler().scheduleDelayedTask(new Task() {
            public void onRun(int currentTick) {
                CustomCraftingTransaction.this.source.dataPacket(pk);
            }
        }, 20);
        this.source.resetCraftingGridType();
    }

    public boolean execute() {
        if (super.execute()) {
            switch(this.primaryOutput.getId()) {
                case 58:
                    this.source.awardAchievement("buildWorkBench");
                    break;
                case 61:
                    this.source.awardAchievement("buildFurnace");
                    break;
                case 257:
                case 274:
                case 278:
                case 285:
                    this.source.awardAchievement("buildBetterPickaxe");
                    break;
                case 264:
                    this.source.awardAchievement("diamond");
                    break;
                case 268:
                    this.source.awardAchievement("buildSword");
                    break;
                case 270:
                    this.source.awardAchievement("buildPickaxe");
                    break;
                case 290:
                    this.source.awardAchievement("buildHoe");
                    break;
                case 297:
                    this.source.awardAchievement("makeBread");
                    break;
                case 354:
                    this.source.awardAchievement("bakeCake");
            }

            return true;
        } else {
            return false;
        }
    }
}
