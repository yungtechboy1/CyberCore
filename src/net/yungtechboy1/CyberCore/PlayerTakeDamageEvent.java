package net.yungtechboy1.CyberCore;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;

public class PlayerTakeDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    public CustomEntityDamageEvent source;
    public float DamageReduction = 0;
    public float DamageIncrease = 0;

    public PlayerTakeDamageEvent(CustomEntityDamageEvent source) {
        this.source = source;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

    public float getFinalDamage() {
        return source.getFinalDamage() + DamageIncrease + DamageReduction;
    }
}
