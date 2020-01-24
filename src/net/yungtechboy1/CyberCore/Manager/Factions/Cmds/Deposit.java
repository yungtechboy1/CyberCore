package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;


import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Deposit extends Commands {

    public Deposit(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f deposit <amount>", m);
        senderMustBePlayer = true;
        senderMustBeMember = true;
        senderMustBeInFaction = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (Args.length < 2) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.GRAY + "Usage /f deposit <amount>");
            return;
        }
        Integer money = Math.abs(Integer.parseInt(Args[1]));
        if (money == null || money == 0) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.GRAY + "Usage /f deposit <amount>");
            return;
        }
        if (!((CorePlayer) Sender).MakeTransaction(money)) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "You don't have " + money + " Money!");
            return;
        }
        fac.getSettings().addMoney(money);
        Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "$" + money + " Money Added to your Faction!");
        fac.BroadcastMessage(FactionsMain.NAME + TextFormat.GREEN + Sender.getName() + " has deposited $" + money + " Money to the faction account!");
    }
}
