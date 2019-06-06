package net.yungtechboy1.CyberCore.Manager.Form;

import cn.nukkit.form.response.FormResponseModal;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;

public class CyberFormModal extends CyberForm {
    private final String type = "modal";
    private String title = "";
    private String content = "";
    private String button1 = "";
    private String button2 = "";
    private FormResponseModal response = null;

    public CyberFormModal(FormType.MainForm ttype,String title, String content, String trueButtonText, String falseButtonText) {
        super(ttype);
        this.title = title;
        this.content = content;
        this.button1 = trueButtonText;
        this.button2 = falseButtonText;
    }
    @Deprecated
    private CyberFormModal(String title, String content, String trueButtonText, String falseButtonText) {
        super(null);
        this.title = title;
        this.content = content;
        this.button1 = trueButtonText;
        this.button2 = falseButtonText;
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

    public String getButton1() {
        return this.button1;
    }

    public void setButton1(String button1) {
        this.button1 = button1;
    }

    public String getButton2() {
        return this.button2;
    }

    public void setButton2(String button2) {
        this.button2 = button2;
    }

    public FormResponseModal getResponse() {
        return this.response;
    }

    @Override
    public void setResponse(String data) {
        if (data.equals("null")) {
            this.closed = true;
        } else {
            if (data.equals("true")) {
                this.response = new FormResponseModal(0, this.button1);
            } else {
                this.response = new FormResponseModal(1, this.button2);
            }

        }
    }


    @Override
    public boolean onRun(CorePlayer p){
        super.onRun(p);
        return false;
    };
}
