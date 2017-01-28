package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class Hud extends Command {
    CyberCoreMain Owner;

    public Hud(CyberCoreMain server) {
        super("hud", "Turn your HUD on or off!", "hud <on / class / off>");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("on / class / off", CommandParameter.ARG_TYPE_STRING, false)
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("on")) {
                Owner.HudOff.remove(s.getName().toLowerCase());
                Owner.HudClassOnly.remove(s.getName().toLowerCase());
            } else if (args[0].equalsIgnoreCase("class")) {
                Owner.HudOff.remove(s.getName().toLowerCase());
                Owner.HudClassOnly.add(s.getName().toLowerCase());
            } else if (args[0].equalsIgnoreCase("off")) {
                Owner.HudOff.add(s.getName().toLowerCase());
                Owner.HudClassOnly.remove(s.getName().toLowerCase());
            }
            s.sendMessage(TextFormat.GREEN+"HUD Updated!");
            return true;
        } else {
            return false;
        }
    }
}