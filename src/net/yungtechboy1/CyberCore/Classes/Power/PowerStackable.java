package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.math.NukkitRandom;
import net.yungtechboy1.CyberCore.CorePlayer;

public abstract class PowerStackable extends Power {
    int AvailbleQuantity = 0;
    int MaxAvailbleQuantity = 0;

    public PowerStackable(CorePlayer cp, int lvl, int aq, int maq) {
        this(cp,100,lvl,aq,maq);

    }
    /**
     * @param psc int Power Success Chance
     * @param aq
     * @param maq
     */
    public PowerStackable(CorePlayer cp,int psc, int lvl, int aq, int maq) {
        super(psc, lvl, cp);
        AvailbleQuantity = aq;
        MaxAvailbleQuantity = maq;
    }

    public int getMaxAvailbleQuantity() {
        return MaxAvailbleQuantity;
    }

    public void AddAvailbleQuantity(int i) {
        if(AvailbleQuantity >= MaxAvailbleQuantity)return;
        AvailbleQuantity = AvailbleQuantity + i;
    }

    public void AddAvailbleQuantity() {
        if(AvailbleQuantity >= MaxAvailbleQuantity)return;
        AvailbleQuantity++;
    }

    public void TakeAvailbleQuantity() {
        AvailbleQuantity--;
    }

    public void TakeAvailbleQuantity(int a) {
        AvailbleQuantity = AvailbleQuantity - a;
    }

    public int getAvailbleQuantity() {
        return AvailbleQuantity;
    }

    @Override
    public PowerType getType() {
        return PowerType.PowerStackable;
    }

    @Override
    public boolean CanRun() {
        return CanRun(false);
    }

    @Override
    public boolean CanRun(boolean force) {
        NukkitRandom nr = new NukkitRandom();
        if (nr.nextRange(0, 100) <= PowerSuccessChance || force) {
            //Success
            if (getAvailbleQuantity() <= 0) return false;
            return true;
        }
        return false;
    }

}
