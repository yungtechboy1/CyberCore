package net.yungtechboy1.CyberCore.Classes.New;

import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.PowerData;
import net.yungtechboy1.CyberCore.Manager.PowerManager;

import java.util.ArrayList;

public class ClassSettingsObj {
    //ALL Player Powers
    //Powers that cant be given automatically
    //Class Level up powers too!
    private ArrayList<AdvancedPowerEnum> LearnedPowers = new ArrayList<>();
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
        LearnedPowers = ArrayListtoAPEList((ArrayList<String>)c.get("lp"));
        ClassDefaultPowers = ArrayListStringtoPE((ArrayList<String>) c.get("cdp"));
        ActivatedPowers = ArrayListStringtoPE((ArrayList<String>) c.get("ap"));
        PreferedSlot9 = PowerEnum.fromstr((String) c.get("ps9"));
        PreferedSlot8 = PowerEnum.fromstr((String) c.get("ps8"));
        PreferedSlot7 = PowerEnum.fromstr((String) c.get("ps7"));
        BC = bc;
    }

    public ArrayList<PowerEnum> getEnabledPowers() {
        return ActivatedPowers;
    }

    public ArrayList<String> getActivatedPowersJSON() {
        ArrayList<String> i = new ArrayList<>();
        for (PowerEnum e : getEnabledPowers()) {
            i.add(e.name());
        }
        return i;
    }

    public ArrayList<String> ArrayListPEtoString(ArrayList<PowerEnum> p) {
        ArrayList<String> i = new ArrayList<>();
        for (PowerEnum e : p) {
            i.add(e.name());
        }
        return i;
    }


    public ArrayList<AdvancedPowerEnum> ArrayListtoAPEList(ArrayList<String> p) {
        ArrayList<AdvancedPowerEnum> i = new ArrayList<>();
        for (String e : p) {
            i.add(AdvancedPowerEnum.fromString(e));
        }
        return i;
    }

    public ArrayList<PowerEnum> ArrayListStringtoPE(ArrayList<String> p) {
        ArrayList<PowerEnum> i = new ArrayList<>();
        for (String e : p) {
            i.add(PowerEnum.fromstr(e));
        }
        return i;
    }

    public ArrayList<AdvancedPowerEnum> getLearnedPowers() {
        return LearnedPowers;
    }

    public ArrayList<PowerEnum> getClassDefaultPowers() {
        return ClassDefaultPowers;
    }

    public PowerEnum getPreferedSlot9() {
        return PreferedSlot9;
    }

    public void setPreferedSlot(LockedSlot ls,PowerEnum pe) {
        switch (ls){
            case NA:
                default:
                return;
            case SLOT_7:
                setPreferedSlot7(pe);
                return;
            case SLOT_8:
                setPreferedSlot8(pe);
                return;
            case SLOT_9:
                setPreferedSlot9(pe);
                return;
        }
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

    public PowerEnum getPreferedSlot(LockedSlot ls) {
        if (ls == LockedSlot.SLOT_9) return PreferedSlot9;
        if (ls == LockedSlot.SLOT_8) return PreferedSlot8;
        if (ls == LockedSlot.SLOT_7) return PreferedSlot7;
        return PowerEnum.Unknown;
    }

    public ConfigSection export() {
        return new ConfigSection() {{
            put("lp", ArrayListAPEtoStrings(getLearnedPowers()));
            put("cdp", ArrayListPEtoString(getClassDefaultPowers()));
            put("ap", ArrayListPEtoString(getEnabledPowers()));
            put("ps9", getPreferedSlot9().name());
            put("ps8", getPreferedSlot8().name());
            put("ps7", getPreferedSlot7().name());
        }};
    }

    private ArrayList<String> ArrayListAPEtoStrings(ArrayList<AdvancedPowerEnum> learnedPowers) {
        ArrayList<String> c = new ArrayList<>();
        for (AdvancedPowerEnum ape : learnedPowers) {
            if (!ape.isValid()) continue;
            c.add(ape.toString());
        }
        return c;
    }
//    private ConfigSection ArrayListAPEtoConfigSection(ArrayList<AdvancedPowerEnum> learnedPowers) {
//        ConfigSection c = new ConfigSection();
//        for(AdvancedPowerEnum ape: learnedPowers){
//            if(!ape.isValid() || ape.toConfig().isEmpty())continue;
//        c.put(ape.getPowerEnum().name(),ape.toConfig());
//        }
//    }

    /**
     * Returns All learned powers as a PowerData[] Active or Not
     * Which contains the class it self, The Slot that it should be assigned to
     * @return
     */
    public PowerData[] getPowerDataList() {
        ArrayList<PowerData> pd = new ArrayList<>();
        for (AdvancedPowerEnum pe : getLearnedPowers()) {
//            System.out.println("GPDL >>>>>>>> "+pe.getPowerEnum());
            PowerAbstract ppa = PowerManager.getPowerfromAPE(pe,BC);
            if(ppa == null){
                System.out.println("EEEEEE1342342 234423334 !!!!!!!!!!!!!!!!!!!!!!!!");
                continue;
            }
            boolean a = false;
            if(ppa.getAllowedClasses() != null) {
                for (Class b : ppa.getAllowedClasses()) {
                    if (BC.getClass().isAssignableFrom(b)) {
                        a = true;
                        break;
                    }
                }
                if (!a) continue;
            }
//            PowerAbstract pa = BC.getPossiblePower(pe, false);
            PowerData p = new PowerData(ppa);
            if (getPreferedSlot7() == pe.getPowerEnum()) p.setLS(LockedSlot.SLOT_7);
            if (getPreferedSlot8() == pe.getPowerEnum()) p.setLS(LockedSlot.SLOT_8);
            if (getPreferedSlot9() == pe.getPowerEnum()) p.setLS(LockedSlot.SLOT_9);
            if(getEnabledPowers().contains(ppa.getType()))p.setEnabled(true);
            pd.add(p);
        }
        return pd.toArray(new PowerData[0]);
    }

    public void addActivePower(PowerEnum pe) {
        if (!ActivatedPowers.contains(pe)) ActivatedPowers.add(pe);
    }

    public void delActivePower(PowerEnum pe) {
        ActivatedPowers.remove(pe);
    }

    public void clearSlot7() {
        PreferedSlot7 = PowerEnum.Unknown;
    }

    public void clearSlot8() {
        PreferedSlot8 = PowerEnum.Unknown;
    }

    public void clearSlot9() {
        PreferedSlot9 = PowerEnum.Unknown;
    }

    //Ran to Activate Powers from InternalPlayerSettings
    public void check() {
        for (PowerData pd : getPowerDataList()) {
            if(pd.getPA() == null){
                System.out.println("ERRROO3333333R!!! "+pd.getPowerID());
                return;
            }
            BC.addPossiblePower(pd.getPA());
//            BC.enablePower(pd);
//            PowerAbstract pa = pd.getPA();
//            if(pa != null){
//                pa.setEnabled();
//            }
        }
    }

    public void learnNewPower(PowerEnum pe) {
        learnNewPower(new AdvancedPowerEnum(pe));
    }
    public void learnNewPower(PowerEnum pe, boolean silentfail) {
        learnNewPower(new AdvancedPowerEnum(pe), silentfail);
    }
    public boolean canLearnNewPower(AdvancedPowerEnum a){
        for(AdvancedPowerEnum ape: getLearnedPowers()){
            if(ape.getPowerEnum() == a.getPowerEnum() && ape.isValid())return false;
        }
        return true;
    }
    public void learnNewPower(AdvancedPowerEnum advancedPowerEnum) {
        learnNewPower(advancedPowerEnum,false);
    }
    public void learnNewPower(AdvancedPowerEnum advancedPowerEnum, boolean silentfail) {
        if(!canLearnNewPower(advancedPowerEnum) && !silentfail)BC.getPlayer().sendMessage(TextFormat.YELLOW+"ClassSettings > WARNING > Can not re-learn"+advancedPowerEnum.getPowerEnum()+" Power has already been learned!");
        LearnedPowers.add(advancedPowerEnum);
        BC.getPlayer().sendMessage(TextFormat.GREEN+"ClassSettings > "+advancedPowerEnum.getPowerEnum()+" Power has now been learned!");
    }

    public void delLearnedPower(PowerEnum pe) {
        if(pe == null)return;
        int k = 0;
        System.out.println("LFFFF >>> "+pe);
        for(AdvancedPowerEnum ape: LearnedPowers){
            System.out.println("|||||"+ape);
            if(ape.getPowerEnum() == pe)break;
            k++;
        }
        if(k < LearnedPowers.size())LearnedPowers.remove(k);
    }

    public boolean isPowerLearned(AdvancedPowerEnum pe) {
        return isPowerLearned(pe.getPowerEnum());
    }
    public boolean isPowerLearned(PowerEnum pe) {
        for(AdvancedPowerEnum ape: LearnedPowers){
            if(ape.getPowerEnum() == pe)return true;
        }
        return false;
    }

    public void delLearnedPowerAndLearnIfNotEqual(AdvancedPowerEnum ape) {
        boolean rl = false;
        for(AdvancedPowerEnum aa: LearnedPowers){
            if(aa.sameType(ape)) {
                if(!aa.checkEquals(ape)) {
                    rl = true;
                }
               break;
            }
        }
        if(rl){
                delLearnedPower(ape);
                learnNewPower(ape, true);
        }
    }

    public void delLearnedPower(AdvancedPowerEnum ape) {
        delLearnedPower(ape.getPowerEnum());
    }
}
