package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

import java.awt.*;

public class PlayerJumpEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerJumpEvent(Player player) {
        this.player = player;
    }

}
