package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Thief extends BaseClass {
    public Thief(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, BaseClass.TYPE_Offensive_Thief, xp, cooldowns);
    }

    public Thief(CyberCoreMain main, Player player, ConfigSection cs) {
        super(main, player, BaseClass.TYPE_Offensive_Thief, cs);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {
        Player p = (Player) event.getEntity();
        Item i = p.getInventory().getItemInHand();
        //TODO Check to see if item is a Dagger/Short Sword
//        if()
        return event;
    }

    @Override
    public String getName() {
        return "Thief";
    }


    @Override
    public int getMainID() {
        return BaseClass.TYPE_Offensive_Thief;
    }
}
