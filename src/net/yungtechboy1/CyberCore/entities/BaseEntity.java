package net.yungtechboy1.CyberCore.entities;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.entities.monster.Monster;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.data.ByteEntityData;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityMotionEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.MobAI.MobPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntity extends EntityCreature implements EntityStackable {

    public int stayTime = 0;

    public int moveTime = 0;

    public Vector3 target = null;

    public Entity followTarget = null;

    protected List<Block> blocksAround = new ArrayList<>();

    private boolean movement = true;

    private boolean friendly = false;

    private boolean wallcheck = true;

    public boolean wait = false;

    public BaseEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    public abstract Vector3 updateMove(int tickDiff);

    public abstract int getKillExperience();

    public boolean isFriendly() {
        return this.friendly;
    }

    public boolean isMovement() {
        return this.movement;
    }

    public boolean isKnockback() {
        return this.attackTime > 0;
    }

    public int getAttackTime() {
        return this.attackTime;
    }

    public boolean isWallCheck() {
        return this.wallcheck;
    }

    public void setFriendly(boolean bool) {
        this.friendly = bool;
    }

    public void setMovement(boolean value) {
        this.movement = value;
    }

    public void setWallCheck(boolean value) {
        this.wallcheck = value;
    }

    public double getSpeed() {
        return 1;
    }

    public Entity getTarget() {
        return this.followTarget != null ? this.followTarget : (this.target instanceof Entity ? (Entity) this.target : null);
    }

    // TODO
    public void setTarget(Entity target) {
        this.followTarget = target;

        this.moveTime = 0;
        this.stayTime = 0;
        this.target = null;
    }

    @Override
    public boolean IsStackable() {
        return this.namedTag.contains("IsStack") && namedTag.getBoolean("IsStack");
    }

    @Override
    public int GetStackCount() {
        if (this.namedTag.contains("StackCount") && IsStackable()) return namedTag.getInt("StackCount");
        return 1;
    }

    @Override
    public void AddStackCount(int a) {
        if (this.namedTag.contains("StackCount") && IsStackable()) {
            int _m = namedTag.getInt("MaxStack");
            int tc = a + GetStackCount();
            if (_m >= tc) SetStackCount(tc);
        }

    }

    @Override
    public void RemoveStackCount(int a) {
        a = Math.abs(a);
        if (this.namedTag.contains("StackCount") && IsStackable()) {
            int tc = GetStackCount()-a;
            SetStackCount(tc);
        }

    }

    @Override
    public void kill() {

        super.kill();
    }

    @Override
    public void SetStackCount(int amount) {
        namedTag.putInt("StackCount", amount);
        setNameTag("Entity Stack Count :" + amount);
        setNameTagAlwaysVisible(true);
        saveNBT();
    }

    @Override
    protected void initEntity() {
        super.initEntity();



        /*
        * .putBoolean("IsStack", true)
                    .putInt("Count", 1)
                    .putInt("MaxStack", 64)
        * */

        if (this.namedTag.contains("Movement")) {
            this.setMovement(this.namedTag.getBoolean("Movement"));
        }

        if (this.namedTag.contains("WallCheck")) {
            this.setWallCheck(this.namedTag.getBoolean("WallCheck"));
        }
        this.setDataProperty(new ByteEntityData(DATA_FLAG_NO_AI, (byte) 1));
    }

    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putBoolean("Movement", this.isMovement());
        this.namedTag.putBoolean("WallCheck", this.isWallCheck());
    }

    @Override
    public void spawnTo(Player player) {
        if (!this.hasSpawned.containsKey(player.getLoaderId()) && player.usedChunks.containsKey(Level.chunkHash(this.chunk.getX(), this.chunk.getZ()))) {
            AddEntityPacket pk = new AddEntityPacket();
            pk.entityRuntimeId = this.getId();
            pk.entityUniqueId = this.getId();
            pk.type = this.getNetworkId();
            pk.x = (float) this.x;
            pk.y = (float) this.y;
            pk.z = (float) this.z;
            pk.speedX = pk.speedY = pk.speedZ = 0;
            pk.yaw = (float) this.yaw;
            pk.pitch = (float) this.pitch;
            pk.metadata = this.dataProperties;
            player.dataPacket(pk);

            this.hasSpawned.put(player.getLoaderId(), player);
        }
    }

    @Override
    protected void updateMovement() {
        if (MobPlugin.MOB_AI_ENABLED) {
            if (this.lastX != this.x || this.lastY != this.y || this.lastZ != this.z || this.lastYaw != this.yaw || this.lastPitch != this.pitch) {
                this.lastX = this.x;
                this.lastY = this.y;
                this.lastZ = this.z;
                this.lastYaw = this.yaw;
                this.lastPitch = this.pitch;

                this.addMovement(this.x, this.y, this.z, this.yaw, this.pitch, this.yaw);
            }
        }
    }

    public boolean targetOption(EntityCreature creature, double distance) {
        if (this instanceof Monster) {
            if (creature instanceof Player) {
                Player player = (Player) creature;
                return !player.closed && player.spawned && player.isAlive() && player.isSurvival() && distance <= 100;
            }
            return creature.isAlive() && !creature.closed && distance <= 81;
        }
        return false;
    }

    @Override
    public List<Block> getBlocksAround() {
        if (this.blocksAround == null) {
            int minX = NukkitMath.floorDouble(this.boundingBox.getMinX());
            int minY = NukkitMath.floorDouble(this.boundingBox.getMinY());
            int minZ = NukkitMath.floorDouble(this.boundingBox.getMinZ());
            int maxX = NukkitMath.ceilDouble(this.boundingBox.getMaxX());
            int maxY = NukkitMath.ceilDouble(this.boundingBox.getMaxY());
            int maxZ = NukkitMath.ceilDouble(this.boundingBox.getMaxZ());

            this.blocksAround = new ArrayList<>();

            for (int z = minZ; z <= maxZ; ++z) {
                for (int x = minX; x <= maxX; ++x) {
                    for (int y = minY; y <= maxY; ++y) {
                        Block block = this.level.getBlock(this.temporalVector.setComponents(x, y, z));
                        if (block.hasEntityCollision()) {
                            this.blocksAround.add(block);
                        }
                    }
                }
            }
        }

        return this.blocksAround;
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {

        boolean hasUpdate = false;

        this.blocksAround = null;
        this.justCreated = false;

        if (!this.effects.isEmpty()) {
            for (Effect effect : this.effects.values()) {
                if (effect.canTick()) {
                    effect.applyEffect(this);
                }
                effect.setDuration(effect.getDuration() - tickDiff);

                if (effect.getDuration() <= 0) {
                    this.removeEffect(effect.getId());
                }
            }
        }

        this.checkBlockCollision();

        if (this.isInsideOfSolid()) {
            hasUpdate = true;
            this.attack(new EntityDamageEvent(this, EntityDamageEvent.DamageCause.SUFFOCATION, 1));
        }

        if (this.y <= -16 && this.isAlive()) {
            hasUpdate = true;
            this.attack(new EntityDamageEvent(this, EntityDamageEvent.DamageCause.VOID, 10));
        }

        if (this.fireTicks > 0) {
            if (this.fireProof) {
                this.fireTicks -= 4 * tickDiff;
            } else {
                if (!this.hasEffect(Effect.FIRE_RESISTANCE) && (this.fireTicks % 20) == 0 || tickDiff > 20) {
                    EntityDamageEvent ev = new EntityDamageEvent(this, EntityDamageEvent.DamageCause.FIRE_TICK, 1);
                    this.attack(ev);
                }
                this.fireTicks -= tickDiff;
            }

            if (this.fireTicks <= 0) {
                this.extinguish();
            } else {
                this.setDataFlag(DATA_FLAGS, DATA_FLAG_ONFIRE, true);
                hasUpdate = true;
            }
        }

        if (this.moveTime > 0) {
            this.moveTime -= tickDiff;
        }

        if (this.attackTime > 0) {
            this.attackTime -= tickDiff;
        }

        if (this.noDamageTicks > 0) {
            this.noDamageTicks -= tickDiff;
            if (this.noDamageTicks < 0) {
                this.noDamageTicks = 0;
            }
        }

        this.age += tickDiff;
        this.ticksLived += tickDiff;

        return hasUpdate;
    }

    @Override
    public boolean isInsideOfSolid() {
        Block block = this.level.getBlock(this.temporalVector.setComponents(NukkitMath.floorDouble(this.x), NukkitMath.floorDouble(this.y + this.getHeight() - 0.18f), NukkitMath.floorDouble(this.z)));
        AxisAlignedBB bb = block.getBoundingBox();
        return bb != null && block.isSolid() && !block.isTransparent() && bb.intersectsWith(this.getBoundingBox());
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if (this.isKnockback() && source instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) source).getDamager() instanceof Player) {
            return false;
        }

        if (getHealth() - source.getFinalDamage() < 1) {
            System.out.println("KILL111111111");
            if (IsStackable()) {
                if (GetStackCount() <= 1) {
                    super.attack(source);

                    this.target = null;
                    this.attackTime = 7;

                    return true;
                }else if (GetStackCount() > 1) {
                    Server.getInstance().getLogger().info("Skip Kill!!!!!! Remove 1");
                    RemoveStackCount(1);
                    setHealth(getMaxHealth());
                    for (Item i:getDrops())getLevel().dropItem(this,i);
                    spawnToAll();
                    return false;
                }
            }
        }

        super.attack(source);

        this.target = null;
        this.attackTime = 7;

        return true;
    }

    @Override
    public boolean setMotion(Vector3 motion) {
        if (MobPlugin.MOB_AI_ENABLED) {
            if (!this.justCreated) {
                EntityMotionEvent ev = new EntityMotionEvent(this, motion);
                this.server.getPluginManager().callEvent(ev);
                if (ev.isCancelled()) {
                    return false;
                }
            }

            this.motionX = motion.x;
            this.motionY = motion.y;
            this.motionZ = motion.z;
        }
        return true;
    }

    @Override
    public boolean move(double dx, double dy, double dz) {
        if (MobPlugin.MOB_AI_ENABLED) {
            // Timings.entityMoveTimer.startTiming();

            double movX = dx;
            double movY = dy;
            double movZ = dz;

            AxisAlignedBB[] list = this.level.getCollisionCubes(this, this.level.getTickRate() > 1 ? this.boundingBox.getOffsetBoundingBox(dx, dy, dz) : this.boundingBox.addCoord(dx, dy, dz));
            if (this.isWallCheck()) {
                for (AxisAlignedBB bb : list) {
                    dx = bb.calculateXOffset(this.boundingBox, dx);
                }
                this.boundingBox.offset(dx, 0, 0);

                for (AxisAlignedBB bb : list) {
                    dz = bb.calculateZOffset(this.boundingBox, dz);
                }
                this.boundingBox.offset(0, 0, dz);
            }
            for (AxisAlignedBB bb : list) {
                dy = bb.calculateYOffset(this.boundingBox, dy);
            }
            this.boundingBox.offset(0, dy, 0);

            this.setComponents(this.x + dx, this.y + dy, this.z + dz);
            this.checkChunks();

            this.checkGroundState(movX, movY, movZ, dx, dy, dz);
            this.updateFallState(this.onGround);

            // Timings.entityMoveTimer.stopTiming();
        }
        return true;
    }


}
