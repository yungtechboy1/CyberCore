package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Sethome extends Commands {


    public Sethome(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f sethome <player>", m);
        senderMustBeInFaction = true;
        senderMustBeMember = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
//        FactionRank ar = fac.getPermSettings().getAllowedToSetHome();
//        FactionRank pr = fac.getPlayerRank(Sender);
//        if (!fac.GetLeader().equalsIgnoreCase(Sender.getName()) && !ar.hasPerm(pr)) {
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "You Must Be a Leader or "+ar.getName()+"!");
//            return;
//        }
//        Integer fp = fac.GetPower();
//        if (fp < 1) {
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "You Must Have 1 Faction PowerAbstract!");
//            return;
//        }
//        double x = ((Player) Sender).getX();
//        double z = ((Player) Sender).getZ();
//        //@todo Should they own the Chunk???
//        String co = Main.GetChunkOwner((int) x >> 4, (int) z >> 4);
//
//
//        if (co != null && co.equalsIgnoreCase(fac.getName())) {
//            fac.SetHome((Player) Sender);
//            fac.TakePower(1);
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Home updated!");
//        } else {
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "You must claim the land to set a home there!!!");
//            return;
//        }
    }
}