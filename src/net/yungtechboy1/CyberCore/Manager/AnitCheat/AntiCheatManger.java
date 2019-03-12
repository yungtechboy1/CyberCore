package net.yungtechboy1.CyberCore.Manager.AnitCheat;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.inventory.transaction.data.UseItemOnEntityData;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.GameRule;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import javafx.scene.effect.Effect;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntiyDamageEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class AntiCheatManger {
    public CyberCoreMain Main;

    public HashMap<String,Integer> AttackCooldowns = new HashMap<String, Integer>();

    public AntiCheatManger(CyberCoreMain main) {
        Main = main;
    }

    public void AddCooldown(Player p ,Integer tick){
        AttackCooldowns.put(p.getName().toLowerCase(),tick);
    }

    public boolean HasCooldown(Player p, Integer ct){
        if(!AttackCooldowns.containsKey(p.getName().toLowerCase()))return false;
        return AttackCooldowns.get(p.getName().toLowerCase()) > ct;
    }

    public DataPacket OnPacket(DataPacket packet, Player p) {

        if (packet.pid() == ProtocolInfo.INVENTORY_TRANSACTION_PACKET) {
            InventoryTransactionPacket transactionPacket = (InventoryTransactionPacket) packet;
            if (transactionPacket.transactionType == InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY) {
                UseItemOnEntityData useItemOnEntityData = (UseItemOnEntityData) transactionPacket.transactionData;
                Entity target = p.level.getEntity(useItemOnEntityData.entityRuntimeId);
                if (target == null) {
                    return packet;
<<<<<<< HEAD
                } else if (!target.isAlive()) {
                    return null;
                }
                int type = useItemOnEntityData.actionType;
                if (type == InventoryTransactionPacket.USE_ITEM_ON_ENTITY_ACTION_ATTACK) {
                    //Check Cooldown
                    if(HasCooldown(p, Server.getInstance().getTick()))return null;
                }
                int type = useItemOnEntityData.actionType;
                if (type == InventoryTransactionPacket.USE_ITEM_ON_ENTITY_ACTION_ATTACK){
                    Item item = useItemOnEntityData.itemInHand;
                    float itemDamage = item.getAttackDamage();

                    for (Enchantment enchantment : item.getEnchantments()) {
                        itemDamage += enchantment.getDamageBonus(target);
                    }

                    Map<CustomEntiyDamageEvent.CustomDamageModifier, Float> damage = new EnumMap<>(CustomEntiyDamageEvent.CustomDamageModifier.class);
                    damage.put(CustomEntiyDamageEvent.CustomDamageModifier.BASE, itemDamage);

                    if (!CustomCanInteract(target, GetPlayerMaxReach(p), p)) {
                        return null;
                    } else if (target instanceof Player) {
                        if ((((Player) target).getGamemode() & 0x01) > 0) {
                            return null;
                        }
//                        No need!
//                        else if (!this.server.getPropertyBoolean("pvp") || this.server.getDifficulty() == 0) {
//                            return null;
//                        }
                    }

                    CustomEntityDamageByEntityEvent entityDamageByEntityEvent = new CustomEntityDamageByEntityEvent(p, target, CustomEntiyDamageEvent.CustomDamageCause.ENTITY_ATTACK, damage);
                    if (p.isSpectator()) entityDamageByEntityEvent.setCancelled();
                    if ((target instanceof Player) && !p.level.getGameRules().getBoolean(GameRule.PVP)) {
                        entityDamageByEntityEvent.setCancelled();
                    }
                    if (target.getAbsorption() > 0) {  //Damage Absorption
                        float absorptionHealth = target.getAbsorption() - entityDamageByEntityEvent.getFinalDamage() > 0 ? entityDamageByEntityEvent.getFinalDamage() : target.getAbsorption();
                        target.setAbsorption(target.getAbsorption() - absorptionHealth);
                        entityDamageByEntityEvent.setDamage(-absorptionHealth, CustomEntiyDamageEvent.CustomDamageModifier.ABSORPTION);
                    }


                    if (p.isCreative() && entityDamageByEntityEvent.getCause() != CustomEntiyDamageEvent.CustomDamageCause.SUICIDE
                            && entityDamageByEntityEvent.getCause() != CustomEntiyDamageEvent.CustomDamageCause.VOID
                            ) {
                        //source.setCancelled();
                        return null;
                    } else if (p.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT) && entityDamageByEntityEvent.getCause() == CustomEntiyDamageEvent.CustomDamageCause.FALL) {
                        //source.setCancelled();
                        return null;
                    } else if (entityDamageByEntityEvent.getCause() == CustomEntiyDamageEvent.CustomDamageCause.FALL) {
                        if (p.getLevel().getBlock(p.getPosition().floor().add(0.5, -1, 0.5)).getId() == Block.SLIME_BLOCK) {
                            if (!p.isSneaking()) {
                                p.resetFallDistance();
                                return null;
                            }
                        }
                    }
                    //TODO Tanks use more food
                    p.getFoodData().updateFoodExpLevel(0.3);
                    AddCooldown(p,Server.getInstance().getTick() + 20);
                    //TODO
                    //Check
                    //CALL ALL DAMAGE EVENTS HERE!!!!
                    //TODO

                    if (entityDamageByEntityEvent.isCancelled()) return null;

                    p.setLastDamageCause(Convert(entityDamageByEntityEvent));
                    p.setHealth(p.getHealth() - entityDamageByEntityEvent.getFinalDamage());
                    if (item.isTool() && p.isSurvival()) p.getInventory().sendContents(p);

                    for (Enchantment enchantment : item.getEnchantments()) enchantment.doPostAttack(p, target);
                    //TODO
                    //CALL ALL DAMAGE EVENTS HERE!!!!
                    //TODO

                    if(entityDamageByEntityEvent.isCancelled())return null;

                    p.setLastDamageCause(Convert(entityDamageByEntityEvent));
                    p.setHealth(p.getHealth() - entityDamageByEntityEvent.getFinalDamage());
                    if (item.isTool() && p.isSurvival())p.getInventory().sendContents(p);

                    for (Enchantment enchantment : item.getEnchantments())enchantment.doPostAttack(p, target);


                    if (item.isTool() && p.isSurvival()) {
                        if (item.useOn(target) && item.getDamage() >= item.getMaxDurability()) {
                            p.getInventory().setItemInHand(new ItemBlock(new BlockAir()));
                        } else {
                            p.getInventory().setItemInHand(item);
                        }
                    }
                    return null;
                }
            }
        }
        return packet;
    }

    public EntityDamageByEntityEvent Convert(CustomEntityDamageByEntityEvent v) {
        return new EntityDamageByEntityEvent(v.getDamager(), v.entity, Convert(v.getCause()), v.getFinalDamage());
    }

    public EntityDamageEvent.DamageCause Convert(CustomEntiyDamageEvent.CustomDamageCause v) {
        switch (v) {
    public EntityDamageByEntityEvent Convert(CustomEntityDamageByEntityEvent v){
        return new EntityDamageByEntityEvent(v.getDamager(),v.entity,Convert(v.getCause()),v.getFinalDamage());
    }
    public EntityDamageEvent.DamageCause Convert(CustomEntiyDamageEvent.CustomDamageCause v){
        switch (v){
            case CONTACT:
                return EntityDamageEvent.DamageCause.CONTACT;
            case ENTITY_ATTACK:
                return EntityDamageEvent.DamageCause.ENTITY_ATTACK;
            case PROJECTILE:
                return EntityDamageEvent.DamageCause.PROJECTILE;
            case SUFFOCATION:
                return EntityDamageEvent.DamageCause.SUFFOCATION;
            case FALL:
                return EntityDamageEvent.DamageCause.FALL;
            case FIRE:
                return EntityDamageEvent.DamageCause.FIRE;
            case FIRE_TICK:
                return EntityDamageEvent.DamageCause.FIRE_TICK;
            case LAVA:
                return EntityDamageEvent.DamageCause.LAVA;
            case DROWNING:
                return EntityDamageEvent.DamageCause.DROWNING;
            case BLOCK_EXPLOSION:
                return EntityDamageEvent.DamageCause.BLOCK_EXPLOSION;
            case ENTITY_EXPLOSION:
                return EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
            case VOID:
                return EntityDamageEvent.DamageCause.VOID;
            case SUICIDE:
                return EntityDamageEvent.DamageCause.SUICIDE;
            case MAGIC:
                return EntityDamageEvent.DamageCause.MAGIC;
            case CUSTOM:
                return EntityDamageEvent.DamageCause.CUSTOM;
            case LIGHTNING:
                return EntityDamageEvent.DamageCause.LIGHTNING;
            case HUNGER:
                return EntityDamageEvent.DamageCause.HUNGER;
            default:
                return EntityDamageEvent.DamageCause.CUSTOM;
        }
    }

    public boolean CustomCanInteract(Vector3 pos, double maxDistance, Player p) {
        return CustomCanInteract(pos, maxDistance, p, 0.5);
    }

    public boolean CustomCanInteract(Vector3 pos, double maxDistance, Player p, double maxDiff) {
        if (p.distanceSquared(pos) > maxDistance * maxDistance) {
            return false;
        }
        }
    }

    public boolean CustomCanInteract(Vector3 pos, double maxDistance, Player p) {
        return CustomCanInteract(pos, maxDistance,p, 0.5 );
    }

    public boolean CustomCanInteract(Vector3 pos, double maxDistance,Player p, double maxDiff) {
        if (p.distanceSquared(pos) > maxDistance * maxDistance) {
            return false;
        }

        Vector2 dV = p.getDirectionPlane();
        double dot = dV.dot(new Vector2(p.x, p.z));
        double dot1 = dV.dot(new Vector2(pos.x, pos.z));
        return (dot1 - dot) >= -maxDiff;
    }

    public int GetPlayerMaxReach(Player p) {
        return 5;//Increase?
    }


    //Check Kill Aura and Class Attack Debuffs
    public void AntiCheatOnAttack(PlayerInteractEvent e) {

    }
}
