package net.yungtechboy1.CyberCore.Custom.Inventory;

import cn.nukkit.Player;
import cn.nukkit.block.*;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.*;

/**
 * Created by carlt_000 on 2/22/2017.
 */
public class AuctionHouse implements Inventory {

    protected final String name;
    protected final String title;
    protected final Map<Integer, Item> slots = new HashMap<>();
    protected final Set<Player> viewers = new HashSet<>();
    public int Page = 1;
    protected int maxStackSize = Inventory.MAX_STACK;
    protected int size;
    EntityHuman holder;
    Vector3 BA;
    CyberCoreMain CCM;

    public boolean ConfirmPurchase = false;
    public int ConfirmPurchaseSlot = 0;

    BlockEntity blockEntity2 = null;
    BlockEntity blockEntity = null;

    public AuctionHouse(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba) {
        this(Holder, ccm, ba, 1);
    }

    public AuctionHouse(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba, int page) {
        holder = Holder;
        this.size = 9 * 6;

        CCM = ccm;
        Page = page;

        BA = ba;

        this.title = "Auction House Page" + page;

        this.name = title;
        setContents(CCM.AuctionFactory.getPage(page));
    }

    public void setPage(Integer page) {
        if (1 > page) page = 1;
        Page = page;
    }

    public void onOpen(Player who) {
    }
//    @Override
//    public void onOpen(Player who) {
//
//        UpdateBlockPacket fullBlock1 = new UpdateBlockPacket();
//        fullBlock1.x = (int) BA.x;
//        fullBlock1.y = (int) BA.y - 2;
//        fullBlock1.z = (int) BA.z;
//        fullBlock1.blockId = Block.CHEST;
//        fullBlock1.blockData = 5;
//        fullBlock1.flags = 0;
//        who.dataPacket(fullBlock1);
//        /*Block b = new BlockChest();
//        b.set*/
//        UpdateBlockPacket fullBlock2 = new UpdateBlockPacket();
//        fullBlock2.x = (int) BA.x;
//        fullBlock2.y = (int) BA.y - 2;
//        fullBlock2.z = (int) BA.z - 1;
//        fullBlock2.blockId = Block.CHEST;
//        fullBlock2.blockData = 5;
//        fullBlock2.flags = 0;
//        who.dataPacket(fullBlock2);
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
//        nbt.putInt("pairx", (int) BA.x);
//        nbt.putInt("pairz", (int) BA.z - 1);
//        nbt2.putInt("pairx", (int) BA.x);
//        nbt2.putInt("pairz", (int) BA.z);
//
//        blockEntity = new BlockEntityChest(who.getLevel().getChunk((int) (BA.x) >> 4, (int) (BA.z) >> 4), nbt);
//        blockEntity2 = new BlockEntityChest(who.getLevel().getChunk((int) (BA.x) >> 4, (int) (BA.z) >> 4), nbt2);
//
//        this.viewers.add(who);
//        ContainerOpenPacket pk = new ContainerOpenPacket();
//        pk.windowid = (byte) who.getWindowId(this);
//        pk.type = (byte) this.getType().getNetworkType();
//        //pk.type = 9;
//        pk.slots = 1;
//        pk.x = BA.getFloorX();
//        pk.y = BA.getFloorY() - 2;
//        pk.z = BA.getFloorZ();
//
//        /*pk.x = 85;
//        pk.y = 77;
//        pk.z = 323;*/
//        //57.0|83.0|336.0
//
//        CCM.AuctionFactory.getListOfItems();
//
//        who.batchDataPacket(pk);
//        this.sendContents(who);
//    }

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

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getMaxStackSize() {
        return maxStackSize;
    }

    @Override
    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Item getItem(int index) {
        return this.slots.containsKey(index) ? this.slots.get(index).clone() : new ItemBlock(new BlockAir(), null, 0);
    }

    @Override
    public Map<Integer, Item> getContents() {
        return new HashMap<>(this.slots);
    }

    @Override
    public void setContents(Map<Integer, Item> items) {
        this.slots.clear();
        for (Map.Entry<Integer, Item> a : items.entrySet()) {
            slots.put(a.getKey(), a.getValue());
        }
        ReloadInv();
    }

    public void setContents(ArrayList<Item> items) {
        this.slots.clear();
        int aa = 0;
        for (Item a : items) {
            slots.put(aa++, a);
        }
        ReloadInv();
    }

    public void ReloadInv() {
        Item diamond = Item.get(Item.DIAMOND);
        diamond.setCustomName(
                TextFormat.GOLD + "" + TextFormat.BOLD + "Items you are Selling" + TextFormat.RESET + "\n" +
                        TextFormat.GREEN + " Click here to view all the items" + TextFormat.RESET + "\n" + TextFormat.GREEN + "you are currently selling on the auction" + TextFormat.RESET + "\n\n" +
                        TextFormat.GREEN + "Can also use " + TextFormat.DARK_GREEN + "/ah listed"
        );
        Item potato = Item.get(Item.POTATO, 1);
        potato.setCustomName(
                TextFormat.GOLD + "" + TextFormat.BOLD + "Collect Expired Items" + TextFormat.RESET + "\n" +
                        TextFormat.GREEN + " Click here to view all the items" + TextFormat.RESET + "\n" + TextFormat.GREEN + " you have canceled or experied" + TextFormat.RESET + "\n\n" +
                        TextFormat.GREEN + "Can also use " + TextFormat.DARK_GREEN + "/ah expired"
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
        this.slots.put(45, diamond);
        this.slots.put(46, potato);
        this.slots.put(48, redglass);
        this.slots.put(49, netherstar);
        this.slots.put(50, greenglass);
        this.slots.put(52, chest);
        sendContents((Player) holder);
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
        this.slots.put(index, item.clone());
        this.onSlotChange(index, null);
    }

    @Override
    public boolean setItem(int index, Item item) {
        item = item.clone();
        if (index < 0 || index >= this.size) {
            return false;
        } else if (item.getId() == 0 || item.getCount() <= 0) {
            return this.clear(index);
        }


        Item old = this.getItem(index);
        this.slots.put(index, item.clone());
        this.onSlotChange(index, old);
        //if (getItem(0).getId() == 0 || getItem(4).getId() == 0) setItem2(2, Item.get(Item.ANVIL));

        return true;
    }

    @Override
    public boolean setItem(int index, Item item, boolean send) {
        return false;
    }

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
    public boolean canAddItem(Item item) {
        item = item.clone();
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (int i = 0; i < this.getSize(); ++i) {
            Item slot = this.getItem(i);
            if (item.equals(slot, checkDamage, checkTag)) {
                int diff;
                if ((diff = slot.getMaxStackSize() - slot.getCount()) > 0) {
                    item.setCount(item.getCount() - diff);
                }
            } else if (slot.getId() == Item.AIR) {
                item.setCount(item.getCount() - this.getMaxStackSize());
            }

            if (item.getCount() <= 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Item[] addItem(Item... slots) {
        List<Item> itemSlots = new ArrayList<>();
        for (Item slot : slots) {
            if (slot.getId() != 0 && slot.getCount() > 0) {
                itemSlots.add(slot.clone());
            }
        }

        List<Integer> emptySlots = new ArrayList<>();

        for (int i = 0; i < this.getSize(); ++i) {
            //Only 0 and 4 Slot are editable!
            if (i != 0 && i != 4) continue;
            Item item = this.getItem(i);
            if (item.getId() == Item.AIR || item.getCount() <= 0) {
                emptySlots.add(i);
            }

            for (Item slot : new ArrayList<>(itemSlots)) {
                if (slot.equals(item) && item.getCount() < item.getMaxStackSize()) {
                    int amount = Math.min(item.getMaxStackSize() - item.getCount(), slot.getCount());
                    amount = Math.min(amount, this.getMaxStackSize());
                    if (amount > 0) {
                        slot.setCount(slot.getCount() - amount);
                        item.setCount(item.getCount() + amount);
                        this.setItem(i, item);
                        if (slot.getCount() <= 0) {
                            itemSlots.remove(slot);
                        }
                    }
                }
            }
            if (itemSlots.isEmpty()) {
                break;
            }
        }

        if (!itemSlots.isEmpty() && !emptySlots.isEmpty()) {
            for (int slotIndex : emptySlots) {
                if (!itemSlots.isEmpty()) {
                    Item slot = itemSlots.get(0);
                    int amount = Math.min(slot.getMaxStackSize(), slot.getCount());
                    amount = Math.min(amount, this.getMaxStackSize());
                    slot.setCount(slot.getCount() - amount);
                    Item item = slot.clone();
                    item.setCount(amount);
                    this.setItem(slotIndex, item);
                    if (slot.getCount() <= 0) {
                        itemSlots.remove(slot);
                    }
                }
            }
        }

        return itemSlots.stream().toArray(Item[]::new);
    }

    @Override
    public Item[] removeItem(Item... slots) {
        List<Item> itemSlots = new ArrayList<>();
        for (Item slot : slots) {
            if (slot.getId() != 0 && slot.getCount() > 0) {
                itemSlots.add(slot.clone());
            }
        }

        for (int i = 0; i < this.getSize(); ++i) {
            if (i != 0 && i != 4) continue;
            Item item = this.getItem(i);
            if (item.getId() == Item.AIR || item.getCount() <= 0) {
                continue;
            }

            for (Item slot : new ArrayList<>(itemSlots)) {
                if (slot.equals(item, item.hasMeta(), item.getCompoundTag() != null)) {
                    int amount = Math.min(item.getCount(), slot.getCount());
                    slot.setCount(slot.getCount() - amount);
                    item.setCount(item.getCount() - amount);
                    this.setItem(i, item);
                    if (slot.getCount() <= 0) {
                        itemSlots.remove(slot);
                    }

                }
            }

            if (itemSlots.size() == 0) {
                break;
            }
        }

        return itemSlots.stream().toArray(Item[]::new);
    }

    @Override
    public boolean clear(int index) {
        if (this.slots.containsKey(index)) {
            Item item = new ItemBlock(new BlockAir(), null, 0);
            Item old = this.slots.get(index);
            if (item.getId() != Item.AIR) {
                this.slots.put(index, item.clone());
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

    @Override
    public void sendContents(Player player) {
        this.sendContents(new Player[]{player});
    }

    @Override
    public void sendContents(Player[] players) {

    }
//    public void sendContents(Player[] players) {
//        ContainerSetContentPacket pk = new ContainerSetContentPacket();
//        pk.slots = new Item[this.getSize()];
//        for (int i = 0; i < this.getSize(); ++i) {
//            pk.slots[i] = this.getItem(i);
//        }
//
//        for (Player player : players) {
//            int id = player.getWindowId(this);
//            if (id == -1 || !player.spawned) {
//                this.close(player);
//                continue;
//            }
//            pk.windowid = (byte) id;
//            player.batchDataPacket(pk);
//        }
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
