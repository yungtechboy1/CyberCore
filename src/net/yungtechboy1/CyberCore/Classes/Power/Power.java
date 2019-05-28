package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;

/**
 * Created by carlt on 5/16/2019.
 */
public abstract class Power {
    public final static int TNT_Specialist = 0;
    public final static int MineLife = 1;
    public final static int OreKnowledge = 2;
    public Effect PotionEffect = null;
    public CoolDown Cooldown = null;
    int PowerSuccessChance = 100;
    int Level = 0;
    int DurationTicks = 0;
    CorePlayer Player = null;
    private boolean MasterToggle = false;

    public Power(int lvl, CorePlayer p) {
        this(100, lvl, p);
    }

    public Power(int psc, int lvl, CorePlayer p) {
        PowerSuccessChance = psc;
        Level = lvl;
        Player = p;
        initStages();
    }

    protected boolean isEvent() {
        return false;
    }

    public void setPowerSuccessChance(int powerSuccessChance) {
        PowerSuccessChance = powerSuccessChance;
    }

    public int getCooldownTime() {
        return 60 * 15;//15 Mins
    }

    public int getCooldownTimeTick() {
        return getCooldownTime() * 20;
    }

    public void initStages() {

    }


    public boolean getMasterToggle() {
        return MasterToggle;
    }

    public void setMasterToggle(boolean masterToggle) {
        MasterToggle = masterToggle;
    }

    public void setMasterToggle() {
        setMasterToggle(true);
    }

    public abstract PowerType getType();

    public boolean CanRun() {
        return CanRun(false);
    }

    public Object usePower(CorePlayer cp, Object... args) {
        if (cp == null) return null;
        if (getEffect() != null) {
            //Send Effect
            getEffect().applyEffect(cp);
        }
        addCooldown();
        return null;
    }

    public void StartPower() {
        StartPower(Player);
    }

    public void StartPower(CorePlayer cp) {
        if (CanRun()) {
            usePower(cp);
        }
    }

    public boolean CanRun(boolean force) {
        NukkitRandom nr = new NukkitRandom();
        if (nr.nextRange(0, 100) <= PowerSuccessChance || force) {
            //Success
            if (Cooldown != null && !force) return !Cooldown.isValid();
            return true;
        }
        return false;
    }

    public Effect getEffect() {
        return PotionEffect;
    }

    public void setDurationTicks(int d) {
        DurationTicks = d;
    }

    public int getEffectDuration() {
        return DurationTicks;
    }

    public Stage getStage() {
        return Stage.getStageFromInt((int) Math.floor(Level / 20));
    }

    public void addCooldown(CoolDown c) {
        if (!c.isValid()) return;
        Cooldown = c;
    }

    @Deprecated
    public void addCooldown() {
        addCooldown(getCooldown());
    }

    public CoolDown getCooldown() {
        return getCooldown(getCooldownTime());
    }

    public CoolDown getCooldown(int secs) {
        CoolDown c = new CoolDown();
        c.setTimeSecs(secs);
        return c;
    }

    public enum PowerType {
        Unknown,
        FactionDamager,
        Vanisher, RaidRage, MineLife, TNTSpecialistPower,

    }

    public enum Stage {
        STAGE_1,
        STAGE_2,
        STAGE_3,
        STAGE_4,
        STAGE_5;

        public static Stage getStageFromInt(int i) {
            if (i == 0) return STAGE_1;
            if (i == 1) return STAGE_2;
            if (i == 2) return STAGE_3;
            if (i == 3) return STAGE_4;
            if (i == 4) return STAGE_5;
            if (i == 5) return STAGE_1;
            return STAGE_1;
        }
    }
}
