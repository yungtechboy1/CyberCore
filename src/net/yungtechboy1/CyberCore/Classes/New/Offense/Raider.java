package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

public class Raider extends BaseClass {
    public Raider(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, BaseClass.TYPE_Offensive_Raider, xp, cooldowns);
    }

    public Raider(CyberCoreMain main, Player player, ConfigSection cs) {
        super(main, player, BaseClass.TYPE_Offensive_Raider, cs);
    }

    public CustomEntityDamageEvent CustomEntityDamageEvent(CustomEntityDamageEvent event) {
        Player p = (Player)event.entity;
        int x = p.getFloorX() >> 4;
        int z = p.getFloorZ() >> 4;
        if(CyberCoreMain.getInstance().FM.FFactory.PlotsList.containsKey(x+"|"+z)){
            float ad = event.getDamage(CustomEntityDamageEvent.CustomDamageModifier.BASE) * -.1f;
            event.setDamage(ad, CustomEntityDamageEvent.CustomDamageModifier.ARMOR);
        }
        return event;
    }

    @Override
    public String getName() {
        return "Raider";
    }

    @Override
    public int getMainID() {
        return BaseClass.TYPE_Offensive_Raider;
    }
}
