package net.yungtechboy1.CyberCore.Manager.Factions.ServerCmds;


import cn.nukkit.command.Command;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

public abstract class CommandBase extends Command {

    protected FactionsMain main;

    public CommandBase(String name, String desc, String usage, FactionsMain api) {
        super(name, desc, usage);
        main = api;
    }

}