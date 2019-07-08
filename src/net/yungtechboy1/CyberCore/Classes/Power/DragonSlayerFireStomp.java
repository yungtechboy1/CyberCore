package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.block.BlockIgniteEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.particle.ItemBreakParticle;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import net.yungtechboy1.CyberCore.Classes.New.Offense.DragonSlayer;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockFire;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DragonSlayerFireStomp extends PowerHotBar {
    private boolean WaitingOnFall = false;
    private int WaitingOnFallTick = -1;
    private int StartNullFire = -1;

    public DragonSlayerFireStomp(DragonSlayer b) {
        super(b, null, 100, 1, LockedSlot.SLOT_9);
        TickUpdate = 10;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PlayerJumpEvent PlayerJumpEvent(PlayerJumpEvent e) {
//        initPowerRun();
        return super.PlayerJumpEvent(e);
    }

    private void CheckGrace() {
        if (WaitingOnFall && WaitingOnFallTick != -1) {
            if (WaitingOnFallTick + GraceTicks() < getPlayer().getServer().getTick()) {
                //Grace Period Expired
                WaitingOnFallTick = -1;
                WaitingOnFall = false;
            }
        }
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent e) {
        System.out.println("BNOOOOOO"+ e.getCause());
        if (WaitingOnFall) {
            System.out.println("BNOOOOOO!1111" + e.getCause());
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled();
                spawnParticles();
                WaitingOnFall = false;
                WaitingOnFallTick = -1;
                return e;
            } else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                e.setCancelled();
                getPlayer().getLevel().addLevelSoundEvent(getPlayer(), LevelSoundEventPacket.SOUND_EXTINGUISH_FIRE);
                getPlayer().fireTicks = 0;
                StartNullFire = -1;
                return e;
            }
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            System.out.println("Were HeRE >> "+StartNullFire+"||"+StartNullFire + GraceTicks()+"||"+getPlayer().getServer().getTick());
            if (StartNullFire != -1 && StartNullFire + GraceTicks() > getPlayer().getServer().getTick()) {
                e.setCancelled();
                getPlayer().getLevel().addLevelSoundEvent(getPlayer(), LevelSoundEventPacket.SOUND_EXTINGUISH_FIRE);
                getPlayer().fireTicks = 0;
                StartNullFire = -1;

            }
        }
        return super.EntityDamageEvent(e);
    }

    public int GraceTicks() {
        return 7 * 20;
    }

    @Override
    protected int getCooldownTime() {
        return 15;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.FireStomp;
    }

    public Object usePower(Object... args) {
        BlockFace bf = getPlayer().getDirection();
        Vector3 m = bf.getUnitVector();
        Vector3 mm = new Vector3(0, 1, 0).multiply(3).add(m);
//        getPlayer().addMovement(mm.x,mm.y,mm.z,0,0,0);
//        getPlayer().addMotion(mm.x,mm.y,mm.z);
        getPlayer().setMotion(mm);
        getPlayer().sendMessage("Fire Stomp Activated!!!!!!!!!!!!!!!!!" + mm);
        WaitingOnFall = true;
        WaitingOnFallTick = getPlayer().getServer().getTick();
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
        return "Fire Stomp";
    }

    @Override
    public void onTick(int tick) {
        if (WaitingOnFall && ((getPlayer().isOnGround()) || getPlayer().getLevel().getBlock(getPlayer().down()).getId() != 0) || getPlayer().isSwimming()) {
            System.out.println("CLEANNNNNNNNNNNNNINGNNNGGG");
            if (getPlayer().isSwimming() || getPlayer().getLevel().getBlock(getPlayer().down()).getId() == BlockID.WATER || getPlayer().getLevel().getBlock(getPlayer().down()).getId() == BlockID.STILL_WATER) {
                //In Water Cancels the Fire!
                WaitingOnFall = false;
                getPlayer().getLevel().addLevelSoundEvent(getPlayer(), LevelSoundEventPacket.SOUND_BREAK);
            } else if (getPlayer().isOnGround() || getPlayer().getLevel().getBlock(getPlayer().down()).getId() != BlockID.AIR) {
                spawnParticles();
            }
            WaitingOnFall = false;
            WaitingOnFallTick = -1;
        }
        super.onTick(tick);
    }

    private Entity[] getEntitiesAround() {
        return getPlayer().getLevel().getNearbyEntities(new SimpleAxisAlignedBB(getPlayer().add(-getMaxSize(), -5, -getMaxSize()), getPlayer().add(getMaxSize(), 5, getMaxSize())));
    }

    private void onFall() {

    }

    private void spawnParticles() {
        StartNullFire = getPlayer().getServer().getTick();
        ArrayList<Vector3> vv = getAffectedVectors();
        for (Vector3 v : vv) {
            Block f = new CustomBlockFire();
            f.setComponents(v.x, v.y, v.z);
            f.setLevel(getPlayer().getLevel());
            getPlayer().getLevel().addParticle(new ItemBreakParticle(v.add(0, 1, 0), new ItemBlock(getPlayer().getLevel().getBlock(v.add(0, -1, 0)))));
            BlockIgniteEvent e = new BlockIgniteEvent(f, null, getPlayer(), BlockIgniteEvent.BlockIgniteCause.FLINT_AND_STEEL);
            if (v.distance(getPlayer()) > getMaxSize() * 1.5) continue;
            getPlayer().getServer().getPluginManager().callEvent(e);
//            CyberCoreMain.getInstance().HandleCyberEvent(e);
            if (!e.isCancelled()) {
                getPlayer().getLevel().setBlock(v, f, true, true);
                getPlayer().getLevel().scheduleUpdate(f, f.tickRate() + ThreadLocalRandom.current().nextInt(10));
//                getPlayer().getLevel().setBlockDataAt(v,new BlockFire(),true,true);
            }
        }
    }
}
