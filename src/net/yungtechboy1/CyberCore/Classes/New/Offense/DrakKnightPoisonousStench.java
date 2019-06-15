package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.BubbleParticle;
import cn.nukkit.level.particle.InkParticle;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlaySoundPacket;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.PowerAbility;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.PowerAbilityAreaEffect;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

import java.util.ArrayList;

public class DrakKnightPoisonousStench extends PowerAbilityAreaEffect {


    public DrakKnightPoisonousStench(BaseClass bc) {
        super(bc, 100,50);
        TickUpdate = 20;//1 Secs
    }

    private Entity[] getEntitiesAround(){
        return getPlayer().getLevel().getNearbyEntities(new SimpleAxisAlignedBB(getPlayer().add(-getMaxSize(),-5,-getMaxSize()), getPlayer().add(getMaxSize(),5,getMaxSize())));
    }

    private void spawnParticles(){
        ArrayList<Vector3> vv = getAffectedVectors();
        for (Vector3 v : vv) {
            getPlayer().getLevel().addParticle(new InkParticle(v));
        }
    }

    @Override
    public void onAbilityActivate() {
        spawnParticles();
        getPlayer().getLevel().addSound(getPlayer(),Sound.MOB_WITCH_THROW);
//        Entity[] es = getPlayer().getLevel().getNearbyEntities(new SimpleAxisAlignedBB(getPlayer().add(-getMaxSize(),-5,-getMaxSize()), getPlayer().add(getMaxSize(),5,getMaxSize()));
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
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

}
