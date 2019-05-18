/**
 * MobPlugin.java
 * 
 * Created on 17:46:07
 */
package net.yungtechboy1.CyberCore.MobAI;

import net.yungtechboy1.CyberCore.Custom.Item.ItemEnderPearl;
import net.yungtechboy1.CyberCore.Custom.Item.ItemInkSac;
import net.yungtechboy1.CyberCore.Custom.Item.MobPluginItems;
import net.yungtechboy1.CyberCore.entities.BaseEntity;
import net.yungtechboy1.CyberCore.entities.animal.flying.Bat;
import net.yungtechboy1.CyberCore.entities.animal.swimming.Squid;
import net.yungtechboy1.CyberCore.entities.animal.walking.*;
import net.yungtechboy1.CyberCore.entities.block.BlockEntitySpawner;
import net.yungtechboy1.CyberCore.entities.monster.flying.Blaze;
import net.yungtechboy1.CyberCore.entities.monster.flying.Ghast;
import net.yungtechboy1.CyberCore.entities.monster.walking.*;
import net.yungtechboy1.CyberCore.entities.monster.walking.*;
import net.yungtechboy1.CyberCore.entities.utils.Utils;
import cn.nukkit.IPlayer;
import cn.nukkit.OfflinePlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import cn.nukkit.event.player.PlayerMouseOverEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemMuttonCooked;
import cn.nukkit.item.ItemMuttonRaw;
import cn.nukkit.item.food.Food;
import cn.nukkit.item.food.FoodNormal;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.DyeColor;
import net.yungtechboy1.CyberCore.entities.projectile.EntityFireBall;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.entities.animal.walking.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz (kniffo80)</a>
 */
public class MobPlugin implements Listener {

    public static boolean MOB_AI_ENABLED = false;

    private int           counter        = 0;

    private Config        pluginConfig   = null;

    CyberCoreMain CCM;

    public MobPlugin(CyberCoreMain ccm){
        CCM = ccm;/*
        onLoad();
        onEnable();*/
    }

    public Config getConfig(){
        return new Config(new File(CCM.getDataFolder(), "MobPlugin.yml"), Config.YAML, new ConfigSection());
    }

    public void onLoad() {
        registerEntities();
        registerItems();
        Utils.logServerInfo("Plugin loaded successfully.");
    }


    public void onEnable() {

        CCM.getLogger().error("RUNNNINNNGNGNGGG");
        // Config reading and writing
        pluginConfig = new Config(new File(CCM.getDataFolder(), "MobPlugin.yml"));

        // we need this flag as it's controlled by the _plugin's entities
        MOB_AI_ENABLED = true;
        int spawnDelay = 0;

        // register as listener to _plugin events
        CCM.getServer().getPluginManager().registerEvents(this, CCM);

        if (spawnDelay > 0) {
//            CCM.getServer().getScheduler().scheduleRepeatingTask(new AutoSpawnTask(this), spawnDelay, true);
        }

        Utils.logServerInfo(String.format("Plugin enabling successful [aiEnabled:%s] [autoSpawnTick:%d]", MOB_AI_ENABLED, spawnDelay));
    }

    public void onDisable() {
        Utils.logServerInfo("Plugin disabled successful.");
    }

    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] sub) {
        String output = "";

        if (sub.length == 0) {
            output += "no command given. Use 'mob spawn Wolf <opt:playername(if spawned by server)>' e.g.";
        } else {
            switch (sub[0]) {
                case "spawn":
                    String mob = sub[1];
                    Player playerThatSpawns = null;

                    if (sub.length == 3) {
                        playerThatSpawns = CCM.getServer().getPlayer(sub[2]);
                    } else {
                        playerThatSpawns = (Player) commandSender;
                    }

                    if (playerThatSpawns != null) {
                        Position pos = playerThatSpawns.getPosition();

                        Entity ent;
                        if ((ent = MobPlugin.create(mob, pos)) != null) {
                            ent.spawnToAll();
                            output += "spawned " + mob + " to " + playerThatSpawns.getName();
                        } else {
                            output += "Unable to spawn " + mob;
                        }
                    } else {
                        output += "Unknown player " + (sub.length == 3 ? sub[2] : ((Player) commandSender).getName());
                    }
                    break;
                case "removeall":
                    int count = 0;
                    for (Level level : CCM.getServer().getLevels().values()) {
                        for (Entity entity : level.getEntities()) {
                            if (entity instanceof BaseEntity && !entity.closed && entity.isAlive()) {
                                entity.close();
                                count++;
                            }
                        }
                    }
                    output += "Removed " + count + " entities from all levels.";
                    break;
                case "removeitems":
                    count = 0;
                    for (Level level : CCM.getServer().getLevels().values()) {
                        for (Entity entity : level.getEntities()) {
                            if (entity instanceof EntityItem && entity.isOnGround()) {
                                entity.close();
                                count++;
                            }
                        }
                    }
                    output += "Removed " + count + " items on ground from all levels.";
                    break;
                default:
                    output += "Unkown command.";
                    break;
            }
        }

        commandSender.sendMessage(output);
        return true;
    }

    /**
     * Returns _plugin specific yml configuration
     * 
     * @return a {@link Config} instance
     */
    public Config getPluginConfig() {
        return this.pluginConfig;
    }

    public static void registerEntities() {
        // register living entities
        Entity.registerEntity(Bat.class.getSimpleName(), Bat.class);
        Entity.registerEntity(Chicken.class.getSimpleName(), Chicken.class);
        Entity.registerEntity(Cow.class.getSimpleName(), Cow.class);
        Entity.registerEntity(Donkey.class.getSimpleName(), Donkey.class);
        Entity.registerEntity(Horse.class.getSimpleName(), Horse.class);
        Entity.registerEntity(Mooshroom.class.getSimpleName(), Mooshroom.class);
        Entity.registerEntity(Mule.class.getSimpleName(), Mule.class);
        Entity.registerEntity(Ocelot.class.getSimpleName(), Ocelot.class);
        Entity.registerEntity(Pig.class.getSimpleName(), Pig.class);
        Entity.registerEntity(Rabbit.class.getSimpleName(), Rabbit.class);
        Entity.registerEntity(Sheep.class.getSimpleName(), Sheep.class);
        Entity.registerEntity(SkeletonHorse.class.getSimpleName(), SkeletonHorse.class);
        Entity.registerEntity(Squid.class.getSimpleName(), Squid.class);
        Entity.registerEntity(ZombieHorse.class.getSimpleName(), ZombieHorse.class);

        Entity.registerEntity(Blaze.class.getSimpleName(), Blaze.class);
        Entity.registerEntity(Ghast.class.getSimpleName(), Ghast.class);
        Entity.registerEntity(CaveSpider.class.getSimpleName(), CaveSpider.class);
        Entity.registerEntity(Creeper.class.getSimpleName(), Creeper.class);
        Entity.registerEntity(Enderman.class.getSimpleName(), Enderman.class);
        Entity.registerEntity(IronGolem.class.getSimpleName(), IronGolem.class);
        Entity.registerEntity(PigZombie.class.getSimpleName(), PigZombie.class);
        Entity.registerEntity(Silverfish.class.getSimpleName(), Silverfish.class);
        Entity.registerEntity(Skeleton.class.getSimpleName(), Skeleton.class);
        Entity.registerEntity(SnowGolem.class.getSimpleName(), SnowGolem.class);
        Entity.registerEntity(Spider.class.getSimpleName(), Spider.class);
        Entity.registerEntity(Witch.class.getSimpleName(), Witch.class);
        Entity.registerEntity(Wolf.class.getSimpleName(), Wolf.class);
        Entity.registerEntity(Zombie.class.getSimpleName(), Zombie.class);
        Entity.registerEntity(ZombieVillager.class.getSimpleName(), ZombieVillager.class);

        // register the fireball entity
        Entity.registerEntity("FireBall", EntityFireBall.class);

        // register the mob spawner (which is probably not needed anymore)
        BlockEntity.registerBlockEntity("MobSpawner", BlockEntitySpawner.class);
        Utils.logServerInfo("registerEntites: done.");
    }

    public static void registerItems() {
        // register the new items
        Item.addCreativeItem(new ItemMuttonCooked());
        Item.addCreativeItem(new ItemMuttonRaw());
        Item.addCreativeItem(new ItemEnderPearl());
        Item.addCreativeItem(new ItemInkSac());

        // register the items as food
//        Food.registerFood(new FoodNormal(6, 9.6F).addRelative(MobPluginItems.COOKED_MUTTON), CCM);
//        Food.registerFood(new FoodNormal(2, 1.2F).addRelative(MobPluginItems.RAW_MUTTON), CCM);

        Item.list[MobPluginItems.COOKED_MUTTON] = ItemMuttonCooked.class;
        Item.list[MobPluginItems.RAW_MUTTON] = ItemMuttonRaw.class;
        Item.list[MobPluginItems.ENDER_PEARL] = ItemEnderPearl.class;
        Item.list[MobPluginItems.INK_SAC] = ItemInkSac.class;

        Utils.logServerInfo("registerItems: done.");
    }

    /**
     * @param type
     * @param source
     * @param args
     * @return
     */
    public static Entity create(Object type, Position source, Object... args) {
        FullChunk chunk = source.getLevel().getChunk((int) source.x >> 4, (int) source.z >> 4, true);
        if (!chunk.isGenerated()) {
            chunk.setGenerated();
        }
        if (!chunk.isPopulated()) {
            chunk.setPopulated();
        }

        CompoundTag nbt = new CompoundTag().putList(new ListTag<DoubleTag>("Pos").add(new DoubleTag("", source.x)).add(new DoubleTag("", source.y)).add(new DoubleTag("", source.z)))
                .putList(new ListTag<DoubleTag>("Motion").add(new DoubleTag("", 0)).add(new DoubleTag("", 0)).add(new DoubleTag("", 0)))
                .putList(new ListTag<FloatTag>("Rotation").add(new FloatTag("", source instanceof Location ? (float) ((Location) source).yaw : 0))
                        .add(new FloatTag("", source instanceof Location ? (float) ((Location) source).pitch : 0)));

        return Entity.createEntity(type.toString(), chunk, nbt, args);
    }

    /**
     * Returns all registered players to the current server
     * 
     * @return a {@link List} containing a number of {@link IPlayer} elements, which can be {@link Player} or {@link OfflinePlayer}
     */
    public List<IPlayer> getAllRegisteredPlayers() {
        List<IPlayer> playerList = new ArrayList<>();
        for (Player player : CCM.getServer().getOnlinePlayers().values()) {
            playerList.add(player);
        }
        /*
        // now get all stores offline players ...
        File playerDirectory = new File(this.getServer().getDataPath() + "players");
        File entry;
        String[] storedFiles = playerDirectory.list();
        if (storedFiles != null && storedFiles.length > 0) {
            for (String file : storedFiles) {
                entry = new File(file);
                String filename = entry.getName();
                filename = filename.substring(0, filename.indexOf(".dat"));
                if (!isPlayerAlreadyInList(filename, playerList)) {
                    playerList.add(new OfflinePlayer(this.getServer(), filename));
                }
            }
        }*/
        return playerList;
    }

    /**
     * checks if a given player name's player instance is already in the given list
     * 
     * @param name the name of the player to be checked
     * @param playerList the existing entries
     * @return <code>true</code> if the player is already in the list
     */
    private boolean isPlayerAlreadyInList(String name, List<IPlayer> playerList) {
        for (IPlayer player : playerList) {
            if (player.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    // --- event listeners ---
    /**
     * This event is called when an entity dies. We need this for experience gain.
     * 
     * @param ev the event that is received
     */
    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent ev) {
        if (ev.getEntity() instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) ev.getEntity();
            if (baseEntity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                Entity damager = ((EntityDamageByEntityEvent) baseEntity.getLastDamageCause()).getDamager();
                if (damager instanceof Player) {
                    Player player = (Player) damager;
                    int killExperience = baseEntity.getKillExperience();
                    if (killExperience > 0 && player != null && player.isSurvival()) {
                        player.addExperience(killExperience);
                        // don't drop that fucking experience orbs because they're somehow buggy :(
                        // if (player.isSurvival()) {
                        // for (int i = 1; i <= killExperience; i++) {
                        // player.getLevel().dropExpOrb(baseEntity, 1);
                        // }
                        // }
                    }
                }
            }
        }
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent ev) {
        if (ev.isCancelled()) {
            return;
        }

        Block block = ev.getBlock();
        if ((block.getId() == Block.STONE || block.getId() == Block.STONE_BRICK || block.getId() == Block.STONE_WALL || block.getId() == Block.STONE_BRICK_STAIRS)
            && block.getLevel().getBlockLightAt((int) block.x, (int) block.y, (int) block.z) < 12 && Utils.rand(1, 5) == 1) {
            // TODO: 돌만 붓시면 되긋나
            /*
             * Silverfish entity = (Silverfish) create("Silverfish", block.add(0.5, 0, 0.5)); if(entity != null){ entity.spawnToAll(); }
             */
        }
    }

    @EventHandler
    public void PlayerMouseOverEntityEvent(PlayerMouseOverEntityEvent ev) {
        if (this.counter > 10) {
            counter = 0;
            FileLogger.debug(String.format("Received PlayerMouseOverEntityEvent [entity:%s]", ev.getEntity()));
            // wolves can be tamed using bones
            if (ev != null && ev.getEntity() != null && ev.getPlayer() != null && ev.getEntity().getNetworkId() == Wolf.NETWORK_ID && ev.getPlayer().getInventory().getItemInHand().getId() == Item.BONE) {
                // check if already owned and tamed ...
                Wolf wolf = (Wolf) ev.getEntity();
                if (!wolf.isAngry() && wolf.getOwner() == null) {
                    // now try it out ...
                    EntityEventPacket packet = new EntityEventPacket();
                    packet.eid = ev.getEntity().getId();
                    packet.event = EntityEventPacket.TAME_SUCCESS;
                    Server.broadcastPacket(new Player[] { ev.getPlayer() }, packet);
                    
                    // set the owner
                    wolf.setOwner(ev.getPlayer());
                    wolf.setCollarColor(DyeColor.BLUE);
                    wolf.saveNBT();
                }
            }
        } else {
            counter++;
        }
    }
    //
    // @EventHandler
    // public void PlayerMouseRightEntityEvent(PlayerMouseRightEntityEvent ev) {
    // FileLogger.debug(String.format("Received PlayerMouseRightEntityEvent [entity:%s]", ev.getEntity()));
    // }
}
