package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.FormType;

import java.util.HashMap;

public abstract class StagePowerAbstract extends PowerAbstract {

    public HashMap<StageEnum, String> CustomSageName = new HashMap<>();
    private StageEnum MaxStage = StageEnum.STAGE_10;
    private StageEnum Stage = StageEnum.NA;
    public StagePowerAbstract(BaseClass b, int psc) {
        super(b, LevelingType.Stage, psc);
    }

    public StagePowerAbstract(BaseClass b, int psc, double cost) {
        super(b, LevelingType.Stage, psc, cost);
    }

    public StageEnum getMaxStage() {
        return MaxStage;
    }

    public void setMaxStage(StageEnum maxStage) {
        MaxStage = maxStage;
    }

    public StageEnum getStage() {
//        if (getLT() == LevelingType.Stage) return StageEnum.getStageFromInt(Stage);
        return Stage;
    }

    public void setStage(StageEnum s) {
        if (s.ordinal() > MaxStage.ordinal()) s = MaxStage;
        Stage = s;
    }

    @Override
    public ConfigSection exportConfig() {
        ConfigSection c = super.exportConfig();
        c.put("Stage", getStage().ordinal());
        return c;
    }

    @Override
    public void importConfig(ConfigSection cs) {
        super.importConfig(cs);
        if (cs.containsKey("Stage")) setStage(StageEnum.getStageFromInt(cs.getInt("Stage")));
    }
}
