package net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin.Crate;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class AdminCrateMainMenu extends CyberFormSimple {
    public AdminCrateMainMenu() {
        super(FormType.MainForm.Admin_Main, "Admin > Crate Admin Page");
        addButton(new ElementButton("Add Possible Item to Chest"));//0//TODO - LATER
        addButton(new ElementButton("View Possible Item from Chest"));//1//TODO - LATER
        addButton(new ElementButton("Add Crate to Chest"));//2 // DONE
        addButton(new ElementButton("Add Crate Key to Chest"));//3 DONE
        addButton(new ElementButton("Create Crate Key for Chest"));//4 DONE
        addButton(new ElementButton("Get Crate Key for Chest"));//5 DONE
        addButton(new ElementButton("Save Config"));//6 DONE
        addButton(new ElementButton("Re-Load Config"));//7 DONE
        addButton(new ElementButton("Back"));//8
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        switch (getResponse().getClickedButtonId()) {
            case 2:
                CyberCoreMain.getInstance().CrateMain.PrimedPlayer.add(p.getName());
                p.sendMessage("Tap a chest to make that chest a chest crate");
                break;
            case 4:
                p.showFormWindow(new AdminCrateKeyCreator());
                break;
            case 3:
                Item hand = p.getInventory().getItemInHand();
                if (CrateMain.isItemKey(hand)) {
                    CyberCoreMain.getInstance().CrateMain.PrimedPlayer.add(p.getName());
                    CyberCoreMain.getInstance().CrateMain.SetKeyPrimedPlayer.add(p.getName());
                    p.sendMessage("Now tap the chest you would like to add the key to");
                }
                break;
            case 5:
                p.showFormWindow(new AdminCrateGetCrateKey());
                break;
            case 0:
                Item h = p.getInventory().getItemInHand();
                if (!h.isNull()) {
                    CyberCoreMain.getInstance().CrateMain.PrimedPlayer.add(p.getName());
                    CyberCoreMain.getInstance().CrateMain.SetCrateItemPrimedPlayer.add(p.getName());
                    p.sendMessage("Now tap the chest you would like to add your item to");
                }
                break;
            case 1:
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
