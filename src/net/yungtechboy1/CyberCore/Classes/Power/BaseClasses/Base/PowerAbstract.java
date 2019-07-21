package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import cn.nukkit.Server;
import cn.nukkit.event.Event;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityInventoryChangeEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.CoolDownTick;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.MainClassSettingsWindow;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

import java.util.concurrent.locks.Lock;

/**
 * Created by carlt on 5/16/2019.
 */
public abstract class PowerAbstract {

    public BaseClass PlayerClass = null;
    public int TickUpdate = -1;
    public CoolDownTick Cooldown = null;
    public boolean TakePowerOnFail = false;
    public boolean PlayerToggleable = true;
    public boolean CanSendCanNotRunMessage = true;
    public PowerSettings PS = null;
    public PowerType MainPowerType = PowerType.Regular;
    public PowerType SecondaryPowerType = PowerType.None;
    protected int DeActivatedTick = -1;
    LockedSlot LS = LockedSlot.NA;
    private LevelingType LT = LevelingType.None;
    private ClassLevelingManager LM = null;
    private boolean Active = false;
    private int PowerSuccessChance = 0;
    private int _lasttick = -1;
    private double PowerSourceCost = 0;
    private int DurationTick = -1;

    public PowerAbstract(BaseClass b, ClassLevelingManager lt, int psc) {
        this(b, lt, null, psc);
    }

    public PowerAbstract(BaseClass b, ClassLevelingManager lt, int psc, double cost) {
        this(b, lt, null, psc, cost);
    }

    public PowerAbstract(BaseClass b, ClassLevelingManager lt, PowerSettings ps, int psc) {
        this(b, lt, ps, psc, 5);
    }

    public PowerAbstract(BaseClass b, ClassLevelingManager lm, PowerSettings ps, int psc, double cost) {
        PowerSuccessChance = psc;
        PlayerClass = b;
        loadLevelManager(lm);
        if (ps != null) {
            PS = ps;
        } else
            b.getPlayer().getServer().getLogger().warning("POWER ABSTRACT ERROR! NO POWER SOURCE ASSIGNED FOR " + getName());
//        Level = lvl;
//        Level = b.getLVL();
        initStages();
        initAfterCreation();
        PowerSourceCost = cost;
    }

    public PowerSettings getPowerSettings() {
        if (PS == null)
            getPlayer().getServer().getLogger().error("POWER ABSTRACT ERROR! NO POWER SOURCE ASSIGNED FOR " + getName());
        return PS;
    }

    protected void setPowerSettings(boolean ability, boolean effect, boolean hotbar, boolean passive) {
        if (getPowerSettings() == null) PS = new PowerSettings();
        getPowerSettings().setAbility(ability);
        getPowerSettings().setEffect(effect);
        getPowerSettings().setHotbar(hotbar);
        getPowerSettings().setPassive(passive);
    }

    public final void activate() {
        if (isActive()) return;
        setActive(true);
        onAbilityActivate();
//        onActivate();
    }

    private void onAbilityActivate() {
    }

    public void setPowerAsAbility() {
        MainPowerType = PowerType.Ability;
        TickUpdate = 10;
    }

    public int getDeActivatedTick() {
        return DeActivatedTick;
    }

    public final void setDeActivatedTick(int deActivatedTick) {
        DeActivatedTick = deActivatedTick;
    }

    public void loadLevelManager(ClassLevelingManager lm) {
        if (lm == null) return;
        LM = lm;
        LT = LM.getType();
    }

    public int getDurationTick() {
        return DurationTick;
    }

    public void setDurationTick(int t) {
        DurationTick = t;
    }

    public ClassLevelingManager getLM() {
        return LM;
    }

    public LevelingType getLT() {
        return LT;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        if (active) {
            if (!hasPowerSettings()){
                System.out.println("====> CAN NOT ACTIVATE POWER NO POWER SETTINGS!!!");
                return;
            }
            if (getPowerSettings().isHotbar() && isLSNull()){
                System.out.println("====> CAN NOT ACTIVATE POWER NO HOT BAR SLOT IN SETTINGS!!!");
                return;
            }
        }
        Active = active;
        DeActivatedTick = Server.getInstance().getTick() + getRunTimeTick();
        PowerEnum pe = getType();
        if (PlayerClass.getClassSettings().getLearnedPowers().contains(pe) && active) {
            PlayerClass.getClassSettings().addActivePower(pe);
            onActivate();
        } else {
            getPlayer().sendMessage(TextFormat.RED + "ERROR > POWER > Could not activate " + getDispalyName() + TextFormat.RED + " Please make sure you have learned this power!");
        }
    }

    public boolean hasPowerSettings() {
        return getPowerSettings() != null;
    }

    public boolean isLSNull() {
        LockedSlot ls = getLS();
        if(ls == null)return true;
        return ls == LockedSlot.NA;
    }

    public int getRunTimeTick() {
        return getStage().getValue() * 20;//1-5 Secs
    }

    public void setActive() {
        setActive(true);
    }

    public StageEnum getStage() {
        if (LM != null) {
            return LM.getStage();
        }
        return StageEnum.STAGE_1;
    }

    public void onActivate() {

    }

    public LockedSlot getLS() {
        return LS;
    }

    public void setLS(LockedSlot LS) {
        this.LS = LS;
    }

    public double getPowerSourceCost() {
        return PowerSourceCost;
    }

    public void setPowerSourceCost(double powerSourceCost) {
        PowerSourceCost = powerSourceCost;
    }

    public int getPowerSuccessChance() {
        return PowerSuccessChance;
    }

    public void setPowerSuccessChance(int powerSuccessChance) {
        PowerSuccessChance = powerSuccessChance;
    }

    public int getDefaultPowerSuccessChance() {
        NukkitRandom nr = new NukkitRandom();
        int l = PlayerClass.getLVL();
        double f = ((-Math.sin(l / 90) * 13 + Math.sin(-50 + (l / 80))));
        return (int) Math.round(f * 100);
    }

    public void initAfterCreation() {

    }

    public CorePlayer getPlayer() {
        return PlayerClass.getPlayer();
    }

    //TODO IMPLEMENT
    public Event handelEvent(Event event) {
        if (event instanceof EntityDamageEvent)
            return EntityDamageEvent((EntityDamageEvent) event);
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

    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent e) {
        return e;
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

    /**
     * Time in Secs
     *
     * @return int Time in secs
     */
    protected int getCooldownTime() {
        return 60 * 3;//3 Mins
    }

    public final String getSafeName() {
        return getName().replaceAll(" ", "_");
    }

    public final int getCooldownTimeTick() {
        return getCooldownTime() * 20;
    }

    public void importConfig(ConfigSection cs) {
        if (LM != null) LM.importConfig(cs);
    }

    public ConfigSection exportConfig() {
        ConfigSection c = new ConfigSection();
        if (LM != null) c.put("LM", LM.exportConfig());
        return c;
    }

    public void initStages() {

    }

    public final void handleTick(int tick) {
//        System.out.println("PowerAbstract Call TICK");
        if (TickUpdate == -1) return;
//        System.out.println("PowerAbstract Call TICK 1");
        if (_lasttick + TickUpdate < tick) {
//            System.out.println("PowerAbstract Called THE ACTUAL TICK");
            onTick(tick);
            _lasttick = tick;
        }
    }

    public abstract PowerEnum getType();
//    {
//        CyberCoreMain.getInstance().getLogger().error("ERROR GETTING TYPE FROM POWER!!!!!");
//        return PowerEnum.Unknown;
//    }

    //USE TO RUN
    public final void initPowerRun(Object... args) {
        if (CanRun(false)) {
            PlayerClass.takePowerSourceCount(PowerSourceCost);
            usePower(args);
            afterPowerRun(args);
        } else {
            if (Cooldown != null && Cooldown.isValid()) {
                if (CanSendCanNotRunMessage) sendCanNotRunMessage();
            }
        }
    }

    public final void initForcePowerRun(Object... args) {
        PlayerClass.takePowerSourceCount(PowerSourceCost);
        usePower(args);
        afterPowerRun(args);
    }

    public void sendCanNotRunMessage() {
        getPlayer().sendMessage(TextFormat.RED + "Error! PowerAbstract " + getDispalyName() + TextFormat.RED + " still has a " + TextFormat.LIGHT_PURPLE + Cooldown.toString() + TextFormat.RED + " Cooldown.");
    }

    public void afterPowerRun(Object... args) {
        addCooldown();
        getPlayer().sendMessage(getSuccessUsageMessage());
    }

    public Effect getEffect() {
        return Effect.getEffect(Effect.FATAL_POISON);
    }

    public String getSuccessUsageMessage() {
        return TextFormat.GREEN + " > PowerAbstract " + getDispalyName() + TextFormat.GREEN + " has been activated!";
    }

    public Object usePower(Object... args) {
        if (MainPowerType == PowerType.Regular) {
        } else if (MainPowerType == PowerType.Ability) {
            activate();
        }
        return null;
    }

    public boolean CanRun(boolean force, Object... args) {
        if (force) return true;
        if (PlayerClass.getPowerSourceCount() < PowerSourceCost) {
            getPlayer().sendMessage(TextFormat.RED + "Not enough " + PlayerClass.getPowerSourceType().name() + " Energy!");
            return false;
        }
        NukkitRandom nr = new NukkitRandom();
        if (nr.nextRange(0, 100) <= PowerSuccessChance) {
            //Success
            if (Cooldown != null && Cooldown.isValid()) return false;

        } else return false;//Fail
        if (MainPowerType == PowerType.Regular) {
            return true;
        } else if (MainPowerType == PowerType.Ability) {
            return !isActive();
        }
        return false;
    }

    public boolean isAbility() {
        return getPowerSettings().isAbility();
    }

    public void onTick(int tick) {
        if (isAbility()) {
            //Only For Deactivation
            System.out.println("POWER TICKKKKKK2");
            if (isActive()) {
                System.out.println("POWER TICKKKKKK3");
                whileAbilityActive();
                if (tick >= DeActivatedTick) {
                    System.out.println("POWER TICKKKKKK44444444444444444444444444444");
                    setActive(false);
                    DeActivatedTick = -1;
                    onAbilityDeActivate();
                }
            }
        }
    }

    public void onAbilityDeActivate() {

    }

    public void whileAbilityActive() {

    }

    public CoolDownTick addCooldown() {
        return addCooldown(getCooldownTime());
    }

    public CoolDownTick addCooldown(int secs) {
        Cooldown = new CoolDownTick(getType().name(), secs * 20);
        return Cooldown;
    }

    public abstract String getName();

    public String getDispalyName() {
        return getName();
    }

    /**
     * Button Callback to add a Button to the Window!
     *
     * @param mainClassSettingsWindow
     */
    public void addButton(MainClassSettingsWindow mainClassSettingsWindow) {
//        if()
    }

    public enum LevelingType {
        None,
        XPLevel,
        Stage
    }

    public enum StageEnum {
        NA,
        STAGE_1,
        STAGE_2,
        STAGE_3,
        STAGE_4,
        STAGE_5,
        STAGE_6,
        STAGE_7,
        STAGE_8,
        STAGE_9,
        STAGE_10;

        public static StageEnum getStageFromInt(int i) {
            if (i < values().length) {
                return values()[i];
            }
            return NA;
        }

        public int getValue() {
            return ordinal();
        }

        public String getDisplayName() {
            return name();
        }

        public String getDisplayName2() {
            return name();
        }
    }
}
