package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Item.CItemBook;
import net.yungtechboy1.CyberCore.Data.CustomSimpleConfigClass;

import java.util.ArrayList;

public class ClassMerchantData extends CustomSimpleConfigClass {
    private PowerEnum PE;
    private int ClassXPCost;
    private int ClassLevelCost;
    private int PlayerXPCost;
    private int PlayerLevelCost;
    private int MoneyCost;
    public ArrayList<ClassType> AllowedToPurchase = new ArrayList<>();
    public ClassMerchantData(PowerEnum PE) {
        this.PE = PE;
    }

    @Override
    public String toString() {
        return "ClassMerchantData{" +
                "PE=" + PE +
                ", ClassXPCost=" + ClassXPCost +
                ", ClassLevelCost=" + ClassLevelCost +
                ", PlayerXPCost=" + PlayerXPCost +
                ", PlayerLevelCost=" + PlayerLevelCost +
                ", MoneyCost=" + MoneyCost +
                ", AllowedToPurchase=" + AllowedToPurchase +
                '}';
    }

    public ClassMerchantData(ConfigSection c) {
        super(c);
        System.out.println(AllowedToPurchase.size()+"_______________________________");
        for(ClassType cv : AllowedToPurchase){
            System.out.println(cv+"<<<<<<<<<<<<<<|||||||");
        }
    }

    public ArrayList<ClassType> getAllowedToPurchase() {
        return AllowedToPurchase;
    }

//    public void setAllowedToPurchase(ArrayList<ClassType> allowedToPurchase) {
//        AllowedToPurchase = allowedToPurchase;
//    }

    @Override
    public Object phraseFromSave(String key, Object obj) {
//        if(key == "CMD"){
//            ArrayList<ClassMerchantData> c = new ArrayList<>();
//            for(Object o :((ConfigSection)obj).values()){
//                if(!(o instanceof ConfigSection))continue;
//                c.add(new ClassMerchantData((ConfigSection) o));
//            }
//            if (c != null && !c.isEmpty()) return c;
//            return null;
//        }
        System.out.println("111222222222221Could not pharse FROM save " + key);
        return null;
    }

    public PowerEnum getPE() {
        return PE;
    }

    public void setPE(PowerEnum PE) {
        this.PE = PE;
    }

    public int getClassXPCost() {
        return ClassXPCost;
    }

    public void setClassXPCost(int classXPCost) {
        ClassXPCost = classXPCost;
    }

    public int getClassLevelCost() {
        return ClassLevelCost;
    }

    public void setClassLevelCost(int classLevelCost) {
        ClassLevelCost = classLevelCost;
    }

    public int getPlayerXPCost() {
        return PlayerXPCost;
    }

    public void setPlayerXPCost(int playerXPCost) {
        PlayerXPCost = playerXPCost;
    }

    public int getPlayerLevelCost() {
        return PlayerLevelCost;
    }

    public void setPlayerLevelCost(int playerLevelCost) {
        PlayerLevelCost = playerLevelCost;
    }

    public int getMoneyCost() {
        return MoneyCost;
    }

    public void setMoneyCost(int moneyCost) {
        MoneyCost = moneyCost;
    }

    public String toButtonString() {
        String s = "";
        s += PE;
        if(getClassXPCost() > 0)s += " | ClassXP: "+getClassXPCost();
        if(getClassLevelCost() > 0)s += " | ClassLevel: "+getClassLevelCost();
        if(getPlayerLevelCost() > 0)s += " | PlayerLevel: "+getPlayerLevelCost();
        if(getPlayerXPCost() > 0)s += " | PlayerXP: "+getPlayerXPCost();
        if(getMoneyCost() > 0)s += " | Money: $"+getMoneyCost();
        return s;
    }

    public Item getItemBook() {
        CItemBook i = new CItemBook(getPE());
        return i;
    }
}
