package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.Item;

public enum ModifiedMaterial {
    Wood(Item.WOODEN_SWORD, "Wood"),
    FireStone(Item.WOODEN_SWORD, "Wood"),//Burn Damage
    IcedStone(Item.WOODEN_SWORD, "Wood"),//Freeze Opponents
    Gold(Item.GOLD_SWORD, "Wood"),
    Iron(Item.IRON_SWORD, "Wood"),
    Steel(Item.IRON_SWORD, "Wood"),
    Diamond(Item.DIAMOND_SWORD, "Wood"),
    Safire(Item.DIAMOND_SWORD, "Wood");

    public int getId() {
        return i;
    }

    private int i;
    private String n;

    private ModifiedMaterial(int id, String name) {
        i = id;
        n = name;
    }
}
