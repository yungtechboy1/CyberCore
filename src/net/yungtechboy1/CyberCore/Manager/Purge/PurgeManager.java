package net.yungtechboy1.CyberCore.Manager.Purge;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 3/8/2019.
 */
public class PurgeManager extends Thread implements InterruptibleThread {

    public static boolean isActive() {
        return Active;
    }

    private static boolean Active = false;
    public CyberCoreMain CCM;
    public boolean FirstSleep = false;
    private PurgeStage State = PurgeStage.Off;

    public boolean isStoped() {
        return Stop;
    }

    public void setStop(boolean stop) {
        Stop = stop;
    }

    private boolean Stop = false;

    public PurgeManager(CyberCoreMain m) {
        CCM = m;
    }

    public void KillPurge() {
        if (isAlive()) interrupt();
    }

//    public static boolean CheckTick(int lasttick){
//        int tick = Server.getInstance().getTick();
//        if (tick != lasttick) {
//            lasttick = tick;
//    }

    public void run() {
        State = PurgeStage.First_Cooldown;
//        csleep(60 * 5);//Wait 5 Mins
        int lasttick = -1;
        while (Server.getInstance().isRunning()) {//Master Start
            while (!Stop) {//Check Sleep Setting / Not Sleeping
                int tick = Server.getInstance().getTick();
                if (tick != lasttick) {
                    lasttick = tick;
                    int ll = 0;
                    switch (State) {
                        case First_Cooldown:
                            int l1 = 0;
                            while (l1 <= 6 * 5) {//5 Mins
                                l1++;
                                if (Stop) break;//Just Checking Stop to Break wait time
                                csleep(10);
                            }
                            if (Stop) break;
                            State = PurgeStage.WaitingToStart;//Ready to start!
                            continue;
                        case WaitingToStart:
                            CCM.getServer().broadcastMessage(TextFormat.DARK_PURPLE + "------------------");
                            CCM.getServer().broadcastMessage(TextFormat.DARK_PURPLE + "-------PURGE------");
                            CCM.getServer().broadcastMessage(TextFormat.DARK_PURPLE + "------Warning-----");
                            CCM.getServer().broadcastMessage(TextFormat.DARK_PURPLE + "------------------");
                            CCM.getServer().broadcastMessage(TextFormat.DARK_PURPLE + "In 1 Min ALL PVP Will be enabled! ");
                            CCM.getServer().broadcastMessage(TextFormat.DARK_PURPLE + "Spawn PVP Protection will be removed");
                            CCM.getServer().broadcastMessage(TextFormat.DARK_PURPLE + "Good Luck, May The odds Be with you!");

                            int l2 = 0;
                            while (l2 <= 3) {//30 secs
                                l2++;
                                if (Stop) break;//Just Checking Stop to Break wait time
                                csleep(10);
                            }
                            if (Stop) break;
                            State = PurgeStage.W30;//W30!
                            continue;
                        case W30:
                            CCM.getServer().broadcastMessage(TextFormat.YELLOW + "[PURGE] ! 30 Sec Warning !");
                            int l3 = 0;
                            while (l3 <= 3) {//15 secs
                                l3++;
                                if (Stop) break;//Just Checking Stop to Break wait time
                                csleep(5);
                            }
                            if (Stop) break;
                            State = PurgeStage.W15;//W15!
                            continue;
                        case W15:
                            CCM.getServer().broadcastMessage(TextFormat.BLUE + "[PURGE] !! 15 Sec Warning !!");
                            csleep(5);
                            if (Stop) break;
                            State = PurgeStage.W10;//W10!
                            continue;
                        case W10:
                            CCM.getServer().broadcastMessage(TextFormat.DARK_PURPLE + "[PURGE] !!! 10 Sec Warning !!!");
                            csleep(5);
                            if (Stop) break;
                            State = PurgeStage.W5;//W5!
                            continue;
                        case W5:
                            CCM.getServer().broadcastMessage(TextFormat.RED + "[PURGE] !!!! 5 Sec Warning !!!!");
                            csleep(5);
                            State = PurgeStage.W4;//W4!
                            continue;
                        case W4:
                            CCM.getServer().broadcastMessage(TextFormat.RED + "[PURGE] !!!! 4 Sec Warning !!!!");
                            csleep(5);
                            if (Stop) break;
                            State = PurgeStage.W3;//W3!
                            continue;
                        case W3:
                            CCM.getServer().broadcastMessage(TextFormat.RED + "[PURGE] !!!! 3 Sec Warning !!!!");
                            csleep(5);
                            if (Stop) break;
                            State = PurgeStage.W2;//W2!
                            continue;
                        case W2:
                            CCM.getServer().broadcastMessage(TextFormat.RED + "[PURGE] !!!! 2 Sec Warning !!!!");
                            csleep(5);
                            if (Stop) break;
                            State = PurgeStage.W1;//W1!
                            continue;
                        case W1:
                            CCM.getServer().broadcastMessage(TextFormat.RED + "[PURGE] !!!! 1 Sec Warning !!!!");
                            csleep(5);
                            if (Stop) break;
                            State = PurgeStage.On;//On!
                            continue;
                        case On:
                            Active = true;
                            CCM.getServer().broadcastMessage(TextFormat.RED + "====== PURGE ACTIVE ======");
                            ll = 0;
                            while (ll <= (60/5)) {//15 secs
                                ll++;
                                if (Stop) break;//Just Checking Stop to Break wait time
                                csleep(5);
                            }
                            if (Stop) break;
                            State = PurgeStage.Cooldown;//W10!
                            continue;
                        case Cooldown:
                            Active = false;
                            CCM.getServer().broadcastMessage(TextFormat.AQUA + "====== PURGE HAS ENDED ======");
                            ll = 0;
                            while (ll <= (60/5)) {//5 minssecs
                                ll++;
                                if (Stop) break;//Just Checking Stop to Break wait time
                                csleep(5);
                                if(State == PurgeStage.WaitingToStart)break;
                            }
                            if (Stop) break;
                            State = PurgeStage.WaitingToStart;//W10!
                            continue;


                    }


//Start Warning


                }
                csleep(1);
            }
            State = PurgeStage.Off;
            csleep(30);
        }
    }

    public void csleep(int a) {
        try {
            Thread.sleep(a * 100);//1 Sec * a
        } catch (InterruptedException e) {
            //ignore
        }
    }

    private enum PurgeStage {
        Off,
        Stopped,
        W60,
        W30,
        W15,
        W10,
        W5,
        W4,
        W3,
        W2,
        W1,
        On,
        Cooldown,
        First_Cooldown,
        WaitingToStart
    }
}

