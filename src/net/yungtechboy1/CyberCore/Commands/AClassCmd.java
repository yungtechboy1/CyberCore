package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.data.CommandParamType;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.*;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
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
                new CommandParameter("del", CommandParamType.STRING, true)
        });
        this.commandParameters.put("classes", new CommandParameter[]{
                new CommandParameter("setclass", CommandParamType.STRING, false),
                new CommandParameter("class", CommandParamType.STRING, false)
        });
        this.commandParameters.put("setxp", new CommandParameter[]{
                new CommandParameter("setclass", CommandParamType.STRING, false),
                new CommandParameter("class", CommandParamType.INT, false)
        });
//        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if(!(s instanceof CorePlayer))return false;
        BaseClass bc = Owner.ClassFactory.GetClass((CorePlayer) s);
        if (args.length == 0) {
            if (bc == null) {
                s.sendMessage("ERROR You don't have a class! Please go to our site to pick a class!");
            } else {
                s.sendMessage("TOTAL XP: " + bc.getXP() + " LEVEL: " + bc.getLVL() + " XXP:"+ bc.XPRemainder(bc.getXP()));
            }
        } else if (args.length == 1) {
            if(args[0].equals("del")){
                ((CorePlayer) s).SetPlayerClass(null);
                s.sendMessage("Class Removed!");
            }
        }
        return true;
    }
}