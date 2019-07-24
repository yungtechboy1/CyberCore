package net.yungtechboy1.CyberCore.Manager.Crate.Tasks;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 1/17/2017.
 */
public class SendItem extends Task {

    private CrateMain main;
    private ConfigSection data;

    public SendItem(CrateMain main, ConfigSection data) {
        this.main = main;
        this.data = data;
    }

    @Override
    public void onRun(int i) {
        ConfigSection b = data;
        int slot = b.getInt("slot");
        String pn = b.getString("PlayerName").toLowerCase();
        Vector3 pos = (Vector3) b.get("pos");
        ArrayList<Item> pis = (ArrayList<Item>) b.get("possible-items");
        if (pis == null) return;
        Item item = pis.get(slot);

        //Delete the Floating Entity
        if (this.main.eids.containsKey(pn)) {
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.eid = this.main.eids.get(pn);
        }

        //Add Item to players Inv!
        Player p = main.CCM.getServer().getPlayerExact(pn);
        if (p != null) {
            p.getInventory().addItem(item);
            p.sendMessage("ITEM ADDED!");
            if (this.main.eids.containsKey(pn)) {
                RemoveEntityPacket pk = new RemoveEntityPacket();
                long eid = this.main.eids.get(pn);
                pk.eid = eid;
                p.dataPacket(pk);
            }
            main.hideCrate(pos, p);
        }
    }
}
