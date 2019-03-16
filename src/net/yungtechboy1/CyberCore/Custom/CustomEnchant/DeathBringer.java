package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.entity.Entity;
import cn.nukkit.item.enchantment.EnchantmentType;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class DeathBringer extends CustomEnchantment {

    private int cooldown;

    public DeathBringer() {
        super(DEATHBRINGER, "Death Bringer", 2, EnchantmentType.SWORD);
        cooldown = 10 - getLevel();
    }

    @Override
    public int getMaxEnchantableLevel() {
        return super.getMaxEnchantableLevel();
    }

    @Override
    public double getDamageBonus(Entity entity) {
        return getLevel();
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
    public int getMaxLevel() {
        return 5;
    }
}
