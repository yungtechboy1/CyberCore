package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlt on 2/14/2019.
 */


public class FloatingTextFactory extends Thread implements InterruptibleThread {


    public static CyberCoreMain CCM;

    private static HashMap<Long, ArrayList<String>> LastPL = new HashMap<Long, ArrayList<String>>();

    private static List LList = Collections.synchronizedList(new ArrayList(1));
    private static List TList = Collections.synchronizedList(new ArrayList(1));
    private static List RList = Collections.synchronizedList(new ArrayList(1));
    private final static Object llock = new Object();

    public FloatingTextFactory(CyberCoreMain CCM) {
        new FloatingTextFactory(CCM, new ArrayList<>());
    }

    public FloatingTextFactory(CyberCoreMain c, ArrayList<FloatingTextContainer> al) {
        LList = al;
        CCM = c;
        start();
        //TODO
        //Create New Tick Function and exicute every 40 Ticks
        //Just Call run in here

    }

    public List getLList() {
        List _l;
        synchronized (llock) {
            LList.addAll(TList);
            LList.removeAll(RList);
            TList.clear();
            _l =  LList;
        }
        return _l;
    }

    public static void setLList(ArrayList l) {
        synchronized (llock) {
            LList = l;
        }
    }

    public static void AddFloatingText(FloatingTextContainer ftc){
        synchronized (llock){
            TList.add(ftc);
//            System.out.println("added!");
        }
    }

    public void AddToRemoveList(FloatingTextContainer ftc){
        synchronized (llock){
            RList.add(ftc);
//            System.out.println("added!");
        }
    }

    public void CTstop() {
        if (isAlive()) interrupt();
    }

    public HashMap<String, Position> GetPlayerPoss() {
//        System.out.println("GP");
        HashMap<String, Position> playerposs = new HashMap<>();
        for (Player p : Server.getInstance().getOnlinePlayers().values())
            playerposs.put(p.getName(), p.getPosition());

//        System.out.println("EP"+playerposs.size());
        return playerposs;
    }

    public void run() {
        int lasttick = -1;
//        System.out.println("11111111111111111111");
        while (Server.getInstance().isRunning()) {
//System.out.println("======");
            int tick = Server.getInstance().getTick();
            if (tick != lasttick) {

//                System.out.println("||||||||======");
                lasttick = tick;
                HashMap<String, Position> ppss = GetPlayerPoss();
                for (Object ftt : getLList()) {

//                    System.out.println("|||||");
                    FloatingTextContainer ft;
                    ft = (FloatingTextContainer) ftt;
                    if( ft != null)continue;

//                    System.out.println("|||||"+ft.GetText(null));
                    ArrayList<String> a = new ArrayList<>();
                    if (!LastPL.containsKey(ft.EID)) LastPL.put(ft.EID, a);

//                    System.out.println("OU");
                    ft.OnUpdate(tick);
//                    System.out.println("OU");
//                    int ftlt = ft.LastUpdate;
// ?????????
// if (ftlt >= tick) continue;

                    ArrayList<String> ap = new ArrayList<>();
                    for (String player : ppss.keySet()) {

//                        System.out.println("2222"+player);
                        Position ppos = ppss.get(player);
                        //TODO many implement a Quick Check?
                        //Check HERE If X is less that 100
                        //To Save resources
                        if (!ppos.level.getName().equalsIgnoreCase(ft.Pos.level.getName()) && ppos.distance(ft.Pos) > 100)//Not same World & 100+ Blocks away
                            continue;
                        ap.add(player);
//                        System.out.println("AP");
                        if (LastPL.get(ft.EID).size() > 0) LastPL.get(ft.EID).remove(player);
                    }

                    if(ft._CE_Done){
                        LastPL.get(ft.EID).addAll(ap);
                        KillUnnneded(LastPL.get(ft.EID), ft.EID);
                        ap.clear();
                        AddToRemoveList(ft);
                        continue;
                    }
                    if (ap.size() == 0) continue;
                    //Last time AP

                    //Remove Each player from LastPL
                    KillUnnneded(LastPL.get(ft.EID), ft.EID);
                    ft.HaldleSend(ap);

                    //Should i send packes within the Thread?

                }


            }
            //A little faster than .1 of a sec (.06 to be exact...or 1 tick = 50millis and this is 60 millisecs)
            //Low key Every other 4 thics is fine
            try {
                Thread.sleep(200);//4 Ticks
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }

    private void KillUnnneded(ArrayList<String> strings, long eid) {
        for (String p : strings) {
            Player pp = CCM.getServer().getPlayerExact(p);
            kill(eid, pp);
        }
    }

    public String FormatText(String text, Player player) {
//        if(text == null)System.out.println("----0");
//        if(CCM == null)System.out.println("----1");
//        if(CCM.getServer() == null)System.out.println("----2");
//        if(CCM.getServer().getOnlinePlayers() == null)System.out.println("----3");
//        System.out.println("----4"+CCM.getServer().getOnlinePlayers().size());
//        System.out.println("----5"+CCM.getServer().getTicksPerSecondAverage());
//        System.out.println("----6"+text);
        text = text.replace("{online-players}", "" + CCM.getServer().getOnlinePlayers().size())
                .replace("{ticks}", CCM.getServer().getTicksPerSecondAverage() + "")
                .replace("`", "\n")
                .replace("{&}", TextFormat.ESCAPE + "");
        if (player != null) text = text.replace("{name}", player.getName());
        if (player != null) {
            //Faction

            String pf = "No Faction";
            if (CCM.FM != null) {
                pf = CCM.FM.getPlayerFaction(player.getName());
                if (pf == null) pf = "No Faction";
            }
            //Kills
            Double kills = 0d;//Factions.GetKills(player.getName());
//            if(KDConfig.exists(player.getName().toLowerCase())){
//                kills = Double.parseDouble(((LinkedHashMap)KDConfig.get(player.getName().toLowerCase())).get("kills")+"");
//            }
            //Deaths
            Double deaths = 0d;//Factions.GetDeaths(player.getName());
//            if(KDConfig.exists(player.getName().toLowerCase())){
//                deaths = Double.parseDouble(((LinkedHashMap)KDConfig.get(player.getName().toLowerCase())).get("deaths")+"");
//            }
            //KDR
            Double kdr = kills / deaths;//Factions.GetKDR(player.getName());
            CyberCoreMain CC = (CyberCoreMain) CCM.getServer().getPluginManager().getPlugin("net/yungtechboy1/CyberCore");
            String rank = "Guest|";
            if (CC != null) {
                rank = CC.getPlayerRank(player.getName()).getDisplayName();
                if (rank == null) rank = "Guest";
            }
            String tps = "" + CCM.getServer().getTicksPerSecond();
            String players = "" + CCM.getServer().getOnlinePlayers().size();
            String max = "" + CCM.getServer().getMaxPlayers();
            String money = "0";
//            ArchEconMain AA = (ArchEconMain) CCM.getServer().getPluginManager().getPlugin("ArchEcon");
//            if(AA != null){
//                money = ""+AA.GetMoney(player.getName());
//            }

            text = text
                    .replace("{faction}", pf)
                    .replace("{kills}", kills + "")
                    .replace("{deaths}", deaths + "")
                    .replace("{kdr}", kdr + "")
                    .replace("{rank}", rank)
                    .replace("{tps}", tps)
                    .replace("{players}", players)
                    .replace("{max}", max)
                    .replace("{money}", money)
            ;
        } else {
            text = text
                    .replace("{faction}", "No Faction")
                    .replace("{kills}", "N/A")
                    .replace("{deaths}", "N/A")
                    .replace("{kdr}", "N/A");
        }
        return text;
    }

    public static void kill(long eid, Player p) {
        if(p == null)return;
        RemoveEntityPacket pk = new RemoveEntityPacket();
        pk.eid = eid;

        p.dataPacket(pk);
    }
}