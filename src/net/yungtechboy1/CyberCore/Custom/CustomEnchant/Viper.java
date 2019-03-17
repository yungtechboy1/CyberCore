package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class Viper extends CustomEnchantment {

    private int cooldown;

    public Viper() {
        super(VIPER, "Viper", 2, EnchantmentType.SWORD);
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
        int nexttick = ph.getNamedTag().getInt("Vnexttick");

        if (ct >= nexttick) {
            int rand = new NukkitRandom(VIPER*VIPER).nextRange(0, 100);
            if (rand >= 100 - (18 * getLevel())) {
                for(Item a: ((Player) entity).getInventory().getArmorContents()){
                    a.setDamage(a.getDamage()+1+getLevel());
                }

                ((Player) attacker).sendMessage(TextFormat.GREEN + getName().toUpperCase() + " ACTIVATED");
                ((Player) entity).sendMessage(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED " + getName().toUpperCase());
                SendParticles(entity);

                ph.getNamedTag().putLong("Vnexttick", ct + cooldown);
                ((Player) attacker).getInventory().setItemInHand(ph);
            }
        }
    }

    public void SendParticles(Entity entity) {
        entity.getLevel().addParticle(new DestroyBlockParticle(entity, Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, 0, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, 0, 0), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, 0, 1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, 0, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, 0, 1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(1, 0, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(1, 0, 0), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(1, 0, 1), Block.get(Block.ICE)));

        //1 Up
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, 1, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, 1, 0), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, 1, 1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, 1, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, 1, 0), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, 1, 1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(1, 1, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(1, 1, 0), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, 1, 1), Block.get(Block.ICE)));

        //1 Down
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, -1, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, -1, 0), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(-1, -1, 1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, -1, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, -1, 0), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, -1, 1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(1, -1, -1), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(1, -1, 0), Block.get(Block.ICE)));
        entity.getLevel().addParticle(new DestroyBlockParticle(entity.add(0, -1, 1), Block.get(Block.ICE)));
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
