package net.yungtechboy1.CyberCore.Manager.Form.Windows.Faction;

import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.response.FormResponseCustom;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionSettings;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import java.util.ArrayList;


public class FactionSettingsWindow extends CyberFormCustom {

    public FactionSettingsWindow(Faction f) {
        super(FormType.MainForm.Faction_Settings_Window, "Faction Settings Window");
        _Fac = f;
        FactionSettings fs = f.getSettings();
        ArrayList<String> LGOMR = new ArrayList<String>() {{
            add("Leader");
            add("General");
            add("Officer");
            add("Memeber");
            add("Recruit");
        }};
        ArrayList<String> GOMR = new ArrayList<String>() {{
            add("General");
            add("Officer");
            add("Memeber");
            add("Recruit");
        }};
        ArrayList<String> LGOM = new ArrayList<String>() {{
            add("Leader");
            add("General");
            add("Officer");
            add("Memeber");
        }};
        ArrayList<String> LGO = new ArrayList<String>() {{
            add("Leader");
            add("General");
            add("Officer");
        }};
        ArrayList<String> MR = new ArrayList<String>() {{
            add("Member");
            add("Recruit");
        }};
        addElement(new ElementDropdown("Allowed to View Inbox", LGOMR, fs.getAllowedToViewInbox().getFormPower()));
        addElement(new ElementDropdown("Allowed to Accept Ally`", LGOM, fs.getAllowedToAcceptAlly().getFormPower()));
        addElement(new ElementDropdown("Allowed to Edit Settings", LGO, fs.getAllowedToEditSettings().getFormPower()));
        addElement(new ElementDropdown("Allowed to Kick Players", LGOM, fs.getAllowedToKick().getFormPower()));
        addElement(new ElementDropdown("Allowed to Promote/Demote", LGOM, fs.getAllowedToPromote().getFormPower()));
        addElement(new ElementDropdown("Allowed to Invite", LGOMR, fs.getAllowedToInvite().getFormPower()));
        addElement(new ElementDropdown("Allowed to Claim", LGOM, fs.getAllowedToClaim().getFormPower()));
        addElement(new ElementDropdown("Allowed to Withdraw", LGOM, fs.getAllowedToWinthdraw().getFormPower()));
        addElement(new ElementDropdown("Allowed to SetHome", LGOM, fs.getAllowedToSetHome().getFormPower()));
        addElement(new ElementDropdown("Default Join Rank", MR, fs.getDefaultJoinRank().getFormPower()));
        addElement(new ElementSlider("Weekly Faction Tax", 0, 10000, 100, fs.getWeeklyFactionTax()));

    }

    @Override
    public boolean onRun(CorePlayer p) {
        FormResponseCustom frc = getResponse();
        int avi = frc.getDropdownResponse(0).getElementID();
        int aaa = frc.getDropdownResponse(1).getElementID();
        int aes = frc.getDropdownResponse(2).getElementID();
        int akp = frc.getDropdownResponse(3).getElementID();
        int apd = frc.getDropdownResponse(4).getElementID();
        int ai = frc.getDropdownResponse(5).getElementID();
        int ac = frc.getDropdownResponse(6).getElementID();
        int aw = frc.getDropdownResponse(7).getElementID();
        int ash = frc.getDropdownResponse(8).getElementID();
        int djr = frc.getDropdownResponse(9).getElementID();
        int wft = (int) frc.getSliderResponse(10);

        if (_Fac == null) {
            CyberCoreMain.getInstance().getLogger().critical("WHOOOOAAAAAAA FCATION SHOULD NOT BE NULL FOR THIS EEEE3323");
            return true;
        }

        FactionSettings fs = _Fac.getSettings();
        fs.setAllowedToViewInbox(FactionRank.getRankFromForm(avi));
        fs.setAllowedToAcceptAlly(FactionRank.getRankFromForm(aaa));
        fs.setAllowedToEditSettings(FactionRank.getRankFromForm(aes));
        fs.setAllowedToKick(FactionRank.getRankFromForm(akp));
        fs.setAllowedToPromote(FactionRank.getRankFromForm(apd));
        fs.setAllowedToInvite(FactionRank.getRankFromForm(ai));
        fs.setAllowedToClaim(FactionRank.getRankFromForm(ac));
        fs.setAllowedToWinthdraw(FactionRank.getRankFromForm(aw));
        fs.setAllowedToSetHome(FactionRank.getRankFromForm(ash));
        fs.setDefaultJoinRank(FactionRank.getRankFromForm(djr));
        fs.setWeeklyFactionTax(wft);

        p.sendMessage("Faction's Settings Have Been Updated!");

        return true;
    }
}
