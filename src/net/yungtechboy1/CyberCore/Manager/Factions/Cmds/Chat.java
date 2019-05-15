package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FactionSettings;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Chat extends Commands {

    public Chat(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f chat [Text]", m);
        senderMustBeInFaction = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }


    ///f chat - Brings up Chat window

    @Override
    public void RunCommand() {
        CorePlayer p = (CorePlayer) Sender;
        if (p == null) return;

        StringBuilder chat = new StringBuilder();
        int a = 0;
        if (Args.length == 1) {
            FormWindowSimple FWM = new FormWindowSimple("CyberFactions | Faction/Ally Chat Window", "");
            int k = 0;
            FWM.addButton(new ElementButton("Open Faction Chat Window"));
            FWM.addButton(new ElementButton("Open Ally Chat Window"));
            p.showFormWindow(FWM);
            p.LastSentFormType = FormType.MainForm.Faction_Chat_Choose;
        }
        for (String c : Args) {
            a++;
            if (a == 1) continue;
            chat.append(c).append(" ");
        }
        String n = Sender.getName();
        fac.AddFactionChatMessage(chat.toString(),p);
    }
}
