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
import cn.nukkit.level.particle.InkParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class LifeSteal extends CustomEnchantment {

    public LifeSteal() {
        super(LIFESTEALER, "Life Steal", 2, EnchantmentType.SWORD);
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
        if (!(entity instanceof Player) && !(attacker instanceof Player)) return;

        Item ph = ((Player) attacker).getInventory().getItemInHand();


        if (CheckCooldown(ph)) {
            int rand = new NukkitRandom(BLIND * BLIND).nextRange(0, 25 - getLevel());
            //Server.getInstance().getLogger().info("POST ATTACK!!!" + rand + " <= " + 15*getLevel());
            if (rand <= getLevel()) {


                ((Player) attacker).sendActionBar(TextFormat.GREEN + getName().toUpperCase() + " ACTIVATED and took "+GetHeartsStolen()+" Hearts from enemy!");
                ((Player) entity).sendActionBar(TextFormat.RED + attacker.getName().toUpperCase() + " has activated " + TextFormat.YELLOW + getName().toUpperCase()+" and stole "+GetHeartsStolen()+" Hearts!");

                attacker.heal(new EntityRegainHealthEvent(attacker, GetHeartsStolen() * getLevel(), EntityRegainHealthEvent.CAUSE_MAGIC));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity), 2));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity.add(-1, -2, -1)), 2));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity.add(-1, -2, 0)), 2));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity.add(-1, -2, 1)), 2));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity.add(0, -2, -1)), 2));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity.add(0, -2, 1)), 2));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity.add(1, -2, -1)), 2));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity.add(1, -2, 0)), 2));
                entity.getLevel().addParticle(new HeartParticle(entity.getLevel().getSafeSpawn(entity.add(1, -2, 1)), 2));

                SetCooldown(GetCooldown(), ph);
            }else{
                SetCooldown(2,ph);
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

    public float GetHeartsStolen(){
        switch (getLevel()){
            case 1:
                return .5f;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 4;
            case 5:
                return 5;
        }
        return .5f;
    }
}
