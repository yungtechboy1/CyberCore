package net.yungtechboy1.CyberCore.Commands.Abilities;

import cn.nukkit.Player;
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
    public Super_Breaker(CyberCoreMain main, Player p,int xp){
        super(main,p,Ability.TYPE_MINING,Ability.MINING_SUPER_BREAK,xp);
    }


    @Override
    public void BlockBreakEvent(BlockBreakEvent event) {
        Item hand = event.getPlayer().getinv;
    }
}
