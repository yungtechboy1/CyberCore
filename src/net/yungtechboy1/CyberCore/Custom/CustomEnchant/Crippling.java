package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.level.particle.CriticalParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class Crippling extends CustomEnchantment {


    private int cooldown;

    public Crippling() {
        super(CRIPPLING, "Crippling Strike", 2, EnchantmentType.SWORD);
        cooldown = 10 - getLevel();
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

        long ct = new Date().getTime() / 1000;

        Item ph = ((Player) attacker).getInventory().getItemInHand();
        int nexttick = ph.getNamedTag().getInt("CSnexttick");

        if (ct >= nexttick) {
            int rand = new NukkitRandom(CRIPPLING*CRIPPLING).nextRange(0, 100);
            if (rand >= 100 - (18 * getLevel())) {
                ((Player) attacker).sendMessage(TextFormat.GREEN + getName().toUpperCase() + " ACTIVATED");
                ((Player) entity).sendMessage(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED " + getName().toUpperCase());
                SendParticles(entity);
                entity.namedTag.putLong("Crippling", ct+getLevel());

                ph.getNamedTag().putLong("CSnexttick", ct + cooldown);
                ((Player) attacker).getInventory().setItemInHand(ph);
            }
        }
    }

    public void SendParticles(Entity entity) {
        entity.getLevel().addParticle(new CriticalParticle(entity,2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, 0, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, 0, 0),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, 0, 1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, 0, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, 0, 1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(1, 0, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(1, 0, 0),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(1, 0, 1),2));

        //1 Up
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, 1, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, 1, 0),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, 1, 1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, 1, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, 1, 0),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, 1, 1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(1, 1, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(1, 1, 0),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, 1, 1),2));

        //1 Down
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, -1, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, -1, 0),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(-1, -1, 1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, -1, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, -1, 0),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, -1, 1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(1, -1, -1),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(1, -1, 0),2));
        entity.getLevel().addParticle(new CriticalParticle(entity.add(0, -1, 1),2));
    }

    @Override
    public boolean isCompatibleWith(Enchantment enchantment) {
        return super.isCompatibleWith(enchantment);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
