package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.level.Location;
import cn.nukkit.level.particle.BubbleParticle;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

import java.util.ArrayList;

public class HolyKnightHealPower extends Power {
    public HolyKnightHealPower(BaseClass b) {
        super(b, 75);
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.HolyKnightHeal;
    }

    @Override
    public Object usePower(CorePlayer cp, Object... args) {
        return null;
    }

    public ArrayList<CorePlayer> getPlayersInArea(Location pos) {
        ArrayList<CorePlayer> cpl = new ArrayList<>();
        String ffnn = getPlayer().Faction;
        if (ffnn == null) return cpl;
        Faction ff = CyberCoreMain.getInstance().FM.FFactory.getFaction(ffnn);
        if (ff == null) return cpl;
        for (Player p : pos.getLevel().getPlayers().values()) {
            if (p.distance(pos) <= getRange()) {
                if (p instanceof CorePlayer) {
                    CorePlayer cp = (CorePlayer) p;
                    String fn = cp.Faction;
                    if (fn != null) {
                        Faction f = CyberCoreMain.getInstance().FM.FFactory.getFaction(fn);
                        if (f != null) {
                            if (f.GetName().equalsIgnoreCase(ff.GetName()) || f.isAllied(ff)) cpl.add(cp);
                        }
                    }
                }
            }
        }
        return cpl;
    }

    @Override
    public void onTick(int tick) {
        super.onTick(tick);
        int cntr = 0;
        for (CorePlayer cp : getPlayersInArea(getPlayer())) {
            if (cp.getHealth() <= 4) {
                PlayerClass.takeXP(10 + (++cntr * 3));
                cp.heal(new EntityRegainHealthEvent(cp, getHealingAmount(), EntityRegainHealthEvent.CAUSE_MAGIC));
                cp.getLevel().addParticle(new BubbleParticle(cp));
                int xm = 5;
                int ym = 5;
                int zm = 5;
                for (int x = 0; x < xm; x++) {
                    for (int y = 0; y < ym; y++) {
                        for (int z = 0; z < zm; z++) {
                            cp.getLevel().addParticle(new BubbleParticle(cp.add(z, y, z)));
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Holy Knight";
    }

    public int getRange() {
        switch (getStage()) {
            case STAGE_1:
                return 7;
            case STAGE_2:
                return 8;
            case STAGE_3:
                return 9;
            case STAGE_4:
                return 10;
            case STAGE_5:
                return 11;
            case NA:
            default:
                return 7;
        }
    }

    public int getHealingAmount() {
        switch (getStage()) {
            case STAGE_1:
                return 2;
            case STAGE_2:
                return 2;
            case STAGE_3:
                return 3;
            case STAGE_4:
                return 4;
            case STAGE_5:
                return 5;
            case NA:
            default:
                return 7;
        }
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    protected int getCooldownTime() {
        return 30 + (10 * (6 - getStage().ordinal()));
    }
}
