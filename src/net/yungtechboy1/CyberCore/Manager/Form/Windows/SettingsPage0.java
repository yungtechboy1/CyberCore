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

public class SettingsPage0 extends CyberFormCustom {
    public SettingsPage0() {
        this(new ArrayList());
    }

    public SettingsPage0(List<ElementButton> buttons) {
        super(FormType.MainForm.PlayerSettings0, "CyberFactions | Settings Page (1/2)");
        addElement(new ElementToggle("Show Damage Tags"));
        addElement(new ElementToggle("Show Advanced Damage Tags"));
//        addButton(new ElementButton("Show Damage Tags"));
//        addButton(new ElementButton("Show Advanced Damage Tags"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseCustom fap = getResponse();
        int id = fap.g();
        switch (id) {
            case 0:
                cp.Settings.
                break;
            case 1:
                CustomItemMap im = new CustomItemMap();
                PositionImage pi = new PositionImage(128, cp.getFloorX(), cp.getFloorY(), cp.getFloorZ(), cp.getLevel());
                im.setImage(pi);
                cp.getInventory().addItem(im);
                break;
            case 2:
                Item ih = cp.getInventory().getItemInHand();
                CyberCoreMain.getInstance().getLogger().info("Printing Item Data" + ih.getName() + " | " + ih.getCustomName() + " | " + Binary.bytesToHexString(ih.getCompoundTag()));
//                cp.sendMessage("DEPRECATED");
                break;
            case 3:
                CyberCoreMain.getInstance().CraftingManager.rebuildPacket();
                cp.dataPacket(CustomCraftingManager.packet);
                break;
            case 4:
                cp.getInventory().sendCreativeContents();
                break;
            case 5:
                cp.getInventory().clearAll();
                cp.getInventory().addItem(Item.get(BlockID.SAND, 0, 300));
                cp.getInventory().addItem(Item.get(Item.GUNPOWDER, 0, 200));
                cp.getInventory().addItem(Item.get(Item.GUNPOWDER, 2, 200));
                cp.getInventory().addItem(Item.get(Item.COBWEB, 0, 300));
                cp.getInventory().sendContents(cp);
                break;
            case 6:
                cp.getInventory().addItem(Item.get(BlockID.SAND, 0, 300));
                cp.getInventory().addItem(Item.get(Item.GUNPOWDER, 0, 200));
                cp.getInventory().addItem(Item.get(Item.GUNPOWDER, 2, 200));
                cp.getInventory().addItem(Item.get(Item.COBWEB, 0, 300));
                cp.getInventory().sendContents(cp);
                break;
            case 7:
                cp.getInventory().addItem(Item.get(BlockID.PURPLE_GLAZED_TERRACOTTA, 0, 1));
                cp.getInventory().addItem(Item.get(BlockID.PURPLE_GLAZED_TERRACOTTA, 1, 1));
                cp.getInventory().addItem(Item.get(BlockID.PURPLE_GLAZED_TERRACOTTA, 2, 1));
                cp.getInventory().sendContents(cp);
            case 8:
                cp.sendMessage(cp.getInventory().getItemInHand().getClass().getName() + "||||" + cp.getInventory().getItemInHand().getDamage());
                System.out.println(cp.getInventory().getItemInHand().getClass().getName() + "||||" + cp.getInventory().getItemInHand().getDamage());
            case 9:
                cp.getInventory().addItem(new ItemBlock(new CustomElementBlock()));
                cp.getInventory().sendContents(cp);
            case 10:
                CyberCoreMain.getInstance().SpawnShop.OpenShop(cp,1);
        }
    }
}
