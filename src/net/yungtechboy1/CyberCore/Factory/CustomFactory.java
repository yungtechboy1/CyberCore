package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.MovePlayerPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.*;
import net.yungtechboy1.CyberCore.Custom.Inventory.TestInv;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.entities.EntityStackable;

import java.util.Date;

/**
 * Created by carlt_000 on 2/5/2017.
 */
public class CustomFactory implements Listener {

    public CyberCoreMain CCM;
    public ConfigSection VEIList = new ConfigSection();
    protected static Enchantment[] enchantments = new Enchantment[256];

    public CustomFactory(CyberCoreMain main) {
        CCM = main;
//        Block.list[Block.MONSTER_SPAWNER] = SpawnerWithLevelBlock.class;
//        Item.list[Item.BOOK] = CItemBook.class;
//        Item.list[Item.ENCHANT_BOOK] = CItemBookEnchanted.class;
//        Block.init();
        enchantments[CustomEnchantment.BLIND] = new LifeSteal();
//        enchantments[CustomEnchantment.CRIPPLING] = new Crippling();
        enchantments[CustomEnchantment.DEATHBRINGER] = new DeathBringer();
//        enchantments[CustomEnchantment.GOOEY] = new Gooey();
//        enchantments[CustomEnchantment.ICEASPECT] = new IceAspect();
        enchantments[CustomEnchantment.LIFESTEALER] = new LifeSteal();
        enchantments[CustomEnchantment.POISON] = new Poison();
        enchantments[CustomEnchantment.THUNDER] = new Thunder();
        enchantments[CustomEnchantment.VIPER] = new Viper();
        main.getServer().getPluginManager().registerEvents(this, main);
        main.getLogger().info(TextFormat.GREEN + " ||||||||||||||||||||||||||||| " + enchantments.length);
    }

    /**
     * @param type
     * @param source
     * @param args
     * @return
     */
    public static Entity SpawnEntityStack(int type, Position source, Object... args) {
        FullChunk chunk = source.getLevel().getChunk((int) source.x >> 4, (int) source.z >> 4, true);
        if (!chunk.isGenerated()) {
            chunk.setGenerated();
        }
        if (!chunk.isPopulated()) {
            chunk.setPopulated();
        }

        Entity[] el = source.getLevel().getNearbyEntities(source.getLevelBlock().getBoundingBox().grow(16, 16, 16));
//        Server.getInstance().getLogger().info("FOUND "+el.length);
        Entity stackEntity = null;
        for (Entity e : el) {
            if(e instanceof EntityStackable) {
                System.out.println("CORRECT WAY");
                ((EntityStackable) e).AddStackCount(1);
                stackEntity = e;
                break;
            }
        }

        if (stackEntity == null) {
            Vector3 temp = new Vector3();
            temp.x = new NukkitRandom().nextRange(-7,7)+source.x;
            temp.y = new NukkitRandom().nextRange(-4,4)+source.y;
            temp.z = new NukkitRandom().nextRange(-7,7)+source.z;
            Vector3 sp = source.getLevel().getSafeSpawn(temp);
            CompoundTag nbt = new CompoundTag()
                    .putList(new ListTag<DoubleTag>("Pos")
                            .add(new DoubleTag("", sp.x))
                            .add(new DoubleTag("", sp.y))
                            .add(new DoubleTag("", sp.z)))
                    .putList(new ListTag<DoubleTag>("Motion")
                            .add(new DoubleTag("", 0))
                            .add(new DoubleTag("", 0))
                            .add(new DoubleTag("", 0)))
                    .putList(new ListTag<FloatTag>("Rotation")
                            .add(new FloatTag("", source instanceof Location ? (float) ((Location) source).yaw : 0))
                            .add(new FloatTag("", source instanceof Location ? (float) ((Location) source).pitch : 0))
                    )
                    .putBoolean("IsStack", true)
                    .putInt("StackCount", 1)
                    .putInt("MaxStack", 64)

            .putString("CustomName", "Entity Stack Count 1")
            .putBoolean("CustomNameVisible", true);;

            return Entity.createEntity(type+"", chunk, nbt, args);
        } else {
            stackEntity.onUpdate(Server.getInstance().getTick());
            return stackEntity;
        }
    }

    @EventHandler
    public void DamageEvent(EntityDamageByEntityEvent event) {

    }

    @EventHandler
    public void PIE(PlayerInteractEvent event) {
        Player a = event.getPlayer();
        if (event.getBlock().getId() == Block.ANVIL) {
            Block BA = a.getLevel().getBlock(event.getBlock().add(0, -2));
            Inventory b = new TestInv(a, event.getBlock(), BA);
            a.addWindow(b);
            event.setCancelled();
        }
    }

    //@EventHandler
    public void PlayerAttackEvent(DataPacketReceiveEvent pkt) {
        Player player = pkt.getPlayer();
        if (pkt.getPacket().pid() == ProtocolInfo.INTERACT_PACKET) {
           /* if (!player.spawned || !player.isAlive() || player.blocked) {
                pkt.setCancelled();
                return;
            }
            player.craftingType = Player.CRAFTING_SMALL;

            InteractPacket interactPacket = (InteractPacket) pkt.getPacket();

            Entity targetEntity = player.level.getEntity(interactPacket.target);

            if (targetEntity == null || !player.isAlive() || !targetEntity.isAlive()) {
                pkt.setCancelled();
                return;
            }

            if (targetEntity instanceof EntityItem || targetEntity instanceof EntityArrow || targetEntity instanceof EntityXPOrb) {
                player.kick(PlayerKickEvent.Reason.INVALID_PVE, "Attempting to attack an invalid entity");
                player.getServer().getLogger().warning(player.getServer().getLanguage().translateString("nukkit.player.invalidEntity", player.getName()));
                pkt.setCancelled();
                return;
            }

            Item item = player.getInventory().getItemInHand();

            if (interactPacket.action == InteractPacket.ACTION_LEFT_CLICK) {
                if (player.getGamemode() == Player.VIEW) {
                    pkt.setCancelled();
                    return;
                }

                float itemDamage = item.getAttackDamage();

                for (Enchantment enchantment : item.getEnchantments()) {
                    itemDamage += enchantment.getDamageBonus(targetEntity);
                }

                HashMap<Integer, Float> damage = new HashMap<>();
                damage.put(EntityDamageEvent.MODIFIER_BASE, itemDamage);

                //@TODO maybe later add a reach enchant
                if (!player.canInteract(targetEntity, player.isCreative() ? 8 : 5)) {
                    pkt.setCancelled();
                    return;
                } else if (targetEntity instanceof Player) {
                    if ((((Player) targetEntity).getGamemode() & 0x01) > 0) {
                        pkt.setCancelled();
                        return;
                    }
                }

                EntityDamageByEntityEvent entityDamageByEntityEvent = new EntityDamageByEntityEvent(player, targetEntity, EntityDamageEvent.CAUSE_ENTITY_ATTACK, damage);

                targetEntity.attack(entityDamageByEntityEvent);

                if (entityDamageByEntityEvent.isCancelled()) {
                    if (item.isTool() && player.isSurvival()) {
                        player.getInventory().sendContents(player);
                    }
                    pkt.setCancelled();
                    return;
                }

                for (Enchantment enchantment : item.getEnchantments()) {
                    enchantment.doPostAttack(player, targetEntity);
                }

                if (item.isTool() && player.isSurvival()) {
                    if (item.useOn(targetEntity) && item.getDamage() >= item.getMaxDurability()) {
                        player.getInventory().setItemInHand(new ItemBlock(new BlockAir()));
                    } else {
                        player.getInventory().setItemInHand(item);
                    }
                }
                pkt.setCancelled();
            }*/
        } else if (pkt.getPacket().pid() == ProtocolInfo.MOVE_PLAYER_PACKET) {
            Long ct = new Date().getTime() / 1000;
            Long frozen = player.namedTag.getLong("Frozen");
            if (frozen > ct) {
                MovePlayerPacket movePlayerPacket = (MovePlayerPacket) pkt.getPacket();
                Vector3 newPos = new Vector3(movePlayerPacket.x, movePlayerPacket.y - player.getEyeHeight(), movePlayerPacket.z);
                if (newPos.distanceSquared(player) < 0.01) {
                    player.sendMessage(TextFormat.GRAY + "FROZEN");
                    pkt.setCancelled();
                }
            }
        }
    }

}
