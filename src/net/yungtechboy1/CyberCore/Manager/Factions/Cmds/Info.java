package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Info extends Commands {

    public static final int RECRUIT = 1;
    public static final int MEMBER = 2;
    public static final int OFFICER = 3;
    public static final int GENERAL = 4;
    public static final int LEADER = 5;
    public Info(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f info <faction>", m);
        senderMustBeInFaction = true;
        senderMustBePlayer = true;
        sendUsageOnFail = true;
        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (Args.length >= 1) {
            if (!Args[0].matches("^[a-zA-Z0-9]*")) {
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

            StringBuilder sb = new StringBuilder();
            sb.append(formatString("Faction name",ffaction.GetDisplayName())).append("\n");
            sb.append(formatString("Leader",ffaction.GetLeader())).append("\n");
            sb.append(formatString("# of Players",ffaction.GetPlayerCount()+"")).append("\n");
            sb.append(formatString("Max # of Players",ffaction.GetMaxPlayers())).append("\n");
            sb.append(formatString("MOTD",ffaction.GetMOTD())).append("\n");
            sb.append(formatString("Desc",ffaction.GetDesc())).append("\n");
            sb.append(formatString("Power",ffaction.GetPower())).append("\n");
            sb.append(formatString("Land Owned",ffaction.GetPlots().size())).append("\n");
            sb.append(formatString("Money",ffaction.GetMoney())).append("\n");
            sb.append(formatString("XP",ffaction.GetXP())).append("\n");
            sb.append(formatString("Level",ffaction.GetLevel())).append("\n");

            FormWindowSimple FWM = new FormWindowSimple("CyberFactions | "+ffaction.GetDisplayName()+TextFormat.RESET+" Faction info",sb.toString());


            FWM.addButton(new ElementButton("View All Players"));
            FWM.addButton(new ElementButton("Invite to Ally"));
            FWM.addButton(new ElementButton("Add to Enemy"));
            FWM.addButton(new ElementButton("Message Faction"));
            FWM.addButton(new ElementButton("Report Faction"));

            CorePlayer cp = (CorePlayer) Sender;
            cp.showFormWindow(FWM);
            cp.LastSentFormType = FormType.MainForm.Faction_Info_Other;
        } else {
            if (fac == null) {
                if (Main.FFactory.getPlayerFaction(Sender) == null) {
                    Sender.sendMessage(TextFormat.RED + "[CyberTech] You are not in a Faction!");
                }
                Sender.sendMessage(TextFormat.RED + "[CyberTech] You are not in a Faction!");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(formatString("Faction name",fac.GetDisplayName())).append("\n");
            sb.append(formatString("Leader",fac.GetLeader())).append("\n");
            sb.append(formatString("# of Players",fac.GetPlayerCount()+"")).append("\n");
            sb.append(formatString("Max # of Players",fac.GetMaxPlayers())).append("\n");
            sb.append(formatString("MOTD",fac.GetMOTD())).append("\n");
            sb.append(formatString("Desc",fac.GetDesc())).append("\n");
            sb.append(formatString("Power",fac.GetPower())).append("\n");
            sb.append(formatString("Land Owned",fac.GetPlots().size())).append("\n");
            sb.append(formatString("Money",fac.GetMoney())).append("\n");
            sb.append(formatString("XP",fac.GetXP())).append("\n");
            sb.append(formatString("Level",fac.GetLevel())).append("\n");

            FormWindowSimple FWM = new FormWindowSimple("CyberFactions | "+fac.GetDisplayName()+TextFormat.RESET+" Faction info",sb.toString());

            FWM.addButton(new ElementButton("View All Players"));
//            FWM.addButton(new ElementButton("View All Plots Claimed"));

            CorePlayer cp = (CorePlayer) Sender;
            cp.showFormWindow(FWM);
            cp.LastSentFormType = FormType.MainForm.Faction_Info_Self;
        }
    }

    private String formatString(String keyname,Object val){
        return TextFormat.YELLOW+keyname+": " +TextFormat.AQUA+ val;
    }
}