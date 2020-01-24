package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionErrorString.Error_UnableToFindFaction;

/**
 * Created by carlt_000 on 12/10/2016.
 */
public class Enemy extends Commands {

    public Enemy(CorePlayer s, String[] a, FactionsMain m) {
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
                Sender.sendMessage(TextFormat.RED+"Error the faction containing '" + Args[1] + "' could not be found!");
                return;
            } else if (l.size() == 1) {
                target = l.get(0);
            } else {
                FormWindowSimple FWM = new FormWindowSimple("CyberFactions | Add Enemy Factino", "Mulitple factions were found with that name, please choose one.");
                int k = 0;
                FWM.addButton(new ElementButton("Grinch!"));
                for (Faction p : l) {
                    k++;
                    if (k > 20) break;
                    FWM.addButton(new ElementButton(p.getName()));
                }

                CorePlayer cp = (CorePlayer) CyberCoreMain.getInstance().getServer().getPlayerExact(Sender.getName());
                cp.showFormWindow(FWM);
                cp.LastSentFormType = FormType.MainForm.Faction_Enemy_Choose;
                return;
            }



        }

        fac.AddEnemy(target, Sender);
        target.BroadcastMessage(TextFormat.AQUA + "[ArchFactions] " + fac.getDisplayName() + " Has added you as an enemy!");
        Sender.sendMessage(TextFormat.AQUA + "[ArchFactions] " + target.getDisplayName() + " is now an enemy");
    }
}