package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.Event;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityInventoryChangeEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRedstone;
import cn.nukkit.item.ItemSlimeball;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.CoolDownTick;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.MainClassSettingsWindow;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;


/**
 * Created by carlt on 5/16/2019.
 */
public abstract class PowerAbstract {

    public static String getPowerHotBarItemNamedTagKey = "PowerHotBarItem";
    public BaseClass PlayerClass = null;
    public int TickUpdate = -1;
    public CoolDownTick Cooldown = null;
    public boolean PlayerToggleable = true;
    public boolean CanSendCanNotRunMessage = true;
    public PowerType MainPowerType = PowerType.Regular;
    public PowerType SecondaryPowerType = PowerType.None;
    public PowerTypeSettings PowerSettings = new PowerTypeSettings();
    protected int DeActivatedTick = -1;
    LockedSlot LS = LockedSlot.NA;
    boolean check = false;
    private LevelingType LT = LevelingType.None;
    private ClassLevelingManager LM = null;
    private boolean Active = false;
    private int PowerSuccessChance = 0;
    private int _lasttick = -1;
    private double PowerSourceCost = 0;
    private Vector3 ActivatedLocation;

    public PowerAbstract(BaseClass b, ClassLevelingManager lt, int psc) {
        this(b, lt, psc, 0);
    }

    public PowerAbstract(BaseClass b, ClassLevelingManager lm, int psc, double cost) {
        PowerSuccessChance = psc;
        PlayerClass = b;
        loadLevelManager(lm);
//        Level = lvl;
//        Level = b.getLVL();
        initStages();
        initAfterCreation();
        PowerSourceCost = cost;
    }

    public final void activate() {
        if (isActive()) return;
        setActive(true);
        onAbilityActivate();
//        onActivate();
    }

    public void onAbilityActivate() {
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

    public void whileAbilityActive() {

    }

    public void onAbilityDeActivate() {

    }

    public void loadLevelManager(ClassLevelingManager lm) {
        if (lm == null) return;
        LM = lm;
        LT = LM.getType();
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
        Active = active;
        DeActivatedTick = Server.getInstance().getTick() + getRunTimeTick();
        PowerEnum pe = getType();
        if (PlayerClass.getClassSettings().getLearnedPowers().contains(pe) && active) {
            PowerAbstract p = this;
            PlayerClass.getClassSettings().addActivePower(pe);
            onActivate();
        } else {
            getPlayer().sendMessage(TextFormat.RED + "ERROR > POWER > Could not activate " + getDispalyName() + TextFormat.RED + " Please make sure you have learned this power!");
        }
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
        if (PowerSettings.isArea()) {
            ActivatedLocation = getPlayer().clone();
        }
    }

    public int lastHotBarUpdate = -1;
    public boolean skip = false;

    public boolean canUpdateHotBar(int tick) {
        if (tick > lastHotBarUpdate + 10) {
            lastHotBarUpdate = tick;
            return true;
        }
        return false;
    }

    public LockedSlot getLS() {
        return LS;
    }

    public void setLS(LockedSlot ls) {
        LS = ls;
        RemoveAnyItemsInSlot(getPlayer(), LS);
    }

    void RemoveAnyItemsInSlot(CorePlayer cp , LockedSlot ls){
        Item i = cp.getInventory().getItem(ls.getSlot());
        if (!i.isNull()) {//i.getNamedTag() != null
            if (i.getNamedTag() == null || !i.getNamedTag().contains(getPowerHotBarItemNamedTagKey)) {
                if (cp.getInventory().isFull()) {
                    cp.getLevel().dropItem(cp, i);
                } else {
                    for (int ii = 0; ii < cp.getInventory().getSize(); ii++) {
                        if (ii == ls.getSlot()) continue;
                        Item iii = cp.getInventory().getItem(ii);
                        if (iii.isNull()) {
                            cp.getInventory().setItem(ii, i);
                            break;
                        }
                    }
                }
            }
        }
        cp.getInventory().clear(ls.getSlot(), true);
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

    public Vector3 getActivatedLocation() {
        return ActivatedLocation;
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
        if (MainPowerType == PowerType.Hotbar || SecondaryPowerType == PowerType.Hotbar) {//For Hotbar
            if (getLS() == LockedSlot.NA) return e;
            for (InventoryAction action : e.getTransaction().getActions()) {
                if (!(action instanceof SlotChangeAction)) {
                    continue;
                }
                SlotChangeAction slotChange = (SlotChangeAction) action;

                if (slotChange.getInventory() instanceof PlayerInventory) {
//                who = (Player) slotChange.getInventory().getHolder();
                    //Check to see if Slot is fucked with
                    if (slotChange.getSlot() == getLS().getSlot()) {
                        e.setCancelled();
                        getPlayer().sendMessage(TextFormat.RED + "Error! You can not change your Class Slot!");
                    }
                }
            }
        }


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

    void updateHotbar(LockedSlot ls, CoolDownTick c, PowerAbstract p) {
        if (ls == LockedSlot.NA) return;
        if (c == null || !c.isValid()) {
            setPowerAvailable(p);
            System.out.println("ACTIVE POWER");
        } else {
            System.out.println("UNNNNNNNNACTIVE POWER");
            setPowerUnAvailable(p);
        }
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

    public PowerEnum getType() {
        CyberCoreMain.getInstance().getLogger().error("ERROR GETTING TYPE FROM POWER!!!!!");
        return PowerEnum.Unknown;
    }

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
        if(PowerSettings.isHotbar())if (canUpdateHotBar(getPlayer().getServer().getTick())) updateHotbar(getLS(), Cooldown, this);
    }

    public void afterPowerRun(Object... args) {
        addCooldown();
        getPlayer().sendMessage(getSuccessUsageMessage());
    }

    public String getSuccessUsageMessage() {
        return TextFormat.GREEN + " > PowerAbstract " + getDispalyName() + TextFormat.GREEN + " has been activated!";
    }

    public Effect PotionEffect = null;
    public Boolean PotionOnSelf = false;

    public Boolean getPotionOnSelf() {
        return PotionOnSelf;
    }

    public void setPotionOnSelf(boolean pos){
        PotionOnSelf = pos;
    }

    public int getDurationTicks() {
        return DurationTicks;
    }
    int DurationTicks = 0;

    public Object usePower(Object... args) {
        if (MainPowerType == PowerType.Regular) {
        } else if (PowerSettings.isEffect() && (getEffect() != null)) {
            if (getPotionOnSelf()) {
                getPlayer().sendMessage(TextFormat.GREEN+"["+getDispalyName()+"] Effect Active");
                getPlayer().addEffect(getEffect());
            }else {
                ((Player)args[0]).sendMessage(TextFormat.GREEN+"["+getDispalyName()+"] Effect Active");
                ((Entity)args[0]).addEffect(getEffect());
            }
        } else if (MainPowerType == PowerType.Ability) {
            activate();
        }
        return null;
    }

    private Effect getEffect() {
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

    public void onTick(int tick) {

        if (MainPowerType == PowerType.Regular) {

        }
        if (PowerSettings.isHotbar()) {
            if (canUpdateHotBar(tick)) updateHotbar(getLS(), Cooldown, this);
//            if (getLS() == LockedSlot.NA) return;
//            if (Cooldown == null || !Cooldown.isValid()) {
//                setPowerAvailable(this);
//                System.out.println("ACTIVE POWER");
//            } else {
//                System.out.println("UNNNNNNNNACTIVE POWER");
//                setPowerUnAvailable(this);
//            }
            check = !check;
            if (check) antiSpamCheck(this);
        }
        if (PowerSettings.isAbility()) {
            //Only For Deactivation
            System.out.println("POWER TICKKKKKK2");
            if (isActive()) {
                System.out.println("POWER TICKKKKKK3");
                whileAbilityActive();
                if (tick >= DeActivatedTick) {
                    System.out.println("POWER TICKKKKKK444444444444444444444444444444444444444444444444444444444444444444444");
                    setActive(false);
                    DeActivatedTick = -1;
                    onAbilityDeActivate();
                }
            }
        }
    }

    Item getAvailableItem() {
        return new ItemSlimeball();
    }

    Item getActiveItem() {
        return new ItemSlimeball(0, 5);
    }

    Item getUnActiveItem() {
        return new ItemRedstone();
    }

    Item getUnAvailableItem() {
        return new ItemRedstone();
    }

    Item addNamedTag(PowerAbstract p, Item i, String key, String val) {
        if (p.Cooldown == null || !p.Cooldown.isValid()) {
            i.setCustomName(TextFormat.GREEN + "Power: " + p.getDispalyName());
            i.setLore(TextFormat.GREEN + "Ready to Use", TextFormat.GREEN + "Costs: " + p.getPowerSourceCost() + " " + p.PlayerClass.getPowerSourceType().name() + " ");
        } else {
            i.setCustomName(TextFormat.RED + "Power: " + p.getDispalyName());
            i.setLore(p.Cooldown.toString(), TextFormat.GREEN + "Costs: " + p.getPowerSourceCost() + " " + p.PlayerClass.getPowerSourceType().name() + " ");
        }
        CompoundTag ct = i.getNamedTag();
        if (ct == null) ct = new CompoundTag();
        ct.putBoolean(getPowerHotBarItemNamedTagKey, true);
        i.setNamedTag(ct);
        if (key == null) return i;
        ct.putString(key, val);
        i.setNamedTag(ct);
        return i;
    }

    void setPowerAvailable(PowerAbstract p) {

        p.getPlayer().getInventory().setItem(p.getLS().getSlot(), addNamedTag(p, getActiveItem(), p.getSafeName(), "Active"));
//        getPlayer().getInventory().setHeldItemIndex(LS.getSlot());
    }

    void setPowerUnAvailable(PowerAbstract p) {
        p.getPlayer().getInventory().setItem(p.getLS().getSlot(), addNamedTag(p, getUnAvailableItem(), p.getSafeName(), "Idle"));
    }

    private void antiSpamCheck(PowerAbstract p) {
//        int slot = 0;
        boolean k = false;
        for (int slot = 0; slot < p.getPlayer().getInventory().getSize(); slot++) {
            if (slot == p.getLS().getSlot()) continue;
            boolean g = false;
            for (LockedSlot ls : p.PlayerClass.getLockedSlots()) {
                if (ls.getSlot() == slot) {
                    g = true;
                    break;
                }
            }
            if (g) continue;
            //Checking other ActivePowers
//            if()
            Item i = p.getPlayer().getInventory().getItem(slot);
            if (i.getNamedTag() != null) {
                if (i.getNamedTag().contains(getPowerHotBarItemNamedTagKey)) {
                    p.getPlayer().getInventory().clear(slot, true);
                    k = true;
                }
            }
            slot++;
        }
//        if (k) p.getPlayer().kick("Please do not spam system!");
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

    public enum PowerType {
        None,
        Regular,
        Ability,
        Hotbar
    }

    public enum MeshPowerType {
        None,
        Regular,
        Ability,
        Hotbar,

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

    public class PowerTypeSettings {
        public boolean Regular = true;
        public boolean Passive = false;
        public boolean Ability = false;
        public boolean Hotbar = false;
        public boolean Effect = false;
        public boolean Area = false;
//        public boolean Regular = false;


        public boolean isPassive() {
            return Passive;
        }

        public boolean isEffect() {
            return Effect;
        }

        public boolean isArea() {
            return Area;
        }

        public boolean isRegular() {
            return Regular;
        }

        public boolean isAbility() {
            return Ability;
        }

        public boolean isHotbar() {
            return Hotbar;
        }
    }
}
