package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Server;
import cn.nukkit.utils.ConfigSection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.yungtechboy1.CyberCore.Data.CustomSimpleConfig.getPath;
import static net.yungtechboy1.CyberCore.Data.CustomSimpleConfig.skipSave;

public abstract class CustomSimpleConfigClass {
    public CustomSimpleConfigClass() {
    }

    public CustomSimpleConfigClass(ConfigSection c) {
        if (c == null || c.isEmpty()) return;
        importcs(c);
    }

    public Object phraseFromSave(String key, Object obj) {
        System.out.println("Could not pharse FROM save" + key);
        return true;
    }

    public Object phraseToForSave(String key, Object obj) {

        System.out.println("Could not pharse for save" + key);
        return null;
    }

    public Object rawExport() {
        return export();
    }

    public ConfigSection export() {
        ConfigSection cfg = new ConfigSection();
        Class<?> p = getClass().getSuperclass();
        List<Field> f = getAllFields(this.getClass());
        if(p == null){
            System.out.println("ERROR COULD NOT FIND SUPER CLASS!@!!");
            return null;
        }else if(f.size() == 0){
            System.out.println("ERROR COULD NOT FIND FIELDS TO LOAD!!");
            return null;
        }
        System.out.println("EXXXXXX2 >>> "+f.size());
        System.out.println("EXXXXXX2 >>> "+getClass().getDeclaredFields().length);
        System.out.println("EXXXXXX2 >>> "+getClass().getSuperclass().getDeclaredFields().length);
        int vsize = f.size();
//        System.out.println("VAR3 LENGHTHHHHH "+var4);

        for (int counter = 0; counter < vsize; ++counter) {
            Field field = f.get(counter);
//            System.out.println("VAR3 NBOW ON FIELD  "+field+" ||| "+field.getName());

            if (!skipSave(field)) {

//                System.out.println("GOOOOOOOOOO");
                String path = getPath(field);

                try {
//                    if(field.getName().equalsIgnoreCase("PE")) {
//                        System.out.println("T222YRRRYYY>>>>>>> " + field + "|||||||" + (field.getType() == Enum.class || field.getClass().isAssignableFrom(Enum.class) || field.getClass().isInstance(Enum.class) || field.isEnumConstant()));
//                        System.out.println("T222YRRRYYY>>>>>>>222222222222 " + field.getClass() + "|||||||" + field.getClass().getSuperclass());
//                        System.out.println("T222YRRRYYY>>>>>>>2222222244444444442222 " +((Class<?>)field.getType()).isEnum());
//                    }
                    if (path != null) {
                        Type genericFieldType = field.getGenericType();
                        if (field.getType().isAssignableFrom(CustomSimpleConfigClass.class)) {
//                            System.out.println("!!!!!!!!!!!!!!!IS THIS BAD FOR " + field.getName());
                            CustomSimpleConfigClass c = (CustomSimpleConfigClass) field.get(this);
                            if (c == null) continue;
                            cfg.set(path, c.rawExport());
                        } else {
                            if (field.getType() == int.class || field.getType() == Integer.class || field.getType() == boolean.class || field.getType() == Boolean.class || field.getType() == long.class || field.getType() == Long.class || field.getType() == double.class || field.getType() == Double.class || field.getType() == String.class || field.getType() == ConfigSection.class)
                                cfg.set(path, field.get(this));
                            else if (((Class<?>) field.getType()).isEnum()) {
//                                System.out.println("ENUMMMM");
                                cfg.put(path, ((Enum) field.get(this)).ordinal());
                            } else if (field.getType() == List.class || genericFieldType instanceof ParameterizedType) {
//                                System.out.println("LISTRTTTTTTTTT CALLLLL");
                                if (genericFieldType instanceof ParameterizedType) {
//                                    System.out.println("PPPPPPP CALLLLL");
                                    ParameterizedType aType = (ParameterizedType) genericFieldType;
                                    Class fieldArgClass = (Class) aType.getActualTypeArguments()[0];
                                    if (fieldArgClass == Integer.class || fieldArgClass == Boolean.class || fieldArgClass == Double.class || fieldArgClass == Character.class | fieldArgClass == Byte.class || fieldArgClass == Float.class || fieldArgClass == Short.class || fieldArgClass == String.class) {
                                        cfg.set(path, field.get(this));
                                    } else if (fieldArgClass.getSuperclass() == Enum.class) {
//                                        System.out.println("EEEEEEEE CALLLLL" + fieldArgClass.isEnum());
                                        ArrayList<Integer> al = new ArrayList<>();
                                        ArrayList<? extends Enum> c = (ArrayList<? extends Enum>) field.get(this);
                                        for (Enum e : c) {
                                            al.add(e.ordinal());
                                        }
//                                        if(!al.isEmpty())cfg.put(path,al);
                                        cfg.put(path, al);
//                                        else System.out.println("WAS EMPTY LIST NOOOOO!!!!!!!!!!!");
                                    } else {
                                        System.out.println("Error Could Not Pharse List!!!!!!!!");
                                    }
                                } else {
                                    Object o = fieldArrayToObject(field.getName(), field.get(this));
                                    if (o == null) continue;
                                    cfg.set(path, o); // Hell knows what's kind of List was found :)
                                }
                            } else {
                                Object o = phraseToForSave(field.getName(), field.get(this));
                                if (o == null) continue;
                                cfg.set(path, o);
                            }
                        }
                    }
                } catch (Exception var9) {
                    return null;
                }
            }
        }
        return cfg;
    }

    private Object fieldArrayToObject(String name, Object o) {
        System.out.println("Could not pharse FOTA " + name);
        return null;
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            System.out.println("Adding to List from "+type.getName()+" AND ADDING "+c.getDeclaredFields().length);
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        System.out.println("TOTAL SIZE"+fields.size());
        return fields;
    }

    public ConfigSection importcs(ConfigSection cfg) {
//        ConfigSection cfg = new ConfigSection();
        Class<?> p = getClass().getSuperclass();
        if(p == null){
            System.out.println("ERROR COULD NOT FIND SUPER CLASS!@!!22222II");
            return null;
        }
        List<Field> f = getAllFields(this.getClass());
        System.out.println("!!!!!!!!!!!!!!! >> "+f.size());
        for (Field field : f) {
//            System.out.println("CHILD > Field > " + field.getName() + "||" + field.getType());
            if (field.getName().equals("configFile")) continue;
            if (CustomSimpleConfig.skipLoad(field)) continue;
            String path = getPath(field);
            if (path == null) continue;
            if (path.isEmpty()) continue;
            field.setAccessible(true);
            try {
                Type genericFieldType = field.getGenericType();
//                System.out.println("TYRRRYYY>>>>>>> "+field+"|||||||"+(field.getType() == Enum.class || field.getClass().isAssignableFrom(Enum.class) || field.getClass().isInstance(Enum.class)||field.isEnumConstant()));
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
                else if (((Class<?>) field.getType()).isEnum()) {
                    Object o = field.get(this);
                    int i = cfg.getInt(path);
                    Enum e = (Enum) field.getType().getEnumConstants()[i];
                    if (e == null) continue;
////                    e.getDeclaringClass().
//                    System.out.println("EMIM >> "+i);
//                    System.out.println("EMIM EEEE2222>> "+field.getType().getEnumConstants()[i]);
//                    System.out.println("EMIM EEEE>> "+e);
                    field.set(this, e);
                } else if (field.getType() == List.class || genericFieldType instanceof ParameterizedType) {
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
                        else if (fieldArgClass.getSuperclass().isAssignableFrom(CustomSimpleConfigClass.class)) {
                            ConfigSection cc = cfg.getSection(path);
                            Class cs = fieldArgClass.getClass();
                            ArrayList<CustomSimpleConfigClass> c = (ArrayList<CustomSimpleConfigClass>) createListOfType(cs);
                            if (c == null) {
                                System.out.println("2222222EROROR!@@#@!@#@! #@ !@ # !@ A S DA SD AC ++++ NUUULLL");
                                continue;
                            }

                            cc.getAllMap().forEach((key, value) -> {
                                try {
//                                    System.out.println("DONE >>>>> " + fieldArgClass.getConstructors().length);
//                                    System.out.println("DONE >>>>> " + fieldArgClass.getClass().getConstructors().length);
                                    Constructor o = fieldArgClass.getConstructor(ConfigSection.class);
//                                    System.out.println("DONE >>>>> " + o);
                                    CustomSimpleConfigClass oo = (CustomSimpleConfigClass) o.newInstance((ConfigSection) value);
                                    System.out.println("222222CS >>>> " + value);
                                    System.out.println("222222CS2 >>>> " + oo);
//                                    System.out.println("DONE >>>>> " + oo);
                                    if (oo == null) {
                                        System.out.println("222222222HUN HOW NULL!!!");
                                    } else {
//                                    c.add((CustomSimpleConfigClass) o);
                                        c.add((CustomSimpleConfigClass) oo);
                                        System.out.println("1|2222|1+ " + oo);
//                                        c.add(new ClassMerchantData(PowerEnum.MineLife));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("22222CAN NOT FORMAAAAAAAAATTTT");
                                }
                            });
                            if (c != null) {
                                System.out.println("22222FROM FIELD TO " + field.get(this));
                                field.set(this, c);
                                System.out.println("2222222SET FIELD TO " + c);
                            }
                            System.out.println("22222222End of List FOrmatttt!");
                            continue;
                        } else if ((fieldArgClass.getSuperclass() == Enum.class)) {
                            ArrayList<Integer> al = (ArrayList<Integer>) cfg.getIntegerList(path);
//                            System.out.println("CHILD > ILLLLL<>>>>  " + al);
//                            ArrayList<Class<? extends Enum<?>>> c = (ArrayList<Class<? extends Enum<?>>>)field.getType().getConstructor().newInstance();
//                            ArrayList<? extends Enum<?>> c = (ArrayList<? extends Enum<?>>)field.getType().getConstructor().newInstance();
                            ArrayList<Object> c = new ArrayList<>();
//                            ArrayList<Object> c = (ArrayList) field.getType().getConstructor().newInstance();
//                            c = createListOfType(field.getType());


                            if (c == null) System.out.println("WHYYYYYYYYYYYYYYYYYYYYYY");
//                            fieldArgClass.getConstructor().newInstance();
//                            Set<? extends Enum> zc = new Set;
                            for (Integer e : al) {
//                                System.out.println("CHILD > INT > " + e + " ||| " + fieldArgClass.getEnumConstants()[e]);
//                                System.out.println("|1|>>> "+(Object)fieldArgClass.getEnumConstants()[e]);
//                                System.out.println("|1|>>> "+(Enum)fieldArgClass.getEnumConstants()[e]);
//                                System.out.println("|1|>>> "+(Enum<?>)fieldArgClass.getEnumConstants()[e]);
//                                System.out.println("|2|>>> "+(Class<? extends Enum<?>>)fieldArgClass.getEnumConstants()[e]);
                                c.add((Enum) fieldArgClass.getEnumConstants()[e]);
                            }
//                            System.out.println("field name " + field.getName());
//                            System.out.println("C>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + c);
//                            System.out.println("C>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + c.getClass());
//                            System.out.println("C>>>>>>>>>>>>>>>>>>>>>>>>>>>>|||" + field.getType());
//                            System.out.println("C>>>>>>>>>>>>>>>>>>>>>>>>>>>>|||" + field.get(this));
//                            if(!c.isEmpty())field.set(this,c);
//                            ((ArrayList)field.get(this)).addAll(c);
                            if (field.get(this) == null) {
                                System.out.println("CLIDL> WAS NULL");
                                field.set(this, c);
                                if (field.get(this) == null){
                                    System.out.println("CLIDL> WAS NULL!!!!!!!!!!!!");
                                    setField(this,field.getName(),c);
                                    setField(this.getClass().getSuperclass(),field.getName(),c);
                                    if (field.get(this) == null)System.out.println("CLIDL> BARRRRRRRRRRRRRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA WAS NULL!!!!!!!!!!!!");
                                }
                                field.set(this, c);
                            }else
                                field.set(this, c);
                            System.out.println("2221111122222C>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + field.get(this));
                            break;
//                            System.out.println("C>>>>>>>>>>>>>>>>>>>>>>>>>>>>|||"+field.get(this).getClass());
//                            System.out.println("2C>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+);
                        } else {
                            System.out.println("Error Could Not Pharse List!!!!!!!!");
                        }

//                        System.out.println("OUT PRAM >>>2 "+fieldArgClass.getName()+"||||"+fieldArgClass.getTypeName());
                        System.out.println("OUT PRAM >>>3 " + fieldArgClass + "||||" + (fieldArgClass.getSuperclass() == Enum.class));
//                        System.out.println("OUT PRAM >>>4 "+fieldArgClass.toString()+"||||"+fieldArgClass.isAssignableFrom(Enum.class)+"||||"+fieldArgClass.isInstance(Enum.class));
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
                return null;
            }
        }

        return cfg;
    }

    public static <T> List<T> createListOfType(Class<T> type) {
        return new ArrayList<T>();
    }

    private Object pharseList(String name, Object o) {
        System.out.println("Could not pharse List " + name);
        return null;
    }

    /**
     * Sets a field value on a given object
     *
     * @param targetObject the object to set the field value on
     * @param fieldName    exact name of the field
     * @param fieldValue   value to set on the field
     * @return true if the value was successfully set, false otherwise
     */
    public static boolean setField(Object targetObject, String fieldName, Object fieldValue) {
        Field field;
        try {
            field = targetObject.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        Class superClass = targetObject.getClass().getSuperclass();
        while (field == null && superClass != null) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }
        if (field == null) {
            return false;
        }
        field.setAccessible(true);
        try {
            field.set(targetObject, fieldValue);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
