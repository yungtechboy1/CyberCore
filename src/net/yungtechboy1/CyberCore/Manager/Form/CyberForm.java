package net.yungtechboy1.CyberCore.Manager.Form;

import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.window.FormWindow;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

public class CyberForm extends FormWindow {
    public FormType.MainForm FT = FormType.MainForm.NULL;
    public FormType.MainForm AFT = FormType.MainForm.NULL;
    public CyberCoreMain plugin = CyberCoreMain.getInstance();

    public CyberForm(FormType.MainForm type) {
        FT = type;
    }

    @Override
    public String getJSONData() {
        return null;
    }

    @Override
    public FormResponse getResponse() {
        return null;
    }

    @Override
    public void setResponse(String s) {

    }

    public void setResponse(String data, CorePlayer p) {
        setResponse(data);
        onRun(p);


    }

    public Faction Fac = null;

    public void onRun(CorePlayer p) {
        if (p.Faction != null) Fac = CyberCoreMain.getInstance().FM.FFactory.getFaction(p.Faction);
    }

    ;
}
