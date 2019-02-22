package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/1/2017.
 */
public class GetFactions extends PluginTask<CyberCoreMain> {
    public GetFactions(CyberCoreMain main) {
        super(main);
    }

    @Override
    public void onRun(int i) {
        Plugin plug = getOwner().getServer().getPluginManager().getPlugin("CyberFaction");
        if (!(plug instanceof FactionsMain)) {
            //setEnabled(false);
            getOwner().getLogger().error("Error Factions Not Found!");
            getOwner().setEnabled(false);
        }
        getOwner().FM = (FactionsMain) plug;
        if (getOwner().isEnabled()) {
            getOwner().getServer().getScheduler().scheduleDelayedRepeatingTask(new UpdateFloatingTextTask(getOwner()), 20 * 15, 20 * 15);
            getOwner().FTM.ReloadDynamicText();
        }
    }
}
