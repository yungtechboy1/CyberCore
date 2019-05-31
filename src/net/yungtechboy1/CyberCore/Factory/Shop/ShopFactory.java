package net.yungtechboy1.CyberCore.Factory.Shop;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockChest;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Data.AuctionItemData;
import net.yungtechboy1.CyberCore.Data.ShopSQL;

import java.io.IOException;
import java.nio.ByteOrder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ShopFactory implements Listener {
    private final ShopSQL SQL;
    CyberCoreMain CCM;
    /**
     * Settings:
     * Key: {
     * id:
     * meta:
     * count:
     * namedtag:
     * cost:
     * soldby:
     * }
     */
//        Config Settings;


    ArrayList<AuctionItemData> items = new ArrayList<>();
    private ArrayList<ShopMysqlData> ShopCache = null;
    private CoolDown ShopCacheReset = null;

    public ShopFactory(CyberCoreMain CCM) {
        this.CCM = CCM;
//            Settings = new Config(new File(CCM.getDataFolder(), "Auctions.yml"), Config.YAML);
        SQL = new ShopSQL(CCM);
    }

    //                new CoolDown().setTimeSecs(30,0);//30 Mins
    public ArrayList<ShopMysqlData> GetAllItems() {
        if (ShopCache != null) {
            if (ShopCacheReset != null) {
                if (ShopCacheReset.isValid()) {
                    System.out.println("Using Cache!!!");
                    return ShopCache;
                } else {
                    ShopCache = null;
                    ShopCacheReset = null;
                }
            }
        }
        ArrayList<ShopMysqlData> is = new ArrayList<>();
        try {
            ResultSet rs = SQL.ExecuteQuerySQLite("SELECT * FROM `Shop` WHERE `enabled` = 1");
            if (rs != null) {
                try {
                    while (rs.next()) {
                        ShopMysqlData aid = new ShopMysqlData(rs);
                        System.out.println(">>>!!!+" + aid);
                        is.add(aid);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    CCM.getLogger().info("Error loading Shop Items3!");
                    return null;
                }
                CCM.getLogger().info("Loaded " + is.size() + " Items for AH1");
                ArrayList<ShopMysqlData> t = (ArrayList<ShopMysqlData>) is.clone();
                if (t != null) {
                    ShopCache = t;
                    ShopCacheReset = new CoolDown("Shop", 0, 15);
                    //Set Cache
                }
                return is;
            }
        } catch (Exception e) {
            CCM.getLogger().error("SSSSHHHHH ERRRORRROOROORORR", e);
        }
        return is;
    }


    //@Todo
//    public ArrayList<Item> getSoldItems() {
//        ArrayList<Item> is = new ArrayList<>();
//        ResultSet rs = null;//ExecuteQuerySQLite("SELECT * FROM `auctions` WHERE `purchased` != 1");
//        if (rs != null) {
//            try {
//                while (rs.next()) {
//                    int id = rs.getInt("id");
//                    int item_id = rs.getInt("item-id");
//                    int item_meta = rs.getInt("item-meta");
//                    int item_count = rs.getInt("item-count");
//                    byte[] namedtag = rs.getString("namedtag").getBytes();
//                    int cost = rs.getInt("cost");
//                    String soldby = rs.getString("soldby");
//
//                    Item i = Item.get(item_id, item_meta, item_count);
//                    i.setCompoundTag(namedtag);
//
//                    CompoundTag tag = i.getNamedTag();
//                    if (tag == null) tag = new CompoundTag();
//
//                    if (!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
//                        tag.getCompound("display").putString("Name2", i.getCustomName());
//
//                    if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
//                        tag.getCompound("display").putInt("keyid", id);
//                        tag.getCompound("display").putInt("cost", cost);
//                    } else {
//                        tag.putCompound("display", (new CompoundTag("display")).putInt("keyid", id).putInt("cost", cost));
//                    }
//                    i.setNamedTag(tag);
//
//
//                    String cn = i.getCustomName();
//
//                    if (cn.equalsIgnoreCase("")) cn = i.getName();
//
//                    cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
//                            "-------------" + TextFormat.RESET + "\n" +
//                            TextFormat.GREEN + "$" + cost + TextFormat.RESET + "\n" +
//                            TextFormat.GOLD + "Sold By: " + soldby
//                    // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
//                    ;
//
//                    i.setCustomName(cn);
//
//                    is.add(i);
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                CCM.getLogger().info("ERror loading Items!");
//                return null;
//            }
//            CCM.getLogger().info("Loaded " + is.size() + " Items for AH");
//            items = is;
//        }
//        return null;
//    }

    public ArrayList<Item> getListOfItems() {
        ArrayList<Item> il = new ArrayList<>();
        for (ShopMysqlData ahd : GetAllItems()) {
            il.add(ahd.getItem());
        }
        return il;
    }


//    public Item getItem(int page, int slot) {
//        int stop = page * 45;
//        int start = stop - 45;
//        int key = start + slot;
//        return items.get(key);
//    }


    public HashMap<Integer, Item> getPageHash(int page) {
        HashMap<Integer, Item> list = new HashMap<Integer, Item>();
        int k = 0;
        for (Item i : getPage(page)) {
            list.put(k, i);
            k++;
        }

        return list;

    }


    public ArrayList<ShopMysqlData> GetAllItemsDataLimit(int start, int stop) {
        ArrayList<ShopMysqlData> il = new ArrayList<>();
        ArrayList<ShopMysqlData> a = GetAllItems();
        for (int i = start; i < stop; i++) {
            if (i >= a.size()) break;
            ShopMysqlData smd = a.get(i);
            if (smd != null) il.add(smd);
        }
        return il;
    }

    public ArrayList<Item> GetAllItemsLimit(int start, int stop) {
        ArrayList<Item> il = new ArrayList<>();
        ArrayList<ShopMysqlData> a = GetAllItems();
        for (int i = start; i < stop; i++) {
            if (i >= a.size()) break;
            ShopMysqlData smd = a.get(i);
            if (smd != null) {
                il.add(smd.getItem());
            }
        }
        return il;
    }
//
//    public ArrayList<ShopMysqlData> GetAllItemsLimitData(int start, int stop) {
//        ArrayList<ShopMysqlData> il = new ArrayList<>();
//        for (ShopMysqlData ahd : GetAllItemsDataLimit(start, stop)) {
//            il.add(ahd);
//        }
//        return il;
//    }

    public Item getItemFromPage(int page, int slot) {
        if (slot > 45) {
            CCM.getLogger().error("ERROR! Slot out of range! E443 Slot:" + slot);
            return null;
        }
        Item[] list = getPage(page);
        if (slot > list.length) {
            CCM.getLogger().error("ERROR! Selected Slot out of List Range! E33342 SLOT:" + slot + " OF " + list.length);
            return null;
        }
        Item s = list[slot];
        if (s.getId() == Item.AIR) return null;
        return s;
    }


    public Item[] getPage(int page) {
        int stop = page * 45;
        int start = stop - 45;
        ArrayList<Item> list2 = GetAllItemsLimit(start, stop);
        if (45 > list2.size()) {
            ArrayList<Item> a = new ArrayList<Item>();
            for (int i = 0; i < 45; i++) {
//                list2.iterator().n
                if (list2.size() > i && list2.get(i) != null) {
//                    System.out.println("ADDING ACTUAL ITEM || " + list2.get(i).getId());
                    a.add(list2.get(i));
                } else {
                    a.add(new ItemBlock(new BlockAir(), 0, 0));
//                    System.out.println("ADDING AIR ||");
                }
            }

            return a.toArray(new Item[45]);
        } else {
            return list2.toArray(new Item[45]);
        }
    }

    public ArrayList<ShopMysqlData> getPageData(int page) {
        int stop = page * 45;
        int start = stop - 45;
        ArrayList<ShopMysqlData> list2 = GetAllItemsDataLimit(start, stop);
        if (45 > list2.size()) {
            ArrayList<ShopMysqlData> a = new ArrayList<ShopMysqlData>();
            for (int i = 0; i < 45; i++) {
//                list2.iterator().n
                if (list2.size() > i && list2.get(i) != null) {
                    a.add(list2.get(i));
                } else {
                    a.add(null);
//                    System.out.println("ADDING AIR ||");
                }
            }

            return a;
        } else {
            return list2;
        }
    }


    public void OpenShop(CorePlayer p, Integer pg) {
        SpawnFakeBlockAndEntity(p, new CompoundTag().putString("CustomName", "SHOP!"));
        ShopInv b = new ShopInv(p, CCM, p, pg);
        CyberCoreMain.getInstance().getLogger().info(b.getContents().values().size() + " < SIZZEEE" + b.getSize());
        CyberCoreMain.getInstance().getServer().getScheduler().scheduleDelayedTask(new OpenShop(p, b), 5);
//        b.open()
    }

    public void SpawnFakeBlockAndEntity(Player to, CompoundTag data) {

        SpawnBlock(to, new BlockChest());
        SpawnBlockEntity(to, data);

    }

    public void SpawnBlock(Player to, Block b) {
        UpdateBlockPacket a = new UpdateBlockPacket();
        UpdateBlockPacket aa = new UpdateBlockPacket();
        a.x = aa.x = to.getFloorX();
        a.y = aa.y = to.getFloorY() - 2;
        a.z = aa.z = to.getFloorZ();
        aa.z += 1;
        a.flags = aa.flags = UpdateBlockPacket.FLAG_ALL;
        a.blockRuntimeId = aa.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(b.getFullId());
        to.dataPacket(a);
        to.dataPacket(aa);
    }

    public void SpawnBlockEntity(Player to, CompoundTag data) {
        BlockEntityDataPacket bedp = new BlockEntityDataPacket();
        BlockEntityDataPacket bedp2 = new BlockEntityDataPacket();
        bedp2.x = bedp.x = to.getFloorX();
        bedp2.y = bedp.y = to.getFloorY() - 2;
        bedp2.z = bedp.z = to.getFloorZ();
        bedp2.z += 1;
        try {
            bedp.namedTag = NBTIO.write(data, ByteOrder.LITTLE_ENDIAN, true);
            bedp2.namedTag = NBTIO.write(new CompoundTag().putInt("pairx", bedp.x).putInt("pairz", bedp.z), ByteOrder.LITTLE_ENDIAN, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        to.dataPacket(bedp);
        to.dataPacket(bedp2);
    }

//    @EventHandler(ignoreCancelled = true)
//    public void TTE(InventoryClickEvent event) {
//
//        System.out.println("++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("++++++++++++++++++++++++++++++++++++++++");
//        System.out.println(event.getSlot());
//        System.out.println(event.getInventory().getClass().getName());
//
//        System.out.println("CALLLLCLLIICCCKKK");
//                System.out.println("CALLLL SLOTCCCCCCCC");
//                int slott = event.getSlot();
//
////                sca.getInventory()
//
//                Inventory inv = event.getInventory();
//                System.out.println("CHECK INNNNNVVVVVVV " + inv.getClass().getName());
////                if (inv.isEmpty()) return;
//
//                System.out.println("NEEEEEEE" + inv.getClass().getTypeName());
//                if (inv instanceof PlayerInventory) {
//
//                }
//                if (inv instanceof AuctionHouse) {
//
//                    AuctionHouse ah = (AuctionHouse) inv;
////                    if(!ah.Init)return;
//                    System.out.println(slott + " || " + ah.getHolder().getName() + " || " + ah.getHolder().getClass().getName());
//                    CorePlayer ccpp = (CorePlayer) ah.getHolder();
//                    int slot = slott;
////                    event.setCancelled();
//                    if (slot < 5 * 9) {
//                        System.out.println("TOP INV");
//                        //TODO CONFIRM AND SHOW ITEM
//                        if (!ah.ConfirmPurchase) {
//                            ah.ConfirmItemPurchase(slot);
//                            event.setCancelled();
//                            System.out.println("SSSSSSSSSSSSCPPPPPPPP");
////                        ccpp.AH.ConfirmItemPurchase(slot);
//                        } else {
//                            System.out.println("CPPPPPPPP");
//                            Item si = ah.getContents().get(slot);
//                            if (si != null) {
//                                if (si.getId() == BlockID.EMERALD_BLOCK) {
//                                    System.out.println("CONFIRM PURCHASE!!!!!!!");
////                                    ah.AF.PurchaseItem((CorePlayer) ah.getHolder(), Page, slot);
//                                } else if (si.getId() == BlockID.REDSTONE_BLOCK) {
//                                    System.out.println("DENCLINE PURCHASE!!!!!!!!");
//                                }
//                            }
//                        }
//                    } else {
//                        switch (slot) {
//                            case AuctionHouse.MainPageItemRef.LastPage:
//                                Page--;
//                                if (Page < 1) Page = 1;
//                                Server.getInstance().getPlayerExact(ah.getHolder().getName()).sendPopup("PAGE SET TO " + Page);
//                                ah.clearAll();
//                                ah.addItem(getPage(Page));
//                                ah.ReloadInv();
//                                ah.sendContents(ah.getHolder());
//
//                                break;
//                            case AuctionHouse.MainPageItemRef.NextPage:
//                                Server.getInstance().getPlayerExact(ah.getHolder().getName()).sendTip("PAGE SET TO " + Page);
//                                Page++;
//
//                                ah.clearAll();
//                                ah.addItem(getPage(Page));
//                                ah.ReloadInv();
//                                ah.sendContents(ah.getHolder());
//                                break;
//                            case AuctionHouse.MainPageItemRef.Search:
//                                break;
//                            case AuctionHouse.MainPageItemRef.Reload:
//                                ah.clearAll();
//                                ah.addItem(getPage(Page));
//                                ah.ReloadInv();
//                                ah.SendAllSlots(ah.getHolder());
//                                break;
//                            case AuctionHouse.MainPageItemRef.PlayerSelling:
//                                ah.setContents(getPageHash(Page, ah.getHolder().getName()), true);
//                                ah.sendContents((Player) ah.getHolder());
//                                ah.SendAllSlots((Player) ah.getHolder());
//                                event.setCancelled(false);
//                                break;
//
//                        }
//                    }
//                }
//            }


    //TODO MAke Pages with new API
    @EventHandler(ignoreCancelled = true)
    public void TE(InventoryTransactionEvent event) {
        System.out.println("CALLLL");
        InventoryTransaction transaction = event.getTransaction();
        Set<InventoryAction> traa = transaction.getActions();
        for (InventoryAction t : traa) {
            System.out.println("CALLLL TTTTTTTTTTTTTTTTTTT" + t.getClass().getName());
            if (t instanceof SlotChangeAction) {
                System.out.println("CALLLL SLOTCCCCCCCC");
                SlotChangeAction sca = (SlotChangeAction) t;

//                sca.getInventory()

                Inventory inv = sca.getInventory();
                System.out.println("CHECK INNNNNVVVVVVV " + inv.getClass().getName());
//                if (inv.isEmpty()) return;

                System.out.println("NEEEEEEE" + inv.getClass().getTypeName());
                if (inv instanceof PlayerInventory) {

                }
                if (inv instanceof ShopInv) {

                    ShopInv ah = (ShopInv) inv;
//                    if(!ah.Init)return;
                    System.out.println(sca.getSlot() + " || " + ah.getHolder().getName() + " || " + ah.getHolder().getClass().getName());
                    CorePlayer ccpp = (CorePlayer) ah.getHolder();
                    int slot = sca.getSlot();
//                    event.setCancelled();
                    event.setCancelled();
                    if (slot < 5 * 9) {
                        System.out.println("TOP INV");
                        //TODO CONFIRM AND SHOW ITEM
                        if (!ah.ConfirmPurchase) {
                            ah.ConfirmItemPurchase(slot);
//                        ccpp.AH.ConfirmItemPurchase(slot);
                        } else {
                            if (ah.CurrentPage == ShopInv.CurrentPageEnum.PlayerSellingPage) {
                                int sx = slot % 9;
                                int sy = (int) Math.floor(slot / 9);
                                Item is = ah.getItem(slot);
                                boolean isi = false;
                                int isc = is.getCount();
                                if (is != null && is.getId() != 0) {
                                    if (is.getId() == Item.IRON_BLOCK) isi = true;
                                    System.out.println("Selected Slot SX:" + sx + " | SY:" + sy);
                                    if (sy != 0 && sy != 5 && sx != 4 && !isi) {
                                        if (sx < 4) {
                                            //Sell
                                            ah.SetupPageToFinalConfirmItem(ah.MultiConfirmData, isc, true);

                                        } else {
                                            //Buy
                                            ah.SetupPageToFinalConfirmItem(ah.MultiConfirmData, isc, false);
                                        }
                                    }
                                }
                                event.setCancelled();
                                return;
                            } else {
                                Item si = ah.getContents().get(slot);
                                if (si != null) {
                                    if (ah.getCurrentPage() == ShopInv.CurrentPageEnum.Confirm_Purchase_Not_Enough_Money) {
                                        ah.setPage(1);
                                        ah.ClearConfirmPurchase();
                                        //Back Home
                                        break;
                                    } else {
                                        System.out.println("CPPPPPPPP");

                                        if (si.getId() == BlockID.EMERALD_BLOCK) {
                                            System.out.println("CONFIRM PURCHASE!!!!!!!");
                                            ah.AF.PurchaseItem((CorePlayer) ah.getHolder(), ah.getPage(), ah.ConfirmPurchaseSlot, si.getCount());
                                            break;
                                        } else if (si.getId() == BlockID.REDSTONE_BLOCK) {
                                            System.out.println("DENCLINE PURCHASE!!!!!!!!");
                                            ah.setPage(1);
                                            ah.ClearConfirmPurchase();
                                            break;
                                        } else {
                                            ah.setPage(1);
                                            System.out.println("UNKNOWNMNNN!!!!!!!!");
                                            ah.ClearConfirmPurchase();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        switch (slot) {
                            case ShopInv.MainPageItemRef.LastPage:
                                ah.GoToPrevPage();
                                break;
                            case ShopInv.MainPageItemRef.NextPage:
                                ah.GoToNextPage();
                                break;
                            case ShopInv.MainPageItemRef.Search:
                                break;
                            case ShopInv.MainPageItemRef.Reload:
                                ah.ReloadCurrentPage();
                                break;
                            case ShopInv.MainPageItemRef.Catagories:
                                ah.DisplayCatagories();
                                break;
                            case ShopInv.MainPageItemRef.ToggleAdmin:
                                ah.AdminMode = !ah.AdminMode;
                                event.setCancelled(false);
                                ah.ReloadCurrentPage();
                                break;

                        }
                    }
                }
            }
        }
    }

    //SetupPageToFinalConfirmItemSell
    private void PurchaseItem(CorePlayer holder, int page, int slot, int count) {
        ShopMysqlData aid = getItemFrom(page, slot);
        if (aid == null) {
            System.out.println("ERROR IN SELECTION!!!!");
        } else if (aid.getPrice(count) > holder.GetMoney()) {
            holder.Shop.SetupPageNotEnoughMoney(aid);
            return;
        }
//        int c = holder.Shop.SetupPageToFinalConfirmItemCount;
        if (holder.Shop.SetupPageToFinalConfirmItemSell) {
            holder.AddMoney(aid.getSellPrice(count));
            Item i = aid.getItem(true);
            i.setCount(count);
            holder.getInventory().removeItem(i);
            holder.Shop.ClearConfirmPurchase();
            holder.Shop.setPage(1);
        } else {

//        SetBought(aid.getMasterid());
            holder.TakeMoney(aid.getPrice(count));
            Item i = aid.getItem(true);
            i.setCount(count);
            holder.getInventory().addItem(i);
            holder.Shop.ClearConfirmPurchase();
            holder.Shop.setPage(1);
        }
    }

    public ShopMysqlData getItemFrom(int page, int slot) {
        ArrayList<ShopMysqlData> smd = getPageData(page);
        if (smd.size() < slot) return null;
        return smd.get(slot);
    }

    public void additem(Item i, CorePlayer p, int cost) {
        AuctionItemData aid = new AuctionItemData(i, cost, p);
        SQL.AddItemForSale(aid);
    }

}

