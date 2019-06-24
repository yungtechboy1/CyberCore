package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.block.Block;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;

import static net.yungtechboy1.CyberCore.FormType.MainForm.*;

public class Enchanting0Window extends CyberFormModal {
    public Enchanting0Window(String name) {
        super(FormType.MainForm.Enchanting_0, name,
                "Welcome to the Enchantment GUI\n" +
                        "The fee to view your enchants is $5000"
                , "Go Back", "Pay & Continue >");
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frm = (FormResponseModal) getResponse();
        if (frm.getClickedButtonId() == 0) {
            Item e = Item.get(Block.ENCHANT_TABLE, 0, 1);
            cp.getInventory().addItem(e.setCustomName("TTTTTTTTTT"));
            cp.LastSentFormType = NULL;
        } else {
            cp.ReturnItemBeingEnchanted();
            Item i = cp.getInventory().getItemInHand();
            cp.getInventory().remove(i);//Take item and Store it
            cp.setItemBeingEnchanted(i);
            cp.LastSentFormType = Enchanting_1;
            cp.showFormWindow(cp.getNewWindow());
            cp.clearNewWindow();
        }
        return false;
    }
}
