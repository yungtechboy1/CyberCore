package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

public abstract class StagePowerAbstract extends PowerAbstract{
    public StagePowerAbstract(BaseClass b, int psc) {
        this(b, psc,5);
    }

    public StagePowerAbstract(BaseClass b, int psc, double cost) {
        super(b,  new ClassLevelingManagerStage(), psc, cost);
    }

    @Override
    public ClassLevelingManagerStage getLM() {
        return (ClassLevelingManagerStage)super.getLM();
    }

    public void setMaxStage(StageEnum s){
        getLM().setMaxStage(s);
    }
}
