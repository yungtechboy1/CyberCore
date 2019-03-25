package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.entity.Entity;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;

public class KingOfHearts extends CustomEnchantment {
    public KingOfHearts() {
        super(KINGOFHEARTS, "King of Hearts", 2, EnchantmentType.ARMOR_TORSO);
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
        return 10;
    }

    public double GetLevelEffect() {
        return GetLevelEffect(getLevel());
    }
    public double GetLevelEffect(int lvl) {
        switch (lvl) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 1;
        }
    }

}