package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.ConfigSection;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CrateData {

//    public final String PossibleItemsKey = "PI";

    public ArrayList<ItemChanceData> PossibleItems = new ArrayList<>();
    public String Key = new NukkitRandom().nextRange(0, 10000) + "__UNNAMED CRATE__";
    public String Name = "__UNNAMED CRATE__";
    public String SubName = "==========";
    public ArrayList<String> KeyItems = new ArrayList<String>();
//    public Item Key;


    public CrateData(String name) {
        Name = name;

    }

    public CrateData(ConfigSection c) {
        if (c.containsKey("Key")) Key = c.getString("Key");
        if (c.containsKey("Name")) Name = c.getString("Name");
        if (c.containsKey("SubName")) SubName = c.getString("SubName");
        if (c.containsKey("KeyItems")) KeyItems = new ArrayList<>(c.getStringList("KeyItems"));
        if (c.containsKey("PossibleItems")) {
            if (c.get("PossibleItems") instanceof String)
                PossibleItems = PossibleItemsFromJSON(c.getString("PossibleItems"));
            else
                PossibleItems = PossibleItemsFromConfig(c.getSection("PossibleItems"));
        }E
//        if(c.containsKey("PossibleItems"))PossibleItems = (ArrayList<ItemChanceData>)c.get("PossibleItems");

//        if (c.containsKey(PossibleItemsKey)) {

//        }
    }

    public String getPossibleItemsToJSON() {
        return new Gson().toJson(PossibleItems, new TypeToken<ArrayList<ItemChanceData>>() {
        }.getType());
    }

    public ConfigSection getPossibleItemsToConfig() {
        ConfigSection c = new ConfigSection();
        int k = 0;
        for (ItemChanceData icd : PossibleItems) {
            c.put(k++ + "", icd.export());
        }
        return c;
    }
//    public ArrayList<ConfigSection> getPossibleItemsToConfig() {
//        ArrayList<ConfigSection> c = new  ArrayList<>();
//       int k = 0;
//       for(ItemChanceData icd : PossibleItems){
//           c.add(icd.export());
//       }
//       return c;
//    }

    public ArrayList<ItemChanceData> PossibleItemsFromConfig(ConfigSection j) {
        ArrayList<ItemChanceData> a = new ArrayList<>();
        for (Object o : j.getAllMap().values()) {
            if (o instanceof ConfigSection) {
                ConfigSection c = (ConfigSection) o;
                ItemChanceData icd = new ItemChanceData(c);
                a.add(icd);
            }
        }
        return a;
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
        c.put("Key", Key);
        c.put("Name", Name);
        c.put("SubName", SubName);
//        c.put("PossibleItems", getPossibleItemsToJSON());
        c.put("PossibleItems", getPossibleItemsToConfig());
//        c.put("PossibleItems", PossibleItems);
        return c;
    }
}
