package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;

import java.util.LinkedList;

public class FactionChatFactionWindow extends CyberFormCustom {
    public FactionChatFactionWindow(LinkedList<String> ls) {
        super(FormType.MainForm.Faction_Chat_Faction, "CyberFactions | Faction Chat Window");
        addElement(new ElementInput("Send Message", "Type Message Here"));
        if (ls == null) System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        String s;
        while ((s = ls.removeFirst()) != null) {
            addElement(new ElementLabel(s));
        }
    }


    @Override
    public boolean onRun(CorePlayer cp) {
        super.onRun(cp);
        Faction fac = null;
        if (cp.Faction != null) fac = CyberCoreMain.getInstance().FM.FFactory.getFaction(cp.Faction);
        if (fac == null) return false;
        fac.HandleFactionChatWindow((FormResponseCustom) getResponse(), cp);
        return false;
    }
}