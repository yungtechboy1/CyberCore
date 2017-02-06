package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.SimpleTransactionGroup;
import cn.nukkit.inventory.Transaction;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.MovePlayerPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.Block.SpawnerWithLevelBlock;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.*;
import net.yungtechboy1.CyberCore.Custom.Inventory.TestInv;
import net.yungtechboy1.CyberCore.Custom.Item.CItemBook;
import net.yungtechboy1.CyberCore.Custom.Item.CItemBookEnchanted;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by carlt_000 on 2/5/2017.
 */
public class CustomFactory implements Listener {

    public CyberCoreMain CCM;

    public CustomFactory(CyberCoreMain main){
        CCM = main;
        Block.list[Block.MONSTER_SPAWNER] = SpawnerWithLevelBlock.class;
        Item.list[Item.BOOK] = CItemBook.class;
        Item.list[Item.ENCHANT_BOOK] = CItemBookEnchanted.class;
        Block.init();
        Enchantment.enchantments[CustomEnchantment.BLIND] = new LifeSteal();
        Enchantment.enchantments[CustomEnchantment.CRIPPLING] = new Crippling();
        Enchantment.enchantments[CustomEnchantment.DEATHBRINGER] = new DeathBringer();
        Enchantment.enchantments[CustomEnchantment.GOOEY] = new Gooey();
        Enchantment.enchantments[CustomEnchantment.ICEASPECT] = new IceAspect();
        Enchantment.enchantments[CustomEnchantment.LIFESTEALER] = new LifeSteal();
        Enchantment.enchantments[CustomEnchantment.POISON] = new Poison();
        Enchantment.enchantments[CustomEnchantment.THUNDER] = new Thunder();
        Enchantment.enchantments[CustomEnchantment.VIPER] = new Viper();
        main.getServer().getPluginManager().registerEvents(this, main);
        main.getLogger().info(TextFormat.GREEN + " ||||||||||||||||||||||||||||| " + Enchantment.enchantments.length);
    }

    @EventHandler
    public void DamageEvent(EntityDamageByEntityEvent event) {
        long ct = new Date().getTime() / 1000;
        if (event.getDamager().namedTag.getLong("Crippling") > ct) {
            event.setDamage(event.getDamage(EntityDamageEvent.MODIFIER_BASE) / 2, EntityDamageEvent.MODIFIER_BASE);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void TE(InventoryTransactionEvent event){
        SimpleTransactionGroup transaction = (SimpleTransactionGroup) event.getTransaction();
        Set<Transaction> traa = new HashSet<>(transaction.getTransactions());

        for(Transaction t: traa){
            Inventory inv = t.getInventory();
            if(inv.getName().equals("TEST INV")){
                CCM.getLogger().debug("TI > "+t.getSlot());
                if(t.getSlot() == 3 || t.getSlot() == 1) {
                    if(t.getSourceItem().getId() == Item.SLIME_BLOCK){
                        CCM.getLogger().info("11111!!");
                        if(inv instanceof TestInv)((TestInv) inv).Take(transaction.getSource());
                    }
                    CCM.getLogger().debug("CANCELED!!");
                    event.setCancelled();
                    break;
                }else if(t.getSlot() == 2){
                    CCM.getLogger().debug("CANCELED!!");
                    event.setCancelled();
                    break;
                }
            }
        }
    }

    @EventHandler
    public void PIE(PlayerInteractEvent event) {
        Player a = event.getPlayer();
        if (event.getBlock().getId() == Block.ANVIL) {
            Block BA = a.getLevel().getBlock(event.getBlock().add(0,-2));
            Inventory b = new TestInv(a, event.getBlock(),BA);
            //b = new TestInv2(a);
            //b = new PlayerEnderChestInventory(a);
            a.addWindow(b);
            CCM.getLogger().debug("3");
            event.setCancelled();
            //getLogger().info("CALLED!!!"+(a).addWindow(new TestInv(a)));
            //getLogger().info("CALLED22222!!!"+(a).addWindow(new PlayerEnderChestInventory(a)));
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
