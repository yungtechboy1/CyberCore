package net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay;

import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class1Window;
import net.yungtechboy1.CyberCore.Tasks.TeleportEvent;

public class HTP_4_Window extends CyberFormModal {
    public HTP_4_Window(){
        super(FormType.MainForm.HTP_4,"Tutorial - Factions System Description (4 / 6)","Continue (5/6)>", "Skip / Close");
        setContent(TextFormat.YELLOW+"~~~ Coming Soon! ~~~");
    }

    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frm = (FormResponseModal) getResponse();
        if (frm.getClickedButtonId() == 0) {
            cp.showFormWindow(new HTP_5_Window());
        } else {
            cp.showFormWindow(new Class1Window());
        }
        return false;
    }
}