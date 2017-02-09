package net.yungtechboy1.CyberCore.entities.monster.walking;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemSwordGold;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import net.yungtechboy1.CyberCore.entities.monster.WalkingMonster;
import net.yungtechboy1.CyberCore.entities.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PigZombie extends WalkingMonster {

    public static final int NETWORK_ID = 36;

    int                     angry      = 0;

    public PigZombie(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.72f;
    }

    @Override
    public float getHeight() {
        return 1.8f;
    }

    @Override
    public float getEyeHeight() {
        return 1.62f;
    }

    @Override
    public double getSpeed() {
        return 1.15;
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        if (this.namedTag.contains("Angry")) {
            this.angry = this.namedTag.getInt("Angry");
        }

        this.fireProof = true;
        this.setDamage(new int[] { 0, 5, 9, 13 });
        setMaxHealth(20);
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putInt("Angry", this.angry);
    }

    @Override
    public boolean targetOption(EntityCreature creature, double distance) {
        if (distance <= 100 && this.isAngry() && creature instanceof net.yungtechboy1.CyberCore.entities.monster.walking.PigZombie && !((net.yungtechboy1.CyberCore.entities.monster.walking.PigZombie) creature).isAngry()) {
            ((net.yungtechboy1.CyberCore.entities.monster.walking.PigZombie) creature).setAngry(1000);
        }
        return this.isAngry() && super.targetOption(creature, distance);
    }

    @Override
    public void attackEntity(Entity player) {
        if (this.attackDelay > 10 && this.distanceSquared(player) < 1.44) {
            this.attackDelay = 0;
            HashMap<Integer, Float> damage = new HashMap<>();
            damage.put(EntityDamageEvent.MODIFIER_BASE, (float) this.getDamage());
            player.attack(new EntityDamageByEntityEvent(this, player, EntityDamageEvent.CAUSE_ENTITY_ATTACK, damage));
        }
    }

    public boolean isAngry() {
        return this.angry > 0;
    }

    public void setAngry(int val) {
        this.angry = val;
    }

    @Override
    public void attack(EntityDamageEvent ev) {
        super.attack(ev);

        if (!ev.isCancelled()) {
            this.setAngry(1000);
        }
    }

    @Override
    public void spawnTo(Player player) {
        super.spawnTo(player);

        MobEquipmentPacket pk = new MobEquipmentPacket();
        pk.eid = this.getId();
        pk.item = new ItemSwordGold();
        pk.slot = 10;
        pk.selectedSlot = 10;
        player.dataPacket(pk);
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();
        if (this.lastDamageCause instanceof EntityDamageByEntityEvent) {
            int rottenFlesh = Utils.rand(0, 2); // drops 0-1 rotten flesh
            int goldNuggets = Utils.rand(0, 101) <= 3 ? 1 : 0; // with a 2,5% chance a gold nugget is dropped
            int goldSword = Utils.rand(0, 101) <= 9 ? 1 : 0; // with a 8,5% chance it's gold sword is dropped
            for (int i=0; i < rottenFlesh; i++) {
                drops.add(Item.get(Item.ROTTEN_FLESH, 0, 1));
            }
            for (int i=0; i < goldNuggets; i++) {
                drops.add(Item.get(Item.GOLD_NUGGET, 0, 1));
            }
            for (int i=0; i < goldSword; i++) {
                drops.add(Item.get(Item.GOLD_SWORD, 0, 1));
            }
        }
        return drops.toArray(new Item[drops.size()]);
    }
    
    @Override
    public int getKillExperience () {
        return 5; // gain 5 experience
    }

}
