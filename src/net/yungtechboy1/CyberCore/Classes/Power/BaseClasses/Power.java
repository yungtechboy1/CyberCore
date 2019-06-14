package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.event.Event;
import cn.nukkit.event.entity.EntityInventoryChangeEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CoolDownTick;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

/**
 * Created by carlt on 5/16/2019.
 */
public abstract class Power {
    public BaseClass PlayerClass = null;
    public int TickUpdate = -1;
    public CoolDownTick Cooldown = null;
    public boolean PlayerToggleable = true;
    int Level = 0;
    private int PowerSuccessChance = 100;
    private int _lasttick = -1;

    public double getPowerSourceCost() {
        return PowerSourceCost;
    }

    public void setPowerSourceCost(double powerSourceCost) {
        PowerSourceCost = powerSourceCost;
    }

    private double PowerSourceCost = 0;

    public Power(BaseClass b, int psc) {
        this(b,psc,0);
    }
    public Power(BaseClass b, int psc, double cost) {
        PowerSuccessChance = psc;
        PlayerClass = b;
//        Level = lvl;
        Level = b.getLVL();
        initStages();
        initAfterCreation();
        PowerSourceCost = cost;
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
    public Event handelEvent(Event event) {
        if (event instanceof CustomEntityDamageByEntityEvent)
            return CustomEntityDamageByEntityEvent((CustomEntityDamageByEntityEvent) event);
        if (event instanceof PlayerJumpEvent)
            return PlayerJumpEvent((PlayerJumpEvent) event);
        if (event instanceof InventoryTransactionEvent)
            return InventoryTransactionEvent((InventoryTransactionEvent) event);
        if (event instanceof InventoryClickEvent)
            return InventoryClickEvent((InventoryClickEvent) event);
        if (event instanceof EntityInventoryChangeEvent) {
            return EntityInventoryChangeEvent((EntityInventoryChangeEvent) event);
        }
        return event;
    }


    public InventoryClickEvent InventoryClickEvent(InventoryClickEvent e) {
        return e;
    }
    public InventoryTransactionEvent InventoryTransactionEvent(InventoryTransactionEvent e) {
        return e;
    }
    public EntityInventoryChangeEvent EntityInventoryChangeEvent(EntityInventoryChangeEvent e) {
        return e;
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
        return 60 * 3;//3 Mins
    }

    public final String getSafeName() {
        return getName().replaceAll(" ", "_");
    }

    public final int getCooldownTimeTick() {
        return getCooldownTime() * 20;
    }

    public void initStages() {

    }

    public final void handleTick(int tick) {
//        System.out.println("Power Call TICK");
        if (TickUpdate == -1) return;
//        System.out.println("Power Call TICK 1");
        if (_lasttick + TickUpdate < tick) {
//            System.out.println("Power Called THE ACTUAL TICK");
            onTick(tick);
            _lasttick = tick;
        }
    }

    public abstract PowerEnum getType();

    //USE TO RUN
    public final void initPowerRun(Object... args) {
        if (CanRun(false)) {
            PlayerClass.takePowerSourceCount(PowerSourceCost);
            usePower(args);
            afterPowerRun(args);
        }else{
            if(Cooldown != null && Cooldown.isValid()){
                getPlayer().sendMessage(TextFormat.RED+"Error! Power "+getDispalyName()+TextFormat.RED+" still has a "+TextFormat.LIGHT_PURPLE+Cooldown.toString()+TextFormat.RED+" Cooldown.");
            }
        }
    }

    public void afterPowerRun(Object... args) {
        addCooldown();
        getPlayer().sendMessage(getSuccessUsageMessage());
    }

    public String getSuccessUsageMessage(){
        return TextFormat.GREEN+ " > Power "+getDispalyName()+TextFormat.GREEN+" has been activated!";
    }

    public Object usePower(Object... args) {
        return null;
    }

    public boolean CanRun(boolean force, Object... args) {
        if (force) return true;
        if(PlayerClass.getPowerSourceCount() < PowerSourceCost){
            getPlayer().sendMessage(TextFormat.RED+"Not enough "+PlayerClass.getPowerSourceType().name()+" Energy!");
            return false;
        }
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

    public CoolDownTick addCooldown() {
        return addCooldown(getCooldownTime());
    }

    public CoolDownTick addCooldown(int secs) {
        Cooldown = new CoolDownTick(getType().name(),secs*20);
        return Cooldown;
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