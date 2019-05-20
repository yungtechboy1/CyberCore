package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.Player;
import cn.nukkit.inventory.BigCraftingGrid;
import cn.nukkit.inventory.CraftingRecipe;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.CraftingTakeResultAction;
import cn.nukkit.inventory.transaction.action.CraftingTransferMaterialAction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.scheduler.Task;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.Arrays;
import java.util.Iterator;
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
        for (var6 = 0; var6 < var5; ++var6) {
            a = var4[var6];
            Arrays.fill(a, air);
        }

        this.secondaryOutputs = new Item[this.gridSize][this.gridSize];
        var4 = this.secondaryOutputs;
        var5 = var4.length;

        for (var6 = 0; var6 < var5; ++var6) {
            a = var4[var6];
            Arrays.fill(a, air);
        }

        this.init2(source, actions);
    }

    protected void init2(Player source, List<InventoryAction> actions) {
//        this.creationTime = System.currentTimeMillis();
        this.source = source;
        Iterator var3 = actions.iterator();

        while (var3.hasNext()) {
            InventoryAction action = (InventoryAction) var3.next();
//            if ( action instanceof CraftingTransferMaterialAction) {
//                CraftingTransferMaterialAction a = (CraftingTransferMaterialAction)action;
//                if (a.getSourceItem().isNull()) {
//                    setInput(a.slot, a.targetItem);
//                } else {
//                    if (!this.targetItem.isNull()) {
//                        throw new RuntimeException("Invalid " + this.getClass().getName() + ", either source or target item must be air, got source: " + this.sourceItem + ", target: " + this.targetItem);
//                    }
//
//                    ((CraftingTransaction)transaction).setExtraOutput(this.slot, this.sourceItem);
//                }
//            }else
//            if (action instanceof CraftingTakeResultAction || action instanceof CraftingTransferMaterialAction) {
//                setPrimaryOutput((action).getSourceItem());
//            } else {
            this.addAction(action);
//            }
        }

    }

    @Override
    public void addAction(InventoryAction action) {
        if (!this.actions.contains(action)) {
            this.actions.add(action);
            if (action instanceof CraftingTakeResultAction || action instanceof CraftingTransferMaterialAction) {
                setPrimaryOutput(action.getSourceItem());
            } else {
                action.onAddToTransaction(this);
            }
        } else {
            throw new RuntimeException("Tried to add the same action to a transaction twice");
        }
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
        for (y = 0; y < this.inputs.length; ++y) {
            Item[] row = this.inputs[y];

            for (int x = 0; x < row.length; ++x) {
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

            for (int i = 0; my <= yMax; ++i) {
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

    public boolean preexec() {
        if (!this.hasExecuted() && this.canExecute()) {
            System.out.println("CALL 1");
            if (!this.callExecuteEvent()) {
                System.out.println("CALL 1.1");
                this.sendInventories();
                return true;
            } else {
                System.out.println("CALL 1.2");
                Iterator var1 = this.actions.iterator();

                InventoryAction action;
                do {
                    if (!var1.hasNext()) {
                        var1 = this.actions.iterator();

                        while (var1.hasNext()) {
                            action = (InventoryAction) var1.next();
                            if (action.execute(this.source)) {
                                action.onExecuteSuccess(this.source);
                            } else {
                                action.onExecuteFail(this.source);
                            }
                        }

                        this.hasExecuted = true;

                        System.out.println("CALL 1.3");
                        return true;
                    }

                    System.out.println("CALL 1.4");

                    action = (InventoryAction) var1.next();
                } while (action.onPreExecute(this.source));

                System.out.println("CALL 1.5");
                this.sendInventories();
                return true;
            }
        } else {
            System.out.println("CALL 2");
            if (this.hasExecuted()) System.out.println("CALL 2.1");
            if (!this.canExecute()) System.out.println("CALL 2.2");
            this.sendInventories();
            return false;
        }
    }

    @Override
    public boolean execute() {
        if (preexec()) {
            switch (this.primaryOutput.getId()) {
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
