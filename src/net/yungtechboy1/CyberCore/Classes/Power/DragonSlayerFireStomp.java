package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFire;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.block.BlockIgniteEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.particle.ItemBreakParticle;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import net.yungtechboy1.CyberCore.Classes.New.Offense.DragonSlayer;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBar;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockFire;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Events.Custom.PluginBlockSetEvent;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

import java.util.ArrayList;

public class DragonSlayerFireStomp extends PowerHotBar {
    private boolean WaitingOnFall = false;

    public DragonSlayerFireStomp(DragonSlayer b) {
        super(b, 100, 1, LockedSlot.SLOT_8);
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

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent e) {
        System.out.println("BNOOOOOO");
        if (WaitingOnFall) {
            System.out.println("BNOOOOOO"+e.getCause());
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled();
                spawnParticles();
                WaitingOnFall = false;
            }
        }
        return super.EntityDamageEvent(e);
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
        if (WaitingOnFall && ((getPlayer().isOnGround()) || getPlayer().getLevel().getBlock(getPlayer().down()).getId() != 0)|| getPlayer().isSwimming()) {
            System.out.println("CLEANNNNNNNNNNNNNINGNNNGGG");
            if (getPlayer().isSwimming()) {
//                WaitingOnFall =false;
            } else if (getPlayer().isOnGround()) {
//                spawnParticles();
            }
            WaitingOnFall = false;
        }
        super.onTick(tick);
    }

    private Entity[] getEntitiesAround() {
        return getPlayer().getLevel().getNearbyEntities(new SimpleAxisAlignedBB(getPlayer().add(-getMaxSize(), -5, -getMaxSize()), getPlayer().add(getMaxSize(), 5, getMaxSize())));
    }

    private void onFall() {

    }

    private void spawnParticles() {
        ArrayList<Vector3> vv = getAffectedVectors();
        for (Vector3 v : vv) {
            Block f = new BlockFire();
            f.setComponents(v.x,v.y,v.z);
            f.setLevel(getPlayer().getLevel());
            getPlayer().getLevel().addParticle(new ItemBreakParticle(v.add(0, 1, 0), new ItemBlock(getPlayer().getLevel().getBlock(v.add(0, -1, 0)))));
            BlockIgniteEvent e = new BlockIgniteEvent(f, null, getPlayer(), BlockIgniteEvent.BlockIgniteCause.FLINT_AND_STEEL);
            if(v.distance(getPlayer()) > 10)continue;
            getPlayer().getServer().getPluginManager().callEvent(e);
//            CyberCoreMain.getInstance().HandleCyberEvent(e);
            if (!e.isCancelled()) {
                getPlayer().getLevel().setBlock(v,new CustomBlockFire(),true,true);
//                getPlayer().getLevel().setBlockDataAt(v,new BlockFire(),true,true);
            }
        }
    }
}
