package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.entity.Entity;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;

/**
 * Created by carlt on 3/25/2019.
 */
public class Restoration extends CustomEnchantment {
    public Restoration() {
        super(RESTORATION, "Restoration", 2, EnchantmentType.ARMOR_TORSO);
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
    public boolean isCompatibleWith(Enchantment enchantment) {
        return super.isCompatibleWith(enchantment);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public float GetLevelEffect() {
        return GetLevelEffect(getLevel());
    }
    public float GetLevelEffect(int lvl) {
        switch (lvl) {
            case 1:
                return 1.5f;
            case 2:
                return 2.5f;
            case 3:
                return 3.5f;
            default:
                return 1;
        }
    }

}