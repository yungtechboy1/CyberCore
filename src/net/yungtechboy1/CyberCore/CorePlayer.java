package net.yungtechboy1.CyberCore;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.PlayerFood;
import cn.nukkit.block.*;
import cn.nukkit.entity.data.ShortEntityData;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Position;
import cn.nukkit.math.*;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.network.protocol.*;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.DummyBossBar;
import co.aikar.timings.Timing;
import co.aikar.timings.Timings;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Rank.Rank;
import net.yungtechboy1.CyberCore.Rank.RankList;

import java.util.HashMap;

public class CorePlayer extends Player {


    public FormType.MainForm LastSentFormType = FormType.MainForm.NULL;
    public FormType.SubMenu LastSentSubMenu = FormType.SubMenu.MainMenu;
    private FormWindow nw;


    public Integer money = 0;
    public Integer kills = 0;
    public Integer deaths = 0;
    public Integer banned = 0;
    public String faction_id = null;
    public String uuid;
    public HashMap<String, Object> extraData = new HashMap<>();

    long uct = 0;
    boolean uw = false;

    private Rank rank = RankList.PERM_GUEST.getRank();

    public CorePlayer(SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);
    }

    public Integer addDeath() {
        return deaths += 1;
    }

    public Integer addDeath(Integer amount) {
        return deaths += amount;
    }

    public Integer addKill() {
        return kills += 1;
    }

    public Integer addKills(Integer amount) {
        return kills += amount;
    }

    public double calculateKD() {
        return kills / deaths;
    }

    public boolean hasNewWindow() {
        return nw != null;
    }

    public FormWindow getNewWindow() {
        return nw;
    }

    public void setNewWindow(FormWindow nw) {
        this.nw = nw;
    }

    public void clearNewWindow() {
        this.nw = null;
    }

    private BlockVector3 lastBreakPosition1 = new BlockVector3();


    @Override
    public void fall(float fallDistance) {
        if(!uw)super.fall(fallDistance);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        return !(uw && source.getCause() == EntityDamageEvent.DamageCause.FALL) && super.attack(source);
    }

    public boolean CheckGround(){
            AxisAlignedBB bb = this.boundingBox.clone();
            bb.setMinY(bb.getMinY() - 0.75);

            this.onGround = this.level.getCollisionBlocks(bb).length > 0;
        this.isCollided = this.onGround;
        return onGround;
    }

    @Override
    public void handleDataPacket(DataPacket packet) {
        if (!connected) {
            return;
        }

        try (Timing timing = Timings.getReceiveDataPacketTiming(packet)) {
            DataPacketReceiveEvent ev = new DataPacketReceiveEvent(this, packet);
            this.server.getPluginManager().callEvent(ev);
            if (ev.isCancelled()) {
                return;
            }

            if (packet.pid() == ProtocolInfo.BATCH_PACKET) {
                this.server.getNetwork().processBatch((BatchPacket) packet, this);
                return;
            }

            packetswitch:
            switch (packet.pid()) {

                case ProtocolInfo.MOVE_PLAYER_PACKET:
                    super.handleDataPacket(packet);
                    if (this.teleportPosition != null) {
                        break;
                    }

                    MovePlayerPacket movePlayerPacket = (MovePlayerPacket) packet;
                    Vector3 newPos = new Vector3(movePlayerPacket.x, movePlayerPacket.y - this.getEyeHeight(), movePlayerPacket.z);

                    Vector3 dif = newPos.subtract(this);
                    CheckGround();
                    if (dif.getY() > 0 && !uw) {
                        sendMessage(dif + "!");
                        addMotion(dif.x, dif.y*1.5, dif.z);
                        resetFallDistance();
                        uct = lastUpdate + 20;
                        uw =true;
//                        upp++;
//                        if (upp > 3) uw = true;
                    } else if(onGround && uct > lastUpdate) {
//                        if (upp > 0) upp--;
                        uw = false;
                    }
                    if(uw)inAirTicks = 0;
//                    if (upp == 0) uw = false;


                    break;
                case ProtocolInfo.PLAYER_ACTION_PACKET:
                    PlayerActionPacket playerActionPacket = (PlayerActionPacket) packet;
                    if (!this.spawned || (!this.isAlive() && playerActionPacket.action != PlayerActionPacket.ACTION_RESPAWN && playerActionPacket.action != PlayerActionPacket.ACTION_DIMENSION_CHANGE_REQUEST)) {
                        return;
                    }

                    playerActionPacket.entityId = this.id;
                    Vector3 pos = new Vector3(playerActionPacket.x, playerActionPacket.y, playerActionPacket.z);
                    BlockFace face = BlockFace.fromIndex(playerActionPacket.face);

                    actionswitch:
                    switch (playerActionPacket.action) {
                        case PlayerActionPacket.ACTION_START_BREAK:
                            long currentBreak = System.currentTimeMillis();
                            BlockVector3 currentBreakPosition = new BlockVector3(playerActionPacket.x, playerActionPacket.y, playerActionPacket.z);
                            // HACK: Client spams multiple left clicks so we need to skip them.
                            if ((lastBreakPosition1.equals(currentBreakPosition) && (currentBreak - this.lastBreak) < 10) || pos.distanceSquared(this) > 100) {
                                return;
                            }
                            Block target = this.level.getBlock(pos);
                            PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(this, this.inventory.getItemInHand(), target, face, target.getId() == 0 ? PlayerInteractEvent.Action.LEFT_CLICK_AIR : PlayerInteractEvent.Action.LEFT_CLICK_BLOCK);
                            this.getServer().getPluginManager().callEvent(playerInteractEvent);
                            if (playerInteractEvent.isCancelled()) {
                                this.inventory.sendHeldItem(this);
                                return;
                            }
                            switch (target.getId()) {
                                case Block.NOTEBLOCK:
                                    ((BlockNoteblock) target).emitSound();
                                    break actionswitch;
                                case Block.DRAGON_EGG:
                                    ((BlockDragonEgg) target).teleport();
                                    break actionswitch;
                            }
                            Block block = target.getSide(face);
                            if (block.getId() == Block.FIRE) {
                                //Chance of getting caught on fire!
                                RandomChanceOfFire(77);
                                this.level.setBlock(block, new BlockAir(), true);
                                return;
                            }
                            if (!this.isCreative()) {
                                //improved this to take stuff like swimming, ladders, enchanted tools into account, fix wrong tool break time calculations for bad tools (pmmp/PocketMine-MP#211)
                                //Done by lmlstarqaq
                                double breakTime = Math.ceil(target.getBreakTime(this.inventory.getItemInHand(), this) * 20);
                                if (breakTime > 0) {
                                    LevelEventPacket pk = new LevelEventPacket();
                                    pk.evid = LevelEventPacket.EVENT_BLOCK_START_BREAK;
                                    pk.x = (float) pos.x;
                                    pk.y = (float) pos.y;
                                    pk.z = (float) pos.z;
                                    pk.data = (int) (65535 / breakTime);
                                    this.getLevel().addChunkPacket(pos.getFloorX() >> 4, pos.getFloorZ() >> 4, pk);
                                }
                            }

                            this.breakingBlock = target;
                            this.lastBreak = currentBreak;
                            this.lastBreakPosition1 = currentBreakPosition;
                            break;
                        case PlayerActionPacket.ACTION_JUMP:
                            sendMessage("JUMMMPPPPP!!!" + getDirection());
//                            addMovement(0,2.5,0,0,0,0);
//                            switch (getDirection()) {
//                                case NORTH:
//                                    addMotion(motionX * 2, 3, 0);
//                                    break;
//                                case EAST:
//                                    addMotion(0, 3, motionZ * 2);
//                                    break;
//                                case WEST:
//                                    addMotion(motionX * -2, 3, 0);
//                                    break;
//                                case SOUTH:
//                                    addMotion(0, 3, motionZ * -2);
//                                    break;
//
//                            }
                            break;
                    }
            }
        }
        super.handleDataPacket(packet);
    }

    public void RandomChanceOfFire(int max) {
        NukkitRandom nr = new NukkitRandom(entityCount * max);
        int f = nr.nextRange(0, 100);
        if (f < max) setOnFire(nr.nextRange(1, 4));
    }

//
//    @Override
//    public boolean onUpdate(int currentTick) {
//        if (!this.loggedIn) {
//            return false;
//        }
//
//        int tickDiff = currentTick - this.lastUpdate;
//
//        if (tickDiff <= 0) {
//            return true;
//        }
//
//        this.messageCounter = 2;
//
//        this.lastUpdate = currentTick;
//
//        if (!this.isAlive() && this.spawned) {
//            ++this.deadTicks;
//            if (this.deadTicks >= 10) {
//                this.despawnFromAll();
//            }
//            return true;
//        }
//
//        if (this.spawned) {
//            this.processMovement(tickDiff);
//
//            this.entityBaseTick(tickDiff);
//
//            if (this.getServer().getDifficulty() == 0 && this.level.getGameRules().getBoolean(GameRule.NATURAL_REGENERATION)) {
//                if (this.getHealth() < this.getMaxHealth() && this.ticksLived % 20 == 0) {
//                    this.heal(1);
//                }
//
//                PlayerFood foodData = this.getFoodData();
//
//                if (foodData.getLevel() < 20 && this.ticksLived % 10 == 0) {
//                    foodData.addFoodLevel(1, 0);
//                }
//            }
//
//            if (this.isOnFire() && this.lastUpdate % 10 == 0) {
//                if (this.isCreative() && !this.isInsideOfFire()) {
//                    this.extinguish();
//                } else if (this.getLevel().isRaining()) {
//                    if (this.getLevel().canBlockSeeSky(this)) {
//                        this.extinguish();
//                    }
//                }
//            }
//
//            if (!this.isSpectator() && this.speed != null) {
//                if (this.onGround) {
//                    if (this.inAirTicks != 0) {
//                        this.startAirTicks = 5;
//                    }
//                    this.inAirTicks = 0;
//                    this.highestPosition = this.y;
//                } else {
//                    if (!this.isGliding() && !server.getAllowFlight() && !this.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT) && this.inAirTicks > 10 && !this.isSleeping() && !this.isImmobile() && uw) {
//                        double expectedVelocity = (-this.getGravity()) / ((double) this.getDrag()) - ((-this.getGravity()) / ((double) this.getDrag())) * Math.exp(-((double) this.getDrag()) * ((double) (this.inAirTicks - this.startAirTicks)));
//                        double diff = (this.speed.y - expectedVelocity) * (this.speed.y - expectedVelocity);
//
//                        Block block = level.getBlock(this);
//                        boolean onLadder = block.getId() == BlockID.LADDER;
//
//                        if (!this.hasEffect(Effect.JUMP) && diff > 0.6 && expectedVelocity < this.speed.y && !onLadder) {
//                            if (this.inAirTicks < 100) {
//                                //this.sendSettings();
//                                this.setMotion(new Vector3(0, expectedVelocity, 0));
//                            } else if (this.kick(PlayerKickEvent.Reason.FLYING_DISABLED, "Flying is not enabled on this server")) {
//                                return false;
//                            }
//                        }
//                        if (onLadder) {
//                            this.resetFallDistance();
//                        }
//                    }
//
//                    if (this.y > highestPosition) {
//                        this.highestPosition = this.y;
//                    }
//
//                    if (this.isGliding()) this.resetFallDistance();
//
//                    ++this.inAirTicks;
//
//                }
//
//                if (this.isSurvival() || this.isAdventure()) {
//                    if (this.getFoodData() != null) this.getFoodData().update(tickDiff);
//                }
//            }
//        }
//
//        this.checkTeleportPosition();
//        this.checkInteractNearby();
//
//        if (this.spawned && this.dummyBossBars.size() > 0 && currentTick % 100 == 0) {
//            this.dummyBossBars.values().forEach(DummyBossBar::updateBossEntityPosition);
//        }
//
//        return true;
//    }
}
