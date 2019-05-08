package net.yungtechboy1.CyberCore.Custom.Inventory;

import cn.nukkit.Player;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockEnderChest;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ContainerOpenPacket;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Data.AuctionItemData;
import net.yungtechboy1.CyberCore.Factory.AuctionHouse.AuctionFactory;

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
    public AuctionFactory AF = null;

    public AuctionHouse(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba) {
        this(Holder, ccm, ba, 1);
    }

    public AuctionHouse(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba, int page) {
//        super(Holder, InventoryType.DOUBLE_CHEST, CyberCoreMain.getInstance().AuctionFactory.getPageHash(page), 9 * 6);//54??
        super(Holder, InventoryType.DOUBLE_CHEST, new HashMap<>(), 9 * 6);//54??
        //TODO SHOULD SIZE BE 54!?!?
        holder = Holder;
//        this.size = 9 * 6;

        CCM = ccm;
        AF = CCM.AuctionFactory;
        addItem(AF.getPage(Page));
        Page = page;

        BA = ba;

        this.title = "Auction House Page" + page;

        this.name = title;
        System.out.println("Creating AuctionHouse Class");
//        if (CyberCoreMain.getInstance().AuctionFactory.getPageHash(page) == null) System.out.println("NUUUUUUUUUUU");
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


    }

    @Override
    public void onClose(Player who) {

    }

    @Override
    public void onSlotChange(int index, Item before, boolean send) {

    }


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

    public void ReloadInv() {
        StaticItems si = new StaticItems(Page);
        int k = 9;
        setItem(getSize() - k--, si.Redglass);
        setItem(getSize() - k--, si.Paper);
        setItem(getSize() - k--, si.Grayglass);
        setItem(getSize() - k--, si.Diamond);
        setItem(getSize() - k--, si.Netherstar);
        setItem(getSize() - k--, si.Chest);
        setItem(getSize() - k--, si.Grayglass);
        setItem(getSize() - k--, si.Map);
        setItem(getSize() - k, si.Greenglass);
//        sendContents((Player) holder);
    }

    public void ConfirmItemPurchase(int slot) {
        AuctionItemData aid = AF.getAIDFromPage(Page, slot);
        SetupPageToConfirmSingleItem(aid);
        slots.clear();
        ConfirmPurchase = true;

//        sendContents((Player) holder);


    }

    /**
     * Maybe use later... Probablly wont work well with /ah I think...
     * @deprecated
     * @param aid
     */
    public void SetupPageToConfirmMultiItem(AuctionItemData aid) {
        StaticItems si = new StaticItems(Page);
        Item item = aid.MakePretty();
        Item confrim = si.Confirm;
        Item deny = si.Deny;
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 9; ii++) {
                int key = (i * 9) + ii;
                Item add = null;
                switch (ii) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        add = si.Deny.clone();
                        setItem(key, add, true);
                        break;
//                    case 1:
//                        if (item.count >= 32) add = si.RmvX32.clone();
//                        else add = si.Deny.clone();
//                        setItem(key,add,true);
//                        break;
//                    case 2:
//                        if (item.count >= 10) add = si.RmvX10.clone();
//                        else add = si.Deny.clone();
//                        setItem(key,add,true);
//                        break;
//                    case 3:
//                        if (item.count >= 1) add = si.RmvX1.clone();
//                        else add = si.Deny.clone();
//                        setItem(key,add,true);
//                        break;
                    case 4:
                        if (i == 2) add = item.clone();
                        else add = Item.get(160).clone();
                        setItem(key,add,true);
                        break;
                    case 5:
                        if (item.count >= 1) add = si.AddX1.clone();
                        else add = si.Deny.clone();
                        setItem(key,add,true);
                        break;
                }


                if (ii < 4) {
                    //RED
                    setItem(key, deny.clone(), true);
                } else if (ii == 4) {
                    //White or Item
                    if (i == 2) {
                        //@TODO Get ITem
                        setItem(key, item, true);
                    } else {
                        setItem(key, Item.get(160), true);
                    }
                } else {
                    //GREEN
                    setItem(key, confrim.clone(), true);
                }
            }
        }
    }

    @Override
    public void setContents(Map<Integer, Item> items) {
        super.setContents(items);
        if(holder != null)SendAllSlots((Player)holder);
//        setContents(items,true);
    }

    public void setContents(Map<Integer, Item> items, boolean send) {

        for(int i = 0; i < this.size; ++i) {
            if (!items.containsKey(i)) {
                if (this.slots.containsKey(i)) {
                    this.clear(i);
                }
            } else if (!this.setItem(i, (Item)((Map)items).get(i),send)) {
                this.clear(i);
            }
        }


        ReloadInv();
    }

    @Override
    public void sendContents(Player player) {
        ArrayList<Player> al = new ArrayList<>();
        al.add(player);
        this.sendContents(al.toArray(new Player[1]));
    }

    public void SendAllSlots(Player p){
        ArrayList<Player> al = new ArrayList<>();
        al.add(p);
        for(int i = 0; i < getSize(); i++){
            sendSlot(i,p);
        }
    }

    public void SetupPageToConfirmSingleItem(AuctionItemData aid) {
        StaticItems si = new StaticItems(Page);
        Item item = aid.MakePretty();
        Item confrim = si.Confirm.clone();
        Item deny = si.Deny.clone();
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 9; ii++) {
                int key = (i * 9) + ii;
                if (ii < 4) {
                    //RED
                    setItem(key, deny.clone(), true);
                } else if (ii == 4) {
                    //White or Item
                    if (i == 2) {
                        //@TODO Get ITem
                        setItem(key, item, true);
                    } else {
                        setItem(key, Item.get(160), true);
                    }
                } else {
                    //GREEN
                    setItem(key, confrim.clone(), true);
                }
            }
        }
    }


    public void setItem2(int index, Item item) {
        setItem(index, item.clone());
        this.onSlotChange(index, null);
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
        System.out.println("CLEARRRR###" + index);
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

        switch (index) {
            case MainPageItemRef.Reload:
                SendPage(Page);
                break;
        }
        this.sendSlot(index, this.getViewers());
    }

    public void SendPage(int page) {

    }

    @Override
    public Item[] addItem(Item... slots) {
        if (slots.length > 5 * 9) {
            ArrayList<Item> I = new ArrayList<>();
            for (int i = 0; i < 5 * 9; i++) {
                I.add(slots[i]);
            }
        }
        CyberCoreMain.getInstance().getLogger().error("ERROR TRIED TO ADD " + slots.length);
        return super.addItem(slots);
    }

    @Override
    public void sendContents(Collection<Player> players) {
        this.sendContents(players.stream().toArray(Player[]::new));
    }

    @Override
    public void sendSlot(int index, Player player) {
        this.sendSlot(index, new Player[]{player});
    }

//    @Override
//    public void sendContents(Player player) {
//        this.sendContents(new Player[]{player});
//    }


    @Override
    public InventoryType getType() {
        return InventoryType.DOUBLE_CHEST;
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

    public class StaticItems {
        public final Item Diamond;
        public final Item Potato;
        public final Item Grayglass;
        public final Item Redglass;
        public final Item Greenglass;
        public final Item Netherstar;
        public final Item Chest;
        public final Item Paper;
        public final Item Map;
        public final Item Confirm;
        public final Item AddX1;
        public final Item AddX10;
        public final Item AddX32;
        public final Item RmvX1;
        public final Item RmvX10;
        public final Item RmvX32;
        public final Item Deny;

        StaticItems() {
            this(-1);
        }

        StaticItems(int page) {
            CompoundTag T = new CompoundTag();
            T.putBoolean("AHITEM", true);
            AddX1 = Item.get(Item.EMERALD_BLOCK);
            AddX1.setCompoundTag(T);
            AddX1.getNamedTag().putInt("ADD", 1);
            AddX1.setCount(1);
            AddX1.setCustomName(TextFormat.GREEN + " Add 1 To Cart");

            AddX10 = Item.get(Item.EMERALD_BLOCK);
            AddX10.setCompoundTag(T);
            AddX10.getNamedTag().putInt("ADD", 10);
            AddX10.setCount(10);
            AddX10.setCustomName(TextFormat.GREEN + " Add 10 To Cart");


            AddX32 = Item.get(Item.EMERALD_BLOCK);
            AddX32.setCompoundTag(T);
            AddX32.getNamedTag().putInt("ADD", 32);
            AddX32.setCount(32);
            AddX32.setCustomName(TextFormat.GREEN + " Add 32 To Cart");


            RmvX1 = Item.get(Item.REDSTONE_BLOCK);
            RmvX1.setCompoundTag(T);
            RmvX1.getNamedTag().putInt("RMV", 1);
            RmvX1.setCustomName(TextFormat.GREEN + "Remove 1 From Cart");


            RmvX10 = Item.get(Item.REDSTONE_BLOCK);
            RmvX10.setCompoundTag(T);
            RmvX10.getNamedTag().putInt("RMV", 10);
            RmvX10.setCustomName(TextFormat.GREEN + "Remove 10 From Cart");


            RmvX32 = Item.get(Item.REDSTONE_BLOCK);
            RmvX32.setCompoundTag(T);
            RmvX32.getNamedTag().putInt("RMV", 32);
            RmvX32.setCustomName(TextFormat.GREEN + "Remove 32 From Cart");


            Confirm = Item.get(Item.EMERALD_BLOCK);
            Confirm.setCompoundTag(T);
            Confirm.setCustomName(TextFormat.GREEN + "Confirm Cart Purchase");
            Deny = Item.get(Item.REDSTONE_BLOCK);
            Deny.setCompoundTag(T);
            Deny.setCustomName(TextFormat.GREEN + "Cancel Cart Purchase");
            Diamond = Item.get(Item.DIAMOND);
            Diamond.setCompoundTag(T);
            Diamond.setCustomName(
                    TextFormat.GOLD + "" + TextFormat.BOLD + "Items you are Selling" + TextFormat.RESET + "\n" +
                            TextFormat.GREEN + " Click here to view all the items" + TextFormat.RESET + "\n" + TextFormat.GREEN + "you are currently selling on the auction" + TextFormat.RESET + "\n\n" +
                            TextFormat.GREEN + "Can also use " + TextFormat.DARK_GREEN + "/ah listed"
            );
            Potato = Item.get(Item.POISONOUS_POTATO, 1);
            Potato.setCompoundTag(T);
            Potato.setCustomName(
                    TextFormat.GOLD + "" + TextFormat.BOLD + "Collect Expired Items" + TextFormat.RESET + "\n" +
                            TextFormat.GREEN + " Click here to view all the items" + TextFormat.RESET + "\n" + TextFormat.GREEN + " you have canceled or experied" + TextFormat.RESET + "\n\n" +
                            TextFormat.GREEN + "Can also use " + TextFormat.DARK_GREEN + "/ah expired"
            );

            Grayglass = Item.get(Item.STAINED_GLASS_PANE, 7);
            Grayglass.setCompoundTag(T);
            Grayglass.setCustomName(
                    TextFormat.DARK_GRAY + "" + TextFormat.BOLD + "-------------"
            );
            Redglass = Item.get(Item.STAINED_GLASS_PANE, 14);
            Redglass.setCompoundTag(T);
            Redglass.setCustomName(
                    TextFormat.YELLOW + "" + TextFormat.BOLD + "Previous Page"
            );
            Greenglass = Item.get(Item.STAINED_GLASS_PANE, 5);
            Greenglass.setCompoundTag(T);
            Greenglass.setCustomName(
                    TextFormat.YELLOW + "" + TextFormat.BOLD + "Next Page"
            );
            Netherstar = Item.get(Item.NETHER_STAR);
            Netherstar.setCompoundTag(T);
            Netherstar.setCustomName(
                    TextFormat.GREEN + "" + TextFormat.BOLD + "Refresh Page"
            );
            if (page != -1) Netherstar.getNamedTag().putInt("page", page);
            Chest = Item.get(Item.CHEST);
            Chest.setCompoundTag(T);
            Chest.setCustomName(
                    TextFormat.GOLD + "" + TextFormat.BOLD + "Categories"
            );

            Map = Item.get(Item.MAP);
            Map.setCustomName(TextFormat.GOLD + "" + TextFormat.BOLD + "List Item In Hand");

            Paper = Item.get(Item.PAPER);
            Paper.setCustomName(TextFormat.GOLD + "" + TextFormat.BOLD + "Search Auction House For Item");
        }
    }

    public class MainPageItemRef {
        public static final int Size = 6 * 9;
        public static final int LastPage = Size - 9;
        public static final int Search = Size - 8;
        //        public static final int NULL = Size - 7;
        public static final int PlayerSelling = Size - 6;
        public static final int Reload = Size - 5;
        public static final int Catagories = Size - 4;
        //        public static final int MULL = Size - 3;
        public static final int ListItem = Size - 2;
        public static final int NextPage = Size - 1;
    }
}
