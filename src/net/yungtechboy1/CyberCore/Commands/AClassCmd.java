package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.*;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class AClassCmd extends Command {
    CyberCoreMain Owner;

    public AClassCmd(CyberCoreMain server) {
        super("aclass", "View your Class info!", "/aclass [key]");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("del", CommandParameter.ARG_TYPE_STRING, true)
        });
        this.commandParameters.put("classes", new CommandParameter[]{
                new CommandParameter("setclass", CommandParameter.ARG_TYPE_STRING, false),
                new CommandParameter("class", CommandParameter.ARG_TYPE_STRING, false)
        });
        this.commandParameters.put("setxp", new CommandParameter[]{
                new CommandParameter("setclass", CommandParameter.ARG_TYPE_STRING, false),
                new CommandParameter("class", CommandParameter.ARG_TYPE_INT, false)
        });
        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if(!(s instanceof Player))return false;
        BaseClass bc = Owner.ClassFactory.GetClass((Player) s);
        if (args.length == 0) {
            if (bc == null) {
                s.sendMessage("ERROR You don't have a class! Please go to our site to pick a class!");
            } else {
                s.sendMessage("TOTAL XP: " + bc.getXP() + " LEVEL: " + bc.getLVL() + " XXP:"+ bc.XPRemainder(bc.getXP()));
            }
        } else if (args.length == 1) {
            if(args[0].equals("del")){
                Owner.ClassFactory.SetClass((Player) s,null);
                s.sendMessage("Class Removed!");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setclass")) {
                if (args[1].equalsIgnoreCase("Miner")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Miner(Owner, (Player) s, BaseClass.TYPE_MINER, 0, new ConfigSection()));
                    s.sendMessage("Class Set!");
                    return true;
                }else if (args[1].equalsIgnoreCase("digger")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Digger(Owner, (Player) s, BaseClass.TYPE_DIGGER, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a Digger!");
                } else if (args[1].equalsIgnoreCase("farmer")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Farmer(Owner, (Player) s, BaseClass.TYPE_FARMER, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a Farmer!");
                } else if (args[1].equalsIgnoreCase("LumberJack")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_LumberJack(Owner, (Player) s, BaseClass.TYPE_LUMBERJACK, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a LumberJack!");
                } else if (args[1].equalsIgnoreCase("Digger")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Miner(Owner, (Player) s, BaseClass.TYPE_MINER, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a Digger!");
                } else if (args[1].equalsIgnoreCase("Tank")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Tank(Owner, (Player) s, BaseClass.TYPE_TANK, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a Tank!");
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