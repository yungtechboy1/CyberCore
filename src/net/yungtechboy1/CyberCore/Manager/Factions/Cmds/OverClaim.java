package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */

@Deprecated
public class OverClaim extends Commands {

    public OverClaim(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f overclaim [radius = 1]", m);
        senderMustBeInFaction = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        Integer Radius = GetIntegerAtArgs(1, 1);
        if (Radius > 1) {
            Integer rr = Radius * Radius;
            Integer money = (5000 * rr);
            Integer power = (3 * rr);
            if (fac.getSettings().getMoney() > money) {
                Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Your Faction does not have $" + money + " in your faction account!");
                return;
            }
            if (fac.getSettings().getPower() < power) {
                Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Your Faction does not have "+power+" power!");
                return;
            }
            for (int x = Math.negateExact(Radius); x < Radius; x++) {
                for (int z = Math.negateExact(Radius); z < Radius; z++) {
                    int xx = ((int) ((Player) Sender).getX() >> 4) + x;
                    int zz = ((int) ((Player) Sender).getZ() >> 4) + z;
                    OverClaimLand(xx, zz);
                }
            }
        } else {
            int x = (int) ((Player) Sender).getX() >> 4;
            int z = (int) ((Player) Sender).getZ() >> 4;
            OverClaimLand(x,z);
        }

    }

    private void OverClaimLand(Integer x, Integer z) {
        int money = 5000;
        int power = 3;
        if (fac.getSettings().getMoney() < money) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Your Faction does not have $" + money + " in your faction account to claim Chunk at X:" + x + " Z:" + z + "!");
            return;
        }
        if (fac.getSettings().getPower() < power) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Your Faction does not have " + power + " PowerAbstract to claim Chunk at X:" + x + " Z:" + z + "!");
            return;
        }
        if (!Main.FFactory.PM.isPlotClaimed(x , z)) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.YELLOW + "That Chunk at X:" + x + " Z:" + z + " is not Claimed by a faction and is being skipped!");
            return;
        }
        Faction fac2 = Main.FFactory.getFaction(Main.FFactory.PM.getFactionFromPlot(x , z));
        if (fac.getName().equalsIgnoreCase("peace")) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error! You can not overclaim peace!");
            return;
        }
        if (fac2.getSettings().getPower() < fac2.GetPlots().toArray().length) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Plot Overclaim Successful! $5000 and 3 PowerAbstract to over ClaimChunk at X:" + x + " Z:" + z + "!");
            fac.getSettings().takeMoney(money);
            fac.AddPlots(x, z, Sender);
            fac2.DelPlots(x,z,Sender,true);
            fac.TakePower(power);
//            Main.FFactory.PlotsList.remove(x + "|" + z);
//            Main.FFactory.PlotsList.put(x + "|" + z, fac.getName());
        }else{

        }
    }
}
