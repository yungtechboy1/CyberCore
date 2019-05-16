package net.yungtechboy1.CyberCore;

/**
 * Created by carlt on 5/16/2019.
 */
public class CoolDown {
    public int t = -1;
    public String k = null;

    public CoolDown(String name){
//        t = tick;
        k = name;
    }
    public CoolDown(String name, int tick){
        t = tick;
        k = name;
    }

    public CoolDown AddSecs(int s){
        t =+ s;
        return this;
    }

    public int getTime(){
        return t;
    }

    public String getKey(){
        return k;
    }

    public boolean isValid(){
        int ct = CyberCoreMain.getInstance().GetIntTime();
        return ct > t;
    }

}
