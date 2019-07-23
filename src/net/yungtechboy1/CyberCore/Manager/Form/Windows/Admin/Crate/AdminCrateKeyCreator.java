package net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin.Crate;

import cn.nukkit.form.element.ElementInput;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateMain;
import net.yungtechboy1.CyberCore.Manager.Crate.KeyData;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

public class AdminCrateKeyCreator extends CyberFormCustom {
    public AdminCrateKeyCreator() {
        super(FormType.MainForm.Crate_Admin_KeyCreator, "Admin > Crate > Key Creator");
        addElement(new ElementInput("Custom Item Name"));
        addElement(new ElementInput("Custom NBT Key"));
        addElement(new ElementInput("Crate Key Internal Name"));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        if (p.getInventory().getItemInHand().getId() != 0) {
            Item hand = p.getInventory().getItemInHand().clone();
            String k1 = getResponse().getInputResponse(0);
            String k3 = getResponse().getInputResponse(2);
            if(k1 != null && k1.length() != 0 )hand.setCustomName(k1);
            String k2 = getResponse().getInputResponse(1);
            hand.getNamedTag().putString(CrateMain.CK,k2);
            CyberCoreMain.getInstance().CrateMain.addCrateKey(new KeyData(hand,k3,k1));
        }
        return true;
    }
}
