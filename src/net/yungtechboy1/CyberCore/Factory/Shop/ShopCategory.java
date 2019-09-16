package net.yungtechboy1.CyberCore.Factory.Shop;

public enum ShopCategory {
    Food, NA;

    public static ShopCategory getFromString(String ccc) {
        for(ShopCategory s:values()){
            if(s.name().equalsIgnoreCase(ccc)){
                return s;
            }
        }
        return null;
    }
}
