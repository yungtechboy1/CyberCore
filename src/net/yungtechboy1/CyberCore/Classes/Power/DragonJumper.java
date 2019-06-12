package net.yungtechboy1.CyberCore.Classes.Power;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Offense.DragonSlayer;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

public class DragonJumper extends PowerStackable {
    /**
     * @param c
     * @param aq
     * @param maq
     */
    public DragonJumper(DragonSlayer c, int aq, int maq) {
        super(c, 35, aq, maq);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PlayerJumpEvent PlayerJumpEvent(PlayerJumpEvent e) {
        InitPowerRun();
        return super.PlayerJumpEvent(e);
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.DragonJumper;
    }

    @Override
    public Object usePower(CorePlayer cp, Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return "Dragon Jumper";
    }
}
