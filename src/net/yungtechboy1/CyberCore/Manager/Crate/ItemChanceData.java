package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.item.Item;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.Utils;

public class ItemChanceData {
    private int ItemID;
    private int ItemMeta;
    private String NBT = "";//NBT_HEX
    private int Chance;
    private int Max_Count;

    public ItemChanceData(Item i, int chance, int max_Count) {
        this(i.getId(),i.getDamage(),chance,max_Count, i.hasCompoundTag() ? Binary.bytesToHexString(i.getCompoundTag()) : "");
    }
    public ItemChanceData(Item i, int chance) {
        this(i,chance,1);
    }
    public ItemChanceData(int itemID, int itemMeta, int chance) {
        this(itemID,itemMeta,chance,1);
    }
    public ItemChanceData(int itemID, int itemMeta, int chance, int max_count) {
        this(itemID,itemMeta,chance,max_count,"");
    }

    public ItemChanceData(int itemID, int itemMeta, int chance , int max_count, String NBT) {
        ItemID = itemID;
        ItemMeta = itemMeta;
        this.NBT = NBT;
        Max_Count = max_count;
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

    public Item check() {
        Item i = Item.get(ItemID,ItemMeta,1,NBT.getBytes());
        if(i.isNull())return null;
        if(new NukkitRandom().nextRange(0,100) < Chance){
            while(new NukkitRandom().nextRange(0,100) < Chance*2){
                if(i.getCount() >= Max_Count)break;
                i.setCount(i.getCount()+1);
            }
            return i;
        }
        return null;
    }
}
