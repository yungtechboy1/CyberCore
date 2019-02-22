package net.yungtechboy1.CyberCore.Manager.Factions.BossBar;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.Map;

public class BossBarNotification{

    public int eid;
    public Player owner;
    public String title;
    public String msg;
    public int maxHealth;
    public int currentHealth;
    public int lasttick;
    public boolean visible = true;

    public FactionsMain Main;

    public int startTime = -1;
    public int endTime = -1;

    public BossBarNotification(Player owner, String ttitle, String mmsg, FactionsMain main){
        this(owner, ttitle, mmsg, 20*6, main);
    }

    public BossBarNotification(Player owner, String ttitle, String mmsg, int tticks, FactionsMain main){
        maxHealth = tticks;
        currentHealth = tticks;
        title = ttitle;
        msg = mmsg;
        Main = main;

        this.owner = owner;
        this.eid = (int) Entity.entityCount++;
    }

    public void setVisible(boolean bool){
        if(bool){
            send();
        }else{
            Packet.removeBossBar(getOwner(), this.eid);
        }
        this.visible = bool;
    }

    public void setHealth(Integer health){
        if(health > this.maxHealth){
            health = this.maxHealth;
        }
        if(health < 0){
            health = 0;
        }
        currentHealth = health;

        Packet.sendPercentage(getOwner(),this.eid, this.currentHealth / (double) this.maxHealth * 100);
    }

    public int getHealth(){
        return this.currentHealth;
    }

    public int getMaxHealth(){
        return this.maxHealth;
    }

    public Player getOwner(){
        return this.owner;
    }

    public void setTitle(String ttitle){
        setTitle(ttitle,"");
    }
    public void setTitle(String ttitle, String mmessage){
        title = ttitle;
        msg = mmessage;
        Packet.sendTitle(getOwner(),this.eid, title+ TextFormat.RED+"\n\n"+msg);
    }

    //update interval is 10 tick
    public void onUpdate(int tick){
        int diff = tick - lasttick;
        int ch = getHealth();
        int diff2 = ch - diff;
        if(diff2 < 0){//Kill
            kill();
            return;
        }
        setHealth(diff2);
        lasttick = tick;
    }

    public void kill(){
        Main.DelBBN(getOwner());
        Packet.removeBossBar(getOwner(),eid);
    }

    public void send(){
        if(! this.visible){
            return;
        }
        Packet.sendBossBar(getOwner(), this.eid, this.title);
        Packet.sendPercentage(getOwner(), this.eid, this.currentHealth / (double) this.maxHealth * 100);

    }

    public void sendTo(Player player){
        if(! this.visible){
            return;
        }
        Packet.sendBossBar(player, this.eid, this.title);
        Packet.sendPercentage(player, this.eid, this.currentHealth / (double) this.maxHealth * 100);
        return;
    }
}

