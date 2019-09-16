package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;

public abstract class StagePowerAbstract extends PowerAbstract{
//    @Deprecated
//    public StagePowerAbstract(BaseClass b, int psc) {
//        this(b,null, psc,5);
//    }
//@Deprecated
//    public StagePowerAbstract(BaseClass b, PowerSettings ps, int psc) {
//        this(b,ps, psc,5);
//    }
//@Deprecated
//    public StagePowerAbstract(BaseClass b,  int psc, double cost) {
//        this(b,  null, psc, cost);
//    }

//    @Deprecated
//    public StagePowerAbstract(BaseClass b,  PowerSettings ps,int psc, double cost) {
//        super(b, StageEnum.STAGE_1,ps, psc, cost);
//    }
//@Deprecated
//    public StagePowerAbstract(BaseClass b, AdvancedPowerEnum ape,PowerSettings ps,int psc, double cost)  {
//        super(b,ape,ps, psc, cost);
//    }
    public StagePowerAbstract(AdvancedPowerEnum ape) {
        super(ape);
    }
    public StagePowerAbstract(BaseClass b) {
        super(b, StageEnum.STAGE_1);
    }

    public StagePowerAbstract(BaseClass b, AdvancedPowerEnum ape)  {
        super(b,ape);
    }

//    public StagePowerAbstract(BaseClass b, int psc, double cost, PowerSettings ps) {
//        super(b,  new ClassLevelingManagerStage(),ps, psc, cost);
//    }

    public abstract StageEnum getMaxStage();

    public void setMaxStage(StageEnum s){
        if(getStageLevelManager() == null){
            System.out.println("ERRORORORO RWTF HOW IS THIS NULLLLL!!!");
            return;
        }
        getStageLevelManager().setMaxStage(s);
    }
}
