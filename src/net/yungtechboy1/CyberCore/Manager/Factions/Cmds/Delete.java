package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionConfirmDelete;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Delete extends Commands {

    public Delete(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f delete", m);
        senderMustBeInFaction = true;
        senderMustBeLeader = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (fac.Leader.equalsIgnoreCase(Sender.getName())) {
            Sender.showFormWindow(new FactionConfirmDelete());
        } else {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "You are not the leader!");
        }
    }
}