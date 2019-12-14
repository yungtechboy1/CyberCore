package net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay;

import cn.nukkit.form.response.FormResponseModal;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class1Window;

public class HTP_3_Window extends CyberFormModal {
    public HTP_3_Window(){
        super(FormType.MainForm.HTP_3,"Tutorial - Power System Description (3 / 6)","Continue (4/6)>", "Skip / Close");
        setContent("A full list of Powers UnlimitedPE.com/PowerLists\n" +
                "\n" +
                "Be-whare that every season powers may change\n" +
                "\n" +
                "When you switch classes you will be given a starter power\n" +
                "\n" +
                "New players will also get a Power Crate Key\n" +
                "\n" +
                "More Powers can be learned but finding and using a Power Books at the Class Merchant\n" +
                "\n" +
                " \n" +
                "\n" +
                "There are 3 types of Powers\n" +
                "\n" +
                "Hotbar / Ability / Passive\n" +
                "\n" +
                "Hotbar - These powers can only be activated by tapping your Hotbar slot 7 , 8, and 9. (Normally the last 3 Hot bar slots.) In order to get a power to be in the hotbar use /class and select the power you want for each Hotbar slot.\n" +
                "\n" +
                "Ability - These powers run for a length of time. These powers work 'continuously' until the duration time has passed\n" +
                "\n" +
                "Passive - These powers are ran automatically in the background by the server. Sadly no way to manually activate these");
    }

    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frm = (FormResponseModal) getResponse();
        if (frm.getClickedButtonId() == 0) {
            cp.showFormWindow(new HTP_4_Window());
        } else {
            cp.showFormWindow(new Class1Window());
        }
        return false;
    }
}

