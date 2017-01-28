package net.yungtechboy1.CyberCore.Classes;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Potion;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Abilities.ForestFire;
import net.yungtechboy1.CyberCore.Abilities.Tree_Feller;
import net.yungtechboy1.CyberCore.Abilities.Tree_Feller_Async;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/26/2017.
 */
public class Class_LumberJack extends BaseClass {
    public Block lb;

    HashMap<Integer, Integer> LumberJack_List = new HashMap<Integer, Integer>() {{
        put(Block.WOOD, 70);
        put(Block.WOOD2, 70);
        put(Block.LEAVE, 5);
        put(Block.LEAVE2, 5);
    }};

    public Class_LumberJack(CyberCoreMain main, Player player, ConfigSection cs) {
        super(main, player, cs);
    }

    @Override
    public void BlockBreakEvent(BlockBreakEvent event) {
        int xp = LumberJack_List.getOrDefault(event.getBlock().getId(), 0);
        addXP(xp);
    }

    @Override
    public String getName() {
        return "LumberJack";
    }

    @Override
    public ArrayList<Class> PossibleAbillity() {
        return new ArrayList<Class>() {{
            add(Tree_Feller.class);
            add(ForestFire.class);
        }};
    }

    @Override
    public void activateAbility() {
        super.activateAbility();
    }

    @Override
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        Item hand = event.getItem();
        if (isPrime() && !HasCooldown(PrimeKey)) {
            if (hand.isAxe()) {
                setPrime(false);
                activateAbility();
            }
        }
    }
}