package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.event.Event;
import cn.nukkit.math.NukkitRandom;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

/**
 * Created by carlt on 5/16/2019.
 */
public abstract class Power {
    public BaseClass PlayerClass = null;
    public int TickUpdate = -1;
    public CoolDown Cooldown = null;
    public boolean PlayerToggleable = true;
    int Level = 0;
    private int PowerSuccessChance = 100;
    private int _lasttick = -1;

    public Power(BaseClass b, int psc) {
        PowerSuccessChance = psc;
        PlayerClass = b;
//        Level = lvl;
        Level = b.getLVL();
        initStages();
        initAfterCreation();
    }

    public int getPowerSuccessChance() {
        return PowerSuccessChance;
    }

    public void setPowerSuccessChance(int powerSuccessChance) {
        PowerSuccessChance = powerSuccessChance;
    }

    public void initAfterCreation() {

    }

    public CorePlayer getPlayer() {
        return PlayerClass.getPlayer();
    }

    //TODO IMPLEMENT
    public Event HandelEvent(Event event) {
        if (event instanceof CustomEntityDamageByEntityEvent)
            return CustomEntityDamageByEntityEvent((CustomEntityDamageByEntityEvent) event);
        if (event instanceof PlayerJumpEvent)
            return PlayerJumpEvent((PlayerJumpEvent) event);
        return event;
    }


    public PlayerJumpEvent PlayerJumpEvent(PlayerJumpEvent e) {
        return e;
    }

    /**
     * ALWAYS RETURN THE EVENT
     *
     * @param e
     * @return e Event
     */
    public abstract CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e);

    protected int getCooldownTime() {
        return 60 * 15;//15 Mins
    }

    public final int getCooldownTimeTick() {
        return getCooldownTime() * 20;
    }

    public void initStages() {

    }

    public final void handleTick(int tick) {
        System.out.println("Power Call TICK");
        if (TickUpdate == -1) return;
        System.out.println("Power Call TICK 1");
        if (_lasttick + TickUpdate < tick) {
            System.out.println("Power Called THE ACTUAL TICK");
            onTick(tick);
            _lasttick = tick;
        }
    }

    public abstract PowerEnum getType();

    //USE TO RUN
    public void InitPowerRun(Object... args) {
        if (CanRun()) {
            usePower(PlayerClass.getPlayer(), args);
        }
    }

    public boolean CanRun(Object... args) {
        return CanRun(false, args);
    }

    @Deprecated
    public Object usePower(Object... args) {
        return usePower(null, args);
    }

    public abstract Object usePower(CorePlayer cp, Object... args);

    public boolean CanRun(boolean force, Object... args) {
        if (force) return true;
        NukkitRandom nr = new NukkitRandom();
        if (nr.nextRange(0, 100) <= PowerSuccessChance) {
            //Success
            if (Cooldown != null) return !Cooldown.isValid();
            return true;
        }
        return false;
    }

    public void onTick(int tick) {

    }


    public Stage getStage() {
        return Stage.getStageFromInt((int) Math.floor(Level / 20));
    }

    public CoolDown addCooldown() {
        return addCooldown(getCooldownTime());
    }

    public CoolDown addCooldown(int secs) {
        CoolDown c = new CoolDown();
        c.setTimeSecs(secs);
        return c;
    }

    public abstract String getName();

    public String getDispalyName() {
        return getName();
    }

    public enum Stage {
        NA,
        STAGE_1,
        STAGE_2,
        STAGE_3,
        STAGE_4,
        STAGE_5;

        public static Stage getStageFromInt(int i) {
            if (i < values().length) {
                return values()[i];
            }
            return null;
        }

        public int getValue() {
            return ordinal() + 1;
        }
    }
}
