package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.block.BlockID;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseCustom;
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
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;
import net.yungtechboy1.CyberCore.Manager.PositionImage;

import java.util.ArrayList;
import java.util.List;

public class SettingsPage0 extends CyberFormSimple {
    public SettingsPage0() {
        this(new ArrayList());
    }

    public SettingsPage0(List<ElementButton> buttons) {
        super(FormType.MainForm.SettingsPage0, "CyberFactions | Settings Manager");
        addButton(new ElementButton("Player Setting"));
        addButton(new ElementButton("Faction Settings"));
//        addButton(new ElementButton("Show Damage Tags"));
//        addButton(new ElementButton("Show Advanced Damage Tags"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fap = getResponse();
        int k = fap.getClickedButtonId();
        if (k == 0) {
            cp.showFormWindow(new PlayerSettingsPage0());
        } else if (k == 1) {
            cp.showFormWindow(new PlayerFactionSettingsPage0());

        }
    }
}
