package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.event.Listener;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.Inventory.AuctionHouse;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by carlt_000 on 2/22/2017.
 */
public class AuctionFactory implements Listener {
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



    ArrayList<Item> items = new ArrayList<>();

    public AuctionFactory(CyberCoreMain CCM) {
        this.CCM = CCM;
        Settings = new Config(new File(CCM.getDataFolder(), "Auctions.yml"), Config.YAML);
        Init();
    }

    public Item GetItemfromDB(int idd) {
        if(idd == 0)return null;
        ResultSet rs = null;//ExecuteQuerySQLite("SELECT * FROM `auctions` WHERE `id` = '" + idd + "'");
        if (rs != null) {
            try {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int item_id = rs.getInt("item-id");
                    int item_meta = rs.getInt("item-meta");
                    int item_count = rs.getInt("item-count");
                    byte[] namedtag = rs.getString("namedtag").getBytes();
                    int cost = rs.getInt("cost");
                    String soldby = rs.getString("soldby");

                    Item i = Item.get(item_id, item_meta, item_count);
                    i.setCompoundTag(namedtag);

                    CompoundTag tag = i.getNamedTag();
                    if (tag == null) tag = new CompoundTag();

                    if (!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
                        tag.getCompound("display").putString("Name2", i.getCustomName());

                    if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
                        tag.getCompound("display").putInt("keyid", id);
                        tag.getCompound("display").putInt("cost", cost);
                        tag.getCompound("display").putString("soldby", soldby);
                    } else {
                        tag.putCompound("display", (new CompoundTag("display")).putInt("keyid", id).putInt("cost",cost).putString("soldby",soldby));
                    }
                    i.setNamedTag(tag);


                    String cn = i.getCustomName();

                    if (cn.equalsIgnoreCase("")) cn = i.getName();

                    cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                            "-------------" + TextFormat.RESET + "\n" +
                            TextFormat.GREEN + "$" + cost + TextFormat.RESET + "\n" +
                            TextFormat.GOLD + "Sold By: " + soldby
                    // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
                    ;

                    i.setCustomName(cn);

                    return i;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                CCM.getLogger().info("ERror loading Items2!");
                return null;
            }
        }
        return null;
    }

    public void Init() {
        ArrayList<Item> is = new ArrayList<>();
        ResultSet rs = null ;//ExecuteQuerySQLite("SELECT * FROM `auctions` WHERE `purchased` != 1");
        if (rs != null) {
            try {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int item_id = rs.getInt("item-id");
                    int item_meta = rs.getInt("item-meta");
                    int item_count = rs.getInt("item-count");
                    byte[] namedtag = rs.getString("namedtag").getBytes();
                    int cost = rs.getInt("cost");
                    String soldby = rs.getString("soldby");

                    Item i = Item.get(item_id, item_meta, item_count);
                    i.setCompoundTag(namedtag);

                    CompoundTag tag = i.getNamedTag();
                    if (tag == null) tag = new CompoundTag();

                    if (!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
                        tag.getCompound("display").putString("Name2", i.getCustomName());

                    if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
                        tag.getCompound("display").putInt("keyid", id);
                        tag.getCompound("display").putInt("cost", cost);
                        tag.getCompound("display").putString("soldby", soldby);
                    } else {
                        tag.putCompound("display", (new CompoundTag("display")).putInt("keyid", id).putInt("cost",cost).putString("soldby",soldby));
                    }
                    i.setNamedTag(tag);


                    String cn = i.getCustomName();

                    if (cn.equalsIgnoreCase("")) cn = i.getName();

                    cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                            "-------------" + TextFormat.RESET + "\n" +
                            TextFormat.GREEN + "$" + cost + TextFormat.RESET + "\n" +
                            TextFormat.GOLD + "Sold By: " + soldby
                    // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
                    ;

                    i.setCustomName(cn);

                    is.add(i);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                CCM.getLogger().info("ERror loading Items!");
                return;
            }
            CCM.getLogger().info("Loaded " + is.size() + " Items for AH");
            items = is;
        }
    }

    //@Todo
    public ArrayList<Item> getSoldItems() {
        ArrayList<Item> is = new ArrayList<>();
        ResultSet rs = null;//ExecuteQuerySQLite("SELECT * FROM `auctions` WHERE `purchased` != 1");
        if (rs != null) {
            try {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int item_id = rs.getInt("item-id");
                    int item_meta = rs.getInt("item-meta");
                    int item_count = rs.getInt("item-count");
                    byte[] namedtag = rs.getString("namedtag").getBytes();
                    int cost = rs.getInt("cost");
                    String soldby = rs.getString("soldby");

                    Item i = Item.get(item_id, item_meta, item_count);
                    i.setCompoundTag(namedtag);

                    CompoundTag tag = i.getNamedTag();
                    if (tag == null) tag = new CompoundTag();

                    if (!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
                        tag.getCompound("display").putString("Name2", i.getCustomName());

                    if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
                        tag.getCompound("display").putInt("keyid", id);
                        tag.getCompound("display").putInt("cost", cost);
                    } else {
                        tag.putCompound("display", (new CompoundTag("display")).putInt("keyid", id).putInt("cost",cost));
                    }
                    i.setNamedTag(tag);


                    String cn = i.getCustomName();

                    if (cn.equalsIgnoreCase("")) cn = i.getName();

                    cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                            "-------------" + TextFormat.RESET + "\n" +
                            TextFormat.GREEN + "$" + cost + TextFormat.RESET + "\n" +
                            TextFormat.GOLD + "Sold By: " + soldby
                    // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
                    ;

                    i.setCustomName(cn);

                    is.add(i);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                CCM.getLogger().info("ERror loading Items!");
                return null;
            }
            CCM.getLogger().info("Loaded " + is.size() + " Items for AH");
            items = is;
        }
        return null;
    }

    public ArrayList<Item> getListOfItems() {
        return items;
    }

    public Item getItem(int page, int slot) {
        int stop = page * 45;
        int start = stop - 45;
        int key = start + slot;
        return items.get(key);
    }

    public ArrayList<Item> getPage(int page) {
        System.out.println(page);
        int stop = page * 45;
        int start = stop - 45;
        if (start > getListOfItems().size()) {
            ArrayList<Item> a = new ArrayList<Item>();
            for(int i = 0; i < 45; i++){
                a.add(new ItemBlock(new BlockAir(), (Integer)null, 0));
            }
            return a;
        }

        ArrayList<Item> list = new ArrayList<>();

        for (int a = start; a < getListOfItems().size(); a++) {
            if (a >= stop) break;
            Item newitem = getListOfItems().get(a).clone();
            System.out.println(newitem.toString());
            if (newitem == null) list.add(new ItemBlock(new BlockAir(), (Integer)null, 0));
            else list.add(newitem);
        }

        return list;

        /*
        1 => 0 | 44
        2 => 45 | 89
         */
    }

    public void OpenAH(Player p, Integer pg) {
        Inventory b = new AuctionHouse(p, CCM, p, pg);
        p.addWindow(b);
    }

    //TODO
//    @EventHandler(ignoreCancelled = true)
//    public void TE(InventoryTransactionEvent event) {
//        InventoryTransaction transaction =  event.getTransaction();
//        Set<InventoryAction> traa = transaction.getActions();
//        for (InventoryAction t : traa) {
//            Set<Inventory> inv = transaction.getInventories();
//            if(inv.isEmpty())return;
//            if (inv instanceof AuctionHouse) {
//                AuctionHouse AHINV = ((AuctionHouse) inv);
//                if (!AHINV.ConfirmPurchase) {
//                    t.g
//                    if (t.getSlot() >= 0 && t.getSlot() < 45) {
//                        //Confirm Item
//                        AHINV.ConfirmItemPurchase(t.getSlot());
//                    } else if (t.getSlot() == 45) {
//                        //@TODO Show Items they are selling
//                    } else if (t.getSlot() == 46) {
//                        //@TODO Show Expired Items
//                    } else if (t.getSlot() == 48) {
//                        //@TODO Previous
//                        AHINV.setPage(AHINV.Page - 1);
//                        AHINV.setContents(getPage(AHINV.Page));
//                    } else if (t.getSlot() == 49) {
//                        //@TODO Resend Page
//                        AHINV.setPage(AHINV.Page);
//                        AHINV.setContents(getPage(AHINV.Page));
//                    } else if (t.getSlot() == 50) {
//                        //@TODO Next
//                        AHINV.setPage(AHINV.Page + 1);
//                        AHINV.setContents(getPage(AHINV.Page));
//                    } else if (t.getSlot() == 52) {
//                        //@TODO Catagories
//                    }
//                } else {
//                    if (t.getSourceItem().getId() == Item.EMERALD_BLOCK) {
//                        event.setCancelled();
//                        Player p = (Player)AHINV.getHolder();
//                        if(p == null){
//                            System.out.println("?!??!?! Ahh222222hhh");
//                            return;
//                        }
//                        //DONE!
//                        //Give Item
//                        Item TI = AHINV.getItem(31);
//                        if (TI.hasCompoundTag()) {
//                            CompoundTag tag = TI.getNamedTag();
//                            if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
//                                CompoundTag tag2 = tag.getCompound("display");
//                                if (tag2 != null) {
//                                    ArchEconMain AA = (ArchEconMain) CCM.getServer().getPluginManager().getPlugin("ArchEcon");
//                                    int id = tag2.getInt("keyid");
//                                    int price = tag2.getInt("cost");
//                                    if(AA == null){
//                                        p.sendMessage(TextFormat.RED+"Error! 258");
//                                        AHINV.close(p);
//                                        return;
//                                    }
//                                    //@TODO Check Items
//                                    Item checkitem = GetItemfromDB(id);
//                                    if(checkitem == null && !checkitem.equals(TI)){
//                                        p.sendMessage(TextFormat.RED+"Error! 273");
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
//                                    if(CN != null && CN.length() != 0)tag2.putString("Name",CN);
//                                    tag2.remove("Name2");
//                                    TI.setNamedTag(tag);
//
//                                    if(!AA.TakeMoney(p.getName(),1.0 * price,false)){
//                                        p.sendMessage(TextFormat.RED+"Error! You don't have enough money!");
//                                        AHINV.close(p);
//                                        return;
//                                    }
//
//                                    SetBought(id);
//                                    AA.GiveMoney(soldby,1.0*price);
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
//                        }else{
//                            System.out.println("Braaa");
//                        }
//                    }
//                    System.out.println("Other Blokc ");
//                }
//                event.setCancelled();
//            }
//        }
//    }

    public void SetBought(int id) {
        String sql = "UPDATE `auctions` SET `purchased` = '1' WHERE `auctions`.`id` = "+id+";";
        //ExecuteUpdateSQLite(sql);
    }
    public void ClaimMoney(int id) {
        String sql = "UPDATE `auctions` SET `moneysent` = '1' WHERE `auctions`.`id` = "+id+";";
        //ExecuteUpdateSQLite(sql);
    }

    public void additem(Item i, Player p, int cost) {
        int id = i.getId();
        int meta = i.getDamage();
        int count = i.getCount();
        String namedtag = new String(i.getCompoundTag());
        String sql = "INSERT INTO `auctions` (`id`, `item-id`, `item-meta`, `item-count`, `namedtag`, `cost`, `soldby`, `moneysent`) VALUES (NULL, '" + id + "', '" + meta + "', '" + count + "', '" + namedtag + "', '" + cost + "', '" + p.getName() + "', '0','0')";
        //ExecuteUpdateSQLite(sql);
        String sql2 = "SELECT * FROM `auctions` ORDER BY `auctions`.`id` DESC";
        System.out.println(sql);
        //Take Item

        CompoundTag tag = i.getNamedTag();

        if (tag == null) tag = new CompoundTag();

        if (!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
            tag.getCompound("display").putString("Name2", i.getCustomName());

        if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
            tag.getCompound("display").putInt("keyid", id);
            tag.getCompound("display").putInt("cost", cost);
            tag.getCompound("display").putString("soldby", p.getName());
        } else {
            tag.putCompound("display", (new CompoundTag("display")).putInt("keyid", id).putInt("cost",cost).putString("soldby",p.getName()));
        }

        i.setNamedTag(tag);

        String cn = i.getCustomName();

        if (cn.equalsIgnoreCase("")) cn = i.getName();

        cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                "-------------" + TextFormat.RESET + "\n" +
                TextFormat.GREEN + "$" + cost + TextFormat.RESET + "\n" +
                TextFormat.GOLD + "Sold By: " + p.getName()
        // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
        ;

        i.setCustomName(cn);

        items.add(i);
    }

}
