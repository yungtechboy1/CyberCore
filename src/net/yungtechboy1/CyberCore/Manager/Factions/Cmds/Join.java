package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Join extends Commands {

    public Join(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f join <faction>", m);
        senderMustBePlayer = true;
        senderMustBeMember = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    //@TODO
    public void RunCommand() {
        if (Args.length < 1) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.GRAY + "Usage /f join or /f join <faction>");
            return;
        }
        if (Args.length == 1) {
            ArrayList<Faction> af = Main.FFactory.GetAllOpenFactions();
            FormWindowSimple FWM = new FormWindowSimple("CyberFactions | Joining an Open Faction", "");
            if (af.size() == 0) {
                FWM.addButton(new ElementButton("--No Public Faction--"));
            } else {
                int i = 0;
                for(Faction f: af){
                    i++;
                    if(i > 20)continue;
                    FWM.addButton(new ElementButton(f.GetDisplayName()));
                }
//            FWM.addButton(new ElementButton("View All Plots Claimed"));
            }
            CorePlayer cp = (CorePlayer) Sender;
            cp.showFormWindow(FWM);
            cp.LastSentFormType = FormType.MainForm.Faction_Join_List;
        } else if (Args.length == 2) {
            String fn = Main.FFactory.factionPartialName(Args[1]);
            Faction f = Main.FFactory.getFaction(fn);
            if (f == null) {
                Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Faction not Found!");
                return;
            }
            if (f.GetPrivacy() == 1) {
                Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Error! You can not join a private Faction!");
                return;
            }
            f.AddRecruit(Sender.getName().toLowerCase());
            Main.FFactory.FacList.put(Sender.getName().toLowerCase(), fn);
            CorePlayer cp = (CorePlayer) Sender;
            cp.Faction = fn;

            Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Congrads! You Just Joined " + fac.GetDisplayName() + " Faction!");
            f.BroadcastMessage(FactionsMain.NAME + TextFormat.GREEN + Sender.getName() + " Has joined your faction!");
//        if(Main.CC != null)Main.CC.Setnametag((Player)Sender);
        }
    }
}
