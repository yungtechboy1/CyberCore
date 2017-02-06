package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.*;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Tasks.LumberJackTreeCheckerTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlt_000 on 1/24/2017.
 */
public class ClassFactory implements Listener {

    public Config MMOSave;
    public Config LumberJackTreePlants;
    CyberCoreMain CCM;
    private HashMap<String, BaseClass> ClassList = new HashMap<>();

    public ClassFactory(CyberCoreMain main) {
        CCM = main;
        MMOSave = new Config(new File(CCM.getDataFolder(), "MMOSave.yml"), Config.YAML);
        LumberJackTreePlants = new Config(new File(CCM.getDataFolder(), "LumberJackTreePlants.yml"), Config.YAML);
        CCM.getServer().getScheduler().scheduleDelayedRepeatingTask(new LumberJackTreeCheckerTask(main),20*60,20*60);//Every Min
    }

    public BaseClass GetClass(Player p) {
        if (!ClassList.containsKey(p.getName().toLowerCase())) {
            Object o = MMOSave.get(p.getName().toLowerCase());
            if (o != null && o instanceof ConfigSection) {
                BaseClass data = new BaseClass(CCM, p, (ConfigSection) o);
                if (((ConfigSection) o).getInt("TYPE", -1) == BaseClass.TYPE_MINER) {
                    data = new Class_Miner(CCM, p, (ConfigSection) o);
                } else if (((ConfigSection) o).getInt("TYPE", -1) == BaseClass.TYPE_DIGGER) {
                    data = new Class_Digger(CCM, p, (ConfigSection) o);
                } else if (((ConfigSection) o).getInt("TYPE", -1) == BaseClass.TYPE_FARMER) {
                    data = new Class_Farmer(CCM, p, (ConfigSection) o);//@TODO Farm
                } else if (((ConfigSection) o).getInt("TYPE", -1) == BaseClass.TYPE_LUMBERJACK) {
                    data = new Class_LumberJack(CCM, p, (ConfigSection) o);
                } else if (((ConfigSection) o).getInt("TYPE", -1) == BaseClass.TYPE_PYRO) {
                    data = new Class_Miner(CCM, p, (ConfigSection) o);//@TODO PYRO
                } else if (((ConfigSection) o).getInt("TYPE", -1) == BaseClass.TYPE_RAIDER) {
                    data = new Class_Miner(CCM, p, (ConfigSection) o);//@TODO RAIDER
                } else if (((ConfigSection) o).getInt("TYPE", -1) == BaseClass.TYPE_TANK) {
                    data = new Class_Tank(CCM, p, (ConfigSection) o);
                } else if (((ConfigSection) o).getInt("TYPE", -1) == BaseClass.TYPE_WARRIOR) {
                    data = new Class_Miner(CCM, p, (ConfigSection) o);//@TODO Warrior
                }
                ClassList.put(p.getName().toLowerCase(), data);
                return data;
            }
        }
        return ClassList.get(p.getName().toLowerCase());
    }

    public void AddToClassListAfterSave(String p) {
        Object o = MMOSave.get(p.toLowerCase());
        if (o != null && o instanceof ConfigSection) {
            BaseClass data = new BaseClass(CCM, null, (ConfigSection) o);
            ClassList.put(p.toLowerCase(), data);
            return;
        }
    }

    public void SetClass(Player p, BaseClass bc) {
        ClassList.put(p.getName().toLowerCase(), bc);
    }

    @EventHandler
    public void OnEvent(BlockBreakEvent event) {
        HandelEvent(event, event.getPlayer());
    }

    @EventHandler
    public void OnEvent(BlockPlaceEvent event) {
        HandelEvent(event, event.getPlayer());
    }

    @EventHandler
    public void OnEvent(PlayerInteractEvent event) {
        HandelEvent(event, event.getPlayer());
    }

    @EventHandler
    public void OnEvent(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) HandelEvent(event, (Player) event.getEntity());
    }

/*
    public void OnEvent(Event event) {
        if (event instanceof BlockBreakEvent || event instanceof BlockPlaceEvent || event instanceof PlayerInteractEvent || event instanceof EntityRegainHealthEvent) {
            if (event instanceof BlockBreakEvent) {
                HandelEvent(event, ((BlockBreakEvent) event).getPlayer());
            } else if (event instanceof BlockPlaceEvent) {
                HandelEvent(event, ((BlockPlaceEvent) event).getPlayer());
            } else if (event instanceof PlayerInteractEvent) {
                HandelEvent(event, ((PlayerInteractEvent) event).getPlayer());
            } else if (event instanceof EntityRegainHealthEvent && ((EntityRegainHealthEvent) event).getEntity() instanceof Player) {
                HandelEvent(event, (Player) ((EntityRegainHealthEvent) event).getEntity());
            }
        }
    }*/

    public void HandelEvent(Event event, Player p) {
        BaseClass bc = GetClass(p);
        if (bc == null) return;
        bc.HandelEvent(event);
    }

    public void Saveall() {
        for (Map.Entry<String, BaseClass> o : ClassList.entrySet()) {
            if(o.getValue() == null)continue;
            MMOSave.set(o.getKey(), o.getValue().export());
        }
        MMOSave.save();
        LumberJackTreePlants.save();
    }
}
