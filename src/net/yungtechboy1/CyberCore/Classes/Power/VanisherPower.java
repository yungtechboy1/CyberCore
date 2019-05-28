package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;

public class VanisherPower extends Power {
    public VanisherPower(CorePlayer p, int lvl) {
        this(100,lvl, p);
    }

    public VanisherPower(int psc, int lvl, CorePlayer p) {
        super(psc, lvl, p);
    }

    @Override
    public boolean isEvent() {
        return false;
    }

    @Override
    public int getCooldownTime() {
        switch (getStage()) {
            case STAGE_1:
            default:
                return 20*60;
            case STAGE_2:
                return 18*60;
            case STAGE_3:
                return 15*60;
            case STAGE_4:
                return 13*60;
            case STAGE_5:
                return 10*60;
        }
    }

    @Override
    public Effect getEffect() {
        Effect e = Effect.getEffect(Effect.INVISIBILITY);
        e.setDuration(getEffectDuration());
        e.setAmplifier(1);
        return e;
    }

    @Override
    public int getEffectDuration() {
        NukkitRandom nr = new NukkitRandom();
        switch (getStage()) {
            case STAGE_1:
            default:
                return nr.nextRange(25,35);
            case STAGE_2:
                return nr.nextRange(30,55);
            case STAGE_3:
                return nr.nextRange(45,65);
            case STAGE_4:
                return nr.nextRange(55,85);
            case STAGE_5:
                return nr.nextRange(60,120);
        }
    }

    @Override
    public PowerType getType() {
        return PowerType.Vanisher;
    }
}
