package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.*;
import cn.nukkit.form.response.FormResponseCustom;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionString;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Error_SA223;

public class FactionCreate0  extends CyberFormCustom {
    public FactionCreate0() {
        super(FormType.MainForm.Faction_Create_0,"CyberFactions | Create Faction (1/2)");
        addElement(new ElementInput("Desired Faction Name"));
        addElement(new ElementInput("MOTD", "A CyberTech Faction"));
        addElement(new ElementLabel("Enabeling Faction Privacy will require a player to have an invite to join your faction."));
        addElement(new ElementToggle("Faction Privacy", false));
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
//        new FormWindowModal("CyberFactions | Create Faction (2/2)!", "Faction Created!", "OK", "OK");
        FormResponseCustom frc = getResponse();
        String fn = frc.getInputResponse(0);
        if (fn == null || fn.length() == 0) return false;
        System.out.println("PRINGING THE NAME " + fn);
        int r = _plugin.FM.FFactory.CheckFactionName(fn);
        if (r != 0) {
            FactionString fs = FactionsMain.getInstance().TextList.getOrDefault(r, null);
            cp.showFormWindow(new FactionCreate0Error(fs));
            return false;
        }
        String  motd = frc.getInputResponse(1);
        boolean privacy = frc.getToggleResponse(3);

        Faction f = _plugin.FM.FFactory.CreateFaction(fn, cp, motd, privacy);
        if (f == null) cp.sendMessage(Error_SA223.getMsg()+"!!!!!++11<<");

        return privacy;
    }
}