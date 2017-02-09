package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHumanType;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class Gooey extends CustomEnchantment {


    private int cooldown;

    public Gooey() {
        super(GOOEY, "Gooey", 2, EnchantmentType.SWORD);
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

    public void FlyUp(Entity entity) {
        entity.addMotion(0,5,0);
    }

    @Override
    public void doPostAttack(Entity attacker, Entity entity) {
        if (!(entity instanceof Player)) return;
        if (!(attacker instanceof Player)) return;

        EntityHumanType human = (EntityHumanType) entity;

        long ct = new Date().getTime();

        Item ph = ((Player) attacker).getInventory().getItemInHand();
        int nexttick = ph.getNamedTag().getInt("nexttick");
        int lasthit = ph.getNamedTag().getInt("lasthit");
        int hitcounter = ph.getNamedTag().getInt("lasthit");

        if (ct >= nexttick) {
            if (ct < lasthit + 800) {//Attacked withing .8 Secs
                hitcounter++;
                if (hitcounter > 6 - getLevel()) {
                    hitcounter = 0;
                    int rand = new NukkitRandom(GOOEY*GOOEY).nextRange(0, 100);
                    if (rand >= 100 - (18 * getLevel())) {
                        FlyUp(entity);
                        SendParticles(entity);

                        ((Player) attacker).sendMessage(TextFormat.GREEN +getName().toUpperCase()+" ACTIVATED");
                        ((Player) entity).sendMessage(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED "+getName().toUpperCase());
                        ph.getNamedTag().putLong("nexttick", ct+cooldown);
                    }
                }
                ph.getNamedTag().putLong("hitcounter", hitcounter);
            }
            ph.getNamedTag().putLong("lasthit", lasthit);
            ((Player) attacker).getInventory().setItemInHand(ph);
        }
    }

    public void SendParticles(Entity entity) {
        entity.getLevel().addParticle(new GenericParticle(entity, 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, 0, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, 0, 0), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, 0, 1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, 0, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, 0, 1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(1, 0, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(1, 0, 0), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(1, 0, 1), 22, 2));

        //1 Up
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, 1, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, 1, 0), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, 1, 1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, 1, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, 1, 0), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, 1, 1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(1, 1, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(1, 1, 0), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, 1, 1), 22, 2));

        //1 Down
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, -1, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, -1, 0), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(-1, -1, 1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, -1, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, -1, 0), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, -1, 1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(1, -1, -1), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(1, -1, 0), 22, 2));
        entity.getLevel().addParticle(new GenericParticle(entity.add(0, -1, 1), 22, 2));
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
