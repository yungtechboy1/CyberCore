package net.yungtechboy1.CyberCore.Manager.Form;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import com.google.gson.Gson;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;

import java.util.ArrayList;
import java.util.List;

public class CyberFormSimple extends CyberForm {
    private final String type;
    private String title;
    private String content;
    private List<ElementButton> buttons;
    private FormResponseSimple response;

    public CyberFormSimple(FormType.MainForm ttype, String title) {
        this(ttype,title,"");
    }
    public CyberFormSimple(FormType.MainForm ttype, String title, String content) {
        this(ttype, title, content, new ArrayList());
    }

    public CyberFormSimple(FormType.MainForm ttype, String title, String content, List<ElementButton> buttons) {
        super(ttype);
        this.type = "form";
        this.title = "";
        this.content = "";
        this.response = null;
        this.title = title;
        this.content = content;
        this.buttons = buttons;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ElementButton> getButtons() {
        return this.buttons;
    }

    public void addButton(ElementButton button) {
        this.buttons.add(button);
    }

    @Override
    public FormResponseSimple getResponse() {
        return response;
    }

    @Override
    public void setResponse(String data) {
        if (data.equals("null")) {
            this.closed = true;
        } else {
            int buttonID;
            try {
                buttonID = Integer.parseInt(data);
            } catch (Exception var4) {
                return;
            }

            if (buttonID >= this.buttons.size()) {
                this.response = new FormResponseSimple(buttonID, (ElementButton) null);
            } else {
                this.response = new FormResponseSimple(buttonID, (ElementButton) this.buttons.get(buttonID));
            }
        }
    }

    @Override
    public void onRun(CorePlayer p) {
       super.onRun(p);
    }

    ;
}
