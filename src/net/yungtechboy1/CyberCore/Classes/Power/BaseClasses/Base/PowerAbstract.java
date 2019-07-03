package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import cn.nukkit.event.Event;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityInventoryChangeEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBarInt;
import net.yungtechboy1.CyberCore.CoolDownTick;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.MainClassSettingsWindow;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

import java.util.HashMap;

/**
 * Created by carlt on 5/16/2019.
 */
public abstract class PowerAbstract implements PowerInt {

    public BaseClass PlayerClass = null;
    public int TickUpdate = -1;
    public CoolDownTick Cooldown = null;
    public boolean PlayerToggleable = true;
    public boolean CanSendCanNotRunMessage = true;
    LockedSlot LS = LockedSlot.NA;
    private LevelingType LT = LevelingType.None;
    private boolean Active = false;
    private int PowerSuccessChance = 100;
    private int _lasttick = -1;
    private double PowerSourceCost = 0;
    public PowerAbstract(BaseClass b, LevelingType lt, int psc) {
        this(b, lt, psc, 0);
    }

    public PowerAbstract(BaseClass b, LevelingType lt, int psc, double cost) {
        PowerSuccessChance = psc;
        PlayerClass = b;
        LT = lt;
//        Level = lvl;
//        Level = b.getLVL();
        initStages();
        initAfterCreation();
        PowerSourceCost = cost;
    }

    public LevelingType getLT() {
        return LT;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
        PowerEnum pe = getType();
        if (PlayerClass.getClassSettings().getLearnedPowers().contains(pe)) {
            PowerAbstract p = this;
            PlayerClass.getClassSettings().addActivePower(pe);
            onActivate();
        } else {
            getPlayer().sendMessage(TextFormat.RED + "ERROR > POWER > Could not activate " + getDispalyName() + TextFormat.RED + " Please make sure you have learned this power!");
        }
    }

    public void setActive() {
        setActive(true);
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
    }

    public ConfigSection exportConfig() {
        return new ConfigSection();
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

    public abstract PowerEnum getType();// {
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

    public void sendCanNotRunMessage() {
        getPlayer().sendMessage(TextFormat.RED + "Error! PowerAbstract " + getDispalyName() + TextFormat.RED + " still has a " + TextFormat.LIGHT_PURPLE + Cooldown.toString() + TextFormat.RED + " Cooldown.");
    }

    public void afterPowerRun(Object... args) {
        addCooldown();
        getPlayer().sendMessage(getSuccessUsageMessage());
    }

    public String getSuccessUsageMessage() {
        return TextFormat.GREEN + " > PowerAbstract " + getDispalyName() + TextFormat.GREEN + " has been activated!";
    }

    public abstract Object usePower(Object... args);

    public boolean CanRun(boolean force, Object... args) {
        if (force) return true;
        if (PlayerClass.getPowerSourceCount() < PowerSourceCost) {
            getPlayer().sendMessage(TextFormat.RED + "Not enough " + PlayerClass.getPowerSourceType().name() + " Energy!");
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
    }


    public interface StagePowerAbilityHotBar extends PowerHotBarInt {
        
//        public StagePowerAbilityHotBar(BaseClass b, int psc, double cost, LockedSlot ls) {
//            super(b, psc, cost);
//            TickUpdate = 20;
//            setLS(ls);
//            PowerHotBarInt.RemoveAnyItemsInSlot(getPlayer(),ls);
//        }

//    @Override
//    public InventoryTransactionEvent InventoryTransactionEvent(InventoryTransactionEvent e) {
//        if(getLS() == LockedSlot.NA)return e;
//        for (InventoryAction action : e.getTransaction().getActions()) {
//            if (!(action instanceof SlotChangeAction)) {
//                continue;
//            }
//            SlotChangeAction slotChange = (SlotChangeAction) action;
//
//            if (slotChange.getInventory() instanceof PlayerInventory) {
////                who = (Player) slotChange.getInventory().getHolder();
//                //Check to see if Slot is fucked with
//                if (slotChange.getSlot() == getLS().getSlot()) {
//                    e.setCancelled();
//                    getPlayer().sendMessage(TextFormat.RED + "Error! You can not change your Class Slot!");
//                }
//            }
//        }
//
//        return super.InventoryTransactionEvent(e);
//    }


        boolean check = false;
        @Override
        void onTick(int tick) {
            if(getLS() == LockedSlot.NA)return;
            if (Cooldown == null || !Cooldown.isValid()) {
                setPowerAvailable(this);
                System.out.println("ACTIVE POWER");
            } else {
                System.out.println("UNNNNNNNNACTIVE POWER");
                setPowerUnAvailable(this);
            }
            check = !check;
            if (check) antiSpamCheck(this);
            super.onTick(tick);
        }




    }


    public abstract class StagePowerAbstract extends PowerAbstract {

        public HashMap<StageEnum, String> CustomSageName = new HashMap<>();
        private StageEnum MaxStage = StageEnum.STAGE_10;
        private StageEnum Stage = StageEnum.NA;
        public StagePowerAbstract(BaseClass b, int psc) {
            super(b, LevelingType.Stage, psc);
        }

        public StagePowerAbstract(BaseClass b, int psc, double cost) {
            super(b, LevelingType.Stage, psc, cost);
        }

        public StageEnum getMaxStage() {
            return MaxStage;
        }

        public void setMaxStage(StageEnum maxStage) {
            MaxStage = maxStage;
        }

        public StageEnum getStage() {
//        if (getLT() == LevelingType.Stage) return StageEnum.getStageFromInt(Stage);
            return Stage;
        }

        public void setStage(StageEnum s) {
            if (s.ordinal() > MaxStage.ordinal()) s = MaxStage;
            Stage = s;
        }

        @Override
        public ConfigSection exportConfig() {
            ConfigSection c = super.exportConfig();
            c.put("Stage", getStage().ordinal());
            return c;
        }

        @Override
        public void importConfig(ConfigSection cs) {
            super.importConfig(cs);
            if (cs.containsKey("Stage")) setStage(StageEnum.getStageFromInt(cs.getInt("Stage")));
        }
    }


    public abstract class XPLevelingPowerAbstract extends PowerAbstract {

        private int XP = 0;
        private int Stage = 0;
        private int MaxLevel = 100;

        public XPLevelingPowerAbstract(BaseClass b, int psc) {
            super(b, LevelingType.XPLevel, psc);
        }

        public XPLevelingPowerAbstract(BaseClass b, int psc, double cost) {
            super(b, LevelingType.XPLevel, psc, cost);
        }

        public int getMaxLevel() {
            return MaxLevel;
        }

        public void setMaxLevel(int maxLevel) {
            MaxLevel = maxLevel;
        }

        public StageEnum getStage() {
//        if (getLT() == LevelingType.Stage) return StageEnum.getStageFromInt(Stage);
            return StageEnum.getStageFromInt(1 + ((int) Math.floor(getLevel() / 20)));
        }

        protected int XPNeededToLevelUp(int CurrentLevel) {
//        if(CurrentLevel == 0)return 0;
//        int cl = NextLevel - 1;
            int cl = CurrentLevel;
            if (cl <= 15) {
                return 2 * (cl) + 7;
            } else if (cl <= 30) {
                return 5 * (cl) - 38;
            } else {
                return 9 * (cl) - 158;
            }
        }

        public int getLevel() {
            int x = getXP();
            int l = 0;
            while (true) {
                int a = XPNeededToLevelUp(l);
                if (a < x) {
                    x -= a;
                    l++;
                } else {
                    break;
                }
            }
            return l;
        }

        protected int getXP() {
            return XP;
        }

        protected int getRealXP() {
            return XP - XPNeededToLevelUp(getLevel());
        }

        protected void addXP(int a) {
            XP += Math.abs(a);
//        if(XP > XPNeededToLevelUp(getLevel()));
        }

        protected void takeXP(int a) {
            XP -= Math.abs(a);
        }

        @Override
        public ConfigSection exportConfig() {
            ConfigSection c = super.exportConfig();
            c.put("XP", getXP());
            return c;
        }

        @Override
        public void importConfig(ConfigSection cs) {
            super.importConfig(cs);
            if (cs.containsKey("XP")) addXP(cs.getInt("XP"));
        }
    }
}
