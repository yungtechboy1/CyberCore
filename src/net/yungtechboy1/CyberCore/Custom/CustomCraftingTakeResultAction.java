package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.Player;
import cn.nukkit.inventory.transaction.CraftingTransaction;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;

public class CustomCraftingTakeResultAction extends InventoryAction {
    public CustomCraftingTakeResultAction(Item sourceItem, Item targetItem) {
        super(sourceItem, targetItem);
    }

    public void onAddToTransaction(InventoryTransaction transaction) {
        if (transaction instanceof CraftingTransaction ) {
            ((CraftingTransaction)transaction).setPrimaryOutput(this.getSourceItem());
        } if (transaction instanceof CustomCraftingTransaction ) {
            ((CustomCraftingTransaction)transaction).setPrimaryOutput(this.getSourceItem());
        } else {
            throw new RuntimeException(this.getClass().getName() + " can only be added to CraftingTransactions");
        }
    }

    public boolean isValid(Player source) {
        return true;
    }

    public boolean execute(Player source) {
        return true;
    }

    public void onExecuteSuccess(Player $source) {
    }

    public void onExecuteFail(Player source) {
    }
}
