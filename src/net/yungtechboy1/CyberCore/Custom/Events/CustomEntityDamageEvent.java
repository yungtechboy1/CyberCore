package net.yungtechboy1.CyberCore.Custom.Events;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityEvent;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.EventException;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by carlt on 3/11/2019.
 */
public class CustomEntityDamageEvent extends EntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    public final Map<CustomDamageModifier, Float> modifiers;
    public final Map<CustomDamageModifier, Float> originals;
    private final CustomDamageCause cause;
    public Entity entity;
    private boolean isCancelled = false;
    private int CoolDownTicks = 20;

    public CustomEntityDamageEvent(Entity entity, CustomDamageCause cause, float damage) {
        this(entity, cause, new EnumMap<CustomDamageModifier, Float>(CustomDamageModifier.class) {
            {
                put(CustomDamageModifier.BASE, damage);
            }
        });
    }

    public CustomEntityDamageEvent(Entity entity, CustomDamageCause cause, Map<CustomDamageModifier, Float> modifiers) {
        this.entity = entity;
        this.cause = cause;
        this.modifiers = modifiers;

        this.originals = this.modifiers;

        if (!this.modifiers.containsKey(CustomDamageModifier.BASE)) {
            throw new EventException("BASE Damage modifier missing");
        }

        if (entity.hasEffect(Effect.DAMAGE_RESISTANCE)) {
            this.setDamage((float) -(this.getDamage(CustomDamageModifier.BASE) * 0.20 * (entity.getEffect(Effect.DAMAGE_RESISTANCE).getAmplifier() + 1)), CustomDamageModifier.RESISTANCE);
        }
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

    public int getCoolDownTicks() {
        return CoolDownTicks;
    }

    public void setCoolDownTicks(int coolDownTicks) {
        CoolDownTicks = coolDownTicks;
    }

    public CustomDamageCause getCause() {
        return cause;
    }

    public float getOriginalDamage() {
        return this.getOriginalDamage(CustomDamageModifier.BASE);
    }

    public float getOriginalDamage(CustomDamageModifier type) {
        if (this.originals.containsKey(type)) {
            return this.originals.get(type);
        }

        return 0;
    }

    public float getDamage(CustomDamageModifier type) {
        if (this.modifiers.containsKey(type)) {
            return this.modifiers.get(type);
        }

        return 0;
    }

    public void setDamage(float damage) {
        this.setDamage(damage, CustomDamageModifier.BASE);
    }

    public void setDamage(float damage, CustomDamageModifier type) {
        this.modifiers.put(type, damage);
    }

    public boolean isApplicable(CustomDamageModifier type) {
        return this.modifiers.containsKey(type);
    }

    public float getFinalDamage() {
        float damage = 0;
        for (Float d : this.modifiers.values()) {
            if (d != null) {
                damage += d;
            }
        }

        return damage;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean forceCancel) {

        isCancelled = forceCancel;
    }

    @Override
    public void setCancelled() {
        setCancelled(true);
    }

    public enum CustomDamageModifier {
        /**
         * Raw amount of damage
         */
        BASE,
        /**
         * Damage reduction caused by wearing armor
         */
        ARMOR,
        /**
         * Additional damage caused by damager's Strength potion effect
         */
        STRENGTH,
        /**
         * Damage reduction caused by damager's Weakness potion effect
         */
        WEAKNESS,
        /**
         * Damage reduction caused by the Resistance potion effect
         */
        RESISTANCE,
        /**
         * Damage reduction caused by the Damage absorption effect
         */
        ABSORPTION,

        //ARMOR_ENCHANTMENTS

        MODIFIER_ARMOR_ABILLITY
    }

    public enum CustomDamageCause {
        /**
         * Damage caused by contact with a block such as a Cactus
         */
        CONTACT,
        /**
         * Damage caused by being attacked by another entity
         */
        ENTITY_ATTACK,
        /**
         * Damage caused by being hit by a projectile such as an Arrow
         */
        PROJECTILE,
        /**
         * Damage caused by being put in a block
         */
        SUFFOCATION,
        /**
         * Fall damage
         */
        FALL,
        /**
         * Damage caused by standing in fire
         */
        FIRE,
        /**
         * Burn damage
         */
        FIRE_TICK,
        /**
         * Damage caused by standing in lava
         */
        LAVA,
        /**
         * Damage caused by running out of air underwater
         */
        DROWNING,
        /**
         * Block explosion damage
         */
        BLOCK_EXPLOSION,
        /**
         * Entity explosion damage
         */
        ENTITY_EXPLOSION,
        /**
         * Damage caused by falling into the void
         */
        VOID,
        /**
         * Player commits suicide
         */
        SUICIDE,
        /**
         * Potion or spell damage
         */
        MAGIC,
        /**
         * Plugins
         */
        CUSTOM,
        /**
         * Damage caused by being struck by lightning
         */
        LIGHTNING,
        /**
         * Damage caused by hunger
         */
        HUNGER, DoubleTakeMagic(EntityDamageEvent.DamageCause.MAGIC);

        EntityDamageEvent.DamageCause CDC = null;

        CustomDamageCause(EntityDamageEvent.DamageCause dc) {
            CDC = dc;
        }

        CustomDamageCause() {
        }


        public EntityDamageEvent.DamageCause getEntityDamageEventCause() {
            if (ordinal() > HUNGER.ordinal()) {
                return CDC;
            }
            return EntityDamageEvent.DamageCause.values()[this.ordinal()];
        }
    }
}
