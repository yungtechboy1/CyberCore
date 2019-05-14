package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Error_UnableToFindFaction;
import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Error_UnableToFindPlayer;

/**
 * Created by carlt_000 on 12/10/2016.
 */
public class Enemy extends Commands {

    public Enemy(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f enemy <fac>", m);
        senderMustBeInFaction = true;
        senderMustBeLeader = true;
        senderMustBeGeneral = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (Args.length <= 1) {
            Sender.sendMessage(TextFormat.RED + "[ArchFactions] Usage: /f enemy <fac>");
            return;
        }

        Faction target = Main.FFactory.getFaction(Args[1]);
        if (target == null) {
            //Partial Name Search
            ArrayList<Faction> l = CyberCoreMain.getInstance().getAllFactionNamesCloseTo(Args[1]);
            if (l.size() == 0) {
                Sender.sendMessage(Error_UnableToFindFaction.getMsg());
                return;
            } else if (l.size() == 1) {
                target = l.get(0);
            } else {
                FormWindowSimple FWM = new FormWindowSimple("CyberFactions | Invite Player", "");
                int k = 0;
                FWM.addButton(new ElementButton("Grinch!"));
                for (Faction p : l) {
                    k++;
                    if (k > 20) break;
                    FWM.addButton(new ElementButton(p.GetName()));
                }

                CorePlayer cp = (CorePlayer) CyberCoreMain.getInstance().getServer().getPlayerExact(Sender.getName());
                cp.showFormWindow(FWM);
                cp.LastSentFormType = FormType.MainForm.Faction_Enemy_Choose;
                return;
            }


            Sender.sendMessage(TextFormat.RED+"Error the faction containing '" + Args[1] + "' could not be found!");
            return;
        }

        fac.AddEnemy(target.GetName());
        target.BroadcastMessage(TextFormat.AQUA+"[ArchFactions] "+fac.GetDisplayName()+" Has added you as an enemy!");
        Sender.sendMessage(TextFormat.AQUA+"[ArchFactions] "+target.GetDisplayName()+" is now an enemy");
    }
}