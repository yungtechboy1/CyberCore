package net.yungtechboy1.CyberCore.Classes.New.Minner;

import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.TNTSpecialistPower;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

/**
 * Created by carlt on 5/16/2019.
 */
public class TNTSpecialist extends BaseClass {
    public TNTSpecialist(CyberCoreMain main, CorePlayer player, int xp, ArrayList<Integer> cooldowns) {
        super(main, player, ClassType.Class_Miner_TNT_Specialist, xp, cooldowns);
        SetPowers();
    }

    public TNTSpecialist(CyberCoreMain main, CorePlayer player) {
        this(main, player,0,null);
    }

    public int GetMaxTNTPower() {
        double mi = getLVL() / 10;
        int m = (int) Math.round(mi);
        switch (m) {
            case 0:
            case 1:
            case 2:
                return 10;
            default:
                return 10;
        }
    }

    public void SetPowers() {
        AddPower(new TNTSpecialistPower(0,GetMaxTNTPower()));
    }

    @Override
    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {
        event = super.BlockBreakEvent(event);
        Item hand = getPlayer().getInventory().getItemInHand();
        if (hand.isPickaxe()) {

        }


        return event;
    }

    @Override
    public ArrayList<Ability> PossibleAbillity() {
        ArrayList<Ability> a = new ArrayList<Ability>();
        a.add(new)
        return a;
    }
}