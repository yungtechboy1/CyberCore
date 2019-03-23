package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHumanType;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.level.particle.InkParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class Blind extends CustomEnchantment {


    public Blind() {
        super(BLIND, "Blind", 2, EnchantmentType.SWORD);
        ER = EnchantRarity.R30;
        SetCooldown(60 - getLevel());
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
            int rand = new NukkitRandom(BLIND*BLIND).nextRange(0,100);
            //Server.getInstance().getLogger().info("POST ATTACK!!!" + rand + " <= " + 15*getLevel());
            if(rand <= 15*getLevel()){
                Effect e = Effect.getEffect(15);
                e.setAmplifier(getLevel());
                e.setDuration(15*getLevel());
                entity.addEffect(e);

                ((Player) attacker).sendActionBar(TextFormat.GREEN +getName().toUpperCase()+" ACTIVATED");
                ((Player) entity).sendActionBar(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED "+getName().toUpperCase());

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

            }
            SetCooldown(GetCooldown(),ph);

            Server.getInstance().getLogger().info("NEW TICK " + nextregintick + " ||| " + (ct + cooldown));
        }
        ((Player) attacker).getInventory().setItemInHand(ph);

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
