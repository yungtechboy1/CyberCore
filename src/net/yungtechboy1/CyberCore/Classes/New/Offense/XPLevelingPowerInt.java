package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;

public interface XPLevelingPowerInt {


    public int getMaxLevel();
    public void setMaxLevel(int maxLevel);

    public default PowerAbstract.StageEnum getStage() {
//        if (getLT() == LevelingType.Stage) return StageEnum.getStageFromInt(Stage);
        return PowerAbstract.StageEnum.getStageFromInt(1 + ((int) Math.floor(getLevel() / 20)));
    }

    public default int XPNeededToLevelUp(int CurrentLevel) {
//        if(CurrentLevel == 0)return 0;
//        int cl = NextLevel - 1;
        int cl = CurrentLevel;
        if (cl <= 15) {
            return 2 * (cl) + 7;
        } else if (cl <= 30) {
            return 5 * (cl) - 38;
        } else {
            return 9 * (cl) - 158;
        }
    }

    public default int getLevel() {
        int x = getXP();
        int l = 0;
        while (true) {
            int a = XPNeededToLevelUp(l);
            if (a < x) {
                x -= a;
                l++;
            } else {
                break;
            }
        }
        return l;
    }

    public int getXP();

    public int getRealXP();

    public void addXP(int a);

    public void takeXP(int a);

//    @Override
//    public ConfigSection exportConfig() {
//        ConfigSection c = super.exportConfig();
//        c.put("XP", getXP());
//        return c;
//    }
//
//    @Override
//    public void importConfig(ConfigSection cs) {
//        super.importConfig(cs);
//        if (cs.containsKey("XP")) addXP(cs.getInt("XP"));
//    }
}
