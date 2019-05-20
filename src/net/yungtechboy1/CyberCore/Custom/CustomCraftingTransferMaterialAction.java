package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.Player;
import cn.nukkit.inventory.transaction.CraftingTransaction;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;

public class CustomCraftingTransferMaterialAction  extends InventoryAction {
    public int slot;

    public CustomCraftingTransferMaterialAction(Item sourceItem, Item targetItem, int slot) {
        super(sourceItem, targetItem);
        this.slot = slot;
    }

    public void onAddToTransaction(InventoryTransaction transaction) {
        if (transaction instanceof CraftingTransaction) {
            if (this.sourceItem.isNull()) {
                ((CraftingTransaction)transaction).setInput(this.slot, this.targetItem);
            } else {
                if (!this.targetItem.isNull()) {
                    throw new RuntimeException("Invalid " + this.getClass().getName() + ", either source or target item must be air, got source: " + this.sourceItem + ", target: " + this.targetItem);
                }

                ((CraftingTransaction)transaction).setExtraOutput(this.slot, this.sourceItem);
            }

        } else if(transaction instanceof CustomCraftingTransaction){
            if (this.sourceItem.isNull()) {
                ((CustomCraftingTransaction)transaction).setInput(this.slot, this.targetItem);
            } else {
                if (!this.targetItem.isNull()) {
                    throw new RuntimeException("Invalid " + this.getClass().getName() + ", either source or target item must be air, got source: " + this.sourceItem + ", target: " + this.targetItem);
                }

                ((CustomCraftingTransaction)transaction).setExtraOutput(this.slot, this.sourceItem);
            }
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
