package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.AdvancedPowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class MainClassWindowLearnedPowers extends CyberFormSimple {
BaseClass _BC ;
    public MainClassWindowLearnedPowers(BaseClass bc) {
        super(FormType.MainForm.Main_Class_Settings_Window_Learned_Power, "You're "+bc.getDisplayName()+" Class Learned Powers");
        _BC = bc;
        for(AdvancedPowerEnum ape: bc.getClassSettings().getLearnedPowers()){
            addButton(new ElementButton(ape.getPowerEnum()+" Power "+ ape.getValue()));
        }
    }

    @Override
    public boolean onRun(CorePlayer p) {
        p.showFormWindow(p.getPlayerClass().getSettingsWindow());
        return true;
    }
}
