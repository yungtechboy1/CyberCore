package net.yungtechboy1.CyberCore;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateObject;
import net.yungtechboy1.CyberCore.Manager.Crate.ItemChanceData;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin.Crate.AdminCrateMainMenu;

public class AdminCrateViewItemsWindow extends CyberFormSimple {
    CrateObject _CO;
    int _SI;

    public AdminCrateViewItemsWindow(CrateObject co) {
        this(co, -1);
    }

    public AdminCrateViewItemsWindow(CrateObject co, int si) {
        super(FormType.MainForm.Crate_Admin_ViewItems, "Admin > Crate > View Possible Items");
        _CO = co;
        _SI = si;
        addButton(new ElementButton("<Go Back"));
        if (si == -1) {
            int k = 0;
            for (ItemChanceData z : co.CD.PossibleItems) {
                Item c = z.getItem();
                addButton(new ElementButton("Key: " + k + " ID: " + c.getId() + " | " + c.getName() + " | " + String.join(",",c.getLore())));
                k++;
            }
        } else {
            ItemChanceData p = co.CD.PossibleItems.get(si);
            if (p == null) {
                setContent("You are currently attempting to edit: \n" +
                        "----ITEM---\n" +
                        "Error Getting!");
            } else {
                Item i = p.getItem();
                setContent("You are currently attempting to edit: \n" +
                        "----ITEM---\n" +
                        "ID: " + i.getId() + " Meta: " + i.getDamage() + "\n" +
                        "Name: " + i.getName() + " Lore: " + String.join(",",i.getLore()) + "\n" +
                        "Chance: " + p.getChance() + " Max Count: " + p.getMax_Count());
                addButton(new ElementButton("Give Item"));
                addButton(new ElementButton("Remove Item"));
                addButton(new ElementButton("Edit Item"));
            }
        }
    }

    @Override
    public boolean onRun(CorePlayer p) {
        int k = getResponse().getClickedButtonId();
        if (k == 0) {
            if (_SI == -1) {
                p.showFormWindow(new AdminCrateMainMenu());
            } else {
                p.showFormWindow(new AdminCrateViewItemsWindow(_CO, -1));
            }
        } else {
            if (_SI == -1) {
                p.showFormWindow(new AdminCrateViewItemsWindow(_CO, k-1));
            } else {
                if (k == 1) {
                    ItemChanceData a = _CO.CD.PossibleItems.get(_SI);
                    Item i = a.getItem();
                    p.getInventory().addItem(i);
                } else if (k == 2) {
                     _CO.CD.PossibleItems.remove(_SI);
                } else if (k == 3) {
                    p.showFormWindow(new AdminCrateEditCrateItemDataWindow(_CO, _SI));
                }

            }
        }
        return true;
    }
}
