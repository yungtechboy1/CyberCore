package net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay;

import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormModal;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class1Window;

public class HTP_0_Window extends CyberFormModal {

    public HTP_0_Window() {
            super(FormType.MainForm.HTP_0,"Welcome to UnlimitedPE!", TextFormat.RED+"       !!!!! Please take time and read the below   instructions on how to play !!!!\n\n      !!!!! Please take time and read the below instructions on how to play !!!!\n\n      !!!!! Please take time and read the below     instructions on how to play !!!!", "Continue (0/6)>", "Skip / Close");
        setContent(getContent()+TextFormat.RESET+"\n\n\n This Window can always be reaccessed using the "+TextFormat.YELLOW+"/howtoplay"+TextFormat.RESET+" or "+TextFormat.YELLOW+"/htp"+TextFormat.RESET+" command in game, or by visiting "+TextFormat.YELLOW+"UnlimitedPE.com/how-to-play/ \n\n" +TextFormat.RESET+
                "This tutorial is 6 Pages (Short Pages) Long, You can choose to skip below.\n\n\n" +
                TextFormat.BOLD+TextFormat.YELLOW+
                "Table of Contents\n\n" +TextFormat.RESET+
                "\n" +TextFormat.GREEN+
                "- Helpful tips and FAQs\n" +
                "\n" +
                "- Class System Description\n" +
                "\n" +
                "- Power System Description\n" +
                "\n" +
                "- Factions System Description\n" +
                "\n" +
                "- Player / Class Leveling System\n" +
                "\n" +
                "- Custom Items");
    }

    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseModal frm = (FormResponseModal) getResponse();
        if (frm.getClickedButtonId() == 0) {
            cp.showFormWindow(new HTP_1_Window());
        } else {
            cp.showFormWindow(new Class1Window());
        }
        return false;
    }
}