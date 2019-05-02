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
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemBook;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Inventory.AuctionHouse;
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

/**
 * Created by carlt_000 on 2/22/2017.
 */
public class AuctionFactory implements Listener {
    private final AHSqlite Sqlite;
    public int Page;
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
        Sqlite = new AHSqlite(CCM, "server");
        Page = 1;
    }


    public ArrayList<AuctionItemData> GetAllItems() {
        ArrayList<AuctionItemData> is = new ArrayList<>();
        try {
            ResultSet rs = Sqlite.ExecuteQuerySQLite("SELECT * FROM `auctions` WHERE `purchased` != 1");
            if (rs != null) {
                try {
                    while (rs.next()) {
                        AuctionItemData aid = new AuctionItemData(rs);
                        is.add(aid);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    CCM.getLogger().info("Error loading Items!");
                    return null;
                }
                CCM.getLogger().info("Loaded " + is.size() + " Items for AH");
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

    public Item[] getPage(int page) {
        System.out.println(page);
        int stop = page * 45;
        int start = stop - 46;
        if (start > getListOfItems().size()) {
            ArrayList<Item> a = new ArrayList<Item>();
            for (int i = 0; i < 45; i++) {
                a.add(new ItemBlock(new BlockAir(), (Integer) null, 0));
            }
            return (Item[]) a.toArray();
        }

        ArrayList<Item> list = new ArrayList<>();

        for (int a = start; a < getListOfItems().size(); a++) {
            if (a >= stop) break;
            Item newitem = getListOfItems().get(a).clone();
            System.out.println(newitem.toString());
            if (newitem == null) list.add(new ItemBlock(new BlockAir(), (Integer) null, 0));
            else list.add(newitem);
        }

        return (Item[]) list.toArray();

        /*
        1 => 0 | 44
        2 => 45 | 89
         */
    }

    public void OpenAH(CorePlayer p, Integer pg) {
        SpawnFakeBlockAndEntity(p, new CompoundTag().putString("CustomName", "Auction House!"));
        AuctionHouse b = new AuctionHouse(p, CCM, p, pg);
        b.addItem(getPage(1));
        CyberCoreMain.getInstance().getLogger().info(b.getContents().values().size() + " < SIZZEEE" + b.getSize());
        CyberCoreMain.getInstance().getServer().getScheduler().scheduleDelayedTask(new OpenAH(p, b), 10);
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

    @EventHandler(ignoreCancelled = true)
    public void TE(InventoryTransactionEvent event) {
        InventoryTransaction transaction = event.getTransaction();
        Set<InventoryAction> traa = transaction.getActions();
        for (InventoryAction t : traa) {
            Set<Inventory> inv = transaction.getInventories();
            if (inv.isEmpty()) return;
            if (inv instanceof AuctionHouse) {
                AuctionHouse AHINV = ((AuctionHouse) inv);
                if (!AHINV.ConfirmPurchase) {
                    Item si = t.getSourceItem();
                    System.out.println(si.getId());
                    if (si.getNamedTag().contains("AHITEM") && si.getNamedTag().getBoolean("AHITEM")) {
//                    t.g
                        if (si.getId() == BlockID.STAINED_GLASS_PANE) {//Next, Previous, & Null
                            //Confirm Item
                            if(si.getDamage() == 14){
                                //red
                                Page--;
                                return;
                            }
                            else if(si.getDamage() == 5){
                                //green
                                Page++;
                                return;
                            }
                            else if(si.getDamage() == 7){
                                return;
                                //Gray Glass
                            }
                        } else if (si.getId() == ItemID.DIAMOND) {
                            //@TODO Show Items they are selling
                        }
                    }
                } else {
                    if (t.getSourceItem().getId() == Item.EMERALD_BLOCK) {
                        event.setCancelled();
                        Player p = (Player) AHINV.getHolder();
                        if (p == null) {
                            System.out.println("?!??!?! Ahh222222hhh");
                            return;
                        }
                        //DONE!
                        //Give Item
                        Item TI = AHINV.getItem(31);
//                        if (TI.hasCompoundTag()) {
//                            CompoundTag tag = TI.getNamedTag();
//                            if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
//                                CompoundTag tag2 = tag.getCompound("display");
//                                if (tag2 != null) {
//                                    ArchEconMain AA = (ArchEconMain) CCM.getServer().getPluginManager().getPlugin("ArchEcon");
//                                    int id = tag2.getInt("keyid");
//                                    int price = tag2.getInt("cost");
//                                    if (AA == null) {
//                                        p.sendMessage(TextFormat.RED + "Error! 258");
//                                        AHINV.close(p);
//                                        return;
//                                    }
//                                    //@TODO Check Items
//                                    Item checkitem = GetItemfromDB(id);
//                                    if (checkitem == null && !checkitem.equals(TI)) {
//                                        p.sendMessage(TextFormat.RED + "Error! 273");
//                                        AHINV.close(p);
//                                        System.out.println("?!??!?! Ahhhhh");
//                                        return;
//                                    }
//
//                                    //@TODO reset all my custom shit!
//                                    String soldby = tag2.getString("soldby");
//                                    tag2.remove("keyid");
//                                    tag2.remove("cost");
//                                    tag2.remove("soldby");
//                                    String CN = tag2.getString("Name2");
//                                    tag2.remove("Name");
//                                    if (CN != null && CN.length() != 0) tag2.putString("Name", CN);
//                                    tag2.remove("Name2");
//                                    TI.setNamedTag(tag);
//
////                                    if (!AA.TakeMoney(p.getName(), 1.0 * price, false)) {
////                                        p.sendMessage(TextFormat.RED + "Error! You don't have enough money!");
////                                        AHINV.close(p);
////                                        return;
////                                    }
//
//                                    SetBought(id);
////                                    AA.GiveMoney(soldby, 1.0 * price);
//                                    p.getInventory().addItem(TI);
//                                    AHINV.close(p);
//                                    //p.getLevel().dropItem(p,TI.clone());
//
//
//                                    System.out.println("Braasssssa");
//                                    return;
//                                    //Take Money
//                                    //DEl Item
//                                }
//                            } else {
//                                System.out.println("Braaaaaaaaa");
//                            }
//                        } else {
//                            System.out.println("Braaa");
//                        }
                    }
                    System.out.println("Other Blokc ");
                }
                event.setCancelled();
            }
        }
    }

    public void SetBought(int id) {
        String sql = "UPDATE `auctions` SET `purchased` = '1' WHERE `auctions`.`id` = " + id + ";";
        //ExecuteUpdateSQLite(sql);
    }

    public void ClaimMoney(int id) {
        String sql = "UPDATE `auctions` SET `moneysent` = '1' WHERE `auctions`.`id` = " + id + ";";
        //ExecuteUpdateSQLite(sql);
    }

    public void additem(Item i, CorePlayer p, int cost) {
        AuctionItemData aid = new AuctionItemData(i, cost, p);
        Sqlite.AddItemForSale(aid);
    }

}
