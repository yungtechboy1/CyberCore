package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionFactory;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Balance extends Commands {

    public Balance(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f balance [fac]", m);
        senderMustBePlayer = true;
        senderMustBeInFaction = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (Args.length >= 1) {
            String f = Args[1];
            FactionFactory cff = Main.FFactory;
            Faction ff = cff.getFaction(cff.factionPartialName(f));
            if (ff == null) {
                ff = Main.FFactory.getFaction(Main.FFactory.factionPartialName(Args[1]));
                if (ff == null) {
                    Sender.sendMessage(TextFormat.RED + "Error the faction containing '" + Args[1] + "' could not be found!");
                    return;
                }
                Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Faction not found!");
                return;
            }
            Integer money = ff.GetMoney();
            Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + ff.GetDisplayName() + " Faction has " + TextFormat.AQUA + money);
            fac.UpdateTopResults();
        } else {
            Integer money = fac.GetMoney();
            Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Your Faction has " + TextFormat.AQUA + money);
            fac.UpdateTopResults();
        }
    }
}
