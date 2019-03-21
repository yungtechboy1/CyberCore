package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.MovePlayerPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.Block.SpawnerWithLevelBlock;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.*;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.*;
import net.yungtechboy1.CyberCore.Custom.Inventory.TestInv;
import net.yungtechboy1.CyberCore.Custom.Item.CItemBook;
import net.yungtechboy1.CyberCore.Custom.Item.CItemBookEnchanted;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.Date;

/**
 * Created by carlt_000 on 2/5/2017.
 */
public class CustomFactory implements Listener {

    public CyberCoreMain CCM;
    public ConfigSection VEIList = new ConfigSection();
    protected static Enchantment[] enchantments = new Enchantment[256];

    public CustomFactory(CyberCoreMain main){
        CCM = main;
        Block.list[Block.MONSTER_SPAWNER] = SpawnerWithLevelBlock.class;
        Item.list[Item.BOOK] = CItemBook.class;
        Item.list[Item.ENCHANT_BOOK] = CItemBookEnchanted.class;
        Block.init();
        enchantments[CustomEnchantment.BLIND] = new LifeSteal();
        enchantments[CustomEnchantment.CRIPPLING] = new Crippling();
        enchantments[CustomEnchantment.DEATHBRINGER] = new DeathBringer();
        enchantments[CustomEnchantment.GOOEY] = new Gooey();
        enchantments[CustomEnchantment.ICEASPECT] = new IceAspect();
        enchantments[CustomEnchantment.LIFESTEALER] = new LifeSteal();
        enchantments[CustomEnchantment.POISON] = new Poison();
        enchantments[CustomEnchantment.THUNDER] = new Thunder();
        enchantments[CustomEnchantment.VIPER] = new Viper();
        main.getServer().getPluginManager().registerEvents(this, main);
        main.getLogger().info(TextFormat.GREEN + " ||||||||||||||||||||||||||||| " + enchantments.length);
    }

    @EventHandler
    public void DamageEvent(EntityDamageByEntityEvent event) {

    }

    @EventHandler
    public void PIE(PlayerInteractEvent event) {
        Player a = event.getPlayer();
        if (event.getBlock().getId() == Block.ANVIL) {
            Block BA = a.getLevel().getBlock(event.getBlock().add(0,-2));
            Inventory b = new TestInv(a, event.getBlock(),BA);
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
