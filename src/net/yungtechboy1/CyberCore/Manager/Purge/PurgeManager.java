package net.yungtechboy1.CyberCore.Manager.Purge;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Server;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/8/2019.
 */
public class PurgeManager extends Thread implements InterruptibleThread {


    public CyberCoreMain CCM;


    public void KillPurge() {
        if (isAlive()) interrupt();
    }

    public void run() {
        int lasttick = -1;
        while (Server.getInstance().isRunning()) {
            int tick = Server.getInstance().getTick();
            if (tick == lasttick) {
                lasttick = tick;
            }
        }
    }
}
