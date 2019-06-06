package net.yungtechboy1.CyberCore.Classes.New.Crafting;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/12/2019.
 */
public class MadScientist extends BaseClass {
    public MadScientist(CyberCoreMain main, CorePlayer player, int mid, int rank, int xp) {
        super(main, player, ClassType.Class_Miner_TNT_Specialist);
    }

    public int GetImpurity(){
        int lvl =  getLVL();
        int mi = -1;
        if(lvl >= 91 && mi == -1)mi = 30;
        else if(lvl >= 81 && mi == -1)mi = 35;
        else if(lvl >= 71 && mi == -1)mi = 40;
        else if(lvl >= 61 && mi == -1)mi = 45;
        else if(lvl >= 51 && mi == -1)mi = 50;
        else if(lvl >= 41 && mi == -1)mi = 60;
        else if(lvl >= 31 && mi == -1)mi = 70;
        else if(lvl >= 21 && mi == -1)mi = 80;
        else if(lvl >= 11 && mi == -1)mi = 90;
        else if(lvl >= 1 && mi == -1)mi = 100;
        return mi;
    }

    public int GetMaxStandBuff(){
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
    public ClassType getTYPE() {
        return null;
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
        return null;
    }
    }
