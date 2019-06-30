package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.DeBuff;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.DragonJumper;
import net.yungtechboy1.CyberCore.Classes.Power.DragonSlayerFireStomp;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class DragonSlayer extends BaseClass {
    public DragonSlayer(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        super(main, player, ClassType.DragonSlayer, data);
    }

    public DragonSlayer(CyberCoreMain main, CorePlayer player) {
        super(main, player, ClassType.DragonSlayer);
    }

    @Override
    public PrimalPowerType getPowerSourceType() {
        return PrimalPowerType.Earth;
    }

    @Override
    public ClassType getTYPE() {
        return ClassType.DragonSlayer;
    }

    @Override
    public void SetPowers() {

        addPower(new DragonSlayerFireStomp(this));
        addPower(new DragonJumper(this));
//        addPower(new DragonJumper(this));
//        addPower(new TNTAirStrikePower(this));
    }

    @Override
    public void initBuffs() {
        addBuff(new Buff(Buff.BuffType.Jump, 3f));
        addDeBuff(new DeBuff(Buff.BuffType.Movement, .9f));
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
    public String getDisplayName() {
        return TextFormat.RED + "\\\\" + getName() + "//";
    }

    @Override
    public String getName() {
        return "Dragon Slayer";
    }

//    @Override
//    public String FormatHudText() {
//        String f = "";
//        int lvl = XPToLevel(getXP());
//        String pclass = getDisplayName();
//        int pxp = XPRemainder(getXP());
//        int pxpof = calculateRequireExperience(lvl + 1);
//        int plvl = lvl;
//        f += TextFormat.AQUA + pclass + TextFormat.GRAY + " | " + TextFormat.GREEN + pxp + TextFormat.AQUA + " / " + TextFormat.GOLD + pxpof + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + plvl + " | " + loadingBarCooldownForPower(20, "|", PowerEnum.DragonJumper);
//        return f;
//    }
//
//    private Object loadingBarCooldownForPower(int loadingBarLength, String BarChar, PowerEnum powertype) {
//        String finaltext = "";
//        PowerAbstract p = getPower(powertype);
//        if(p instanceof DragonJumper){
//
//            for (int i = 0; i < loadingBarLength ;i++){
//                String c = new String(BarChar);
//                c +=
//                finaltext += c;
//            }
//        }
//    }
}
