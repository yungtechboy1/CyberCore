package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.item.Item;
import cn.nukkit.utils.Utils;

public class ItemChanceData {
    private int ItemID;
    private int ItemMeta;
    private String NBT = "";//NBT_HEX
    private int Chance;

    public ItemChanceData(int itemID, int itemMeta, int chance) {
        ItemID = itemID;
        ItemMeta = itemMeta;
        Chance = chance;
    }

    public ItemChanceData(int itemID, int itemMeta, String NBT, int chance) {
        ItemID = itemID;
        ItemMeta = itemMeta;
        this.NBT = NBT;
        Chance = chance;
    }

    public Item getItem() {
        return Item.get(ItemID,ItemMeta,1,NBTToByte());
    }

    public int getChance() {
        return Chance;
    }

    public byte[] NBTToByte(){
        if(NBT == null || NBT.length() == 0)return new byte[0];
        return Utils.parseHexBinary(NBT);
    }
}
