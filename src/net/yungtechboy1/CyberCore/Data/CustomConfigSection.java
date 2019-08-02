package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Server;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.SimpleConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CustomConfigSection {

    public CustomConfigSection() {
    this(null);
    }
    public CustomConfigSection(ConfigSection cfg) {
        if(cfg == null)return;
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getName().equals("configFile")) continue;
            if (skipSave(field)) continue;
            String path = getPath(field);
            if (path == null) continue;
            if (path.isEmpty()) continue;
            field.setAccessible(true);
            try {
                if (field.getType() == int.class || field.getType() == Integer.class)
                    field.set(this, cfg.getInt(path, field.getInt(this)));
                else if (field.getType() == boolean.class || field.getType() == Boolean.class)
                    field.set(this, cfg.getBoolean(path, field.getBoolean(this)));
                else if (field.getType() == long.class || field.getType() == Long.class)
                    field.set(this, cfg.getLong(path, field.getLong(this)));
                else if (field.getType() == double.class || field.getType() == Double.class)
                    field.set(this, cfg.getDouble(path, field.getDouble(this)));
                else if (field.getType() == String.class)
                    field.set(this, cfg.getString(path, (String) field.get(this)));
                else if (field.getType() == ConfigSection.class)
                    field.set(this, cfg.getSection(path));
                else if (field.getType() == List.class) {
                    Type genericFieldType = field.getGenericType();
                    if (genericFieldType instanceof ParameterizedType) {
                        ParameterizedType aType = (ParameterizedType) genericFieldType;
                        Class fieldArgClass = (Class) aType.getActualTypeArguments()[0];
                        if (fieldArgClass == Integer.class) field.set(this, cfg.getIntegerList(path));
                        else if (fieldArgClass == Boolean.class) field.set(this, cfg.getBooleanList(path));
                        else if (fieldArgClass == Double.class) field.set(this, cfg.getDoubleList(path));
                        else if (fieldArgClass == Character.class) field.set(this, cfg.getCharacterList(path));
                        else if (fieldArgClass == Byte.class) field.set(this, cfg.getByteList(path));
                        else if (fieldArgClass == Float.class) field.set(this, cfg.getFloatList(path));
                        else if (fieldArgClass == Short.class) field.set(this, cfg.getFloatList(path));
                        else if (fieldArgClass == String.class) field.set(this, cfg.getStringList(path));
                    } else field.set(this, cfg.getList(path)); // Hell knows what's kind of List was found :)
                } else
                    throw new IllegalStateException("SimpleConfig did not supports class: " + field.getType().getName());
            } catch (Exception e) {
                Server.getInstance().getLogger().logException(e);
                return;
            }
        }
    }

    private boolean skipSave(Field field) {
        if (!field.isAnnotationPresent(SimpleConfig.Skip.class)) return false;
        return field.getAnnotation(SimpleConfig.Skip.class).skipSave();
    }

    private boolean skipLoad(Field field) {
        if (!field.isAnnotationPresent(SimpleConfig.Skip.class)) return false;
        return field.getAnnotation(SimpleConfig.Skip.class).skipLoad();
    }

    private String getPath(Field field) {
        String path = null;
        if (field.isAnnotationPresent(SimpleConfig.Path.class)) {
            SimpleConfig.Path pathDefine = field.getAnnotation(SimpleConfig.Path.class);
            path = pathDefine.value();
        }
        if (path == null || path.isEmpty()) path = field.getName().replaceAll("_", ".");
        if (Modifier.isFinal(field.getModifiers())) return null;
        if (Modifier.isPrivate(field.getModifiers())) field.setAccessible(true);
        return path;
    }

    public ConfigSection save() {
        ConfigSection cfg = new ConfigSection();
        for (Field field : this.getClass().getDeclaredFields()) {
            if (skipSave(field)) continue;
            String path = getPath(field);
            try {
                cfg.set(path, field.get(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
//            cfg.put(path, field.get(this));
        }
        return cfg;
    }
}
