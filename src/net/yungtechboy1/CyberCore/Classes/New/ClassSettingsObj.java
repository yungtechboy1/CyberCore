package net.yungtechboy1.CyberCore.Classes.New;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBarInt;
import net.yungtechboy1.CyberCore.Classes.Power.PowerData;

import java.util.ArrayList;

public class ClassSettingsObj {
    //ALL Player Powers
    //Powers that cant be given automatically
    //Class Level up powers too!
    private ArrayList<PowerEnum> LearnedPowers = new ArrayList<>();
    //Powers given from Class
    private ArrayList<PowerEnum> ClassDefaultPowers = new ArrayList<>();
    //Only Powers that are Currently Active
    private ArrayList<PowerEnum> ActivatedPowers = new ArrayList<>();
    private PowerEnum PreferedSlot9 = PowerEnum.Unknown;
    private PowerEnum PreferedSlot8 = PowerEnum.Unknown;
    private PowerEnum PreferedSlot7 = PowerEnum.Unknown;
    private BaseClass BC;

    public ClassSettingsObj(BaseClass bc) {
        BC = bc;
    }

    public ClassSettingsObj(BaseClass bc, ConfigSection c) {
        LearnedPowers = (ArrayList<PowerEnum>) c.get("lp");
        ClassDefaultPowers = (ArrayList<PowerEnum>) c.get("cdp");
        ActivatedPowers = (ArrayList<PowerEnum>) c.get("ap");
        PreferedSlot9 = (PowerEnum) c.get("ps9");
        PreferedSlot8 = (PowerEnum) c.get("ps8");
        PreferedSlot7 = (PowerEnum) c.get("ps7");
        BC = bc;
    }

    public ArrayList<PowerEnum> getActivatedPowers() {
        return ActivatedPowers;
    }

    public ArrayList<PowerEnum> getLearnedPowers() {
        return LearnedPowers;
    }

    public ArrayList<PowerEnum> getClassDefaultPowers() {
        return ClassDefaultPowers;
    }

    public PowerEnum getPreferedSlot9() {
        return PreferedSlot9;
    }

    public void setPreferedSlot9(PowerEnum preferedSlot9) {
        PreferedSlot9 = preferedSlot9;
    }

    public PowerEnum getPreferedSlot8() {
        return PreferedSlot8;
    }

    public void setPreferedSlot8(PowerEnum preferedSlot8) {
        PreferedSlot8 = preferedSlot8;
    }

    public PowerEnum getPreferedSlot7() {
        return PreferedSlot7;
    }

    public void setPreferedSlot7(PowerEnum preferedSlot7) {
        PreferedSlot7 = preferedSlot7;
    }

    public ConfigSection export() {
        return new ConfigSection() {{
            put("lp", getLearnedPowers());
            put("cdp", getClassDefaultPowers());
            put("ap", getActivatedPowers());
            put("ps9", getPreferedSlot9());
            put("ps8", getPreferedSlot8());
            put("ps7", getPreferedSlot7());
        }};
    }

    public PowerData[] getPowerDataList() {
        ArrayList<PowerData> pd = new ArrayList<>();
        for (PowerEnum pe : getLearnedPowers()) {
            PowerAbstract pa = BC.getPower(pe);
            PowerData p = new PowerData(pe, getActivatedPowers().contains(pe), pa instanceof PowerHotBarInt);
            if (getPreferedSlot7() == pe) p.setLS(LockedSlot.SLOT_7);
            if (getPreferedSlot8() == pe) p.setLS(LockedSlot.SLOT_8);
            if (getPreferedSlot9() == pe) p.setLS(LockedSlot.SLOT_9);
            pd.add(p);
        }
        return pd.toArray(new PowerData[0]);
    }
}
