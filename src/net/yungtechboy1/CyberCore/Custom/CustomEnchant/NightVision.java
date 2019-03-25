package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.level.particle.HeartParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Potion;
import cn.nukkit.utils.TextFormat;

/**
 * Created by carlt on 3/24/2019.
 */
public class NightVision extends CustomEnchantment {
    public NightVision() {
        super(NIGHTVISION, "Night Vision", 2, EnchantmentType.ARMOR_HEAD);
        SetCooldown(0);
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
        return;
    }

    @Override
    public boolean isCompatibleWith(Enchantment enchantment) {
        return super.isCompatibleWith(enchantment);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

}
