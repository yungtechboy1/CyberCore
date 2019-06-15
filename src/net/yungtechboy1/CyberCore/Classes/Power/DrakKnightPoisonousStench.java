package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.command.defaults.ParticleCommand;
import cn.nukkit.level.particle.RedstoneParticle;
import cn.nukkit.math.Vector3;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerAbilityHotBar;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Manager.Econ.PlayerEconData;

public class DrakKnightPoisonousStench extends PowerAbilityHotBar {


    public DrakKnightPoisonousStench(BaseClass bc) {
        super(bc, 100, 150, LockedSlot.SLOT_9);
        TickUpdate = 20;//1 Secs
    }

    public int getGridSize() {
        return 5 + getStage().ordinal();
    }

    @Override
    public void onAbilityActivate() {
        //Only Add Particles! Damage happens only On Tick or WhileAbilityActive
        for (int x = -getGridSize(); x < getGridSize(); x++) {
            for (int z = -getGridSize(); z < getGridSize(); z++) {
                int y = 2;
                Vector3 v = PlayerClass.getPlayer().getLevel().getSafeSpawn(PlayerClass.getPlayer().add(x,y,z));
                PlayerClass.getPlayer().getLevel().addParticle(new RedstoneParticle(v,10));
            }
        }
    }

    @Override
    public void whileAbilityActive() {

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
