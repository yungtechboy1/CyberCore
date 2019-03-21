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
 * Created by carlt on 3/21/2019.
 */
public class HasteCE extends CustomEnchantment {
    private int cooldown;

    public HasteCE() {
        super(HASTE, "Haste", 2, EnchantmentType.DIGGER);
        ER = EnchantRarity.R50;
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

        EntityHumanType human = (EntityHumanType) entity;

        long ct = new Date().getTime() / 1000;

        Item ph = ((Player) attacker).getInventory().getItemInHand();
        int nextregintick = ph.getNamedTag().getInt("nextblindtick");



        if (ct >= nextregintick) {
            int rand = new NukkitRandom(BLIND*BLIND).nextRange(0,100);
            //Server.getInstance().getLogger().info("POST ATTACK!!!" + rand + " <= " + 15*getLevel());
            if(rand <= 15*getLevel()){
                Effect e = Effect.getEffect(15);
                e.setAmplifier(getLevel());
                e.setDuration(15*getLevel());
                entity.addEffect(e);

                ((Player) attacker).sendMessage(TextFormat.GREEN +getName().toUpperCase()+" ACTIVATED");
                ((Player) entity).sendMessage(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED "+getName().toUpperCase());

                attacker.heal(new EntityRegainHealthEvent(attacker, .5f * getLevel(), EntityRegainHealthEvent.CAUSE_MAGIC));
                entity.getLevel().addParticle(new InkParticle(entity, 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, 0, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, 0, 0), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, 0, 1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, 0, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, 0, 1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(1, 0, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(1, 0, 0), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(1, 0, 1), 2));

                //1 Up
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, 1, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, 1, 0), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, 1, 1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, 1, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, 1, 0), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, 1, 1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(1, 1, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(1, 1, 0), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, 1, 1), 2));

                //1 Down
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, -1, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, -1, 0), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(-1, -1, 1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, -1, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, -1, 0), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, -1, 1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(1, -1, -1), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(1, -1, 0), 2));
                entity.getLevel().addParticle(new InkParticle(entity.add(0, -1, 1), 2));

            }

            ph.getNamedTag().putLong("nextblindtick",ct + cooldown);

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
