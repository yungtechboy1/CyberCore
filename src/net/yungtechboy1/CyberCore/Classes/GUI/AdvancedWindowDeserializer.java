package net.yungtechboy1.CyberCore.Classes.GUI;

import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementStepSlider;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdvancedWindowDeserializer implements JsonDeserializer<AdvancedWindow> {

    @Override
    public AdvancedWindow deserialize(JsonElement je, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String t;
        Element[] e;

        JsonObject jsonObject = je.getAsJsonObject();
        t = jsonObject.get("Title").getAsString();
        ArrayList<Element> eeee = new ArrayList<>();
        JsonArray ee = jsonObject.getAsJsonArray("Elements");
        for(int eee = 0;eee <ee.size();eee++){
            JsonObject jo = ee.get(eee).getAsJsonObject();
            eeee.add(PharseElement(jo));
        }
//            for (int x = -size; x < size; x++) {
        new AdvancedWindow(t,e);

        return null;
    }

    public Element PharseElement(JsonObject jo){
        String jt,jid,jimg,jaction = null;
        jt = jo.get("type").getAsString();
        jid = jo.get("id").getAsString();
        jimg = jo.get("img").getAsString();
        jaction = jo.get("action").getAsString();

        switch (jt.toLowerCase()){
            case "button":
                return new ElementButton(jid);
                break;
            case "button-img":
                return new ElementButton(jid)
                break;
        }

    }
}
