package net.yungtechboy1.CyberCore.Custom.BlockEntity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityExplosive;
import cn.nukkit.entity.data.IntEntityData;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityExplosionPrimeEvent;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

//TODO TNT
public class BlastMiningEntity extends Entity implements EntityExplosive {

    public static final int DATA_FUSE_LENGTH = 56; //int

    public static final int NETWORK_ID = 65;
    protected int fuse;

    public BlastMiningEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public float getWidth() {
        return 0.98f;
    }

    @Override
    public float getLength() {
        return 0.98f;
    }

    @Override
    public float getHeight() {
        return 0.98f;
    }

    @Override
    protected float getGravity() {
        return 50f;
    }

    @Override
    protected float getDrag() {
        return 50f;
    }

    @Override
    public boolean canCollide() {
        return false;
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if (source.getCause() == EntityDamageEvent.DamageCause.VOID) {
            super.attack(source);
        }
        return true;
    }

    protected void initEntity() {
        super.initEntity();

        if (namedTag.contains("Fuse")) {
            fuse = namedTag.getByte("Fuse");
        } else {
            fuse = 80;
        }

        setDataFlag(DATA_FLAGS, DATA_FLAG_IGNITED, true);
        setDataProperty(new IntEntityData(DATA_FUSE_LENGTH, fuse));
    }


    public boolean canCollideWith(Entity entity) {
        return false;
    }

    public void saveNBT() {
        super.saveNBT();
        namedTag.putByte("Fuse", fuse);
    }

    public boolean onUpdate(int currentTick) {

        if (closed) {
            return false;
        }

        timing.startTiming();

        int tickDiff = currentTick - lastUpdate;

        if (tickDiff <= 0 && !justCreated) {
            return true;
        }

        if (fuse % 5 == 0) {
            setDataProperty(new IntEntityData(DATA_FUSE_LENGTH, fuse));
        }

        lastUpdate = currentTick;

        boolean hasUpdate = entityBaseTick(tickDiff);

        if (isAlive()) {

            motionY -= getGravity();

            move(motionX, motionY, motionZ);

            float friction = 1 - getDrag();

            motionX *= friction;
            motionY *= friction;
            motionZ *= friction;

            updateMovement();

            if (onGround) {
                motionY *= -0.5;
                motionX *= 0.7;
                motionZ *= 0.7;
            }

            fuse -= tickDiff;

            if (fuse <= 0) {
                explode();
                kill();
            }

        }

        timing.stopTiming();

        return hasUpdate || fuse >= 0 || Math.abs(motionX) > 0.00001 || Math.abs(motionY) > 0.00001 || Math.abs(motionZ) > 0.00001;
    }

    public void explode() {
        EntityExplosionPrimeEvent event = new EntityExplosionPrimeEvent(this, Math.max(4,namedTag.getInt("force")));
        server.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
    }

////    TODO
//    public boolean explodeC(EntityExplosionPrimeEvent event, Position source) {
//        if (namedTag.getInt("size") < 0.1) {
//            return false;
//        }
//
//        List<Block> affectedBlocks = new ArrayList<>();
//
//        int rays = 32;
//        double stepLen = 0.3d;
//
//        Vector3 vector = new Vector3(0, 0, 0);
//        Vector3 vBlock = new Vector3(0, 0, 0);
//
//        int mRays = rays - 1;
//        for (int i = 0; i < rays; ++i) {
//            for (int j = 0; j < rays; ++j) {
//                for (int k = 0; k < rays; ++k) {
//                    if (i == 0 || i == mRays || j == 0 || j == mRays || k == 0 || k == mRays) {
//                        vector.setComponents((double) i / (double) mRays * 2d - 1, (double) j / (double) mRays * 2d - 1, (double) k / (double) mRays * 2d - 1);
//                        double len = vector.length();
//                        vector.setComponents((vector.x / len) * stepLen, (vector.y / len) * stepLen, (vector.z / len) * stepLen);
//                        double pointerX = source.x;
//                        double pointerY = source.y;
//                        double pointerZ = source.z;
//
//                        for (double blastForce = namedTag.getInt("size") * (ThreadLocalRandom.current().nextInt(700, 1301)) / 1000d; blastForce > 0; blastForce -= stepLen * 0.75d) {
//                            int x = (int) pointerX;
//                            int y = (int) pointerY;
//                            int z = (int) pointerZ;
//                            vBlock.x = pointerX >= x ? x : x - 1;
//                            vBlock.y = pointerY >= y ? y : y - 1;
//                            vBlock.z = pointerZ >= z ? z : z - 1;
//                            if (vBlock.y < 0 || vBlock.y > 255) {
//                                break;
//                            }
//                            Block block = level.getBlock(vBlock);
//
//                            if (block.getId() != 0) {
//                                blastForce -= (block.getResistance() / 5 + 0.3d) * stepLen;
//                                if (blastForce > 0) {
//                                    if (!affectedBlocks.contains(block)) {
//                                        affectedBlocks.add(block);
//                                    }
//                                }
//                            }
//                            pointerX += vector.x;
//                            pointerY += vector.y;
//                            pointerZ += vector.z;
//                        }
//                    }
//                }
//            }
//        }
//
//        //EXPLODE B
//
//
//        HashMap<BlockVector3, Boolean> updateBlocks = new HashMap<>();
//        List<Vector3> send = new ArrayList<>();
//
//        double yield = (1d / namedTag.getInt("size")) * 100d;
//
//            EntityExplodeEvent ev = new EntityExplodeEvent(event.getEntity(), source, affectedBlocks, yield);
//            level.getServer().getPluginManager().callEvent(ev);
//            if (ev.isCancelled()) {
//                return false;
//            } else {
//                yield = ev.getYield();
//                affectedBlocks = ev.getBlockList();
//            }
//
//        double explosionSize = namedTag.getInt("size") * 2d;
//        double minX = NukkitMath.floorDouble(source.x - explosionSize - 1);
//        double maxX = NukkitMath.ceilDouble(source.x + explosionSize + 1);
//        double minY = NukkitMath.floorDouble(source.y - explosionSize - 1);
//        double maxY = NukkitMath.ceilDouble(source.y + explosionSize + 1);
//        double minZ = NukkitMath.floorDouble(source.z - explosionSize - 1);
//        double maxZ = NukkitMath.ceilDouble(source.z + explosionSize + 1);
//
//        AxisAlignedBB explosionBB = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
//
//        Entity[] list = level.getNearbyEntities(explosionBB, event.getEntity() instanceof Entity ? (Entity) event.getEntity() : null);
//        for (Entity entity : list) {
//            double distance = entity.distance(source) / explosionSize;
//
//            if (distance <= 1) {
//                Vector3 motion = entity.subtract(source).normalize();
//                int exposure = 1;
//                double impact = (1 - distance) * exposure;
//                int damage = (int) (((impact * impact + impact) / 2) * 8 * explosionSize + 1);
//
//                if (event.getEntity() instanceof Entity) {
//                    EntityDamageByEntityEvent ev2 = new EntityDamageByEntityEvent((Entity) event.getEntity(), entity, EntityDamageEvent.CAUSE_ENTITY_EXPLOSION, damage);
//                    entity.attack(ev2);
//                } else {
//                    EntityDamageEvent ev2 = new EntityDamageEvent(entity, EntityDamageEvent.CAUSE_BLOCK_EXPLOSION, damage);
//                    entity.attack(ev2);
//                }
//
//                entity.setMotion(motion.multiply(impact));
//            }
//        }
//
//        ItemBlock air = new ItemBlock(new BlockAir());
//
//        //Iterator iter = affectedBlocks.entrySet().iterator();
//        for (Block block : affectedBlocks) {
//            //Block block = (Block) ((HashMap.Entry) iter.next()).getValue();
//            if (block.getId() == Block.TNT) {
//                double mot = Math.random() * Math.PI * 2;
//                EntityPrimedTNT tnt = new EntityPrimedTNT(level.getChunk((int) block.x >> 4, (int) block.z >> 4),
//                        new CompoundTag()
//                                .putList(new ListTag<DoubleTag>("Pos")
//                                        .add(new DoubleTag("", block.x + 0.5))
//                                        .add(new DoubleTag("", block.y))
//                                        .add(new DoubleTag("", block.z + 0.5)))
//                                .putList(new ListTag<DoubleTag>("Motion")
//                                        .add(new DoubleTag("", -Math.sin(mot) * 0.02))
//                                        .add(new DoubleTag("", 0.2))
//                                        .add(new DoubleTag("", -Math.cos(mot) * 0.02)))
//                                .putList(new ListTag<FloatTag>("Rotation")
//                                        .add(new FloatTag("", 0))
//                                        .add(new FloatTag("", 0)))
//                                .put("Fuse", new ByteTag("", (byte) (10 + (Math.random() * 30) + 1))
//                                ));
//                tnt.spawnToAll();
//            } else if (Math.random() * 100 < namedTag.getInt("force")/16.6d+30) {
//                for (int[] drop : block.getDrops(air)) {
//                    level.dropItem(block.add(0.5, 0.5, 0.5), Item.get(drop[0], drop[1], drop[2]));
//                }
//            }
//
//            level.setBlockIdAt((int) block.x, (int) block.y, (int) block.z, 0);
//
//            Vector3 pos = new Vector3(block.x, block.y, block.z);
//
//            for (int side = 0; side < 5; side++) {
//                Vector3 sideBlock = pos.getSide(side);
//                BlockVector3 index = Level.blockHash((int) sideBlock.x, (int) sideBlock.y, (int) sideBlock.z);
//                if (!affectedBlocks.contains(sideBlock) && !updateBlocks.containsKey(index)) {
//                    BlockUpdateEvent ev3 = new BlockUpdateEvent(level.getBlock(sideBlock));
//                    level.getServer().getPluginManager().callEvent(ev3);
//                    if (!ev3.isCancelled()) {
//                        ev3.getBlock().onUpdate(Level.BLOCK_UPDATE_NORMAL);
//                    }
//                    updateBlocks.put(index, true);
//                }
//            }
//            send.add(new Vector3(block.x - source.x, block.y - source.y, block.z - source.z));
//        }
//
//        ExplodePacket pk = new ExplodePacket();
//        pk.x = (float) source.x;
//        pk.y = (float) source.y;
//        pk.z = (float) source.z;
//        pk.radius = (float) namedTag.getInt("size");
//        pk.records = send.stream().toArray(Vector3[]::new);
//
//        level.addChunkPacket((int) source.x >> 4, (int) source.z >> 4, pk);
//        level.addParticle(new HugeExplodeSeedParticle(source));
//
//        return true;
//    }

    public void spawnTo(Player player) {
        AddEntityPacket packet = new AddEntityPacket();
        packet.type = NETWORK_ID;
        packet.entityUniqueId = getId();
        packet.entityRuntimeId = getId();
        packet.x = (float) x;
        packet.y = (float) y;
        packet.z = (float) z;
        packet.speedX = (float) motionX;
        packet.speedY = (float) motionY;
        packet.speedZ = (float) motionZ;
        packet.metadata = dataProperties;
        player.dataPacket(packet);
        super.spawnTo(player);
    }

}