package net.yungtechboy1.CyberCore.Manager.Factions.Mission;

import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joneca04 on 12/28/2016.
 */
public class Mission {
    public FactionsMain Main;
    public String name;
    public String desc;
    public Integer id;
    public boolean enabled;
    public HashMap<String, Integer> Break = new HashMap<>();
    public HashMap<String, Integer> Place = new HashMap<>();
    public ArrayList<Item> ItemReq = new ArrayList<>();
    public Integer Kill = 0;
    public Integer XPReward = 0;
    public Integer MoneyReward = 0;
    public ArrayList<Item> ItemReward = new ArrayList<>();
    public Integer PointReward = 0;


    public Mission(FactionsMain main, Mission mission) {
        Main = mission.Main;
        name = mission.name;
        desc = mission.desc;
        id = mission.id;
        enabled = mission.enabled;
        Break = mission.Break;
        Place = mission.Place;
        ItemReq = mission.ItemReq;
        Kill = mission.Kill;
        XPReward = mission.XPReward;
        MoneyReward = mission.MoneyReward;
        ItemReward = mission.ItemReward;
        PointReward = mission.PointReward;
        Main = main;
    }

    public Mission(FactionsMain main, ConfigSection config) {
        Main = main;
        name = (String)config.get("name");
        desc = (String)config.get("desc");
        id = (int)config.get("id");
        enabled = (Boolean) config.get("enabled");
        ConfigSection requirement = (ConfigSection) config.get("requirement");
        ConfigSection reward = (ConfigSection) config.get("reward");
        if (requirement != null) {
            //break
            if (requirement.containsKey("break")) {
                ConfigSection brk = (ConfigSection) requirement.get("break");
                if (brk != null && brk.entrySet().size() > 0) {
                    for (Map.Entry<String, Object> a : brk.entrySet()) {
                        String key = a.getKey() + "";
                        Integer val = (Integer) a.getValue();
                        Break.put(key, val);
                    }
                }
            }
            //place
            if (requirement.containsKey("place")) {
                ConfigSection plc = (ConfigSection) requirement.get("place");
                if (plc != null && plc.entrySet().size() > 0) {
                    for (Map.Entry<String, Object> a : plc.entrySet()) {
                        String key = a.getKey() + "";
                        Integer val = (Integer) a.getValue();
                        Place.put(key, val);
                    }
                }
            }
            //item
            if (requirement.containsKey("item")) {
                ConfigSection itm = requirement.getSection("item");
                if (itm.entrySet().size() > 0) {
                    for (Map.Entry<String, Object> a : itm.entrySet()) {
                        String key = a.getKey();
                        Integer val = (Integer) a.getValue();

                        Integer bid = 0;
                        Integer bmeta = 0;
                        Integer bcount = 0;
                        if (key.contains("|")) {
                            bid = Integer.parseInt(key.split("\\|")[0]);
                            bmeta = Integer.parseInt(key.split("\\|")[1]);
                        } else {
                            bid = Integer.parseInt(key);
                        }
                        bcount = (int) a.getValue();
                        Item i = Item.get(bid, bmeta, bcount);
                        ItemReq.add(i);
                    }
                }
            }
            //kill
            if (requirement.containsKey("kill")) Kill = (Integer) requirement.get("kill");
        }
        if (reward != null) {
            XPReward = reward.getInt("xp");
            PointReward = reward.getInt("point");
            MoneyReward = reward.getInt("money");
            //item
            if (reward.containsKey("item")) {
                ConfigSection itm = reward.getSection("item");
                if (itm.entrySet().size() > 0) {
                    for (Map.Entry<String, Object> a : itm.entrySet()) {
                        String key = a.getKey();
                        Integer val = (Integer) a.getValue();

                        Integer bid = 0;
                        Integer bmeta = 0;
                        Integer bcount = 0;
                        if (key.contains("|")) {
                            bid = Integer.parseInt(key.split("\\|")[0]);
                            bmeta = Integer.parseInt(key.split("\\|")[1]);
                        } else {
                            bid = Integer.parseInt(key);
                        }
                        bcount = (int) a.getValue();
                        Item i = Item.get(bid, bmeta, bcount);
                        ItemReward.add(i);
                    }
                }
            }

        }
    }

    public ArrayList<Item> getItemReward() {
        return ItemReward;
    }

    public void setItemReward(ArrayList<Item> itemReward) {
        ItemReward = itemReward;
    }

    /*
    * for(Map.Entry<String,Object> a: plc.entrySet()){
                        String key = a.getKey();
                        Integer bid = 0;
                        Integer bmeta = 0;
                        Integer bcount = 0;
                        if(key.contains(|")){
                            bid = Integer.parseInt(key.split("\\|")[0]);
                            bmeta = Integer.parseInt(key.split("\\|")[1]);
                        }else{
                            bid = Integer.parseInt(key);
                        }
                        bcount = (int)a.getValue();
                        Item i = Item.get(bid,bmeta,bcount);
                    }
    * */
}
