package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.math.NukkitRandom;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

public class RaidRagePower extends PowerEvent {
    public RaidRagePower(int lvl, CorePlayer p) {
        super(lvl, p);
    }

    public RaidRagePower(int psc, int lvl, CorePlayer p) {
        super(psc, lvl, p);
    }

    @Override
    public int getCooldownTime() {
        switch (getStage()) {
            case STAGE_1:
            default:
                return 45*60;
            case STAGE_2:
                return 40*60;
            case STAGE_3:
                return 25*60;
            case STAGE_4:
                return 20*60;
            case STAGE_5:
                return 15*60;
        }
    }

    @Override
    public int getEffectDuration() {
        NukkitRandom nr = new NukkitRandom();
        switch (getStage()) {
            case STAGE_1:
            default:
                return nr.nextRange(25,35)+20;
            case STAGE_2:
                return nr.nextRange(30,55)+30;
            case STAGE_3:
                return nr.nextRange(45,65)+40;
            case STAGE_4:
                return nr.nextRange(55,85)+50;
            case STAGE_5:
                return nr.nextRange(60,120)+60;
        }
    }

    @Override
    public PowerType getType() {
        return PowerType.RaidRage;
    }

    @Override
    public EntityDamageByEntityEvent EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof cn.nukkit.Player) {
            float bd = event.getDamage(EntityDamageEvent.DamageModifier.BASE) * .90f;
            event.setDamage(bd, EntityDamageEvent.DamageModifier.BASE);

        }
        return event;
    }
}
