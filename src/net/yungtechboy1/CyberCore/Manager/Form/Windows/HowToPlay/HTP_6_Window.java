package net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class1Window;

public class HTP_6_Window extends CyberFormSimple {
    public HTP_6_Window(){
        super(FormType.MainForm.HTP_6,"Tutorial - Custom Items (6 / 6)");
        setContent(TextFormat.YELLOW+"Now that you are a pro at CyberTech Factions and have a good ideal on how Classes, Powers and XP/Leveling work, are you ready to pick your Class?");
        addButton(new ElementButton("Yes, Class Selections"));
        addButton(new ElementButton("No, I'll choose later"));
    }

    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple frm = getResponse();
        if (frm.getClickedButtonId() == 0) {
            cp.showFormWindow(new HTP_7_Window());
        } else {
            cp.sendMessage(TextFormat.YELLOW+"Use /htp to re-access this window!");
//            cp.showFormWindow(new Class1Window());
        }
        return false;
    }
}