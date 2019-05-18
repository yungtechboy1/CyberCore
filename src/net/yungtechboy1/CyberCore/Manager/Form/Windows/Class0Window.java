package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.block.Block;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;

import static net.yungtechboy1.CyberCore.FormType.MainForm.*;

public class ChooseClassWindow extends CyberFormModal {

    public ChooseClassWindow() {
        super(FormType.MainForm.Class_0,"Welcome to the Class Picker!", "Please Choose a class, but please not that after choosing a class you can not change it!\n" +
                "Well.. There is one way but its not cheep or easy!\n" +
                "I know one of the bosses drops a potion that can allow you to change!\n" +
                "But that was only rumors! Good Luck Choose wise!", "Go Back", "Learn More >");
    }

    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frm = (FormResponseModal) getResponse();
        if (frm.getClickedButtonId() == 0) {
            cp.LastSentFormType = NULL;
        } else {
            cp.showFormWindow(cp.getNewWindow());
            cp.LastSentFormType = Class_1;
            cp.clearNewWindow();
        }
    }
}