package net.yungtechboy1.CyberCore.Manager.Crate;


import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.BlockEventPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.CrateAdminChooseCrateWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CrateMain {
    public ArrayList<String> PrimedPlayer = new ArrayList<>();
    private HashMap<String, FloatingTextParticle> cratetxt = new HashMap<>();
    private ConfigSection CrateLocations = new ConfigSection();
    private HashMap<String, CrateData> CrateMap = new HashMap<>();
    private HashMap<Vector3, CrateObject> CrateChests = new HashMap<>();
    private Config c;
    private Config cc;
    private CyberCoreMain CCM;

    //    public final String CrateKeyNBTKey =
    public CrateMain(CyberCoreMain ccm) {
        CCM = ccm;
        CCM.getLogger().info("Loaded Crates System");
        CCM.getServer().getPluginManager().registerEvents(new CrateListener(CCM), CCM);
        c = new Config(new File(ccm.getDataFolder(), "crate-locations.yml"), Config.YAML, new LinkedHashMap<String, Object>() {
        });
        cc = new Config(new File(ccm.getDataFolder(), "crate-data.yml"), Config.YAML, new LinkedHashMap<String, Object>() {
        });
        c.save();
        cc.save();

        ConfigSection cd = cc.getRootSection();
        System.out.println(cd);
        Map<String, Object> cdd = cd.getAllMap();
        if (cd.isEmpty()) {
            CrateData ccd = new CrateData("DEFAULT");
            cc.set("DEFAULT", ccd.toConfig());
            cc.save();
        } else {
            System.out.println("CD SIZE +=====>>" + cd.size());
            for (Object o : cdd.values()) {
                if (o instanceof ConfigSection) {
                    ConfigSection c = (ConfigSection) o;
                    System.out.println("THIS IS A >>>>" + o + "+" + o.getClass());
                    String nme = c.getString("Name");
                    if(nme == null || nme.length() == 0){
                        CCM.getLogger().error("Error loading Crate Map! No Name Found for this config! E2545");
                        continue;
                    }
                    CCM.getLogger().info("[CRATE] Crate"+nme+" Has been loaded!");
                    CrateMap.put(nme,new CrateData(c));
                }
            }
//            for(int i = 0;i < cd.size();i++){
//                ConfigSection z = cd.getSection(cd.get);
//            }
//            CrateMap.put()
        }

    }

    public String Vector3toKey(Vector3 v) {
        return v.floor().toString();
    }

    public void addCrate(CorePlayer cp, Vector3 v) {
        cp.showFormWindow(new CrateAdminChooseCrateWindow(v));
//        CrateLocations.put(Vector3toKey(v),)
    }

    public void createCrate(String name, Block b) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("Item-NBT-Tag", "DefaultKey");
        String save = b.getX() + ":" + b.getY() + ":" + b.getZ();
        data.put("Loc", save);
        data.put("Prizes", new ArrayList<String>());
        c.set(name, data);
        c.save();
    }

    public boolean isKey(String cratename, Item i) {
        HashMap<String, Object> data = (HashMap<String, Object>) c.get(cratename);
        if (i.getNamedTag().contains((String) data.get("Item-NBT-Tag"))) {
            return true;
        }
        return false;
    }

    public String isCrate(Block b) {
        String loc = b.getX() + ":" + b.getY() + ":" + b.getZ();
        for (Map.Entry<String, Object> entry : c.getAll().entrySet()) {
            HashMap<String, Object> data = (HashMap<String, Object>) entry.getValue();
            if (data.get("Loc").equals(loc)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void showCrate(Block b, Player p) {
        p.sendMessage("Opened"); //Debug
        BlockEventPacket pk = new BlockEventPacket();
        pk.x = (int) b.getX();
        pk.y = (int) b.getY();
        pk.z = (int) b.getZ();
        pk.case1 = 1;
        pk.case2 = 2;
        p.dataPacket(pk);
        FloatingTextParticle ft = new FloatingTextParticle(new Vector3(b.getX() + 0.5, b.getY(), b.getZ() + 0.5), "", "Some Item");
        DataPacket[] packets = ft.encode();
        if (packets.length == 1) {
            p.dataPacket(packets[0]);
        } else {
            for (DataPacket packet : packets) {
                p.dataPacket(packet);
            }
        }
        cratetxt.put(p.getName(), ft);
    }

    public void hideCrate(Block b, Player p) {
        p.sendMessage("Closed"); //Debug
        BlockEventPacket pk = new BlockEventPacket();
        pk.x = (int) b.getX();
        pk.y = (int) b.getY();
        pk.z = (int) b.getZ();
        pk.case1 = 1;
        pk.case2 = 0;
        p.dataPacket(pk);
        if (cratetxt.containsKey(p.getName())) {
            FloatingTextParticle ft = cratetxt.get(p.getName());
            ft.setInvisible();
            DataPacket[] packets = ft.encode();
            if (packets.length == 1) {
                p.dataPacket(packets[0]);
            } else {
                for (DataPacket packet : packets) {
                    p.dataPacket(packet);
                }
            }
            cratetxt.remove(p.getName());
        }
    }

}
