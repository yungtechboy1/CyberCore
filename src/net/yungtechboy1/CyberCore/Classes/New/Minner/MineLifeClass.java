package net.yungtechboy1.CyberCore.Classes.New.Minner;

import cn.nukkit.block.Block;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.Abilities.Ability;
import net.yungtechboy1.CyberCore.Classes.Passive.OreKnowledge;
import net.yungtechboy1.CyberCore.Classes.Power.MineLifePower;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class MineLifeClass extends MinnerBaseClass {
    public MineLifeClass(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        super(main, player, ClassType.Miner_MineLife, data);
    }
//
//    public MineLifeClass(CyberCoreMain main, CorePlayer player) {
//        super(main, player);
//    }

    @Override
    public void SetPowers() {
        Powers.add(Power.MineLife, new MineLifePower(getPlayer(),getLVL()));
        Powers.add(Power.OreKnowledge, new OreKnowledge(getLVL(),getPlayer()));
    }

    @Override
    public boolean TryRunPower(Power.PowerType powerid) {
        Power p = GetPower(powerid);
        if (p == null) return false;
        return p.CanRun();
    }


    public Object RunPower(Power.PowerType powerid, Item itemInHand, Block target, double breakTime) {
        Power p = GetPower(powerid);
        if (p == null) {
            CCM.getLogger().error("No Power found or Incorrect Args For MineLife E334221");
            return null;
        }
        if (powerid == Power.PowerType.MineLife && p instanceof MineLifePower) {
            MineLifePower mlp = (MineLifePower) p;
            return mlp.usePower(itemInHand, target, breakTime);
        }
        return breakTime;
    }

    @Override
    public Object RunPower(Power.PowerType powerid, Object... args) {
        Power p = GetPower(powerid);
        if (p == null || args.length != 3) {
            CCM.getLogger().error("No Power found or Incorrect Args For MineLife E334221");
            return -1;
        }
        if (powerid.ordinal() == 1 && p instanceof MineLifePower) {
            MineLifePower mlp = (MineLifePower) p;
            return mlp.usePower((Item) args[0], (Block) args[1], (double) args[2]);
        }
        return (double) args[2];
    }

    @Override
    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {
        event = super.BlockBreakEvent(event);
        if (TryRunPower(Power.PowerType.OreKnowledge)) {
            //Run

        }

        return event;
    }

}
