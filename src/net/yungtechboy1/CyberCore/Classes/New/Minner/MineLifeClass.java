package net.yungtechboy1.CyberCore.Classes.New.Minner;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.Power.MineLifePower;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.Classes.Power.TNTSpecialistPower;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class MineLifeClass extends  MinnerBaseClass{
    public MineLifeClass(CyberCoreMain main, CorePlayer player, ClassType t, ConfigSection data) {
        super(main, player, t, data);
    }
//
//    public MineLifeClass(CyberCoreMain main, CorePlayer player) {
//        super(main, player);
//    }

    @Override
    public void SetPowers() {
        Powers.add(Power.MineLife, new MineLifePower(getLVL()));
    }

    @Override
    public boolean TryRunPower(int powerid){
        Power p = Powers.get(powerid);
        if(p == null)return false;
        return p.CanRun();
    }

    @Override
    public void RunPower(int powerid){

        Power p = Powers.get(powerid);
        if(p == null)return;

    }



//    public double RunPower(int mineLife, Item itemInHand, Block target, double breakTime) {
    public double RunPower(int powerid, Object ...args) {
        Power p = Powers.get(powerid);
        if(p == null || args.length != 3 ){
            CCM.getLogger().error("No Power found or Incorrect Args For MineLife E334221");
            return -1;
        }
        if(powerid == 1 && p instanceof MineLifePower){
            MineLifePower mlp = (MineLifePower) p;
            return mlp.GetBreakTime((Item)args[0],(Block)args[1],(double)args[2]);
        }
        return (double)args[2];
    }
}
