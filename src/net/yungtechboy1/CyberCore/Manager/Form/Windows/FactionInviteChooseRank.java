package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;

public class FactionInviteChooseRank extends CyberFormSimple {
    CorePlayer _t;
    public FactionInviteChooseRank(CorePlayer inviter, CorePlayer target) {
        super(FormType.MainForm.Faction_Invite_Choose_Rank, "CyberFactions | Invite Player | Choose Rank", "", new ArrayList<>());
        _t = target;
        setContent("Now please choose what rank you would like to invite " + target.getName() + " to your faction as:");
        addButton(new ElementButton("Recruit"));
        if (inviter.getFaction().getPlayerRank(inviter).hasPerm(FactionRank.Member))
            addButton(new ElementButton("Member"));

        if (inviter.getFaction().getPlayerRank(inviter).hasPerm(FactionRank.Officer))
            addButton(new ElementButton("Officer"));

        if (inviter.getFaction().getPlayerRank(inviter).hasPerm(FactionRank.General))
            addButton(new ElementButton("General"));

    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fic = getResponse();
        int k = fic.getClickedButtonId();
        FactionRank pr = cp.getFaction().getPlayerRank(cp);
        FactionRank fr = FactionRank.Recruit;
        if (k == 1) {
            if (pr.hasPerm(FactionRank.Member)) fr = FactionRank.Member;
            else
                cp.sendMessage("Error! You can not invite player as a Member rank! Invite will be send as a Recruit instead.");
        } else if (k == 2) {
            if (pr.hasPerm(FactionRank.Officer)) fr = FactionRank.Officer;
            else
                cp.sendMessage("Error! You can not invite player as a Officer rank! Invite will be send as a Recruit instead.");
        } else if (k == 3) {
            if (pr.hasPerm(FactionRank.General)) fr = FactionRank.General;
            else
                cp.sendMessage("Error! You can not invite player as a General rank! Invite will be send as a Recruit instead.");
        }


        FactionsMain.getInstance().PlayerInvitedToFaction(_t, cp, _Fac, fr);

//            _Fac.AddInvite(cpp.getName().toLowerCase(), time);
//            CyberCoreMain.getInstance().FM.FFactory.InvList.put(cpp.getName().toLowerCase(), _Fac.getName());

//            cp.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Successfully invited " + cpp.getName() + "!");
//            cpp.sendMessage(FactionsMain.NAME + TextFormat.YELLOW + "You have been invited to faction.\n" + TextFormat.GREEN + "Type '/f accept' or '/f deny' into chat to accept or deny!");
        return true;

    }
}
