package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.Old.*;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.Calendar;

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
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        BaseClass bc = Owner.ClassFactory.GetClass((Player) s);
        if (args.length == 0) {
            if (bc == null) {
                String message = "";
                message += "------------ CLASSES -----------\n";
                message += " 1 > Class Digger -> Find some thing when you dig\n";
                message += " 2 > Class Farmer -> You can farm Faster and Better!\n";
                message += " 3 > Class Lumber Jack -> Clearing Forests is your specialty\n";
                message += " 4 > Class Miner -> Double Drops and More Ore\n";
                message += " 5 > Class Tank -> Take more damage at the cost of dealing less damage\n";
                message += "--Choose a Class and Use /class set <key>--\n";
                s.sendMessage(message);
            } else {
                //Show Class Info
                s.sendMessage("TOTAL XP: " + bc.getXP() + " LEVEL: " + bc.getLVL() + " XP needed for next level:" + bc.XPRemainder(bc.getXP()));
            }
        } else if (args.length == 1) {
            Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000);
            if (bc == null || (Owner.cooldowns.getInt("class." + s.getName().toLowerCase(), 0) > ct || s.isOp())) {
                int key = 0;
                try {
                    key = Integer.parseInt(args[0]);
                }catch (Exception e){}
                if (key == 1 || args[0].equalsIgnoreCase("digger")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Digger(Owner, (Player) s, BaseClass.TYPE_DIGGER, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a Digger!");
                } else if (key == 2 || args[0].equalsIgnoreCase("Farmer")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Farmer(Owner, (Player) s, BaseClass.TYPE_FARMER, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a Farmer!");
                } else if (key == 3 || args[0].equalsIgnoreCase("LumberJack")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_LumberJack(Owner, (Player) s, BaseClass.TYPE_LUMBERJACK, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a LumberJack!");
                } else if (key == 4 || args[0].equalsIgnoreCase("miner")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Miner(Owner, (Player) s, BaseClass.TYPE_MINER, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a Miner!");
                } else if (key == 5 || args[0].equalsIgnoreCase("Tank")) {
                    Owner.ClassFactory.SetClass((Player) s, new Class_Tank(Owner, (Player) s, BaseClass.TYPE_TANK, 0, new ConfigSection()));
                    s.sendMessage(TextFormat.GREEN + "You are now a Tank!");
                } else {
                    s.sendMessage(TextFormat.RED + "Key not found!");
                    return true;
                }

                Owner.cooldowns.set("class." + s.getName().toLowerCase(), ct + 60 * 30);//30 Mins

                return true;
            } else {
                s.sendMessage(TextFormat.RED + "Error! You can not reset your class anymore!");
            }
        }
        return true;
    }
}