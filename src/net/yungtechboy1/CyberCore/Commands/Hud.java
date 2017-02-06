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
            String snl = s.getName().toLowerCase();
            if (args[0].equalsIgnoreCase("on")) {
                Owner.HudOff.remove(snl);
                Owner.HUDClassOff.remove(snl);
                Owner.HUDPosOff.remove(snl);
                Owner.HUDFactionOff.remove(snl);
            } else if (args[0].equalsIgnoreCase("class")) {
                Owner.HudOff.remove(snl);
                //@TODO Check that the IF Statement Will Work!
                if(Owner.HUDClassOff.contains(snl))Owner.HUDClassOff.remove(snl);else Owner.HUDClassOff.add(snl);
            }else if (args[0].equalsIgnoreCase("fac")) {
                Owner.HudOff.remove(snl);
                //@TODO Check that the IF Statement Will Work!
                if(Owner.HUDFactionOff.contains(snl))Owner.HUDFactionOff.remove(snl);else Owner.HUDFactionOff.add(snl);
            }else if (args[0].equalsIgnoreCase("pos")) {
                Owner.HudOff.remove(snl);
                //@TODO Check that the IF Statement Will Work!
                if(Owner.HUDPosOff.contains(snl))Owner.HUDPosOff.remove(snl);else Owner.HUDPosOff.add(snl);
            } else if (args[0].equalsIgnoreCase("off")) {
                Owner.HudOff.add(snl);
            }
            s.sendMessage(TextFormat.GREEN+"HUD Updated!");
            return true;
        } else {
            return false;
        }
    }
}