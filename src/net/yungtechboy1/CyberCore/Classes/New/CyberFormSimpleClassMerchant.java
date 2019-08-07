package net.yungtechboy1.CyberCore.Classes.New;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.Commands.ClassMerchant;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.ClassMerchantData;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.List;

import static net.yungtechboy1.CyberCore.Classes.New.BaseClass.XPToGetToLevel;
import static net.yungtechboy1.CyberCore.Classes.New.BaseClass.XPToLevel;

public class CyberFormSimpleClassMerchant extends CyberFormSimple {
    public CyberFormSimpleClassMerchant(FormType.MainForm ttype, String title) {
        super(ttype, title);
    }

    public CyberFormSimpleClassMerchant(FormType.MainForm ttype, String title, String content) {
        super(ttype, title, content);
    }

    public CyberFormSimpleClassMerchant(FormType.MainForm ttype, String title, String content, List<ElementButton> buttons) {
        super(ttype, title, content, buttons);
    }
BaseClass _BC ;
    public CyberFormSimpleClassMerchant(BaseClass baseClass) {
        super(FormType.MainForm.Class_Merchant, baseClass.getDisplayName()+" Class Merchant");
        _BC = baseClass;
        ArrayList<ClassMerchantData> a = CyberCoreMain.getInstance().CMC.getPurchaseablePowers(baseClass.getTYPE());
        addButton(new ElementButton("<< Back"));
        if(a.isEmpty())return;
        for(ClassMerchantData cmd: a){
            addButton(new ElementButton(cmd.toButtonString()));
        }
    }
    @Override
    public boolean onRun(CorePlayer p) {
        int k = getResponse().getClickedButtonId();
        if(k == 0) {
            p.showFormWindow(_BC.getSettingsWindow());
        }else {
            ArrayList<ClassMerchantData> a = CyberCoreMain.getInstance().CMC.getPurchaseablePowers(_BC.getTYPE());
            if(a.isEmpty() || k >= a.size())return false;
            ClassMerchantData cmd = a.get(k);
            if(cmd == null)return false;
            tryPurchaseCMD(cmd,p);
        }
        return true;
    }

    public void tryPurchaseCMD(ClassMerchantData cmd, CorePlayer p){
        if(cmd.getMoneyCost() > 0 && !p.canMakeTransaction(cmd.getMoneyCost())){
            p.sendMessage("Error! You do not have $"+cmd.getMoneyCost()+" to make this purchase!");
            return;
        }
        if(cmd.getPlayerLevelCost() > 0 && p.getExperienceLevel() < cmd.getPlayerLevelCost()){
            p.sendMessage("Error! You do not have enough Player levels to purchase this power! Try again when you are level "+cmd.getPlayerLevelCost()+" to make this purchase!");
            return;
        }
        if(cmd.getPlayerXPCost() > 0 && p.getTotalExperience() < cmd.getPlayerXPCost()){
            //TODO Bug XPTOLevel is Hack for Player XP Level Calculation
            p.sendMessage("Error! You do not have enough Player levels to purchase this power! Try again when you are level "+(XPToLevel(cmd.getPlayerXPCost())+1)+" to make this purchase!");
            return;
        }
        if(cmd.getClassXPCost() > 0 && p.getPlayerClass() != null &&p.getPlayerClass().getXP() < cmd.getClassXPCost()){
            if(p.getPlayerClass() != null)p.sendMessage("Error! You do not have enough Class XP to purchase this power! Try again when you are Class Level "+(XPToLevel(cmd.getClassXPCost())+1)+" to make this purchase!");
            return;
        }
        if(cmd.getClassLevelCost() > 0 && p.getPlayerClass() != null &&p.getPlayerClass().getLVL() < cmd.getClassLevelCost()){
            if(p.getPlayerClass() != null)p.sendMessage("Error! You do not have enough Class XP to purchase this power! Try again when you are Class Level "+cmd.getClassLevelCost()+" to make this purchase!");
            return;
        }
        if(cmd.getMoneyCost() > 0)p.MakeTransaction(cmd.getMoneyCost());
        if(cmd.getPlayerLevelCost() > 0)p.takeExperience(XPToGetToLevel(cmd.getPlayerLevelCost()));
        if(cmd.getPlayerXPCost() > 0)p.takeExperience(cmd.getPlayerXPCost());
        if(cmd.getClassLevelCost() > 0)p.getPlayerClass().takeXP(XPToGetToLevel(cmd.getClassLevelCost()));
        if(cmd.getClassXPCost() > 0)p.getPlayerClass().takeXP(cmd.getClassXPCost());
        p.getInventory().addItem(cmd.getItemBook());


    }

}
