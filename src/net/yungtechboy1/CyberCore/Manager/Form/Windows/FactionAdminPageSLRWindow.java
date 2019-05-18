package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Data.FactionSQL;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.List;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Success_ADMIN_Faction_Saved;

public class FactionAdminPageSLRWindow extends CyberFormSimple {
    public FactionAdminPageSLRWindow() {
        this(new ArrayList());
    }

    public FactionAdminPageSLRWindow(List<ElementButton> buttons) {
        super(FormType.MainForm.Faction_Admin_Page_SLR, "CyberFactions | Admin Page > SLR", "", buttons);
        addButton(new ElementButton("Save"));
        addButton(new ElementButton("Load"));
        addButton(new ElementButton("Reload"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fapp = getResponse();
        int idd = fapp.getClickedButtonId();
        switch (idd) {
            case 0:
                plugin.FM.FFactory.SaveAllFactions();
                cp.sendMessage(Success_ADMIN_Faction_Saved.getMsg());
                break;
            case 1:
                plugin.FM = new FactionsMain(plugin, new FactionSQL(plugin));
                cp.sendMessage(Success_ADMIN_Faction_Saved.getMsg());
                break;
            case 2:
                plugin.FM.FFactory.SaveAllFactions();
                plugin.FM = new FactionsMain(plugin, new FactionSQL(plugin));
                break;
        }
    }
}
