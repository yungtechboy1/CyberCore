package net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Binary;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin.Crate.AdminCrateMainMenu;

public class AdminMainWindow extends CyberFormSimple {
    public AdminMainWindow() {
        super(FormType.MainForm.Admin_Main, "Admin Page [1/1]");
        addButton(new ElementButton("Chest Manager"));
        addButton(new ElementButton("Book Editor"));
        addButton(new ElementButton("Print Item NBT to Hex"));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        switch (getResponse().getClickedButtonId()){
            case 0:
                p.showFormWindow(new AdminCrateMainMenu());
                break;
            case 1:
                p.showFormWindow(new AdminBookMainMenu());
                break;
            case 2:
                Item ih = p.getInventory().getItemInHand();
                CyberCoreMain.getInstance().getLogger().info("Printing Item Data" + ih.getName() + " | " + ih.getCustomName() + " | " + Binary.bytesToHexString(ih.getCompoundTag()));
//                cp.sendMessage("DEPRECATED");
                break;
        }
        return true;
    }
}
