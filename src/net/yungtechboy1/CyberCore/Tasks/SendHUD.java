package net.yungtechboy1.CyberCore.Tasks;

import net.yungtechboy1.CyberCore.Classes.Old.BaseClass;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 1/28/2017.
 */
public class SendHUD extends PluginTask<CyberCoreMain> {
    public ArrayList<String> HUDClassOff = new ArrayList<>();
    public ArrayList<String> HUDFactionOff = new ArrayList<>();
    public ArrayList<String> HUDPosOff = new ArrayList<>();

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
        String pnl = p.getName().toLowerCase();
        Faction fac = null;
        if(getOwner().FM != null) {
            fac = getOwner().FM.FFactory.getPlayerFaction(p);
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
        if (!getOwner().HUDPosOff.contains(pnl))
            f += TextFormat.GRAY + " ----------- " + TextFormat.GREEN + "X: " + px + " Y: " + py + " Z:" + pz + TextFormat.GRAY + " ----------- " + TextFormat.RESET + "\n";
        if (!getOwner().HUDFactionOff.contains(pnl))
            f += TextFormat.GRAY + " -- " + TextFormat.GRAY + "Faction : " + TextFormat.AQUA + fn + TextFormat.GRAY + " | " + TextFormat.GREEN + fxp + TextFormat.AQUA+" / " + TextFormat.GOLD+ fxpm + TextFormat.GRAY + " | "+ TextFormat.GREEN+"Level: "+TextFormat.YELLOW + flvl + TextFormat.GRAY + " -- " + TextFormat.RESET + "\n";
        if (!getOwner().HUDClassOff.contains(pnl)) {
            String pclass = "NONE";
            BaseClass bc = getOwner().ClassFactory.GetClass(p);
            int pxp = 0;
            int pxpof = 0;
            int plvl = 0;
            if (bc != null) {
                int lvl = bc.XPToLevel(bc.getXP());
                pclass = bc.getName();
                pxp = bc.XPRemainder(bc.getXP());
                pxpof = bc.calculateRequireExperience(lvl + 1);
                plvl = lvl;
                f += TextFormat.GRAY + "Class : " + TextFormat.AQUA + pclass + TextFormat.GRAY + " | " + TextFormat.GREEN + pxp + TextFormat.AQUA + " / " + TextFormat.GOLD + pxpof + TextFormat.GRAY + " | " + TextFormat.GREEN + "Level: " + TextFormat.YELLOW + plvl;
            }
        }
        p.sendTip(f);
    }
}
