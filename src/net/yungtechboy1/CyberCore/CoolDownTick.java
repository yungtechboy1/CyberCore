package net.yungtechboy1.CyberCore;

import cn.nukkit.Server;
import cn.nukkit.utils.ConfigSection;

/**
 * Created by carlt on 5/16/2019.
 */
public class CoolDownTick extends ConfigSection {
    protected int Tick = -1;
    public String Key = null;

    public CoolDownTick() {
    }

    public CoolDownTick(int t) {
        Tick = t;
    }

    /***
     *
     * @param name
     * @param secs
     */
    public CoolDownTick(String name,int secs, int mins, int hrs) {
//        t = tick;
        Key = name;
        setTimeSecs(secs, mins, hrs);
    }

    public CoolDownTick(String name,int secs, int mins) {
//        t = tick;
        Key = name;
        setTimeSecs(secs, mins);
    }

    public CoolDownTick(String name,int ticks) {
//        t = tick;
        Key = name;
        setTimeTick(ticks);
    }

    public CoolDownTick(String name) {
//        t = tick;
        Key = name;
    }

    public CoolDownTick AddTo(int s) {
        Tick = +s;
        return this;
    }

    public int getTick() {
        return Tick;
    }

    private void setTick(int ticks) {
        Tick = ticks;
    }

    public CoolDownTick setTimeSecs(int hrs, int mins, int secs) {
        return setTimeSecs(secs + (60 * mins) + (60 * 60 * hrs));
    }

    public CoolDownTick setTimeSecs(int mins, int secs) {
        return setTimeSecs(secs + (60 * mins));
    }

    public CoolDownTick setTimeSecs(int secs) {
        setTick(CyberCoreMain.getInstance().getServer().getTick() + (20*secs));
        return this;
    }

    public CoolDownTick setTimeTick(int ticks) {
        setTick(CyberCoreMain.getInstance().getServer().getTick() + ticks);
        return this;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public boolean isValid() {
            return (isValidTick());
    }

    private boolean isValidTick() {
        int ct = Server.getInstance().getTick();
//        System.out.println(ct+" > "+Time);
        return ct < Tick;
    }
}
