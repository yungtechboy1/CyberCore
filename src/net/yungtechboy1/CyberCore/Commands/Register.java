package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Password;

import java.util.Calendar;

/**
 * Created by carlt_000 on 2/4/2017.
 */
public class Register extends Command {
    CyberCoreMain Owner;

    public Register(CyberCoreMain server) {
        super("register", "Register on Server", "/register <password>");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("password", CommandParameter.ARG_TYPE_STRING, true)
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
        if (args.length >= 1) {
            if (!a.IsRegistered()) {
                //Check Last IP Registered
                long lr =  Owner.PasswordFactoy.getLastRegisterTimeFromPlayer((Player)s);
                long now = (Calendar.getInstance().getTime().getTime() / 1000);
                int ar = Owner.PasswordFactoy.getAccountsRegisteredWithinFromPlayer((Player)s,60*60);
                if(lr + 60*60 > now && ar > 2){
                    s.sendMessage(TextFormat.RED + "This IP has registered too many accounts! Please Try again in an hour!");
                    return true;
                }
                if (!a.RegisterPass((Player) s, args[0])) {
                    s.sendMessage(TextFormat.RED + "This account is already Registered!");
                } else {
                    s.sendMessage(TextFormat.GREEN + "Account Registered!");
                }
            }else{
                s.sendMessage(TextFormat.GREEN + "Account Registered!");
                return true;
            }
        } else {
            s.sendMessage(TextFormat.RED + "Usage: /register <password>");
        }
        return true;
    }
}

