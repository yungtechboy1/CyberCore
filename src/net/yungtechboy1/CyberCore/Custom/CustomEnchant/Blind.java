package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.level.particle.InkParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class Blind extends CustomEnchantment {


    public Blind() {
        super(BLIND, "Blind", 2, EnchantmentType.SWORD);
        ER = EnchantRarity.R30;
        SetCooldown(60 - (getLevel() * 3));
    }

    @Override
    public int getMaxEnchantableLevel() {
        return super.getMaxEnchantableLevel();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isMajor() {
        return true;
    }

    @Override
    public void doPostAttack(Entity attacker, Entity entity) {
        if (!(entity instanceof Player)) return;
        if (!(attacker instanceof Player)) return;

        Item ph = ((Player) attacker).getInventory().getItemInHand();


        if (CheckCooldown(ph)) {
            int rand = new NukkitRandom(BLIND * BLIND).nextRange(0, 25 - getLevel());
            //Server.getInstance().getLogger().info("POST ATTACK!!!" + rand + " <= " + 15*getLevel());
            if (rand <= getLevel()) {
                entity.addEffect(GetEffect());

                ((Player) attacker).sendActionBar(TextFormat.GREEN + getName().toUpperCase() + " ACTIVATED");
                ((Player) entity).sendActionBar(TextFormat.RED + attacker.getName().toUpperCase() + " has activated " + TextFormat.YELLOW + getName().toUpperCase());

                attacker.heal(new EntityRegainHealthEvent(attacker, .5f * getLevel(), EntityRegainHealthEvent.CAUSE_MAGIC));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity), 2));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity.add(-1, -2, -1)), 2));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity.add(-1, -2, 0)), 2));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity.add(-1, -2, 1)), 2));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity.add(0, -2, -1)), 2));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity.add(0, -2, 1)), 2));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity.add(1, -2, -1)), 2));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity.add(1, -2, 0)), 2));
                entity.getLevel().addParticle(new InkParticle(entity.getLevel().getSafeSpawn(entity.add(1, -2, 1)), 2));

                SetCooldown(GetCooldown(), ph);
            } else {
                SetCooldown(2, ph);
            }

//            Server.getInstance().getLogger().info("NEW TICK " + nextregintick + " ||| " + (ct + cooldown));
        }
        ((Player) attacker).getInventory().setItemInHand(ph);

    }

    @Override
    public boolean isCompatibleWith(Enchantment enchantment) {
        return super.isCompatibleWith(enchantment);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    public Effect GetEffect() {
        Effect e = Effect.getEffect(Effect.BLINDNESS);
        switch (getLevel()) {
            case 1:
                e.setAmplifier(1);
                e.setDuration(GetDuration());
                return e;
            case 2:
                e.setAmplifier(1);
                e.setDuration(GetDuration());
                return e;
            case 3:
                e.setAmplifier(2);
                e.setDuration((int) (GetDuration() * .5));
                return e;
            case 4:
                e.setAmplifier(2);
                e.setDuration(GetDuration() - 15);
                return e;
            case 5:
                e.setAmplifier(3);
                e.setDuration(GetDuration() - 20);
                return e;

        }
        return null;
    }

    private int GetDuration() {

        return 15 * getLevel();
    }
}
