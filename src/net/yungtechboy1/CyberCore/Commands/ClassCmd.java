package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Class_Miner;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class ClassCmd extends Command {
    CyberCoreMain Owner;

    public ClassCmd(CyberCoreMain server) {
        super("class", "View your Class info!", "/class [key]");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("key", CommandParameter.ARG_TYPE_INT, true)
        });
        this.commandParameters.put("classes", new CommandParameter[]{
                new CommandParameter("setclass", CommandParameter.ARG_TYPE_STRING, false),
                new CommandParameter("class", CommandParameter.ARG_TYPE_STRING, false)
        });
        this.commandParameters.put("setxp", new CommandParameter[]{
                new CommandParameter("setclass", CommandParameter.ARG_TYPE_STRING, false),
                new CommandParameter("class", CommandParameter.ARG_TYPE_INT, false)
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        BaseClass bc = Owner.ClassFactory.GetClass((Player) s);
        if (args.length == 0) {
            if (bc == null) {
                s.sendMessage("ERROR You don't have a class! Please go to our site to pick a class!");
            } else {
                s.sendMessage("TOTAL XP: " + bc.getXP() + " LEVEL: " + bc.getLVL() + " XXP:"+ bc.XPRemainder(bc.getXP()));
            }
        } else if (args.length == 1) {

        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setclass")) {
                if (args[1].equalsIgnoreCase("Miner")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Miner(Owner, (Player) s, BaseClass.TYPE_MINER, 0, new ConfigSection()));
                    s.sendMessage("Class Set!");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("addxp")) {
                int xp = 0;
                try {
                    xp = Integer.parseInt(args[1]);
                } catch (Exception e) {
                }
                if (bc != null) {
                    bc.addXP(xp);
                }
                s.sendMessage(xp + "XP added!");
                return true;
            }
        }
        return true;
    }
}