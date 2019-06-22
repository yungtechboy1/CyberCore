package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.particle.ItemBreakParticle;
import cn.nukkit.level.particle.SpellParticle;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.BlockColor;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Passive.PassivePower;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBar;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

import java.util.ArrayList;

public class DragonJumper extends PassivePower {
    private boolean WaitingOnFall = false;

    public DragonJumper(BaseClass b) {
        super(b, 100);
        //TODO make this so that this power Runs Automatically!
        TickUpdate = 10;
    }



    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PlayerJumpEvent PlayerJumpEvent(PlayerJumpEvent e) {
        initPowerRun();
        return e;
//        return super.PlayerJumpEvent(e);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent e) {
        if (WaitingOnFall) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled();
                WaitingOnFall = false;
            }
        }
        return super.EntityDamageEvent(e);
    }

    @Override
    public int getPowerSuccessChance() {
        return (90/5)*getStage().getValue();
    }

    @Override
    protected int getCooldownTime() {
        switch (getStage()){
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
        BlockFace bf = getPlayer().getDirection();
//        Vector3 m = bf.getUnitVector();
        Vector3 mm = getPlayer().getMotion().add(0, 1, 0).multiply(1.5);
//        getPlayer().addMovement(mm.x,mm.y,mm.z,0,0,0);
//        getPlayer().addMotion(mm.x,mm.y,mm.z);
        getPlayer().setMotion(mm);
        getPlayer().sendMessage("Drangon Jumper Activated!!!!!!!!!!!!!!!!!" + mm);
        WaitingOnFall = true;
        return null;
    }

    private int getMaxSize() {
        return getStage().getValue() + 2;
    }

    private ArrayList<Vector3> getAffectedVectors() {
        ArrayList<Vector3> v = new ArrayList<>();
        for (int x = -getMaxSize(); x < getMaxSize(); x++) {
            for (int z = -getMaxSize(); z < getMaxSize(); z++) {
                v.add(getPlayer().getLevel().getSafeSpawn(getPlayer().add(x, 0, z)));
//                getPlayer().getLevel().addParticle(new InkParticle(v));
            }
        }
        return v;
    }

    @Override
    public String getName() {
        return "Dragon Jumper";
    }

    @Override
    public void onTick(int tick) {
        if (WaitingOnFall) {
            if (getPlayer().isOnGround() || getPlayer().isSwimming()) {
                WaitingOnFall = false;
            }
        }
        super.onTick(tick);
    }

    private Entity[] getEntitiesAround() {
        return getPlayer().getLevel().getNearbyEntities(new SimpleAxisAlignedBB(getPlayer().add(-getMaxSize(), -5, -getMaxSize()), getPlayer().add(getMaxSize(), 5, getMaxSize())));
    }

    private void spawnParticles() {
        ArrayList<Vector3> vv = getAffectedVectors();
        for (Vector3 v : vv) {
            getPlayer().getLevel().addParticle(new ItemBreakParticle(v.add(0, 1, 0), new ItemBlock(getPlayer().getLevel().getBlock(v.add(0,-1,0)))));
        }
    }
}
