package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.math.Vector3;

/**
 * Created by carlt on 4/11/2019.
 */
public class HomeData {


    private final String owner;
    private final String name;
    private final String posstring;

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }


    public String getPosstring() {
        return posstring;
    }


    public HomeData(String owner, String name, Vector3 v) {

        this.owner = owner;
        this.name = name;
        this.posstring = v.x + "," + v.y + "," + v.z;
    }

    public HomeData(String owner, String name, String posstring) {
        this.owner = owner;
        this.name = name;
        this.posstring = posstring;
    }

    public Vector3 toVector3() {
        Vector3 v3 = new Vector3();
        String[] s = getPosstring().split(",");
        v3.x = Double.parseDouble(s[0]);
        v3.y = Double.parseDouble(s[1]);
        v3.z = Double.parseDouble(s[2]);
        return v3;
    }
}
