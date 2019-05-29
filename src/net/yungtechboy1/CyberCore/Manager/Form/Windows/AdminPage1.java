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

public class AdminPage1 extends CyberFormSimple {
    public AdminPage1() {
        this(new ArrayList());
    }

    public AdminPage1(List<ElementButton> buttons) {
        super(FormType.MainForm.Admin_Page_1, "CyberFactions | Admin Page 1","", buttons);
        addButton(new ElementButton("Misc Admin PG1"));
        addButton(new ElementButton("ClassAdmin"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fap = getResponse();
        int id = fap.getClickedButtonId();
        switch (id) {
            case 0:
                cp.showFormWindow(new AdminPage1_1());
                break;
            case 1:
                cp.showFormWindow(new AdminPage1_1());
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
            case 5:
                cp.getInventory().clearAll();
                cp.getInventory().addItem(Item.get(BlockID.SAND,0,300));
                cp.getInventory().addItem(Item.get(Item.GUNPOWDER,0,200));
                cp.getInventory().addItem(Item.get(Item.GUNPOWDER,2,200));
                cp.getInventory().addItem(Item.get(Item.COBWEB,0,300));
                cp.getInventory().sendContents(cp);
                break;
            case 6:
                cp.getInventory().addItem(Item.get(BlockID.SAND,0,300));
                cp.getInventory().addItem(Item.get(Item.GUNPOWDER,0,200));
                cp.getInventory().addItem(Item.get(Item.GUNPOWDER,2,200));
                cp.getInventory().addItem(Item.get(Item.COBWEB,0,300));
                cp.getInventory().sendContents(cp);
                break;
            case 7:
                cp.getInventory().addItem(Item.get(BlockID.PURPLE_GLAZED_TERRACOTTA,0,1));
                cp.getInventory().addItem(Item.get(BlockID.PURPLE_GLAZED_TERRACOTTA,1,1));
                cp.getInventory().addItem(Item.get(BlockID.PURPLE_GLAZED_TERRACOTTA,2,1));
                cp.getInventory().sendContents(cp);
            case 8:
                cp.sendMessage(cp.getInventory().getItemInHand().getClass().getName()+"||||"+cp.getInventory().getItemInHand().getDamage());
                System.out.println(cp.getInventory().getItemInHand().getClass().getName()+"||||"+cp.getInventory().getItemInHand().getDamage());
            case 9:
                cp.getInventory().addItem(new ItemBlock(new CustomElementBlock()));
                cp.getInventory().sendContents(cp);
        }
    }
}
