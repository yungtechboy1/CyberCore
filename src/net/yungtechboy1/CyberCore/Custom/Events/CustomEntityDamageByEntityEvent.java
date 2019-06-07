package net.yungtechboy1.CyberCore.Custom.Events;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.CorePlayer;

import java.util.Map;

/**
 * Created by carlt on 3/11/2019.
 */
public class CustomEntityDamageByEntityEvent extends CustomEntityDamageEvent {

    private final CorePlayer damager;

    private float knockBack;

    public CustomEntityDamageByEntityEvent(CorePlayer damager, Entity entity, CustomEntityDamageEvent.CustomDamageCause cause, float damage) {
        this(damager, entity, cause, damage, 0.3f);
    }

    public CustomEntityDamageByEntityEvent(CorePlayer damager, Entity entity, CustomEntityDamageEvent.CustomDamageCause cause, Map<CustomDamageModifier, Float> modifiers) {
        this(damager, entity, cause, modifiers, 0.3f);
    }

    public CustomEntityDamageByEntityEvent(CorePlayer damager, Entity entity, CustomEntityDamageEvent.CustomDamageCause cause, float damage, float knockBack) {
        super(entity, cause, damage);
        this.damager = damager;
        this.knockBack = knockBack;
        this.addAttackerModifiers(damager);
    }

    public CustomEntityDamageByEntityEvent(CorePlayer damager, Entity entity, CustomEntityDamageEvent.CustomDamageCause cause, Map<CustomDamageModifier, Float> modifiers, float knockBack) {
        super(entity, cause, modifiers);
        this.damager = damager;
        this.knockBack = knockBack;
        this.addAttackerModifiers(damager);
    }

    protected void addAttackerModifiers(CorePlayer damager) {
        if (damager.hasEffect(Effect.STRENGTH)) {
            this.setDamage((float) (this.getDamage(CustomDamageModifier.BASE) * 0.3 * (damager.getEffect(Effect.STRENGTH).getAmplifier() + 1)), CustomDamageModifier.STRENGTH);
        }

        if (damager.hasEffect(Effect.WEAKNESS)) {
            this.setDamage(-(float) (this.getDamage(CustomDamageModifier.BASE) * 0.2 * (damager.getEffect(Effect.WEAKNESS).getAmplifier() + 1)), CustomDamageModifier.WEAKNESS);
        }
    }

    public CorePlayer getDamager() {
        return damager;
    }

    public CorePlayer getCorePlayer() {
        return damager;
    }

    public float getKnockBack() {
        return knockBack;
    }

    public void setKnockBack(float knockBack) {
        this.knockBack = knockBack;
    }
}
