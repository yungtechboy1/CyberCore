package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class CustomSimpleConfig {

    private final File configFile;
    public HashMap<Type, Class> customtypetoclass = new HashMap<>();


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

    public static String getPath(Field field) {
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

    public static boolean skipSave(Field field) {
        if (!field.isAnnotationPresent(cn.nukkit.utils.SimpleConfig.Skip.class)) return false;
        return field.getAnnotation(cn.nukkit.utils.SimpleConfig.Skip.class).skipSave();
    }

//    public abstract ConfigSection toConfigSection();

    public static boolean skipLoad(Field field) {
        if (!field.isAnnotationPresent(cn.nukkit.utils.SimpleConfig.Skip.class)) return false;
        return field.getAnnotation(cn.nukkit.utils.SimpleConfig.Skip.class).skipLoad();
    }

    public static <T> List<T> createListOfType(Class<T> type) {
        return new ArrayList<T>();
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
            try {
                System.out.println("SAVING " + field.getName() + "||" + field.get(this) + "||" + field);
//                System.out.println("SAVING >>>>>> "+field.getType().isAssignableFrom(CustomSimpleConfigClass.class));
//                System.out.println("SAVING >>>>>> "+field.getType().getClass().isAssignableFrom(CustomSimpleConfigClass.class));
//                System.out.println("SAVING >>>>>> "+field.getType().isInstance(CustomSimpleConfigClass.class));
//                System.out.println("SAVING >>>>>> "+field.getType().getClass().isInstance(CustomSimpleConfigClass.class));
//                System.out.println("SAVING >>>>>> "+field.getType().getSuperclass().isInstance(CustomSimpleConfigClass.class));
//                System.out.println("SAVING >>>>>> "+field.getType().getSuperclass().isAssignableFrom(CustomSimpleConfigClass.class));
            } catch (Exception e) {
                System.out.println("SAVING " + field.getName() + "||||" + field);
            }
            if (skipSave(field)) continue;
            String path = getPath(field);
            try {
                Type genericFieldType = field.getGenericType();
                if (field.getType().isAssignableFrom(CustomSimpleConfigClass.class)) {
                    System.out.println("SAVING AS CSCC");
                    CustomSimpleConfigClass c = (CustomSimpleConfigClass) field.get(this);
                    if (c == null) continue;
                    cfg.set(path, c.rawExport());
                } else {
                    if (field.getType() == int.class || field.getType() == Integer.class || field.getType() == boolean.class || field.getType() == Boolean.class || field.getType() == long.class || field.getType() == Long.class || field.getType() == double.class || field.getType() == Double.class || field.getType() == String.class || field.getType() == ConfigSection.class) {

                        System.out.println("SAVING AS EZ");
                        cfg.set(path, field.get(this));
                    } else if (field.getType() == List.class || genericFieldType instanceof ParameterizedType) {
                        System.out.println("SAVING AS LIST");
                        if (genericFieldType instanceof ParameterizedType) {
                            System.out.println("SAVING AS PRAMMMMMM");
                            ParameterizedType aType = (ParameterizedType) genericFieldType;
                            Class fieldArgClass = (Class) aType.getActualTypeArguments()[0];
                            if (fieldArgClass == Integer.class || fieldArgClass == Boolean.class || fieldArgClass == Double.class || fieldArgClass == Character.class | fieldArgClass == Byte.class || fieldArgClass == Float.class || fieldArgClass == Short.class || fieldArgClass == String.class) {

                                System.out.println("SAVING AS QQQQQQ");
                                cfg.set(path, field.get(this));
                            } else if (fieldArgClass.getClass().isInstance(CustomSimpleConfigClass.class)) {
                                System.out.println("SAVING AS SCCCCCCC");
                                int k = 0;
                                ConfigSection c = new ConfigSection();
                                ArrayList<CustomSimpleConfigClass> cc = (ArrayList<CustomSimpleConfigClass>) field.get(this);
//                                field.getType().getConstructor(ConfigSection.class).newInstance()
                                if (cc == null) {
                                    System.out.println("ERROR!!!!! >>>>>>>>" + field.getType() + "||" + field.get(this));
                                    continue;
                                }
                                for (CustomSimpleConfigClass ccc : cc) {
                                    Object o = ccc.rawExport();
                                    System.out.println("RAW EXPORT >>"+o);
                                    if (o != null) c.put(k++ + "", o);
                                }
                                if (c != null && !c.isEmpty()) cfg.set(path, c);
                            } else if((fieldArgClass.getSuperclass() == Enum.class)){
                                System.out.println("SAVING AS EEEEEECCCCCCC");
                                ArrayList<Integer> al = (ArrayList<Integer>) cfg.getIntegerList(path);
                                System.out.println("2222ILLLLL<>>>>  "+al);
//                            ArrayList<Class<? extends Enum<?>>> c = (ArrayList<Class<? extends Enum<?>>>)field.getType().getConstructor().newInstance();
//                            ArrayList<? extends Enum<?>> c = (ArrayList<? extends Enum<?>>)field.getType().getConstructor().newInstance();
                                ArrayList<Enum<?>> c = (ArrayList<Enum<?>>)field.getType().getConstructor().newInstance();


                                if(c == null)System.out.println("22222222WHYYYYYYYYYYYYYYYYYYYYYY");
//                            fieldArgClass.getConstructor().newInstance();
//                            Set<? extends Enum> zc = new Set;
                                for(Integer e: al) {
                                    Class cc = fieldArgClass.getClass();
//                                System.out.println("|1|>>> "+(Object)fieldArgClass.getEnumConstants()[e]);
//                                System.out.println("|1|>>> "+(Enum)fieldArgClass.getEnumConstants()[e]);
//                                System.out.println("|1|>>> "+(Enum<?>)fieldArgClass.getEnumConstants()[e]);
//                                System.out.println("|2|>>> "+(Class<? extends Enum<?>>)fieldArgClass.getEnumConstants()[e]);
                                    c.add((Enum<?>) fieldArgClass.getEnumConstants()[e]);
                                }
                                System.out.println("1111field name "+field.getName());
                                System.out.println("2222C>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+c);
//                            if(!c.isEmpty())field.set(this,c);
                                field.set(this,c);
                                System.out.println("33332C>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+field.get(this));
//                            System.out.println("2C>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+);
                            }else{
                                System.out.println("111111Error Could Not Pharse List!!!!!!!!");
                            }
                        } else {
                            Object o = fieldArrayToObject(field.getName(), field.get(this));
                            if (o == null) continue;
                            cfg.set(path, o); // Hell knows what's kind of List was found :)
                        }
                    } else {
                        System.out.println("SAVING AS RO");
                        Object o = phraseToForSave(field.getName(), field.get(this));
                        if (o == null) continue;
                        System.out.println("PAST SABVING AS RO || " + o);
                        cfg.set(path, o);
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        cfg.save(async);
        return true;
    }

    public boolean load() {
        if (!this.configFile.exists()) return false;
        Config cfg = new Config(configFile, Config.YAML);
        for (Field field : this.getClass().getDeclaredFields()) {
            System.out.println("PARENT > Field > "+field.getName());
            System.out.println("LOADING FOR " + field);
            if (field.getName().equals("configFile")) continue;
            if (skipLoad(field)) continue;
            String path = getPath(field);
            if (path == null) continue;
            if (path.isEmpty()) continue;
            field.setAccessible(true);
            try {
                Type genericFieldType = field.getGenericType();
//                System.out.println("NOW TRY 2");
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
                else if (field.getType() == List.class || genericFieldType instanceof ParameterizedType) {
//                    System.out.println("IS LIST 1");
                    if (genericFieldType instanceof ParameterizedType) {
//                        System.out.println("IS PRAM 1");
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
                        else if ((fieldArgClass.getSuperclass() == Enum.class)) {
                            ArrayList<Integer> al = (ArrayList<Integer>) cfg.getIntegerList(path);
                            ArrayList<Enum<?>> c = (ArrayList<Enum<?>>) field.getType().getConstructor().newInstance();


                            if (c == null) System.out.println("WHYYYYYYYYYYYYYYYYYYYYYY");
                            for (Integer e : al) {
                                System.out.println("Loading Enum >>> "+fieldArgClass.getEnumConstants()[e]);
                                c.add((Enum<?>) fieldArgClass.getEnumConstants()[e]);
                            }
//                            System.out.println("TOTAL  Enum SIZEWEEE>>> "+c.size());
//                            System.out.println("C>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + c);
                            if (!c.isEmpty()) field.set(this, c);
//                            System.out.println("2C>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + field.get(this));
                        } else if (fieldArgClass.getSuperclass().isAssignableFrom(CustomSimpleConfigClass.class)) {
                            ConfigSection cc = cfg.getSection(path);
                            Class cs = fieldArgClass.getClass();
                            ArrayList<CustomSimpleConfigClass> c = (ArrayList<CustomSimpleConfigClass>) createListOfType(cs);
                            if (c == null) {
                                System.out.println("EROROR!@@#@!@#@! #@ !@ # !@ A S DA SD AC ++++ NUUULLL");
                                continue;
                            }

                            cc.getAllMap().forEach((key, value) -> {
                                try {
//                                    System.out.println("DONE >>>>> " + fieldArgClass.getConstructors().length);
//                                    System.out.println("DONE >>>>> " + fieldArgClass.getClass().getConstructors().length);
                                    Constructor o = fieldArgClass.getConstructor(ConfigSection.class);
//                                    System.out.println("DONE >>>>> " + o);
                                    CustomSimpleConfigClass oo = (CustomSimpleConfigClass)o.newInstance((ConfigSection) value);
//                                    System.out.println("CS >>>> "+value);
//                                    if(!((ClassMerchantData)oo).getAllowedToPurchase().contains(ClassType.Unknown))((ClassMerchantData)oo).getAllowedToPurchase().add(ClassType.Unknown);
//                                    System.out.println("CS2 >>>> "+oo);
//                                    System.out.println("DONE >>>>> " + oo);
                                    if(oo == null){
                                        System.out.println("HUN HOW NULL!!!");
                                    }else {
//                                    c.add((CustomSimpleConfigClass) o);
                                        c.add((CustomSimpleConfigClass)oo);
                                        System.out.println("1||1+ "+oo);
//                                        c.add(new ClassMerchantData(PowerEnum.MineLife));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("CAN NOT FORMAAAAAAAAATTTT");
                                }
                            });
                            if(c != null){
//                                System.out.println("FROM FIELD TO "+field.get(this));
                                field.set(this,c);
//                                System.out.println("SET FIELD TO "+c);
                            }
                            System.out.println("End of List FOrmatttt!");
                            continue;

                        } else {
                            System.out.println("ERRPR NO CLASS FOUND!!!!!!!!!!!!!!!!!!!!!!!!!!sasda11" + fieldArgClass);
                            System.out.println("ERRPR NO CLASS FOUND!!!!!!!!!!!!!!!!!!!!!!!!!!sasda211" + fieldArgClass.getSuperclass());
                            System.out.println("ERRPR NO CLASS FOUND!!!!!!!!!!!!!!!!!!!!!!!!!!sasda3311" + fieldArgClass.getClass());
                        }
                    } else {
                        Object o = pharseList(field.getName(), cfg.get(path));
                        if (o == null) continue;
                        field.set(this, o); // Hell knows what's kind of List was found :)
                    }
                } else if (field.getType().isAssignableFrom(CustomSimpleConfigClass.class)) {
                    ConfigSection c = cfg.getSection(path);
                    if (c.isEmpty() || c.size() == 0 || c == null) {
                        continue;
                    }
                    field.set(this, field.getType().getConstructor(ConfigSection.class).newInstance(c));
//                } else if (customtypetoclass.containsKey(field.getType())) {//Why Implement?
//                    field.set(this, cfg.get(path));
                } else {
                    Object o = phraseFromSave(field.getName(), cfg.get(path));
                    if (o == null) continue;
                    field.set(this, o);
                }
            } catch (Exception e) {
                Server.getInstance().getLogger().logException(e);
                return false;
            }
        }
        return true;
    }

    private Object pharseList(String name, Object o) {
        System.out.println("111Could not pharse List " + name);
        return null;
    }

    private Object fieldArrayToObject(String name, Object o) {
        System.out.println("111111Could not pharse FOTA " + name);
        return null;
    }

    public Object phraseFromSave(String key, Object obj) {
        System.out.println("1111Could not pharse FROM save " + key);
        return null;
    }

    public Object phraseToForSave(String key, Object obj) {

        System.out.println("11ssCould not pharse for save " + key);
        return null;
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