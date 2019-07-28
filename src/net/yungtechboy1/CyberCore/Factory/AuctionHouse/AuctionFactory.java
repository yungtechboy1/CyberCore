package net.yungtechboy1.CyberCore.Factory.AuctionHouse;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockChest;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.PlayerCursorInventory;
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
import cn.nukkit.utils.Config;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Data.AHSqlite;
import net.yungtechboy1.CyberCore.Data.AuctionItemData;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static net.yungtechboy1.CyberCore.Factory.AuctionHouse.AuctionHouse.CurrentPageEnum.*;

/**
 * Created by carlt_000 on 2/22/2017.
 */
public class AuctionFactory implements Listener {
    private final AHSqlite Sqlite;
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
    Config Settings;


    ArrayList<AuctionItemData> items = new ArrayList<>();

    public AuctionFactory(CyberCoreMain CCM) {
        this.CCM = CCM;
        Settings = new Config(new File(CCM.getDataFolder(), "Auctions.yml"), Config.YAML);
        Sqlite = new AHSqlite(CCM);
    }


    public ArrayList<AuctionItemData> GetAllItems() {
        ArrayList<AuctionItemData> is = new ArrayList<>();
        try {
            ResultSet rs = Sqlite.ExecuteQuerySQLite("SELECT * FROM `AuctionHouse` WHERE `purchased` != 1");
            if (rs != null) {
                try {
                    while (rs.next()) {
                        AuctionItemData aid = new AuctionItemData(rs);
                        is.add(aid);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    CCM.getLogger().info("Error loading Items3!");
                    return null;
                }
                CCM.getLogger().info("Loaded " + is.size() + " Items for AH1");
                return is;
            }
        } catch (Exception e) {
            CCM.getLogger().error("ERRRORRROOROORORR", e);
        }
        return is;
    }


    public ArrayList<AuctionItemData> GetAllItemsLimit(int start, int stop, String seller) {
        ArrayList<AuctionItemData> is = new ArrayList<>();
        try {
            ResultSet rs;
            if (seller != null)
                rs = Sqlite.ExecuteQuerySQLite("SELECT * FROM `AuctionHouse` WHERE `soldbyn` = '" + seller + "' AND `purchased` != true LIMIT " + start + "," + stop);
            else
                rs = Sqlite.ExecuteQuerySQLite("SELECT * FROM `AuctionHouse` WHERE `purchased` != true LIMIT " + start + "," + stop);
            if (rs != null) {
                try {
                    while (rs.next()) {
                        AuctionItemData aid = new AuctionItemData(rs);
                        is.add(aid);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    CCM.getLogger().info("Error loading Items33!");
                    return null;
                }
                CCM.getLogger().info("Loaded " + is.size() + " Items for AH2");
                return is;
            }
        } catch (Exception e) {
            CCM.getLogger().error("ERRRORRROOROORORR", e);
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
        for (AuctionItemData ahd : GetAllItems()) {
            il.add(ahd.MakePretty());
        }
        return il;
    }

    public ArrayList<Item> getListOfItemsBetween(int start, int stop) {
        return getListOfItemsBetween(start, stop, null);
    }

    public ArrayList<AuctionItemData> getListOfAIDBetween(int start, int stop) {
        return getListOfAIDBetween(start, stop, null);
    }

    public ArrayList<AuctionItemData> getListOfAIDBetween(int start, int stop, String seller) {
        ArrayList<AuctionItemData> il = new ArrayList<>();
//        if(GetAllItemsLimit(start, stop) == null)System.out.println("YEAAAAAAAAAA THISSSSS SSSHSHHHHIIIITTT NUUUULLLLLLIINNNNN~!!!!!!!!");
        for (AuctionItemData ahd : GetAllItemsLimit(start, stop, seller)) {
            il.add(ahd);
        }
        return il;
    }

    public ArrayList<Item> getListOfItemsBetween(int start, int stop, String seller) {
        ArrayList<Item> il = new ArrayList<>();
//        if(GetAllItemsLimit(start, stop) == null)System.out.println("YEAAAAAAAAAA THISSSSS SSSHSHHHHIIIITTT NUUUULLLLLLIINNNNN~!!!!!!!!");
        for (AuctionItemData ahd : GetAllItemsLimit(start, stop, seller)) {
            il.add(ahd.MakePretty());
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
        return getPageHash(page, null);
    }

    public HashMap<Integer, Item> getPageHash(int page, String seller) {
        HashMap<Integer, Item> list = new HashMap<Integer, Item>();
        int k = 0;
        if (seller == null) {
            for (Item i : getPage(page)) {
                list.put(k, i);
                k++;
            }
        } else {
            for (Item i : SetPagePlayerSelling(seller, page)) {
                list.put(k, i);
                k++;
            }
        }
        return list;

    }

    public Item[] SetPagePlayerSelling(String seller, int page) {
        int stop = page * 45;
        int start = stop - 45;
//        System.out.println("START = " + start + ", STOP = " + stop + " Seller" + seller);
        ArrayList<Item> list2 = getListOfItemsBetween(start, stop, seller);
        if (45 > list2.size()) {
            ArrayList<Item> a = new ArrayList<Item>();
            for (int i = 0; i < 45; i++) {
//                list2.iterator().n
                if (list2.size() > i && list2.get(i) != null) {
//                    System.out.println("ADDING ACTUAL ITEM " + list2.get(i).getId());
                    a.add(list2.get(i));
                } else {
                    a.add(new ItemBlock(new BlockAir(), 0, 0));
//                    System.out.println("ADDING AIR");
                }
            }

            return a.toArray(new Item[45]);
        } else {
            return list2.toArray(new Item[45]);
        }
    }

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

    public AuctionItemData getAIDFromPage(int page, int slot) {
        if (slot > 45) {
            CCM.getLogger().error("ERROR! Slot out of range! E443 Slot:" + slot);
            return null;
        }
        AuctionItemData[] list = getPageAID(page);
        if (slot > list.length) {
            CCM.getLogger().error("ERROR! Selected Slot out of List Range! E33342 SLOT:" + slot + " OF " + list.length);
            return null;
        }
        return list[slot];
    }

    public AuctionItemData[] getPageAID(int page) {
        int stop = page * 45;
        int start = stop - 45;
//        System.out.println("START = " + start + ", STOP = " + stop);
        ArrayList<AuctionItemData> list2 = getListOfAIDBetween(start, stop);
        if (45 > list2.size()) {
            ArrayList<AuctionItemData> a = new ArrayList<AuctionItemData>();
            for (int i = 0; i < 45; i++) {
//                list2.iterator().n
                if (list2.size() > i && list2.get(i) != null) {
//                    System.out.println("ADDING ACTUAL ITEM " + list2.get(i).toString());
                    a.add(list2.get(i));
                } else {
                    a.add(null);
//                    System.out.println("ADDING AIR");
                }
            }

            return a.toArray(new AuctionItemData[45]);
        } else {
            return list2.toArray(new AuctionItemData[45]);
        }
    }

    public Item[] getPage(int page) {
        int stop = page * 45;
        int start = stop - 45;
//        System.out.println("START = " + start + ", STOP = " + stop);
        ArrayList<Item> list2 = getListOfItemsBetween(start, stop);
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
//
//        ArrayList<Item> list = new ArrayList<>();
//
//        for (int a = start; a < list2.size(); a++) {
//            if (a >= stop) break;
//            Item newitem = list2.get(a).clone();
//            System.out.println(newitem.toString());
//            if (newitem == null) list.add(new ItemBlock(new BlockAir(), (Integer) null, 0));
//            else list.add(newitem);
//        }
//
//        return (Item[]) list.toArray();

        /*
        1 => 0 | 44
        2 => 45 | 89
         */
    }

    public void OpenAH(CorePlayer p, Integer pg) {
        SpawnFakeBlockAndEntity(p, new CompoundTag().putString("CustomName", "Auction House!"));
        AuctionHouse b = new AuctionHouse(p, CCM, p, pg);
        CyberCoreMain.getInstance().getLogger().info(b.getContents().values().size() + " < SIZZEEE" + b.getSize());
        CyberCoreMain.getInstance().getServer().getScheduler().scheduleDelayedTask(new OpenAH(p, b), 5);
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
//    @EventHandler(ignoreCancelled = true)
//    public void TE(InventoryTransactionEvent event) {
////        System.out.println("CALLLL");
//        InventoryTransaction transaction = event.getTransaction();
//        Set<InventoryAction> traa = transaction.getActions();
//        for (InventoryAction t : traa) {
////            System.out.println("CALLLL TTTTTTTTTTTTTTTTTTT" + t.getClass().getName());
//            if (t instanceof SlotChangeAction) {
////                System.out.println("CALLLL SLOTCCCCCCCC");
//                SlotChangeAction sca = (SlotChangeAction) t;
//
////                sca.getInventory()
//
//                Inventory inv = sca.getInventory();
//                System.out.println("CHECK INNNNNVVVVVV2222222222222V " + inv.getClass().getName());
////                if (inv.isEmpty()) return;
//
////                System.out.println("NEEEEEEE" + inv.getClass().getTypeName());
//                if (inv instanceof PlayerInventory) {
//
//                }else if(inv instanceof PlayerCursorInventory){
//                    event.setCancelled();
//                    transaction.getSource().sendAllInventories();
//                    System.out.println("+++++>"+transaction.getSource().getCursorInventory());
//                    System.out.println("+++++>"+transaction.getSource().getCursorInventory().slots);
//                }
//                if (inv instanceof AuctionHouse) {
//
//                    AuctionHouse ah = (AuctionHouse) inv;
////                    if(!ah.Init)return;
//                    System.out.println(sca.getSlot() + " || " + ah.getHolder().getName() + " || " + ah.getHolder().getClass().getName());
//                    CorePlayer ccpp = (CorePlayer) ah.getHolder();
//                    int slot = sca.getSlot();
////                    event.setCancelled();
//                    event.setCancelled();
//                    if (slot < 5 * 9) {
//                        System.out.println("TOP INV");
//                        //TODO CONFIRM AND SHOW ITEM
//                        if (!ah.ConfirmPurchase) {
//                            ah.ConfirmItemPurchase(slot);
//                            System.out.println("SSSSSSSSSSSSCPPPPPPPP");
////                        ccpp.AH.ConfirmItemPurchase(slot);
//                        } else {
//                            Item si = ah.getContents().get(slot);
//                            if (si != null) {
//                                if (ah.getCurrentPage() == Confirm_Purchase_Not_Enough_Money) {
//                                    ah.setPage(1);
//                                    ah.ClearConfirmPurchase();
//                                    //Back Home
//                                    break;
//                                } else {
//                                    System.out.println("CPPPPPPPP");
//
//                                    if (si.getId() == BlockID.EMERALD_BLOCK) {
//                                        System.out.println("CONFIRM PURCHASE!!!!!!!");
//                                        ah.AF.PurchaseItem((CorePlayer) ah.getHolder(), ah.getPage(), ah.ConfirmPurchaseSlot);
//                                        break;
//                                    } else if (si.getId() == BlockID.REDSTONE_BLOCK) {
//                                        System.out.println("DENCLINE PURCHASE!!!!!!!!");
//                                        ah.setPage(1);
//                                        ah.ClearConfirmPurchase();
//                                        break;
//                                    } else {
//                                        ah.setPage(1);
//                                        System.out.println("UNKNOWNMNNN!!!!!!!!");
//                                        ah.ClearConfirmPurchase();
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        switch (slot) {
//                            case AuctionHouse.MainPageItemRef.LastPage:
//                                ah.GoToPrevPage();
//                                break;
//                            case AuctionHouse.MainPageItemRef.NextPage:
//                                ah.GoToNextPage();
//                                break;
//                            case AuctionHouse.MainPageItemRef.Search:
//                                break;
//                            case AuctionHouse.MainPageItemRef.Reload:
//                                ah.ReloadCurrentPage();
//                                break;
//                            case AuctionHouse.MainPageItemRef.Catagories:
//                                ah.DisplayCatagories();
//                                break;
//                            case AuctionHouse.MainPageItemRef.PlayerSelling:
//                                ah.GoToSellerPage();
//                                event.setCancelled(false);
//                                break;
//
//                        }
//                    }
//                }
//            }
//        }
//    }

    public void PurchaseItem(CorePlayer holder, int page, int slot) {
        AuctionItemData aid = getAIDFromPage(page, slot);
        if (aid == null) {
            System.out.println("ERROR IN SELECTION!!!!");
        } else if (aid.getCost() > holder.GetMoney()) {
            holder.AH.SetupPageNotEnoughMoney(aid);
            return;
        }
//        SetBought(aid.getMasterid());
        holder.TakeMoney(aid.getCost());
        holder.getInventory().addItem(aid.getKeepItem());
        holder.AH.ClearConfirmPurchase();
        holder.AH.setPage(1);
    }

    public void SetBought(int id) {
        String sql = "UPDATE `AuctionHouse` SET `purchased` = '1' WHERE `auctions`.`id` = " + id + ";";
        try {
            Sqlite.executeUpdate(sql);
        } catch (Exception e) {
            CCM.getLogger().error("ERRORRR 555 ", e);
        }
    }

    public void ClaimMoney(int id) {
        String sql = "UPDATE `AuctionHouse` SET `moneysent` = '1' WHERE `auctions`.`id` = " + id + ";";
        //ExecuteUpdateSQLite(sql);
    }

    public void additem(Item i, CorePlayer p, int cost) {
        AuctionItemData aid = new AuctionItemData(i, cost, p);
        Sqlite.AddItemForSale(aid);
    }

}
