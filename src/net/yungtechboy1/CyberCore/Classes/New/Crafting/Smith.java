package net.yungtechboy1.CyberCore.Classes.New.Crafting;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/13/2019.
 */
public class Smith extends BaseClass {
    public Smith(CyberCoreMain main, CorePlayer player, int mid, ConfigSection cs) {

        super(main, player, ClassType.Class_Miner_TNT_Specialist);
    }

    @Override
    public PrimalPowerType getPowerSourceType() {
        return null;
    }

    @Override
    public ClassType getTYPE() {
        return null;
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public void initBuffs() {

    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

}
