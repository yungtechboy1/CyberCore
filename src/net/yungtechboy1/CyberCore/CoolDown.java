package net.yungtechboy1.CyberCore;

/**
 * Created by carlt on 5/16/2019.
 */
public class CoolDown {//TODO Maybe extend ConfigSelection
    public String Key = null;
    protected int Time = -1;

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
    public CoolDown(String name, int secs, int mins, int hrs) {
//        t = tick;
        Key = name;
        setTimeSecs(secs, mins, hrs);
    }

    public CoolDown(String name, int secs, int mins) {
//        t = tick;
        Key = name;
        setTimeSecs(secs, mins);
    }

    public CoolDown(String name, int secs) {
//        t = tick;
        Key = name;
        setTimeSecs(secs);
    }

    public CoolDown(String name) {
//        t = tick;
        Key = name;
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
        setTime(CyberCoreMain.getInstance().getIntTime() + secs);
        return this;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public boolean isValid() {
        return isValidTime();
    }

    @Override
    public String toString() {
        return "CoolDown{" +
                "Key='" + Key + '\'' +
                ", Time=" + Time +
                '}';
    }

    private boolean isValidTime() {
        int ct = CyberCoreMain.getInstance().getIntTime();
//        System.out.println(ct+" > "+Time);
        return ct < Time;
    }

}
