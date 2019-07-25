package net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin.Crate;

import cn.nukkit.form.element.ElementButton;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CrateKey;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Crate.KeyData;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class AdminCrateGetCrateKey extends CyberFormSimple {
    public AdminCrateGetCrateKey() {
        super(FormType.MainForm.Crate_Admin_GetCrateKey, "Admin > Crate > Get Crate Keys");
        for(KeyData c : CyberCoreMain.getInstance().CrateMain.CrateKeys.values()){
            addButton(new ElementButton(c.getItemKey()+" | "+c.getKey_Name()));
        }
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        int k = getResponse().getClickedButtonId();
        if(k > CyberCoreMain.getInstance().CrateMain.CrateKeys.values().size()){
            p.sendMessage("Error! E32213");
            return true;
        }
        KeyData kd = (KeyData)CyberCoreMain.getInstance().CrateMain.CrateKeys.values().toArray()[k];
        if(kd != null){
            p.getInventory().addItem(kd.getItemKey());
        }
        return true;
    }
}
