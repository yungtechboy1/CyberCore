package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;

/**
 * Created by carlt on 3/21/2019.
 */
public class HasteCE extends CustomEnchantment {
    private int cooldown;

    public HasteCE() {
        this(1);
    }

    public HasteCE(int lvl) {
        super(HASTE, "Haste", 2, EnchantmentType.DIGGER);
        ER = EnchantRarity.R30;
        cooldown = (10 - getLevel())*12;
        level = lvl;
    }

    @Override
    public int getMaxEnchantableLevel() {
        return 4;
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
        return 6;
    }

    public boolean TestChances(int rand) {
       int ii = GetChances();
       return rand < ii;
    }
    public int GetChances() {
        switch (level) {
            case 1:
                return 25;
            case 2:
                return 35;
            case 3:
                return 45;
            case 4:
                return 55;
            case 5:
                return 65;
            case 6:
                return 75;
        }
        return 25;
    }
}
