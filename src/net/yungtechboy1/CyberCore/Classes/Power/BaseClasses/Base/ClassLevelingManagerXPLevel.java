package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import cn.nukkit.utils.ConfigSection;

public class ClassLevelingManagerXPLevel extends ClassLevelingManager {
   private int XP = 0;
    private int MaxLevel = 100;

    public int getMaxLevel() {
        return MaxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        MaxLevel = maxLevel;
    }

//    @Override
//    public PowerAbstract.StageEnum getStage() {
//        return PowerAbstract.StageEnum.getStageFromInt();
//    }

    public PowerAbstract.StageEnum getStage() {
//        if (getLT() == LevelingType.Stage) return StageEnum.getStageFromInt(Stage);
        return PowerAbstract.StageEnum.getStageFromInt(1 + ((int) Math.floor(getLevel() / 20)));
    }
    protected int XPNeededToLevelUp(int CurrentLevel) {
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

    public int getLevel() {
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

    protected int getXP() {
        return XP;
    }

    protected int getRealXP() {
        return XP - XPNeededToLevelUp(getLevel());
    }

    protected void addXP(int a) {
        XP += Math.abs(a);
//        if(XP > XPNeededToLevelUp(getLevel()));
    }

    protected void takeXP(int a) {
        XP -= Math.abs(a);
    }

    @Override
    public ConfigSection exportConfig() {
        ConfigSection c = new ConfigSection();
        c.put("XP", getXP());
        return c;
    }

    @Override
    public void importConfig(ConfigSection cs) {
        if (cs.containsKey("XP")) addXP(cs.getInt("XP"));
    }

    @Override
    public PowerAbstract.LevelingType getType() {
        return PowerAbstract.LevelingType.XPLevel;
    }

    @Override
    public void setMaxStage(PowerAbstract.StageEnum stage) {

    }
}
