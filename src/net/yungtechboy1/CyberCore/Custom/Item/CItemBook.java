package net.yungtechboy1.CyberCore.Custom.Item;

/**
 * Created by carlt_000 on 1/18/2017.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;

public class CItemBook extends Item {
    public boolean isPower = false;
    public boolean isClass = false;
    public boolean loadedfromnt = false;
    AdvancedPowerEnum APE = null;
    ClassType BCT = null;
    int spam = 0;

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
        setLore();
//        LoadFromNamedTag();
    }

    @Override
    public Item setCompoundTag(byte[] tags) {
        Item i = super.setCompoundTag(tags);
        if (!loadedfromnt) {
            LoadFromNamedTag();
        }
        return i;
    }

    @Override
    public final Item setNamedTag(CompoundTag tag) {
        Item i = super.setNamedTag(tag);
//        if (!loadedfromnt) LoadFromNamedTag();
        return i;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        //Try and learn Power to Player!
        System.out.println("22222222");
        System.out.println("HEARDDD"+isPower+"||"+((CItemBook)player.getInventory().getItemInHand()).isPower);
        LoadFromNamedTag();
        System.out.println("HEARDDD22222"+getNamedTag());
        CorePlayer p = (CorePlayer) player;
        if (p.getServer().getTick() < spam) return false;
        spam = p.getServer().getTick() + 40;
        //Try to Switch Player Class
        if (isPower) {
            //Try and learn Power to Player!
            if (p.getPlayerClass() == null) {
                p.sendMessage(TextFormat.RED + "Error! You must be in a Class to use this!");
                return true;
            }
            BaseClass bc = p.getPlayerClass();
            if (bc.getClassSettings().canLearnNewPower(APE)) {
                System.out.println("Attempting to learn new Power to player !!!!!!!!!!");
                bc.getClassSettings().learnNewPower(APE);
                setCount(0);
            } else {
                p.sendMessage("Error! You already have this power learned!");
            }
            p.getInventory().setItemInHand(this);
            return true;
        } else if( isClass){

        };
        return true;
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
        setNamedTag(c);
        formatNametag();
//        System.out.println("NTTTTTTTTTTTTTTT>>>+"+c);
//        System.out.println("NTTTTTTTTTTTTTTT>>>+"+getNamedTag());
        setNamedTag(getNamedTag());
    }

    private void formatNametag() {
        if (isPower) {
            System.out.println("UPDATING NAMETAGGGG");
            setCustomName("Power Book");
            setLore("PowerID: " + APE.getPowerEnum().ordinal(),
                    APE.getLore1(),
                    APE.getLore2(), APE.getLore3());
        } else if (isClass) {
            setCustomName("Class Book");
            setLore("ClassID: " + BCT.ordinal(),
                    BCT + "");
        }
        System.out.println("AAAAAAAAAAAAAAAAAAAAAA>>>+"+getNamedTag());
    }

    public void LoadFromNamedTag() {
        System.out.println("LOADDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        loadedfromnt = true;
        CompoundTag c = getNamedTag();
        if (c == null) {
            System.out.println("Was Nulll!?!?!" + hasCompoundTag());
            setCompoundTag(new CompoundTag());
        } else {
            if (c.contains("isPower") && c.getBoolean("isPower")) {
                System.out.println("ISPORTTTWWRRR");
                isPower = true;
                APE = AdvancedPowerEnum.fromString(c.getString("APE"));
                if (APE == null) {
                    System.out.println("ERROROROR !!!!! E33443242322s");
                }
            } else if (c.contains("isClass") && c.getBoolean("isClass")) {
                System.out.println("IS CCCCCCCCWSASDASD");
                isClass = true;
                BCT = ClassType.values()[c.getInt("ClassID")];
            }else{
                System.out.println("WTF NOTHIGNG?!?!?!?!??!");
                System.out.println(c+"|||+"+c.toString());
            }

            formatNametag();
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
