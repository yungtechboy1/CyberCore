package net.yungtechboy1.CyberCore.Classes.Abilities;

import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import cn.nukkit.Player;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

import java.util.Calendar;

/**
 * Created by carlt_000 on 1/25/2017.
 */
public class Ability extends PluginTask<CyberCoreMain> {
    public static int NONE = 0;
    public static int MINER_SUPER_BREAK = 1;
    public static int MINER_DOUBLE_DROPS = 2 ;
    public static int MINER_BLAST_MINER = 3;
    public static int MINER_BIGGER_BOMBS = 4;
    public static int MINER_DEMOLITIONS_EXPERTISE = 5;

    public static int TANK_DOUBLE_HEARTS= 1;

    public static int LUMBERJACK_TREE_FELLER = 1;
    public static int LUMBERJACK_FORESTFIRE = 2;

    @Deprecated
    public static int FARMER_GREEN_THUMB = 1;


    @Deprecated
    public static int HERBALISM_GREEN_TERRA = 1;
    public static int HERBALISM_GREEN_THUMB = 1;
    public static int HERBALISM_SHROOM_THUMB = 1;
    public static int HERBALISM_FARMERS_DIET = 1;
    @Deprecated
    public static int HERBALISM_HYLIAN_LUCK = 1;
    public static int HERBALISM_DOUBLE_DROPS = 1;

    public static int TYPE_EXCAVATION_ = 2;
    public static int EXCAVATION_NONE = 0 ;



    public CyberCoreMain CCM;
    public int ID = 0;
    public int TYPE = 0;
    public BaseClass BC;

    public Item firsthand = null;
    public Item afterhand = null;

    public Player player;
    public Ability(CyberCoreMain main){
        super(main);
        CCM = main;
    }
    public Ability(CyberCoreMain main, BaseClass bc, int type, int id){
        super(main);
        CCM = main;
        ID = id;
        TYPE = type;
        BC = bc;
        player = BC.getPlayer();
    }

    public String getName(){
        return this.getClass().getName();
        //return "Unknown Abillity #"+ID+" Type "+TYPE;
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

    public CraftItemEvent CraftItemEvent(CraftItemEvent event){
        return event;
    }
    public PlayerToggleSprintEvent PlayerToggleSprintEvent(PlayerToggleSprintEvent event){
        return event;
    }

    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event){
        return event;
    }

    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event){
        return event;
    }

    public BlockPlaceEvent BlockPlaceEvent(BlockPlaceEvent event){
        return event;
    }

    public PlayerJumpEvent PlayerJumpEvent(PlayerJumpEvent event) {
        return event;
    }
    public EntityRegainHealthEvent EntityRegainHealthEvent(EntityRegainHealthEvent event) {
        return event;
    }

    public PlayerInteractEvent PlayerInteractEvent(PlayerInteractEvent event) {
        return event;
    }

    public void PlayerDropItemEvent(PlayerDropItemEvent event){
        Item dropped = event.getItem();
        if(dropped.deepEquals(firsthand,true,true) || dropped.deepEquals(afterhand))event.setCancelled();
    }
    public void PlayerDeathEvent(PlayerDeathEvent event){
        Item[] drops = event.getDrops();
        Item[] drops2 = event.getDrops().clone();
        int a = 0;
        for(Item item: drops) {
            if (item.deepEquals(firsthand, true, true) || item.deepEquals(afterhand,true,true)){
              drops2[a] = new Item(0,0,0);
            }
            a++;
        }
        event.setDrops(drops2);
    }
    public void PlayerQuitEvent(PlayerQuitEvent event){
        PlayerInventory pi = event.getPlayer().getInventory();
        for(int x = 0; x < pi.getSize()-1; x++){
            Item i = pi.getItem(x);
            if (i.deepEquals(firsthand, true, true) || i.deepEquals(afterhand,true,true)){
                pi.setItem(x,Item.get(0));
                break;
            }

        }
    }

    public boolean activate() {
        return true;
    }

    public void deactivate(){
        BaseClass bm = CCM.ClassFactory.GetClass((CorePlayer) player);
        bm.AddCooldown(ID+"",GetCooldown());
    }
    @Override
    public void onRun(int i) {

    }

    public int LevelToSec(){
       return LevelToSec(50,2);
    }
    public int LevelToSec(int divide){
        return LevelToSec(divide,2);
    }
    public int LevelToSec(int divide, int min){
        int res =  BC.getLVL() % divide;
        return min + ((BC.getLVL() - res) / divide);
    }

    public int getTime(){
        return (int)(Calendar.getInstance().getTime().getTime()/1000);
    }

    public int GetCooldown(){
        return getTime() + 240;
    }

    public void PrimeEvent(){}
}
