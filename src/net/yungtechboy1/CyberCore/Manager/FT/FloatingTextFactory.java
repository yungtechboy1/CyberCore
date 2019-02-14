package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.Vector3f;
import javafx.geometry.Pos;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlt on 2/14/2019.
 */
public class FloatingTextFactory extends Thread implements InterruptibleThread {

    private CyberCoreMain CCM;

    protected final FloatingTextUpdater floatingTextUpdater = new FloatingTextUpdater(this);
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

        if (floatingTextUpdater.isAlive()) floatingTextUpdater.interrupt();
    }

    public HashMap<String, Position> GetPlayerPoss() {
        HashMap<String, Position> playerposs = new HashMap<>();
        for(Player p: Server.getInstance().getOnlinePlayers().values())playerposs.put(p.getName(),p.getPosition());
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
                    for(String player: ppss.keySet()){
                        Position pos = ppss[player];
                        if(pos.distance(ft.Pos) > 100)continue;
                        ap.add(player);
                    }
                    if(ap.size() == 0)continue;
                    Server.getInstance().getPlayer("aa").spawnTo();
                    ft

                }


                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }
    }

}