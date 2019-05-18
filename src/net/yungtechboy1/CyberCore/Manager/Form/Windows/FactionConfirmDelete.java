package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.block.Block;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;

import static net.yungtechboy1.CyberCore.FormType.MainForm.Enchanting_1;
import static net.yungtechboy1.CyberCore.FormType.MainForm.NULL;

public class FactionConfirmDelete extends CyberFormModal {
    public FactionConfirmDelete() {
        super(FormType.MainForm.Faction_Delete_Confirm, "CyberFactions | Faction Delete Confirmation", TextFormat.RED+""+TextFormat.BOLD+"WARNING!!!!\n Are you sure you want to delete your faction?","Confirm and Delete", "Cancel");
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal fi = getResponse();
        if(fi.getClickedButtonId() == 0 && Fac != null) {
            cp.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Faction Deleted!");
            Fac.BroadcastMessage(FactionsMain.NAME + TextFormat.YELLOW + "!!~~!!Faction has been Deleted by " + cp.getName());
            CyberCoreMain.getInstance().FM.FFactory.RemoveFaction(Fac);
        }
    }
}