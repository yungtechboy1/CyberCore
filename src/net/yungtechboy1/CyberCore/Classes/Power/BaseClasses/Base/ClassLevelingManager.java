package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import cn.nukkit.utils.ConfigSection;

public abstract class ClassLevelingManager {


    public abstract PowerAbstract.StageEnum getStage();
    public abstract ConfigSection exportConfig();

    public abstract void importConfig(ConfigSection cs);

    public abstract PowerAbstract.LevelingType getType();

    public abstract void setMaxStage(PowerAbstract.StageEnum stage);
}
