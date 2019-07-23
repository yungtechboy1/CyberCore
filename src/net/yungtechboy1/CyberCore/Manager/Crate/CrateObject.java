package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

public class CrateObject {
    //new TypeToken<ArrayList<UUID>>() {}.getType();
    public Position Location;
    public CrateData CD;


    public CrateObject(Position p, CrateData cd) {
        Location = p;
        CD = cd;
    }

    public CrateObject(ConfigSection c) {
        if (c.containsKey("Key")) CD = CyberCoreMain.getInstance().CrateMain.getCrateMap().get(c.getString("Key"));
        if (c.containsKey("Loc")) Location = (Position) c.get("Loc");
//        super(c);
    }

    public ConfigSection toConfig() {
        ConfigSection cf = new ConfigSection();
        ConfigSection c = new ConfigSection();
        c.put("x", Location.x);
        c.put("y", Location.y);
        c.put("z", Location.z);
        c.put("level", Location.getLevel().getName());
        if (CD != null) cf.put("Key", CD.Key);
        cf.put("Loc", Location);
//        cf.put("Loc", .put.put("y",));
        return cf;
    }

    public ArrayList<String> getPossibleKeys() {
        return CD == null ? null : CD.KeyItems;
    }

    public boolean checkKey(Item hand) {
        ArrayList<String> pk = getPossibleKeys();
        if(pk == null || pk.size() == 0 || !hand.hasCompoundTag())return false;
        String n = hand.getNamedTag().getString(CrateMain.CK);
        return pk.contains(n);

    }
}
