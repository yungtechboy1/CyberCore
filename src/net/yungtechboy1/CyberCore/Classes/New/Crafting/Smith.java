package net.yungtechboy1.CyberCore.Classes.New.Crafting;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/13/2019.
 */
public class Smith extends BaseClass {

    public Smith(CyberCoreMain main, CorePlayer player, ClassType rank, ConfigSection data) {
        super(main, player, rank, data);
    }

    public Smith(CyberCoreMain main, CorePlayer player, ClassType rank) {
        super(main, player, rank);
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public Object RunPower(Power.PowerType powerid, Object... args) {
        return null;
    }

}
