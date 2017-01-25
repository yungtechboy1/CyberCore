package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.block.BlockOreCoal;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/24/2017.
 */
public class MCMMO {

    Player p;

    //Gathering
    Integer Mining = 0;
    Integer MiningL = 0;
    Integer WoodCutting = 0;
    Integer WoodCuttingL = 0;
    Integer Herbalism = 0;
    Integer HerbalismL = 0;
    Integer Excavation = 0;
    Integer ExcavationL = 0;
    Integer Fishing = 0;
    Integer FishingL = 0;

    Integer Unarmed = 0;
    Integer UnarmedL = 0;
    Integer Archery = 0;
    Integer ArcheryL = 0;
    Integer Swords = 0;
    Integer SwordsL = 0;
    Integer Axes = 0;
    Integer AxesL = 0;
    Integer Taming = 0;
    Integer TamingL = 0;

    Integer Repair = 0;
    Integer RepairL = 0;
    Integer Acrobatics = 0;
    Integer AcrobaticsL = 0;
    Integer Alchemy = 0;
    Integer AlchemyL = 0;
    Integer Slavage = 0;
    Integer SlavageL = 0;
    Integer Smelting = 0;
    Integer SmeltingL = 0;

    HashMap<Integer, Integer> OreBreak = new HashMap<Integer, Integer>() {{
        put(Item.GOLD_ORE, 30);
        put(Item.HARDENED_CLAY, 30);
        put(Item.MOSS_STONE, 30);
        put(Item.NETHERRACK, 30);
        put(Item.SANDSTONE, 30);
        put(Item.STONE, 30);
        put(Item.PACKED_ICE, 50);
        put(Item.STAINED_HARDENED_CLAY, 50);
        put(Item.PRISMARINE, 70);
        put(Item.COAL_ORE, 100);
        put(Item.RED_SANDSTONE, 100);
        put(Item.QUARTZ_ORE, 100);
        put(Item.REDSTONE_ORE, 150);
        put(Item.END_STONE, 150);
        put(Item.OBSIDIAN, 150);
        put(Item.IRON_ORE, 250);
        put(Item.GOLD_ORE, 350);
        put(Item.LAPIS_ORE, 400);
        put(Item.DIAMOND_ORE, 750);
        put(Item.EMERALD_ORE, 1000);
    }};

    public MCMMO(Player player, ConfigSection cs) {
        p = player;
        Mining = cs.getInt("Mining");
        WoodCutting = cs.getInt("WoodCutting");
        Herbalism = cs.getInt("Herbalism");
        Excavation = cs.getInt("Excavation");
        Fishing = cs.getInt("Fishing");
        Unarmed = cs.getInt("Unarmed");
        Archery = cs.getInt("Archery");
        Swords = cs.getInt("Swords");
        Axes = cs.getInt("Axes");
        Taming = cs.getInt("Taming");
        Repair = cs.getInt("Repair");
        Acrobatics = cs.getInt("Acrobatics");
        Alchemy = cs.getInt("Alchemy");
        Slavage = cs.getInt("Slavage");
        Smelting = cs.getInt("Smelting");
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

    public void breakblock(BlockBreakEvent event) {
        int id = event.getBlock().getId();
        int xp = OreBreak.getOrDefault(id, 0);
        ArrayList<Item> drops = new ArrayList<>(Arrays.asList(event.getDrops()));
        int[][] d2 = event.getBlock().getDrops(event.getPlayer().getInventory().getItemInHand());
        int[][] d3 = event.getBlock().getDrops(event.getPlayer().getInventory().getItemInHand());
        for (int[] i : d2) {
            drops.add(Item.get(i[0], i[1], i[2]));
        }
        for (int[] i : d3) {
            drops.add(Item.get(i[0], i[1], i[2]));
        }
        drops.addAll(Arrays.asList());
        new BlockOreCoal().getDrops()
        event.getBlock().getDrops()
    }
}
