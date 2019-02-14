package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Server;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityData;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by carlt on 2/14/2019.
 */
public class FloatingTextUpdater extends Thread implements InterruptibleThread {

    private final FloatingTextFactory plugin;


    private static final Attribute[] DEFAULT_ATTRIBUTES = new Attribute[]{/*Attribute.getAttribute(Attribute.MAX_HEALTH).setMaxValue(100).setValue(100)*/};

    public FloatingTextUpdater(FloatingTextFactory plugin) {
        this.plugin = plugin;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        int lasttick = -1;
        while (Server.getInstance().isRunning()) {
            int tick = Server.getInstance().getTick();
            if (tick == lasttick) {
                lasttick = tick;



                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }
    }
}