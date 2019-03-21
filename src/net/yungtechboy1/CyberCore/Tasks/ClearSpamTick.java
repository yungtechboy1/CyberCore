package net.yungtechboy1.CyberCore.Tasks;

/**
 * Created by carlt_000 on 1/22/2017.
 */

import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.HashMap;

public class ClearSpamTick extends PluginTask<CyberCoreMain> {
    public ClearSpamTick(CyberCoreMain owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        this.getOwner().Spam = new HashMap<>();
    }
}
