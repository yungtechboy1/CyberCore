package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import net.yungtechboy1.CyberCore.CorePlayer;

/**
 * Created by carlt on 4/11/2019.
 */
public class HomeData {


    private final String name;
    private final int x;
    private final int y;
    private final int z;
    private final String level;
    private final String owner;
    private final String owneruuid;

    public HomeData(String name, Position v, CorePlayer p) {
        this(name, v.getFloorX(), v.getFloorY(), v.getFloorZ(), v.getLevel().getName(), p.getName(), p.getUniqueId().toString());
    }

    public HomeData(String name, CorePlayer p) {
        this(name, p.getFloorX(), p.getFloorY(), p.getFloorZ(), p.getLevel().getName(), p.getName(), p.getUniqueId().toString());
    }

    public HomeData(String name, Double x, Double y, Double z, String level, CorePlayer p) {
        this(name, (int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z), level, p.getName(), p.getUniqueId().toString());
    }

    public HomeData(String name, int x, int y, int z, String level, CorePlayer p) {
        this(name, x, y, z, level, p.getName(), p.getUniqueId().toString());
    }

    public HomeData(String name, int x, int y, int z, String level, String owner, String owneruuid) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.level = level;
        this.owner = owner;
        this.owneruuid = owneruuid;
    }


    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getLevel() {
        return level;
    }

    public String getOwner() {
        return owner;
    }

    public String getOwneruuid() {
        return owneruuid;
    }

    public Vector3 toVector3() {

        return new Vector3(x, y, z);
    }

    public Position toPosition(Level l) {

        return new Position(x, y, z, l);
    }
}
