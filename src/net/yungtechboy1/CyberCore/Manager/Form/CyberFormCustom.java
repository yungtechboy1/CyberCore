package net.yungtechboy1.CyberCore.Manager.Form;

import cn.nukkit.form.element.*;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.window.FormWindowCustom;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CyberFormCustom extends CyberForm {
    private final String type;
    private String title;
    private ElementButtonImageData icon;
    private List<Element> content;
    private FormResponseCustom response;
    public CyberFormCustom(FormType.MainForm ttype,String title) {
            this(ttype,title, new ArrayList());
    }

    public CyberFormCustom(FormType.MainForm ttype,String title, List<Element> contents) {
        this(ttype,title, contents, (ElementButtonImageData)null);
    }

    public CyberFormCustom(FormType.MainForm ttype,String title, List<Element> contents, String icon) {
        this(ttype,title, contents, icon.isEmpty() ? null : new ElementButtonImageData("url", icon));
    }

    public CyberFormCustom(FormType.MainForm ttype, String title, List<Element> contents, ElementButtonImageData icon) {
        super(ttype);
        this.type = "custom_form";
        this.title = "";
        this.title = title;
        this.content = contents;
        this.icon = icon;
    }
    public CyberFormCustom(String title) {
            this(title, new ArrayList());
    }

    public CyberFormCustom(String title, List<Element> contents) {
        this(title, contents, (ElementButtonImageData)null);
    }

    public CyberFormCustom(String title, List<Element> contents, String icon) {
        this(title, contents, icon.isEmpty() ? null : new ElementButtonImageData("url", icon));
    }

    public CyberFormCustom( String title, List<Element> contents, ElementButtonImageData icon) {
        super();
        this.type = "custom_form";
        this.title = "";
        this.title = title;
        this.content = contents;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Element> getElements() {
        return this.content;
    }

    public void addElement(Element element) {
        this.content.add(element);
    }

    public ElementButtonImageData getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        if (!icon.isEmpty()) {
            this.icon = new ElementButtonImageData("url", icon);
        }

    }

    public void setIcon(ElementButtonImageData icon) {
        this.icon = icon;
    }

    @Override
    public String getJSONData() {
        String toModify = super.getJSONData();
        return toModify.replace("defaultOptionIndex", "default").replace("defaultText", "default").replace("defaultValue", "default").replace("defaultStepIndex", "default");
    }

    public FormResponseCustom getResponse() {
        return this.response;
    }

    public void setResponse(String data) {
        if (data.equals("null")) {
            this.closed = true;
        } else {
            List<String> elementResponses = (List)(new Gson()).fromJson(data, (new TypeToken<List<String>>() {
            }).getType());
            int i = 0;
            HashMap<Integer, FormResponseData> dropdownResponses = new HashMap();
            HashMap<Integer, String> inputResponses = new HashMap();
            HashMap<Integer, Float> sliderResponses = new HashMap();
            HashMap<Integer, FormResponseData> stepSliderResponses = new HashMap();
            HashMap<Integer, Boolean> toggleResponses = new HashMap();
            HashMap<Integer, Object> responses = new HashMap();
            HashMap<Integer, String> labelResponses = new HashMap();

            for(Iterator var11 = elementResponses.iterator(); var11.hasNext(); ++i) {
                String elementData = (String)var11.next();
                if (i >= this.content.size()) {
                    break;
                }

                Element e = (Element)this.content.get(i);
                if (e == null) {
                    break;
                }

                if (e instanceof ElementLabel) {
                    labelResponses.put(i, ((ElementLabel)e).getText());
                    responses.put(i, ((ElementLabel)e).getText());
                } else {
                    if (e instanceof ElementDropdown) {
                        String answer = (String)((ElementDropdown)e).getOptions().get(Integer.parseInt(elementData));
                        dropdownResponses.put(i, new FormResponseData(Integer.parseInt(elementData), answer));
                        responses.put(i, answer);
                    } else if (e instanceof ElementInput) {
                        inputResponses.put(i, elementData);
                        responses.put(i, elementData);
                    } else if (e instanceof ElementSlider) {
                        Float answer2 = Float.parseFloat(elementData);
                        sliderResponses.put(i, answer2);
                        responses.put(i, answer2);
                    } else if (e instanceof ElementStepSlider) {
                        String answer3 = (String)((ElementStepSlider)e).getSteps().get(Integer.parseInt(elementData));
                        stepSliderResponses.put(i, new FormResponseData(Integer.parseInt(elementData), answer3));
                        responses.put(i, answer3);
                    } else if (e instanceof ElementToggle) {
                        Boolean answer = Boolean.parseBoolean(elementData);
                        toggleResponses.put(i, answer);
                        responses.put(i, answer);
                    }
                }
            }

            this.response = new FormResponseCustom(responses, dropdownResponses, inputResponses, sliderResponses, stepSliderResponses, toggleResponses, labelResponses);
        }
    }

    public void setElementsFromResponse() {
        if (this.response != null) {
            this.response.getResponses().forEach((i, response) -> {
                Element e = (Element)this.content.get(i);
                if (e != null) {
                    if (e instanceof ElementDropdown) {
                        ((ElementDropdown)e).setDefaultOptionIndex(((ElementDropdown)e).getOptions().indexOf(response));
                    } else if (e instanceof ElementInput) {
                        ((ElementInput)e).setDefaultText((String)response);
                    } else if (e instanceof ElementSlider) {
                        ((ElementSlider)e).setDefaultValue((Float)response);
                    } else if (e instanceof ElementStepSlider) {
                        ((ElementStepSlider)e).setDefaultOptionIndex(((ElementStepSlider)e).getSteps().indexOf(response));
                    } else if (e instanceof ElementToggle) {
                        ((ElementToggle)e).setDefaultValue((Boolean)response);
                    }
                }

            });
        }

    }

    @Override
    public void onRun(CorePlayer p){
        super.onRun(p);
    };
}
