package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.math.NukkitRandom;

public class PowerStackable extends Power {
    int AvailbleQuantity = 0;
    int MaxAvailbleQuantity = 0;

    /**
     * @param psc int Power Success Chance
     * @param aq
     * @param maq
     */
    public PowerStackable(int psc,int lvl, int aq, int maq) {
        super(psc,lvl);
        AvailbleQuantity = aq;
        MaxAvailbleQuantity = maq;
    }

    public void AddAvailbleQuantity(int i) {
        AvailbleQuantity = AvailbleQuantity + i;
    }

    public void AddAvailbleQuantity() {
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
    public boolean CanRun() {
        return CanRun(false);
    }

    @Override
    public boolean CanRun(boolean force) {
        NukkitRandom nr = new NukkitRandom();
        if (nr.nextRange(0, 100) <= PowerSuccessChance || force) {
            //Success
            if(getAvailbleQuantity() <= 0)return false;
            return true;
        }
        return false;
    }

}
