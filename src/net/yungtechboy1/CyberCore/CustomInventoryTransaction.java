package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.utils.MainLogger;
import net.yungtechboy1.CyberCore.Custom.CustomCraftingTakeResultAction;
import net.yungtechboy1.CyberCore.Custom.CustomCraftingTransferMaterialAction;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

public class CustomInventoryTransaction extends InventoryTransaction {
    public CustomInventoryTransaction(CorePlayer corePlayer, List<InventoryAction> actions) {
        super(corePlayer, actions,false);
        init2(corePlayer,actions);
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
            System.out.println("ADDING ACTION LIKE 444444");
            this.addAction(action);
//            }
        }

    }

    @Override
    public void addAction(InventoryAction action) {
        if (!this.actions.contains(action)) {
            this.actions.add(action);
//            System.out.println("Adding Action : " + action);
//            System.out.println("Adding Action : " + action.getClass().getName());
            if(action instanceof SlotChangeAction) {
                SlotChangeAction sca = (SlotChangeAction)action;
//                System.out.println("|||||||3..");
//                System.out.println("|||||||3"+sca.getSlot());
//                System.out.println("|||||||3"+sca.getInventory().getSize());
                addInventory(sca.getInventory());
            }else{
//                System.out.println("ERRRR");
                action.onAddToTransaction(this);
            }
        } else {
            throw new RuntimeException("Tried to add the same action to a transaction twice");
        }
        System.out.println("------");
    }


    @Override
    public boolean canExecute() {
        this.squashDuplicateSlotChanges2();
//        System.out.println("!231231231231231 "+(this.actions.size() > 0));
        return this.actions.size() > 0;
    }

    private boolean squashDuplicateSlotChanges2() {

        Map<Integer, List<SlotChangeAction>> slotChanges = new HashMap<>();
//        System.out.println("SQUASSSSHH");
        for (InventoryAction action : this.actions) {
//            System.out.println("ACTION ");
            if (action instanceof SlotChangeAction) {
//                System.out.println("ACTION 2");
                int hash = Objects.hash(((SlotChangeAction) action).getInventory(), ((SlotChangeAction) action).getSlot());

                List<SlotChangeAction> list = slotChanges.get(hash);
                if (list == null) {
                    list = new ArrayList<>();
                }

                list.add((SlotChangeAction) action);

                slotChanges.put(hash, list);
            }
        }

        for (Map.Entry<Integer, List<SlotChangeAction>> entry : new ArrayList<>(slotChanges.entrySet())) {
            int hash = entry.getKey();
//            System.out.println("ACTION 3 HASH > "+hash);
            List<SlotChangeAction> list = entry.getValue();

            if (list.size() == 1) { //No need to compact inventorySlot changes if there is only one on this inventorySlot
                slotChanges.remove(hash);
//                System.out.println("ACTION 4 DEL");
                continue;
            }

            List<SlotChangeAction> originalList = new ArrayList<>(list);

            SlotChangeAction originalAction = null;
            Item lastTargetItem = null;

            for (int i = 0; i < list.size(); i++) {
                SlotChangeAction action = list.get(i);

//                System.out.println("ACTION 5 > "+i + " || "+action);
//                System.out.println("ACTION 5.1.1 > "+action.getSlot());
//                System.out.println("ACTION 5.1.2 > "+action.getSourceItem());
//                System.out.println("ACTION 5.2 > "+action.getInventory().getItem(action.getSlot()));
                if (action.getSourceItem() != null) {
//                    System.out.println("ACTION 6");
//                    if(action.isValid(this.source)) {
                        originalAction = action;
                        lastTargetItem = action.getTargetItem();
                        list.remove(i);
                        break;
                    }
//                }else{
//                    System.out.println("ACTION 6.2");
////                    originalAction = action;
////                    lastTargetItem = action.getTargetItem();
////                    list.remove(i);
//
//                }
            }

            if (originalAction == null) {
                System.out.println("ACTION 7");
                return false; //Couldn't find any actions that had a source-item matching the current inventory inventorySlot
            }

            int sortedThisLoop;

            do {
                sortedThisLoop = 0;
                System.out.println("ACTION 8");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println("ACTION 9 i > "+i);
                    SlotChangeAction action = list.get(i);

                    Item actionSource = action.getSourceItem();
                    if (actionSource.equalsExact(lastTargetItem)) {
                        lastTargetItem = action.getTargetItem();
                        list.remove(i);
                        sortedThisLoop++;
                        System.out.println("ACTION 10");
                    }
                    else if (actionSource.equals(lastTargetItem)) {
                        lastTargetItem.count -= actionSource.count;
                        list.remove(i);
                        System.out.println("ACTION 11 ||| "+lastTargetItem.count);
                        if (lastTargetItem.count == 0) sortedThisLoop++;
                    }
                }
            } while (sortedThisLoop > 0);

            System.out.println("ACTION 12");
            if (list.size() > 0) { //couldn't chain all the actions together
                MainLogger.getLogger().debug("Failed to compact " + originalList.size() + " actions for " + this.source.getName());
                return false;
            }

            System.out.println("ACTION 13");
            for (SlotChangeAction action : originalList) {
                this.actions.remove(action);
            }
            System.out.println("ACTION 14");

            this.addAction(new SlotChangeAction(originalAction.getInventory(), originalAction.getSlot(), originalAction.getSourceItem(), lastTargetItem));

            System.out.println("ACTION 15");

            MainLogger.getLogger().debug("Successfully compacted " + originalList.size() + " actions for " + this.source.getName());
        }

        return true;
    }
    @Override
    public boolean execute() {
        if (!this.hasExecuted() && this.canExecute()) {
//            System.out.println("CALL 1111111111111112");
            if (!this.callExecuteEvent()) {
                System.out.println("CALL 1.1!!!!!!!!!!!!!!2222222222222222333333333444444444444");
                this.sendInventories();
                return true;
            } else {
                System.out.println("CALL 1.2 | TRANSACTION IS BEING RAN!!!!!!!!!!!!");
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

//                        System.out.println("CALL 1.3");
                        return true;
                    }

//                    System.out.println("CALL 1.4");

                    action = (InventoryAction) var1.next();
                } while (action.onPreExecute(this.source));

//                System.out.println("CALL 1.5");
                this.sendInventories();
                return true;
            }
        } else {
            System.out.println("CALL 2");
//            if (this.hasExecuted()) System.out.println("CALL 2.1");
//            if (!this.canExecute()) System.out.println("CALL 2.2");
//            if (this.recipe == null) System.out.println("CALL 2.3");
//            if (super.canExecute()) System.out.println("CALL 2.4");
            this.sendInventories();
            return false;
        }
    }

}
