package net.yungtechboy1.CyberCore.Factory.Shop;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ShopMysqlData {
    private int ShopID = 0;
    private int ItemID = 0;
    private int ItemDamage = 0;
    private int Price = 0;
    private int Quantity = 0;
    private String DisplayName = "N/A";
    private byte[] Namedtag = null;
    private boolean isValid = true;


    public ShopMysqlData(int shopID, int itemID, int itemDamage, int price, int quantity, String displayName, byte[] namedtag) {
        ShopID = shopID;
        ItemID = itemID;
        ItemDamage = itemDamage;
        Price = price;
        Quantity = quantity;
        DisplayName = displayName;
        Namedtag = namedtag;
    }

    public ShopMysqlData(ResultSet rs) {
        try {
            ShopID = rs.getInt("ShopID");
            ItemID = rs.getInt("itemid");
            ItemDamage = rs.getInt("itemdamage");
            Quantity = rs.getInt("quantity");
            Price = rs.getInt("cost");
            String ns = rs.getString("nametag");
            if(ns != null)Namedtag = ns.getBytes();
            DisplayName = rs.getString("DisplayName");
            System.out.println("Loading Shop Item >"+ShopID+"|"+ItemID+"|"+Price+DisplayName);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("ERRRRRRRRRR Loading Shop Item !!!!");
            isValid = false;
        }
    }

    public Item getItem() {
        return getItem(false);
    }

    public Item getItem(boolean pretty) {
        Item i = Item.get(ItemID, ItemDamage, Quantity);
        if (Namedtag != null) i.setCompoundTag(Namedtag);
        if(!pretty){
            CompoundTag c = new CompoundTag();
            if(i.getNamedTag() != null){
                c = i.getNamedTag();
            }
            c.putInt("ShopID",ShopID);
            i.setNamedTag(c);
            i.setLore("Cost: "+Price,
                    "Click to Buy!");
        }
        return i;
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

    public void setPrice(int price) {
        Price = price;
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
