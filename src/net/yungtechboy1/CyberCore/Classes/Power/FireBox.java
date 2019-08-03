package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.Sound;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public class FireBox extends StagePowerAbstract {

    public FireBox(BaseClass b) {
        super(b);
        //TODO make this so that this power Runs Automatically!
//        TickUpdate = 10;
//        CanSendCanNotRunMessage = false;
    }

    @Override
    public PowerSettings getPowerSettings() {
        return new PowerSettings( false, false, true, false);
    }

    @Override
    public double getPowerSourceCost() {
        return 10;//250? TODO
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent e) {
        return super.EntityDamageEvent(e);
    }

    @Override
    public int getPowerSuccessChance() {
        return (90 / 5) * getStage().getValue();
    }

    @Override
    protected int getCooldownTime() {
        switch (getStage()) {
            default:
            case NA:
            case STAGE_1:
                return 45;
            case STAGE_2:
                return 35;
            case STAGE_3:
                return 30;
            case STAGE_4:
                return 25;
            case STAGE_5:
                return 15;
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.DragonJumper;
    }

    public Object usePower(Object... args) {
        //Add Gast Fire Ball Sound
        getPlayer().getLevel().addLevelSoundEvent(getPlayer(), LevelSoundEventPacket.SOUND_LAVA_POP);
        getPlayer().getLevel().addSound(getPlayer(), Sound.MOB_GHAST_FIREBALL, 1.5f, 1);
        //Add Fire Particles to floor
        spawnParticles();
        //Or Maybe use Breaking Particle
        //Add knockback to Affected Entites
        Entity[] el = getEntitiesAround();
        for (Entity e : el) {
            if (e instanceof EntityLiving) {
                double deltaX = e.x - getPlayer().x;
                double deltaZ = e.z - getPlayer().z;
                ((EntityLiving) e).knockBack(getPlayer(), 1, deltaX, deltaZ, .8);
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "Fire Box";
    }


    private void spawnParticles() {
        ArrayList<Vector3> vv = getAffectedVectors(getPlayer());
        for (Vector3 v : vv) {
            getPlayer().getLevel().addParticle(new cn.nukkit.level.particle.FlameParticle(v.add(0, 1, 0)));
//            if (v.distance(getPlayer()) > getMaxSize() * 1.5) continue;
        }
    }

    private ArrayList<Vector3> getAffectedVectors(Vector3 v3) {
        ArrayList<Vector3> v = new ArrayList<>();
        for (int x = -getMaxSize(); x < getMaxSize(); x++) {
            for (int z = -getMaxSize(); z < getMaxSize(); z++) {
                v.add(getPlayer().getLevel().getSafeSpawn(v3.add(x, 0, z)));
//                getPlayer().getLevel().addParticle(new InkParticle(v));
            }
        }
        return v;
    }

    private Entity[] getEntitiesAround() {
        ArrayList<Entity> fl = new ArrayList<>();
        Entity[] e = getPlayer().getLevel().getNearbyEntities(new SimpleAxisAlignedBB(getPlayer().add(-getMaxSize(), -5, -getMaxSize()), getPlayer().add(getMaxSize(), 5, getMaxSize())));
        if (e.length > 0 && getPlayer().Faction != null) {//Player in faction and Entities around
            for (Entity ee : e) {
                if (ee == getPlayer()) continue;
                if (ee instanceof CorePlayer) {
                    CorePlayer p = (CorePlayer) ee;
                    if (p.Faction != null && p.getFaction().isAllied(p)) {//Target in faction and That faction is Allied
                        continue;
                    }
                }
                fl.add(ee);
            }
        }
        return (Entity[]) fl.toArray();
    }

    private int getMaxSize() {
        return getStage().getValue() + 3;
    }

    @Override
    public void onTick(int tick) {
        super.onTick(tick);
    }

}
