package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Info extends Commands {

    public static final int RECRUIT = 1;
    public static final int MEMBER = 2;
    public static final int OFFICER = 3;
    public static final int GENERAL = 4;
    public static final int LEADER = 5;
    public Info(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f info <player>", m);
        senderMustBeInFaction = true;
        senderMustBePlayer = true;
        sendUsageOnFail = true;
        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (Args.length > 1) {
            if (!Args[1].matches("^[a-zA-Z0-9]*")) {
                Sender.sendMessage(TextFormat.RED + "Invalid Faction Name");
                return;
            }
            Faction ffaction = Main.FFactory.getFaction(Args[1]);
            if (ffaction == null) {
                Faction lc = Main.FFactory.getFaction(Main.FFactory.factionPartialName(Args[1]));
                if (lc == null) {
                    Sender.sendMessage(TextFormat.RED + "Faction does not exist");
                    return;
                } else {
                    ffaction = lc;
                }
            }
            String dn = ffaction.GetDisplayName();
            Sender.sendMessage("-------------------------");
            String faa = "";
            Sender.sendMessage(dn);
            Sender.sendMessage(TextFormat.YELLOW+"Leader: " +TextFormat.AQUA+ ffaction.GetLeader());
            Sender.sendMessage(TextFormat.YELLOW+"# of Players: " +TextFormat.AQUA+ Main.getNumberOfPlayers(ffaction.GetName()));
            int max = ffaction.GetMaxPlayers();
            Sender.sendMessage(TextFormat.YELLOW+"Max # of Players: " +TextFormat.AQUA+ max);
            Sender.sendMessage(TextFormat.YELLOW+"MOTD: " +TextFormat.AQUA+ ffaction.GetMOTD());
            Sender.sendMessage(TextFormat.YELLOW+"Desc: " +TextFormat.AQUA+ ffaction.GetDesc());
            Sender.sendMessage(TextFormat.YELLOW+"Power: " +TextFormat.AQUA+ ffaction.GetPower());
            Sender.sendMessage("-------------------------");
        } else {
            if (fac == null) {
                if (Main.FFactory.getPlayerFaction(Sender) == null) {
                    Sender.sendMessage(TextFormat.RED + "[CyberTech] You are not in a Faction!");
                }
                Sender.sendMessage(TextFormat.RED + "[CyberTech] You are not in a Faction!");
                return;
            }
            String dn = fac.GetDisplayName();
            Sender.sendMessage("-------------------------");
            String faa = "";
            Sender.sendMessage(TextFormat.YELLOW+"Faction name: "+TextFormat.AQUA+dn);
            Sender.sendMessage(TextFormat.YELLOW+"Leader: " +TextFormat.AQUA+ fac.GetLeader());
            Sender.sendMessage(TextFormat.YELLOW+"# of Players: " +TextFormat.AQUA+ Main.getNumberOfPlayers(fac.GetName()));
            int max = fac.GetMaxPlayers();
            Sender.sendMessage(TextFormat.YELLOW+"Max # of Players: " +TextFormat.AQUA+ max);
            Sender.sendMessage(TextFormat.YELLOW+"MOTD: " +TextFormat.AQUA+ fac.GetMOTD());
            Sender.sendMessage(TextFormat.YELLOW+"Desc: " +TextFormat.AQUA+ fac.GetDesc());
            Sender.sendMessage(TextFormat.YELLOW+"Power: " +TextFormat.AQUA+ fac.GetPower()+" of "+fac.CalculateMaxPower());
            Sender.sendMessage(TextFormat.YELLOW+"Land Owned: " +TextFormat.AQUA+ fac.GetPlots().size());
            Sender.sendMessage(TextFormat.YELLOW+"Money: " +TextFormat.AQUA+ fac.GetMoney());
            Sender.sendMessage(TextFormat.YELLOW+"Money: " +TextFormat.AQUA+ fac.GetMoney());
            Sender.sendMessage(TextFormat.YELLOW+"XP: " +TextFormat.AQUA+ fac.GetXP()+" / "+fac.calculateRequireExperience(fac.GetLevel()));
            Sender.sendMessage(TextFormat.YELLOW+"Level: " +TextFormat.AQUA+ fac.GetLevel());
            Sender.sendMessage("-------------------------");
        }
    }
}