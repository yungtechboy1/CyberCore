package net.yungtechboy1.CyberCore;

import cn.nukkit.Server;
import cn.nukkit.utils.ConfigSection;

/**
 * Created by carlt on 5/16/2019.
 */
public class CoolDown extends ConfigSection {
    public int Time = -1;
    public String Key = null;
    private int Type = 0;

    public CoolDown() {
    }

    public CoolDown(int t) {
        Time = t;
    }

    /***
     *
     * @param name
     * @param secs
     */
    public CoolDown(String name,int secs, int mins, int hrs) {
//        t = tick;
        Key = name;
        setTimeSecs(secs, mins, hrs);
    }

    public CoolDown(String name,int secs, int mins) {
//        t = tick;
        Key = name;
        setTimeSecs(secs, mins);
    }

    public CoolDown(String name,int secs) {
//        t = tick;
        Key = name;
        setTimeSecs(secs);
    }

    public CoolDown(String name) {
//        t = tick;
        Key = name;
    }

    public CoolDown( int tick, String key) {
        Time = tick;
        Key = key;
    }

    public CoolDown AddTo(int s) {
        Time = +s;
        return this;
    }

    public int getTime() {
        return Time;
    }

    private void setTime(int time) {
        Time = time;
    }

    public CoolDown setTimeSecs(int hrs, int mins, int secs) {
        return setTimeSecs(secs + (60 * mins) + (60 * 60 * hrs));
    }

    public CoolDown setTimeSecs(int mins, int secs) {
        return setTimeSecs(secs + (60 * mins));
    }

    public CoolDown setTimeSecs(int secs) {
        Type = 1;
        setTime(CyberCoreMain.getInstance().GetIntTime() + secs);
        return this;
    }

    public CoolDown setTimeTick(int hrs, int mins, int secs) {
        return setTimeTick(secs + (60 * mins) + (60 * 60 * hrs));
    }

    public CoolDown setTimeTick(int mins, int secs) {
        return setTimeTick(secs + (60 * mins));
    }

    public CoolDown setTimeTick(int secs) {
        Type = 2;
        setTime(CyberCoreMain.getInstance().getServer().getTick() + secs);
        return this;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public int getType() {
        return Type;
    }

    public boolean isValid() {
        if (Type == 1) {
            return isValidTime();
        } else if (Type == 2) {
            return isValidTick();
        } else {
            return (isValidTick() || isValidTime());
        }
    }

    private boolean isValidTick() {
        int ct = Server.getInstance().getTick();
//        System.out.println(ct+" > "+Time);
        return ct < Time;
    }

    private boolean isValidTime() {
        int ct = CyberCoreMain.getInstance().GetIntTime();
//        System.out.println(ct+" > "+Time);
        return ct < Time;
    }
}
