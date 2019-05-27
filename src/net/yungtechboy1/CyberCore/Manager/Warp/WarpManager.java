package net.yungtechboy1.CyberCore.Manager.Warp;

import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.HashMap;

/**
 * Created by carlt on 4/15/2019.
 */
public class WarpManager {
    HashMap<String, WarpData> WarpList = new HashMap<>();
    private CyberCoreMain Main;

    public WarpManager(CyberCoreMain main) {

        Main = main;
    }

    public void AddWarp(WarpData wd) {
        WarpList.put(wd.getName(), wd);
    }

    public void AddWarp(String name, double x, double y, double z, String level) {
        WarpList.put(name.toLowerCase(), new WarpData(name, x, y, z, level));
    }

    public void RemoveWarp(String name) {
        WarpList.remove(name);
    }

    public WarpData GetWarp(String name) {
        if (!WarpList.containsKey(name.toLowerCase())) return null;
        return WarpList.get(name.toLowerCase());
    }
}
