package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class Hud extends Command {
    CyberCoreMain Owner;

    public Hud(CyberCoreMain server) {
        super("hud", "Turn your HUD on or off!", "hud <on / class / fac / pos / off>");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("on / class / fac / pos / off", CommandParamType.RAWTEXT, false)
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (args.length == 1 && s instanceof CorePlayer) {
            CorePlayer p = (CorePlayer) s;
            String snl = s.getName().toLowerCase();
            if (args[0].equalsIgnoreCase("on")) {
                p.Settings.TurnOnHUD();
            } else if (args[0].equalsIgnoreCase("class")) {
                p.Settings.setHudOff(false);
                p.Settings.setHudClassOff();
            } else if (args[0].equalsIgnoreCase("fac")) {
                p.Settings.setHudOff(false);
                p.Settings.setHudFactionOff();
            } else if (args[0].equalsIgnoreCase("pos")) {
                p.Settings.setHudOff(false);
                p.Settings.setHudPosOff();
            } else if (args[0].equalsIgnoreCase("off")) {
                p.Settings.setHudOff(false);
               p.Settings.setHudOff();
            }
            s.sendMessage(TextFormat.GREEN + "HUD Updated!");
            return true;
        } else {
            return false;
        }
    }
}