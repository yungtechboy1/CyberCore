package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

public abstract class StagePowerAbstract extends PowerAbstract{
    public StagePowerAbstract(BaseClass b, int psc) {
        this(b,null, psc,5);
    }

    public StagePowerAbstract(BaseClass b, PowerSettings ps, int psc) {
        this(b,ps, psc,5);
    }

    public StagePowerAbstract(BaseClass b,  int psc, double cost) {
        super(b,  new ClassLevelingManagerStage(),null, psc, cost);
    }

    public StagePowerAbstract(BaseClass b,  PowerSettings ps,int psc, double cost) {
        super(b,  new ClassLevelingManagerStage(),ps, psc, cost);
    }

//    public StagePowerAbstract(BaseClass b, int psc, double cost, PowerSettings ps) {
//        super(b,  new ClassLevelingManagerStage(),ps, psc, cost);
//    }

    @Override
    public ClassLevelingManagerStage getLM() {
        return (ClassLevelingManagerStage)super.getLM();
    }

    public void setMaxStage(StageEnum s){
        getLM().setMaxStage(s);
    }
}
