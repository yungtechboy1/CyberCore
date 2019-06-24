package net.yungtechboy1.CyberCore.entities.animal.walking;

import cn.nukkit.Player;
import cn.nukkit.block.BlockWool;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import net.yungtechboy1.CyberCore.Custom.Item.Class.Sheepsblood;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemPurpleGlazedTerraCotta;
import net.yungtechboy1.CyberCore.Custom.Item.MobPluginItems;
import net.yungtechboy1.CyberCore.entities.animal.WalkingAnimal;
import net.yungtechboy1.CyberCore.entities.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Sheep extends WalkingAnimal {

    public static final int NETWORK_ID = 13;

    public Sheep(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
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
        return 1.3f;
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(8);
    }

    @Override
    public boolean targetOption(EntityCreature creature, double distance) {
        if (creature instanceof Player) {
            Player player = (Player) creature;
            return player.spawned && player.isAlive() && !player.closed && player.getInventory().getItemInHand().getId() == Item.SEEDS && distance <= 49;
        }
        return false;
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();
        if (this.lastDamageCause instanceof EntityDamageByEntityEvent) {
            drops.add(Item.get(Item.WOOL, 0, 1)); // each time drops 1 wool
            int muttonDrop = Utils.rand(1, 3); // drops 1-2 muttons / cooked muttons
            int Wool = Utils.rand(0, 100); // drops 1-2 muttons / cooked muttons
            int Woolcount = Utils.rand(0, 3); // drops 1-2 muttons / cooked muttons
            for (int i = 0; i < muttonDrop; i++) {
                drops.add(Item.get(this.isOnFire() ? MobPluginItems.COOKED_MUTTON : MobPluginItems.RAW_MUTTON, 0, 1));
            }
            if (Woolcount > 0) {
                if (40 >= Wool) {
                    //Regular wool
                    drops.add(new ItemBlock(new BlockWool()));
                } else if (60 >= Wool) {
                    //Full Fail
                    drops.add(new Sheepsblood());
                } else if (100 >= Wool) {
                    drops.add(new CustomItemPurpleGlazedTerraCotta());
                }
            }
        }
        return drops.toArray(new Item[drops.size()]);
    }

    @Override
    public int getKillExperience() {
        return Utils.rand(1, 4); // gain 1-3 experience
    }

}