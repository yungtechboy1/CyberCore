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
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.Abilities.Ability;
import net.yungtechboy1.CyberCore.Classes.New.Buff.BuffType;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.*;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.Manager.Form.CyberForm;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.ClassSettingsWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public abstract class BaseClass {

    public ArrayList<CoolDown> COOLDOWNS = new ArrayList<>();
    public boolean Prime = false;
    public int PrimeKey = 0;
    public int SwingTime = 20;
    public HashMap<Integer, Power> Powers = new HashMap<>();
    protected int MainID = 0;
    protected CyberCoreMain CCM;
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
    private ClassType TYPE;
    private int LVL = 0;
    private int XP = 0;
    private Ability ActiveAbility;
    private HashMap<BuffType, Buff> Buffs = new HashMap<>();
    private HashMap<BuffType, DeBuff> DeBuffs = new HashMap<>();
    private double PowerSourceCount = 0;

    public BaseClass(CyberCoreMain main, CorePlayer player, ClassType rank, ConfigSection data) {
        this(main, player, rank);
        if (data != null) {
            if (data.containsKey("cooldowns")) {
                ArrayList<CoolDown> css = (ArrayList<CoolDown>) data.get("cooldowns");
                if (css == null) {
                    System.out.println("ERROROORR COOLDOWNS NOT IN CORRECT FOPRMT");
                } else {
                    COOLDOWNS = css;
                }
            }

            if (data.containsKey("xp")) {
                int xpi = data.getInt("xp", 0);
                addXP(xpi);
            }
            if (data.containsKey("PowerSourceCount")) {
                int psc = data.getInt("PowerSourceCount", 0);
                addPowerSourceCount(psc);
            }
        }
        SetPowers();
    }

    public BaseClass(CyberCoreMain main, CorePlayer player, ClassType rank) {
        CCM = main;
//        MainID = mid;
        P = player;
        TYPE = rank;
        LVL = XPToLevel(XP);
        startbuffs();
        SetPowers();
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

    public TextFormat getColor() {
        return TextFormat.GRAY;
    }

    public boolean takePowerSourceCount(double a) {
        if (a > PowerSourceCount) return false;
        PowerSourceCount -= a;
        return true;
    }

    public double getMaxPowerSourceCount() {
        return Math.round((Math.abs(Math.pow(57 * getLVL(), 2)) / Math.sqrt(Math.pow(20 * getLVL(), 3))) + getLVL() * 10);
    }

    public void tickPowerSource(int tick) {
        addPowerSourceCount();//From Server Every 20 Secs
        double t = Math.abs(Math.pow(27 * getLVL(), 2));
        double b = Math.sqrt(Math.pow(18 * getLVL(), 3));
        int f = (int) Math.round((t / b) * .2);
        addPowerSourceCount(f);
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

    public ClassType getTYPE() {
        return TYPE;
    }

    public abstract void SetPowers();

    public int getMainID() {
        return getTYPE().getKey();
    }

    public abstract void initBuffs();

    private void registerAllBuffsToCorePlayer(CorePlayer cp) {
        for (Buff b : getBuffs().values()) {
            cp.addBuffFromClass(b);
        }
        for (DeBuff b : getDeBuffs().values()) {
            cp.addDeBuffFromClass(b);
        }
        cp.initAllClassBuffs();
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

    public Buff getBuff(int o) {
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

    public Collection<Power> getPowers() {
        return Powers.values();
    }

    public Power getPower(PowerEnum key) {
        return Powers.get(key.ordinal());
    }

    public abstract Object RunPower(PowerEnum powerid, Object... args);

    public void addPower(Power power) {
        Powers.put(power.getType().ordinal(), power);
    }
//        Power p = Powers.get(powerid);
//        if(p == null || args.length != 3 ){
//            CCM.getLogger().error("No Power found or Incorrect Args For MineLife E334221");
//            return -1;
//        }
//        if(powerid == 1 && p instanceof MineLifePower){
//            MineLifePower mlp = (MineLifePower) p;
//            return mlp.GetBreakTime((Item)args[0],(Block)args[1],(double)args[2]);
//        }
//        return (double)args[2];
//    }

    public boolean TryRunPower(PowerEnum powerid) {
        Power p = getPower(powerid);
        if (p == null) return false;
        return p.CanRun();
    }

    public void CmdRunPower(PowerEnum powerid) {
        RunPower(powerid);
    }

    public void RunPower(PowerEnum powerid) {
        Power p = getPower(powerid);
        if (p == null) return;
        p.usePower(getPlayer());

    }

    public ArrayList<Power> PossiblePowers() {
        ArrayList<Power> a = new ArrayList<Power>();
        return a;
    }

    public abstract String getName();

    public ConfigSection export() {
        return new ConfigSection() {{
            put("COOLDOWNS", getCOOLDOWNS());
            put("PowerSourceCount", PowerSourceCount);
            put("XP", getXP());
            put("TYPE", getTYPE().ordinal());
        }};
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
        return LVL;
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
        for (Power p : getPowers()) {
            p.handelEvent(e);
        }
        return e;
    }

    //TODO
    public Event HandelEvent(Event event) {
        event = PowerHandelEvent(event);
        if (event instanceof CustomEntityDamageByEntityEvent) {
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

    public CyberForm GetSettingsWindow() {
        return new ClassSettingsWindow(this, FormType.MainForm.NULL, "Settings Window", "");
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
        for (Power p : Powers.values()) p.CustomEntityDamageByEntityEvent(event);
        float bd = event.getOriginalDamage();
        Buff b = getBuff(BuffType.Damage.ordinal());
        if (event.getEntity() instanceof Player && getBuff(BuffType.DamageToPlayer.ordinal()) != null) {
            b = getBuff(BuffType.DamageToPlayer.ordinal());
        } else if (getBuff(BuffType.DamageToEntity.ordinal()) != null) {
            b = getBuff(BuffType.DamageToEntity.ordinal());
        }
        if (b != null) bd *= b.getAmount();
        event.setDamage(bd);
        return event;
    }

    public CustomEntityDamageEvent CustomEntityDamageEvent(CustomEntityDamageEvent event) {
        float bd = event.getOriginalDamage();
        Buff b = getBuff(BuffType.Damage.ordinal());
        if (b != null) bd *= b.getAmount();
        event.setDamage(bd);
        return event;
    }

    public int XPToLevel(int xp) {
        int lvl = 0;
        while (xp >= calculateRequireExperience(lvl)) {
            xp = xp - calculateRequireExperience(lvl);
            lvl++;
        }
        return lvl;
    }

    public int XPRemainder(int xp) {
        int lvl = 0;
        while (xp >= calculateRequireExperience(lvl)) {
            xp = xp - calculateRequireExperience(lvl);
            lvl++;
        }
        return xp;
    }

    public int calculateRequireExperience(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9 * 100;
        } else if (level >= 15) {
            return 37 + (level - 15) * 5 * 100;
        } else {
            return 7 + level * 2 * 100;
        }
    }

    public void tickPowers(int tick) {
//        System.out.println("Tring to TICKING POWER "+getPowers().size());
//        System.out.println("Tring to TICKING POWER "+getPowers());
        for (Power p : getPowers()) {
//            System.out.println("TICKING POWER " + p.getName());
            try {
                if (p.TickUpdate != -1) p.handleTick(tick);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onUpdate(int tick) {
//        System.out.println("TICKING BASECLASS");
        tickPowers(tick);
    }

    public String FormatHudText() {
        String f = "";
        int lvl = XPToLevel(getXP());
        String pclass = getName();
        int pxp = XPRemainder(getXP());
        int pxpof = calculateRequireExperience(lvl + 1);
        int plvl = lvl;
        f += TextFormat.AQUA + pclass + TextFormat.GRAY + " | " + TextFormat.GREEN + pxp + TextFormat.AQUA + " / " + TextFormat.GOLD + pxpof + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + plvl;
        return f;
    }

    public CyberForm getHowToUseClassWindow() {
        return null;
    }

    public FormWindow getClassMerchantWindow() {
        return null;
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


    public enum ClassType {
        Unknown, Class_Miner_TNT_Specialist, Class_Miner_MineLife, Class_Offense_Mercenary, DragonSlayer, Class_Magic_Enchanter, Class_Rouge_Thief, Class_Offense_Knight, Class_Offense_Holy_Knight, Class_Offense_Dark_Knight, Class_Offense_Assassin, Class_Offense_Raider;


        public int getKey() {
            return ordinal();
        }
    }

}