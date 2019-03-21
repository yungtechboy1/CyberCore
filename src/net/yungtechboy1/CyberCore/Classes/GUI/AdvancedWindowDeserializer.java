package net.yungtechboy1.CyberCore.Classes.GUI;

import cn.nukkit.form.element.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdvancedWindowDeserializer implements JsonDeserializer<AdvancedWindow> {

    @Override
    public AdvancedWindow deserialize(JsonElement je, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String t;
        Element[] e;

        JsonObject jsonObject = je.getAsJsonObject();
        t = jsonObject.get("Title").getAsString();
        ArrayList<Element> eeee = new ArrayList<>();
        JsonArray ee = jsonObject.getAsJsonArray("Elements");
        for (int eee = 0; eee < ee.size(); eee++) {
            JsonObject jo = ee.get(eee).getAsJsonObject();
            eeee.add(PharseElement(jo));
        }
//            for (int x = -size; x < size; x++) {
        new AdvancedWindow(t, eeee);

        return null;
    }

    public Element PharseElement(JsonObject jo) {
        String jt, jid, jimg, jaction = null;
        jt = jo.get("type").getAsString();
        jid = jo.get("id").getAsString();
        jimg = jo.get("img").getAsString();
        jaction = jo.get("action").getAsString();
        Object jdefault;
        switch (jt.toLowerCase()) {
            case "dropdown":
                /**
                 * {
                 *      default: 0
                 *      values: Array String
                 * }
                 */
                ElementDropdown ed = new ElementDropdown(jid);
                jdefault = 0;
                if (jo.has("default")) jdefault = jo.get("default").getAsInt();
                JsonArray jd = jo.getAsJsonArray("values");
                ArrayList<String> jl = new ArrayList<>();
                for (int i = 0; i < jd.size(); i++) {
                    ed.addOption(jd.get(i).getAsString());
                }
                ed.setDefaultOptionIndex((int) jdefault);
                return ed;
            case "Input":
                ElementInput ei = new ElementInput(jid);
                String jtext = jo.get("text").getAsString();
                String jplaceholder = jo.get("placeholder").getAsString();
                jdefault = jo.get("default").getAsString();
                ei.setText(jtext);
                ei.setPlaceHolder(jplaceholder);
                ei.setDefaultText((String) jdefault);
                return ei;
            case "Label":
                ElementLabel el = new ElementLabel(jid);
                return el;
            case "Slider":
                float jmin = jo.get("min").getAsFloat();
                float jmax = jo.get("max").getAsFloat();
                int jstep = jo.get("step").getAsInt();
                jdefault = jo.get("default").getAsFloat();
                return new ElementSlider(jid, jmin, jmax, jstep, (float) jdefault);
            case "StepSlider":
                float jmin1 = jo.get("min").getAsFloat();
                float jmax1 = jo.get("max").getAsFloat();
                int jstep1 = jo.get("step").getAsInt();
                jdefault = jo.get("default").getAsFloat();
                JsonArray jd1 = jo.getAsJsonArray("values");
                ElementStepSlider ess = new ElementStepSlider(jid, new ArrayList<>(), (int) jdefault);
                for (int i = 0; i < jd1.size(); i++) ess.addStep(jd1.get(i).getAsString());
                return ess;
            case "Toggle":
                ElementToggle et = new ElementToggle(jid);
                jdefault = false;
                if (jo.has("default")) jdefault = jo.get("default").getAsBoolean();
                et.setDefaultValue((boolean) jdefault);
                return et;
            default:
                return null;
        }

    }

    /**
     * @param jo
     * @return ElementButton | ElementButtonImageData
     */
    public Object PharseButtonElement(JsonObject jo) {
        String jt, jid, jimg, jaction = null;
        jt = jo.get("type").getAsString();
        jid = jo.get("id").getAsString();
        jimg = jo.get("img").getAsString();
        jaction = jo.get("action").getAsString();

        switch (jt.toLowerCase()) {
            case "button":
                return new ElementButton(jid);
            case "button-img":
                String jurl = jo.get("action").getAsString();
                return new ElementButtonImageData(jid, jurl);
            default:
                return null;
        }
    }
}
