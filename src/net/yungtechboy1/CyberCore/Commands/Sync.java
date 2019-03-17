package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Tasks.SendInvTask;

import java.util.Map;

/**
 * Created by carlt_000 on 2/3/2017.
 */
public class Sync extends Command {
    CyberCoreMain Owner;

    public Sync(CyberCoreMain server) {
        super("sync", "Sync your inv", "/sync");
        Owner = server;
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.player");
    }
    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        CyberCoreMain server = Owner;
        if (s instanceof Player) {
            Player p = (Player) s;
            Map<Integer, Item>  a = p.getInventory().getContents();
            p.getInventory().clearAll();
            Owner.getServer().getScheduler().scheduleDelayedTask(new SendInvTask(Owner,p,a),20);
            //p.getInventory().sendHeldItem(p);
            p.sendMessage(TextFormat.GREEN+"Sync Completed!! Items Resending in as Sec!");
        }
        return true;
    }
}