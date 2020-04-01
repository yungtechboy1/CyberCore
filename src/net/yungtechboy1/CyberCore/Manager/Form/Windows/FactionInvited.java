package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.response.FormResponseModal;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;

public class FactionInvited extends CyberFormModal {
    public FactionInvited(String in, String fn) {
        super(FormType.MainForm.Faction_Invited, "CyberFactions | Faction Invite",
                "Greetings " + in + "!\n The faction " + fn + " would like to recruit you!",
                "Accept Faction Invite", "Deny Faction Invite");
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frsi =  getResponse();
        if (cp.FactionInvite != null) {
            Faction f = _Fac;
            if (frsi.getClickedButtonId() == 0) {
                //Accept
                if (f.AcceptInvite(cp))
                    cp.sendMessage("Welcome to " + f.getDisplayName());
                else
                    cp.sendMessage("Error! Invite timed out!");
            } else if (frsi.getClickedButtonId() == 1) {
                //Ignore / deny
                cp.ClearFactionInvite(true);
            } else {
                CyberCoreMain.getInstance().getLogger().error("UNKNOWN CLICKED BUTTON");
            }
        }
        cp.ClearFactionInvite();
        return false;
    }
}