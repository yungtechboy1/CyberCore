package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.PlayerFood;
import cn.nukkit.event.Event;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerToggleSprintEvent;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

/**
 * Created by carlt on 5/16/2019.
 */
public abstract class Power {
    public BaseClass PlayerClass = null;
    public Effect PotionEffect = null;
    public int TickUpdate = -1;
    public CoolDown Cooldown = null;

    public int getPowerSuccessChance() {
        return PowerSuccessChance;
    }

    private int PowerSuccessChance = 100;
    public boolean PlayerToggleable = true;
    int Level = 0;
    int DurationTicks = 0;
    public Power(BaseClass b,int psc, int lvl) {
        PowerSuccessChance = psc;
        PlayerClass = b;
        Level = lvl;
        initStages();
        initAfterCreation();
    }
    public void initAfterCreation(){

    }

    public CorePlayer getPlayer(){
        return PlayerClass.getPlayer();
    }

    //TODO IMPLEMENT
    public Event HandelEvent(Event event) {
        if (event instanceof CustomEntityDamageByEntityEvent) return CustomEntityDamageByEntityEvent((CustomEntityDamageByEntityEvent) event);
        return event;
    }

    public abstract CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e);

    public void setPowerSuccessChance(int powerSuccessChance) {
        PowerSuccessChance = powerSuccessChance;
    }

    protected int getCooldownTime() {
        return 60 * 15;//15 Mins
    }

    public final int getCooldownTimeTick() {
        return getCooldownTime() * 20;
    }

    public void initStages() {

    }

    private int _lasttick = -1;
    public final void handleTick(int tick){
        if(TickUpdate == -1)return;
        if(_lasttick + TickUpdate < tick){
            onTick(tick);
            _lasttick = tick;
        }
    }

    public abstract PowerEnum getType();

    //USE TO RUN
    public void InitPowerRun(Object... args){
        if(CanRun()){
            usePower(PlayerClass.getPlayer(),args);
        }
    }

    public boolean CanRun(Object... args) {
        return CanRun(false,args);
    }

    @Deprecated
    public Object usePower(Object... args) {
        return usePower(null, args);
    }

    public abstract Object usePower(CorePlayer cp, Object... args);

    public boolean CanRun(boolean force, Object... args) {
        if(force)return true;
        NukkitRandom nr = new NukkitRandom();
        if (nr.nextRange(0, 100) <= PowerSuccessChance) {
            //Success
            if(Cooldown != null)return !Cooldown.isValid();
            return true;
        }
        return false;
    }

    public void onTick(int tick){

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

    public CoolDown addCooldown() {
        return addCooldown(getCooldownTime());
    }

    public CoolDown addCooldown(int secs) {
        CoolDown c = new CoolDown();
        c.setTimeSecs(secs);
        return c;
    }

    public abstract String getName();
    public String getDispalyName(){
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
            if(i < values().length){
                return values()[i];
            }
            return null;
        }

        public int getValue(){
            return ordinal()+1;
        }
    }
}
