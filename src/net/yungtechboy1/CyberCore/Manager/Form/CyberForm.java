package net.yungtechboy1.CyberCore.Manager.Form;

import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.window.FormWindow;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

public class CyberForm extends FormWindow {
    public  FormType.MainForm _FT = FormType.MainForm.NULL;
    public  FormType.MainForm _AFT = FormType.MainForm.NULL;
    public  CyberCoreMain _plugin = CyberCoreMain.getInstance();
    public Faction _Fac = null;

    public FormType.MainForm get_FT() {
        return _FT;
    }

    public CyberForm(FormType.MainForm type) {
        _FT = type;
    }

    @Override
    public String getJSONData() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("_");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }

        });
        Gson gson = gsonBuilder.create();
        return (gson.toJson(this));
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
        if(!closed)onRun(p);


    }

    public boolean onRun(CorePlayer p) {
        if (p.Faction != null) _Fac = CyberCoreMain.getInstance().FM.FFactory.getFaction(p.Faction);
        return false;
    }

    ;
}
