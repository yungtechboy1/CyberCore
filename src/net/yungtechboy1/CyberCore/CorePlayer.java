package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.network.SourceInterface;

import java.lang.reflect.Field;
import java.util.HashMap;

public class CorePlayer extends Player {


    public Integer kills = 0;
    public Integer deaths = 0;
    public HashMap<String, Object> extraData = new HashMap<>();
    private Player P;

    public CorePlayer(Player p, SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);
        try {
            Class clazz = Player.class;
            Field[] fields = clazz.getFields();//Gives all declared public fields and inherited public fields of Super class
            for (Field field : fields) {
                Class type = field.getType();
                Object obj = field.get(p);
                this.getClass().getField(field.getName()).set(this, obj);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        P = p;
    }

    public Integer addDeath() {
        return deaths += 1;
    }

    public Integer addDeath(Integer amount) {
        return deaths += amount;
    }

    public Integer addKill() {
        return kills += 1;
    }

    public Integer addKills(Integer amount) {
        return kills += amount;
    }

    public double calculateKD() {
        return kills / deaths;
    }
}
