package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.InterruptibleThread;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

/**
 * Created by carlt_000 on 1/28/2017.
 */
public class SendHUD extends Thread implements InterruptibleThread {


    public SendHUD() {
    }

    public void run() {
        int lasttick = -1;
//        System.out.println("11111111111111111111");
        while (Server.getInstance().isRunning()) {
//System.out.println("======");
            int tick = Server.getInstance().getTick();
            if (tick >= lasttick + 10) {

//                System.out.println("||||||||======");
                lasttick = tick;
                CyberCoreMain Main = CyberCoreMain.getInstance();
                for (Player p : Main.getServer().getOnlinePlayers().values()) {
                    if(p.isAlive() && p.isOnline())
                    FormatHUD((CorePlayer) p);
                }
                try {
                    Thread.sleep(200);//4 Ticks
                } catch (InterruptedException e) {
                    //ignore
                }
            }
            try {
                Thread.sleep(200);//4 Ticks
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }


    public void CTstop() {
        if (isAlive()) interrupt();
    }

    private void FormatHUD(CorePlayer p) {
        if (p == null || p.settings.isHudOff()) return;
        CyberCoreMain Main = CyberCoreMain.getInstance();

        int px = p.getFloorX();
        int py = p.getFloorY();
        int pz = p.getFloorZ();
        String pnl = p.getDisplayName();
        Faction fac = null;
        if (Main.FM != null) {
            fac = Main.FM.FFactory.getPlayerFaction(p);
        }
        String fn = "No Faction";
        Integer flvl = 0;
        Integer fxp = 0;
        Integer fxpm = 0;
        if (fac != null) {
            fn = fac.GetDisplayName();
            flvl = fac.GetLevel();
            fxp = fac.GetXP();
            fxpm = fac.calculateRequireExperience(flvl);
        }

        String f = "";
        if (!p.settings.isHudPosOff())
            f += TextFormat.GRAY + " ----------- " + TextFormat.GREEN + "X: " + px + " Y: " + py + " Z:" + pz + TextFormat.GRAY + " ----------- " + TextFormat.RESET + "\n";
        if (!p.settings.isHudFactionOff())
            f += TextFormat.GRAY + " -- " + TextFormat.GRAY + "Faction : " + TextFormat.AQUA + fn + TextFormat.GRAY + " | " + TextFormat.GREEN + fxp + TextFormat.AQUA + " / " + TextFormat.GOLD + fxpm + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + flvl + TextFormat.GRAY + " -- " + TextFormat.RESET + "\n";
        if (!p.settings.isHudClassOff()) {
//            TODO
//              String pclass = "NONE";
//            BaseClass bc = getOwner().ClassFactory.GetClass(p);
//            int pxp = 0;
//            int pxpof = 0;
//            int plvl = 0;
//            if (bc != null) {
//                int lvl = bc.XPToLevel(bc.getXP());
//                pclass = bc.getName();
//                pxp = bc.XPRemainder(bc.getXP());
//                pxpof = bc.calculateRequireExperience(lvl + 1);
//                plvl = lvl;
//                f += TextFormat.GRAY + "Class : " + TextFormat.AQUA + pclass + TextFormat.GRAY + " | " + TextFormat.GREEN + pxp + TextFormat.AQUA + " / " + TextFormat.GOLD + pxpof + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + plvl;
//            }
        }
        p.sendTip(f);
    }
}
