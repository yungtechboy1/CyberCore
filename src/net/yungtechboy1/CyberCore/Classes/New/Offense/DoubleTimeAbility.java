package net.yungtechboy1.CyberCore.Classes.New.Offense;

import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.PowerAbilityHotBar;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.ClassLevelingManagerXPLevel;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class DoubleTimeAbility extends PowerAbilityHotBar {
    public DoubleTimeAbility(Knight knight) {
        super(knight,new ClassLevelingManagerXPLevel(),75,10);
    }

    private Buff oldbuff = null;

    @Override
    public void onActivate() {
        getPlayer().sendMessage("TUNNING");
//        getPlayer().addTemporaryBuff(new Buff);
//        oldbuff = PlayerClass.getBuff(Buff.BuffType.Movement);
//        PlayerClass.addBuff(new Buff(Buff.BuffType.Movement,1.1f));
//        PlayerClass.initBuffs();
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return null;
    }


    @Override
    public PowerEnum getType() {
        return PowerEnum.DoubleTime;
    }

    @Override
    public String getName() {
        return "Double Time";
    }

    @Override
    public void onAbilityActivate() {
        getPlayer().sendMessage("ON ACTIVATE");
    }

    @Override
    public void whileAbilityActive() {

        getPlayer().sendMessage("WHILE ACTIVATE");
    }

    @Override
    public void onAbilityDeActivate() {

        getPlayer().sendMessage("ON DEACTIVATE");
    }

    @Override
    public boolean canUpdateHotBar(int tick) {
        return true;
    }
}
