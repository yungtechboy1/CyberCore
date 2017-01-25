package net.yungtechboy1.CyberCore.Commands.MCMMO;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.Event;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.item.*;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Commands.Abilities.Ability;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/25/2017.
 */
public class BaseMMO {
    public static int NONE = 0;
    public int TYPE_MINING = 0;
    public int TYPE_WOOD_CUTTING = 0;
    public int TYPE_HERBALISM = 0;
    public int TYPE_EXCAVATION = 0;
    public int TYPE_FISHING = 0;
    ;
    public int TYPE_SMELTING = 0;

    HashMap<Integer, Integer> OreBreak = new HashMap<Integer, Integer>() {{
        put(Block.GOLD_ORE, 30);
        put(Block.HARDENED_CLAY, 30);
        put(Block.MOSS_STONE, 30);
        put(Block.NETHERRACK, 30);
        put(Block.SANDSTONE, 30);
        put(Block.STONE, 30);
        put(Block.PACKED_ICE, 50);
        put(Block.STAINED_HARDENED_CLAY, 50);
        put(Block.PRISMARINE, 70);
        put(Block.COAL_ORE, 100);
        put(Block.RED_SANDSTONE, 100);
        put(Block.QUARTZ_ORE, 100);
        put(Block.REDSTONE_ORE, 150);
        put(Block.END_STONE, 150);
        put(Block.OBSIDIAN, 150);
        put(Block.IRON_ORE, 250);
        put(Block.GOLD_ORE, 350);
        put(Block.LAPIS_ORE, 400);
        put(Block.DIAMOND_ORE, 750);
        put(Block.EMERALD_ORE, 1000);
    }};

    HashMap<Integer, Integer> WoodCutting = new HashMap<Integer, Integer>() {{
        put(Block.WOOD, 70);
        put(Block.WOOD2, 70);
    }};

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
    HashMap<Integer, Integer> Excavation2 = new HashMap<Integer, Integer>() {{
        put(Block.GRASS, Item.GLOWSTONE_DUST);
        put(Block.MYCELIUM, Item.GLOWSTONE_DUST);
        put(Block.DIRT, Item.GLOWSTONE_DUST);
        put(Block.SAND, Item.GLOWSTONE_DUST);
    }};

    private CyberCoreMain CCM;
    private Player P;
    private int TYPE = 0;
    private int LVL = 0;
    private int XP = 0;
    private Ability ActiveAbility;

    public BaseMMO(CyberCoreMain main) {
        CCM = main;
    }

    public BaseMMO(CyberCoreMain main, Player player, ConfigSection cs) {
        CCM = main;
        P = player;
        TYPE_MINING = cs.getInt("TYPE_MINING");
        TYPE_WOOD_CUTTING = cs.getInt("TYPE_WOOD_CUTTING");
        TYPE_HERBALISM = cs.getInt("TYPE_HERBALISM");
        TYPE_EXCAVATION = cs.getInt("TYPE_EXCAVATION");
        TYPE_FISHING = cs.getInt("TYPE_FISHING");
    }

    public ConfigSection export() {
        return new ConfigSection() {{
            put("TYPE_MINING", TYPE_MINING);
            put("TYPE_WOOD_CUTTING", TYPE_WOOD_CUTTING);
            put("TYPE_HERBALISM", TYPE_HERBALISM);
            put("TYPE_EXCAVATION", TYPE_EXCAVATION);
            put("TYPE_FISHING", TYPE_FISHING);
        }};
    }

    public void HandelEvent(Event event) {
        if (event instanceof BlockBreakEvent) {
            BlockBreakEvent((BlockBreakEvent) event);
        } else if (event instanceof BlockPlaceEvent) {
            BlockPlaceEvent((BlockPlaceEvent) event);
        }
    }

    public void ExcavationBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        int chance = 0;
        int rand = 100;
        ArrayList<Item> nl = new ArrayList<>(Arrays.asList(event.getDrops()));
        int XL = XPToLevel(TYPE_EXCAVATION);

        switch (block.getId()) {//GLOWSTONE - LVL 25
            case Block.GRASS:
            case Block.MYCELIUM:
            case Block.DIRT:
            case Block.SAND:
                if (XL >= 25) {
                    chance = Math.max(((XL / 10) - 10), 5);
                    rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 100);
                    if (chance > rand) {
                        nl.add(new ItemGlowstoneDust(0, 1));
                        TYPE_EXCAVATION += 80;
                    }
                }
                break;
        }

        if (block.getId() == Block.GRAVEL && XL >= 75) {// - LVL 75
            chance = Math.max(((XL / 10) - 10), 10);
            rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 100);
            if (chance > rand) {
                nl.add(new ItemGunpowder(0, 1));
                TYPE_EXCAVATION += 30;
            }
        }

        if (block.getId() == Block.CLAY_BLOCK && XL >= 150) {// - LVL 150
            chance = Math.max(((XL / 10) - 10), 1);
            rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 100);
            if (chance > rand) {
                nl.add(new ItemSlimeball(0, 1));
                TYPE_EXCAVATION += 10;
            }
        }

        if (block.getId() == Block.GRAVEL && XL >= 175) {// - LVL 175
            chance = Math.max(((XL / 10) - 10), 1);
            rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 100);
            if (chance > rand) {
                nl.add(new ItemSlimeball(0, 1));
                TYPE_EXCAVATION += 10;
            }
        }

        // LVL 250 Group ////////////////////////////////////////////
        if (XL >= 250) {
            if (block.getId() == Block.GRASS) {
                chance = Math.max(((XL / 10) - 10), 1);
                rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 100);
                if (chance > rand) {
                    nl.add(new ItemEgg(0, 1));
                    TYPE_EXCAVATION += 100;
                }
            }
            if (block.getId() == Block.GRASS || block.getId() == Block.MYCELIUM) {
                chance = Math.max(((XL / 10) - 10), 1);
                rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 1000);
                if (chance > rand) {
                    nl.add(new ItemApple(0, 1));
                    TYPE_EXCAVATION += 100;
                }
            }
            if (block.getId() == Block.CLAY_BLOCK) {
                chance = Math.max(((XL / 10) - 10), 5);
                rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 100);
                if (chance > rand) {
                    nl.add(new ItemString(0, 1));
                    TYPE_EXCAVATION += 200;
                }
            }
        }
        ////////////////////////////////////////////////////////////

        if (XL >= 350) { //Lvl 350
            switch (block.getId()) {
                case Block.GRASS:
                case Block.MYCELIUM:
                case Block.DIRT:
                    chance = Math.max(((XL / 10) - 10), 1);
                    rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 100);
                    if (chance > rand) {
                        nl.add(new Item(Item.COCOA,0, 1));
                        TYPE_EXCAVATION += 80;
                    }
            }
        }

        if (XL >= 500) { //Lvl 500
            switch (block.getId()) {
                case Block.BROWN_MUSHROOM:
                case Block.BROWN_MUSHROOM_BLOCK:
                case Block.RED_MUSHROOM:
                case Block.RED_MUSHROOM_BLOCK:
                    chance = Math.max(((XL / 10) - 10), 1);
                    rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 500);
                    if (chance > rand) {
                        nl.add(new ItemGlowstoneDust(0, 1));
                        TYPE_EXCAVATION += 80;
                    }
            }
        }

        if (block.getId() == Block.CLAY_BLOCK && XL >= 500) {// - LVL 500
            chance = Math.max(((XL / 10) - 10), 1);
            rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 1000);
            if (chance > rand) {
                nl.add(new ItemClock(0, 1));
                TYPE_EXCAVATION += 10;
            }
            chance = Math.max(((XL / 10) - 10), 1);
            rand = new NukkitRandom(TYPE_EXCAVATION).nextRange(0, 1000);
            if (chance > rand) {
                nl.add(new ItemBucket(0, 1));
                TYPE_EXCAVATION += 10;
            }
        }
    }

    public void BlockBreakEvent(BlockBreakEvent event) {
        ExcavationBreakEvent(event);
        int id = event.getBlock().getId();
        int obxp = OreBreak.getOrDefault(id, 0);
        int wcxp = WoodCutting.getOrDefault(id, 0);
        int hxp = Herbal.getOrDefault(id, 0);
        int exp = Excavation.getOrDefault(id, 0);
        if (obxp > 0) TYPE_MINING += obxp;
        if (wcxp > 0) TYPE_WOOD_CUTTING += wcxp;
        if (hxp > 0) TYPE_HERBALISM += hxp;
        if (exp > 0) TYPE_EXCAVATION += exp;
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