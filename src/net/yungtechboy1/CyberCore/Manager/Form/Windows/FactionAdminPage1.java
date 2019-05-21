package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.inventory.CraftingManager;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Binary;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemGunpowder;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemMap;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.CustomCraftingManager;
import net.yungtechboy1.CyberCore.Manager.Factions.Data.FactionSQL;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;
import net.yungtechboy1.CyberCore.Manager.PositionImage;

import java.util.ArrayList;
import java.util.List;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Success_ADMIN_Faction_Saved;

public class FactionAdminPage1 extends CyberFormSimple {
    public FactionAdminPage1() {
        this(new ArrayList());
    }

    public FactionAdminPage1(List<ElementButton> buttons) {
        super(FormType.MainForm.Faction_Admin_Page_1, "CyberFactions | Admin Page (1/2)","", buttons);
        addButton(new ElementButton("Save/Load/Reload"));
        addButton(new ElementButton("GiveTestImage"));
        addButton(new ElementButton("Print Item NBT to Hex"));
        addButton(new ElementButton("Resend Crafting Packet"));
        addButton(new ElementButton("Resend Creative Packet"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fap = getResponse();
        int id = fap.getClickedButtonId();
        switch (id) {
            case 0:
                cp.showFormWindow(new FactionAdminPageSLRWindow());
                break;
            case 1:
                CustomItemMap im = new CustomItemMap();
                PositionImage pi = new PositionImage(128,cp.getFloorX(),cp.getFloorY(),cp.getFloorZ(),cp.getLevel());
                im.setImage(pi);
                cp.getInventory().addItem(im);
                break;
            case 2:
                Item ih = cp.getInventory().getItemInHand();
                CyberCoreMain.getInstance().getLogger().info("Printing Item Data"+ih.getName()+" | "+ ih.getCustomName() +" | "+ Binary.bytesToHexString( ih.getCompoundTag()));
//                cp.sendMessage("DEPRECATED");
                break;
            case 3:
                CyberCoreMain.getInstance().CraftingManager.rebuildPacket();
                cp.dataPacket(CustomCraftingManager.packet);
                break;
            case 4:
                cp.getInventory().sendCreativeContents();
                break;
        }
    }
}
