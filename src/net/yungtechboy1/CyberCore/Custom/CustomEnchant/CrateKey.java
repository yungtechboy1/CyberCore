package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;

/**
 * Created by carlt_000 on 2/6/2017.
 */
public class CrateKey extends CustomEnchantment {

    private int cooldown;

    public CrateKey() {
        super(CrateKey, "CrateKey", 2, EnchantmentType.ALL);
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
    public boolean isCompatibleWith(Enchantment enchantment) {
        return super.isCompatibleWith(enchantment);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
