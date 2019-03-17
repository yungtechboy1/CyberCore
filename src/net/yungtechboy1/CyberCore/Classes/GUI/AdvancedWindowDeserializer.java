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
        new AdvancedWindow(t, e);

        return null;
    }

    public Element PharseElement(JsonObject jo) {
        String jt, jid, jimg, jaction = null;
        jt = jo.get("type").getAsString();
        jid = jo.get("id").getAsString();
        jimg = jo.get("img").getAsString();
        jaction = jo.get("action").getAsString();

        switch (jt.toLowerCase()) {
            case "dropdown":
                /**
                 * {
                 *      default: 0
                 *      values: Array String
                 * }
                 */
                ElementDropdown ed = new ElementDropdown(jid);
                Integer jdef = 0;
                if (jo.has("default")) jdef = jo.get("default").getAsInt();
                JsonArray jd = jo.getAsJsonArray("values");
                ArrayList<String> jl = new ArrayList<>();
                for (int i = 0; i < jd.size(); i++) {
                    ed.addOption(jd.get(i).getAsString());
                }
                ed.setDefaultOptionIndex(jdef);
                return ed;
            case "Input":
                ElementInput ei = new ElementInput(jid);
                String jtext = jo.get("text").getAsString();
                String jplaceholder = jo.get("placeholder").getAsString();
                String jdefault = jo.get("default").getAsString();
                ei.setText(jtext);
                ei.setPlaceHolder(jplaceholder);
                ei.setDefaultText(jdefault);
                return ei;
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
