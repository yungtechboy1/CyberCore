package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Assassin extends BaseClass {
    public Assassin(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, BaseClass.TYPE_Offensive_Assassin, xp, cooldowns);
    }

    public Assassin(CyberCoreMain main, Player player, ConfigSection cs) {
        super(main, player, BaseClass.TYPE_Offensive_Assassin, cs);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {

        return event;
    }

    @Override
    public String getName() {
        return "Assassin";
    }

    @Override
    public int getMainID() {
        return BaseClass.TYPE_Offensive_Assassin;
    }
}
