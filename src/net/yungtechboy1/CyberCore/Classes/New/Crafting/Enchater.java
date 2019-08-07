package net.yungtechboy1.CyberCore.Classes.New.Crafting;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/13/2019.
 */
public class Enchater extends BaseClass {
    public Enchater(CyberCoreMain main, CorePlayer player, int xp) {
        super(main, player, ClassType.Class_Miner_TNT_Specialist);
    }

    public int GetMaxFail(){
        int lvl =  getLVL();
        int mi = -1;
        if(lvl >= 91 && mi == -1)mi = 30;
        else if(lvl >= 81 && mi == -1)mi = 40;
        else if(lvl >= 71 && mi == -1)mi = 45;
        else if(lvl >= 61 && mi == -1)mi = 50;
        else if(lvl >= 51 && mi == -1)mi = 60;
        else if(lvl >= 41 && mi == -1)mi = 75;
        else if(lvl >= 31 && mi == -1)mi = 85;
        else if(lvl >= 1 && mi == -1)mi = 100;
        return mi;
    }

    public int GetMaxSuccess(){
        int lvl =  getLVL();
        int mi = -1;
        if(lvl >= 91 && mi == -1)mi = 100;
        else if(lvl >= 81 && mi == -1)mi = 95;
        else if(lvl >= 71 && mi == -1)mi = 85;
        else if(lvl >= 61 && mi == -1)mi = 75;
        else if(lvl >= 51 && mi == -1)mi = 60;
        else if(lvl >= 41 && mi == -1)mi = 45;
        else if(lvl >= 31 && mi == -1)mi = 30;
        else if(lvl >= 21 && mi == -1)mi = 20;
        else if(lvl >= 11 && mi == -1)mi = 15;
        else if(lvl >= 1 && mi == -1)mi = 10;
        return mi;
    }

    @Override
    public PrimalPowerType getPowerSourceType() {
        return null;
    }

    @Override
    public ClassType getTYPE() {
        return ClassType.Class_Magic_Enchanter;
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public void initBuffs() {

    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }


    @Override
    public String getName() {
        return "Enchanter";
    }

}