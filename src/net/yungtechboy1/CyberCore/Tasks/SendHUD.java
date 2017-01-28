package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/28/2017.
 */
public class SendHUD extends PluginTask<CyberCoreMain> {
    public SendHUD(CyberCoreMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        for (Player p : getOwner().getServer().getOnlinePlayers().values()) {
            FormatHUD(p);
        }
    }

    private void FormatHUD(Player p) {
        if (getOwner().HudOff.contains(p.getName().toLowerCase())) return;
        int px = p.getFloorX();
        int py = p.getFloorY();
        int pz = p.getFloorZ();
        String f = TextFormat.GRAY + " ----------- " + TextFormat.GREEN + "X: " + px + " Y: " + py + " Z:" + pz + TextFormat.GRAY + " ----------- ";
        if (getOwner().HudClassOnly.contains(p.getName().toLowerCase())) f = "";
        String pclass = "NONE";
        BaseClass bc = getOwner().ClassFactory.GetClass(p);
        int pxp = 0;
        int pxpof = 0;
        int plvl = 0;
        if (bc != null) {
            int lvl = bc.XPToLevel(bc.getXP());
            pclass = bc.getName();
            pxp = bc.XPRemainder(bc.getXP());
            pxpof = bc.calculateRequireExperience(lvl+1);
            plvl = lvl;
            f += TextFormat.RESET + "\n" + TextFormat.GRAY + "Class : " + TextFormat.AQUA + pclass + TextFormat.GRAY + " | " + TextFormat.GREEN + pxpof + TextFormat.AQUA + " / " + TextFormat.GOLD + pxp + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + plvl;
        }
        p.sendTip(f);
    }
}
