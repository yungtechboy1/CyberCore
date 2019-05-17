package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class MineLifePower extends Power {
    public MineLifePower(int level) {
        super(((int) Math.floor(.65d*level)+1),level);
        int psc = ((int) Math.floor(.65d*level)+1);
    }

    private double GetBreakTime(Item itemInHand, Block target, double cbreakTime){
        CyberCoreMain.getInstance().getLogger().info("BREAKTIMMMMM >> "+cbreakTime);
        if(itemInHand == null || target == null || cbreakTime < .5d)return cbreakTime;
        double fbreaktime = cbreakTime;
        int l = (int)Math.floor(Level/10);
        fbreaktime *= (1-((Level/100d)/2));
        CyberCoreMain.getInstance().getLogger().info("NEWE BREAKTIMMMMM >> "+fbreaktime);
        return fbreaktime;
    }

    public Object UsePower(Item itemInHand, Block target, double cbreakTime) {
       return GetBreakTime(itemInHand,target,cbreakTime);

    }
    @Override
    public Object UsePower(Object... args) {
        return super.UsePower(args);
    }
}
