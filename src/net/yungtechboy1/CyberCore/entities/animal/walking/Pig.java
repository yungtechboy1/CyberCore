package net.yungtechboy1.CyberCore.entities.animal.walking;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.EntityRideable;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import net.yungtechboy1.CyberCore.entities.animal.WalkingAnimal;
import net.yungtechboy1.CyberCore.entities.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Pig extends WalkingAnimal implements EntityRideable {

    public static final int NETWORK_ID = 12;

    public Pig(FullChunk chunk, CompoundTag nbt) {

        super(chunk, nbt);
        System.out.println("CRRRR");
}

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        return 0.9f;
    }

    @Override
    public float getEyeHeight() {
        return 0.9f;
    }

    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(10);
    }

    public boolean targetOption(EntityCreature creature, double distance) {
        if (creature instanceof Player) {
            Player player = (Player) creature;
            return player.spawned && player.isAlive() && !player.closed && player.getInventory().getItemInHand().getId() == Item.CARROT && distance <= 49;
        }
        return false;
    }

    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();
        if (this.lastDamageCause instanceof EntityDamageByEntityEvent) {
            int drop = Utils.rand(1, 4); // drops 1-3 raw porkchop / cooked porkchop when on fire
            for (int i = 0; i < drop; i++) {
                drops.add(Item.get(this.isOnFire() ? Item.COOKED_PORKCHOP : Item.RAW_PORKCHOP, 0, 1));
            }
        }
        return drops.toArray(new Item[drops.size()]);
    }
    
    public int getKillExperience () {
        return Utils.rand(1, 4); // gain 1-3 experience
    }

    @Override
    public boolean mountEntity(Entity entity) {
        return false;
    }
}