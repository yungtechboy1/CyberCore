package net.yungtechboy1.CyberCore.Classes;

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
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Abilities.Super_Breaker;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/25/2017.
 */
public class BaseClass {
    public static int NONE = 0;
    public static int TYPE_MINER = 1;
    public static int TYPE_TANK = 2;
    public static int TYPE_LUMBERJACK = 3;
    public static int TYPE_FARMER = 4;
    public static int TYPE_DIGGER = 5;
    public static int TYPE_WARRIOR = 6;
    public static int TYPE_RAIDER = 7;
    public static int TYPE_PYRO = 8;

    public ConfigSection COOLDOWNS = new ConfigSection();
    public boolean Prime = false;
    public int PrimeKey = 0;
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
    private Player P;
    private int TYPE = 0;
    private int LVL = 0;
    private int XP = 0;
    private Ability ActiveAbility;

    public BaseClass(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        CCM = main;
        P = player;
        TYPE = rank;
        XP = xp;
        LVL = XPToLevel(xp);
        COOLDOWNS = cooldowns;
    }

    public BaseClass(CyberCoreMain main, Player player, ConfigSection cs) {
        CCM = main;
        P = player;
        XP = cs.getInt("XP");
        TYPE = cs.getInt("TYPE");
        LVL = XPToLevel(XP);
        COOLDOWNS = cs.getSection("COOLDOWNS");
    }

    public ArrayList<Ability> PossibleAbillity() {
        ArrayList<Ability> a = new ArrayList<Ability>();
        a.add(new Super_Breaker(CCM, this));
        a.add(new Super_Breaker(CCM, this));
        return a;
    }

    public String getName() {
        return "---N/A---";
    }

    public ConfigSection export() {
        return new ConfigSection() {{
            put("COOLDOWNS", COOLDOWNS);
            put("XP", XP);
            put("TYPE", TYPE);
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

    public void setPrime(int key) {
        setPrime(true);
        PrimeKey = key;
        Ability a = PossibleAbillity().get(key);
        a.PrimeEvent();
    }

    public void setPrime(boolean prime) {
        Prime = prime;
    }

    public void AddCooldown(int perk, int value) {
        String key = "" + perk;
        COOLDOWNS.put(key, value);
    }

    public boolean HasCooldown(int perk) {
        String key = "" + perk;
        Integer time = (int) (Calendar.getInstance().getTime().getTime() / 1000);
        return time < COOLDOWNS.getInt(key);
    }

    public void HandelEvent(Event event) {
        if (event instanceof BlockBreakEvent) {
            BlockBreakEvent((BlockBreakEvent) event);
            if (ActiveAbility != null) ActiveAbility.BlockBreakEvent((BlockBreakEvent) event);
        } else if (event instanceof PlayerToggleSprintEvent) {
            PlayerToggleSprintEvent((PlayerToggleSprintEvent) event);
            if (ActiveAbility != null) ActiveAbility.PlayerToggleSprintEvent((PlayerToggleSprintEvent) event);
        } else if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent((PlayerInteractEvent) event);
            if (ActiveAbility != null) ActiveAbility.PlayerInteractEvent((PlayerInteractEvent) event);
        } else if (event instanceof EntityRegainHealthEvent) {
            EntityRegainHealthEvent((EntityRegainHealthEvent) event);
            if (ActiveAbility != null) ActiveAbility.EntityRegainHealthEvent((EntityRegainHealthEvent) event);
        } else if (event instanceof BlockPlaceEvent) {
            BlockPlaceEvent((BlockPlaceEvent) event);
            if (ActiveAbility != null) ActiveAbility.BlockPlaceEvent((BlockPlaceEvent) event);
        } else if (event instanceof EntityDamageEvent) {
            EntityDamageEvent((EntityDamageEvent) event);
            if (ActiveAbility != null) ActiveAbility.EntityDamageEvent((EntityDamageEvent) event);
        } else if (event instanceof CraftItemEvent) {
            CraftItemEvent((CraftItemEvent) event);
            if (ActiveAbility != null) ActiveAbility.CraftItemEvent((CraftItemEvent) event);
        }
    }

    public void activateAbility() {
        if(HasCooldown(PrimeKey)){
            getPlayer().sendMessage("This Has a CoolDown!");
            return;
        }else if (PrimeKey <= PossibleAbillity().size() - 1) {
            Ability a = PossibleAbillity().get(PrimeKey);
            if (a != null && a.activate()) {
                setActiveAbility(a);
            }
        }
    }

    public void activateAbility(Vector3 pos) {
        activateAbility(pos, 0);
    }

    public void activateAbility(int id) {
        activateAbility(null, id);
    }

    public void activateAbility(Vector3 pos, int id) {
        if (TYPE == TYPE_MINER) {

        } else if (TYPE == TYPE_LUMBERJACK && this instanceof Class_LumberJack) {
            //CCM.getServer().getScheduler().scheduleAsyncTask(new Tree_Feller_Async(CCM, ));
        }
    }

    public void disableAbility() {
        ActiveAbility.deactivate();
    }

    public void EntityDamageEvent(EntityDamageEvent event) {

    }

    public void PlayerToggleSprintEvent(PlayerToggleSprintEvent event) {

    }

    public void BlockPlaceEvent(BlockPlaceEvent event) {

    }

    public void EntityRegainHealthEvent(EntityRegainHealthEvent event) {

    }

    public void PlayerInteractEvent(PlayerInteractEvent event) {

    }

    public void BlockBreakEvent(BlockBreakEvent event) {

    }

    public void CraftItemEvent(CraftItemEvent event) {

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
}