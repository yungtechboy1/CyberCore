package net.yungtechboy1.CyberCore.Factory.Shop.Spawner;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockEnderChest;
import cn.nukkit.block.BlockGlassPaneStained;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.event.entity.EntityInventoryChangeEvent;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemID;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ContainerOpenPacket;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Factory.AuctionHouse.AuctionHouse;

import java.util.*;

public class SpawnerShop extends BaseInventory implements Inventory {

    protected final String name;
    protected final String title;
    //    public static HashMap<Integer, Item> slots = new HashMap<>();
    protected final Set<Player> viewers = new HashSet<>();
    public boolean AdminMode = false;
    public boolean ConfirmPurchase = false;
    public int ConfirmPurchaseSlot = 0;
    public SpawnShopFactory SSF = null;
    protected int maxStackSize = Inventory.MAX_STACK;
    EntityHuman holder;
    Vector3 BA;
    CyberCoreMain CCM;
    BlockEntity blockEntity2 = null;
    BlockEntity blockEntity = null;
    public CurrentPageEnum CurrentPage;
    private int Page = 1;

    public SpawnerShop(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba) {
        this(Holder, ccm, ba, 1);
    }

    public SpawnerShop(EntityHuman Holder, CyberCoreMain ccm, Vector3 ba, int page) {
//        super(Holder, InventoryType.DOUBLE_CHEST, CyberCoreMain.getInstance().SF.getPageHash(page), 9 * 6);//54??
        super(Holder, InventoryType.DOUBLE_CHEST, ccm.SpawnShop.getPageHash(page), 9 * 6);//54??
        //TODO SHOULD SIZE BE 54!?!?
        holder = Holder;
//        this.size = 9 * 6;

        CCM = ccm;
        SSF = CCM.SpawnShop;
//        addItem(SF.getPage(Page));
        Page = page;

        BA = ba;

        this.title = "Spawnnn Shop Page" + page;

        this.name = title;
        System.out.println("Creating SHopIn Class");
//        if (CyberCoreMain.getInstance().SF.getPageHash(page) == null) System.out.println("NUUUUUUUUUUU");
//        setContents(CyberCoreMain.getInstance().SF.getPageHash(page));
    }

    public CurrentPageEnum getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(CurrentPageEnum currentPage) {
        CurrentPage = currentPage;
    }

    public void GoToSellerPage() {
        clearAll();
        setPage(1);
        setContents(SSF.getPageHash(getPage()), true);
        ReloadInv();
        sendContents(getHolder());
        SendAllSlots(getHolder());
    }

    public void ReloadCurrentPage() {
        switch (CurrentPage) {
            case ItemPage:
                clearAll();
                setPage(getPage());
                break;
            case PlayerSellingPage:
                setPagePlayerSelling(getPage());
                break;


        }
    }

    public void ClearConfirmPurchase() {

        ConfirmPurchase = false;
        ConfirmPurchaseSlot = -1;
    }

    public void DisplayCatagories() {
        clearAll();
        for (int i = 0; i < 5 * 9; i++) {
            Block itm = new BlockGlassPaneStained(7);
            Item bi = new ItemBlock(itm);
            bi.setCustomName(TextFormat.GRAY + "FEATURE CURRENTLY DISABLED!");
            setItem(i, bi, true);
        }
        ReloadInv();
        SendAllSlots(getHolder());
    }

    public void GoToNextPage() {
        setPage(getPage() + 1);
    }

    public void GoToPrevPage() {
        setPage(getPage() - 1);
    }

    public void setPagePlayerSelling() {
        setPagePlayerSelling(1);
    }

    public void setPagePlayerSelling(Integer page) {
        Page = page;
        CurrentPage = CurrentPageEnum.PlayerSellingPage;
        clearAll();
        setContents(SSF.getPageHash(getPage()));
        ReloadInv();
        SendAllSlots(getHolder());
    }

    public int getPage() {
        return Page;
    }

    public void setPage(Integer page) {
        if (1 > page) page = 1;
        Page = page;
        CurrentPage = CurrentPageEnum.ItemPage;
        clearAll();
        addItem(SSF.getPage(getPage()));
        ReloadInv();
        SendAllSlots(getHolder());
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
        super.onSlotChange(index, before, send);
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

    @Override
    public void setContents(Map<Integer, Item> items) {
//        super.setContents(items);
//        if (holder != null) SendAllSlots((Player) holder);
        setContents(items, true);
    }

    public void ReloadInv() {
        StaticItems si = new StaticItems(Page);
        int k = 9;
        setItem(MainPageItemRef.LastPage, si.Redglass);
//        setItem( k--, si.Paper);
//        setItem( k--, si.Grayglass);
        setItem(MainPageItemRef.ToggleAdmin, si.Diamond);
        setItem(MainPageItemRef.Reload, si.Netherstar);
//        setItem( k--, si.Chest);
//        setItem( k--, si.Grayglass);
//        setItem( k--, si.Map);
        setItem(MainPageItemRef.NextPage, si.Greenglass);
//        sendContents((Player) holder);
    }

    public void ConfirmItemPurchase(int slot) {
        clearAll();
        SpawnerShopData aid = SSF.getItemFrom(Page, slot);
        SetupPageToConfirmMultiItem(aid);
        ReloadInv();
        ConfirmPurchase = true;
        ConfirmPurchaseSlot = slot;

//        sendContents((Player) holder);


    }

    /**
     * Maybe use later... Probablly wont work well with /ah I think...
     *
     * @param aid
     */
    public SpawnerShopData MultiConfirmData = null;
    public void SetupPageToConfirmMultiItem(SpawnerShopData aid) {
        CurrentPage = CurrentPageEnum.PlayerSellingPage;
        StaticItems si = new StaticItems(Page);
        CorePlayer cp = (CorePlayer) getHolder();
        Item item = aid.getItem();
        MultiConfirmData = aid;
        Collection<Item> ai =  cp.getInventory().all(aid.getItem(true)).values();
        int ic = 0;
        for(Item iii: ai){
            ic += iii.getCount();
        }
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 9; ii++) {
                int key = (i * 9) + ii;
                Item add = null;
                if (i == 0) {
                    if (ii <= 3) {
                        add = si.ChestCancel.clone();
                        add.setLore("Cancel/Stop current transaction");
                        setItem(key, add, true);
                    } else if(ii == 4){
                        Item g = si.Gold.clone();
                        g.setCustomName(TextFormat.GOLD + " Your money: " + cp.getMoney());
                        setItem(key, g, true);
                    } else {
                        add = si.ChestBuy.clone();
                        int mb = (int)Math.floor(cp.getMoney()/aid.getPrice());
                        add.setLore("You can buy "+mb+" "+item.getName()+"(s)");
                        setItem(key, add, true);
                    }
                    continue;
                }
                add = null;
                switch (ii) {
                    case 0:
                        add = si.Deny.clone();
                        setItem(key, add, true);
                        break;
                    case 1:
                        add = si.Deny.clone();
                        setItem(key, add, true);
                        break;
                    case 2:
                         add = si.Deny.clone();
                        setItem(key, add, true);
                        break;
                    case 3:
                        add = si.Deny.clone();
                        setItem(key, add, true);
                        break;
                    case 4:
                        if (i == 2) add = item.clone();
                        else {
                            add = Item.get(160).clone();
                            add.setCustomName("--------");
                        }
                        setItem(key, add, true);
                        break;
                    case 5:
                        if(cp.getMoney() > aid.getPrice())add = si.AddX1.clone();
                        else add = si.AddX1N.clone();
                        setItem(key, add, true);
                        break;
                    case 6:
                        if(cp.getMoney() > aid.getPrice(10))add = si.AddX10.clone();
                        else add = si.AddX10N.clone();
                        setItem(key, add, true);
                        break;
                    case 7:
                        if(cp.getMoney() > aid.getPrice(32))add = si.AddX32.clone();
                        else add = si.AddX32N.clone();
                        setItem(key, add, true);
                        break;
                    case 8:
                        if(cp.getMoney() > aid.getPrice(64))add = si.AddX64.clone();
                        else add = si.AddX64N.clone();
                        setItem(key, add, true);
                        break;
                }


//                if (ii < 4) {
//                    //RED
//                    setItem(key, deny.clone(), true);
//                } else if (ii == 4) {
//                    //White or Item
//                    if (i == 2) {
//                        //@TODO Get ITem
//                        setItem(key, item, true);
//                    } else {
//                        setItem(key, Item.get(160), true);
//                    }
//                } else {
//                    //GREEN
//                    setItem(key, confrim.clone(), true);
//                }
            }
        }
    }

    public void setContents(Map<Integer, Item> items, boolean send) {
        System.out.println("SETTINNGG CCCOONNNTTTZ " + items.size());
        for (int i = 0; i < this.size - 1; ++i) {

//            System.out.println("SETTING ITEM IN KEY " + i + " VVVVVVVV " + items.get(i).getClass().getName());
            if (!items.containsKey(i)) {
                if (this.slots.containsKey(i)) {
                    this.clear(i);
                }
            } else if (!this.setItem(i, (Item) ((Map) items).get(i), send)) {
                this.clear(i);
            }
        }


        ReloadInv();
    }

    @Override
    public boolean setItem(int index, Item item, boolean send) {
        item = item.clone();
//    System.out.println("INNNNEEDDDDEDEE >> "+index);
//    System.out.println("INNNNEEDDDDEDEE >> "+item.getClass().getName());
//        System.out.println("INNNNEEDDDDEDEE >> "+item.getCount());
//        System.out.println("INNNNEEDDDDEDEE >> "+item.getId());
        if (index >= 0 && index < getSize()) {
            if (item.getId() != 0 && item.getCount() > 0) {
                InventoryHolder holder = this.getHolder();
                if (holder instanceof Entity && !send) {
                    EntityInventoryChangeEvent ev = new EntityInventoryChangeEvent((Entity) holder, this.getItem(index), item, index);
                    Server.getInstance().getPluginManager().callEvent(ev);
                    if (ev.isCancelled()) {
                        this.sendSlot(index, (Collection) this.getViewers());
                        return false;
                    }

                    item = ev.getNewItem();
                }

                if (holder instanceof BlockEntity) {
                    ((BlockEntity) holder).setDirty();
                }
//
                Item old = this.getItem(index);
                slots.put(index, item.clone());
//            System.out.println("AAAAAAAAAAAAAAAAAAAA >> "+index);
//            System.out.println("AAAAAAAAAAAAAAAAAAAA >> "+old);
//            System.out.println("AAAAAAAAAAAAAAAAAAAA >> "+send);
//            this.onSlotChange(index, old, send);
                if (getHolder() != null) sendSlot(index, getHolder());
                return true;

//            return super.setItem(index,item,send);
            } else {
                return this.clear(index);
            }
        } else {
            return false;
        }
    }

    @Override
    public void sendContents(Player player) {
        ArrayList<Player> al = new ArrayList<>();
        al.add(player);
        this.sendContents(al.toArray(new Player[1]));
    }

//    public boolean setItem(int index, Item item, boolean send) {
//        item = item.clone();
//        if (index >= 0 && index < this.size) {
//            if (item.getId() != 0 && item.getCount() > 0) {
//                InventoryHolder holder = this.getHolder();
//                if (holder instanceof Entity && !send) {
//                    EntityInventoryChangeEvent ev = new EntityInventoryChangeEvent((Entity) holder, this.getItem(index), item, index);
//                    Server.getInstance().getPluginManager().callEvent(ev);
//                    if (ev.isCancelled()) {
//                        this.sendSlot(index, (Collection) this.getViewers());
//                        return false;
//                    }
//
//                    item = ev.getNewItem();
//                }
//
//                if (holder instanceof BlockEntity) {
//                    ((BlockEntity) holder).setDirty();
//                }
//
//                Item old = this.getItem(index);
//                System.out.println("SEEEEETTTTT >> "+index);
//                System.out.println("SEEEEETTTTT >> "+item.getClass().getName());
//                System.out.println("SEEEEETTTTT >> "+item.clone().getClass().getName());
//                System.out.println("SEEEEETTTTT >> "+slots.getClass().getName());
//                slots.put((Integer) index, item.clone());
//                this.onSlotChange(index, old, send);
//                return true;
//            } else {
//                slots.put(index, new ItemBlock(new BlockAir(), 0, 0));
//                this.onSlotChange(index, this.getItem(index), send);
//                System.out.println("MAN CLLEEAARRR");
////                return this.clear(index);
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }

    public void SendAllSlots(Player p) {
        ArrayList<Player> al = new ArrayList<>();
        al.add(p);
        for (int i = 0; i < getSize(); i++) {
            sendSlot(i, p);
        }
    }

    public void SetupPageNotEnoughMoney(SpawnerShopData aid) {
        CorePlayer cp = (CorePlayer) getHolder();
        StaticItems si = new StaticItems(Page);
        Item item = aid.getItem();
        Item deny = si.Deny.clone();
        Item deny2 = si.Deny.clone();
        CurrentPage = CurrentPageEnum.Confirm_Purchase_Not_Enough_Money;
        deny.setCustomName(TextFormat.RED + "Not Enough Money!");
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 9; ii++) {
                int key = (i * 9) + ii;
                if (ii != 4) {
                    //RED
                    setItem(key, deny.clone(), true);
                } else {
                    //White or Item
                    if (i == 2) {
                        //@TODO Get ITem
                        setItem(key, item, true);
                    } else if (i == 0) {
                        Item g = si.Gold.clone();
                        g.setCustomName(TextFormat.GOLD + " Your money: " + cp.getMoney());
                        setItem(key, g, true);
                    } else {
                        Item r = Item.get(160, 14);
                        r.setCustomName(TextFormat.RED + "Not Enough Money \n" + TextFormat.YELLOW + " Your Balance : " + cp.getMoney() + "\n" + TextFormat.AQUA + "Item Cost : " + aid.getPrice());
                        setItem(key, r, true);
                    }
                }
            }
        }
    }

    public void SetupPageToFinalConfirmItem(SpawnerShopData aid) {
        SetupPageToFinalConfirmItem(aid,1,false);
    }
    boolean SetupPageToFinalConfirmItemSell = false;
    //    int SetupPageToFinalConfirmItemCount = 0;
    public void SetupPageToFinalConfirmItem(SpawnerShopData aid, int count, boolean sell) {
        CurrentPage = CurrentPageEnum.Confirm_Purchase_Final;
        SetupPageToFinalConfirmItemSell = sell;
//        SetupPageToFinalConfirmItemCount = count;
        CorePlayer cp = (CorePlayer) getHolder();
        StaticItems si = new StaticItems(Page);
        Item item = aid.getItem().clone();
        item.setCount(count);
        Item confrim = si.Confirm.clone();
//        if(sell)confrim = si.ConfirmSell.clone();
        confrim.setCount(count);

            confrim.setCustomName("Buy " + aid.getPrettyString(count));
            confrim.setLore(TextFormat.AQUA + "Buy Price Per Item: " + TextFormat.GREEN + aid.getPrice(), TextFormat.AQUA + "Purchase Quantity: " + TextFormat.GREEN + count, TextFormat.GRAY + "------------------------", TextFormat.AQUA + "Purchase price: " + TextFormat.GREEN + aid.getPrice(count));

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

                    } else if (i == 0) {
                        Item g = si.Gold.clone();
                        g.setCustomName(TextFormat.GOLD + " Your money: " + cp.getMoney());
                        setItem(key, g, true);
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
        return this.clear(index, true);
    }

    @Override
    public boolean clear(int index, boolean send) {
//        System.out.println("AAAAAAAAAAAAAA" + index);
        if (this.slots.containsKey(index)) {
            Item item = new ItemBlock(new BlockAir(), null, 0);
            Item old = this.slots.get(index);
//            if (item.getId() != Item.AIR) {
//                setItem(index, item.clone());
//            } else {
            this.slots.remove(index);
//            }
//            this.onSlotChange(index, old, send);
        } else if (send) {
            Item item = new ItemBlock(new BlockAir(), null, 0);
            Item old = this.slots.get(index);
//            setItem(index, item.clone(),true);
//            this.onSlotChange(index, old, true);
            slots.remove(index);
        }
        if (getHolder() != null) sendSlot(index, getHolder());
//        System.out.println("CLEARRRR###" + index);
        return true;
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
    public Player getHolder() {
        return (Player) this.holder;
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
            case AuctionHouse.MainPageItemRef.Reload:
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
            CyberCoreMain.getInstance().getLogger().error("ERROR TRIED TO ADD " + slots.length);
            return this.addItem(I.toArray(new Item[45]));
        }
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

    @Override
    public InventoryType getType() {
        return InventoryType.DOUBLE_CHEST;
    }

//    @Override
//    public void sendContents(Player player) {
//        this.sendContents(new Player[]{player});
//    }


    public enum CurrentPageEnum {
        ItemPage,
        PlayerSellingPage,
        Expired,
        Confirm_Purchase,
        Confirm_Purchase_Not_Enough_Money, Confirm_Purchase_Final,

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
        public final Item ChestCancel;
        public final Item ChestSell;
        public final Item ChestBuy;
        public final Item Paper;
        public final Item Map;
        public final Item Confirm;
        public final Item ConfirmSell;
        public final Item AddX1;
        public final Item AddX10;
        public final Item AddX32;
        public final Item AddX64;
        public final Item AddX1N;
        public final Item AddX10N;
        public final Item AddX32N;
        public final Item AddX64N;
        public final Item RmvX1;
        public final Item RmvX1N;
        public final Item RmvX10;
        public final Item RmvX10N;
        public final Item RmvX32;
        public final Item RmvX32N;
        public final Item RmvX64;
        public final Item RmvX64N;
        public final Item Deny;
        public final Item Gold;
        public static final String KeyName = "SSHOPITEM";

        StaticItems() {
            this(-1);
        }

        StaticItems(int page) {
            CompoundTag T = new CompoundTag();
            T.putBoolean(KeyName, true);
            Gold = Item.get(ItemID.GOLD_INGOT, 0, 1);
            Gold.setCompoundTag(T);
            Gold.setCustomName(TextFormat.GOLD + " Your money: ");

            AddX1 = Item.get(Item.EMERALD_BLOCK);
            AddX1.setCompoundTag(T);
            AddX1.getNamedTag().putInt("ADD", 1);
            AddX1.setCount(1);
            AddX1.setCustomName(TextFormat.GREEN + " Buy 1");

            AddX10 = Item.get(Item.EMERALD_BLOCK);
            AddX10.setCompoundTag(T);
            AddX10.getNamedTag().putInt("ADD", 10);
            AddX10.setCount(10);
            AddX10.setCustomName(TextFormat.GREEN + " Buy 10");


            AddX32 = Item.get(Item.EMERALD_BLOCK);
            AddX32.setCompoundTag(T);
            AddX32.getNamedTag().putInt("ADD", 32);
            AddX32.setCount(32);
            AddX32.setCustomName(TextFormat.GREEN + " Buy 32");

            AddX64 = Item.get(Item.EMERALD_BLOCK);
            AddX64.setCompoundTag(T);
            AddX64.getNamedTag().putInt("ADD", 64);
            AddX64.setCount(64);
            AddX64.setCustomName(TextFormat.GREEN + " Buy 64");

            AddX1N = Item.get(Item.IRON_BLOCK);
            AddX1N.setCompoundTag(T);
            AddX1N.getNamedTag().putInt("ADD", 1);
            AddX1N.setCount(1);
            AddX1N.setCustomName(TextFormat.GREEN + "Cannot Buy 1");

            AddX10N = Item.get(Item.IRON_BLOCK);
            AddX10N.setCompoundTag(T);
            AddX10N.getNamedTag().putInt("ADD", 10);
            AddX10N.setCount(10);
            AddX10N.setCustomName(TextFormat.GREEN + "Cannot Buy 10");


            AddX32N = Item.get(Item.IRON_BLOCK);
            AddX32N.setCompoundTag(T);
            AddX32N.getNamedTag().putInt("ADD", 32);
            AddX32N.setCount(32);
            AddX32N.setCustomName(TextFormat.GREEN + "Cannot Buy 32");

            AddX64N = Item.get(Item.IRON_BLOCK);
            AddX64N.setCompoundTag(T);
            AddX64N.getNamedTag().putInt("ADD", 64);
            AddX64N.setCount(64);
            AddX64N.setCustomName(TextFormat.GREEN + "Cannot Buy 64");


            RmvX1 = Item.get(Item.REDSTONE_BLOCK);
            RmvX1N = Item.get(Item.IRON_BLOCK);
            RmvX1.setCompoundTag(T);
            RmvX1.getNamedTag().putInt("RMV", 1);
            RmvX1.setCustomName(TextFormat.GREEN + "Sell 1");
            RmvX1N.setCompoundTag(T);
            RmvX1N.getNamedTag().putInt("RMV", 1);
            RmvX1N.setCustomName(TextFormat.RED + "Can Not Sell 1");


            RmvX10 = Item.get(Item.REDSTONE_BLOCK);
            RmvX10N = Item.get(Item.IRON_BLOCK);
            RmvX10.setCompoundTag(T);
            RmvX10.setCount(10);
            RmvX10.getNamedTag().putInt("RMV", 10);
            RmvX10.setCustomName(TextFormat.GREEN + "Sell 10");
            RmvX10N.setCompoundTag(T);
            RmvX10N.getNamedTag().putInt("RMV", 1);
            RmvX10N.setCustomName(TextFormat.RED + "Can Not Sell 1");


            RmvX32 = Item.get(Item.REDSTONE_BLOCK);
            RmvX32.setCompoundTag(T);
            RmvX32.setCount(32);
            RmvX32.getNamedTag().putInt("RMV", 32);
            RmvX32.setCustomName(TextFormat.GREEN + "Sell 32");
            RmvX32N = Item.get(Item.IRON_BLOCK);
            RmvX32N.setCompoundTag(T);
            RmvX32N.setCount(32);
            RmvX32N.getNamedTag().putInt("RMV", 32);
            RmvX32N.setCustomName(TextFormat.GREEN + "Can Not Sell 32 From Cart");


            RmvX64 = Item.get(Item.REDSTONE_BLOCK);
            RmvX64.setCompoundTag(T);
            RmvX64.setCount(64);
            RmvX64.getNamedTag().putInt("RMV", 64);
            RmvX64.setCustomName(TextFormat.GREEN + "Sell 64");
            RmvX64N = Item.get(Item.IRON_BLOCK);
            RmvX64N.setCompoundTag(T);
            RmvX64N.setCount(64);
            RmvX64N.getNamedTag().putInt("RMV", 64);
            RmvX64N.setCustomName(TextFormat.GREEN + "Can Not Sell 64 From Cart");


            Confirm = Item.get(Item.EMERALD_BLOCK);
            Confirm.setCompoundTag(T);
            Confirm.setCustomName(TextFormat.GREEN + "Confirm Cart Purchase");
            ConfirmSell = Item.get(Item.EMERALD_BLOCK);
            ConfirmSell.setCompoundTag(T);
            ConfirmSell.setCustomName(TextFormat.GREEN + "Confirm Cart Sale");
            Deny = Item.get(Item.REDSTONE_BLOCK);
            Deny.setCompoundTag(T);
            Deny.setCustomName(TextFormat.RED + "Cancel Cart Purchase");
            Diamond = Item.get(Item.DIAMOND);
            Diamond.setCompoundTag(T);
            Diamond.setCustomName(TextFormat.GOLD + "" + TextFormat.BOLD + "Toggle Admin Mode" + TextFormat.RESET + "\n");
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
                    TextFormat.GREEN + "" + TextFormat.BOLD + "Refresh Page\n"
                            + TextFormat.GRAY + " Current Page " + page
            );
            if (page != -1) Netherstar.getNamedTag().putInt("page", page);
            ChestSell = Item.get(Item.CHEST);
            ChestSell.setCompoundTag(T);
            ChestSell.setCustomName(
                    TextFormat.GOLD + "" + TextFormat.BOLD + "Sell Selection"
            );ChestCancel = Item.get(Item.CHEST);
            ChestCancel.setCompoundTag(T);
            ChestCancel.setCustomName(
                    TextFormat.GOLD + "" + TextFormat.BOLD + "Cancel Selection"
            );
            ChestBuy = Item.get(Item.CHEST);
            ChestBuy.setCompoundTag(T);
            ChestBuy.setCustomName(
                    TextFormat.GOLD + "" + TextFormat.BOLD + "Buy Section"
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
        public static final int ToggleAdmin = Size - 6;
        public static final int Reload = Size - 5;
        public static final int Catagories = Size - 4;
        //        public static final int MULL = Size - 3;
        public static final int ListItem = Size - 2;
        public static final int NextPage = Size - 1;
    }
}

