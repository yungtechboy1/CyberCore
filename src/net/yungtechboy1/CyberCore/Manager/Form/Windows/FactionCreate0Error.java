package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowModal;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionString;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import static net.yungtechboy1.CyberCore.FormType.MainForm.Faction_Create_0_Error;
import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Error_SA221;
import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Error_SA223;

public class FactionCreate0Error extends CyberFormCustom {
    public FactionCreate0Error(FactionString fs) {
        super(Faction_Create_0_Error, "CyberFactions | Create Faction (1/2)");
        if (fs == null) fs = Error_SA221;
        addElement(new ElementInput("Desired Faction Name"));
        addElement(new ElementLabel(fs.getMsg()));
        addElement(new ElementInput("MOTD", "A CyberTech Faction"));
        addElement(new ElementLabel("Enabeling Faction Privacy will require a player to have an invite to join your faction."));
        addElement(new ElementToggle("Faction Privacy", false));
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        new FormWindowModal("CyberFactions | Create Faction (2/2)!", "Faction Created!", "OK", "OK");
        FormResponseCustom frc = (FormResponseCustom) getResponse();
        String fn = frc.getInputResponse(1);

        if (fn == null || fn.length() == 0) return false;
        System.out.println("PRINGING THE NAME " + fn);
        int r = _plugin.FM.FFactory.CheckFactionName(fn);
        if (r != 0) {
            FactionString fs = FactionsMain.getInstance().TextList.getOrDefault(r, null);
            cp.showFormWindow(new FactionCreate0Error(fs));
            return false;
        }
        String motd = frc.getInputResponse(2);
        boolean privacy = frc.getToggleResponse(4);

        Faction f = _plugin.FM.FFactory.CreateFaction(fn, cp, motd, privacy);
        if (f == null) {
            cp.sendMessage(Error_SA223.getMsg());
        }
        return privacy;
    }
}