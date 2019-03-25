package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.entity.Entity;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;

/**
 * Created by carlt on 3/24/2019.
 */
public class Gills extends CustomEnchantment {
    public Gills() {
        super(GILLS, "Gills", 2, EnchantmentType.ARMOR_HEAD);
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
