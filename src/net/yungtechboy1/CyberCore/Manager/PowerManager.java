package net.yungtechboy1.CyberCore.Manager;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import org.yaml.snakeyaml.constructor.Construct;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.stream.StreamSupport;

public class PowerManager {

    public static HashMap<PowerEnum, Class> PowerList = new HashMap<>();

    CyberCoreMain CCM;

    public PowerManager(CyberCoreMain CCM) {
        this.CCM = CCM;
//        addPowerToList();


    }

    public static void addPowerToList(PowerEnum pe,Class c) {
        if(PowerList.containsKey(pe))System.out.println("WARNING!!! Overwriting a Power with the Key > "+pe);
        PowerList.put(pe, c);
    }

    public static PowerAbstract getPowerfromAPE(AdvancedPowerEnum pe, BaseClass b) {
        Class cpa = PowerList.get(pe.getPowerEnum());
        if (cpa == null) {
            System.out.println("NONE IN POWER LIST!!!"+pe);
            return null;
        }
        if(cpa.getSuperclass().isAssignableFrom(StagePowerAbstract.class)) {
            //Stage
            try {
                Constructor c = cpa.getConstructor(BaseClass.class, AdvancedPowerEnum.class);
                if(c == null) {
                    System.out.println("ERRORROOORROR C  +++++=====  NUUULLLLLL");
                    return null;
                }
                    return (PowerAbstract)c.newInstance(b,pe);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
//        }else if(cpa.isAssignableFrom()){
        }else{
            System.out.println("ERROR! "+cpa.getName()+"|| "+cpa);
        }
//        return cpa;
        return null;
    }
}
