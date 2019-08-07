package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.math.NukkitRandom;
import cn.nukkit.plugin.Plugin;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Commands.ClassMerchant;
import net.yungtechboy1.CyberCore.Data.CustomSimpleConfig;

import java.util.ArrayList;


public class ClassMerchantConfig extends CustomSimpleConfig {
    public ArrayList<ClassMerchantData> CMD = new ArrayList<>();

    public ClassMerchantConfig(Plugin plugin) {
        super(plugin, "ClassMerchantConfig.yml");
        load();
//        for (ClassMerchantData cmd : CMD) {
////            System.out.println(cmd.toString()+"!!!!!!!!!!!!!!!!!!");
//        }
        if(CMD.isEmpty())CMD.add(RandomData());
//        for (ClassMerchantData cmd : CMD) {
////            System.out.println(cmd.toString()+"2!!!!!!!!!!!!!!!!!!");
//        }
    }

    public ArrayList<ClassMerchantData> getPurchaseablePowers(ClassType ct){
        ArrayList<ClassMerchantData> c = new ArrayList<>();
        for(ClassMerchantData cmd: CMD){
            if(cmd.AllowedToPurchase.isEmpty())continue;
            if(cmd.AllowedToPurchase.contains(ct))c.add(cmd);
        }
        return c;
    }

    public ClassMerchantData RandomData() {
        NukkitRandom nr = new NukkitRandom();
        ClassMerchantData cmd = new ClassMerchantData(PowerEnum.values()[nr.nextRange(0, PowerEnum.values().length - 1)]);
        cmd.setMoneyCost(nr.nextRange(500, 10000));
        cmd.getAllowedToPurchase().add(ClassType.values()[nr.nextRange(1, ClassType.values().length - 1)]);
        cmd.getAllowedToPurchase().add(ClassType.values()[nr.nextRange(1, ClassType.values().length - 1)]);
        cmd.getAllowedToPurchase().add(ClassType.values()[nr.nextRange(1, ClassType.values().length - 1)]);
        return cmd;
    }

//    @Override
//    public Object phraseFromSave(String key, Object obj) {
//        if(obj == null)return null;
//        if (key == "CMD") {
//            ArrayList<ClassMerchantData> c = new ArrayList<>();
//            for (Object o : ((ConfigSection) obj).values()) {
//                if (!(o instanceof ConfigSection)) continue;
//                c.add(new ClassMerchantData((ConfigSection) o));
//            }
//            if (c != null && !c.isEmpty()) return c;
//            return null;
//        }
//        System.out.println("1111Could not pharse FROM save " + key);
//        return null;
//    }
//
//    @Override
//    public Object phraseToForSave(String key, Object obj) {
//        if (key == "CMD") {
//            int k = 0;
//            ConfigSection c = new ConfigSection();
////            ArrayList<CustomSimpleConfigClass> cc = (ArrayList<CustomSimpleConfigClass>) field.get(this);
////                                field.getType().getConstructor(ConfigSection.class).newInstance()
//            if (obj == null) {
//                System.out.println("ERROR!!!!! >>>>>>>>" + obj);
//                return null;
//            }
//            for (CustomSimpleConfigClass ccc : ((ArrayList<? extends CustomSimpleConfigClass>) obj)) {
//                Object o = ccc.rawExport();
////                System.out.println("GOT "+o);
//                if (o != null) c.put(k++ + "", o);
//            }
//            if (c != null && !c.isEmpty()) return c;
//            return null;
//        }
//        return null;
//
//    }
}
