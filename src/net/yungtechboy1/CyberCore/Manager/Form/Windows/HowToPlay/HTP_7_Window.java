package net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.Classes.New.Magic.Priest;
import net.yungtechboy1.CyberCore.Classes.New.Magic.Sorcerer;
import net.yungtechboy1.CyberCore.Classes.New.Offense.Knight;
import net.yungtechboy1.CyberCore.Classes.New.Offense.Theif;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class HTP_7_Window extends CyberFormSimple {
    public HTP_7_Window() {
        super(FormType.MainForm.HTP_7, "Tutorial - Choose your First Class");

        setContent("Now it is time to choose a class to start with!\n" +
                "\n" +
                "You have the option between 4 differnt classes!\n" +
                "\n" +
                "Knight - Gain extra hearts and absorb damage better but move slower and regenerate hearts slower\n" +
                "\n" +
                "Sorcerer - Specializes in all around magic\n" +
                "\n" +
                "Theif - Deal A little more damage and gain the ability to steal items from players (Complex Mechanic)\n" +
                "\n" +
                "Preist - Basicily a medic class, deal less damage and have less hearts but you can heal and keep team mates in fights longer!");
        addButton(new ElementButton("Knight"));
        addButton(new ElementButton("Sorcerer"));
        addButton(new ElementButton("Theif"));
        addButton(new ElementButton("Priest"));
        addButton(new ElementButton("Choose Later"));
    }

    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple frm = getResponse();
        if (frm.getClickedButtonId() == getKey()) {
            //Knight
            Knight ts = new Knight(_plugin, cp, null);
            cp.SetPlayerClass(ts);
            _plugin.ClassFactory.SaveClassToFile(cp);
            cp.sendMessage("Class Set!");
        } else if (frm.getClickedButtonId() == getKey()) {
            Sorcerer ts = new Sorcerer(_plugin, cp, null);
            cp.SetPlayerClass(ts);
            _plugin.ClassFactory.SaveClassToFile(cp);
            cp.sendMessage("Class Set!");
        } else if (frm.getClickedButtonId() == getKey()) {
            Theif ts = new Theif(_plugin, cp, null);
            cp.SetPlayerClass(ts);
            _plugin.ClassFactory.SaveClassToFile(cp);
            cp.sendMessage("Class Set!");
        } else if (frm.getClickedButtonId() == getKey()) {
            Priest ts = new Priest(_plugin, cp, null);
            cp.SetPlayerClass(ts);
            _plugin.ClassFactory.SaveClassToFile(cp);
            cp.sendMessage("Class Set!");
        } else if (frm.getClickedButtonId() == getKey()) {
            cp.sendMessage("Use /htp when you are ready to choose your class!");
        }
        return false;
    }
}