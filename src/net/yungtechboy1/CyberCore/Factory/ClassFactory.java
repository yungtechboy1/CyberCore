package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Minner.MineLifeClass;
import net.yungtechboy1.CyberCore.Classes.New.Minner.TNTSpecialist;
import net.yungtechboy1.CyberCore.Classes.New.Offense.*;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;

/**
 * Created by carlt_000 on 1/24/2017.
 */
public class ClassFactory implements Listener{

    public Config MMOSave;
    public Config LumberJackTreePlants;
    CyberCoreMain CCM;
//    private HashMap<String, BaseClass> ClassList = new HashMap<>();

    public ClassFactory(CyberCoreMain main) {
        CCM = main;
        MMOSave = new Config(new File(CCM.getDataFolder(), "MMOSave.yml"), Config.YAML);
        LumberJackTreePlants = new Config(new File(CCM.getDataFolder(), "LumberJackTreePlants.yml"), Config.YAML);
//        CCM.getServer().getScheduler().scheduleDelayedRepeatingTask(new LumberJackTreeCheckerTask(main), 20 * 60, 20 * 60);//Every Min
    }

//    handelEvent(event, cp);

    public void leaveClass(CorePlayer p) {
        MMOSave.remove(p.getName().toLowerCase());
        p.SetPlayerClass(null);
        p.sendMessage(TextFormat.GREEN + "You left your class!");
    }

    public BaseClass GetClass(CorePlayer p) {
        return GetClass(p,false);
    }
    public BaseClass GetClass(CorePlayer p, boolean force) {
        if (p == null) {
            CyberCoreMain.getInstance().getLogger().info("Error! Tring to get class from NULL");
            return null;
        }

        if(p.getPlayerClass() != null && !force){
            return p.getPlayerClass();
        }

        ConfigSection o = (ConfigSection) MMOSave.get(p.getName().toLowerCase());
        if (o != null) {
            BaseClass data = null;//new BaseClass(CCM, p, (ConfigSection) o);
            switch (BaseClass.ClassType.values()[o.getInt("TYPE",0)]){
                case Class_Miner_TNT_Specialist:
                    data = new TNTSpecialist(CCM, p, o);
                    break;
                case Class_Miner_MineLife:
                    data = new MineLifeClass(CCM, p, o);
                    break;
                case Class_Offense_Knight:
                    data = new Knight(CCM, p, o);
                    break;
                case Class_Offense_Mercenary:
                    data = new Mercenary(CCM, p, o);
                    break;
                case Class_Offense_Holy_Knight:
                    data = new HolyKnight(CCM, p, o);
                    break;
                case Class_Offense_Dark_Knight:
                    data = new DarkKnight(CCM, p, o);
                    break;
                case DragonSlayer:
                    data = new DragonSlayer(CCM, p, o);
                    break;
            }

            p.SetPlayerClass(data);
            return data;
        }
        return null;
    }

    public void SaveClassToFile(CorePlayer p) {
        BaseClass bc = p.getPlayerClass();
        if (bc != null) {
            MMOSave.set(p.getName().toLowerCase(), p.getPlayerClass().export());
            System.out.println("SAVEEE");
        } else {
            System.out.println(p.getName() + " HASS NUNN CLASS???");
        }
    }

//    @EventHandler
//    public void OnEvent(BlockBreakEvent event) {
//        handelEvent(event, (CorePlayer) event.getPlayer());
//    }
//
//    @EventHandler
//    public void OnEvent(BlockPlaceEvent event) {
//        handelEvent(event, (CorePlayer) event.getPlayer());
//    }
//
//    @EventHandler
//    public void OnEvent(PlayerInteractEvent event) {
//        handelEvent(event, (CorePlayer) event.getPlayer());
//    }

//    @EventHandler
//    public void OnEvent(BlockPlaceEvent event) {
//        CorePlayer cp = (CorePlayer) ((BlockPlaceEvent) event).getPlayer();
//        handelEvent(event, cp);
//    }
//
//    @EventHandler
//    public void OnEvent(BlockBreakEvent event) {
//        CorePlayer cp = (CorePlayer) ((BlockBreakEvent) event).getPlayer();
//        handelEvent(event, cp);
//    }
//
//    @EventHandler
//    public void OnEvent(EntityDamageEvent event) {
//        if(event.getEntity() instanceof Player) {
//            CorePlayer cp = (CorePlayer) (event).getEntity();
//            handelEvent(event, cp);
////        } else {
////            handelEvent(event, null);
//        }
//    }

//    @EventHandler
//    public void OnEvent(EntityDamageByEntityEvent event) {
//        CorePlayer cp = (CorePlayer) ((BlockPlaceEvent) event).getPlayer();
//        handelEvent(event, cp);
//    }


//    public void OnEvent(Event event) {
//        CorePlayer cp = null;
//        if (event instanceof 1) {
//            if (event instanceof BlockPlaceEvent) {
//            } else if (event instanceof BlockBreakEvent) {
//                cp = (CorePlayer) ((BlockBreakEvent) event).getPlayer();
//            }
//        } else if (event instanceof PlayerEvent) {
//            cp = (CorePlayer) ((PlayerEvent) event).getPlayer();
//        } else if (event instanceof EntityEvent) {
//            cp = (CorePlayer) ((EntityEvent) event).getEntity();
//        }
//
//        if (cp == null) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        if (cp == null) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        if (cp == null) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        if (cp == null) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        if (cp == null) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        if (cp == null) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        if (cp == null) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//
//    }

/*
    public void OnEvent(Event event) {
        if (event instanceof BlockBreakEvent || event instanceof BlockPlaceEvent || event instanceof PlayerInteractEvent || event instanceof EntityRegainHealthEvent) {
            if (event instanceof BlockBreakEvent) {
                handelEvent(event, ((BlockBreakEvent) event).getPlayer());
            } else if (event instanceof BlockPlaceEvent) {
                handelEvent(event, ((BlockPlaceEvent) event).getPlayer());
            } else if (event instanceof PlayerInteractEvent) {
                handelEvent(event, ((PlayerInteractEvent) event).getPlayer());
            } else if (event instanceof EntityRegainHealthEvent && ((EntityRegainHealthEvent) event).getEntity() instanceof Player) {
                handelEvent(event, (Player) ((EntityRegainHealthEvent) event).getEntity());
            }
        }
    }*/

    public void HandelEvent(Event event, CorePlayer p) {
        BaseClass bc = GetClass(p);
        if (bc == null) return;
        bc.HandelEvent(event);
    }

    public void Saveall() {
        CCM.getLogger().info("SAving All Classes!");
        for (Player p : CCM.getServer().getOnlinePlayers().values()) {
            if (!(p instanceof CorePlayer)) continue;
            CorePlayer cp = (CorePlayer) p;
            if (cp.getPlayerClass() == null) continue;
            MMOSave.set(cp.getName().toLowerCase(), cp.getPlayerClass().export());
        }
        CCM.getLogger().info("SAving File!");
        MMOSave.save();
    }
}
