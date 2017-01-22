package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;

/**
 * Created by carlt_000 on 7/7/2016.
 */
public class Restart extends PluginTask<CyberCoreMain> {

    public Position Pos;
    public Player P;
    public Integer Time;

    public Restart(CyberCoreMain owner, Integer time) {
        super(owner);
        Time = time;
    }
    public Restart(CyberCoreMain owner) {
        this(owner,120);
    }

    @Override
    public void onRun(int currentTick) {
        if(Time == 120){
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),60),60);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),30),90);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),15),105);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),10),110);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),5),115);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),4),116);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),3),117);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),2),118);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),1),119);
            getOwner().getServer().getScheduler().scheduleDelayedTask(new Restart(getOwner(),0),120);
            getOwner().getServer().broadcastMessage(TextFormat.AQUA+"Server Restart in 2 Mins!");
        }else if(Time == 0){
            getOwner().getServer().shutdown();
        }else{
            getOwner().getServer().broadcastMessage(TextFormat.AQUA+"Server Restart in "+Time+" Secs!");
        }
    }
}
