package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.List;

public class FactionChatChoose extends CyberFormSimple {
    public FactionChatChoose() {
        this(new ArrayList());
    }

    public FactionChatChoose( List<ElementButton> buttons) {
        super(FormType.MainForm.Faction_Chat_Choose, "CyberFactions | Faction/Ally Chat Window", "", buttons);
        addButton(new ElementButton("Open Faction Chat Window"));
        addButton(new ElementButton("Open Ally Chat Window"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fr =  getResponse();
        switch (fr.getClickedButtonId()){
            case 0:
                //Faction Chat
                _Fac.SendFactionChatWindow(cp);
                break;
            case 1:
                //Ally Chat
                break;
            default:
                cp.LastSentFormType = null;
        }
    }
}
