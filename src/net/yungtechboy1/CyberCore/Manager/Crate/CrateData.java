package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CrateData {

//    public final String PossibleItemsKey = "PI";

    public ArrayList<ItemChanceData> PossibleItems = new ArrayList<>();
    public String Name = "__UNNAMED CRATE__";
    public String SubName = "==========";
    public ArrayList<String> KeyItems = new ArrayList<String>();
//    public Item Key;


    public CrateData(String name) {
        Name = name;

    }

    public CrateData(ConfigSection c) {
        if(c.containsKey("Name"))Name = c.getString("Name");
        if(c.containsKey("SubName"))SubName = c.getString("SubName");
        if(c.containsKey("KeyItems"))KeyItems = new ArrayList<>(c.getStringList("KeyItems"));
        if(c.containsKey("PossibleItems"))PossibleItems = PossibleItemsFromJSON(c.getString("PossibleItems"));

//        if (c.containsKey(PossibleItemsKey)) {

//        }
    }

    public String getPossibleItemsToJSON() {
        return new Gson().toJson(PossibleItems, new TypeToken<ArrayList<ItemChanceData>>() {
        }.getType());
    }

    public ArrayList<ItemChanceData> PossibleItemsFromJSON(String j) {
        return new Gson().fromJson(j, new TypeToken<ArrayList<ItemChanceData>>() {
        }.getType());
    }


    //https://github.com/google/gson/blob/master/UserGuide.md#TOC-Serializing-and-Deserializing-Collection-with-Objects-of-Arbitrary-Types
    public String export() {
        return (new Gson().toJson(this));
    }


    public ConfigSection toConfig() {
        ConfigSection c = new ConfigSection();
//        c.put("Key", Key.getId() + ":" + Key.getDamage());
//        c.put("Key_ID", Key_ID);
//        c.put("Key_Meta", Key_Meta);
//        c.put("Key_NBT", Key_NBT);
        c.put("KeyItems", KeyItems);
        c.put("Name", Name);
        c.put("SubName", SubName);
        c.put("PossibleItems", getPossibleItemsToJSON());
        return c;
    }
}
