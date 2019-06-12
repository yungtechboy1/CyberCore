package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.math.NukkitRandom;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

public abstract class PowerStackable extends Power {
    int AvailbleQuantity = 0;
    int MaxAvailbleQuantity = 0;

    /**
     * @param psc int Power Success Chance
     * @param aq
     * @param maq
     */
    public PowerStackable(BaseClass c, int psc, int aq, int maq) {
        super(c, psc);
        AvailbleQuantity = aq;
        MaxAvailbleQuantity = maq;
    }

    public int getMaxAvailbleQuantity() {
        return MaxAvailbleQuantity;
    }

    public void AddAvailbleQuantity(int i) {
        if (AvailbleQuantity >= MaxAvailbleQuantity) return;
        AvailbleQuantity = AvailbleQuantity + i;
    }

    public void AddAvailbleQuantity() {
        if (AvailbleQuantity >= MaxAvailbleQuantity) return;
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
    public boolean CanRun(boolean force, Object... o) {
        if(!hasAvailableQuantity())return false;
        NukkitRandom nr = new NukkitRandom();
        if (nr.nextRange(0, 100) <= getPowerSuccessChance() || force) {
            //Success
            if (getAvailbleQuantity() <= 0) return false;
            return true;
        }
        return false;
    }

    private boolean hasAvailableQuantity() {
        return getAvailbleQuantity() > 0;
    }


}
