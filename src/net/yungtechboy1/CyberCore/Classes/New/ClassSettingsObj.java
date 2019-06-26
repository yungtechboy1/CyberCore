package net.yungtechboy1.CyberCore.Classes.New;

import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;

import java.util.ArrayList;

public class ClassSettingsObj {
    private ArrayList<PowerEnum> LearnedPowers = new ArrayList<>();
    private ArrayList<PowerEnum> ClassDefaultPowers = new ArrayList<>();
    private ArrayList<PowerEnum> ActivatedPowers = new ArrayList<>();
    private PowerEnum PreferedSlot9 = PowerEnum.Unknown;
    private PowerEnum PreferedSlot8 = PowerEnum.Unknown;
    private PowerEnum PreferedSlot7 = PowerEnum.Unknown;
    public ClassSettingsObj() {

    }

    public ClassSettingsObj(ConfigSection c) {
        LearnedPowers = (ArrayList<PowerEnum>) c.get("lp");
        ClassDefaultPowers = (ArrayList<PowerEnum>) c.get("cdp");
        ActivatedPowers = (ArrayList<PowerEnum>) c.get("ap");
        PreferedSlot9 = (PowerEnum) c.get("ps9");
        PreferedSlot8 = (PowerEnum) c.get("ps8");
        PreferedSlot7 = (PowerEnum) c.get("ps7");
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

    public PowerEnum getPreferedSlot8() {
        return PreferedSlot8;
    }

    public PowerEnum getPreferedSlot7() {
        return PreferedSlot7;
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
}
