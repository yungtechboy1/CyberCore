package net.yungtechboy1.CyberCore.Classes.Power.Mercenary;

import cn.nukkit.event.entity.EntityRegainHealthEvent;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.PowerAbility;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;

public class MercenaryDoubleTake extends PowerAbility {
    public MercenaryDoubleTake(BaseClass bc, int lvl) {
        super(bc,20, lvl);
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MercenaryDoubleTake;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDispalyName() {
        return null;
    }

    @Override
    public boolean CanRun(boolean force, Object... args) {
        if(force)return true;
        super.CanRun(force,args);
        CorePlayer cp = getPlayer();
        if(cp.getHealth() > 8)return false;
        return true;
    }

    @Override
    public int getRunTimeTick() {
        return 20*30;
    }

    @Override
    public void onAbilityActivate() {
        EntityRegainHealthEvent e = new EntityRegainHealthEvent(getPlayer(),1, EntityRegainHealthEvent.CAUSE_REGEN);
        getPlayer().heal(e);
    }

    @Override
    public void onAbilityDeActivate() {

    }
}
