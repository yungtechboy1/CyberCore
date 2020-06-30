package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import static net.yungtechboy1.CyberCore.FormType.MainForm.Crate_Confirm_Add;

public class CrateConfirmWindow extends CyberFormSimple {
    public CrateConfirmWindow(FormType.MainForm ttype, String title) {
        super(ttype, title);

    }

    @Override
    public boolean onRun(CorePlayer p) {
        if (getResponse().getClickedButtonId() == 0) {
            if (get_FT() == Crate_Confirm_Add) {
                CyberCoreMain.getInstance().CrateMain.SetCrateItemPrimedPlayer.add(p.getName());
                CyberCoreMain.getInstance().CrateMain.PrimedPlayer.add(p.getName());
            } else if (get_FT() == Crate_Confirm_Add) {
                CyberCoreMain.getInstance().CrateMain.PrimedPlayer.add(p.getName());
                CyberCoreMain.getInstance().CrateMain.SetKeyPrimedPlayer.add(p.getName());
            } else {
                CyberCoreMain.getInstance().CrateMain.PrimedPlayer.add(p.getName());
                CyberCoreMain.getInstance().CrateMain.SetKeyPrimedPlayer.add(p.getName());
            }
            return super.onRun(p);
        }else{
            return true;
        }
    }
}
