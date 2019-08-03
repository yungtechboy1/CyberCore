package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class AdvancedPowerEnum {
    PowerEnum PE;
    PowerAbstract.StageEnum SE = PowerAbstract.StageEnum.NA;
    Integer XP = -1;
    PowerAbstract.LevelingType tt = PowerAbstract.LevelingType.None;


    public AdvancedPowerEnum(PowerEnum PE) {
        this.PE = PE;
    }

    public AdvancedPowerEnum(PowerEnum PE, Integer XP) {
        this.PE = PE;
        this.XP = XP;
        tt = PowerAbstract.LevelingType.XPLevel;
    }

    public AdvancedPowerEnum(PowerEnum PE, PowerAbstract.StageEnum SE) throws Exception {
        if (SE == PowerAbstract.StageEnum.NA)
            throw new Exception("Error! Can not pass StageEnum.NA to AdvancedPowerEnum Constructor!");
        this.PE = PE;
        this.SE = SE;
        tt = PowerAbstract.LevelingType.Stage;
    }

    public boolean isStage(){
        //Something so Simple low key caused 2 hours of work! >:(
        if(getStageEnum() != PowerAbstract.StageEnum.NA)return true;
        return false;
    }

    //0    1            2  3
    //APE|DragonJumper|XP|100
    public static AdvancedPowerEnum fromString(String s) {
        String[] ss = s.split("\\|");
        if (ss.length == 3) {
            //None, Invalid Save
            CyberCoreMain.getInstance().getLogger().error("Error! Invalid AdvancedPowerEnum Save from > " + s);
            return null;
        }
        PowerEnum pe = PowerEnum.fromstr(ss[1]);
        if (pe == PowerEnum.Unknown) {
            CyberCoreMain.getInstance().getLogger().error("Error! Invalid PowerEnu Name from Save > " + s);
            return null;
        }
        if (ss[2].equalsIgnoreCase("xp")) {
            return new AdvancedXPPowerEnum(pe,Integer.parseInt(ss[3]));
        } else if (ss[2].equalsIgnoreCase("stage")) {
            try {
                return new AdvancedStagePowerEnum(pe, PowerAbstract.StageEnum.getStageFromInt(Integer.parseInt(ss[3])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Error! Why did this go all the way!?!?!? E110393");
        return null;
    }

    public PowerEnum getPowerEnum() {
        return PE;
    }

    public PowerAbstract.StageEnum getStageEnum() {
        return SE;
    }

    public Integer getXP() {
        return XP;
    }

    public PowerAbstract.LevelingType getTt() {
        return tt;
    }

    public boolean isValid() {
        if (tt == PowerAbstract.LevelingType.None) return false;
        if (tt == PowerAbstract.LevelingType.XPLevel) {
            if (XP == -1) return false;
        }
        if (tt == PowerAbstract.LevelingType.Stage) {
            if (SE == PowerAbstract.StageEnum.NA) return false;
        }
        return true;
    }

    public String toString() {
        String s = "APE|" + getPowerEnum() + "|";
        switch (tt) {
            case XPLevel:
                s += "XP|" + getXP();
            case Stage:
                s += "Stage|" + getStageEnum().ordinal();
            case None:
            default:
                s += "None";
        }
        return s;
    }

//    public ConfigSection toConfig() {
//        ConfigSection c = new ConfigSection();
//        if (tt == PowerAbstract.LevelingType.None) return c;
//        if (tt == PowerAbstract.LevelingType.XPLevel) {
//            if (XP == -1) return false;
//        }
//        if (tt == PowerAbstract.LevelingType.Stage) {
//            if (SE == PowerAbstract.StageEnum.NA) return false;
//        }
//    }
}

