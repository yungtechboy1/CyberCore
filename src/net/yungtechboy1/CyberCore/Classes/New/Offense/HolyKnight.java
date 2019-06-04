package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.DeBuff;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class HolyKnight extends BaseClass {
    public HolyKnight(CyberCoreMain main, CorePlayer player, ClassType rank, ConfigSection data) {
        super(main, player, rank, data);
    }

    public HolyKnight(CyberCoreMain main, CorePlayer player, ClassType rank) {
        super(main, player, rank);
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public void initBuffs() {
        addBuff(new Buff(Buff.BuffType.DamageToEntity,1.2f));
        addBuff(new Buff(Buff.BuffType.Magic,.75f));
//        addBuff(new Buff(Buff.BuffType.Armor,1.3f));
//        addBuff(new Buff(Buff.BuffType.Health,4f));
        addDeBuff(new DeBuff(Buff.BuffType.Movement,.8f));
//        addBuff(new Buff(Buff.BuffType.SwingSpeed,1.5f));
//        addBuff(new Buff(Buff.BuffType.SuperFoodHeartRegin,1f));
    }

    @Override
    public Object RunPower(int powerid, Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return "Holy Knight";
    }
}
