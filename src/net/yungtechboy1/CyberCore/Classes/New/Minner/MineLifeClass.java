package net.yungtechboy1.CyberCore.Classes.New.Minner;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.Passive.OreKnowledge;
import net.yungtechboy1.CyberCore.Classes.Power.MineLifePower;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class MineLifeClass extends MinnerBaseClass {
    public MineLifeClass(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        super(main, player, ClassType.Class_Miner_MineLife, data);
    }
//
//    public MineLifeClass(CyberCoreMain main, CorePlayer player) {
//        super(main, player);
//    }

    @Override
    public void SetPowers() {
        addPower(new MineLifePower(this));
        addPower(new OreKnowledge(this));
    }



//    public Object RunPower(int powerid, Item itemInHand, Block target, double breakTime) {
//        PowerPublicInterface p = Powers.get(powerid);
//        if (p == null) {
//            CCM.getLogger().error("No PowerPublicInterface found or Incorrect Args For MineLife E334221");
//            return null;
//        }
//        if (powerid == PowerPublicInterface.MineLife && p instanceof MineLifePower) {
//            MineLifePower mlp = (MineLifePower) p;
//            return mlp.usePower(itemInHand, target, breakTime);
//        }
//        return breakTime;
//    }

//    @Override
//    public Object RunPower(int powerid, Object... args) {
//        PowerPublicInterface p = Powers.get(powerid);
//        if (p == null || args.length != 3) {
//            CCM.getLogger().error("No PowerPublicInterface found or Incorrect Args For MineLife E334221");
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
//        if (TryRunPower(PowerPublicInterface.OreKnowledge)) {
//            //Run
//
//        }
//
//        return event;
//    }

}
