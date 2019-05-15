package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

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
            CorePlayer p = (CorePlayer)Sender;

            FormWindowModal FWM = new FormWindowModal("CyberFactions | Faction Delete Confirmation", TextFormat.RED+""+TextFormat.BOLD+"WARNING!!!!\n Are you sure you want to delete your faction?","Confirm and Delete", "Cancel");
            p.showFormWindow(FWM);
            p.LastSentFormType = FormType.MainForm.Faction_Delete_Confirm;
        } else {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "You are not the leader!");
        }
    }
}