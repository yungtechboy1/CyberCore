package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Thief extends BaseClass {
    public Thief(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, xp, cooldowns);
    }

    public Thief(CyberCoreMain main, Player player, ConfigSection cs) {
        super(main, player, cs);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {
        Player p = (Player)event.getEntity();
        int x = p.getFloorX() >> 4;
        int z = p.getFloorZ() >> 4;
        if(CyberCoreMain.getInstance().FM.FFactory.PlotsList.containsKey(x+"|"+z)){
            float bd = event.getDamage(EntityDamageEvent.MODIFIER_BASE) * .90f;
            event.setDamage(bd,EntityDamageEvent.MODIFIER_BASE);
        }
        return event;
    }

    @Override
    public String getName() {
        return "Raider";
    }
}
