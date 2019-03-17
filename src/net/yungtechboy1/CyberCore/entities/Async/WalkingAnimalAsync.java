package net.yungtechboy1.CyberCore.entities.Async;

import net.yungtechboy1.CyberCore.entities.animal.WalkingAnimal;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFence;
import cn.nukkit.block.BlockFenceGate;
import cn.nukkit.block.BlockLiquid;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.AsyncTask;
import net.yungtechboy1.CyberCore.MobAI.MobPlugin;

/**
 * Created by carlt_000 on 1/23/2017.
 */
public class WalkingAnimalAsync extends AsyncTask {

    public double motionX;
    public double motionY;
    public double motionZ;
    public double dx;
    public double dy;
    public double dz;
    MobPlugin MP;
    int TD;
    int CT;
    boolean UM = false;
    boolean MV = false;

    WalkingAnimal CP;

    public WalkingAnimalAsync(WalkingAnimal creeper, int Tickdiff, int CurrentTick) {
        CP = creeper;
        CT = CurrentTick;
        TD = Tickdiff;
    }

    @Override
    public void onCompletion(Server server) {
        Level lvl = server.getDefaultLevel();
        Entity e = lvl.getEntity(CP.getId());
        if (e instanceof WalkingAnimal) {
            e.lastUpdate = CT;
            e.entityBaseTick(TD);
            WalkingAnimal wm = (WalkingAnimal) e;
            wm.checkTarget();
            if ((!wm.isFriendly() || !(wm.target instanceof Player)) && wm.target instanceof Entity) {

            } else if (wm.target != null && (Math.pow(wm.x - wm.target.x, 2) + Math.pow(wm.z - wm.target.z, 2)) <= 1) {
                wm.moveTime = 0;
            }

            if (MV || UM) {
                wm.move(dx/3, dy, dz/3);
                if (e.lastX != CP.x || e.lastY != CP.y || e.lastZ != CP.z || e.lastYaw != CP.yaw || e.lastPitch != CP.pitch) {
                    e.lastX = CP.x;
                    e.lastY = CP.y;
                    e.lastZ = CP.z;
                    e.lastYaw = CP.yaw;
                    e.lastPitch = CP.pitch;
                    wm.addMovement(CP.x, CP.y, CP.z, CP.yaw, CP.pitch, CP.yaw);
                }
            }
            wm.wait = false;
        }
    }

    @Override
    public void onRun() {
        //Server.getInstance().getLogger().debug(TD+"--"+CP.getAttackTime());
        int tickDiff = TD;

        if (!CP.isMovement()) return;
        if (CP.isKnockback()) {
            dx = CP.motionX * tickDiff;
            dy = CP.motionY - 0.08F * tickDiff;
            dz = CP.motionZ * tickDiff;
            UM = true;
            //onCompletion(Server.getInstance());
            return;
        }

        if (CP.followTarget != null && !CP.followTarget.closed && CP.followTarget.isAlive()) {
            double x = CP.followTarget.x - CP.x;
            double y = CP.followTarget.y - CP.y;
            double z = CP.followTarget.z - CP.z;

            double diff = Math.abs(x) + Math.abs(z);
            if (CP.stayTime > 0 || CP.distance(CP.followTarget) <= (CP.getWidth() + 0.0d) / 2 + 0.05) {
                this.motionX = 0;
                this.motionZ = 0;
            } else {
                this.motionX = CP.getSpeed() * 0.15 * (x / diff);
                this.motionZ = CP.getSpeed() * 0.15 * (z / diff);
            }
            CP.yaw = Math.toDegrees(-Math.atan2(x / diff, z / diff));
            CP.pitch = y == 0 ? 0 : Math.toDegrees(-Math.atan2(y, Math.sqrt(x * x + z * z)));

            dx = CP.motionX * tickDiff;
            dy = CP.motionY - 0.08F * tickDiff;
            dz = CP.motionZ * tickDiff;
            UM = true;

            return;
        }

        Vector3 before = CP.target;
        CP.checkTarget();

        if (CP.target instanceof EntityCreature || before != CP.target) {
            double x = CP.target.x - CP.x;
            double y = CP.target.y - CP.y;
            double z = CP.target.z - CP.z;

            Vector3 target = CP.target;
            double diff = Math.abs(x) + Math.abs(z);
            if (CP.stayTime > 0 || CP.distance(CP.target) <= (CP.getWidth() + 0.0d) / 2 + 0.05) {
                this.motionX = 0;
                this.motionZ = 0;
            } else {
                this.motionX = CP.getSpeed() * 0.15 * (x / diff);
                this.motionZ = CP.getSpeed() * 0.15 * (z / diff);
            }
            CP.yaw = Math.toDegrees(-Math.atan2(x / diff, z / diff));
            CP.pitch = y == 0 ? 0 : Math.toDegrees(-Math.atan2(y, Math.sqrt(x * x + z * z)));
        }


        dx = CP.motionX * TD;
        dz = CP.motionZ * TD;
        boolean isJump = this.checkJump(dx, dz);
        if (CP.stayTime > 0) {
            CP.stayTime -= TD;
            dx = 0;
            dy = CP.motionY * TD;
            dz = 0;
            MV = true;
        } else {
            Vector2 be = new Vector2(CP.x + dx, CP.z + dz);
            dy = CP.motionY * TD;
            MV = true;
            Vector2 af = new Vector2(CP.x, CP.z);

            if ((be.x != af.x || be.y != af.y) && !isJump) {
                CP.moveTime -= 90 * TD;
            }
        }

        if (!isJump) {
            if (CP.onGround) {
                CP.motionY = 0;
            } else if (CP.motionY > -0.08F * 4) {
                if (!(CP.level.getBlock(new Vector3(NukkitMath.floorDouble(CP.x), (int) (CP.y + 0.8), NukkitMath.floorDouble(CP.z))) instanceof BlockLiquid)) {
                    CP.motionY -= 0.08F * 1;
                }
            } else {
                CP.motionY -= 0.08F * TD;
            }
        }

        dx = CP.motionX * TD;
        dy = CP.motionY * TD;
        dz = CP.motionZ * TD;

        UM = true;
        //onCompletion(Server.getInstance());
    }

    private boolean checkJump(double dx, double dz) {
        if (CP.motionY == 0.08F * 2) {
            return CP.level.getBlock(new Vector3(NukkitMath.floorDouble(CP.x), (int) CP.y, NukkitMath.floorDouble(CP.z))) instanceof BlockLiquid;
        } else {
            if (CP.level.getBlock(new Vector3(NukkitMath.floorDouble(CP.x), (int) (CP.y + 0.8), NukkitMath.floorDouble(CP.z))) instanceof BlockLiquid) {
                CP.motionY = 0.08F * 2;
                return true;
            }
        }

        if (!CP.onGround || CP.stayTime > 0) {
            return false;
        }

        Block that = CP.getLevel().getBlock(new Vector3(NukkitMath.floorDouble(CP.x + dx + .5), (int) CP.y, NukkitMath.floorDouble(CP.z + dz + .5)));
        if (CP.getDirection() == null) {
            return false;
        }
        Block block = that.getSide(CP.getDirection());
        if (!block.canPassThrough() && block.getSide(BlockFace.UP).canPassThrough() && that.getSide(BlockFace.UP, 2).canPassThrough()) {
            if (block instanceof BlockFence || block instanceof BlockFenceGate) {
                CP.motionY = 0.08F;
            } else if (CP.motionY <= 0.08F * 7) {
                CP.motionY = 0.08F * 7;
            } else {
                CP.motionY += 0.08F * .25;
            }
            return true;
        }
        return false;
    }
}
