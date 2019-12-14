package net.yungtechboy1.CyberCore;

import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Binary;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateObject;
import net.yungtechboy1.CyberCore.Manager.Crate.ItemChanceData;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

public class AdminCrateEditCrateItemDataWindow   extends CyberFormCustom {
    CrateObject _CO;
    int _SI;

    public AdminCrateEditCrateItemDataWindow(CrateObject co, int si) {
        super(FormType.MainForm.Crate_Admin_ViewItems, "Admin > Crate > Edit Possible Item Data");
        _CO = co;
        _SI = si;
        if (si == -1) {

        } else {
            ItemChanceData p = co.CD.PossibleItems.get(si);
            if (p == null) {
                addElement(new ElementLabel("You are currently attempting to edit: \n" +
                        "----ITEM---\n" +
                        "Error Getting!"));
            } else {
                Item i = p.getItem();
                addElement(new ElementLabel("You are currently attempting to edit: \n" +
                        "----ITEM---\n" +
                        "ID: " +i.getId()+" Meta: "+i.getDamage()+"\n" +
                        "Name: "+i.getName()+" Lore: "+i.getLore()+"\n" +
                        "Chance: "+p.getChance()+" Max Count: "+p.getMax_Count()));
                addElement(new ElementInput("ID",i.getId()+"",i.getId()+""));
                addElement(new ElementInput("Meta",i.getDamage()+"",i.getDamage()+""));
                addElement(new ElementInput("Name",i.getName()+"",i.getName()+""));
                addElement(new ElementInput("Lore",String.join(",",i.getLore()),String.join(",",i.getLore())));
                addElement(new ElementInput("Chance",p.getChance()+"",p.getChance()+""));
                addElement(new ElementInput("MaxCount",p.getMax_Count()+"",p.getMax_Count()+""));
            }
        }
    }

    @Override
    public boolean onRun(CorePlayer p) {
        ItemChanceData a = _CO.CD.PossibleItems.get(_SI);
        Item i = a.getItem();
        super.onRun(p);
        FormResponseCustom frc = getResponse();
        if(!frc.getInputResponse(1).equalsIgnoreCase(i.getId()+"")){
            a.setItemID(Integer.parseInt(frc.getInputResponse(1)));
        }
        if(!frc.getInputResponse(2).equalsIgnoreCase(i.getDamage()+"")){
            a.setItemMeta(Integer.parseInt(frc.getInputResponse(2)));
        }
        if(!frc.getInputResponse(3).equalsIgnoreCase(i.getName()+"")){
            i.setCustomName(frc.getInputResponse(3));
            a.setNBT(Binary.bytesToHexString(i.getCompoundTag()));
        }
        if(!frc.getInputResponse(4).equalsIgnoreCase(String.join(",",i.getLore()))){
            String[] s = frc.getInputResponse(4).split(",");
            i = a.getItem();
            i.setLore(s);
            a.setNBT(Binary.bytesToHexString(i.getCompoundTag()));
        }
        if(!frc.getInputResponse(5).equalsIgnoreCase(a.getChance()+"")){
            a.setChance(Integer.parseInt(frc.getInputResponse(5)));
        }
        if(!frc.getInputResponse(6).equalsIgnoreCase(i.getId()+"")){
            a.setMax_Count(Integer.parseInt(frc.getInputResponse(6)));
        }

        p.showFormWindow(new AdminCrateViewItemsWindow(_CO, _SI));
        _CO.CD.PossibleItems.set(_SI,a);
return true;
    }
}

