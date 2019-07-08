package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import cn.nukkit.utils.ConfigSection;

import java.util.HashMap;

public class ClassLevelingManagerStage extends ClassLevelingManager {
    public HashMap<PowerAbstract.StageEnum, String> CustomSageName = new HashMap<>();
    private PowerAbstract.StageEnum MaxStage = PowerAbstract.StageEnum.STAGE_10;
    private PowerAbstract.StageEnum Stage = PowerAbstract.StageEnum.STAGE_1;


    public ClassLevelingManagerStage(HashMap<PowerAbstract.StageEnum, String> customSageName, PowerAbstract.StageEnum maxStage, PowerAbstract.StageEnum stage) {
        CustomSageName = customSageName;
        MaxStage = maxStage;
        Stage = stage;
    }
    public ClassLevelingManagerStage( PowerAbstract.StageEnum stage,PowerAbstract.StageEnum maxStage) {
        MaxStage = maxStage;
        Stage = stage;
    }
    public ClassLevelingManagerStage( PowerAbstract.StageEnum stage) {
        Stage = stage;
    }
    public ClassLevelingManagerStage() {
    }

    public PowerAbstract.StageEnum getMaxStage() {
        return MaxStage;
    }

    public void setMaxStage(PowerAbstract.StageEnum maxStage) {
        MaxStage = maxStage;
    }

    public PowerAbstract.StageEnum getStage() {
//        if (getLT() == LevelingType.Stage) return StageEnum.getStageFromInt(Stage);
        return Stage;
    }

    public void setStage(PowerAbstract.StageEnum s) {
        if (s.ordinal() > MaxStage.ordinal()) s = MaxStage;
        Stage = s;
    }

    public ConfigSection exportConfig() {
        ConfigSection c = new ConfigSection();
        c.put("Stage", getStage().ordinal());
        return c;
    }

    public void importConfig(ConfigSection cs) {
//        super.importConfig(cs);
        if (cs.containsKey("Stage")) setStage(PowerAbstract.StageEnum.getStageFromInt(cs.getInt("Stage")));
    }

    @Override
    public PowerAbstract.LevelingType getType() {
        return PowerAbstract.LevelingType.Stage;
    }
}
