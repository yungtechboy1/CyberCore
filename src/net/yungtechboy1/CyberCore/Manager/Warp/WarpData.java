package net.yungtechboy1.CyberCore.Manager.Warp;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;

/**
 * Created by carlt on 4/15/2019.
 */
public class WarpData {
    private final String name;
    private final double x;
    private final double y;
    private final double z;
    private final String level;

    public WarpData(String name, double x, double y, double z, String level) {

        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getLevel() {
        return level;
    }

    public Location toLocation(Level l) {
        return new Location(x, y, z, l);
    }

    public Location toLocation() {
        return new Location(x, y, z);
    }
}
