package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.SimpleConfig;

import java.io.File;

public class MMOConfig extends SimpleConfig {
    public MMOConfig(Plugin plugin) {
        super(plugin, new File(plugin.getDataFolder(), "MMOSave.yml").toString());
    }

    public ConfigSection Map = new ConfigSection();
}
