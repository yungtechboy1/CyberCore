package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHumanType;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class Poison extends CustomEnchantment {


    private int cooldown;

    public Poison() {
        super(POISON, "Poison", 2, EnchantmentType.SWORD);
        SetCooldown(90 - (getLevel()*5));
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

        EntityHumanType human = (EntityHumanType) entity;

        long ct = new Date().getTime();

        Item ph = ((Player) attacker).getInventory().getItemInHand();
        int nexttick = ph.getNamedTag().getInt("nexttick");

        if (ct >= nexttick) {
            int rand = new NukkitRandom(POISON*POISON).nextRange(0, 100);
            if (rand >= 100 - (18 * getLevel())) {
                Effect e = Effect.getEffect(Effect.POISON);
                e.setAmplifier(getLevel());
                e.setDuration(15 * getLevel());
                entity.addEffect(e);

                ((Player) attacker).sendMessage(TextFormat.GREEN + getName().toUpperCase() + " ACTIVATED");
                ((Player) entity).sendMessage(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED " + getName().toUpperCase());
                ph.getNamedTag().putLong("nexttick", ct + cooldown);
                ((Player) attacker).getInventory().setItemInHand(ph);
            }
        }
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
