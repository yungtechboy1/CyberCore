package net.yungtechboy1.CyberCore.Classes.Old;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Abilities.Super_Breaker;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/26/2017.
 */
public class Class_Miner extends BaseClass {
    HashMap<Integer, Integer> OreBreak = new HashMap<Integer, Integer>() {{
        put(Block.GOLD_ORE, 50);
        put(Block.HARDENED_CLAY, 30);
        put(Block.MOSS_STONE, 15);
        put(Block.NETHERRACK, 3);
        put(Block.SANDSTONE, 10);
        put(Block.STONE, 5);
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


    public Class_Miner(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, xp, cooldowns);
    }

    public Class_Miner(CyberCoreMain main, Player player, ConfigSection cs) {
        super(main, player, cs);
    }

    @Override
    public String getName() {
        return "Miner";
    }

    @Override
    public void BlockBreakEvent(BlockBreakEvent event) {
        super.BlockBreakEvent(event);
        int id = event.getBlock().getId();
        int xp = OreBreak.getOrDefault(id, 0);
        addXP(xp);

        Block block = event.getBlock();
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

    @Override
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if(isPrime()){
            Item hand = event.getItem();
            if(hand.isPickaxe()){
                setPrime(false);
                activateAbility();
            }
        }
    }

    @Override
    public ArrayList<Ability> PossibleAbillity() {
        ArrayList<Ability> a = new ArrayList<Ability>();
        a.add(new Super_Breaker(CCM,this));
        return a;
    }

    @Override
    public void activateAbility() {
        super.activateAbility();
    }
}
