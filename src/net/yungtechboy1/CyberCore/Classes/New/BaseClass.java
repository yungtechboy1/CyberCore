package net.yungtechboy1.CyberCore.Classes.New;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.Event;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerToggleSprintEvent;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.Abilities.Ability;
import net.yungtechboy1.CyberCore.Classes.Power.MineLifePower;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseClass {
    protected final static int TYPE_Offensive_Raider = 1;
    protected final static int TYPE_Offensive_Thief = 2;
    protected final static int TYPE_Offensive_Assassin = 3;
    protected final static int TYPE_Offensive_Knight = 4;
    protected final static int TYPE_Offensive_Tank = 5;
    protected final static int TYPE_Crafting_MadScientist = 6;
    protected final static int TYPE_Crafting_Enchater = 7;
    protected final static int TYPE_Crafting_Smith = 8;
    protected final static int TYPE_Crafting_Crafter = 9;
    protected final static int TYPE_Farming_Farmer = 10;
    protected final static int TYPE_Farming_LumberJack = 11;
    protected final static int TYPE_Farming_Miner = 12;
    public static int NONE = 0;
    public ArrayList<CoolDown> COOLDOWNS = new ArrayList<>();
    public boolean Prime = false;
    public int PrimeKey = 0;
    public int SwingTime = 20;
    public ArrayList<Power> Powers = new ArrayList<>();
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

    public ClassType getTYPE() {
        return TYPE;
    }

    private ClassType TYPE = ClassType.Class_Miner_TNT_Specialist;
    private int LVL = 0;
    private int XP = 0;
    private Ability ActiveAbility;
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
        }
    }
    public BaseClass(CyberCoreMain main, CorePlayer player, ClassType rank) {
        CCM = main;
//        MainID = mid;
        P = player;
        TYPE = rank;
        LVL = XPToLevel(XP);
    }

    public abstract void SetPowers();

        public int getMainID() {
        return MainID;
    }

    public ArrayList<Power> getPowers() {
        return Powers;
    }

    public Power GetPower(int key) {
        return Powers.get(key);
    }

    public abstract Object RunPower(int powerid, Object ...args);
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

    public void AddPower(Power power) {
        Powers.add(power);
    }

     public boolean TryRunPower(int powerid){
        Power p = Powers.get(powerid);
        if(p == null)return false;
        return p.CanRun();
    }

    public void RunPower(int powerid){

        Power p = Powers.get(powerid);
        if(p == null)return;

    }

    public ArrayList<Ability> PossibleAbillity() {
        ArrayList<Ability> a = new ArrayList<Ability>();
        return a;
    }

    public String getName() {
        return "---N/A---";
    }

    public ConfigSection export() {
        return new ConfigSection() {{
            put("COOLDOWNS", COOLDOWNS);
            put("XP", XP);
            put("TYPE", TYPE.getKey());
        }};
    }

    public Player getPlayer() {
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
    }

    public int getXP() {
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
        Ability a = PossibleAbillity().get(key);
        a.PrimeEvent();
    }

    public void AddCooldown(String perk, int value) {
        COOLDOWNS.add(new CoolDown(perk, value));
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
        for (CoolDown c : COOLDOWNS) {
            if (c.getKey().equalsIgnoreCase(perk)) {
                return c.isValid();
            }
        }
        return false;
    }

    //TODO
    public Event HandelEvent(Event event) {
        if (event instanceof BlockBreakEvent) {
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
        }
        return event;
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
        return event;
    }

    public CustomEntityDamageEvent CustomEntiyDamageEvent(CustomEntityDamageEvent event) {
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

    public void onUpdate(int tick) {

    }


    public enum ClassType {
        Class_Miner_TNT_Specialist(1);

        int k = -1;

        ClassType(int i) {
            k = i;
        }

        public int getKey() {
            return k;
        }
    }

}