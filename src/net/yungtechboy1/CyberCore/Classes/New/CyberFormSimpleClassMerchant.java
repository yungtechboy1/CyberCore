package net.yungtechboy1.CyberCore.Classes.New;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.List;

public class CyberFormSimpleClassMerchant extends CyberFormSimple {
    public CyberFormSimpleClassMerchant(FormType.MainForm ttype, String title) {
        super(ttype, title);
    }

    public CyberFormSimpleClassMerchant(FormType.MainForm ttype, String title, String content) {
        super(ttype, title, content);
    }

    public CyberFormSimpleClassMerchant(FormType.MainForm ttype, String title, String content, List<ElementButton> buttons) {
        super(ttype, title, content, buttons);
    }

    @Override
    public String getJSONData() {
        return null;
    }

    @Override
    public void setResponse(String s) {

    }

    @Override
    public FormResponseSimple getResponse() {
        return null;
    }
}
