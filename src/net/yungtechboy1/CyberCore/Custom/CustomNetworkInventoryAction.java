package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.Player;
import cn.nukkit.inventory.AnvilInventory;
import cn.nukkit.inventory.BeaconInventory;
import cn.nukkit.inventory.EnchantInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.action.*;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.types.ContainerIds;
import cn.nukkit.network.protocol.types.NetworkInventoryAction;

public class CustomNetworkInventoryAction extends NetworkInventoryAction {

    public CustomNetworkInventoryAction(NetworkInventoryAction na) {
        sourceType = na.sourceType;
        windowId = na.windowId;
        unknown = na.unknown;
        inventorySlot = na.inventorySlot;
        oldItem = na.oldItem;
        newItem = na.newItem;
    }


    @Override
    public InventoryAction createInventoryAction(Player player) {
        switch (this.sourceType) {
            case SOURCE_CONTAINER:
                if (this.windowId == ContainerIds.ARMOR) {
                    //TODO: HACK!
                    this.inventorySlot += 36;
                    this.windowId = ContainerIds.INVENTORY;
                }

                Inventory window = player.getWindowById(this.windowId);
                if (window != null) {
                    return new SlotChangeAction(window, this.inventorySlot, this.oldItem, this.newItem);
                }

                player.getServer().getLogger().debug("Player " + player.getName() + " has no open container with window ID " + this.windowId);
                return null;
            case SOURCE_WORLD:
                if (this.inventorySlot != InventoryTransactionPacket.ACTION_MAGIC_SLOT_DROP_ITEM) {
                    player.getServer().getLogger().debug("Only expecting drop-item world actions from the client!");
                    return null;
                }

                return new DropItemAction(this.oldItem, this.newItem);
            case SOURCE_CREATIVE:
                int type;

                switch (this.inventorySlot) {
                    case InventoryTransactionPacket.ACTION_MAGIC_SLOT_CREATIVE_DELETE_ITEM:
                        type = CreativeInventoryAction.TYPE_DELETE_ITEM;
                        break;
                    case InventoryTransactionPacket.ACTION_MAGIC_SLOT_CREATIVE_CREATE_ITEM:
                        type = CreativeInventoryAction.TYPE_CREATE_ITEM;
                        break;
                    default:
                        player.getServer().getLogger().debug("Unexpected creative action type " + this.inventorySlot);
                        return null;
                }

                return new CreativeInventoryAction(this.oldItem, this.newItem, type);
            case SOURCE_CRAFT_SLOT:
            case SOURCE_TODO:
                //These types need special handling.
                switch (this.windowId) {
                    case SOURCE_TYPE_CRAFTING_ADD_INGREDIENT:
                    case SOURCE_TYPE_CRAFTING_REMOVE_INGREDIENT:
                        return new SlotChangeAction(player.getCraftingGrid(), this.inventorySlot, this.oldItem, this.newItem);
                    case SOURCE_TYPE_CONTAINER_DROP_CONTENTS:
                        return new SlotChangeAction(player.getCraftingGrid(), this.inventorySlot, this.oldItem, this.newItem);
                    case SOURCE_TYPE_CRAFTING_RESULT:
                        return new CustomCraftingTakeResultAction(this.oldItem, this.newItem);
                    case SOURCE_TYPE_CRAFTING_USE_INGREDIENT:
                        return new CustomCraftingTransferMaterialAction(this.oldItem, this.newItem, this.inventorySlot);
                }

                if (this.windowId >= SOURCE_TYPE_ANVIL_OUTPUT && this.windowId <= SOURCE_TYPE_ANVIL_INPUT) { //anvil actions
                    Inventory inv = player.getWindowById(Player.ANVIL_WINDOW_ID);

                    if (!(inv instanceof AnvilInventory)) {
                        player.getServer().getLogger().debug("Player " + player.getName() + " has no open anvil inventory");
                        return null;
                    }
                    AnvilInventory anvil = (AnvilInventory) inv;

                    switch (this.windowId) {
                        case SOURCE_TYPE_ANVIL_INPUT:
                            //System.out.println("action input");
                            this.inventorySlot = 0;
                            return new SlotChangeAction(anvil, this.inventorySlot, this.oldItem, this.newItem);
                        case SOURCE_TYPE_ANVIL_MATERIAL:
                            //System.out.println("material");
                            this.inventorySlot = 1;
                            return new SlotChangeAction(anvil, this.inventorySlot, this.oldItem, this.newItem);
                        case SOURCE_TYPE_ANVIL_OUTPUT:
                            //System.out.println("action output");
                            break;
                        case SOURCE_TYPE_ANVIL_RESULT:
                            this.inventorySlot = 2;
                            anvil.clear(0);
                            anvil.clear(1);
                            anvil.setItem(2, this.oldItem);
                            //System.out.println("action result");
                            return new SlotChangeAction(anvil, this.inventorySlot, this.oldItem, this.newItem);
                    }
                }

                if (this.windowId >= SOURCE_TYPE_ENCHANT_OUTPUT && this.windowId <= SOURCE_TYPE_ENCHANT_INPUT) {
                    Inventory inv = player.getWindowById(Player.ENCHANT_WINDOW_ID);

                    if (!(inv instanceof EnchantInventory)) {
                        player.getServer().getLogger().debug("Player " + player.getName() + " has no open enchant inventory");
                        return null;
                    }
                    EnchantInventory enchant = (EnchantInventory) inv;

                    switch (this.windowId) {
                        case SOURCE_TYPE_ENCHANT_INPUT:
                            this.inventorySlot = 0;
                            Item local = enchant.getItem(0);
                            if (local.equals(this.newItem, true, false)) {
                                enchant.setItem(0, this.newItem);
                            }
                            break;
                        case SOURCE_TYPE_ENCHANT_MATERIAL:
                            this.inventorySlot = 1;
                            break;
                        case SOURCE_TYPE_ENCHANT_OUTPUT:
                            //enchant.sendSlot(0, player);
                            //ignore?
                            //return null;
                            break;
                    }

                    return new SlotChangeAction(enchant, this.inventorySlot, this.oldItem, this.newItem);
                }

                if (this.windowId == SOURCE_TYPE_BEACON) {
                    Inventory inv = player.getWindowById(Player.BEACON_WINDOW_ID);

                    if (!(inv instanceof BeaconInventory)) {
                        player.getServer().getLogger().debug("Player " + player.getName() + " has no open beacon inventory");
                        return null;
                    }
                    BeaconInventory beacon = (BeaconInventory) inv;

                    this.inventorySlot = 0;
                    return new SlotChangeAction(beacon, this.inventorySlot, this.oldItem, this.newItem);
                }

                //TODO: more stuff
                player.getServer().getLogger().debug("Player " + player.getName() + " has no open container with window ID " + this.windowId);
                return null;
            default:
                player.getServer().getLogger().debug("Unknown inventory source type " + this.sourceType);
                return null;
        }
    }
}
