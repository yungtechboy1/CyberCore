package net.yungtechboy1.CyberCore.Classes.Power.Mercenary;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.PowerAbility;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;

public class MercenaryDoubleTake extends PowerAbility {
    public MercenaryDoubleTake(BaseClass bc, int lvl) {
        super(bc,100, lvl);
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
}
