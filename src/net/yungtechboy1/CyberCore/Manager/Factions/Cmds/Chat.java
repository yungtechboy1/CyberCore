package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FactionSettings;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Chat extends Commands {

    public Chat(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f chat [Text]", m);
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
            if (Args[1].equalsIgnoreCase("fac") || Args[1].equalsIgnoreCase("on")) {
                p.fsettings.setChatSelection(FactionSettings.ChatSetting.Fac);
                Sender.sendMessage(TextFormat.GREEN + "Faction Chat Activated!");
                return;
            } else if (Args[1].equalsIgnoreCase("ally")) {

                p.fsettings.setChatSelection(FactionSettings.ChatSetting.All);
                Sender.sendMessage(TextFormat.GREEN + "Ally Chat Activated!");
            } else if (Args[1].equalsIgnoreCase("off")) {
                p.fsettings.setChatSelection(FactionSettings.ChatSetting.All);
                Sender.sendMessage(TextFormat.RED + "Faction / Ally Chat Disabled!");
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
            fac.BroadcastMessage(TextFormat.YELLOW + "~***[" + n + "]***~: " + chat);
        } else if (fac.IsGeneral(Sender.getName())) {
            fac.BroadcastMessage(TextFormat.YELLOW + "~**[" + n + "]**~: " + chat);
        } else if (fac.IsOfficer(Sender.getName())) {
            fac.BroadcastMessage(TextFormat.YELLOW + "~*[" + n + "]*~: " + chat);
        } else if (fac.IsMember(Sender.getName())) {
            fac.BroadcastMessage(TextFormat.YELLOW + "~[" + n + "]~: " + chat);
        } else {
            fac.BroadcastMessage(TextFormat.YELLOW + "-[" + n + "]-: " + chat);
        }
    }
}
