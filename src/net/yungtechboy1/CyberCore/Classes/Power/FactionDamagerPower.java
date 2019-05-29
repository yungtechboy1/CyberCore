package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageByChildEntityEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

public class FactionDamagerPower extends PassivePowerEvent {
    public FactionDamagerPower(CorePlayer cp, int lvl) {
        super(lvl,cp);

    }

    @Override
    public PowerType getType() {
        return PowerType.FactionDamager;
    }

    @Override
    public boolean CanRun(boolean force) {
        if(!super.CanRun(force) || Player == null)return false;
        FactionsMain fm = CyberCoreMain.getInstance().FM;
        int xx = Player.getChunkX();
        int zz = Player.getChunkZ();
        Faction f = fm.FFactory.GetOwnedChunk(xx,zz);
        if(f == null)return false;

        return true;
    }

    @Override
    public EntityDamageByEntityEvent EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof cn.nukkit.Player) {
            int xx = event.getEntity().getChunkX();
            int zz = event.getEntity().getChunkZ();
            Faction f = CyberCoreMain.getInstance().FM.FFactory.GetPlotStatus(xx, zz);
            if (f != null) StartPower(Player);
        }
        return event;
    }
}
