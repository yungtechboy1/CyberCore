package net.yungtechboy1.CyberCore.Classes.New.Minner;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Passive.OreKnowledge;
import net.yungtechboy1.CyberCore.Classes.Power.MineLifePower;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class MineLifeClass extends MinnerBaseClass {
    public MineLifeClass(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        super(main, player,data);
    }
//
//    public MineLifeClass(CyberCoreMain main, CorePlayer player) {
//        super(main, player);
//    }

    @Override
    public void SetPowers() {
        addPossiblePower(new MineLifePower(this));
        addPossiblePower(new OreKnowledge(this));
    }



//    public Object RunPower(int powerid, Item itemInHand, Block target, double breakTime) {
//        PowerAbstract p = ActivePowers.get(powerid);
//        if (p == null) {
//            CCM.getLogger().error("No PowerAbstract found or Incorrect Args For MineLife E334221");
//            return null;
//        }
//        if (powerid == PowerAbstract.MineLife && p instanceof MineLifePower) {
//            MineLifePower mlp = (MineLifePower) p;
//            return mlp.usePower(itemInHand, target, breakTime);
//        }
//        return breakTime;
//    }

//    @Override
//    public Object RunPower(int powerid, Object... args) {
//        PowerAbstract p = ActivePowers.get(powerid);
//        if (p == null || args.length != 3) {
//            CCM.getLogger().error("No PowerAbstract found or Incorrect Args For MineLife E334221");
//            return -1;
//        }
//        if (powerid == 1 && p instanceof MineLifePower) {
//            MineLifePower mlp = (MineLifePower) p;
//            return mlp.usePower((Item) args[0], (Block) args[1], (double) args[2]);
//        }
//        return (double) args[2];
//    }

//    @Override
//    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {
//        event = super.BlockBreakEvent(event);
//        if (TryRunPower(PowerAbstract.OreKnowledge)) {
//            //Run
//
//        }
//
//        return event;
//    }

}
