package net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay;

import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class1Window;

public class HTP_1_Window extends CyberFormModal {
    public HTP_1_Window(){
        super(FormType.MainForm.HTP_1,"Tutorial - Helpful Tips (1 / 6)","",  "Continue (2/6)>","Skip / Close");
        setContent(TextFormat.YELLOW+"~~~ Coming Soon ~~~");
    }

    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frm = (FormResponseModal) getResponse();
        if (frm.getClickedButtonId() == 0) {
            cp.showFormWindow(new HTP_2_Window());
        } else {
            cp.showFormWindow(new Class1Window());
        }
        return false;
    }
}

