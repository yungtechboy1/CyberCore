package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.SimpleConfig;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public abstract class CustomSimpleConfig{

    private final File configFile;
    public HashMap<Type,Class> customtypetoclass = new HashMap<>();


    public CustomSimpleConfig(Plugin plugin) {
        this(plugin, "config.yml");
    }

    public CustomSimpleConfig(Plugin plugin, String fileName) {
        this(new File(plugin.getDataFolder() + File.separator + fileName));
    }

    public CustomSimpleConfig(File file) {
        this.configFile = file;
        configFile.getParentFile().mkdirs();
    }

    public boolean save() {
        return save(false);
    }

    public boolean save(boolean async) {
        if (configFile.exists()) try {
            configFile.createNewFile();
        } catch (Exception e) {
            return false;
        }
        Config cfg = new Config(configFile, Config.YAML);
        for (Field field : this.getClass().getDeclaredFields()) {
            if (skipSave(field)) continue;
            String path = getPath(field);
            try {
                if (path != null) cfg.set(path, field.get(this));
            } catch (Exception e) {
                return false;
            }
        }
        cfg.save(async);
        return true;
    }

    public abstract ConfigSection toConfigSection();

    public boolean load() {
        if (!this.configFile.exists()) return false;
        Config cfg = new Config(configFile, Config.YAML);
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
                else if (field.getType().isAssignableFrom(CustomSimpleConfigClass.class)) {
                    ConfigSection c = cfg.getSection(path);
                    if(c.isEmpty() ||c.size() == 0|| c == null){

                    }

                    field.set(this,cfg.getSection(path));
                }else if (customtypetoclass.containsKey(field.getType())) {//Why Implement?

                }
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
                    throw new IllegalStateException("CustomSimpleConfig did not supports class: " + field.getType().getName() + " for config field " + configFile.getName());
            } catch (Exception e) {
                Server.getInstance().getLogger().logException(e);
                return false;
            }
        }
        return true;
    }

    private String getPath(Field field) {
        String path = null;
        if (field.isAnnotationPresent(cn.nukkit.utils.SimpleConfig.Path.class)) {
            cn.nukkit.utils.SimpleConfig.Path pathDefine = field.getAnnotation(cn.nukkit.utils.SimpleConfig.Path.class);
            path = pathDefine.value();
        }
        if (path == null || path.isEmpty()) path = field.getName().replaceAll("_", ".");
        if (Modifier.isFinal(field.getModifiers())) return null;
        if (Modifier.isPrivate(field.getModifiers())) field.setAccessible(true);
        return path;
    }

    private boolean skipSave(Field field) {
        if (!field.isAnnotationPresent(cn.nukkit.utils.SimpleConfig.Skip.class)) return false;
        return field.getAnnotation(cn.nukkit.utils.SimpleConfig.Skip.class).skipSave();
    }

    private boolean skipLoad(Field field) {
        if (!field.isAnnotationPresent(cn.nukkit.utils.SimpleConfig.Skip.class)) return false;
        return field.getAnnotation(cn.nukkit.utils.SimpleConfig.Skip.class).skipLoad();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Path {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Skip {
        boolean skipSave() default true;

        boolean skipLoad() default true;
    }
}