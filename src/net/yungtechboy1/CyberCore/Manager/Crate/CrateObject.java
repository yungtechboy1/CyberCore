package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.FT.CyberFloatingTextContainer;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextFactory;

import java.util.ArrayList;

public class CrateObject {
    //new TypeToken<ArrayList<UUID>>() {}.getType();
    public Position Location;
    public CrateData CD;
    private boolean isinit = false;
    private boolean ftloaded = false;
    private CyberFloatingTextContainer ft = null;


    public CrateObject(Position p, CrateData cd) {
        Location = p;
        CD = cd;
        if(cd != null)init();
    }

    public String getDisplayText(){
        if(CD != null){
            return "===|Crate|===\n"+CD.Name+"\n"+CD.SubName;
        }
        return  "===|Crate|===";
    }

    public void init() {
        isinit = true;
        ftloaded = true;
        ft = new CyberFloatingTextContainer(CyberCoreMain.getInstance().FTM,Location,getDisplayText());
        FloatingTextFactory.AddFloatingText(ft);
//        FloatingTextParticle
    }

    public void removeFT(){
        ft.kill();
    }

    public boolean isInit() {
        return isinit;
    }

    public boolean isFtloaded() {
        return ftloaded;
    }

    public CyberFloatingTextContainer getFt() {
        return ft;
    }

    //    public CrateObject(ConfigSection c) {
//        if (c.containsKey("Key")) CD = CyberCoreMain.getInstance().CrateMain.getCrateMap().get(c.getString("Key"));
//        if (c.containsKey("Loc")) Location = (Position) c.get("Loc");
////        super(c);
//    }

    public ConfigSection toConfig() {
        ConfigSection cf = new ConfigSection();
        System.out.println("1111" + Location);
        cf.put("x", Location.x);
        cf.put("y", Location.y);
        cf.put("z", Location.z);
        System.out.println("1111");
        if (Location.getLevel() != null) cf.put("level", Location.getLevel().getName());
        System.out.println("1111");
        if (CD != null) cf.put("Key", CD.Key);
        System.out.println("1111");
//        cf.put("Loc", Location);
//        cf.put("Loc", .put.put("y",));
        return cf;
    }

    public ArrayList<String> getPossibleKeys() {
        return CD == null ? null : CD.KeyItems;
    }

    public boolean checkKey(Item hand) {
        ArrayList<String> pk = getPossibleKeys();
        if (pk == null || pk.size() == 0 || !hand.hasCompoundTag()) return false;
        String n = hand.getNamedTag().getString(CrateMain.CK);
        return pk.contains(n);

    }

    public ArrayList<Item> getPossibleItems() {
        ArrayList<Item> pi = new ArrayList<>();
        for (ItemChanceData icd : CD.PossibleItems) {
            Item i = icd.check();
            if (i == null || i.isNull()) continue;
            pi.add(i);
        }
        return pi;
    }
}
