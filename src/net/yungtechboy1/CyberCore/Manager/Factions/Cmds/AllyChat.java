package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FactionSettings;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class AllyChat extends Commands {

    public AllyChat(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f allychat [Text]", m);
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
        CorePlayer p = (CorePlayer) Sender;
        if (p == null) return;

        String chat = "";
        int a = 0;
        if (Args.length == 2) {
            String name = Sender.getName().toLowerCase();
            if (Args[1].equalsIgnoreCase("on")) {
                p.fsettings.setChatSelection(FactionSettings.ChatSetting.Ally);
                Sender.sendMessage(TextFormat.GREEN + "Ally Chat Activated!");
                return;
            } else if (Args[1].equalsIgnoreCase("off")) {
                p.fsettings.setChatSelection(FactionSettings.ChatSetting.All);
                Sender.sendMessage(TextFormat.RED + "Ally Chat Removed!");
                return;
            }
        }
        for (String c : Args) {
            a++;
            if (a == 1) continue;
            chat += c + " ";
        }
        String n = Sender.getName();
        if (fac.Leader.equalsIgnoreCase(Sender.getName())) {
            fac.MessageAllys(TextFormat.YELLOW + "~***[" + n + "]***~: " + chat);
        } else if (fac.IsGeneral(Sender.getName())) {
            fac.MessageAllys(TextFormat.YELLOW + "~**[" + n + "]**~: " + chat);
        } else if (fac.IsOfficer(Sender.getName())) {
            fac.MessageAllys(TextFormat.YELLOW + "~*[" + n + "]*~: " + chat);
        } else if (fac.IsMember(Sender.getName())) {
            fac.MessageAllys(TextFormat.YELLOW + "~[" + n + "]~: " + chat);
        } else {
            fac.MessageAllys(TextFormat.YELLOW + "-[" + n + "]-: " + chat);
        }
    }
}
