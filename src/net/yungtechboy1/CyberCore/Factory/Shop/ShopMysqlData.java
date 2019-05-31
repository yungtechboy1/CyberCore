package net.yungtechboy1.CyberCore.Factory.Shop;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;

import java.sql.ResultSet;

public class ShopMysqlData {
    private int ShopID = 0;
    private int ItemID = 0;
    private int ItemDamage = 0;
    private int Price = 0;
    private int SellPrice = 0;
    private int Quantity = 0;
    private String DisplayName = "N/A";
    private byte[] Namedtag = null;
    private boolean isValid = true;
    public ShopMysqlData(ResultSet rs) {
        try {
            ShopID = rs.getInt("ShopID");
            ItemID = rs.getInt("itemid");
            ItemDamage = rs.getInt("itemdamage");
            Quantity = rs.getInt("quantity");
            Price = rs.getInt("cost");
            SellPrice = rs.getInt("sellprice");
            String ns = rs.getString("nametag");
            if (ns != null) Namedtag = ns.getBytes();
            DisplayName = rs.getString("DisplayName");
            System.out.println("Loading Shop Item >" + ShopID + "|" + ItemID + "|" + Price + DisplayName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRRRRRRRRR Loading Shop Item !!!!");
            isValid = false;
        }
    }

    public int getSellPrice() {
        return SellPrice;
    }

    public int getSellPrice(int c) {
        return SellPrice*c;
    }

    public void setSellPrice(int sellPrice) {
        SellPrice = sellPrice;
    }

    public Item getItem() {
        return getItem(false);
    }

    public Item getItem(boolean pretty) {
        Item i = Item.get(ItemID, ItemDamage, Quantity);
        if (Namedtag != null) i.setCompoundTag(Namedtag);
        if (!pretty) {
            CompoundTag c = new CompoundTag();
            if (i.getNamedTag() != null) {
                c = i.getNamedTag();
            }
            c.putInt("ShopID", ShopID);
            i.setNamedTag(c);
            i.setLore(TextFormat.AQUA + "Purchase Price: " + TextFormat.GREEN + Price,
                    TextFormat.YELLOW + "Sell Price: " + TextFormat.GREEN + SellPrice,
                    "Click to Buy/Sell!");
        }
        return i;
    }

    public Item getItemMainMenu() {
        return getItem();
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getPrice() {
        return Price;
    }
    public int getPrice(int c) {
        return Price*c;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getPrettyString(int q, boolean buy) {
        String s = q + " " + getItem(true).getName() + " for ";
        if (buy) s += getPrice() * q;
        else s += getSellPrice() * q;
        return s;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }
}
