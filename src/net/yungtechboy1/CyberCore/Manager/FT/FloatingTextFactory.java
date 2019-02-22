package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.Vector3f;
import cn.nukkit.utils.TextFormat;
import javafx.geometry.Pos;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by carlt on 2/14/2019.
 */
public class FloatingTextFactory extends Thread implements InterruptibleThread {

    private CyberCoreMain CCM;

    ArrayList<FloatingTextContainer> LList = new ArrayList<>();

    public FloatingTextFactory(CyberCoreMain CCM) {
        new FloatingTextFactory(CCM, new ArrayList<>());
    }

    public FloatingTextFactory(CyberCoreMain CCM, ArrayList<FloatingTextContainer> al) {
        LList = al;
        start();
        //TODO
        //Create New Tick Function and exicute every 40 Ticks
        //Just Call run in here

    }

    public void CTstop() {
        if (isAlive()) interrupt();
    }

    public HashMap<String, Position> GetPlayerPoss() {
        HashMap<String, Position> playerposs = new HashMap<>();
        for (Player p : Server.getInstance().getOnlinePlayers().values()) playerposs.put(p.getName(), p.getPosition());
        return playerposs;
    }

    public void run() {
        int lasttick = -1;
        while (Server.getInstance().isRunning()) {
            int tick = Server.getInstance().getTick();
            if (tick == lasttick) {
                lasttick = tick;
                HashMap<String, Position> ppss = GetPlayerPoss();
                for (FloatingTextContainer ft : LList) {
                    int ftlt = ft.LastUpdate;
                    if (ftlt >= tick) continue;
                    ArrayList<String> ap = new ArrayList<>();
                    for (String player : ppss.keySet()) {
                        Position ppos = ppss.get(player);
                        //TODO many implement a Quick Check?
                        //Check HERE If X is less that 100
                        //To Save resources
                        if (!ppos.level.getName().equalsIgnoreCase(ft.Pos.level.getName()) && ppos.distance(ft.Pos) > 100)//Not same World & 100+ Blocks away
                            continue;
                        ap.add(player);
                    }
                    if (ap.size() == 0) continue;
                    ft.HaldleSend(ap);

                    //Should i send packes within the Thread?

                }


            }
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }

    public String FormatText(String text, Player player) {
        text = text.replace("{online-players}", "" + CCM.getServer().getOnlinePlayers().size())
                .replace("{ticks}", CCM.getServer().getTicksPerSecondAverage() + "")
                .replace("`", "\n")
                .replace("{&}", TextFormat.ESCAPE + "");
        if (player != null) text = text.replace("{name}", player.getName());
        if (player != null) {
            //Faction

            String pf = "No Faction";
            if (CCM.FM != null) {
                pf = CCM.FM.getPlayerFaction(player);
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
            CyberCoreMain CC = (CyberCoreMain) CCM.getServer().getPluginManager().getPlugin("CyberCore");
            String rank = "Guest|";
            if (CC != null) {
                rank = CC.getPlayerRankCache(player.getName());
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
}