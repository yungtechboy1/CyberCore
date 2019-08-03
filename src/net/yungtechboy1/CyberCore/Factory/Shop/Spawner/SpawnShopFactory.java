package net.yungtechboy1.CyberCore.Factory.Shop.Spawner;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockChest;
import cn.nukkit.event.Listener;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Block.SpawnerWithLevelBlock;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;

public class SpawnShopFactory implements Listener {
//    private final ShopSQL SQL;
    CyberCoreMain CCM;
    /**
     * InternalPlayerSettings:
     * Key: {
     * id:
     * meta:
     * count:
     * namedtag:
     * cost:
     * soldby:
     * }
     */
//        Config InternalPlayerSettings;

    public ArrayList<SpawnerShopData> ShopCache;

    public SpawnShopFactory(CyberCoreMain CCM) {
        this.CCM = CCM;
//            InternalPlayerSettings = new Config(new File(CCM.getDataFolder(), "Auctions.yml"), Config.YAML);
//        SQL = new ShopSQL(CCM);
        ShopCache = new ArrayList<>();
        ShopCache.add(new SpawnerShopData(SpawnerWithLevelBlock.SpawnerType.Pig, 100, 2, 0));
        ShopCache.add(new SpawnerShopData(SpawnerWithLevelBlock.SpawnerType.Sheep, 100, 1, 0));
        ShopCache.add(new SpawnerShopData(SpawnerWithLevelBlock.SpawnerType.Cow, 100, 3, 0));
        ShopCache.add(new SpawnerShopData(SpawnerWithLevelBlock.SpawnerType.Squid, 100, 4, 0));
        ShopCache.add(new SpawnerShopData(SpawnerWithLevelBlock.SpawnerType.Chicken, 100, 5, 0));
        ShopCache.add(new SpawnerShopData(SpawnerWithLevelBlock.SpawnerType.Rabbit, 100, 6, 0));
        ShopCache.add(new SpawnerShopData(SpawnerWithLevelBlock.SpawnerType.Zombie, 100, 7, 0));
//        new SpawnerShopData(SpawnerWithLevelBlock.SpawnerType.Pig, 100, 3, 0);
//        ShopCache.add(pigdata);
    }

    //                new CoolDown().setTimeSecs(30,0);//30 Mins
    public ArrayList<SpawnerShopData> GetAllItems() {
        return ShopCache;
    }

    public ArrayList<Item> getListOfItems() {
        ArrayList<Item> il = new ArrayList<>();
        for (SpawnerShopData ahd : GetAllItems()) {
            il.add(ahd.getItem());
        }
        return il;
    }

    public HashMap<Integer, Item> getPageHash(int page) {
        HashMap<Integer, Item> list = new HashMap<Integer, Item>();
        int k = 0;
        for (Item i : getPage(page)) {
            list.put(k, i);
            k++;
        }

        return list;

    }


//    public Item getItem(int page, int slot) {
//        int stop = page * 45;
//        int start = stop - 45;
//        int key = start + slot;
//        return items.get(key);
//    }

    public ArrayList<SpawnerShopData> GetAllItemsDataLimit(int start, int stop) {
        ArrayList<SpawnerShopData> il = new ArrayList<>();
        ArrayList<SpawnerShopData> a = GetAllItems();
        for (int i = start; i < stop; i++) {
            if (i >= a.size()) break;
            SpawnerShopData smd = a.get(i);
            if (smd != null) il.add(smd);
        }
        return il;
    }

    public ArrayList<Item> GetAllItemsLimit(int start, int stop) {
        ArrayList<Item> il = new ArrayList<>();
        ArrayList<SpawnerShopData> a = GetAllItems();
        for (int i = start; i < stop; i++) {
            if (i >= a.size()) break;
            SpawnerShopData smd = a.get(i);
            if (smd != null) {
                il.add(smd.getItem());
            }
        }
        return il;
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
//
//    public ArrayList<SpawnerShopData> GetAllItemsLimitData(int start, int stop) {
//        ArrayList<SpawnerShopData> il = new ArrayList<>();
//        for (SpawnerShopData ahd : GetAllItemsDataLimit(start, stop)) {
//            il.add(ahd);
//        }
//        return il;
//    }

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

    public ArrayList<SpawnerShopData> getPageData(int page) {
        int stop = page * 45;
        int start = stop - 45;
        ArrayList<SpawnerShopData> list2 = GetAllItemsDataLimit(start, stop);
        if (45 > list2.size()) {
            ArrayList<SpawnerShopData> a = new ArrayList<SpawnerShopData>();
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
        SpawnFakeBlockAndEntity(p, new CompoundTag().putString("CustomName", "AAAAA SHOP!"));
        SpawnerShop b = new SpawnerShop(p, CCM, p, pg);
        CyberCoreMain.getInstance().getLogger().info(b.getContents().values().size() + " < SIZZEEE" + b.getSize());
        CyberCoreMain.getInstance().getServer().getScheduler().scheduleDelayedTask(new OpenSpawnerShop(p, b), 5);
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
////                System.out.println("CHECK INNNNNVVVVVVV " + inv.getClass().getName());
////                if (inv.isEmpty()) return;
//
////                System.out.println("NEEEEEEE" + inv.getClass().getTypeName());
//                if (inv instanceof PlayerInventory) {
//
//                }
//                if (inv instanceof SpawnerShop) {
//
//                    SpawnerShop ah = (SpawnerShop) inv;
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
////                        ccpp.AH.ConfirmItemPurchase(slot);
//                        } else {
//                            if (ah.CurrentPage == SpawnerShop.CurrentPageEnum.PlayerSellingPage) {
//                                int sx = slot % 9;
//                                int sy = (int) Math.floor(slot / 9);
//                                Item is = ah.getItem(slot);
//                                boolean isi = false;
//                                int isc = is.getCount();
//                                if (is != null && is.getId() != 0) {
//                                    if (is.getId() == Item.IRON_BLOCK) isi = true;
//                                    System.out.println("Selected Slot SX:" + sx + " | SY:" + sy);
//                                    if (sy != 0 && sy != 5 && sx != 4 && !isi) {
//                                        if (sx < 4) {
//                                            //Cancel
//                                            ah.setPage(1);
//                                        } else {
//                                            //Buy
//                                            ah.SetupPageToFinalConfirmItem(ah.MultiConfirmData, isc, false);
//                                        }
//                                    }
//                                }
//                                event.setCancelled();
//                                return;
//                            } else {
//                                Item si = ah.getContents().get(slot);
//                                if (si != null) {
//                                    if (ah.getCurrentPage() == SpawnerShop.CurrentPageEnum.Confirm_Purchase_Not_Enough_Money) {
//                                        ah.setPage(1);
//                                        ah.ClearConfirmPurchase();
//                                        //Back Home
//                                        break;
//                                    } else {
//                                        System.out.println("CPPPPPPPP");
//
//                                        if (si.getId() == BlockID.EMERALD_BLOCK) {
//                                            System.out.println("CONFIRM PURCHASE!!!!!!!");
//                                            ah.SF.PurchaseItem((CorePlayer) ah.getHolder(), ah.getPage(), ah.ConfirmPurchaseSlot, si.getCount());
//                                            break;
//                                        } else if (si.getId() == BlockID.REDSTONE_BLOCK) {
//                                            System.out.println("DENCLINE PURCHASE!!!!!!!!");
//                                            ah.setPage(1);
//                                            ah.ClearConfirmPurchase();
//                                            break;
//                                        } else {
//                                            ah.setPage(1);
//                                            System.out.println("UNKNOWNMNNN!!!!!!!!");
//                                            ah.ClearConfirmPurchase();
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        switch (slot) {
//                            case SpawnerShop.MainPageItemRef.LastPage:
//                                ah.GoToPrevPage();
//                                break;
//                            case SpawnerShop.MainPageItemRef.NextPage:
//                                ah.GoToNextPage();
//                                break;
//                            case SpawnerShop.MainPageItemRef.Search:
//                                break;
//                            case SpawnerShop.MainPageItemRef.Reload:
//                                ah.ReloadCurrentPage();
//                                break;
//                            case SpawnerShop.MainPageItemRef.Catagories:
//                                ah.DisplayCatagories();
//                                break;
//                            case SpawnerShop.MainPageItemRef.ToggleAdmin:
//                                ah.AdminMode = !ah.AdminMode;
//                                event.setCancelled(false);
//                                ah.ReloadCurrentPage();
//                                break;
//
//                        }
//                    }
//                }
//            }
//        }
//    }
//
    //SetupPageToFinalConfirmItemSell
    public void PurchaseItem(CorePlayer holder, int page, int slot, int count) {
        SpawnerShopData aid = getItemFrom(page, slot);
        if (aid == null) {
            System.out.println("ERROR IN SELECTION!!!!");
        } else if (aid.getPrice() > holder.getMoney()) {
            holder.SpawnerShop.SetupPageNotEnoughMoney(aid);
            return;
        }
//        int c = holder.Shop.SetupPageToFinalConfirmItemCount;


//        SetBought(aid.getMasterid());
            holder.TakeMoney(aid.getPrice(count));
            Item i = aid.getItem(true);
            i.setCount(count);
            holder.getInventory().addItem(i);
            holder.SpawnerShop.ClearConfirmPurchase();
            holder.SpawnerShop.setPage(1);
    }

    public SpawnerShopData getItemFrom(int page, int slot) {
        ArrayList<SpawnerShopData> smd = getPageData(page);
        if (smd.size() < slot) return null;
        return smd.get(slot);
    }



}

