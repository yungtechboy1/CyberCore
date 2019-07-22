package net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin.Crate;

import cn.nukkit.form.element.ElementButton;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class AdminCrateMainMenu extends CyberFormSimple {
    public AdminCrateMainMenu() {
        super(FormType.MainForm.Admin_Main, "Admin > Crate Admin Page");
        addButton(new ElementButton("Add Possible Item to Chest"));
        addButton(new ElementButton("View Possible Item from Chest"));
        addButton(new ElementButton("Add Crate to Chest"));
        addButton(new ElementButton("Add Crate Key to Chest"));
        addButton(new ElementButton("Create Crate Key for Chest"));
        addButton(new ElementButton("Get Crate Key for Chest"));
        addButton(new ElementButton("Save Config"));
        addButton(new ElementButton("Re-Load Config"));
        addButton(new ElementButton("Back"));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        switch (getResponse().getClickedButtonId()){
            case 2:
                CyberCoreMain.getInstance().CrateMain.PrimedPlayer.add(p.getName());
                p.sendMessage("Tap a chest to make that chest a chest crate");
                break;
            case 4:

            case 0:
            case 1:
            case 3:
            case 5:
                //TODO
                break;
            case 6:
                CyberCoreMain.getInstance().CrateMain.save();
            case 7:
                CyberCoreMain.getInstance().CrateMain.reload();
        }
        return true;
    }
}
