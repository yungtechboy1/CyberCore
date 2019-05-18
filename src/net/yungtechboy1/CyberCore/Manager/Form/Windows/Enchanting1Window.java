package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.block.Block;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CustomEnchantment;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;

import java.util.ArrayList;

import static net.yungtechboy1.CyberCore.FormType.MainForm.Enchanting_1;
import static net.yungtechboy1.CyberCore.FormType.MainForm.NULL;

public class Enchanting1Window extends CyberFormCustom {
    public Enchanting1Window(CorePlayer cp,CustomEnchantment.Tier tt,Item item) {
        super(FormType.MainForm.Enchanting_1,"Choose your Class Catagory!",
                new ArrayList<Element>() {{
                    addAll(CustomEnchantment.PrepareEnchantList(cp.GetStoredEnchants(tt,3,item)));
                    add(new ElementStepSlider("TE3", new ArrayList<String>() {{
                        add("1");
                        add("2");
                        add("3");
                    }}, 0));
                }});
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseCustom frc1 = (FormResponseCustom) getResponse();
        FormResponseData frd = frc1.getStepSliderResponse(3);
        int ke = frd.getElementID();
        cp.sendMessage(frd.getElementContent() + "<<<<<<<");
        Enchantment e = cp.GetStoredEnchants().get(ke);
        if (e == null) {
            cp.sendMessage("Error!");
        }
    }
}