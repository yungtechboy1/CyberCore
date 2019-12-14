package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

/**
 * Created by carlt_000 on 1/28/2017.
 */
public class SendHUD extends Thread implements InterruptibleThread {


    public SendHUD() {
        start();
    }

    public void run() {
        int lasttick = -1;
//        System.out.println("11111111111111111111");
        while (Server.getInstance().isRunning()) {
//System.out.println("======");
            int tick = Server.getInstance().getTick();
            if (tick >= lasttick + 20) {

//                System.out.println("||||||||======");
                lasttick = tick;
                CyberCoreMain Main = CyberCoreMain.getInstance();
                for (Player p : Main.getServer().getOnlinePlayers().values()) {
                    if (p.isAlive() && p.isOnline())
                        FormatHUD((CorePlayer) p);
                }
                try {
                    Thread.sleep(750);//17.5 Ticks
                } catch (InterruptedException e) {
                    //ignore
                }
            }
            try {
                Thread.sleep(750);//17.5 Ticks
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }


    public void CTstop() {
        if (isAlive()) interrupt();
    }

    private void FormatHUD(CorePlayer p) {
        if (p == null || p.InternalPlayerSettings.isHudOff()) return;
        CyberCoreMain Main = CyberCoreMain.getInstance();

        int px = p.getFloorX();
        int py = p.getFloorY();
        int pz = p.getFloorZ();
        String pnl = p.getDisplayName();
        Faction fac = null;
        if (Main.FM != null) {
            fac = Main.FM.FFactory.getFaction(p.Faction);
        }
        String fn = "No Faction";
        Integer flvl = 0;
        Integer fxp = 0;
        Integer fxpm = 0;
        if (fac != null) {
            fn = fac.getDisplayName();
            flvl = fac.GetLevel();
            fxp = fac.GetXP();
            fxpm = fac.calculateRequireExperience(flvl);
        }

        String f = "";
        f += TextFormat.GRAY + " ----------- " + TextFormat.GREEN + "Hunger " + p.getFoodData().getLevel() + " HP: " + p.getHealth() + " MX/HP:" + p.getMaxHealth() + TextFormat.GRAY + " ----------- " + TextFormat.RESET + "\n";
        if (!p.InternalPlayerSettings.isHudPosOff())
            f += TextFormat.GRAY + " ----------- " + TextFormat.GREEN + "X: " + px + " Y: " + py + " Z:" + pz + TextFormat.GRAY + " ----------- " + TextFormat.RESET + "\n";
        if (!p.InternalPlayerSettings.isHudFactionOff())
            f += TextFormat.GRAY + " -- " + TextFormat.GRAY + "Faction : " + TextFormat.AQUA + fn + TextFormat.GRAY + " | " + TextFormat.GREEN + fxp + TextFormat.AQUA + " / " + TextFormat.GOLD + fxpm + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + flvl + TextFormat.GRAY + " -- " + TextFormat.RESET + "\n";
        if (!p.InternalPlayerSettings.isHudClassOff()) {
//            TODO
            if (p.getPlayerClass() != null) {
                String pclass = "NONE";
                BaseClass bc = p.getPlayerClass();
                //BUG TODO ERROR k
//                String t = bc.FormatHudText();
//                if (t != null && t.length() != 0) f += t;
            }
        }
        p.sendTip(f);
        //HACK TO SEND CORRECT HEALTH TO CLIENT
        if(p.getHealth() >= 20)p.sendHealth();
    }
}
