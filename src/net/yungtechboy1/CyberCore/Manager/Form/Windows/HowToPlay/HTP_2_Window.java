package net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay;

import cn.nukkit.form.response.FormResponseModal;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class1Window;

public class HTP_2_Window extends CyberFormModal {
    public HTP_2_Window(){
        super(FormType.MainForm.HTP_2,"Tutorial - Class System Description (2 / 6)","",  "Continue (3/6)>","Skip / Close");
        setContent("What are Classes? \n" +
                "\n" +
                "The Class system allows the player to gain buffs, that in-turn come with debuffs in other areas, and to learn powers specific to their class which can be used as an attack, defense, or passive (healing)! We have tried to create a class for every player’s playstyle but in order to ensure that our players, you, know how to properly fully utilize the system we only allow you to choose from 4 Starter classes. More will be explained about that later.\n" +
                "\n" +
                "How do I unlock more Classes\n" +
                "\n" +
                "Find and use Class Crate Keys to win Class Books which can change your class\n" +
                "\n" +
                "What can Classes do?\n" +
                "\n" +
                "Every class has a pre-set list of powers that can be learned and acquired through playing or supporting the server with a rank. By default, every class gives you 1 learned power and as a brand new player you also receive a 1 Power Crate Key for your class which can be used win another power. In addation, Classes also contain Buffs and Debuffs, these can affect the Movement speed, Swing Speed, Damage, Jump power, and many more fun variables.");
    }

    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frm = (FormResponseModal) getResponse();
        if (frm.getClickedButtonId() == 0) {
            cp.showFormWindow(new HTP_3_Window());
        } else {
            cp.showFormWindow(new Class1Window());
        }
        return false;
    }

}
