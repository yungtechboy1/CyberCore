package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseCustom;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberTexts;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionErrorString;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionErrorString.Error_SA223;

public class FactionCreate0  extends CyberFormCustom {
    public FactionCreate0() {
        super(FormType.MainForm.Faction_Create_0,"CyberFactions | Create Faction (1/2)");
        addElement(new ElementInput("Faction Name"));
//        addElement(new ElementInput("Faction Description", FactionErrorString.Default_Faction_Description.getMsg()));
        addElement(new ElementInput("Faction Description", CyberTexts.Default_Faction_Description));
//        addElement(new ElementInput("Message of the Day | (MOTD)", FactionErrorString.Default_Faction_MOTD.getMsg()));
        addElement(new ElementInput("Message of the Day | (MOTD)", CyberTexts.Default_Faction_MOTD));
        addElement(new ElementLabel(CyberTexts.Lable_FactionPrivacy));
        addElement(new ElementToggle("Faction Privacy Protection", false));
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
//        new FormWindowModal("CyberFactions | Create Faction (2/2)!", "Faction Created!", "OK", "OK");
        FormResponseCustom frc = getResponse();
        String fn = frc.getInputResponse(0);
        if (fn == null || fn.length() == 0) return false;
        System.out.println("PRINGING THE NAME " + fn);
        FactionErrorString r = _plugin.FM.FFactory.CheckFactionName(fn);
        if (r != null) {
            cp.showFormWindow(new FactionCreate0Error(r));
            return false;
        }
        String desc = frc.getInputResponse(1);
        String motd = frc.getInputResponse(2);
        boolean privacy = frc.getToggleResponse(4);

        Faction f = _plugin.FM.FFactory.CreateFaction(fn, cp, desc, motd, privacy);
        if (f == null) cp.sendMessage(Error_SA223.getMsg()+"!!!!!++11<<");

        return privacy;
    }
}