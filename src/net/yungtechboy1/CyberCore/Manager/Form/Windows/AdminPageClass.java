package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.block.BlockID;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.utils.Binary;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Block.CustomElementBlock;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemMap;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.CustomCraftingManager;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;
import net.yungtechboy1.CyberCore.Manager.PositionImage;

import java.util.ArrayList;
import java.util.List;

public class AdminPageClass extends CyberFormSimple {
    public AdminPageClass() {
        this(new ArrayList());
    }

    public AdminPageClass(List<ElementButton> buttons) {
        super(FormType.MainForm.Admin_Page_Class, "CyberFactions | Admin Page Class","", buttons);
        addButton(new ElementButton("Go Back"));
        addButton(new ElementButton("Remove Current Class"));
        addButton(new ElementButton("Add Class XP"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fap = getResponse();
        int id = fap.getClickedButtonId();
        switch (id) {
            case 0:
                cp.showFormWindow(new FactionAdminPage1());
                break;
            case 1:
                CyberCoreMain.getInstance().ClassFactory.clearClass(cp);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
            case 8:
            case 9:
        }
    }
}
