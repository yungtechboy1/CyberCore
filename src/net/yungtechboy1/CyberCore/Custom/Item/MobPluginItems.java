/**
 * MobPluginItems.java
 * 
 * Created on 15:36:32
 */
package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.ItemEdible;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz (kniffo80)</a>
 */
public abstract class MobPluginItems extends ItemEdible {

    public static final int ENDER_PEARL   = 87;

    public static final int INK_SAC       = 351;

    public static final int RAW_MUTTON    = 423;

    public static final int COOKED_MUTTON = 424;

    public MobPluginItems(int networkId, Integer meta, int count, String name) {
        super(networkId, meta, count, name);
    }

}
