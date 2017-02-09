package net.yungtechboy1.CyberCore.MobAI;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.level.Position;

/**
 * Created by carlt_000 on 1/13/2017.
 */
public class SpawnEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    Position Pos;
    String Type;
    public SpawnEvent(String type, Position pos){
        Type = type;
        Pos = pos;
    }

    public Position getPos() {
        return Pos;
    }

    public String getType() {
        return Type;
    }
}
