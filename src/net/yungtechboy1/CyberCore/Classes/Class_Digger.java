package net.yungtechboy1.CyberCore.Classes;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.item.*;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Abilities.Super_Breaker;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/28/2017.
 */
public class Class_Digger  extends BaseClass {


    public Class_Digger(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, xp, cooldowns);
    }

    public Class_Digger (CyberCoreMain main, Player player, ConfigSection cs){
        super(main,player,cs);
    }

    @Override
    public ArrayList<Ability> PossibleAbillity() {
       return new ArrayList<Ability>();
    }

    @Override
    public void BlockBreakEvent(BlockBreakEvent event) {
        ExcavationBreakEvent(event);
        int id = event.getBlock().getId();
        int xp = Excavation.getOrDefault(id, 0);
        addXP(xp);

        //Double Drop
        int chance = 0;
        int rand = 100;
        ArrayList<Item> nl = new ArrayList<>(Arrays.asList(event.getDrops()));
        int XL = XPToLevel(getXP());

        //Handle Double Drops
        chance = Math.max(XL, 1);
        rand = new NukkitRandom(getXP()).nextRange(0, 1000);
        if (chance > rand) {
            for(Item i: nl){
                i.setCount(i.getCount()+1);
            }
        }
        event.setDrops(nl.toArray(new Item[nl.size()]));
    }

    /**
     * And Passive Events!
     *
     * @param event
     */

    public void ExcavationBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        int chance = 0;
        int rand = 100;
        ArrayList<Item> nl = new ArrayList<>(Arrays.asList(event.getDrops()));
        int XL = XPToLevel(getXP());

        //Handle Double Drops
        chance = Math.max(getXP(),1);
        rand = new NukkitRandom(getXP()).nextRange(0, 1000);
        if (chance > rand) {
            ArrayList<Item> nl2 = new ArrayList<>(nl);
            nl.addAll(nl2);
        }

        switch (block.getId()) {//GLOWSTONE - LVL 25
            case Block.GRASS:
            case Block.MYCELIUM:
            case Block.DIRT:
            case Block.SAND:
                if (XL >= 25) {
                    chance = Math.max(((XL / 10) - 10), 5);
                    rand = new NukkitRandom(getXP()).nextRange(0, 100);
                    if (chance > rand) {
                        nl.add(new ItemGlowstoneDust(0, 1));
                        addXP(80);
                    }
                }
                break;
        }

        if (block.getId() == Block.GRAVEL && XL >= 75) {// - LVL 75
            chance = Math.max(((XL / 10) - 10), 10);
            rand = new NukkitRandom(getXP()).nextRange(0, 100);
            if (chance > rand) {
                nl.add(new ItemGunpowder(0, 1));
                addXP(30);
            }
        }

        if (block.getId() == Block.CLAY_BLOCK && XL >= 150) {// - LVL 150
            chance = Math.max(((XL / 10) - 10), 1);
            rand = new NukkitRandom(getXP()).nextRange(0, 100);
            if (chance > rand) {
                nl.add(new ItemSlimeball(0, 1));
                addXP(10);
            }
        }

        if (block.getId() == Block.GRAVEL && XL >= 175) {// - LVL 175
            chance = Math.max(((XL / 10) - 10), 1);
            rand = new NukkitRandom(getXP()).nextRange(0, 100);
            if (chance > rand) {
                nl.add(new ItemSlimeball(0, 1));
                addXP(10);
            }
        }

        // LVL 250 Group ////////////////////////////////////////////
        if (XL >= 250) {
            if (block.getId() == Block.GRASS) {
                chance = Math.max(((XL / 10) - 10), 1);
                rand = new NukkitRandom(getXP()).nextRange(0, 100);
                if (chance > rand) {
                    nl.add(new ItemEgg(0, 1));
                    addXP(100);
                }
            }
            if (block.getId() == Block.GRASS || block.getId() == Block.MYCELIUM) {
                chance = Math.max(((XL / 10) - 10), 1);
                rand = new NukkitRandom(getXP()).nextRange(0, 1000);
                if (chance > rand) {
                    nl.add(new ItemApple(0, 1));
                    addXP(100);
                }
            }
            if (block.getId() == Block.CLAY_BLOCK) {
                chance = Math.max(((XL / 10) - 10), 5);
                rand = new NukkitRandom(getXP()).nextRange(0, 100);
                if (chance > rand) {
                    nl.add(new ItemString(0, 1));
                    addXP(200);
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
                    rand = new NukkitRandom(getXP()).nextRange(0, 100);
                    if (chance > rand) {
                        nl.add(new Item(Item.COCOA,0, 1));
                        addXP(80);
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
                    rand = new NukkitRandom(getXP()).nextRange(0, 500);
                    if (chance > rand) {
                        nl.add(new ItemGlowstoneDust(0, 1));
                        addXP(80);
                    }
            }
        }

        if (block.getId() == Block.CLAY_BLOCK && XL >= 500) {// - LVL 500
            chance = Math.max(((XL / 10) - 10), 1);
            rand = new NukkitRandom(getXP()).nextRange(0, 1000);
            if (chance > rand) {
                nl.add(new ItemClock(0, 1));
                addXP(10);
            }
            chance = Math.max(((XL / 10) - 10), 1);
            rand = new NukkitRandom(getXP()).nextRange(0, 1000);
            if (chance > rand) {
                nl.add(new ItemBucket(0, 1));
                addXP(10);
            }
        }
    }

    @Override
    public String getName() {
        return "Digger";
    }
}