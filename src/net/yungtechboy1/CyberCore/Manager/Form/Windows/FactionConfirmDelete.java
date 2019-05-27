package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;

public class FactionConfirmDelete extends CyberFormModal {
    public FactionConfirmDelete() {
        super(FormType.MainForm.Faction_Delete_Confirm, "CyberFactions | Faction Delete Confirmation", TextFormat.RED+""+TextFormat.BOLD+"WARNING!!!!\n Are you sure you want to delete your faction?","Confirm and Delete", "Cancel");
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal fi = getResponse();
        if(fi.getClickedButtonId() == 0 && _Fac != null) {
            cp.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Faction Deleted!");
            _Fac.BroadcastMessage(FactionsMain.NAME + TextFormat.YELLOW + "!!~~!!Faction has been Deleted by " + cp.getName());
            CyberCoreMain.getInstance().FM.FFactory.RemoveFaction(_Fac);
        }
    }
}