package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.weather.LightningStrikeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class Thunder extends CustomEnchantment {

    private int cooldown;

    public Thunder() {
        super(THUNDER, "Thunder", 2, EnchantmentType.SWORD);
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

        long ct = new Date().getTime() / 1000;

        Item ph = ((Player) attacker).getInventory().getItemInHand();
        int nexttick = ph.getNamedTag().getInt("Tnexttick");

        if (ct >= nexttick) {
            int rand = new NukkitRandom(THUNDER*THUNDER).nextRange(0, 100);
            if (rand >= 100 - (18 * getLevel())) {

                CompoundTag nbt = new CompoundTag()
                        .putList(new ListTag<DoubleTag>("Pos").add(new DoubleTag("",attacker.getFloorX()))
                                .add(new DoubleTag("", attacker.getFloorY())).add(new DoubleTag("", attacker.getFloorZ())))
                        .putList(new ListTag<DoubleTag>("Motion").add(new DoubleTag("", 0))
                                .add(new DoubleTag("", 0)).add(new DoubleTag("", 0)))
                        .putList(new ListTag<FloatTag>("Rotation").add(new FloatTag("", 0))
                                .add(new FloatTag("", 0)));
                BaseFullChunk chunk = attacker.getLevel().getChunk(attacker.getFloorX() >> 4, attacker.getFloorZ() >> 4);
                EntityLightning bolt = new EntityLightning(chunk,nbt);
                LightningStrikeEvent ev = new LightningStrikeEvent(attacker.getLevel(), bolt);
                if(!ev.isCancelled()){
                    bolt.spawnToAll();
                }

                ((Player) attacker).sendMessage(TextFormat.GREEN + getName().toUpperCase() + " ACTIVATED");
                ((Player) entity).sendMessage(TextFormat.RED + attacker.getName().toUpperCase() + " ACTIVATED " + getName().toUpperCase());

                ph.getNamedTag().putLong("Tnexttick", ct + cooldown);
                ((Player) attacker).getInventory().setItemInHand(ph);
            }
        }
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
