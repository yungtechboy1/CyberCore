package net.yungtechboy1.CyberCore.Custom.Item;

/**
 * Created by carlt_000 on 1/18/2017.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;

public class CItemBook extends Item {
    public boolean isPower = false;
    public boolean isClass = false;
    AdvancedPowerEnum APE = null;
    ClassType BCT = null;

    public CItemBook(ClassType ct) {
        this(0, 1);
        BCT = ct;
        isClass = true;
        SaveItemToNamedTag();
    }

    public CItemBook(PowerEnum pe) {
        this(new AdvancedPowerEnum(pe, PowerAbstract.StageEnum.STAGE_1));
    }

    public CItemBook(AdvancedPowerEnum pe) {
        this(0, 1);
        isPower = true;
        APE = pe;
        SaveItemToNamedTag();
    }

    public CItemBook() {
        this(0, 1);
    }

    public CItemBook(Integer meta) {
        this(meta, 1);
    }

    public CItemBook(Integer meta, int count) {
        super(340, meta, count, "CBook");
        LoadFromNamedTag();
    }

    public void SaveItemToNamedTag() {
        CompoundTag c = getNamedTag();
        if (c == null) c = new CompoundTag();

        if (isPower) {
            c.putBoolean("isPower", true);
            c.putString("APE", APE.toString());
        } else if (isClass) {
            c.putBoolean("isClass", true);
            c.putInt("ClassID", BCT.getKey());
        }
        formatNametag();
    }

    private void formatNametag() {
        if (isPower) {
            setCustomName("Power Book");
            setLore("PowerID: "+APE.getPowerEnum().ordinal(),
                    APE.getLore1(),
                    APE.getLore2(),APE.getLore3());
        } else if (isClass) {
            setCustomName("Class Book");
            setLore("ClassID: "+BCT.ordinal(),
                    BCT+"");
        }
    }

    public void LoadFromNamedTag() {
        CompoundTag c = getNamedTag();
        if (c == null) {
            setCompoundTag(new CompoundTag());
        } else {
            if (c.contains("isPower") && c.getBoolean("isPower")) {
                APE = AdvancedPowerEnum.fromString(c.getString("APE"));
                if (APE == null) {
                    System.out.println("ERROROROR !!!!! E33443242322s");
                }
            } else if (c.contains("isClass") && c.getBoolean("isClass")) {
                BCT = ClassType.values()[c.getInt("ClassID")];
            }
        }
    }

    public int getEnchantAbility() {
        return 25;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
