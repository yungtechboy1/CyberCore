package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.Item;

public class CustomItemElement extends Item {
    public CustomItemElement() {
        super(-12);
    }

    public CustomItemElement( Integer meta) {
        super(-12, meta);
    }

    public CustomItemElement(Integer meta, int count) {
        super(-12, meta, count);
    }

    public CustomItemElement(Integer meta, int count, String name) {
        super(-12, meta, count, name);
        setCustomName(name+meta);
    }
}
