package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHumanType;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.level.particle.HeartParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class LifeSteal extends CustomEnchantment {


    private int cooldown;

    public LifeSteal() {
        super(LIFESTEALER, "Life Steal", 2, EnchantmentType.SWORD);
        cooldown = 5 - getLevel();
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
        if (!(entity instanceof EntityHumanType) && !(attacker instanceof Player)) return;

        EntityHumanType human = (EntityHumanType) entity;

        long ct = new Date().getTime() / 1000;

        Item ph = ((Player) attacker).getInventory().getItemInHand();
        int nextregintick = ph.getNamedTag().getInt("nextlifestealtick");



        if (ct >= nextregintick) {
            int rand = new NukkitRandom().nextRange(0,100);
            //Server.getInstance().getLogger().info("POST ATTACK!!!" + rand + " <= " + 15*getLevel());
            if(rand <= 15*getLevel()){
            human.setHealth(human.getHealth() - (.5f * getLevel()));

            ((Player) attacker).sendMessage(TextFormat.GREEN + "LIFE STEALER ACTIVATED");
            if (entity instanceof Player)
                ((Player) entity).sendMessage(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED LIFE STEALER");

            attacker.heal(new EntityRegainHealthEvent(attacker, .5f * getLevel(), EntityRegainHealthEvent.CAUSE_MAGIC));
            attacker.getLevel().addParticle(new HeartParticle(attacker, 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, 0, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, 0, 0), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, 0, 1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, 0, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, 0, 1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(1, 0, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(1, 0, 0), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(1, 0, 1), 2));

            //1 Up
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, 1, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, 1, 0), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, 1, 1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, 1, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, 1, 0), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, 1, 1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(1, 1, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(1, 1, 0), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, 1, 1), 2));

            //1 Down
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, -1, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, -1, 0), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(-1, -1, 1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, -1, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, -1, 0), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, -1, 1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(1, -1, -1), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(1, -1, 0), 2));
            attacker.getLevel().addParticle(new HeartParticle(attacker.add(0, -1, 1), 2));

        }

            ph.getNamedTag().putLong("nextlifestealtick",ct + cooldown);

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
