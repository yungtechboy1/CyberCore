package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;

import java.security.PublicKey;

/**
 * Created by carlt on 2/14/2019.
 */
public class FloatingTextContainer {
    public String Syntax;
    public Boolean PlayerUnique = false;
    public int UpdateTicks = 20;
    public int LastUpdate = 0;
    public int Range = 64;
    public Position Pos;
    public Level Lvl;

    public ConfigSection GetSave() {
        //todo
        return new ConfigSection() {{
            put("Syntax", Syntax);
            put("PlayerUnique", PlayerUnique);
            put("UpdateTicks", UpdateTicks);
            put("LastUpdate", LastUpdate);
        }};
    }
}