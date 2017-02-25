package net.yungtechboy1.CyberCore.Custom.Inventory;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockClayStained;
import cn.nukkit.block.BlockGlassPane;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemPotatoPoisonous;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.*;
import cn.nukkit.utils.BlockColor;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import sun.swing.plaf.synth.DefaultSynthStyle;

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
    Block OB;
    CyberCoreMain CCM;

    public AuctionHouse(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba, Block ob) {
        this(Holder,ccm, ba, ob, 1);
    }

    public AuctionHouse(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba, Block ob, int page) {
        holder = Holder;
        this.size = 5;

        CCM = ccm;
        Page = page;

        BA = ba;
        OB = ob;

        this.title = "Anvil INV";

        this.name = title;
        ReloadInv();
    }

    private static String IntToRoman(Integer integer) {
        int a = 1;
        if (integer == a++) return "I";
        if (integer == a++) return "II";
        if (integer == a++) return "III";
        if (integer == a++) return "IV";
        if (integer == a++) return "V";
        if (integer == a++) return "VI";
        if (integer == a++) return "VII";
        if (integer == a++) return "VIII";
        if (integer == a++) return "IX";
        if (integer == a) return "X";
        return "-";
    }

    public void Take(Player player) {
        if (getItem(2).getId() != Item.ANVIL && getItem(2).getId() != 0) {
            Server.getInstance().getLogger().debug("COMMMBBBIIINNNEEE@!!!!");
            setItem2(0, Item.get(0));
            setItem2(4, Item.get(0));
            player.getInventory().addItem(getItem(2).clone());
            player.getInventory().sendContents(player);
            setItem2(2, Item.get(Item.ANVIL));
        }
    }

    @Override
    public void onOpen(Player who) {

        UpdateBlockPacket fullBlock1 = new UpdateBlockPacket();
        fullBlock1.x = (int) BA.x;
        fullBlock1.y = (int) BA.y - 2;
        fullBlock1.z = (int) BA.z;
        fullBlock1.blockId = Block.CHEST;
        fullBlock1.blockData = 0;
        fullBlock1.flags = 0;
        who.dataPacket(fullBlock1);

        this.viewers.add(who);
        ContainerOpenPacket pk = new ContainerOpenPacket();
        pk.windowid = (byte) who.getWindowId(this);
        pk.type = (byte) this.getType().getNetworkType();
        //pk.type = 9;
        pk.slots = 1;
        pk.x = BA.getFloorX();
        pk.y = BA.getFloorY() - 2;
        pk.z = BA.getFloorZ();

        /*pk.x = 85;
        pk.y = 77;
        pk.z = 323;*/
        //57.0|83.0|336.0

        CCM.AuctionFactory.getListOfItems();

        who.batchDataPacket(pk);
        this.sendContents(who);
    }

    @Override
    public void onClose(Player who) {
        UpdateBlockPacket fullBlock1 = new UpdateBlockPacket();
        fullBlock1.x = (int) BA.x;
        fullBlock1.y = BA.getFloorY() - 2;
        fullBlock1.z = (int) BA.z;
        fullBlock1.blockId = OB.getId();
        fullBlock1.blockData = OB.getDamage();
        ;
        fullBlock1.flags = 0;
        who.dataPacket(fullBlock1);
        ContainerClosePacket pk = new ContainerClosePacket();
        pk.windowid = (byte) who.getWindowId(this);
        who.dataPacket(pk);
        this.viewers.remove(who);
    }

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

        ReloadInv();
    }

    public void ReloadInv() {
        this.slots.clear();
        Item diamond = Item.get(Item.DIAMOND);
        diamond.setCustomName(
                TextFormat.GOLD + "" + TextFormat.BOLD + "Items you are Selling" + TextFormat.RESET + "\n" +
                        TextFormat.GREEN + " Click here to view all the items you are currently selling on the auction" + TextFormat.RESET + "\n\n" +
                        TextFormat.GREEN + "Can also use " + TextFormat.DARK_GREEN + "/ah listed"
        );
        Item potato = Item.get(Item.POTATO, 1);
        potato.setCustomName(
                TextFormat.GOLD + "" + TextFormat.BOLD + "Collect Expired Items" + TextFormat.RESET + "\n" +
                        TextFormat.GREEN + " Click here to view all the items you have canceled or experied" + TextFormat.RESET + "\n\n" +
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
        this.slots.put(46, diamond);
        this.slots.put(47, potato);
        this.slots.put(49, redglass);
        this.slots.put(50, netherstar);
        this.slots.put(51, greenglass);
        this.slots.put(53, chest);
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
        if (getItem(0).getId() != 0 && getItem(4).getId() != 0 /*&& getItem(0).getId() != i0 && getItem(4).getId() != i4*/) {
            i0 = getItem(0).getId();
            i4 = getItem(4).getId();

        } else if (getItem(0).getId() == 0 || getItem(4).getId() == 0) {
            Item t = Item.get(Item.REDSTONE_BLOCK);
            t.setCustomName(TextFormat.RED + "ERROR!" + TextFormat.RESET + "\n" + TextFormat.YELLOW + "Please Add 2 Items!");
            setItem2(1, t);
            setItem2(3, t);
            setItem2(2, Item.get(Item.ANVIL));
            sendContents((Player) holder);
        }

        return true;
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
    public int firstEmpty(Item item) {
        for (int i = 0; i < this.size; ++i) {
            if (this.getItem(i).getId() == Item.AIR) {
                return i;
            }
        }

        return -1;
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
            Item t = Item.get(Item.REDSTONE_BLOCK);
            t.setCustomName(TextFormat.RED + "ERROR!" + TextFormat.RESET + "\n" + TextFormat.YELLOW + "Please Add 2 Items!");
            setItem2(1, t);
            setItem2(3, t);
            //if(index == 0 ||index == 4)setItem2(2, Item.get(Item.ANVIL));
            this.onSlotChange(index, old);
        }

        return true;
    }

    @Override
    public void clearAll() {
        for (Integer index : this.getContents().keySet()) {
            this.clear(index);
        }
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

    @Override
    public void onSlotChange(int index, Item before) {
        this.sendSlot(index, this.getViewers());
    }

    @Override
    public void sendContents(Player player) {
        this.sendContents(new Player[]{player});
    }

    @Override
    public void sendContents(Player[] players) {
        ContainerSetContentPacket pk = new ContainerSetContentPacket();
        pk.slots = new Item[this.getSize()];
        for (int i = 0; i < this.getSize(); ++i) {
            pk.slots[i] = this.getItem(i);
        }

        for (Player player : players) {
            int id = player.getWindowId(this);
            if (id == -1 || !player.spawned) {
                this.close(player);
                continue;
            }
            pk.windowid = (byte) id;
            player.batchDataPacket(pk);
        }
    }

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
        ContainerSetSlotPacket pk = new ContainerSetSlotPacket();
        pk.slot = index;
        pk.item = this.getItem(index).clone();

        for (Player player : players) {
            int id = player.getWindowId(this);
            if (id == -1) {
                this.close(player);
                continue;
            }
            pk.windowid = (byte) id;
            player.dataPacket(pk);
        }
    }

    @Override
    public void sendSlot(int index, Collection<Player> players) {
        this.sendSlot(index, players.stream().toArray(Player[]::new));
    }

    @Override
    public InventoryType getType() {
        return InventoryType.DOUBLE_CHEST;
    }
}
