package net.yungtechboy1.CyberCore.Manager.Factions.Tasks;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.Map;

/**
 * Created by carlt_000 on 8/15/2016.
 */
public class Purge extends PluginTask<FactionsMain>{

    public FactionsMain Main;
    public boolean Activate;
    public int Secs;

    public Purge(FactionsMain main,boolean activate,int secs){
        super(main);
        Main = main;
        Activate = activate;
        Secs = secs;
    }
/*
    public Purge(FactionsMain main,boolean activate){
        super(main);
        Main = main;
        Activate = activate;
        Secs = -1;

    }*/

    @Override
    public void onRun(int i) {
        if(!Activate && Secs == 60){
            /*for(Player p: Main.getServer().getOnlinePlayers().values()) {
                String t = TextFormat.LIGHT_PURPLE+""+TextFormat.BOLD+"====PURGE "+TextFormat.GOLD+"WARNING====";
                String m = "Purge will be starting soon!s";
                BossBarNotification b = new BossBarNotification(p,t,m,20*60,Main);
                b.send();
                Main.AddBBN(p,b);
            }*/
            Main.getServer().broadcastMessage(TextFormat.GRAY+"|--- "+TextFormat.LIGHT_PURPLE+"<{PURGE WARNING}>"+TextFormat.GRAY+" --|");//|----------<{WARNING}>------|
            Main.getServer().broadcastMessage(TextFormat.GRAY+"|"+TextFormat.LIGHT_PURPLE+"The purge will begin in 60 Secs"+TextFormat.GRAY+"|");//|->The purge will begin in 60 Secs --|
            Main.getServer().broadcastMessage(TextFormat.GRAY+"|"+TextFormat.LIGHT_PURPLE+"During that time ALL PVP Wil"+TextFormat.GRAY+"|");//|->The purge will begin in 60 Secs --|
            Main.getServer().broadcastMessage(TextFormat.GRAY+"|"+TextFormat.LIGHT_PURPLE+"   For Double the Cash!   "+TextFormat.GRAY+"|");//|->The purge will begin in 60 Secs --|
            Main.getServer().broadcastMessage(TextFormat.GRAY+"|----"+TextFormat.RED+"<{WARNING}>"+TextFormat.GRAY+"---|");//|->The purge will begin in 60 Secs --|
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,30),30*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,20),40*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,15),45*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,10),50*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,5),55*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,4),56*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,3),57*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,2),58*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,1),59*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,false,0),60*20);
            Main.getServer().getScheduler().scheduleDelayedTask(new Purge(Main,true,-60),60*20*2);
        }else if(!Activate){
            if(Secs == 0){
                Main.Purge = true;
                Main.getServer().broadcastMessage(TextFormat.GRAY+"["+TextFormat.LIGHT_PURPLE+"PURGE"+TextFormat.GRAY+"] PURGE!!!!!");
                return;
            }
            Main.getServer().broadcastMessage(TextFormat.GRAY+"["+TextFormat.LIGHT_PURPLE+"PURGE"+TextFormat.GRAY+"] Purge Starting in "+Secs+" Secs!");
        }else{
            if(Secs == -60){
                Main.Purge = false;
                Main.getServer().broadcastMessage(TextFormat.GRAY+"["+TextFormat.LIGHT_PURPLE+"PURGE"+TextFormat.GRAY+"] The Purge is now over! We hoped that cleared your souls!!");
            }
        }
    }
}
