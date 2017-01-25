package net.yungtechboy1.CyberCore.Commands.Abilities;

import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/25/2017.
 */
public class Ability {
    public static int NONE = 0;
    public static int TYPE_MINING = 1;
    public static int MINING_SUPER_BREAK = 1;
    public static int MINING_DOUBLE_DROPS = 2 ;
    public static int MINING_BLAST_MINING = 3;
    public static int MINING_BIGGER_BOMBS = 4;
    public static int MINING_DEMOLITIONS_EXPERTISE = 5;
    public static int MINING_ = 1;
    public static int MINING_ = 1;
    public static int MINING_ = 1;
    public static int MINING_ = 1;
    public static int MINING_ = 1;
    public static int MINING_ = 1;

    private CyberCoreMain CCM;
    private int ID = 0;
    private int TYPE = 0;
    private int LVL = 0;
    private int XP = 0;
    public Ability(CyberCoreMain main){
        CCM = main;
    }
    public Ability(CyberCoreMain main, int type, int id, int xp){
        CCM = main;
        ID = id;
        TYPE = type;
        XP = xp;
        LVL = XPToLevel(xp);
    }

    public int XPToLevel(int xp) {
        int lvl = 0;
        while (xp >= calculateRequireExperience(lvl)) {
            xp = xp - calculateRequireExperience(lvl);
            lvl++;
        }
        return lvl;
    }

    public int XPRemainder(int xp) {
        int lvl = 0;
        while (xp >= calculateRequireExperience(lvl)) {
            xp = xp - calculateRequireExperience(lvl);
            lvl++;
        }
        return xp;
    }

    public int calculateRequireExperience(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9 * 100;
        } else if (level >= 15) {
            return 37 + (level - 15) * 5 * 100;
        } else {
            return 7 + level * 2 * 100;
        }
    }

    public void HandelEvent(Event event){
        if(event instanceof BlockBreakEvent){
            BlockBreakEvent((BlockBreakEvent)event);
        }else if(event instanceof BlockPlaceEvent){
            BlockPlaceEvent((BlockPlaceEvent)event);
        }
    }

    public void BlockBreakEvent(BlockBreakEvent event){

    }

    public void BlockPlaceEvent(BlockPlaceEvent event){

    }
}
