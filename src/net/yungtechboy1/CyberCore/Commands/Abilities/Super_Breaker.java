package net.yungtechboy1.CyberCore.Commands.Abilities;

import cn.nukkit.block.BlockOreCoal;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by carlt_000 on 1/25/2017.
 */
public class Super_Breaker extends Ability {
    public Super_Breaker(CyberCoreMain main, int xp){
        super(main,Ability.TYPE_MINING,Ability.MINING_SUPER_BREAK,xp);
    }

    @Override
    public void BlockBreakEvent(BlockBreakEvent event) {
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
