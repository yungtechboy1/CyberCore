package net.yungtechboy1.CyberCore.Manager;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PowerManager {

    public static HashMap<PowerEnum, Class> PowerList = new HashMap<>();

    CyberCoreMain CCM;

    public PowerManager(CyberCoreMain CCM) {
        this.CCM = CCM;


    }

    public static void addPowerToList(PowerEnum pe,Class c) {
        if(PowerList.containsKey(pe))System.out.println("WARNING!!! Overwriting a Power with the Key > "+pe);
        PowerList.put(pe, c);
    }

    public static PowerAbstract getPowerfromAPE(AdvancedPowerEnum pe, BaseClass b) {
        Class cpa = PowerList.get(pe.getPowerEnum());
        if (cpa == null) return null;
        if(cpa.isAssignableFrom(StagePowerAbstract.class)) {
            //Stage
            try {
                return (PowerAbstract)cpa.getConstructor(BaseClass.class, AdvancedPowerEnum.class).newInstance(b,pe);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
//        return cpa;
        return null;
    }
}
