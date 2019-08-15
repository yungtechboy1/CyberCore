package net.yungtechboy1.CyberCore.Classes.New;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.Event;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityInventoryChangeEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerToggleSprintEvent;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.Abilities.Ability;
import net.yungtechboy1.CyberCore.Classes.New.Buff.BuffType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBarInt;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.*;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.Manager.Form.CyberForm;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.MainClassSettingsWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseClass {

    public ArrayList<CoolDown> COOLDOWNS = new ArrayList<>();
    public boolean Prime = false;
    public int PrimeKey = 0;
    public int SwingTime = 20;
    public ArrayList<PowerEnum> ActivePowers = new ArrayList<>();
    //    public ArrayList<PowerEnum> DefaultPowers = new ArrayList<>();
    public HashMap<PowerEnum, PowerAbstract> PossiblePowerList = new HashMap<>();
    public ArrayList<AdvancedPowerEnum> DefaultPowers1 = new ArrayList<>();
    protected int MainID = 0;
    public CyberCoreMain CCM;
    HashMap<Integer, Integer> Herbal = new HashMap<Integer, Integer>() {{
        put(Block.GRASS, 10);
        put(Block.VINE, 10);
        put(Block.PUMPKIN, 20);
        put(Block.MELON_BLOCK, 20);
        put(Block.COCOA_BLOCK, 30);
        put(Block.SUGARCANE_BLOCK, 30);
        put(Block.CACTUS, 30);
        put(Block.CARROT_BLOCK, 50);
        put(Block.WHEAT_BLOCK, 50);
        put(115, 50);//Neather Wart
        put(Block.FLOWER, 100);
        put(Block.BROWN_MUSHROOM, 150);
        put(Block.BROWN_MUSHROOM_BLOCK, 150);
        put(Block.RED_MUSHROOM, 150);
        put(Block.RED_MUSHROOM_BLOCK, 150);
    }};
    HashMap<Integer, Integer> Excavation = new HashMap<Integer, Integer>() {{
        put(Block.GRASS, 40);
        put(Block.MYCELIUM, 40);
        put(Block.DIRT, 40);
        put(Block.GRAVEL, 40);
        put(Block.SAND, 40);
        put(Block.SOUL_SAND, 40);
        put(Block.CLAY_BLOCK, 40);
    }};
    private CorePlayer P;
    //    private ClassType TYPE;
    private int LVL = 0;
    private int XP = 0;
    private Ability ActiveAbility;
    private HashMap<BuffType, Buff> Buffs = new HashMap<>();
    private HashMap<BuffType, DeBuff> DeBuffs = new HashMap<>();
    private ArrayList<LockedSlot> LockedSlots = new ArrayList<>();
    private double PowerSourceCount = 0;
    private ClassSettingsObj ClassSettings = null;//new ClassSettingsObj(this);

    //Get all the Powers that the player has Learned
    //Next Filter By the Class Currently Choosen
    //Then Add all aplicable Powers
    public BaseClass(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        this(main, player);
        if (data != null) {
            if (data.containsKey("COOLDONWS")) {
                ConfigSection css = data.getSection("COOLDONWS");
                if (css == null) {
                    System.out.println("ERROROORR COOLDOWNS NOT IN CORRECT FOPRMT");
                } else {
                    css.getAllMap().forEach((key, value) -> {
                        System.out.println(value + " <<<<<<<<<<<<<<<<<<<<<< ");
                        AddCooldown(key, (int) value);
                    });
                }
            }

            if (data.containsKey("XP")) {
                int xpi = data.getInt("XP", 0);
                addXP(xpi);
            }
            if (data.containsKey("PowerSourceCount")) {
                int psc = data.getInt("PowerSourceCount", 0);
                addPowerSourceCount(psc);
            }
            if (data.containsKey("CS")) {
//                int psc = data.getInt("PowerSourceCount", 0);
                ClassSettings = new ClassSettingsObj(this, ((ConfigSection) data.get("CS")));
            } else {
                System.out.println("Error! No ClassSetting Found!!!");
            }
        } else {
            ClassSettings = new ClassSettingsObj(this);
        }
        learnPlayerDefaultPowers();
        startbuffs();
        startSetPowers();
    }

    public BaseClass(CyberCoreMain main, CorePlayer player) {
        CCM = main;
//        MainID = mid;
        P = player;
//        TYPE = rank;
//        LVL = XPToLevel(XP);
        ClassSettings = new ClassSettingsObj(this);
        startbuffs();
        startSetPowers();
    }

    public BaseClass(CyberCoreMain main) {
        CCM = main;
        ClassSettings = new ClassSettingsObj(this);
//        MainID = mid;
//        P = player;
//        TYPE = rank;
//        LVL = XPToLevel(XP);
//        startbuffs();
//        startSetPowers();
    }

    public static int XPToLevel(int xp) {
        int lvl = 0;
        while (xp >= calculateRequireExperience(lvl)) {
            xp = xp - calculateRequireExperience(lvl);
            lvl++;
        }
        return lvl;
    }

    public static int XPRemainder(int xp) {
        int lvl = 0;
        while (xp >= calculateRequireExperience(lvl)) {
            xp = xp - calculateRequireExperience(lvl);
            lvl++;
        }
        return xp;
    }

    public static int XPToGetToLevel(int level) {
        int xp = 0;
        for (; level > 0; ) {
            xp += calculateRequireExperience(level);
            --level;
        }
        return xp;
    }

    public static int calculateRequireExperience(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9 * 100;
        } else if (level >= 15) {
            return 37 + (level - 15) * 5 * 100;
        } else {
            return 7 + level * 2 * 100;
        }
    }

    public void learnPlayerDefaultPowers() {
        for (AdvancedPowerEnum pe : getDefaultPowers()) {
            if (!getClassSettings().isPowerLearned(pe)) {
                System.out.println("SEND LEARN TO " + pe);
                getClassSettings().learnNewPower(pe, true);
            }
        }
    }

    private void startSetPowers() {
//        CCM.PowerManagerr.getPossiblePowers(getClassSettings().getLearnedPowers());
//        SetPowers();
    }

    public ArrayList<LockedSlot> getLockedSlots() {
        return LockedSlots;
    }

    public void onLeaveClass() {
        if (this instanceof PowerHotBarInt) {
            if (getLockedSlots().size() > 0) {
                for (LockedSlot ls : getLockedSlots()) getPlayer().getInventory().clear(ls.getSlot());
                LockedSlots.clear();
            }
        }
    }

    public PlayerInventory getPlayerInventory() {
        PlayerInventory pi = new PlayerInventory(getPlayer());
        Map<Integer, Item> s = getPlayer().getInventory().slots;
        if (getLockedSlots().size() > 0) for (LockedSlot ls : getLockedSlots()) s.put(ls.getSlot(), Item.get(0));
        pi.setContents(s);
        return pi;
    }

    public abstract PrimalPowerType getPowerSourceType();

    public double getPowerSourceCount() {
        return PowerSourceCount;
    }

    public void addPowerSourceCount() {
        addPowerSourceCount(1);
    }

    public void addPowerSourceCount(double a) {
        if (PowerSourceCount + a > getMaxPowerSourceCount()) {
            double d = getMaxPowerSourceCount() - a;
            if (d < 0) PowerSourceCount += d;
        } else {
            PowerSourceCount += Math.abs(a);
        }
    }

    public ArrayList<AdvancedPowerEnum> getDefaultPowers() {
        return new ArrayList<>();
    }

    public TextFormat getColor() {
        return TextFormat.GRAY;
    }

    public boolean takePowerSourceCount(double a) {
        if (a > PowerSourceCount) return false;
        PowerSourceCount -= a;
        return true;
    }

    public double getMaxPowerSourceCount() {
        return Math.round((Math.abs(Math.pow(57 * (getLVL() + 1), 2)) / Math.sqrt(Math.pow(20 * (getLVL() + 1), 3))) + ((getLVL() + 1) * 10));
    }

    public void tickPowerSource(int tick) {
        addPowerSourceCount();//From Server Every 20 Secs
        double t = Math.abs(Math.pow(27 * (getLVL() + 1), 2));
        double b = Math.sqrt(Math.pow(18 * (getLVL() + 1), 3));
        int f = (int) Math.round((t / b) * .2);
        addPowerSourceCount(Math.abs(f));
        //TODO
        //ISSUE
        //Maybe TIck player power here too??
    }

    public ArrayList<CoolDown> getCOOLDOWNS() {
        return COOLDOWNS;
    }

    private void startbuffs() {
        initBuffs();
        if (P != null) registerAllBuffsToCorePlayer(P);
    }

    public ClassTeir getTeir() {
        int d = (int) Math.floor(getLVL() / 10);
        return ClassTeir.values()[d];
    }

    public abstract ClassType getTYPE();

    public abstract void SetPowers();

    public int getMainID() {
        return getTYPE().getKey();
    }

    public abstract void initBuffs();

    private void registerAllBuffsToCorePlayer(CorePlayer cp) {
//        for (Buff b : getBuffs().values()) {
        for (Buff b : getBuffs().values()) {
            cp.addBuffFromClass(b);
        }
        for (DeBuff b : getDeBuffs().values()) {
            cp.addDeBuffFromClass(b);
        }
        cp.initAllClassBuffs();
    }

    private void recheckAllBuffs(int tick) {
        //No Need to Keep resending :/
//        for (Buff b : getBuffs().values()) {
//            getPlayer().addBuffFromClass(b);
//        }
//        for (DeBuff b : getDeBuffs().values()) {
//            getPlayer().addDeBuffFromClass(b);
//        }
        getPlayer().initAllClassBuffs();
    }

    public String getDisplayName() {
        return getColor() + getName();
    }

    public HashMap<BuffType, Buff> addBuff(Buff o) {
        Buffs.put(o.getBt(), o);
        return (HashMap<BuffType, Buff>) Buffs.clone();
    }

    public HashMap<BuffType, Buff> removeBuffs(Buff o) {
        Buffs.remove(o.getBt().ordinal());
        return (HashMap<BuffType, Buff>) Buffs.clone();
    }

    public HashMap<BuffType, Buff> getBuffs() {
        return (HashMap<BuffType, Buff>) Buffs.clone();
    }

    public Buff getBuff(BuffType o) {
        return Buffs.get(o);
    }

    public HashMap<BuffType, DeBuff> removeDeBuff(DeBuff o) {
        DeBuffs.remove(o.getBt().ordinal());
        return (HashMap<BuffType, DeBuff>) DeBuffs.clone();
    }

    public HashMap<BuffType, DeBuff> addDeBuff(DeBuff o) {
        DeBuffs.put(o.getBt(), o);
        return (HashMap<BuffType, DeBuff>) DeBuffs.clone();
    }

    public HashMap<BuffType, DeBuff> getDeBuffs() {
        return (HashMap<BuffType, DeBuff>) DeBuffs.clone();
    }

    public DeBuff getDeBuff(int o) {
        return DeBuffs.get(o);
    }

    public float getDamageBuff() {
        return 1f;
    }

    public float getArmorBuff() {
        return 1f;
    }

    public int getExtraHealth() {
        return 0;
    }

    public float getMovementBuff() {
        return 0;
    }

    public ArrayList<PowerEnum> getEnabledPowersList() {
        return getClassSettings().getEnabledPowers();
    }
//
//    public void addDefaultPower(PowerEnum power) {
//        DefaultPowers.add(power);
////        getClassSettings().getClassDefaultPowers().add(power);
//    }

    public ArrayList<PowerAbstract> getActivePowers() {
        ArrayList<PowerAbstract> pp = new ArrayList<>();
        for (PowerEnum pe : getEnabledPowersList()) {
            pp.add(getPossiblePower(pe));
        }
        return pp;
    }

    public PowerAbstract getPossiblePower(PowerEnum key, boolean active) {
//        if(active)return ActivePowers.get(key);
        return PossiblePowerList.get(key);
    }

    public PowerAbstract getPossiblePower(PowerEnum key) {
        return getPossiblePower(key, true);
    }

    public abstract Object RunPower(PowerEnum powerid, Object... args);

    public void deactivatePower(PowerEnum pe) {
        PowerAbstract p = getPossiblePower(pe, false);
        if (p == null) {
            getPlayer().sendMessage("Error DeActivating " + pe.name());
        }
        p.setActive(false);
//        getClassSettings().delActiveGPDLPower(pe);
        onPowerDeActivate(p);//callback
        delActivePower(p);
        getPlayer().sendMessage(TextFormat.RED + "POWER > " + p.getDispalyName() + " has been DEactivated!");
    }

    //@Deprecated
//    public void enablePower(PowerData pe) {
//        System.out.println("Attempting to activate "+pe.getPowerID());
//        PowerAbstract p = pe.getPA();
//        if (p == null) {
//            getPlayer().sendMessage("E:221S: Error attempting to Activating " + pe.getPowerID());
//            return;
//        }
//        PossiblePowerList.put(pe.getPowerID(),p);
//        p.enablePower();
//        onPowerEnabled(p);//callback
////        addActivePower(p);
//        getPlayer().sendMessage(TextFormat.GREEN + "POWER > " + p.getDispalyName() + " has been activated!");
//    }
    public void enablePower(AdvancedPowerEnum pe) {
        enablePower(pe,null);
    }
    public void enablePower(AdvancedPowerEnum pe, LockedSlot ls) {
        System.out.println("Attempting to activate222 " + pe);
        PowerAbstract p = PowerManager.getPowerfromAPE(pe, this);
        if (p == null) {
            getPlayer().sendMessage("E:221S: Error attempting to Activating " + pe.getPowerID());
            return;
        }
        PossiblePowerList.put(pe.getPowerID(),p);
        p.enablePower();
        onPowerEnabled(p);//callback
//        addActivePower(p);
        getPlayer().sendMessage(TextFormat.GREEN + "POWER > " + p.getDispalyName() + " has been activated!");
    }
    public void enablePower(PowerEnum pe) {
        System.out.println("Attempting to activate222 "+pe);
        PowerAbstract p = getPossiblePower(pe);
        if (p == null) {
            getPlayer().sendMessage("E:221S: Error attempting to Activating " + pe);
            return;
        }
        if(ls != null && ls != LockedSlot.NA)p.setLS(ls);
        p.enablePower();
        onPowerEnabled(p);//callback
//        addActivePower(p);
        getPlayer().sendMessage(TextFormat.GREEN + "POWER2 > " + p.getDispalyName() + " has been activated!");
    }

    private void onPowerEnabled(PowerAbstract p) {
    }

    public void activatePower(PowerEnum pe) {
        PowerAbstract p = getPossiblePower(pe, false);
        if (p == null) {
            getPlayer().sendMessage("Error Activating " + pe.name());
            return;
        }
        p.setActive();
        onPowerActivate(p);//callback
//        addActivePower(p);
        getPlayer().sendMessage(TextFormat.GREEN + "POWER > " + p.getDispalyName() + " has been activated!");
    }

    public void onPowerDeActivate(PowerAbstract p) {

    }

    public void onPowerActivate(PowerAbstract p) {

    }

    private void addActivePower(PowerAbstract p) {
        addActivePower(p.getType());
        addPossiblePower(p);
    }

    private void addActivePower(PowerEnum p) {
        if (!getClassSettings().getEnabledPowers().contains(p)) getClassSettings().addActivePower(p);
    }

    private void delActivePower(PowerAbstract p) {
        delActivePower(p.getType());
    }


//        PowerAbstract p = ActivePowers.get(powerid);
//        if(p == null || args.length != 3 ){
//            CCM.getLogger().error("No PowerAbstract found or Incorrect Args For MineLife E334221");
//            return -1;
//        }
//        if(powerid == 1 && p instanceof MineLifePower){
//            MineLifePower mlp = (MineLifePower) p;
//            return mlp.GetBreakTime((Item)args[0],(Block)args[1],(double)args[2]);
//        }
//        return (double)args[2];
//    }

    private void delActivePower(PowerEnum p) {
        if (getClassSettings().getEnabledPowers().contains(p)) getClassSettings().delActivePower(p);
        if (getClassSettings().getPreferedSlot7() == p) getClassSettings().clearSlot7();
        if (getClassSettings().getPreferedSlot8() == p) getClassSettings().clearSlot8();
        if (getClassSettings().getPreferedSlot9() == p) getClassSettings().clearSlot9();
    }

    public final void addPossiblePower(PowerAbstract power) {
        PowerSettings ps = power.getPowerSettings();
        if (ps == null) {
            getPlayer().getServer().getLogger().error("CAN NOT ADD POWER " + power.getName() + "! No PowerSetting Set!");
            getPlayer().sendMessage(TextFormat.RED + "Error > Plugin > Power > " + power.getName() + " | Error activating power! No Power Setting Set!!!!");
            return;
        }
        if (!getLearnedPowersPE().contains(power.getType())) {
            getPlayer().sendMessage("Error! Could not Add Possible Power " + power.getDispalyName() + " because you have not learned that power yet!");
            System.out.println("ERror! " + getPlayer().getName() + " has not learned " + power.getDispalyName() + " Yet!");
            return;
        }

        if (ClassSettings.getEnabledPowers().isEmpty() || !ClassSettings.getEnabledPowers().contains(power.getType())) {
            getPlayer().sendMessage("Error! Could not Add non enabled Power to Possible Power " + power.getDispalyName() + " because you have not learned that power yet!");
            System.out.println("ERror! " + getPlayer().getName() + " has not learned " + power.getDispalyName() + " Yet!");
            return;
        }

//        if (power instanceof PowerHotBarInt) {
//            LockedSlots.add(power.getLS());
//            if( getClassSettings().getPreferedSlot7() == power.getType()){
//
//            }
//        }
//        if(ClassSettings.getLearnedPowers().contains(power.getType())){
        //Add to Power List to Pick From!
        PossiblePowerList.put(power.getType(), power);
        //Power is Learned
        //Power Active
        if (ps.isHotbar()) {
            if (ClassSettings.getPreferedSlot7() == power.getType()) power.setLS(LockedSlot.SLOT_7);
            if (ClassSettings.getPreferedSlot8() == power.getType()) power.setLS(LockedSlot.SLOT_8);
            if (ClassSettings.getPreferedSlot9() == power.getType()) power.setLS(LockedSlot.SLOT_9);
        }
//            power.enablePower();
    }

    private ArrayList<PowerEnum> getLearnedPowersPE() {
        ArrayList<PowerEnum> p = new ArrayList<>();
        for (AdvancedPowerEnum pe : getLearnedPowers()) p.add(pe.getPowerEnum());
        return p;
    }

    public ClassSettingsObj getClassSettings() {
//        if(ClassSettings == null)
        return ClassSettings;
    }

    public boolean TryRunPower(PowerEnum powerid) {
        PowerAbstract p = getPossiblePower(powerid);
        if (p == null) return false;
        return p.CanRun(false);
    }

    public void CmdRunPower(PowerEnum powerid) {
        RunPower(powerid);
    }

    public void RunPower(PowerEnum powerid) {
        PowerAbstract p = getPossiblePower(powerid);
        if (p == null) return;
        p.usePower(getPlayer());

    }

    public ArrayList<PowerAbstract> PossiblePowers() {
        ArrayList<PowerAbstract> a = new ArrayList<PowerAbstract>();
        return a;
    }

    public abstract String getName();

    public ConfigSection export() {
        return new ConfigSection() {{
            put("COOLDOWNS", getCOOLDOWNStoConfig());
            put("PowerSourceCount", PowerSourceCount);
            if (getClassSettings() != null) put("CS", getClassSettings().export());
            else put("CS", "{}");
            put("XP", getXP());
            put("TYPE", getTYPE().ordinal());
        }};
    }

    public ConfigSection getCOOLDOWNStoConfig() {
        ConfigSection conf = new ConfigSection();
        for (CoolDown c : getCOOLDOWNS()) {
            System.out.println(c.toString() + "|||| AND " + c.isValid());
            if (c.isValid()) conf.set(c.getKey(), c.getTime());
        }
        return conf;
    }

    public CorePlayer getPlayer() {
        return P;
    }

    public void setActiveAbility(Ability activeAbility) {
        ActiveAbility = activeAbility;
    }

    public void addXP(int xp) {
        XP += xp;
        LVL = XPToLevel(XP);
    }

    public void takeXP(int xp) {
        XP -= xp;
        LVL = XPToLevel(XP);
    }

    public int getXP() {
        LVL = XPToLevel(XP);
        return XP;
    }

    public int getLVL() {
        return XPToLevel(getXP());
    }

    public boolean isPrime() {
        return Prime;
    }

    public void setPrime(boolean prime) {
        Prime = prime;
    }

    public void setPrime(int key) {
        setPrime(true);
        PrimeKey = key;
//        Ability a = PossibleAbillity().get(key);
//        a.PrimeEvent();
    }

    public void AddCooldown(String perk, int value) {
        COOLDOWNS.add(new CoolDown(perk, CCM.GetIntTime() + value));
    }

    public void RemoveCooldown(String perk) {
        if (!HasCooldown(perk)) return;
        CoolDown cr = null;
        for (CoolDown c : COOLDOWNS) {
            if (c.getKey().equalsIgnoreCase(perk)) {
                cr = c;
                break;
            }
        }
        if (cr != null) {
            COOLDOWNS.remove(cr);
        } else {
            CyberCoreMain.getInstance().getLogger().error("Error! No cooldown to remove!");
        }
    }

    public void ReduceCooldown(String perk, int value) {
        if (!HasCooldown(perk)) return;

        CoolDown cr = null;
        for (CoolDown c : COOLDOWNS) {
            if (c.getKey().equalsIgnoreCase(perk)) {
                cr = c;
                break;
            }
        }
        if (cr != null) {
            COOLDOWNS.remove(cr);
            AddCooldown(perk, cr.getTime() - value);
        } else {
            CyberCoreMain.getInstance().getLogger().error("Error! No cooldown to reduce!");
        }
    }

    public CoolDown GetCooldown(String key) {
        if (!HasCooldown(key)) return null;
        for (CoolDown c : COOLDOWNS) {
            if (c.getKey().equalsIgnoreCase(key)) {
                return c;
            }
        }
        return null;
    }

    public boolean HasCooldown(String perk) {
        for (CoolDown c : (ArrayList<CoolDown>) COOLDOWNS.clone()) {
            if (c.getKey().equalsIgnoreCase(perk)) {
                if (!c.isValid()) {
                    COOLDOWNS.remove(c);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public Event PowerHandelEvent(Event e) {
//        Event ee = e;
        for (PowerAbstract p : getActivePowers()) {
            p.handelEvent(e);
        }
        return e;
    }

    //TODO
    public Event HandelEvent(Event event) {
        event = PowerHandelEvent(event);
        if (event instanceof PlayerTakeDamageEvent) {
            event = PlayerTakeDamageEvent((PlayerTakeDamageEvent) event);
//            if (ActiveAbility != null) event = ActiveAbility.CustomEntityDamageByEntityEvent((CustomEntityDamageByEntityEvent) event);
            return event;
        } else if (event instanceof CustomEntityDamageByEntityEvent) {
            event = CustomEntityDamageByEntityEvent((CustomEntityDamageByEntityEvent) event);
//            if (ActiveAbility != null) event = ActiveAbility.CustomEntityDamageByEntityEvent((CustomEntityDamageByEntityEvent) event);
            return event;
        } else if (event instanceof BlockBreakEvent) {
            event = BlockBreakEvent((BlockBreakEvent) event);
            if (ActiveAbility != null) event = ActiveAbility.BlockBreakEvent((BlockBreakEvent) event);
            return event;
        } else if (event instanceof PlayerToggleSprintEvent) {
            event = PlayerToggleSprintEvent((PlayerToggleSprintEvent) event);
            if (ActiveAbility != null) event = ActiveAbility.PlayerToggleSprintEvent((PlayerToggleSprintEvent) event);
            return event;
        } else if (event instanceof PlayerInteractEvent) {
            event = PlayerInteractEvent((PlayerInteractEvent) event);
            if (ActiveAbility != null) event = ActiveAbility.PlayerInteractEvent((PlayerInteractEvent) event);
            return event;
        } else if (event instanceof EntityRegainHealthEvent) {
            event = EntityRegainHealthEvent((EntityRegainHealthEvent) event);
            if (ActiveAbility != null) event = ActiveAbility.EntityRegainHealthEvent((EntityRegainHealthEvent) event);
            return event;
        } else if (event instanceof BlockPlaceEvent) {
            event = BlockPlaceEvent((BlockPlaceEvent) event);
            if (ActiveAbility != null) event = ActiveAbility.BlockPlaceEvent((BlockPlaceEvent) event);
            return event;
        } else if (event instanceof EntityDamageEvent) {
            event = EntityDamageEvent((EntityDamageEvent) event);
            if (ActiveAbility != null) event = ActiveAbility.EntityDamageEvent((EntityDamageEvent) event);
            return event;
        } else if (event instanceof CraftItemEvent) {
            event = CraftItemEvent((CraftItemEvent) event);
            if (ActiveAbility != null) event = ActiveAbility.CraftItemEvent((CraftItemEvent) event);
            return event;
        } else if (event instanceof PlayerJumpEvent) {
            event = PlayerJumpEvent((PlayerJumpEvent) event);
            if (ActiveAbility != null) event = ActiveAbility.PlayerJumpEvent((PlayerJumpEvent) event);
            return event;
        } else if (event instanceof EntityInventoryChangeEvent) {
            event = EntityInventoryChangeEvent((EntityInventoryChangeEvent) event);
//            if (ActiveAbility != null) event = ActiveAbility.PlayerJumpEvent((PlayerJumpEvent) event);
            return event;
        }
        return event;
    }

    public PlayerJumpEvent PlayerJumpEvent(PlayerJumpEvent event) {
        return event;
    }

    public EntityInventoryChangeEvent EntityInventoryChangeEvent(EntityInventoryChangeEvent event) {
        return event;
    }

    //TODO Change to MainClassSettingsWindow return tyoe
    public CyberForm getSettingsWindow() {
        return new MainClassSettingsWindow(this, FormType.MainForm.NULL, "InternalPlayerSettings Window", "");
    }

    @Deprecated
    public void activateAbility() {
//        if (HasCooldown(PrimeKey)) {
//            getPlayer().sendMessage("This Has a CoolDown!");
//            return;
//        } else if (PrimeKey <= PossibleAbillity().size() - 1) {
//            Ability a = PossibleAbillity().get(PrimeKey);
//            if (a != null && a.activate()) {
//                setActiveAbility(a);
//            }
//        }
    }

    public void activateAbility(Vector3 pos) {
        activateAbility(pos, 0);
    }

    public void activateAbility(int id) {
        activateAbility(null, id);
    }

    public void activateAbility(Vector3 pos, int id) {

    }

    public void disableAbility() {
        ActiveAbility.deactivate();
    }

    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {
        return event;
    }

    public PlayerToggleSprintEvent PlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        return event;
    }

    public BlockPlaceEvent BlockPlaceEvent(BlockPlaceEvent event) {
        return event;
    }

    public EntityRegainHealthEvent EntityRegainHealthEvent(EntityRegainHealthEvent event) {
        return event;
    }

    public PlayerInteractEvent PlayerInteractEvent(PlayerInteractEvent event) {
        return event;
    }

    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {
        return event;
    }

    public CraftItemEvent CraftItemEvent(CraftItemEvent event) {
        return event;
    }

    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent event) {
        for (PowerAbstract p : getActivePowers()) p.CustomEntityDamageByEntityEvent(event);
        float bd = event.getOriginalDamage();
        Buff b = getBuff(BuffType.Damage);
        if (event.getEntity() instanceof Player && getBuff(BuffType.DamageToPlayer) != null) {
            b = getBuff(BuffType.DamageToPlayer);
        } else if (getBuff(BuffType.DamageToEntity) != null) {
            b = getBuff(BuffType.DamageToEntity);
        }
        if (b != null) bd *= b.getAmount();
        event.setDamage(bd);
        return event;
    }

    public CustomEntityDamageEvent CustomEntityDamageEvent(CustomEntityDamageEvent event) {
        float bd = event.getOriginalDamage();
        Buff b = getBuff(BuffType.Damage);
        if (b != null) bd *= b.getAmount();
        event.setDamage(bd);
        return event;
    }

    public int XPRemainder() {
        return XPRemainder(getXP());
    }

    public void tickPowers(int tick) {
//        System.out.println("Tring to TICKING POWER "+getActivePowers().size());
//        System.out.println("Tring to TICKING POWER "+getActivePowers());
        for (PowerAbstract p : getActivePowers()) {
//            System.out.println("TICKING POWER " + p.getName());
            try {
                //No Need to tick Disabled Or Non Ticking Powers
                if (p.getTickUpdate() != -1 && p.isActive() || p.isHotbar()) p.handleTick(tick);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onUpdate(int tick) {
//        System.out.println("TICKING BASECLASS");
        tickPowers(tick);
        if (GetCooldown("CheckBuff") == null) {
            AddCooldown("CheckBuff", 15);
            recheckAllBuffs(tick);
        }
//        if(GetCooldown("Recheck") == null) {
//            AddCooldown("CheckBuff", 15);
//            recheckAllBuffs(tick);
//        }
    }

    public ArrayList<String> FormatHudText() {
        ArrayList<String> f = new ArrayList<>();
        int lvl = XPToLevel(getXP());
        String pclass = getName();
        int pxp = XPRemainder(getXP());
        int pxpof = calculateRequireExperience(lvl + 1);
        int plvl = lvl;
        f.add(TextFormat.AQUA + pclass);
        f.add("" + TextFormat.GREEN + pxp + TextFormat.AQUA + " / " + TextFormat.GOLD + pxpof);
        f.add(TextFormat.GREEN + "Level: " + TextFormat.YELLOW + plvl);
        f.add(TextFormat.AQUA + getPowerSourceType().name() + " PowerAbstract : " + getPowerSourceCount() + " / " + getMaxPowerSourceCount());
        return f;
    }

    public CyberForm getHowToUseClassWindow() {
        return null;
    }

    public FormWindow getClassMerchantWindow() {
        return new CyberFormSimpleClassMerchant(this);
//        return null;
    }

    /**
     * After Creating the class from the class manager this method is ran for final checks
     */
    public void onCreate() {
        if (getClassSettings() != null) ClassSettings.check();
        else System.out.println("ERRROROROOROROROOROROROROOasdasd asd asdas dasd111231232122333");
    }

    public void addButtons(MainClassSettingsWindow mainClassSettingsWindow) {
        for (PowerAbstract p : getActivePowers()) {
            p.addButton(mainClassSettingsWindow);
        }
    }

    public ArrayList<AdvancedPowerEnum> getLearnedPowers() {
        return (ArrayList<AdvancedPowerEnum>) getClassSettings().getLearnedPowers().clone();
    }

    public ArrayList<PowerAbstract> getLearnedPowersAbstract() {
        ArrayList<PowerAbstract> pa = new ArrayList<>();
        for (AdvancedPowerEnum e : getClassSettings().getLearnedPowers()) {
            pa.add(PowerManager.getPowerfromAPE(e,this));
        }
        return pa;
    }

    public void disablePower(AdvancedPowerEnum ape) {
        getClassSettings().getEnabledPowers().remove(ape.getPowerEnum());
        PossiblePowerList.remove(ape.getPowerEnum());
    }


    public enum ClassTeir {
        Class1,
        Class2,
        Class3,
        Class4,
        Class5,
        Class6,
        Class7,
        Class8,
        Class9,
        Class10
    }


}
