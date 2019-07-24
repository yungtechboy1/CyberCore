package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.*;

/**
 * Created by carlt on 2/14/2019.
 */


public class FloatingTextFactory extends Thread implements InterruptibleThread {


    public static CyberCoreMain CCM;

    private static HashMap<Long, ArrayList<String>> FTLastSentToRmv = new HashMap<Long, ArrayList<String>>();

    private static List LList = Collections.synchronizedList(new ArrayList(1));
    private static List ToAddList = Collections.synchronizedList(new ArrayList(1));
    private static List ToRemoveList = Collections.synchronizedList(new ArrayList(1));
    private final static Object llock = new Object();

    public FloatingTextFactory(CyberCoreMain CCM) {
        new FloatingTextFactory(CCM, new ArrayList<>());
    }

    public FloatingTextFactory(CyberCoreMain c, ArrayList<CyberFloatingTextContainer> al) {
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
            LList.addAll(ToAddList);
            LList.removeAll(ToRemoveList);
            ToAddList.clear();
            _l =  LList;
        }
        return _l;
    }

    public static void setLList(ArrayList l) {
        synchronized (llock) {
            LList = l;
        }
    }

    public static void AddFloatingText(CyberFloatingTextContainer ftc){
        synchronized (llock){
            ToAddList.add(ftc);
//            System.out.println("added!");
        }
    }

    public static void AddToRemoveList(CyberFloatingTextContainer ftc){
        synchronized (llock){
            ToRemoveList.add(ftc);
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
//                HashMap<String, Position> ppss = GetPlayerPoss();
                for (Object ftt : getLList()) {
                    CyberFloatingTextContainer ft = (CyberFloatingTextContainer) ftt;
                    if( ft == null){
                        System.out.println("ERROR! Loading FT!!! "+ftt);
                        continue;
                    }

//                    System.out.println("|||||"+ft.GetText(null));
                    //Create Blank array if not present!
                    if (!FTLastSentToRmv.containsKey(ft.EID)) FTLastSentToRmv.put(ft.EID, new ArrayList<>());

//                    System.out.println("OU");
                    ft.OnUpdate(tick);
//                    System.out.println("OU");
//                    int ftlt = ft.LastUpdate;
// ?????????
// if (ftlt >= tick) continue;

                    ArrayList<String> ap = new ArrayList<>();
                    ArrayList<Player> app = new ArrayList<>();
                    //For Each player with pos
//                    for (String player : ppss.keySet()) {
//                    System.out.println("ERroror >> 1");
//                    System.out.println("ERroror >> "+ft.Lvl);
//                    System.out.println("ERroror >> "+ft.Syntax);
                    Collection<Player> pc = ft.Lvl.getChunkPlayers(ft.Pos.getChunkX(),ft.Pos.getChunkZ()).values();
                    if(pc == null || pc.isEmpty() || pc.size() == 0){
//                        System.out.println("ERroror roor ororororo E15072458");
                        continue;
                    }
                    for (Player p : pc ) {
                    String player = p.getName();
//                        System.out.println("2222"+player);
                        Position ppos = p.getPosition();
                        //TODO many implement a Quick Check?
                        //Check HERE If X is less that 100
                        //To Save resources
                        if (!ppos.level.getName().equalsIgnoreCase(ft.Pos.level.getName()) && ppos.distance(ft.Pos) > 100)//Not same World & 100+ Blocks away
                            continue;
                        ap.add(player);
                        app.add(p);
//                        System.out.println("AP");
                        //Remove Player we just added cuz We dont need to remove the FT particle from them!
                        if (FTLastSentToRmv.get(ft.EID).size() > 0) FTLastSentToRmv.get(ft.EID).remove(player);
                    }

                    //TODO Alt - Chunk Packet!

                    if(ft._CE_Done){
                        FTLastSentToRmv.get(ft.EID).addAll(ap);
                        KillUnnneded(FTLastSentToRmv.get(ft.EID), ft.EID);
                        ap.clear();
                        AddToRemoveList(ft);
                        continue;
                    }
                    if (ap.size() == 0) continue;
                    //Last time AP

                    //Remove Each player from FTLastSentToRmv
                    KillUnnneded(FTLastSentToRmv.get(ft.EID), ft.EID);
                    ft.HaldleSendP(app);
                    //Add All Players who we sent the packet to the remove list just in case they walk away
                    FTLastSentToRmv.get(ft.EID).addAll(ap);

                    //Should i send packes within the Thread?

                }


            }
            //A little faster than .1 of a sec (.06 to be exact...or 1 tick = 50millis and this is 60 millisecs)
            //Low key Every other 4 thiks is fine
            try {
                Thread.sleep(200*2);//4 Ticks*2
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