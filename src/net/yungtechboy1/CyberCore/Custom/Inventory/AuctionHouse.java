package net.yungtechboy1.CyberCore.Custom.Inventory;

import cn.nukkit.Player;
import cn.nukkit.block.*;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.blockentity.BlockEntityEnderChest;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.BlockEventPacket;
import cn.nukkit.network.protocol.ContainerOpenPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.*;

/**
 * Created by carlt_000 on 2/22/2017.
 */
public class AuctionHouse extends BaseInventory implements Inventory {

    protected final String name;
    protected final String title;
    protected final Map<Integer, Item> slots = new HashMap<>();
    protected final Set<Player> viewers = new HashSet<>();
    public int Page = 1;
    public boolean ConfirmPurchase = false;
    public int ConfirmPurchaseSlot = 0;
    protected int maxStackSize = Inventory.MAX_STACK;
    EntityHuman holder;
    Vector3 BA;
    CyberCoreMain CCM;
    BlockEntity blockEntity2 = null;
    BlockEntity blockEntity = null;

    public AuctionHouse(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba) {
        this(Holder, ccm, ba, 1);
    }

    public AuctionHouse(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba, int page) {
        super(Holder, InventoryType.DOUBLE_CHEST, CyberCoreMain.getInstance().AuctionFactory.getPageHash(page), 9 * 6);//54??
        //TODO SHOULD SIZE BE 54!?!?
        holder = Holder;
//        this.size = 9 * 6;

        CCM = ccm;
        Page = page;

        BA = ba;

        this.title = "Auction House Page" + page;

        this.name = title;
        if (CyberCoreMain.getInstance().AuctionFactory.getPageHash(page) == null) System.out.println("NUUUUUUUUUUU");
//        setContents(CyberCoreMain.getInstance().AuctionFactory.getPageHash(page));
    }

    public void setPage(Integer page) {
        if (1 > page) page = 1;
        Page = page;
    }


    @Override
    public void onOpen(Player who) {
        super.onOpen(who);
        ReloadInv();
        ContainerOpenPacket containerOpenPacket = new ContainerOpenPacket();
        containerOpenPacket.windowId = who.getWindowId(this);
        containerOpenPacket.type = this.getType().getNetworkType();
        BlockEnderChest chest = null;//who.getViewingEnderChest();
        containerOpenPacket.x = who.getFloorX();
        containerOpenPacket.y = who.getFloorY() - 2;
        containerOpenPacket.z = who.getFloorZ();


        who.dataPacket(containerOpenPacket);
        this.sendContents(who);


        who.sendMessage("OPENINGGGGG");
////        new BlockEnderChest()
//        UpdateBlockPacket fullBlock1 = new UpdateBlockPacket();
//        fullBlock1.x = (int) BA.x;
//        fullBlock1.y = (int) BA.y - 2;
//        fullBlock1.z = (int) BA.z;
//        fullBlock1.blockRuntimeId = Block.CHEST;
//        fullBlock1.dataLayer = 5;
//        fullBlock1.flags = 0;
//        who.dataPacket(fullBlock1);
//        /*Block b = new BlockChest();
//        b.set*/
////        UpdateBlockPacket fullBlock2 = new UpdateBlockPacket();
////        fullBlock2.x = (int) BA.x;
////        fullBlock2.y = (int) BA.y - 2;
////        fullBlock2.z = (int) BA.z - 1;
////        fullBlock2.blockRuntimeId = Block.CHEST;
////        fullBlock2.dataLayer = 5;
////        fullBlock2.flags = 0;
////        who.dataPacket(fullBlock2);
//        CompoundTag nbt = new CompoundTag("")
//                .putList(new ListTag<>("Items"))
//                .putString("id", BlockEntity.CHEST)
//                .putInt("x", (int) BA.x)
//                .putInt("y", (int) BA.y - 2)
//                .putInt("z", (int) BA.z);
//        CompoundTag nbt2 = new CompoundTag("")
//                .putList(new ListTag<>("Items"))
//                .putString("id", BlockEntity.CHEST)
//                .putInt("x", (int) BA.x)
//                .putInt("y", (int) BA.y - 2)
//                .putInt("z", (int) BA.z - 1);
//
////        nbt.putInt("pairx", (int) BA.x);
////        nbt.putInt("pairz", (int) BA.z - 1);
////        nbt2.putInt("pairx", (int) BA.x);
////        nbt2.putInt("pairz", (int) BA.z);
//
//        blockEntity = new BlockEntityChest(who.getLevel().getChunk((int) (BA.x) >> 4, (int) (BA.z) >> 4), nbt);
////        blockEntity2 = new BlockEntityChest(who.getLevel().getChunk((int) (BA.x) >> 4, (int) (BA.z) >> 4), nbt2);
//
//        BlockEntity t = this.getLevel().getBlockEntity(this);
//        BlockEntityEnderChest chest;
//        if (t instanceof BlockEntityEnderChest) {
//            chest = (BlockEntityEnderChest) t;
//        } else {
//            CompoundTag nbt = (new CompoundTag("")).putString("id", "EnderChest").putInt("x", (int) this.x).putInt("y", (int) this.y).putInt("z", (int) this.z);
//            chest = new BlockEntityEnderChest(this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
//        }
//
//        this.viewers.add(who);
//        ContainerOpenPacket pk = new ContainerOpenPacket();
//        pk.windowId = (byte) who.getWindowId(this);
//        pk.type = (byte) this.getType().getNetworkType();
//        //pk.type = 9;
//        pk.x = BA.getFloorX();
//        pk.y = BA.getFloorY() - 2;
//        pk.z = BA.getFloorZ();
//
//        /*pk.x = 85;
//        pk.y = 77;
//        pk.z = 323;*/
//        //57.0|83.0|336.0

//        CCM.AuctionFactory.getListOfItems();

//        who.batchDataPacket(pk);
//        this.sendContents(who);
    }

    @Override
    public void onClose(Player who) {

    }

    @Override
    public void onSlotChange(int index, Item before, boolean send) {

    }
//    public void onClose(Player who) {
//        UpdateBlockPacket fullBlock1 = new UpdateBlockPacket();
//        fullBlock1.x = (int) BA.x;
//        fullBlock1.y = BA.getFloorY() - 2;
//        fullBlock1.z = (int) BA.z;
//
//        Block OB = who.getLevel().getBlock(BA);
//
//        fullBlock1.blockId = OB.getId();
//        fullBlock1.blockData = OB.getDamage();
//
//        fullBlock1.flags = 0;
//        who.dataPacket(fullBlock1);
//
//
//        UpdateBlockPacket fullBlock2 = new UpdateBlockPacket();
//        fullBlock2.x = (int) BA.x;
//        fullBlock2.y = BA.getFloorY() - 2;
//        fullBlock2.z = (int) BA.z - 1;
//
//        Block OB2 = who.getLevel().getBlock(BA.add(0, 0, -1));
//
//        fullBlock2.blockId = OB2.getId();
//        fullBlock2.blockData = OB2.getDamage();
//
//        fullBlock2.flags = 0;
//        who.dataPacket(fullBlock2);
//
//        ContainerClosePacket pk = new ContainerClosePacket();
//        pk.windowid = (byte) who.getWindowId(this);
//        who.dataPacket(pk);
//        this.viewers.remove(who);
//
//        if (blockEntity != null) {
//            blockEntity.close();
//            blockEntity = null;
//        }
//        if (blockEntity2 != null) {
//            blockEntity2.close();
//            blockEntity2 = null;
//        }
//    }


    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public Map<Integer, Item> getContents() {

        Map<Integer, Item> contents = new HashMap<>();

        for (int i = 0; i < this.getSize(); ++i) {
            contents.put(i, this.getItem(i));
        }

        return contents;
    }

//    public void setContents(ArrayList<Item> items) {
//
//        super.setContents(items);
//        ReloadInv();
//    }

    @Override
    public void setContents(Map<Integer, Item> items) {
        super.setContents(items);
        ReloadInv();
    }

    public void ReloadInv() {
        Item diamond = Item.get(Item.DIAMOND);
        diamond.setCustomName(
                TextFormat.GOLD + "" + TextFormat.BOLD + "Items you are Selling" + TextFormat.RESET + "\n" +
                        TextFormat.GREEN + " Click here to view all the items" + TextFormat.RESET + "\n" + TextFormat.GREEN + "you are currently selling on the auction" + TextFormat.RESET + "\n\n" +
                        TextFormat.GREEN + "Can also use " + TextFormat.DARK_GREEN + "/ah listed"
        );
        Item potato = Item.get(Item.POISONOUS_POTATO, 1);
        potato.setCustomName(
                TextFormat.GOLD + "" + TextFormat.BOLD + "Collect Expired Items" + TextFormat.RESET + "\n" +
                        TextFormat.GREEN + " Click here to view all the items" + TextFormat.RESET + "\n" + TextFormat.GREEN + " you have canceled or experied" + TextFormat.RESET + "\n\n" +
                        TextFormat.GREEN + "Can also use " + TextFormat.DARK_GREEN + "/ah expired"
        );

        Item grayglass = Item.get(Item.STAINED_GLASS_PANE, 7);
        grayglass.setCustomName(
                TextFormat.DARK_GRAY + "" + TextFormat.BOLD + "-------------"
        );
        Item redglass = Item.get(Item.STAINED_GLASS_PANE, 14);
        redglass.setCustomName(
                TextFormat.YELLOW + "" + TextFormat.BOLD + "Previous Page"
        );
        Item greenglass = Item.get(Item.STAINED_GLASS_PANE, 5);
        greenglass.setCustomName(
                TextFormat.YELLOW + "" + TextFormat.BOLD + "Next Page"
        );
        Item netherstar = Item.get(Item.NETHER_STAR);
        netherstar.setCustomName(
                TextFormat.GREEN + "" + TextFormat.BOLD + "Refresh Page"
        );
        Item chest = Item.get(Item.CHEST);
        chest.setCustomName(
                TextFormat.GOLD + "" + TextFormat.BOLD + "Categories"
        );
        int k = 9;
        setItem(getSize() - k--, redglass);
        setItem(getSize() - k--, grayglass);
        setItem(getSize() - k--, grayglass);
        setItem(getSize() - k--, diamond);
        setItem(getSize() - k--, netherstar);
        setItem(getSize() - k--, chest);
        setItem(getSize() - k--, grayglass);
        setItem(getSize() - k--, grayglass);
        setItem(getSize() - k--, greenglass);
//        sendContents((Player) holder);
    }


    public void ConfirmItemPurchase(int slot) {
        Item confrim = Item.get(Item.EMERALD_BLOCK);
        confrim.setCustomName(TextFormat.GREEN + "Confirm Purchase");
        Item deny = Item.get(Item.REDSTONE_BLOCK);
        deny.setCustomName(TextFormat.GREEN + "Cancel Purchase");
        Item item = slots.get(slot);
        slots.clear();
        ConfirmPurchase = true;
        for (int i = 0; i < 6; i++) {
            for (int ii = 0; ii < 9; ii++) {
                int key = (i * 9) + ii;
                if (ii >= 0 && ii < 4) {
                    //RED
                    slots.put(key, deny.clone());
                } else if (ii == 4) {
                    //White or Item
                    if (i == 3) {
                        //@TODO Get ITem
                        slots.put(key, item);
                    } else {
                        slots.put(key, Item.get(160));
                    }
                } else {
                    //GREEN
                    slots.put(key, confrim.clone());
                }
            }
        }
        sendContents((Player) holder);


    }

    public void setItem2(int index, Item item) {
        setItem(index, item.clone());
        this.onSlotChange(index, null);
    }

//    @Override
//    public boolean setItem(int index, Item item) {
//        item = item.clone();
//        if (index < 0 || index >= this.size) {
//            return false;
//        } else if (item.getId() == 0 || item.getCount() <= 0) {
//            return this.clear(index);
//        }
//
//
//        Item old = this.getItem(index);
//        setItem(index, item.clone());
//        this.onSlotChange(index, old);
//        //if (getItem(0).getId() == 0 || getItem(4).getId() == 0) setItem2(2, Item.get(Item.ANVIL));
//
//        return true;
//    }
//
//    @Override
//    public boolean setItem(int index, Item item, boolean send) {
//        return false;
//    }

    @Override
    public boolean contains(Item item) {
        int count = Math.max(1, item.getCount());
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (Item i : this.getContents().values()) {
            if (item.equals(i, checkDamage, checkTag)) {
                count -= i.getCount();
                if (count <= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Map<Integer, Item> all(Item item) {
        Map<Integer, Item> slots = new HashMap<>();
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (Map.Entry<Integer, Item> entry : this.getContents().entrySet()) {
            if (item.equals(entry.getValue(), checkDamage, checkTag)) {
                slots.put(entry.getKey(), entry.getValue());
            }
        }

        return slots;
    }

    @Override
    public void remove(Item item) {
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (Map.Entry<Integer, Item> entry : this.getContents().entrySet()) {
            if (item.equals(entry.getValue(), checkDamage, checkTag)) {
                this.clear(entry.getKey());
            }
        }
    }

    @Override
    public int first(Item item) {
        int count = Math.max(1, item.getCount());
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (Map.Entry<Integer, Item> entry : this.getContents().entrySet()) {
            if (item.equals(entry.getValue(), checkDamage, checkTag) && entry.getValue().getCount() >= count) {
                return entry.getKey();
            }
        }

        return -1;
    }

    @Override
    public int first(Item item, boolean exact) {
        return 0;
    }

    @Override
    public int firstEmpty(Item item) {
        for (int i = 0; i < this.size; ++i) {
            if (this.getItem(i).getId() == Item.AIR) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void decreaseCount(int slot) {

    }


    @Override
    public boolean clear(int index) {
        if (this.slots.containsKey(index)) {
            Item item = new ItemBlock(new BlockAir(), null, 0);
            Item old = this.slots.get(index);
            if (item.getId() != Item.AIR) {
                setItem(index, item.clone());
            } else {
                this.slots.remove(index);
            }
            this.onSlotChange(index, old);
        }
        System.out.println("CLEARRRR");
        return true;
    }

    @Override
    public boolean clear(int index, boolean send) {
        return false;
    }

    @Override
    public void clearAll() {
        for (Integer index : this.getContents().keySet()) {
            this.clear(index);
        }
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Set<Player> getViewers() {
        return viewers;
    }

    @Override
    public EntityHuman getHolder() {
        return (EntityHuman) this.holder;
    }

    @Override
    public boolean open(Player who) {
        this.onOpen(who);

        return true;
    }

    @Override
    public void close(Player who) {
        this.onClose(who);
    }

    public void onSlotChange(int index, Item before) {
        this.sendSlot(index, this.getViewers());
    }

//    @Override
//    public void sendContents(Player player) {
//        this.sendContents(new Player[]{player});
//    }


    @Override
    public void sendContents(Collection<Player> players) {
        this.sendContents(players.stream().toArray(Player[]::new));
    }

    @Override
    public void sendSlot(int index, Player player) {
        this.sendSlot(index, new Player[]{player});
    }

    @Override
    public void sendSlot(int index, Player[] players) {

    }
//    public void sendSlot(int index, Player[] players) {
//        ContainerSetSlotPacket pk = new ContainerSetSlotPacket();
//        pk.slot = index;
//        pk.item = this.getItem(index).clone();
//
//        for (Player player : players) {
//            int id = player.getWindowId(this);
//            if (id == -1) {
//                this.close(player);
//                continue;
//            }
//            pk.windowid = (byte) id;
//            player.dataPacket(pk);
//        }
//    }

    @Override
    public void sendSlot(int index, Collection<Player> players) {
        this.sendSlot(index, players.stream().toArray(Player[]::new));
    }

    @Override
    public InventoryType getType() {
        return InventoryType.DOUBLE_CHEST;
    }
}
