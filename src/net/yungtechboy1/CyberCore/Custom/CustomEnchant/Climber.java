package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;

public class Climber extends CustomEnchantment {
    public Climber() {
        super(CLIMBER, "Climber", 2, EnchantmentType.ARMOR_FEET);
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
        return 3;
    }

    public double GetLevelEffect() {
        return GetLevelEffect(getLevel());
    }
    public double GetLevelEffect(int lvl) {
        switch (lvl) {
            case 1:
                return 1.5 * Player.MAXIMUM_SPEED;
            case 2:
                return 2.5 * Player.MAXIMUM_SPEED;
            case 3:
                return 3.5 * Player.MAXIMUM_SPEED;
            default:
                return 1.5 * Player.MAXIMUM_SPEED;
        }
    }

}
