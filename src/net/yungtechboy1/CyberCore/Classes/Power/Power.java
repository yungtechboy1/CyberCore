package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.math.NukkitRandom;

/**
 * Created by carlt on 5/16/2019.
 */
public class Power {
    public static int TNT_Specialist = 0;
    public static int MineLife = 1;
    int PowerSuccessChance = 100;
    int Level = 0;

    public Power(int psc, int lvl) {
        PowerSuccessChance = psc;
        Level = lvl;
    }

    public boolean CanRun() {
        return CanRun(false);
    }

    public boolean CanRun(boolean force) {
        NukkitRandom nr = new NukkitRandom();
        if (nr.nextRange(0, 100) <= PowerSuccessChance || force) {
            //Success
            return true;
        }
        return false;
    }


}
