package net.yungtechboy1.CyberCore.Manager.Form.Windows.Faction;

import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;

public class FactionLeaveConfirmWindow extends CyberFormModal {
    public FactionLeaveConfirmWindow(Faction f) {
        super(FormType.MainForm.Faction_Leave_Confirm, "Faction Leave Confirmation", TextFormat.RED + "Are you sure you want to leave " + f.getDisplayName() + "?", "Leave", "Stay / Cancel");
    }

    @Override
    public boolean onRun(CorePlayer p) {
        FormResponseModal fr = getResponse();
        int k = fr.getClickedButtonId();
        Faction fac = p.getFaction();
        if(fac.IsMember( p.getName()))fac.DelMember(p.getName());
        if(fac.IsOfficer( p.getName()))fac.DelOfficer(p.getName());
        if(fac.IsGeneral( p.getName()))fac.DelGeneral(p.getName());
        if(fac.IsRecruit( p.getName()))fac.DelRecruit(p.getName());

        p.sendMessage(FactionsMain.NAME+ TextFormat.GREEN + "You successfully left faction");
        fac.TakePower(1);
        fac.BroadcastMessage(FactionsMain.NAME+TextFormat.YELLOW+p.getName()+" has Left the Faction! ");
//            if(Main.CC != null)Main.CC.Setnametag((Player)p);
//            Main.CC.Setnametag((Player) p);
//            Main.sendBossBar((Player) p);
        _plugin.FM.FFactory.FacList.remove(p.getName().toLowerCase());
        return true;
    }
}
