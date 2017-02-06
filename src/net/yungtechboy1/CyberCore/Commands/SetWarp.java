package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/1/2017.
 */
public class SetWarp extends Command {
    private String Prefix = TextFormat.AQUA + "[TerraTP]";
    CyberCoreMain Owner;

    public SetWarp(CyberCoreMain server) {
        super("setwarp", "Set Warps", "/setwarp [key]");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("key", CommandParameter.ARG_TYPE_STRING, true)
        });
        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        Player pp = (Player) s;
        if (args.length != 1 || pp == null) return false;
        pp.sendMessage("warp." + args[0]);
        if (!Owner.MainConfig.exists("warp." + args[0])) {
            try {
                String v = pp.getX() + "&" + pp.getY() + "&" + pp.getZ() + "&" + pp.getLevel().getFolderName();
                Owner.MainConfig.set("warp." + args[0].toLowerCase(), v);
                pp.sendMessage(Prefix + " Warp set!");
            } catch (Exception e) {
                return false;
            }
        } else {
            pp.sendMessage(Prefix + TextFormat.RED + " Error that Warp already exists");
        }
        return true;
    }
}