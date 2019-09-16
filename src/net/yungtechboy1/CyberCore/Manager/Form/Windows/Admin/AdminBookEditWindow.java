package net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin;

import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerAbstract;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

public class AdminBookEditWindow extends CyberFormCustom {
    boolean _e = false;
    Item _h;

    public AdminBookEditWindow(Item hand) {
        super(FormType.MainForm.Admin_Main_Book_Edit, "Admin > Book Editor");
        _h = hand.clone();
        if (!hand.hasCompoundTag()) {
            addElement(new ElementLabel("Error! No Compound Tag Found!"));
            _e = true;
        } else {
            CompoundTag ct = hand.getNamedTag();
            if (!ct.contains("APE")) {
                addElement(new ElementLabel("APE Not Found!"));
                _e = true;
            } else {
                String a = ct.getString("APE");
                AdvancedPowerEnum ape = AdvancedPowerEnum.fromString(a);
                if (ape == null) {
                    _e = true;
                    addElement(new ElementLabel("APE IS NULL!"));
                } else if (ape.isStage()) {
                    addElement(new ElementInput("Enter Book Stage", ape.getStageEnum().ordinal() + "", ape.getStageEnum().ordinal() + ""));
                }
            }
        }
    }

    @Override
    public boolean onRun(CorePlayer p) {
        if (_e) {
            return true;
        }
        int s = Integer.parseInt(getResponse().getInputResponse(0));
        CompoundTag ct = _h.getNamedTag();
        String a = ct.getString("APE");
        AdvancedPowerEnum ape = AdvancedPowerEnum.fromString(a);
        ape.setSE(PowerAbstract.StageEnum.getStageFromInt(s));
        ct.putString("APE",ape.toString());
        _h.setCompoundTag(ct);
        p.getInventory().setItemInHand(_h);

        return super.onRun(p);
    }
}
