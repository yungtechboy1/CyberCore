package net.yungtechboy1.CyberCore.Classes.New.Crafting;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/13/2019.
 */
public class Crafter extends BaseClass{
    public Crafter(CyberCoreMain main, Player player, int mid, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, mid, rank, xp, cooldowns);
    }

    public Crafter(CyberCoreMain main, Player player, int mid, ConfigSection cs) {
        super(main, player, mid, cs);
    }
}
