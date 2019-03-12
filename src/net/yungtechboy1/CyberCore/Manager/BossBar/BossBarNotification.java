package net.yungtechboy1.CyberCore.Manager.BossBar;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

public class BossBarNotification extends BossBarGeneric{

    public CyberCoreMain Main;

    public BossBarNotification(Player owner, String ttitle, String mmsg, CyberCoreMain main){
        this(owner, ttitle, mmsg, 20*6, main);
    }

    public BossBarNotification(Player owner, String ttitle, String mmsg, int tticks, CyberCoreMain main){
        super(main.BBM,owner,ttitle,mmsg);
        Main = main;
        MaxHealth = tticks;
        CurrentHealth = tticks;
    }

    public void setTitle(String ttitle){
        setTitle(ttitle,"");
    }
    public void setTitle(String ttitle, String mmessage){
        Title = ttitle;
        Msg = mmessage;
        updateBossEntityNameTag();
    }

    //update interval is 10 tick
    @Override
    public void onUpdate(int tick) {
        //TODO
    }

}
