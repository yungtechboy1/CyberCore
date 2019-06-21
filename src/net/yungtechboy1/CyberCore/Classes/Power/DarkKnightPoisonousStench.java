package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.SpellParticle;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.BlockColor;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.Offense.DarkKnight;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.PowerAbilityHotBarAreaEffect;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public class DarkKnightPoisonousStench extends PowerAbilityHotBarAreaEffect {


    int s = 0;

    public DarkKnightPoisonousStench(DarkKnight bc) {
        super(bc, 100, LockedSlot.SLOT_9, 5);
        TickUpdate = 5;//1 Secs or .75 Secs
    }

    private Entity[] getEntitiesAround() {
        return getPlayer().getLevel().getNearbyEntities(new SimpleAxisAlignedBB(getPlayer().add(-getMaxSize(), -5, -getMaxSize()), getPlayer().add(getMaxSize(), 5, getMaxSize())));
    }

    private void spawnParticles() {
        ArrayList<Vector3> vv = getAffectedVectors();
        for (Vector3 v : vv) {
            getPlayer().getLevel().addParticle(new SpellParticle(v.add(0, 1, 0), BlockColor.TNT_BLOCK_COLOR));
        }
    }

    @Override
    public void onAbilityActivate() {
        spawnParticles();
        getPlayer().getLevel().addSound(getPlayer(), Sound.MOB_WITCH_THROW);
//        Entity[] es = getPlayer().getLevel().getNearbyEntities(new SimpleAxisAlignedBB(getPlayer().add(-getMaxSize(),-5,-getMaxSize()), getPlayer().add(getMaxSize(),5,getMaxSize()));
    }

    @Override
    public void whileAbilityActive() {
        spawnParticles();
        s++;
        if (s >= 4) {//Damage every 20 Ticks or Every 4th time whileAbilityActive is run
            getPlayer().sendMessage(TextFormat.GRAY+"POWER > Dealt 1 HP Damage to "+getEntitiesAround().length);
            for (Entity e : getEntitiesAround()) {
                if (e instanceof CorePlayer) {
                    CorePlayer cp = ((CorePlayer) e);
                    if (cp.getPlayerClass() != null) {
                        if (cp.getPlayerClass() instanceof DarkKnight) continue;//Knights are immune
                    }

                }
                if (e == getPlayer()) continue;//Same Player Skip!
                getPlayer().sendMessage(TextFormat.GRAY+"POWER > ENTITY NAME  "+e.getName());
                e.attack(new EntityDamageByEntityEvent(getPlayer(), e, EntityDamageEvent.DamageCause.MAGIC, 1f));
            }
        }
    }

    private ArrayList<Vector3> getAffectedVectors() {
        ArrayList<Vector3> v = new ArrayList<>();
        for (int x = -getMaxSize(); x < getMaxSize(); x++) {
            for (int z = -getMaxSize(); z < getMaxSize(); z++) {
                v.add(getPlayer().getLevel().getSafeSpawn(getActivatedLocation().add(x, 0, z)));
//                getPlayer().getLevel().addParticle(new InkParticle(v));
            }
        }
        return v;
    }

    @Override
    public int getRunTimeTick() {
        return 30 * 20;
    }

    private int getMaxSize() {
        return getStage().getValue() + 2;
    }

    @Override
    public void onAbilityDeActivate() {

    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.DarkKnightPosionousStench;
    }

    @Override
    public String getName() {
        return "Dark Knight Posionous Stench";
    }

}