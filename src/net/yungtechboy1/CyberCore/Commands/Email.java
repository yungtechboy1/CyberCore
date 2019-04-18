package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Password;

/**
 * Created by carlt_000 on 2/4/2017.
 */
public class Email extends Command {
    CyberCoreMain Owner;

    public Email(CyberCoreMain server) {
        super("email", "Set email on server", "/email <email>");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("email", CommandParamType.RAWTEXT, true)
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (!(s instanceof Player)) {
            s.sendMessage(TextFormat.RED + "Error You Must Be A Player To Use This");
            return true;
        }
        Password a = Owner.PasswordFactoy.GetPassword((Player) s);
        if (args.length == 1) {
            a.setEmail(args[0]);
            s.sendMessage(TextFormat.RED + "This account's Email address has been updated!");
        } else {
            s.sendMessage(TextFormat.RED + "Usage: /email <email address>");
        }
        return true;
    }
}

