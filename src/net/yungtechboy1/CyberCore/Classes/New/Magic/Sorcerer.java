package net.yungtechboy1.CyberCore.Classes.New.Magic;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.New.DeBuff;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

public class Sorcerer extends BaseClass {
    public Sorcerer(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        super(main, player,data);
    }

    public Sorcerer(CyberCoreMain main, CorePlayer player) {
        this(main, player, null);
    }

    @Override
    public PrimalPowerType getPowerSourceType() {
        return PrimalPowerType.Moon;
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public ArrayList<AdvancedPowerEnum> getDefaultPowers() {
        ArrayList<AdvancedPowerEnum> pe = new ArrayList<>();
//        pe.add(PowerEnum.KnightSandShield);
//        pe.add(PowerEnum.DoubleTime);
//        pe.add(PowerEnum.AntidotePower);
        return pe;
    }

    @Override
    public void initBuffs() {
        addDeBuff(new DeBuff(DeBuff.BuffType.Magic, .8f));
        addBuff(new Buff(Buff.BuffType.DamageToEntity, 1.1f));
        addDeBuff(new DeBuff(DeBuff.BuffType.Health,2f));
    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }

    @Override
    public ClassType getTYPE() {
        return ClassType.Class_Magic_Sorcerer;
    }

    @Override
    public String getName() {
        return null;
    }

}