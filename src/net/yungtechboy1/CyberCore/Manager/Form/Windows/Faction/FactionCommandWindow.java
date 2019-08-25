package net.yungtechboy1.CyberCore.Manager.Form.Windows.Faction;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Chat;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.HashMap;

public class FactionCommandWindow  extends CyberFormSimple {
    public FactionCommandWindow(String title, String content) {
        super(FormType.MainForm.Faction_CMD_Window, "Faction Command Window", "Below you will find the list of all commands you have access to. You can coose to tap on the command to learn more about it or just exit when done :)");
        addButton(new ElementButton("/f Chat"));
        addButton(new ElementButton("/f Chat"));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        FormResponseSimple s = getResponse();
        if(s.getClickedButton().getText().equalsIgnoreCase("/f Chat"))new Chat(p,new String[0], CyberCoreMain.getInstance().FM);
        if(s.getClickedButton().getText().equalsIgnoreCase("/f Chat"))new Chat(p,new String[0], CyberCoreMain.getInstance().FM);
        return true;
    }

    //    HashMap<String,>
}
