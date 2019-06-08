package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.DeBuff;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.TNTAirStrikePower;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class DragonSlayer extends BaseClass {
    public DragonSlayer(CyberCoreMain main, CorePlayer player, ClassType rank, ConfigSection data) {
        super(main, player, rank, data);
    }

    public DragonSlayer(CyberCoreMain main, CorePlayer player, ClassType rank) {
        super(main, player, rank);
    }

    @Override
    public ClassType getTYPE() {
        return ClassType.DragonSlayer;
    }

    @Override
    public void SetPowers() {
        AddPower(new TNTAirStrikePower(this,getLVL()));
    }

    @Override
    public void initBuffs() {
        addBuff(new Buff(Buff.BuffType.Jump,3f));
        addDeBuff(new DeBuff(Buff.BuffType.Movement,.9f));
//        addBuff(new Buff(Buff.BuffType.Armor,1.3f));
//        addBuff(new Buff(Buff.BuffType.Armor,1.3f));
//        addBuff(new Buff(Buff.BuffType.Health,4f));
//        addBuff(new Buff(Buff.BuffType.SwingSpeed,1.5f));
//        addBuff(new Buff(Buff.BuffType.SuperFoodHeartRegin,1f));
    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return "Dragon Slayer";
    }
}
