package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.New.Magic.Priest;
import net.yungtechboy1.CyberCore.Classes.New.Magic.Sorcerer;
import net.yungtechboy1.CyberCore.Classes.New.Minner.MineLifeClass;
import net.yungtechboy1.CyberCore.Classes.New.Minner.TNTSpecialist;
import net.yungtechboy1.CyberCore.Classes.New.Offense.*;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/24/2017.
 */
public class ClassFactory implements Listener {

    public static final ArrayList<PowerEnum> DEFAULTPOWERS = new ArrayList<PowerEnum>() {
        {
            add(PowerEnum.MineLife);
        }
    };

    public static final HashMap<PowerEnum, Integer> BUYPOWERS = new HashMap<PowerEnum, Integer>() {
        {
            put(PowerEnum.FireBox, 10);//XP Cost
        }
    };


    public Config MMOSave;
    public Config PlayerLearnedPowers;
    CyberCoreMain CCM;
//    private HashMap<String, BaseClass> ClassList = new HashMap<>();

    public ClassFactory(CyberCoreMain main) {
        CCM = main;
        MMOSave = new Config(new File(CCM.getDataFolder(), "MMOSave.yml"), Config.YAML);
        PlayerLearnedPowers = new Config(new File(CCM.getDataFolder(), "PlayerLearnedPowers.yml"), Config.YAML);
//        CCM.getServer().getScheduler().scheduleDelayedRepeatingTask(new LumberJackTreeCheckerTask(main), 20 * 60, 20 * 60);//Every Min
    }

    public static String readFileAsString(String fileName) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    private void loadMMOSave() {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
//            Type listOfMyClassObject = new TypeToken<ArrayList<MyClass>>() {}.getType();
            String content = readFileAsString(new File(CCM.getDataFolder().toString(), "MMOSave.yml").toString());
//            this.config = new ConfigSection(gson.fromJson(content, new TypeToken<LinkedHashMap<String, Object>>() {
//            }.getType()));
        } catch (Exception e) {

        }
    }

    //    handelEvent(event, cp);
    public PowerEnum[] getRegisteredPowers(CorePlayer p) {
        ArrayList<PowerEnum> pe = new ArrayList<>();
        if (!PlayerLearnedPowers.exists(p.getName().toLowerCase())) return new PowerEnum[0];
        ConfigSection c = PlayerLearnedPowers.getSection(p.getName().toLowerCase());
        for (Object v : c.getAllMap().values()) {
            if (v instanceof Integer) {
                pe.add(PowerEnum.fromint((Integer) v));
            } else {
                System.out.println("EEEEEEEEQweqweqwe qwe qwe qweqwqe qweqweqqqqqqqqq!");
            }
        }

        return (PowerEnum[]) pe.toArray();
    }

    public void registerPowerToPlayer(CorePlayer p, PowerEnum e) {
        ConfigSection c = new ConfigSection();
        if (PlayerLearnedPowers.exists(p.getName().toLowerCase())) {
            c = PlayerLearnedPowers.getSection(p.getName().toLowerCase());
        }
        c.set(e.name(), e.ordinal());
        PlayerLearnedPowers.set(p.getName().toLowerCase(), c);
    }

    public void leaveClass(CorePlayer p) {
        p.getPlayerClass().onLeaveClass();
        MMOSave.remove(p.getName().toLowerCase());
        p.SetPlayerClass(null);
        p.sendMessage(TextFormat.GREEN + "You left your class!");
    }

    public BaseClass GetClass(CorePlayer p) {
        return GetClass(p, false);
    }

    public BaseClass GetClass(CorePlayer p, boolean force) {
        if (p == null) {
            CyberCoreMain.getInstance().getLogger().info("Error! Tring to get class from NULL");
            return null;
        }

        if (p.getPlayerClass() != null && !force) {
            return p.getPlayerClass();
        }

        ConfigSection o = (ConfigSection) MMOSave.get(p.getName().toLowerCase());
        if (o != null) {
            BaseClass data = null;//new BaseClass(CCM, p, (ConfigSection) o);
            switch (ClassType.values()[o.getInt("TYPE", 0)]) {
                case Unknown:
                    break;
                case Class_Miner_TNT_Specialist:
                    data = new TNTSpecialist(CCM, p, o);
                    break;
                case Class_Miner_MineLife:
                    data = new MineLifeClass(CCM, p, o);
                    break;
                case Class_Magic_Enchanter:
                    break;
                case Class_Rouge_Theif:
                    data = new Theif(CCM, p, o);
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
                case Class_Offense_DragonSlayer:
                    data = new DragonSlayer(CCM, p, o);
                    break;
                case Class_Offense_Assassin:
                    break;
                case Class_Offense_Raider:
                    break;
                case Class_Magic_Sorcerer:
                    data = new Sorcerer(CCM, p, o);
                    break;
                case Class_Priest:
                    data = new Priest(CCM, p, o);
                    break;
            }
            if(data == null){
                System.out.println("ERROROROROR NONEEE WEREEEEEE DDDDDDDDDDD"+o.getInt("TYPE", 0));
                return null;
            }
            System.out.println(p.getName()+"'s CLASS WAS LOADEDEDDDD NONEEE WEREEEEEE DDDDDDDDDDD");
            data.onCreate();
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
        MMOSave.save();
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

//    public void HandelEvent(Event event, CorePlayer p) {
//        BaseClass bc = GetClass(p);
//        if (bc == null) return;
//        bc.HandelEvent(event);
//    }

    public void Saveall() {
        CCM.getLogger().info("SAving All Classes!");
        for (Player p : new ArrayList<>(CCM.getServer().getOnlinePlayers().values())) {
            if (!(p instanceof CorePlayer)) continue;
            CorePlayer cp = (CorePlayer) p;
            if (cp.getPlayerClass() == null) continue;
            save(cp,false);
        }
        CCM.getLogger().info("SAving File!");
        MMOSave.save();
    }

    public void save(CorePlayer cp){
        save(cp,true);
    }
    public void save(CorePlayer cp, boolean savefile){
        if(cp.getPlayerClass() == null)return;
        MMOSave.set(cp.getName().toLowerCase(), cp.getPlayerClass().export());
        if(savefile)MMOSave.save();
    }
}
