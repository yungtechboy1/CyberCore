package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import sun.security.jgss.GSSCaller;

import java.util.ArrayList;
import java.util.UUID;

public class CrateObject {
//new TypeToken<ArrayList<UUID>>() {}.getType();
    public Position Location;
    public CrateData CD;


    public CrateObject(ConfigSection c) {
//        super(c);
    }
}
