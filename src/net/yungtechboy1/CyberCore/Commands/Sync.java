package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Utils;

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
            p.getInventory().sendContents(p);
            p.getInventory().setContents(a);
            p.getInventory().sendContents(p);
            p.getInventory().sendArmorContents(p);
            p.getInventory().sendHeldItem(p);
            //p.getInventory().sendHeldItem(p);
            p.sendMessage("Sync Completed!!");
        }
        return true;
    }
}