package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHumanType;
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
public class IceAspect extends CustomEnchantment {


    private int cooldown;

    public IceAspect() {
        super(ICEASPECT, "Ice Aspect", 2, EnchantmentType.SWORD);
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

        long ct = new Date().getTime()/1000;

        Item ph = ((Player) attacker).getInventory().getItemInHand();
        int nexttick = ph.getNamedTag().getInt("IAnexttick");

        if (ct >= nexttick) {
            int rand = new NukkitRandom(ICEASPECT*ICEASPECT).nextRange(0, 100);
            int rand2 = new NukkitRandom(ICEASPECT*ICEASPECT*2*ICEASPECT*22).nextRange(0, 100);
            if (rand >= 100 - (18 * getLevel())) {
                //Chance to Freeze:
                if(rand2 <= getLevel()*2){
                    entity.namedTag.putLong("Frozen", ct + (getLevel()));
                    ((Player) attacker).sendMessage(TextFormat.GREEN + "FROZEN ACTIVATED");
                    ((Player) entity).sendMessage(TextFormat.RED + "FROZEN ACTIVATED " + getName().toUpperCase());
                }else{
                    ((Player) attacker).sendMessage(TextFormat.GREEN + getName().toUpperCase() + " ACTIVATED");
                    ((Player) entity).sendMessage(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED " + getName().toUpperCase());
                }
                SendParticles(entity);
                entity.namedTag.putLong("Ice", ct + (getLevel()));
                entity.namedTag.putLong("IceLevel", getLevel());

                ph.getNamedTag().putLong("IAnexttick", ct + cooldown);
                ((Player) attacker).getInventory().setItemInHand(ph);
            }
        }
    }

    public void SendParticles(Entity entity) {
        entity.getLevel().addParticle(new DestroyBlockParticle(entity, Block.get(Block.ICE)));
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
