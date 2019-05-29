package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.level.Sound;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import net.yungtechboy1.CyberCore.CorePlayer;

/**
 * Created by carlt on 5/16/2019.
 */
public class TNTSpecialistPower extends PowerStackable {


    public TNTSpecialistPower(CorePlayer cp, int lvl, int aq, int maq) {
        super(cp, lvl, aq, maq);
    }

    public TNTSpecialistPower(CorePlayer cp, int psc, int lvl, int aq, int maq) {
        super(cp, psc, lvl, aq, maq);
    }

    private double GetTNTMotionPower(){
        int x = Level+1;
        double t = Math.sqrt(x)*x;
        double b = 5d*x;
        double z =t/b;
        NukkitRandom nr = new NukkitRandom();
        return nr.nextRange((int)(z+1.2d*100d),(int)(z+1.8*100d))/100d;
    }

    public Object UsePower(CorePlayer p,int fuse) {
        System.out.println("POWER>>>>>"+GetTNTMotionPower());
        if (getAvailbleQuantity() > 0 || p.isOp()) {
            System.out.println("POWER>>>>>"+GetTNTMotionPower());
            TakeAvailbleQuantity();
            System.out.println("POWER>>>>>"+GetTNTMotionPower());
            double mot = (double) (new NukkitRandom()).nextSignedFloat() * 3.141592653589793D * 5.0D;//Was 2
            CompoundTag nbt = (new CompoundTag()).putList((new ListTag("Pos")).add(new DoubleTag("", p.x + 0.5D)).add(new DoubleTag("", p.y)).add(new DoubleTag("", p.z + 0.5D))).putList(new ListTag<DoubleTag>("Motion")
                    .add(new DoubleTag("", -Math.sin(p.yaw / 180 * Math.PI) * Math.cos(p.pitch / 180 * Math.PI)))
                    .add(new DoubleTag("", -Math.sin(p.pitch / 180 * Math.PI)))
                    .add(new DoubleTag("", Math.cos(p.yaw / 180 * Math.PI) * Math.cos(p.pitch / 180 * Math.PI)))).putList((new ListTag("Rotation")).add(new FloatTag("", 0.0F)).add(new FloatTag("", 0.0F))).putShort("Fuse", fuse);
            Entity tnt = new EntityPrimedTNT(p.getLevel().getChunk(p.getFloorX() >> 4, p.getFloorZ() >> 4), nbt, p);
            tnt.setMotion(tnt.getMotion().multiply(GetTNTMotionPower()));
            tnt.spawnToAll();
            p.level.addSound(p, Sound.RANDOM_FUSE);
        } else {
            p.sendMessage("Error! You don't have any TNT Power");
        }
        return null;
    }
}
