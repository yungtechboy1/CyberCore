package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.List;

public class FactionKickListWindow extends CyberFormSimple {
    public FactionKickListWindow(ArrayList<String> af) {
        this(new ArrayList(),af);
    }

    public FactionKickListWindow( List<ElementButton> buttons,ArrayList<String> af) {
        super(FormType.MainForm.Faction_Kick_List, "CyberFactions | Faction Kick Page", "", buttons);
        if (af.size() == 0) {
            addButton(new ElementButton("--No Other Members In Faction--"));
        } else {
            int i = 0;
            for (String f : af) {
                i++;
                if (i > 20) continue;
                addButton(new ElementButton(f));
            }
        }
        addButton(new ElementButton("CJ123"));
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple frrs = (FormResponseSimple) getResponse();
        String tp = frrs.getClickedButton().getText();
        if(_Fac == null){
            cp.sendMessage("Errpr #251036");
            return;
        }else{
            System.out.println("STARTTING KICKKING >>> "+tp);
            _Fac.KickPlayer(tp);
        }
    }
}