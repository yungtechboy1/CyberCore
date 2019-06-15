package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.DeBuff;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.DrakKnightPoisonousStench;
import net.yungtechboy1.CyberCore.Classes.Power.TNTAirStrikePower;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class DarkKnight extends BaseClass {
    public DarkKnight(CyberCoreMain main, CorePlayer player, ClassType rank, ConfigSection data) {
        super(main, player, rank, data);
    }

    public DarkKnight(CyberCoreMain main, CorePlayer player, ClassType rank) {
        super(main, player, rank);
    }

    @Override
    public PrimalPowerType getPowerSourceType() {
        return null;
    }

    @Override
    public ClassType getTYPE() {
        return ClassType.Class_Offense_Dark_Knight;
    }

    @Override
    public void SetPowers() {
        addPower(new TNTAirStrikePower(this));
        addPower(new DrakKnightPoisonousStench(this));
    }

    @Override
    public void initBuffs() {
        addBuff(new Buff(Buff.BuffType.DamageToPlayer,1.2f));
        addDeBuff(new DeBuff(Buff.BuffType.Magic,.8f));
//        addBuff(new Buff(Buff.BuffType.Armor,1.3f));
//        addBuff(new Buff(Buff.BuffType.Armor,1.3f));
//        addBuff(new Buff(Buff.BuffType.Health,4f));
        addDeBuff(new DeBuff(Buff.BuffType.Movement,.8f));
//        addBuff(new Buff(Buff.BuffType.SwingSpeed,1.5f));
//        addBuff(new Buff(Buff.BuffType.SuperFoodHeartRegin,1f));
    }

    @Override
    public String getDisplayName() {
        return TextFormat.BLACK+"||"+getName()+"||";
    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return "Dark Knight";
    }

//    @Override
//    public String FormatHudText() {
//        String f = "";
//        int lvl = XPToLevel(getXP());
//        String pclass = getDisplayName();
//        int pxp = XPRemainder(getXP());
//        int pxpof = calculateRequireExperience(lvl + 1);
//        int plvl = lvl;
//        f += TextFormat.AQUA + pclass + TextFormat.GRAY + " | " + TextFormat.GREEN + pxp + TextFormat.AQUA + " / " + TextFormat.GOLD + pxpof + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + plvl+" | "+loadingBarForPower(20,"|",PowerEnum.);
//        return f;
//    }


}
