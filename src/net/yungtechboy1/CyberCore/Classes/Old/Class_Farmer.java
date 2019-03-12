package net.yungtechboy1.CyberCore.Classes;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemApple;
import cn.nukkit.item.ItemEdible;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Potion;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.DyeColor;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Abilities.Green_Thumb;
import net.yungtechboy1.CyberCore.Abilities.Super_Breaker;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class Class_Farmer extends BaseClass {

    HashMap<Integer, Integer> HerbBreak = new HashMap<Integer, Integer>() {{
        put(Block.GRASS, 10);
        put(Block.VINE, 10);
        put(Block.PUMPKIN, 20);
        put(Block.MELON_BLOCK, 20);
        put(Block.SUGARCANE_BLOCK, 30);
        put(Block.WHEAT_BLOCK, 50);
        put(Block.CARROT_BLOCK, 50);
        put(Block.FLOWER, 100);
        put(Block.BROWN_MUSHROOM_BLOCK, 100);
        put(Block.RED_MUSHROOM_BLOCK, 100);
        put(Block.BROWN_MUSHROOM, 100);
        put(Block.RED_MUSHROOM, 100);
    }};


    public Class_Farmer(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, xp, cooldowns);
    }

    public Class_Farmer (CyberCoreMain main, Player player, ConfigSection cs){
        super(main,player,cs);
    }

    @Override
    public String getName() {
        return "Farmer";
    }

    @Override
    public void BlockBreakEvent(BlockBreakEvent event) {
        int id = event.getBlock().getId();
        int xp = HerbBreak.getOrDefault(id, 0);
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

    //@TODO Plant Seeds
    @Override
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        super.BlockPlaceEvent(event);
    }

    @Override
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        Item hand = event.getItem();
        if (hand.getId() == Item.DYE && hand.getDamage() == 0x0f) {//BoneMeal
            addXP(100);
        } else if (isPrime() && !HasCooldown(PrimeKey)) {
            if (hand.isSword()) {
                setPrime(false);
                activateAbility();
            }
        }
    }

    @Override
    public void CraftItemEvent(CraftItemEvent event) {
       Recipe r = event.getRecipe();
        if(r.getResult() instanceof ItemEdible){
            addXP(25);
            if(r.getResult().getId() == Item.COOKED_PORKCHOP || r.getResult().getId() == Item.COOKED_CHICKEN){
                int chance = 0;
                int rand = 100;
                int XL = XPToLevel(getXP());

                //Handle Double Drops
                chance = Math.max(XL, 1);
                rand = new NukkitRandom(getXP()).nextRange(0, 1000);
                if (chance > rand){
                    r.getResult().setDamage(Math.max(XL/250,1));
                }
            }
        }
    }

    @Override
    public ArrayList<Ability> PossibleAbillity() {
        ArrayList<Ability> a = new ArrayList<Ability>();
        //@TODO Auto Plant
        //a.add(new Green_Thumb(CCM,this));
        return a;
    }

    @Override
    public void activateAbility() {
        super.activateAbility();
    }
}
