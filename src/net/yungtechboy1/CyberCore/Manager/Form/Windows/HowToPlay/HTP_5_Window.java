package net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay;

import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class1Window;

public class HTP_5_Window extends CyberFormModal {
    public HTP_5_Window(){
        super(FormType.MainForm.HTP_5,"Tutorial - Player / Class Leveling System (5 / 6)","Continue (6/6)>", "Skip / Close");
        setContent(TextFormat.YELLOW+"~~~ Coming Soon! ~~~");
    }

    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frm = (FormResponseModal) getResponse();
        if (frm.getClickedButtonId() == 0) {
            cp.showFormWindow(new HTP_6_Window());
        } else {
            cp.showFormWindow(new Class1Window());
        }
        return false;
    }
}