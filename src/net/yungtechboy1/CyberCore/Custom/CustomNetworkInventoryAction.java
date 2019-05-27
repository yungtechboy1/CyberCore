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
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class CustomNetworkInventoryAction {
    public static final int SOURCE_CONTAINER = 0;
    public static final int SOURCE_WORLD = 2;
    public static final int SOURCE_CREATIVE = 3;
    public static final int SOURCE_TODO = 99999;
    public static final int SOURCE_CRAFT_SLOT = 100;
    public static final int SOURCE_TYPE_CRAFTING_ADD_INGREDIENT = -2;
    public static final int SOURCE_TYPE_CRAFTING_REMOVE_INGREDIENT = -3;
    public static final int SOURCE_TYPE_CRAFTING_RESULT = -4;
    public static final int SOURCE_TYPE_CRAFTING_USE_INGREDIENT = -5;
    public static final int SOURCE_TYPE_ANVIL_INPUT = -10;
    public static final int SOURCE_TYPE_ANVIL_MATERIAL = -11;
    public static final int SOURCE_TYPE_ANVIL_RESULT = -12;
    public static final int SOURCE_TYPE_ANVIL_OUTPUT = -13;
    public static final int SOURCE_TYPE_ENCHANT_INPUT = -15;
    public static final int SOURCE_TYPE_ENCHANT_MATERIAL = -16;
    public static final int SOURCE_TYPE_ENCHANT_OUTPUT = -17;
    public static final int SOURCE_TYPE_TRADING_INPUT_1 = -20;
    public static final int SOURCE_TYPE_TRADING_INPUT_2 = -21;
    public static final int SOURCE_TYPE_TRADING_USE_INPUTS = -22;
    public static final int SOURCE_TYPE_TRADING_OUTPUT = -23;
    public static final int SOURCE_TYPE_BEACON = -24;
    public static final int SOURCE_TYPE_CONTAINER_DROP_CONTENTS = -100;

    public int sourceType;
    public int windowId;
    public long unknown;
    public int inventorySlot;
    public Item oldItem;
    public Item newItem;

    public CustomNetworkInventoryAction() {
    }

    public CustomNetworkInventoryAction read(InventoryTransactionPacket packet) {
        this.sourceType = (int)packet.getUnsignedVarInt();
        switch(this.sourceType) {
            case 0:
                this.windowId = packet.getVarInt();
                break;
            case 2:
                this.unknown = packet.getUnsignedVarInt();
            case 3:
            default:
                break;
            case 100:
            case 99999:
                this.windowId = packet.getVarInt();
                switch(this.windowId) {
                    case -5:
                    case -4:
                        packet.isCraftingPart = true;
                }
        }

        this.inventorySlot = (int)packet.getUnsignedVarInt();
        System.out.println("ABOUT TO READDDDDD ITEM!!!!!!!!222222");
        this.oldItem = packet.getSlot();
        this.newItem = packet.getSlot();
        return this;
    }


    public CustomNetworkInventoryAction read(CustomInventoryTransactionPacket packet) {
        System.out.println("saaaaaaaaaaaaa READDDDDD ITEM!!!!!!!!");
        this.sourceType = (int)packet.getUnsignedVarInt();
        System.out.println("saaaaaaaaaaaaa READDDDDD ITEM!!!!!!!!"+sourceType);
        switch(this.sourceType) {
            case 0:
                this.windowId = packet.getVarInt();
                break;
            case 2:
                this.unknown = packet.getUnsignedVarInt();
            case 3:
            default:
                break;
            case 100:
            case 99999:
                this.windowId = packet.getVarInt();
                switch(this.windowId) {
                    case -5:
                    case -4:
                        packet.isCraftingPart = true;
                }
        }

        this.inventorySlot = (int)packet.getUnsignedVarInt();
        System.out.println("ABOUT TO READDDDDD ITEM!!!!!!!!");
        this.oldItem = packet.getSlot();
        this.newItem = packet.getSlot();
        return this;
    }

    public void write(CustomInventoryTransactionPacket packet) {
        packet.putUnsignedVarInt((long)this.sourceType);
        switch(this.sourceType) {
            case 0:
                packet.putVarInt(this.windowId);
                break;
            case 2:
                packet.putUnsignedVarInt(this.unknown);
            case 3:
            default:
                break;
            case 100:
            case 99999:
                packet.putVarInt(this.windowId);
        }

        packet.putUnsignedVarInt((long)this.inventorySlot);
        packet.putSlot(this.oldItem);
        packet.putSlot(this.newItem);
    }

    public String toString() {
        return "CUSTOMNetworkInventoryAction(sourceType=" + this.sourceType + ", windowId=" + this.windowId + ", unknown=" + this.unknown + ", inventorySlot=" + this.inventorySlot + ", oldItem=" + this.oldItem + ", newItem=" + this.newItem + ")";
    }


    public CustomNetworkInventoryAction(NetworkInventoryAction na) {
        sourceType = na.sourceType;
        windowId = na.windowId;
        unknown = na.unknown;
        inventorySlot = na.inventorySlot;
        oldItem = na.oldItem;
        newItem = na.newItem;
    }


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
//                System.out.println("SOURCE TODO");
//                System.out.println("INV SLOT: >> "+inventorySlot);
//                System.out.println("OLD ITEM: >> "+oldItem);
//                System.out.println("NEW ITEM: >> "+newItem);
                //These types need special handling.
                switch (this.windowId) {
                    case SOURCE_TYPE_CRAFTING_ADD_INGREDIENT:
                    case SOURCE_TYPE_CRAFTING_REMOVE_INGREDIENT:
                        return new SlotChangeAction(player.getCraftingGrid(), this.inventorySlot, this.oldItem, this.newItem);
                    case SOURCE_TYPE_CONTAINER_DROP_CONTENTS:
                        return new SlotChangeAction(player.getCraftingGrid(), this.inventorySlot, this.oldItem, this.newItem);
                    case SOURCE_TYPE_CRAFTING_RESULT:
                        System.out.println("WINDOWID: >> SOURCE_TYPE_CRAFTING_RESULT");
                        return new CustomCraftingTakeResultAction(this.oldItem, this.newItem);
                    case SOURCE_TYPE_CRAFTING_USE_INGREDIENT:
                        System.out.println("WINDOWID: >> SOURCE_TYPE_CRAFTING_USE_INGREDIENT");
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
