package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.New.DeBuff;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.HolyKnightHealPower;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class HolyKnight extends BaseClass {
    public HolyKnight(CyberCoreMain main, CorePlayer player,ConfigSection data) {
        super(main, player, data);
    }

    @Override
    public PrimalPowerType getPowerSourceType() {
        return null;
    }

    @Override
    public ClassType getTYPE() {
        return ClassType.Class_Offense_Holy_Knight;
    }

    @Override
    public void SetPowers() {
        addPossiblePower(new HolyKnightHealPower(this));
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent event) {
        return super.CustomEntityDamageByEntityEvent(event);
    }

    @Override
    public void initBuffs() {
        addBuff(new Buff(Buff.BuffType.Damage, 1.15f));
        addBuff(new Buff(Buff.BuffType.DamageToEntity, 1.25f));
        addBuff(new Buff(Buff.BuffType.Magic, .75f));
//        addBuff(new Buff(Buff.BuffType.Armor,1.3f));
//        addBuff(new Buff(Buff.BuffType.Health,4f));
        addDeBuff(new DeBuff(Buff.BuffType.Movement, .8f));
//        addBuff(new Buff(Buff.BuffType.SwingSpeed,1.5f));
//        addBuff(new Buff(Buff.BuffType.SuperFoodHeartRegin,1f));
    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return "Holy Knight";
    }

    @Override
    public String getDisplayName() {
        return TextFormat.GOLD + "|| " + getName() + " ||";
    }

//    @Override
//    public String FormatHudText() {
//        String f = "";
//        int lvl = XPToLevel(getXP());
//        String pclass = getDisplayName();
//        int pxp = XPRemainder(getXP());
//        int pxpof = calculateRequireExperience(lvl + 1);
//        int plvl = lvl;
//        f += TextFormat.AQUA + pclass + TextFormat.GRAY + " | " + TextFormat.GREEN + pxp + TextFormat.AQUA + " / " + TextFormat.GOLD + pxpof + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + plvl + " | " + loadingBarCooldownForPower(20, "|", PowerEnum.HolyKnightHeal);
//        return f;
//    }
//
//    private Object loadingBarCooldownForPower(int loadingBarLength, String BarChar, PowerEnum powertype) {
//        String finaltext = "";
//        PowerAbstract p = getPossiblePower(powertype);
//        if(p instanceof HolyKnightHealPower){
//
//        for (int i = 0; i < loadingBarLength ;i++){
//            String c = new String(BarChar);
//            finaltext += c;
//        }
//        }
//    }
}
