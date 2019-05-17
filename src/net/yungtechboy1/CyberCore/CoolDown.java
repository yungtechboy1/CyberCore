package net.yungtechboy1.CyberCore;

import cn.nukkit.utils.ConfigSection;

/**
 * Created by carlt on 5/16/2019.
 */
public class CoolDown extends ConfigSection {
    public int Time = -1;
    public String Key = null;

    public CoolDown(String name) {
//        t = tick;
        Key = name;
    }
    public CoolDown(String key, int tick) {
        Time = tick;
        Key = key;
    }

    public CoolDown AddSecs(int s) {
        Time = +s;
        return this;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public boolean isValid() {
        int ct = CyberCoreMain.getInstance().GetIntTime();
        return ct > Time;
    }

}
