package net.yungtechboy1.CyberCore.Manager.Crate;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.BlockEventPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Crate.Tasks.RollTick;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin.Crate.AdminCrateChooseCrateWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CrateMain {
    public static final String CK = "CrateKey";
    public ArrayList<String> PrimedPlayer = new ArrayList<>();
    public ArrayList<String> SetKeyPrimedPlayer = new ArrayList<>();
    public HashMap<Vector3, CrateObject> CrateChests = new HashMap<>();
    public HashMap<String, KeyData> CrateKeys = new HashMap<>();
    //    private ConfigSection CrateLocations = new ConfigSection();
    private HashMap<String, FloatingTextParticle> cratetxt = new HashMap<>();
    private HashMap<String, CrateData> CrateMap = new HashMap<>();
    private Config c;
    private Config cc;
    private Config ck;
    private CyberCoreMain CCM;

    //    public final String CrateKeyNBTKey =
    public CrateMain(CyberCoreMain ccm) {
        CCM = ccm;
        CCM.getLogger().info("Loaded Crates System");
//        CCM.getServer().getPluginManager().registerEvents(new CrateListener(CCM), CCM);
        c = new Config(new File(ccm.getDataFolder(), "crate-locations.yml"), Config.YAML, new LinkedHashMap<String, Object>() {
        });
        ck = new Config(new File(ccm.getDataFolder(), "crate-keys.yml"), Config.YAML, new LinkedHashMap<String, Object>() {
        });
        cc = new Config(new File(ccm.getDataFolder(), "crate-data.yml"), Config.YAML, new LinkedHashMap<String, Object>() {
        });
        c.save();
        ck.save();
        cc.save();

        ConfigSection cl = c.getRootSection();
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
                    String nme = c.getString("Name");
                    String key = c.getString("Key");
                    if (nme == null || nme.length() == 0) {
                        CCM.getLogger().error("Error loading Crate Map! No Name Found for this config! E2545");
                        continue;
                    }
                    CCM.getLogger().info("[CRATE] Crate " + nme + " | " + key + " Has been loaded!");
                    CrateMap.put(key, new CrateData(c));
                }
            }
        }

        if (cl.isEmpty()) {

        } else {
            System.out.println("CL SIZE +=====>>" + cl.size());
            for (Object o : cl.getAllMap().values()) {
                if (o instanceof ConfigSection) {
                    ConfigSection c = (ConfigSection) o;
                    String nme = c.getString("Key");
                    CrateData cda = CrateMap.getOrDefault(nme, null);
//                    ConfigSection cccc = c.getSection("Loc");;
//                    Position po = new Position(cccc.getDouble("x"),cccc.getDouble("y"),cccc.getDouble("z"), Server.getInstance().getLevelByName(cccc.getString("level")));
                    Position po = (Position) c.get("Loc");
                    CrateObject co = new CrateObject(po, cda);
                    if (cda != null) {
                        CrateChests.put(po.asBlockVector3().asVector3(), co);
                    } else {
                        CyberCoreMain.getInstance().getLogger().error("Error Loading Chest Crate Location! " + nme);
                    }
                }

            }
        }

        ConfigSection CKC = ck.getRootSection();
        if (cl.isEmpty()) {

        } else {
            System.out.println("CKC SIZE +=====>>" + CKC.size());
            for (Object o : CKC.getAllMap().values()) {
                if (o instanceof ConfigSection) {
                    ConfigSection c = (ConfigSection) o;
                    String nme = c.getString("Key_Name");
                    if (nme == null || nme.length() == 0) {
                        System.out.println("Error! The Key_Name was Null!");
                        return;
                    }

                    CrateKeys.put(nme, new KeyData(c));
                    CyberCoreMain.getInstance().getLogger().info("Loaded " + nme + " Crate Key!");
                }

            }
        }

    }

    public static boolean isItemKey(Item i){
        if(i == null || i.getId() == 0)return false;
        return i.hasCompoundTag() && i.getNamedTag().contains(CrateMain.CK);
    }
    public HashMap<String, CrateData> getCrateMap() {
        return CrateMap;
    }

    public void save() {
        ConfigSection config = new ConfigSection();
        for (Object o : CrateMap.values()) {
            CrateData cd = (CrateData) o;
            config.put(cd.Name, cd.toConfig());
        }
        ConfigSection config2 = new ConfigSection();
        int k = 0;
        for (Object o : CrateChests.values()) {
            CrateObject cd = (CrateObject) o;
            config2.put("" + k++, cd.toConfig());
        }
        cc.setAll(config);
        cc.save();
        c.setAll(config2);
        c.save();


    }


    public void reload() {
        c = new Config(new File(CCM.getDataFolder(), "crate-locations.yml"), Config.YAML, new LinkedHashMap<String, Object>() {
        });
        cc = new Config(new File(CCM.getDataFolder(), "crate-data.yml"), Config.YAML, new LinkedHashMap<String, Object>() {
        });

        ConfigSection cd = cc.getRootSection();
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
//                    System.out.println("THIS IS A >>>>" + o + "+" + o.getClass());
                    String nme = c.getString("Name");
                    if (nme == null || nme.length() == 0) {
                        CCM.getLogger().error("Error loading Crate Map! No Name Found for this config! E2545");
                        continue;
                    }
                    CCM.getLogger().info("[CRATE] Reloading Crate" + nme + "!");
                    CrateMap.put(nme, new CrateData(c));
                }
            }
        }
    }

    public String Vector3toKey(Vector3 v) {
        return v.floor().toString();
    }

    public void addCrate(CorePlayer cp, Vector3 v) {
        cp.showFormWindow(new AdminCrateChooseCrateWindow(v, this));
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
        return i.getNamedTag().contains((String) data.get("Item-NBT-Tag"));
    }

    public CrateObject isCrate(Vector3 b) {
        if(CrateChests.containsKey(b))return CrateChests.get(b);
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

    public void addCrateKey(KeyData keyData) {
        if (keyData == null) return;
        CrateKeys.put(keyData.Key_Name, keyData);
    }

    public void rollCrate(Block b, Player player) {



        ConfigSection data = new ConfigSection() {{
            put("PlayerName", player.getName());
            put("slot", -1);
            put("possible-items", items);
            put("crate-name", cn);
            put("pos", pos);
        }};
        showCrate(pos, player);
        Server.getInstance().getScheduler().scheduleTask(new RollTick(this, data));
    }
}
