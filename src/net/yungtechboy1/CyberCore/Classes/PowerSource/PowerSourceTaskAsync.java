package net.yungtechboy1.CyberCore.Classes.PowerSource;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextContainer;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PowerSourceTaskAsync  extends Thread implements InterruptibleThread {


    public static CyberCoreMain CCM;

    private final static int Cooldown = 20*20;

    public PowerSourceTaskAsync(CyberCoreMain c) {
        CCM = c;
        start();
        //TODO
        //Create New Tick Function and exicute every 40 Ticks
        //Just Call run in here

    }

    public void CTstop() {
        if (isAlive()) interrupt();
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
                if(tick % Cooldown == 0){
                    for(Player p: CCM.getServer().getOnlinePlayers().values()){
                        if(p instanceof CorePlayer){
                            CorePlayer cp = (CorePlayer)p;
                            cp.tickPowerSource(tick);
                        }
                    }
                }
            }
            //A little faster than .1 of a sec (.06 to be exact...or 1 tick = 50millis and this is 60 millisecs)
            //Low key Every other 4 thics is fine
            try {
                Thread.sleep(Cooldown*50);//4 Ticks
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }
}