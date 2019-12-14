package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.ConfigSection;

public class KeyData {
//    int Item_ID = 0;
//    int Item_Meta = 0;
    Item ItemKey = null;
    String NBT_Key = "";
    String Key_Name = "";

    public Item getItemKey() {
        return ItemKey;
    }

    public String getNBT_Key() {
        return NBT_Key;
    }

    public String getKey_Name() {
        return Key_Name;
    }

    public KeyData(Item i, String name, String nbtkey){
    if(i == null){
        throw new NullPointerException("The Item given is null!");
    }
//    Item_ID = i.getId();
//    Item_Meta = i.getDamage();
    ItemKey = i.clone();
    ItemKey.setCount(1);
    Key_Name = name;
    NBT_Key = nbtkey;
    if(!ItemKey.hasCompoundTag())ItemKey.setNamedTag(new CompoundTag());
    ItemKey.setNamedTag(ItemKey.getNamedTag().putString(CrateMain.CK,Key_Name));

//    Item_NBT
}

public KeyData(ConfigSection c){
    if(!c.containsKey("Item-ID") ||!c.containsKey("Item-Meta") ||!c.containsKey("Item-NBT")||!c.containsKey("Key_Name")||!c.containsKey("NBT_Key")) {
        System.out.println("Error! Invalid Config!!!!!!!!!!!!!!!!!!!!!");
        return;
    }
    int iid = c.getInt("Item-ID");
    int meta = c.getInt("Item-Meta");
    String nbt = c.getString("Item-NBT");
    Key_Name= c.getString("Key_Name");
    NBT_Key= c.getString("NBT_Key");
    ItemKey = Item.get(iid,meta,1);
    System.out.println("ITEMKEYTYYYYYYYYYYYY >>>>>>>>>>>> "+ItemKey);

    if(ItemKey == null){
        System.out.println("ERROROROROOROROQWEQ WEQE!~~!!@##@123123414");
        return;
    }
    if(nbt.length() != 0)ItemKey.setCompoundTag(nbt.getBytes());
}

public ConfigSection toConfig(){
//    if(ItemKey.hasCompoundTag())ItemKey.setNamedTag(ItemKey.getNamedTag());
    ConfigSection c = new ConfigSection();
    c.put("Item-ID", ItemKey.getId());
    c.put("Item-Meta", ItemKey.getDamage());
    c.put("Item-NBT", ItemKey.hasCompoundTag() ? Binary.bytesToHexString(ItemKey.getCompoundTag()) : "");
    c.put("Key_Name", Key_Name);
    c.put("NBT_Key", NBT_Key);
    return c;
}

}
